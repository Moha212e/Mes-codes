package org.example.model.entity;

public class Car {
    private int idCar;
    private String brand;
    private String model;
    private int year;
    private boolean isDeteled;
    private float priceday;
    public Car() {
    }
    public Car(int idCar, String brand, String model, int year, boolean isDeteled, float priceday) {
        this.idCar = idCar;
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.isDeteled = isDeteled;
        this.priceday = priceday;
    }
    public int getIdCar() {
        return idCar;
    }
    public void setIdCar(int idCar) {
        this.idCar = idCar;
    }
    public String getBrand() {
        return brand;
    }
    public void setBrand(String brand) {
        this.brand = brand;
    }
    public String getModel() {
        return model;
    }
    public void setModel(String model) {
        this.model = model;
    }
    public int getYear() {
        return year;
    }
    public void setYear(int year) {
        this.year = year;
    }
    public boolean isDeteled() {
        return isDeteled;
    }
    public void setDeteled(boolean isDeteled) {
        this.isDeteled = isDeteled;
    }
    public float getPriceday() {
        return priceday;
    }
    public void setPriceday(float priceday) {
        this.priceday = priceday;
    }
    @Override
    public String toString() {
        return "Car{" + "idCar=" + idCar + ", brand=" + brand + ", model=" + model + ", year=" + year + ", isDeteled=" + isDeteled + '}';
    }


}
