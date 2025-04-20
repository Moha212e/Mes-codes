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
        assertFalse(car.isDeteled());
        assertEquals(0, car.getPriceday(), 0.0001);
    }

    @Test
    public void testParameterizedConstructor() {
        Car car = new Car(1, "Toyota", "Corolla", 2020, false,25);
        assertEquals(1, car.getIdCar());
        assertEquals("Toyota", car.getBrand());
        assertEquals("Corolla", car.getModel());
        assertEquals(2020, car.getYear());
        assertFalse(car.isDeteled());
        assertEquals(25, car.getPriceday(), 0.0001);

    }

    @Test
    public void testSettersAndGetters() {
        Car car = new Car();
        car.setIdCar(100);
        car.setBrand("Honda");
        car.setModel("Civic");
        car.setYear(2019);
        car.setDeteled(true);
        car.setPriceday(25);

        assertEquals(100, car.getIdCar());
        assertEquals("Honda", car.getBrand());
        assertEquals("Civic", car.getModel());
        assertEquals(2019, car.getYear());
        assertTrue(car.isDeteled());
        assertEquals(25, car.getPriceday(), 0.0001);
    }

    @Test
    public void testToString() {
        Car car = new Car(2, "BMW", "X5", 2022, false,25);
        String expected = "Car{idCar=2, brand=BMW, model=X5, year=2022, isDeteled=false}";
        assertEquals(expected, car.toString());
    }
}