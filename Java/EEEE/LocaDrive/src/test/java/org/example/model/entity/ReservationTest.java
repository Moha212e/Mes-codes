package org.example.model.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/*
    private int idReservation;
    private Date startDate;
    private Date endDate;
    private boolean isDeteled;
 */

public class ReservationTest {

    @Test
    public void testDefaultConstructor() {
        Reservation reservation = new Reservation();
        assertNotNull(reservation);
        assertEquals(0, reservation.getIdReservation());
        assertNull(reservation.getStartDate());
        assertNull(reservation.getEndDate());
        assertFalse(reservation.isDeteled());
        assertNull(reservation.getResponsable());
        assertNull(reservation.getNotes());
        assertEquals(0, reservation.getPrice());


    }
    @Test
    public void testParameterizedConstructor() {
        Reservation reservation = new Reservation(1, null, null, false, null, null,0);
        assertEquals(1, reservation.getIdReservation());
        assertNull(reservation.getStartDate());
        assertNull(reservation.getEndDate());
        assertFalse(reservation.isDeteled());
        assertNull(reservation.getResponsable());
        assertNull(reservation.getNotes());
        assertEquals(0, reservation.getPrice());

    }

    @Test
    public void testSettersAndGetters() {
        Reservation reservation = new Reservation();
        reservation.setIdReservation(100);
        reservation.setStartDate(null);
        reservation.setEndDate(null);
        reservation.setDeteled(true);
        reservation.setResponsable(null);
        reservation.setNotes(null);
        reservation.setPrice(0);


        assertEquals(100, reservation.getIdReservation());
        assertNull(reservation.getStartDate());
        assertNull(reservation.getEndDate());
        assertTrue(reservation.isDeteled());
        assertNull(reservation.getResponsable());
        assertNull(reservation.getNotes());
        assertEquals(0, reservation.getPrice());

    }

    @Test
    public void testToString() {
        Reservation reservation = new Reservation(2, null, null, false, null, null,0);
        String expected = "Reservation{idReservation=2, startDate=null, endDate=null, isDeteled=false}";
        assertEquals(expected, reservation.toString());



    }
}