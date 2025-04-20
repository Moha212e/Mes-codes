package org.example.model.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests pour la classe Reservation
 */
public class ReservationTest {

    @Test
    public void testDefaultConstructor() {
        Reservation reservation = new Reservation();
        assertNotNull(reservation);
        assertEquals(0, reservation.getIdReservation());
        assertNull(reservation.getResponsable());
        assertNull(reservation.getNotes());
        assertEquals(0, reservation.getPrice(), 0.0001);
        assertNotNull(reservation.getCar());
        assertNotNull(reservation.getClient());
        assertFalse(reservation.isDeleted());
    }
    
    @Test
    public void testParameterizedConstructor() {
        Reservation reservation = new Reservation(1, "John Doe", "No notes", 100.0f);
        assertEquals(1, reservation.getIdReservation());
        assertEquals("John Doe", reservation.getResponsable());
        assertEquals("No notes", reservation.getNotes());
        assertEquals(100.0f, reservation.getPrice(), 0.0001);
        assertNotNull(reservation.getCar());
        assertNotNull(reservation.getClient());
        assertFalse(reservation.isDeleted());
    }

    @Test
    public void testSettersAndGetters() {
        Reservation reservation = new Reservation();
        Car car = new Car(1, "Toyota", "Corolla", 2020, 25);
        Client client = new Client(1, "John", "Doe", "john@example.com", null);
        
        reservation.setIdReservation(100);
        reservation.setResponsable("Jane Smith");
        reservation.setNotes("Special request");
        reservation.setPrice(150.0f);
        reservation.setCar(car);
        reservation.setClient(client);
        reservation.setDeleted(true);

        assertEquals(100, reservation.getIdReservation());
        assertEquals("Jane Smith", reservation.getResponsable());
        assertEquals("Special request", reservation.getNotes());
        assertEquals(150.0f, reservation.getPrice(), 0.0001);
        assertEquals(car, reservation.getCar());
        assertEquals(client, reservation.getClient());
        assertTrue(reservation.isDeleted());
    }

    @Test
    public void testToString() {
        Reservation reservation = new Reservation(1, "John Doe", "No notes", 100.0f);
        String toString = reservation.toString();
        assertTrue(toString.contains("idReservation=1"));
    }
    
    @Test
    public void testGetId() {
        Reservation reservation = new Reservation(5, "Manager", "VIP client", 200.0f);
        assertEquals(5, reservation.getId());
    }
    
    @Test
    public void testEqualsAndHashCode() {
        Reservation reservation1 = new Reservation(1, "John", "Note 1", 100.0f);
        Reservation reservation2 = new Reservation(1, "Jane", "Note 2", 150.0f);
        Reservation reservation3 = new Reservation(2, "John", "Note 1", 100.0f);
        
        // Test equals
        assertEquals(reservation1, reservation1); // Réflexivité
        assertEquals(reservation1, reservation2); // Même ID => égaux
        assertNotEquals(reservation1, reservation3); // ID différent => pas égaux
        assertNotEquals(reservation1, null); // Comparaison avec null
        assertNotEquals(reservation1, "Not a reservation"); // Comparaison avec un autre type
        
        // Test hashCode
        assertEquals(reservation1.hashCode(), reservation2.hashCode()); // Même ID => même hashCode
        assertNotEquals(reservation1.hashCode(), reservation3.hashCode()); // ID différent => hashCode différent
    }
}