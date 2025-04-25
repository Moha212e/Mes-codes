package org.example.model.entity;

import org.junit.Test;
import java.time.LocalDate;
import static org.junit.Assert.*;

/**
 * Tests unitaires pour la classe Client
 */
public class ClientTest {

    @Test
    public void testClientConstructor() {
        // Test du constructeur par défaut
        Client client = new Client();
        assertNotNull(client);
        assertEquals(0, client.getIdClient());
        
        // Test du constructeur avec paramètres
        int id = 1;
        String name = "Dupont";
        String surname = "Jean";
        LocalDate birthDate = LocalDate.of(1990, 5, 15);
        String licenseNumber = "ABC123456";
        String address = "123 Rue Test";
        String phoneNumber = "0123456789";
        
        Client clientWithParams = new Client(id, name, surname, birthDate, licenseNumber, address, phoneNumber);
        
        assertEquals(id, clientWithParams.getIdClient());
        assertEquals(name, clientWithParams.getName());
        assertEquals(surname, clientWithParams.getSurname());
        assertEquals(birthDate, clientWithParams.getBirthDate());
        assertEquals(licenseNumber, clientWithParams.getLicenseNumber());
        assertEquals(address, clientWithParams.getAddress());
        assertEquals(phoneNumber, clientWithParams.getPhoneNumber());
    }
    
    @Test
    public void testGettersAndSetters() {
        Client client = new Client();
        
        // Test des setters
        int id = 2;
        String name = "Martin";
        String surname = "Sophie";
        LocalDate birthDate = LocalDate.of(1985, 10, 20);
        String licenseNumber = "XYZ789012";
        String address = "456 Avenue Test";
        String phoneNumber = "9876543210";
        
        client.setIdClient(id);
        client.setName(name);
        client.setSurname(surname);
        client.setBirthDate(birthDate);
        client.setLicenseNumber(licenseNumber);
        client.setAddress(address);
        client.setPhoneNumber(phoneNumber);
        
        // Test des getters
        assertEquals(id, client.getIdClient());
        assertEquals(name, client.getName());
        assertEquals(surname, client.getSurname());
        assertEquals(birthDate, client.getBirthDate());
        assertEquals(licenseNumber, client.getLicenseNumber());
        assertEquals(address, client.getAddress());
        assertEquals(phoneNumber, client.getPhoneNumber());
    }
    
    @Test
    public void testToString() {
        Client client = new Client(3, "Dubois", "Pierre", LocalDate.of(1975, 3, 8), 
                "DEF456789", "789 Boulevard Test", "0567891234");
        
        String toString = client.toString();
        
        // Vérifier que la méthode toString contient les informations essentielles
        assertTrue(toString.contains("Dubois"));
        assertTrue(toString.contains("Pierre"));
    }
    
    @Test
    public void testEqualsAndHashCode() {
        Client client1 = new Client(4, "Leroy", "Marie", LocalDate.of(1980, 7, 12), 
                "GHI789012", "101 Rue Test", "0321654987");
        Client client2 = new Client(4, "Leroy", "Marie", LocalDate.of(1980, 7, 12), 
                "GHI789012", "101 Rue Test", "0321654987");
        Client client3 = new Client(5, "Leroy", "Marie", LocalDate.of(1980, 7, 12), 
                "GHI789012", "101 Rue Test", "0321654987");
        
        // Test equals
        assertEquals(client1, client2);
        assertNotEquals(client1, client3);
        
        // Test hashCode
        assertEquals(client1.hashCode(), client2.hashCode());
        assertNotEquals(client1.hashCode(), client3.hashCode());
    }
    
    @Test
    public void testFullName() {
        Client client = new Client();
        client.setName("Petit");
        client.setSurname("Louis");
        
        assertEquals("Petit Louis", client.getName() + " " + client.getSurname());
    }
}
