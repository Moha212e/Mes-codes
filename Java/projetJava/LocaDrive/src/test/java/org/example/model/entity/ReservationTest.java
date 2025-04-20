package org.example.model.entity;

import org.junit.jupiter.api.Test;
import java.util.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import static org.junit.jupiter.api.Assertions.*;

/*
    private int idReservation;
    private Date startDate;
    private Date endDate;
    private String responsable;
    private String notes;
    private float price;
    private String carId;
    private int clientId;
    private String contratId;
 */

public class ReservationTest {

    @Test
    public void testDefaultConstructor() {
        Reservation reservation = new Reservation();
        assertNotNull(reservation);
        assertEquals(0, reservation.getIdReservation());
        assertNull(reservation.getStartDate());
        assertNull(reservation.getEndDate());
        assertEquals("", reservation.getResponsable());
        assertEquals("", reservation.getNotes());
        assertEquals(0, reservation.getPrice(), 0.001);
        assertNull(reservation.getCar());
        assertNull(reservation.getClient());
        assertNull(reservation.getContrat());
        assertNull(reservation.getCarId());
        assertEquals(0, reservation.getClientId());
        assertNull(reservation.getContratId());
    }
    
    @Test
    public void testParameterizedConstructor() {
        Date startDate = new Date();
        Date endDate = new Date();
        Reservation reservation = new Reservation(2, startDate, endDate, "John Doe", "Test notes", 100.0f);
        assertEquals(2, reservation.getIdReservation());
        assertEquals(startDate, reservation.getStartDate());
        assertEquals(endDate, reservation.getEndDate());
        assertEquals("John Doe", reservation.getResponsable());
        assertEquals("Test notes", reservation.getNotes());
        assertEquals(100.0f, reservation.getPrice(), 0.001);
        assertNull(reservation.getCar());
        assertNull(reservation.getClient());
        assertNull(reservation.getContrat());
    }

    @Test
    public void testFullConstructor() {
        // Créer des objets pour le test
        Car car = new Car("1-ABC-123", "Toyota", "Corolla", 2020, 25);
        Client client = new Client(1, "John", "Doe", "john@example.com", "password", LocalDate.of(1990, 1, 1));
        LocalDate startLocalDate = LocalDate.of(2023, 1, 1);
        LocalDate endLocalDate = LocalDate.of(2023, 1, 10);
        Date startDate = Date.from(startLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date endDate = Date.from(endLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Contrat contrat = new Contrat("C123", 1000.0, "Tous risques", true, StatutContrat.SIGNE);
        
        // Créer la réservation avec le constructeur complet
        Reservation reservation = new Reservation(3, car, client, startLocalDate, endLocalDate, "Jane Doe", 250.0f, "Full test notes", contrat);
        
        // Vérifier les valeurs
        assertEquals(3, reservation.getIdReservation());
        assertNotNull(reservation.getStartDate());
        assertNotNull(reservation.getEndDate());
        assertEquals("Jane Doe", reservation.getResponsable());
        assertEquals("Full test notes", reservation.getNotes());
        assertEquals(250.0f, reservation.getPrice(), 0.001);
        assertEquals(car, reservation.getCar());
        assertEquals(client, reservation.getClient());
        assertEquals(contrat, reservation.getContrat());
        assertEquals("1-ABC-123", reservation.getCarId());
        assertEquals(1, reservation.getClientId());
        assertEquals("C123", reservation.getContratId());
    }

    @Test
    public void testSettersAndGetters() {
        Reservation reservation = new Reservation();
        Date startDate = new Date();
        Date endDate = new Date();
        
        reservation.setIdReservation(5);
        reservation.setStartDate(startDate);
        reservation.setEndDate(endDate);
        reservation.setResponsable("Jane Doe");
        reservation.setNotes("Updated notes");
        reservation.setPrice(150.0f);
        
        // Ajouter des tests pour les nouvelles propriétés
        Car car = new Car("1-DEF-456", "Honda", "Civic", 2021, 30);
        Client client = new Client(2, "Alice", "Smith", "alice@example.com", "password", LocalDate.of(1985, 5, 15));
        Contrat contrat = new Contrat("C456", 1500.0, "Tiers", false, StatutContrat.EN_ATTENTE);
        
        reservation.setCar(car);
        reservation.setClient(client);
        reservation.setContrat(contrat);
        
        assertEquals(5, reservation.getIdReservation());
        assertEquals(startDate, reservation.getStartDate());
        assertEquals(endDate, reservation.getEndDate());
        assertEquals("Jane Doe", reservation.getResponsable());
        assertEquals("Updated notes", reservation.getNotes());
        assertEquals(150.0f, reservation.getPrice(), 0.001);
        assertEquals(car, reservation.getCar());
        assertEquals(client, reservation.getClient());
        assertEquals(contrat, reservation.getContrat());
        assertEquals("1-DEF-456", reservation.getCarId());
        assertEquals(2, reservation.getClientId());
        assertEquals("C456", reservation.getContratId());
        
        // Tester la définition directe des IDs
        reservation = new Reservation();
        reservation.setCarId("1-GHI-789");
        reservation.setClientId(3);
        reservation.setContratId("C789");
        
        assertEquals("1-GHI-789", reservation.getCarId());
        assertEquals(3, reservation.getClientId());
        assertEquals("C789", reservation.getContratId());
    }

    @Test
    public void testToString() {
        Reservation reservation = new Reservation(2, null, null, "John Doe", "Test notes", 100.0f);
        String expected = "Reservation{idReservation=2, startDate=null, endDate=null, responsable='John Doe', notes='Test notes', price=100.0}";  
        assertEquals(expected, reservation.toString());
    }
}