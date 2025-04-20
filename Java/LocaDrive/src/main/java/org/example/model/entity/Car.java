package org.example.model.entity;

import com.google.gson.annotations.Expose;

/**
 * Classe représentant une voiture dans le système
 */
public class Car extends AbstractEntity implements Identifiable {
    
    private String idCar; // Immatriculation belge (ex: 1-ABC-123)
    
    private String brand;
    
    private String model;
    
    private int year;
    
    private float priceday;
    
    private int mileage; // Kilométrage
    
    private String fuelType; // Type de carburant
    
    private String transmission; // Type de transmission
    
    private int seats; // Nombre de places
    
    private boolean available; // Disponibilité
    
    private String image;
    
    /**
     * Constructeur par défaut
     */
    public Car() {
        this.available = true; // Par défaut, une nouvelle voiture est disponible
    }
    
    /**
     * Constructeur avec les attributs de base
     */
    public Car(String idCar, String brand, String model, int year, float priceday, String image) {
        this.idCar = idCar;
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.priceday = priceday;
        this.available = true; // Par défaut, une nouvelle voiture est disponible
        this.image = "";
    }
    
    /**
     * Constructeur complet avec tous les attributs
     */
    public Car(String idCar, String brand, String model, int year, float priceday, 
               int mileage, String fuelType, String transmission, int seats, boolean available, String image) {
        this.idCar = idCar;
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.priceday = priceday;
        this.mileage = mileage;
        this.fuelType = fuelType;
        this.transmission = transmission;
        this.seats = seats;
        this.available = available;
        this.image = image;
    }
    
    // Getters et setters
    public String getIdCar() {
        return idCar;
    }
    
    public void setIdCar(String idCar) {
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
    
    public float getPriceday() {
        return priceday;
    }
    
    public void setPriceday(float priceday) {
        this.priceday = priceday;
    }
    
    public int getMileage() {
        return mileage;
    }
    
    public void setMileage(int mileage) {
        this.mileage = mileage;
    }
    
    public String getFuelType() {
        return fuelType;
    }
    
    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }
    
    public String getTransmission() {
        return transmission;
    }
    
    public void setTransmission(String transmission) {
        this.transmission = transmission;
    }
    
    public int getSeats() {
        return seats;
    }
    
    public void setSeats(int seats) {
        this.seats = seats;
    }
    public void setImage(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }
    
    public boolean isAvailable() {
        return available;
    }
    
    public void setAvailable(boolean available) {
        this.available = available;
    }

    
    @Override
    public Object getId() {
        return idCar;
    }
    
    @Override
    public String toString() {
        return "Car{" + 
               "idCar='" + idCar + '\'' + 
               ", brand='" + brand + '\'' + 
               ", model='" + model + '\'' + 
               ", year=" + year + 
               ", priceday=" + priceday + 
               ", mileage=" + mileage + 
               ", fuelType='" + fuelType + '\'' + 
               ", transmission='" + transmission + '\'' + 
               ", seats=" + seats + 
               ", available=" + available +
               ", image='" + image + '\'' +
               '}';
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        Car car = (Car) o;
        
        return idCar != null ? idCar.equals(car.idCar) : car.idCar == null;
    }
    
    @Override
    public int hashCode() {
        return idCar != null ? idCar.hashCode() : 0;
    }
}
