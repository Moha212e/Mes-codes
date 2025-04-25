package org.example.model.dao;

import org.example.model.entity.Car;
import org.example.model.entity.Client;
import org.example.model.entity.Contrat;
import org.example.model.entity.Reservation;
import org.example.model.entity.StatutContrat;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Tests unitaires pour la classe DAOLocation.
 * Ces tests vérifient les opérations CRUD (Create, Read, Update, Delete) sur les entités.
 */
public class DAOLocationTest {
    
    private DAOLocation dao;
    private static final String TEST_DATA_DIR = "data_test";
    
    /**
     * Configuration avant chaque test.
     * Crée une instance de DAOLocation avec un répertoire de données de test.
     */
    @Before
    public void setUp() {
        // Initialiser le DAO
        dao = new DAOLocation();
        
        // Vider les collections existantes pour isoler les tests
        dao.getAllCars().clear();
        dao.getAllClients().clear();
        dao.getAllContracts().clear();
        dao.getAllReservations().clear();
        
        // Utiliser un répertoire de test pour ne pas interférer avec les données réelles
        System.setProperty("data.dir", TEST_DATA_DIR);
        
        // Créer le répertoire de test s'il n'existe pas
        File testDir = new File(TEST_DATA_DIR);
        if (!testDir.exists()) {
            testDir.mkdirs();
        }
    }
    
    /**
     * Nettoyage après chaque test.
     * Supprime les fichiers de données de test.
     */
    @After
    public void tearDown() {
        // Supprimer les fichiers de test
        deleteDirectory(new File(TEST_DATA_DIR));
    }
    
    /**
     * Méthode utilitaire pour supprimer un répertoire et son contenu.
     */
    private boolean deleteDirectory(File directoryToBeDeleted) {
        File[] allContents = directoryToBeDeleted.listFiles();
        if (allContents != null) {
            for (File file : allContents) {
                deleteDirectory(file);
            }
        }
        return directoryToBeDeleted.delete();
    }
    
    // ========== TESTS POUR LES VOITURES ==========
    
    /**
     * Test de l'ajout d'une voiture.
     */
    @Test
    public void testAddCar() {
        // Créer une voiture de test
        Car car = new Car();
        car.setBrand("Toyota");
        car.setModel("Corolla");
        car.setYear(2020);
        car.setFuelType("Diesel");
        car.setAvailable(true);
        car.setPriceday(50.0f);
        
        // Ajouter la voiture
        int result = dao.addCar(car);
        
        // Vérifier que l'ajout a réussi
        assertEquals(1, result);
        assertNotNull(car.getIdCar());
        
        // Vérifier que la voiture peut être récupérée
        Car retrievedCar = dao.getCarById(car.getIdCar());
        assertNotNull(retrievedCar);
        assertEquals(car.getBrand(), retrievedCar.getBrand());
        assertEquals(car.getModel(), retrievedCar.getModel());
        assertEquals(car.getYear(), retrievedCar.getYear());
        assertEquals(car.getFuelType(), retrievedCar.getFuelType());
        assertEquals(car.isAvailable(), retrievedCar.isAvailable());
        assertEquals(car.getPriceday(), retrievedCar.getPriceday(), 0.01);
    }
    
    /**
     * Test de la mise à jour d'une voiture.
     */
    @Test
    public void testUpdateCar() {
        // Créer et ajouter une voiture
        Car car = new Car();
        car.setBrand("Toyota");
        car.setModel("Corolla");
        car.setYear(2020);
        car.setFuelType("Essence");
        car.setAvailable(true);
        car.setPriceday(50.0f);
        
        dao.addCar(car);
        
        // Modifier la voiture
        car.setBrand("Honda");
        car.setModel("Civic");
        car.setYear(2021);
        car.setFuelType("Diesel");
        car.setAvailable(false);
        car.setPriceday(60.0f);
        
        // Mettre à jour la voiture
        dao.updateCar(car);
        
        // Vérifier que les modifications ont été enregistrées
        Car updatedCar = dao.getCarById(car.getIdCar());
        assertNotNull(updatedCar);
        assertEquals("Honda", updatedCar.getBrand());
        assertEquals("Civic", updatedCar.getModel());
        assertEquals(2021, updatedCar.getYear());
        assertEquals("Diesel", updatedCar.getFuelType());
        assertFalse(updatedCar.isAvailable());
        assertEquals(60.0f, updatedCar.getPriceday(), 0.01);
    }
    
    /**
     * Test de la suppression d'une voiture.
     */
    @Test
    public void testDeleteCar() {
        // Créer et ajouter une voiture
        Car car = new Car();
        car.setBrand("Toyota");
        car.setModel("Corolla");
        car.setYear(2020);
        
        dao.addCar(car);
        
        // Vérifier que la voiture existe
        assertNotNull(dao.getCarById(car.getIdCar()));
        
        // Supprimer la voiture
        boolean result = dao.deleteCar(car);
        
        // Vérifier que la suppression a réussi
        assertTrue(result);
        
        // Vérifier que la voiture n'existe plus
        List<Car> allCars = dao.getAllCars();
        boolean found = false;
        for (Car c : allCars) {
            if (c.getIdCar().equals(car.getIdCar())) {
                found = true;
                break;
            }
        }
        assertFalse(found);
    }
    
    /**
     * Test de la récupération de toutes les voitures.
     */
    @Test
    public void testGetAllCars() {
        // S'assurer que la collection est vide au départ
        List<Car> initialCars = dao.getAllCars();
        initialCars.clear();
        
        // Créer et ajouter plusieurs voitures
        Car car1 = new Car();
        car1.setBrand("Toyota");
        car1.setModel("Corolla");
        dao.addCar(car1);
        
        Car car2 = new Car();
        car2.setBrand("Honda");
        car2.setModel("Civic");
        dao.addCar(car2);
        
        Car car3 = new Car();
        car3.setBrand("Ford");
        car3.setModel("Focus");
        dao.addCar(car3);
        
        // Récupérer toutes les voitures
        List<Car> allCars = dao.getAllCars();
        
        // Vérifier que les voitures ajoutées sont présentes
        boolean foundCar1 = false, foundCar2 = false, foundCar3 = false;
        for (Car car : allCars) {
            if (car.getIdCar().equals(car1.getIdCar())) foundCar1 = true;
            if (car.getIdCar().equals(car2.getIdCar())) foundCar2 = true;
            if (car.getIdCar().equals(car3.getIdCar())) foundCar3 = true;
        }
        
        assertTrue(foundCar1);
        assertTrue(foundCar2);
        assertTrue(foundCar3);
        
        // Vérifier qu'au moins les 3 voitures ajoutées sont présentes
        assertTrue(allCars.size() >= 3);
    }
    
    // ========== TESTS POUR LES CLIENTS ==========
    
    /**
     * Test de l'ajout d'un client.
     */
    @Test
    public void testAddClient() {
        // Créer un client de test
        Client client = new Client();
        client.setName("Dupont");
        client.setSurname("Jean");
        client.setEmail("jean.dupont@example.com");
        client.setLicenseNumber("12345678");
        client.setPhoneNumber("0612345678");
        
        // Ajouter le client
        int clientId = dao.addClient(client);
        
        // Vérifier que l'ajout a réussi
        assertTrue(clientId > 0);
        assertEquals(clientId, client.getIdClient());
        
        // Vérifier que le client peut être récupéré
        List<Client> allClients = dao.getAllClients();
        boolean found = false;
        for (Client c : allClients) {
            if (c.getIdClient() == clientId) {
                found = true;
                assertEquals("Dupont", c.getName());
                assertEquals("Jean", c.getSurname());
                assertEquals("jean.dupont@example.com", c.getEmail());
                assertEquals("12345678", c.getLicenseNumber());
                assertEquals("0612345678", c.getPhoneNumber());
                break;
            }
        }
        assertTrue(found);
    }
    
    /**
     * Test de la mise à jour d'un client.
     */
    @Test
    public void testUpdateClient() {
        // Créer et ajouter un client
        Client client = new Client();
        client.setName("Dupont");
        client.setSurname("Jean");
        client.setEmail("jean.dupont@example.com");
        
        dao.addClient(client);
        
        // Modifier le client
        client.setName("Martin");
        client.setSurname("Pierre");
        client.setEmail("pierre.martin@example.com");
        
        // Mettre à jour le client
        dao.updateClient(client);
        
        // Vérifier que les modifications ont été enregistrées
        List<Client> allClients = dao.getAllClients();
        boolean found = false;
        for (Client c : allClients) {
            if (c.getIdClient() == client.getIdClient()) {
                found = true;
                assertEquals("Martin", c.getName());
                assertEquals("Pierre", c.getSurname());
                assertEquals("pierre.martin@example.com", c.getEmail());
                break;
            }
        }
        assertTrue(found);
    }
    
    /**
     * Test de la suppression d'un client.
     */
    @Test
    public void testDeleteClient() {
        // Créer et ajouter un client
        Client client = new Client();
        client.setName("Dupont");
        client.setSurname("Jean");
        
        dao.addClient(client);
        
        // Vérifier que le client existe
        List<Client> clientsBeforeDelete = dao.getAllClients();
        boolean foundBeforeDelete = false;
        for (Client c : clientsBeforeDelete) {
            if (c.getIdClient() == client.getIdClient()) {
                foundBeforeDelete = true;
                break;
            }
        }
        assertTrue(foundBeforeDelete);
        
        // Supprimer le client
        boolean result = dao.deleteClient(client);
        
        // Vérifier que la suppression a réussi
        assertTrue(result);
        
        // Vérifier que le client n'existe plus
        List<Client> clientsAfterDelete = dao.getAllClients();
        boolean foundAfterDelete = false;
        for (Client c : clientsAfterDelete) {
            if (c.getIdClient() == client.getIdClient()) {
                foundAfterDelete = true;
                break;
            }
        }
        assertFalse(foundAfterDelete);
    }
    
    // ========== TESTS POUR LES RÉSERVATIONS ==========
    
    /**
     * Test de l'ajout d'une réservation.
     */
    @Test
    public void testAddReservation() {
        // Créer une voiture et un client pour la réservation
        Car car = new Car();
        car.setBrand("Toyota");
        car.setModel("Corolla");
        dao.addCar(car);
        
        Client client = new Client();
        client.setName("Dupont");
        client.setSurname("Jean");
        dao.addClient(client);
        
        // Créer une réservation
        Reservation reservation = new Reservation();
        reservation.setCar(car);
        reservation.setClient(client);
        reservation.setStartDate(LocalDate.now());
        reservation.setEndDate(LocalDate.now().plusDays(1)); // +1 jour
        reservation.setPrice(100.0f);
        
        // Ajouter la réservation
        int reservationId = dao.addReservation(reservation);
        
        // Vérifier que l'ajout a réussi
        assertTrue(reservationId > 0);
        assertEquals(reservationId, reservation.getIdReservation());
        
        // Vérifier que la réservation peut être récupérée
        Reservation retrievedReservation = dao.getReservationById(reservationId);
        assertNotNull(retrievedReservation);
        assertEquals(car.getIdCar(), retrievedReservation.getCarId());
        assertEquals(client.getIdClient(), retrievedReservation.getClientId());
        assertEquals(100.0f, retrievedReservation.getPrice(), 0.01);
    }
    
    /**
     * Test de la mise à jour d'une réservation.
     */
    @Test
    public void testUpdateReservation() {
        // Créer une voiture et un client pour la réservation
        Car car = new Car();
        car.setBrand("Toyota");
        car.setModel("Corolla");
        dao.addCar(car);
        
        Client client = new Client();
        client.setName("Dupont");
        client.setSurname("Jean");
        dao.addClient(client);
        
        // Créer et ajouter une réservation
        Reservation reservation = new Reservation();
        reservation.setCar(car);
        reservation.setClient(client);
        reservation.setStartDate(LocalDate.now());
        reservation.setEndDate(LocalDate.now().plusDays(1)); // +1 jour
        reservation.setPrice(100.0f);
        
        dao.addReservation(reservation);
        
        // Modifier la réservation
        reservation.setPrice(150.0f);
        reservation.setNotes("Modification de test");
        
        // Mettre à jour la réservation
        dao.updateReservation(reservation);
        
        // Vérifier que les modifications ont été enregistrées
        Reservation updatedReservation = dao.getReservationById(reservation.getIdReservation());
        assertNotNull(updatedReservation);
        assertEquals(150.0f, updatedReservation.getPrice(), 0.01);
        assertEquals("Modification de test", updatedReservation.getNotes());
    }
    
    /**
     * Test de la suppression d'une réservation.
     */
    @Test
    public void testDeleteReservation() {
        // Créer une voiture et un client pour la réservation
        Car car = new Car();
        car.setBrand("Toyota");
        car.setModel("Corolla");
        dao.addCar(car);
        
        Client client = new Client();
        client.setName("Dupont");
        client.setSurname("Jean");
        dao.addClient(client);
        
        // Créer et ajouter une réservation
        Reservation reservation = new Reservation();
        reservation.setCar(car);
        reservation.setClient(client);
        reservation.setStartDate(LocalDate.now());
        reservation.setEndDate(LocalDate.now().plusDays(1)); // +1 jour
        
        dao.addReservation(reservation);
        
        // Vérifier que la réservation existe
        assertNotNull(dao.getReservationById(reservation.getIdReservation()));
        
        // Supprimer la réservation
        boolean result = dao.deleteReservation(reservation);
        
        // Vérifier que la suppression a réussi
        assertTrue(result);
        
        // Vérifier que la réservation n'existe plus
        List<Reservation> allReservations = dao.getAllReservations();
        boolean found = false;
        for (Reservation r : allReservations) {
            if (r.getIdReservation() == reservation.getIdReservation()) {
                found = true;
                break;
            }
        }
        assertFalse(found);
    }
    
    // ========== TESTS POUR LES CONTRATS ==========
    
    /**
     * Test de l'ajout d'un contrat.
     */
    @Test
    public void testAddContract() {
        // Créer une réservation pour le contrat
        Car car = new Car();
        car.setBrand("Toyota");
        car.setModel("Corolla");
        dao.addCar(car);
        
        Client client = new Client();
        client.setName("Dupont");
        client.setSurname("Jean");
        dao.addClient(client);
        
        Reservation reservation = new Reservation();
        reservation.setCar(car);
        reservation.setClient(client);
        reservation.setStartDate(LocalDate.now());
        reservation.setEndDate(LocalDate.now().plusDays(1)); // +1 jour
        dao.addReservation(reservation);
        
        // Créer un contrat
        Contrat contrat = new Contrat();
        contrat.setReservation(reservation);
        contrat.setStatutContrat(StatutContrat.EN_ATTENTE);
        
        // Ajouter le contrat
        int result = dao.addContract(contrat);
        
        // Vérifier que l'ajout a réussi
        assertEquals(1, result);
        assertNotNull(contrat.getIdContrat());
        
        // Vérifier que le contrat peut être récupéré
        List<Contrat> allContracts = dao.getAllContracts();
        boolean found = false;
        for (Contrat c : allContracts) {
            if (c.getIdContrat().equals(contrat.getIdContrat())) {
                found = true;
                assertEquals(StatutContrat.EN_ATTENTE, c.getStatutContrat());
                break;
            }
        }
        assertTrue(found);
    }
    
    /**
     * Test de la mise à jour d'un contrat.
     */
    @Test
    public void testUpdateContract() {
        // Créer une réservation pour le contrat
        Car car = new Car();
        car.setBrand("Toyota");
        car.setModel("Corolla");
        dao.addCar(car);
        
        Client client = new Client();
        client.setName("Dupont");
        client.setSurname("Jean");
        dao.addClient(client);
        
        Reservation reservation = new Reservation();
        reservation.setCar(car);
        reservation.setClient(client);
        reservation.setStartDate(LocalDate.now());
        reservation.setEndDate(LocalDate.now().plusDays(1)); // +1 jour
        dao.addReservation(reservation);
        
        // Créer et ajouter un contrat
        Contrat contrat = new Contrat();
        contrat.setReservation(reservation);
        contrat.setStatutContrat(StatutContrat.EN_ATTENTE);
        
        dao.addContract(contrat);
        
        // Modifier le contrat
        contrat.setStatutContrat(StatutContrat.SIGNE);
        contrat.setEstSigne(true);
        
        // Mettre à jour le contrat
        dao.updateContract(contrat);
        
        // Vérifier que les modifications ont été enregistrées
        List<Contrat> allContracts = dao.getAllContracts();
        boolean found = false;
        for (Contrat c : allContracts) {
            if (c.getIdContrat().equals(contrat.getIdContrat())) {
                found = true;
                assertEquals(StatutContrat.SIGNE, c.getStatutContrat());
                assertTrue(c.isEstSigne());
                break;
            }
        }
        assertTrue(found);
    }
    
    /**
     * Test de la suppression d'un contrat.
     */
    @Test
    public void testDeleteContract() {
        // Créer une réservation pour le contrat
        Car car = new Car();
        car.setBrand("Toyota");
        car.setModel("Corolla");
        dao.addCar(car);
        
        Client client = new Client();
        client.setName("Dupont");
        client.setSurname("Jean");
        dao.addClient(client);
        
        Reservation reservation = new Reservation();
        reservation.setCar(car);
        reservation.setClient(client);
        reservation.setStartDate(LocalDate.now());
        reservation.setEndDate(LocalDate.now().plusDays(1)); // +1 jour
        dao.addReservation(reservation);
        
        // Créer et ajouter un contrat
        Contrat contrat = new Contrat();
        contrat.setReservation(reservation);
        contrat.setStatutContrat(StatutContrat.EN_ATTENTE);
        
        dao.addContract(contrat);
        
        // Vérifier que le contrat existe
        List<Contrat> contractsBeforeDelete = dao.getAllContracts();
        boolean foundBeforeDelete = false;
        for (Contrat c : contractsBeforeDelete) {
            if (c.getIdContrat().equals(contrat.getIdContrat())) {
                foundBeforeDelete = true;
                break;
            }
        }
        assertTrue(foundBeforeDelete);
        
        // Supprimer le contrat
        boolean result = dao.deleteContract(contrat);
        
        // Vérifier que la suppression a réussi
        assertTrue(result);
        
        // Vérifier que le contrat n'existe plus
        List<Contrat> contractsAfterDelete = dao.getAllContracts();
        boolean foundAfterDelete = false;
        for (Contrat c : contractsAfterDelete) {
            if (c.getIdContrat().equals(contrat.getIdContrat())) {
                foundAfterDelete = true;
                break;
            }
        }
        assertFalse(foundAfterDelete);
    }
}
