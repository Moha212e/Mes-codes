package org.example.model.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/* private int idClient;
    private String name;
    private String surname;
    private String email;
    private String password;
    private Date birthDate;
    private boolean isDeteled;*/

public class ClientTest {

    @Test
    public void testDefaultConstructor() {
        Client client = new Client();
        assertNotNull(client);
        assertEquals(0, client.getIdClient());
        assertEquals("", client.getName());
        assertEquals("", client.getSurname());
        assertEquals("", client.getEmail());
        assertEquals("", client.getPassword());
        assertNull(client.getBirthDate());
    }

    @Test
    public void testParameterizedConstructor() {
        LocalDate birthDate = LocalDate.of(1990, 1, 1);
        Client client = new Client(2, "Pasch", "Mueller", "<EMAIL>", "<PASSWORD>", birthDate);
        assertEquals(2, client.getIdClient());
        assertEquals("Pasch", client.getName());
        assertEquals("Mueller", client.getSurname());
        assertEquals("<EMAIL>", client.getEmail());
        assertEquals("<PASSWORD>", client.getPassword());
        assertEquals(birthDate, client.getBirthDate());
    }

    @Test
    public void testSettersAndGetters() {
        Client client = new Client();
        client.setIdClient(100);
        client.setName("Pasch");
        client.setSurname("Mueller");
        client.setEmail("<EMAIL>");
        client.setPassword("<PASSWORD>");

        assertEquals(100, client.getIdClient());
        assertEquals("Pasch", client.getName());
        assertEquals("Mueller", client.getSurname());
        assertEquals("<EMAIL>", client.getEmail());
        assertEquals("<PASSWORD>", client.getPassword());
    }

    @Test
    public void testToString() {
        LocalDate birthDate = LocalDate.of(1990, 1, 1);
        Client client = new Client(2, "Pasch", "Mueller", "<EMAIL>", "<PASSWORD>", birthDate);
        String expected = "Client{idClient=2, name='Pasch', surname='Mueller', email='<EMAIL>', password='<PASSWORD>', birthDate=1990-01-01}";  
        assertEquals(expected, client.toString());
    }
}