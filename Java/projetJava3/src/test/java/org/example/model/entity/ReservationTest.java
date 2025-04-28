package org.example.model.entity;

import org.junit.Test;
import java.time.LocalDate;
import static org.junit.Assert.*;

/**
 * Tests unitaires pour la classe Reservation
 */
public class ReservationTest {

    @Test
    public void testReservationConstructor() {
        // Test du constructeur par défaut
        Reservation reservation = new Reservation();
        assertNotNull(reservation);
        assertEquals(0, reservation.getIdReservation());
        
        // Test du constructeur avec paramètres de base
        int idReservation = 1;
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now().plusDays(7);
        String responsable = "Agent Test";
        String notes = "Notes de test";
        float price = 350.0f;
        
        Reservation reservationWithParams = new Reservation(idReservation, startDate, endDate, responsable, notes, price);
        
        assertEquals(idReservation, reservationWithParams.getIdReservation());
        assertEquals(startDate, reservationWithParams.getStartDate());
        assertEquals(endDate, reservationWithParams.getEndDate());
        assertEquals(responsable, reservationWithParams.getResponsable());
        assertEquals(notes, reservationWithParams.getNotes());
        assertEquals(price, reservationWithParams.getPrice(), 0.001);
    }
    
    @Test
    public void testReservationCompleteConstructor() {
        // Test du constructeur complet avec objets associés
        int idReservation = 2;
        Car car = new Car("AB-123-CD", "Renault", "Clio", 2020, 45.0f, true, 
                "Essence", "Manuelle", 5, 15000, "clio.jpg");
        Client client = new Client(1, "Dupont", "Jean", LocalDate.of(1990, 5, 15), 
                "ABC123456", "123 Rue Test", "0123456789");
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now().plusDays(7);
        String responsable = "Agent Test";
        float price = 350.0f;
        String notes = "Notes de test";
        Contrat contrat = new Contrat();
        contrat.setIdContrat("C1");
        
        Reservation reservation = new Reservation(idReservation, car, client, startDate, endDate, 
                responsable, price, notes, contrat);
        
        assertEquals(idReservation, reservation.getIdReservation());
        assertEquals(car, reservation.getCar());
        assertEquals(client, reservation.getClient());
        assertEquals(startDate, reservation.getStartDate());
        assertEquals(endDate, reservation.getEndDate());
        assertEquals(responsable, reservation.getResponsable());
        assertEquals(price, reservation.getPrice(), 0.001);
        assertEquals(notes, reservation.getNotes());
        assertEquals(contrat, reservation.getContrat());
        
        // Vérifier que les IDs sont correctement stockés
        assertEquals(car.getIdCar(), reservation.getCarId());
        assertEquals(client.getIdClient(), reservation.getClientId());
        assertEquals(contrat.getIdContrat(), reservation.getContratId());
    }
    
    @Test
    public void testGettersAndSetters() {
        Reservation reservation = new Reservation();
        
        // Test des setters
        int idReservation = 3;
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now().plusDays(5);
        String responsable = "Agent Test 2";
        String notes = "Notes de test modifiées";
        float price = 250.0f;
        
        reservation.setIdReservation(idReservation);
        reservation.setStartDate(startDate);
        reservation.setEndDate(endDate);
        reservation.setResponsable(responsable);
        reservation.setNotes(notes);
        reservation.setPrice(price);
        
        // Test des getters
        assertEquals(idReservation, reservation.getIdReservation());
        assertEquals(startDate, reservation.getStartDate());
        assertEquals(endDate, reservation.getEndDate());
        assertEquals(responsable, reservation.getResponsable());
        assertEquals(notes, reservation.getNotes());
        assertEquals(price, reservation.getPrice(), 0.001);
    }
    
    @Test
    public void testCarAssociation() {
        Reservation reservation = new Reservation();
        Car car = new Car("XY-456-ZW", "Peugeot", "308", 2019, 50.0f, false, 
                "Diesel", "Automatique", 5, 25000, "308.jpg");
        
        reservation.setCar(car);
        
        assertEquals(car, reservation.getCar());
        assertEquals(car.getIdCar(), reservation.getCarId());
        assertEquals(car.getIdCar(), reservation.getCarRegistration());
        
        // Test avec null
        reservation.setCar(null);
        assertNull(reservation.getCar());
        assertNull(reservation.getCarId());
        assertNull(reservation.getCarRegistration());
    }
    
    @Test
    public void testClientAssociation() {
        Reservation reservation = new Reservation();
        Client client = new Client(2, "Martin", "Sophie", LocalDate.of(1985, 10, 20), 
                "XYZ789012", "456 Avenue Test", "9876543210");
        
        reservation.setClient(client);
        
        assertEquals(client, reservation.getClient());
        assertEquals(client.getIdClient(), reservation.getClientId());
        assertEquals(client.getName() + " " + client.getSurname(), reservation.getClientFullName());
        
        // Test avec null
        reservation.setClient(null);
        assertNull(reservation.getClient());
        assertEquals(0, reservation.getClientId());
        assertNull(reservation.getClientFullName());
    }
    
    @Test
    public void testContratAssociation() {
        Reservation reservation = new Reservation();
        Contrat contrat = new Contrat();
        contrat.setIdContrat("C2");
        
        reservation.setContrat(contrat);
        
        assertEquals(contrat, reservation.getContrat());
        assertEquals(contrat.getIdContrat(), reservation.getContratId());
        
        // Test avec null
        reservation.setContrat(null);
        assertNull(reservation.getContrat());
        assertNull(reservation.getContratId());
    }
    
    @Test
    public void testDurationCalculation() {
        Reservation reservation = new Reservation();
        LocalDate startDate = LocalDate.of(2023, 5, 1);
        LocalDate endDate = LocalDate.of(2023, 5, 8);
        
        reservation.setStartDate(startDate);
        reservation.setEndDate(endDate);
        
        // La durée devrait être de 7 jours (du 1er au 8 mai)
        long expectedDays = 7;
        long actualDays = java.time.temporal.ChronoUnit.DAYS.between(startDate, endDate);
        
        assertEquals(expectedDays, actualDays);
    }
}
