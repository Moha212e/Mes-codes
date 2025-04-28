package org.example.model.entity;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests unitaires pour la classe Car
 */
public class CarTest {

    @Test
    public void testCarConstructor() {
        // Test du constructeur par défaut
        Car car = new Car();
        assertNotNull(car);
        
        // Test du constructeur avec paramètres
        String idCar = "AB-123-CD";
        String brand = "Renault";
        String model = "Clio";
        int year = 2020;
        float priceday = 45.0f;
        boolean available = true;
        String fuelType = "Essence";
        String transmission = "Manuelle";
        int seats = 5;
        int mileage = 15000;
        String image = "clio.jpg";
        
        Car carWithParams = new Car(idCar, brand, model, year, priceday, available, 
                fuelType, transmission, seats, mileage, image);
        
        assertEquals(idCar, carWithParams.getIdCar());
        assertEquals(brand, carWithParams.getBrand());
        assertEquals(model, carWithParams.getModel());
        assertEquals(year, carWithParams.getYear());
        assertEquals(priceday, carWithParams.getPriceday(), 0.001);
        assertEquals(available, carWithParams.isAvailable());
        assertEquals(fuelType, carWithParams.getFuelType());
        assertEquals(transmission, carWithParams.getTransmission());
        assertEquals(seats, carWithParams.getSeats());
        assertEquals(mileage, carWithParams.getMileage());
        assertEquals(image, carWithParams.getImage());
    }
    
    @Test
    public void testGettersAndSetters() {
        Car car = new Car();
        
        // Test des setters
        String idCar = "XY-456-ZW";
        String brand = "Peugeot";
        String model = "308";
        int year = 2019;
        float priceday = 50.0f;
        boolean available = false;
        String fuelType = "Diesel";
        String transmission = "Automatique";
        int seats = 5;
        int mileage = 25000;
        String image = "308.jpg";
        
        car.setIdCar(idCar);
        car.setBrand(brand);
        car.setModel(model);
        car.setYear(year);
        car.setPriceday(priceday);
        car.setAvailable(available);
        car.setFuelType(fuelType);
        car.setTransmission(transmission);
        car.setSeats(seats);
        car.setMileage(mileage);
        car.setImage(image);
        
        // Test des getters
        assertEquals(idCar, car.getIdCar());
        assertEquals(brand, car.getBrand());
        assertEquals(model, car.getModel());
        assertEquals(year, car.getYear());
        assertEquals(priceday, car.getPriceday(), 0.001);
        assertEquals(available, car.isAvailable());
        assertEquals(fuelType, car.getFuelType());
        assertEquals(transmission, car.getTransmission());
        assertEquals(seats, car.getSeats());
        assertEquals(mileage, car.getMileage());
        assertEquals(image, car.getImage());
    }
    
    @Test
    public void testToString() {
        Car car = new Car("AB-123-CD", "Renault", "Clio", 2020, 45.0f, true, 
                "Essence", "Manuelle", 5, 15000, "clio.jpg");
        
        String toString = car.toString();
        
        // Vérifier que la méthode toString contient les informations essentielles
        assertTrue(toString.contains("Renault"));
        assertTrue(toString.contains("Clio"));
        assertTrue(toString.contains("AB-123-CD"));
    }
    
    @Test
    public void testEqualsAndHashCode() {
        Car car1 = new Car("AB-123-CD", "Renault", "Clio", 2020, 45.0f, true, 
                "Essence", "Manuelle", 5, 15000, "clio.jpg");
        Car car2 = new Car("AB-123-CD", "Renault", "Clio", 2020, 45.0f, true, 
                "Essence", "Manuelle", 5, 15000, "clio.jpg");
        Car car3 = new Car("XY-456-ZW", "Peugeot", "308", 2019, 50.0f, false, 
                "Diesel", "Automatique", 5, 25000, "308.jpg");
        
        // Test equals
        assertEquals(car1, car2);
        assertNotEquals(car1, car3);
        
        // Test hashCode
        assertEquals(car1.hashCode(), car2.hashCode());
        assertNotEquals(car1.hashCode(), car3.hashCode());
    }
    
    @Test
    public void testAvailabilityChange() {
        Car car = new Car();
        car.setAvailable(true);
        assertTrue(car.isAvailable());
        
        car.setAvailable(false);
        assertFalse(car.isAvailable());
    }
    
    @Test
    public void testCarDescription() {
        Car car = new Car("AB-123-CD", "Renault", "Clio", 2020, 45.0f, true, 
                "Essence", "Manuelle", 5, 15000, "clio.jpg");
        
        String description = car.getBrand() + " " + car.getModel() + " (" + car.getYear() + ")";
        assertEquals("Renault Clio (2020)", description);
    }
}
