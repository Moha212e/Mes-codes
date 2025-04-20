package org.example.model.entity;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests pour la classe Client
 */
public class ClientTest {

    @Test
    public void testDefaultConstructor() {
        Client client = new Client();
        assertNotNull(client);
        assertEquals(0, client.getIdClient());
        assertNull(client.getName());
        assertNull(client.getSurname());
        assertNull(client.getEmail());
        assertNull(client.getPhoneNumber());
        assertNull(client.getBirthDate());
        assertNull(client.getLicenseNumber());
        assertNull(client.getAddress());
        assertFalse(client.isDeleted());
    }

    @Test
    public void testParameterizedConstructor() {
        LocalDate birthDate = LocalDate.of(1990, 1, 1);
        Client client = new Client(1, "John", "Doe", "john.doe@example.com", birthDate);
        assertEquals(1, client.getIdClient());
        assertEquals("John", client.getName());
        assertEquals("Doe", client.getSurname());
        assertEquals("john.doe@example.com", client.getEmail());
        assertEquals(birthDate, client.getBirthDate());
        assertFalse(client.isDeleted());
    }

    @Test
    public void testFullConstructor() {
        LocalDate birthDate = LocalDate.of(1990, 1, 1);
        Client client = new Client(1, "John", "Doe", "john.doe@example.com", "+33612345678", 
                                  birthDate, "ABC123456", "123 Main St");
        assertEquals(1, client.getIdClient());
        assertEquals("John", client.getName());
        assertEquals("Doe", client.getSurname());
        assertEquals("john.doe@example.com", client.getEmail());
        assertEquals("+33612345678", client.getPhoneNumber());
        assertEquals(birthDate, client.getBirthDate());
        assertEquals("ABC123456", client.getLicenseNumber());
        assertEquals("123 Main St", client.getAddress());
        assertFalse(client.isDeleted());
    }

    @Test
    public void testSettersAndGetters() {
        Client client = new Client();
        LocalDate birthDate = LocalDate.of(1985, 5, 15);
        
        client.setIdClient(100);
        client.setName("Jane");
        client.setSurname("Smith");
        client.setEmail("jane.smith@example.com");
        client.setPhoneNumber("+33698765432");
        client.setBirthDate(birthDate);
        client.setLicenseNumber("XYZ987654");
        client.setAddress("456 Oak St");
        client.setDeleted(true);

        assertEquals(100, client.getIdClient());
        assertEquals("Jane", client.getName());
        assertEquals("Smith", client.getSurname());
        assertEquals("jane.smith@example.com", client.getEmail());
        assertEquals("+33698765432", client.getPhoneNumber());
        assertEquals(birthDate, client.getBirthDate());
        assertEquals("XYZ987654", client.getLicenseNumber());
        assertEquals("456 Oak St", client.getAddress());
        assertTrue(client.isDeleted());
    }

    @Test
    public void testToString() {
        LocalDate birthDate = LocalDate.of(1990, 1, 1);
        Client client = new Client(2, "John", "Doe", "john.doe@example.com", "+33612345678", 
                                  birthDate, "ABC123456", "123 Main St");
        
        String toString = client.toString();
        assertTrue(toString.contains("idClient=2"));
        assertTrue(toString.contains("name='John'"));
        assertTrue(toString.contains("surname='Doe'"));
        assertTrue(toString.contains("email='john.doe@example.com'"));
    }
    
    @Test
    public void testGetId() {
        Client client = new Client(5, "Alice", "Johnson", "alice@example.com", LocalDate.of(1995, 3, 10));
        assertEquals(5, client.getId());
    }
    
    @Test
    public void testEqualsAndHashCode() {
        Client client1 = new Client(1, "John", "Doe", "john@example.com", LocalDate.of(1990, 1, 1));
        Client client2 = new Client(1, "Jane", "Smith", "jane@example.com", LocalDate.of(1995, 5, 5));
        Client client3 = new Client(2, "John", "Doe", "john@example.com", LocalDate.of(1990, 1, 1));
        
        // Test equals
        assertEquals(client1, client1); // Réflexivité
        assertEquals(client1, client2); // Même ID => égaux
        assertNotEquals(client1, client3); // ID différent => pas égaux
        assertNotEquals(client1, null); // Comparaison avec null
        assertNotEquals(client1, "Not a client"); // Comparaison avec un autre type
        
        // Test hashCode
        assertEquals(client1.hashCode(), client2.hashCode()); // Même ID => même hashCode
        assertNotEquals(client1.hashCode(), client3.hashCode()); // ID différent => hashCode différent
    }
}