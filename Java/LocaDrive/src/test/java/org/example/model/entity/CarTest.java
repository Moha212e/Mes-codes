package org.example.model.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CarTest {

    @Test
    public void testDefaultConstructor() {
        Car car = new Car();
        assertNotNull(car);
        assertEquals(0, car.getIdCar());
        assertNull(car.getBrand());
        assertNull(car.getModel());
        assertEquals(0, car.getYear());
        assertEquals(0, car.getPriceday(), 0.0001);
        assertTrue(car.isAvailable()); // Vérifie que la voiture est disponible par défaut
        assertFalse(car.isDeleted()); // Vérifie que la voiture n'est pas supprimée par défaut
    }

    @Test
    public void testParameterizedConstructor() {
        Car car = new Car(1, "Toyota", "Corolla", 2020, 25);
        assertEquals(1, car.getIdCar());
        assertEquals("Toyota", car.getBrand());
        assertEquals("Corolla", car.getModel());
        assertEquals(2020, car.getYear());
        assertEquals(25, car.getPriceday(), 0.0001);
        assertTrue(car.isAvailable()); // Vérifie que la voiture est disponible par défaut
        assertFalse(car.isDeleted()); // Vérifie que la voiture n'est pas supprimée par défaut
    }

    @Test
    public void testSettersAndGetters() {
        Car car = new Car();
        car.setIdCar(100);
        car.setBrand("Honda");
        car.setModel("Civic");
        car.setYear(2019);
        car.setPriceday(25);
        car.setAvailable(false);
        car.setDeleted(true);

        assertEquals(100, car.getIdCar());
        assertEquals("Honda", car.getBrand());
        assertEquals("Civic", car.getModel());
        assertEquals(2019, car.getYear());
        assertEquals(25, car.getPriceday(), 0.0001);
        assertFalse(car.isAvailable());
        assertTrue(car.isDeleted());
    }

    @Test
    public void testToString() {
        Car car = new Car(2, "BMW", "X5", 2022, 25);
        car.setAvailable(false);
        
        String toString = car.toString();
        assertTrue(toString.contains("idCar=2"));
        assertTrue(toString.contains("brand='BMW'"));
        assertTrue(toString.contains("model='X5'"));
        assertTrue(toString.contains("year=2022"));
        assertTrue(toString.contains("available=false"));
    }
    
    @Test
    public void testGetId() {
        Car car = new Car(5, "Audi", "A4", 2021, 30);
        assertEquals(5, car.getId());
    }
    
    @Test
    public void testEqualsAndHashCode() {
        Car car1 = new Car(1, "Toyota", "Corolla", 2020, 25);
        Car car2 = new Car(1, "Honda", "Civic", 2019, 20); // Même ID mais différentes propriétés
        Car car3 = new Car(2, "Toyota", "Corolla", 2020, 25); // Mêmes propriétés mais ID différent
        
        // Test equals
        assertEquals(car1, car1); // Réflexivité
        assertEquals(car1, car2); // Même ID => égaux
        assertNotEquals(car1, car3); // ID différent => pas égaux
        assertNotEquals(car1, null); // Comparaison avec null
        assertNotEquals(car1, "Not a car"); // Comparaison avec un autre type
        
        // Test hashCode
        assertEquals(car1.hashCode(), car2.hashCode()); // Même ID => même hashCode
        assertNotEquals(car1.hashCode(), car3.hashCode()); // ID différent => hashCode différent
    }
}