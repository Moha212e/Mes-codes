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
        assertNull(client.getName());
        assertNull(client.getSurname());
        assertNull(client.getEmail());
        assertNull(client.getPassword());
        assertNull(client.getBirthDate());
        assertFalse(client.isDeteled());
    }

    @Test
    public void testParameterizedConstructor() {
        Client client = new Client(1, "Pasch", "Mueller", "<EMAIL>", "123456", null, false);
        assertEquals(1, client.getIdClient());
        assertEquals("Pasch", client.getName());
        assertEquals("Mueller", client.getSurname());
        assertEquals("<EMAIL>", client.getEmail());
        assertEquals("123456", client.getPassword());
        assertNull(client.getBirthDate());
        assertFalse(client.isDeteled());

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
        assertEquals("123456", client.getPassword());
    }

    @Test
    public void testToString() {
        Client client = new Client(2, "Pasch", "Mueller", "<EMAIL>", "123456", null, false);
        String expected = "Client{idClient=2, name=Pasch, surname=Mueller, email=<EMAIL>, password=<PASSWORD>, birthDate=null, isDeteled=false}";
        assertEquals(expected, client.toString());
    }



}