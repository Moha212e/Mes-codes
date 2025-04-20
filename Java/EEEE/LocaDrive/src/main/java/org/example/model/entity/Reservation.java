package org.example.model.entity;

import java.util.Date;


public class Reservation {
    private int idReservation;
    private Date startDate;
    private Date endDate;
    private String responsable;
    private boolean isDeteled;
    private String notes;
    private float price;

    public Reservation() {
    }

    public Reservation(int idReservation, Date startDate, Date endDate, boolean isDeteled, String responsable, String notes, float price) {
        this.idReservation = idReservation;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isDeteled = isDeteled;
        this.responsable = responsable;
        this.notes = notes;
        this.price = price;

    }

    public int getIdReservation() {
        return idReservation;
    }

    public void setIdReservation(int idReservation) {
        this.idReservation = idReservation;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public boolean isDeteled() {
        return isDeteled;
    }

    public void setDeteled(boolean isDeteled) {
        this.isDeteled = isDeteled;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    @Override
    public String toString() {
        return "Reservation{" + "idReservation=" + idReservation + ", startDate=" + startDate + ", endDate=" + endDate + ", isDeteled=" + isDeteled + '}';
    }
    public String getNotes() {
        return notes;
    }
    public void setNotes(String notes) {
        this.notes = notes;
    }
    public float getPrice() {
        return price;
    }
    public void setPrice(float price) {
        this.price = price;
    }
}
