package org.example.model.dao;

import org.example.model.DataAccessLayer;
import org.example.model.entity.Car;
import org.example.model.entity.Client;
import org.example.model.entity.Contrat;
import org.example.model.entity.Reservation;
import org.example.model.entity.StatutContrat;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Tests unitaires pour les opérations CRUD du DAO
 */
public class DAOLocationTest {

    private DataAccessLayer dao;

    @Before
    public void setUp() {
        // Initialiser le DAO avec une implémentation en mémoire
        dao = new MockDAOLocation();
    }

    // ======== TESTS POUR LES VOITURES (CAR) ========

    @Test
    public void testAddCar() {
        Car car = createTestCar();
        int result = dao.addCar(car);
        assertEquals(1, result);

        List<Car> cars = dao.getAllCars();
        assertEquals(1, cars.size());
        assertEquals(car.getBrand(), cars.get(0).getBrand());
    }

    @Test
    public void testGetCarById() {
        Car car = createTestCar();
        dao.addCar(car);

        Car retrievedCar = dao.getCarById(car.getIdCar());
        assertNotNull(retrievedCar);
        assertEquals(car.getBrand(), retrievedCar.getBrand());
    }

    @Test
    public void testUpdateCar() {
        Car car = createTestCar();
        dao.addCar(car);

        car.setBrand("Marque Modifiée");
        dao.updateCar(car);

        Car updatedCar = dao.getCarById(car.getIdCar());
        assertEquals("Marque Modifiée", updatedCar.getBrand());
    }

    @Test
    public void testDeleteCar() {
        Car car = createTestCar();
        dao.addCar(car);

        boolean result = dao.deleteCar(car);
        assertTrue(result);

        List<Car> cars = dao.getAllCars();
        assertEquals(0, cars.size());
    }

    // ======== TESTS POUR LES CLIENTS ========

    @Test
    public void testAddClient() {
        Client client = createTestClient();
        int clientId = dao.addClient(client);
        assertTrue(clientId > 0);

        List<Client> clients = dao.getAllClients();
        assertEquals(1, clients.size());
        assertEquals(client.getName(), clients.get(0).getName());
    }

    @Test
    public void testUpdateClient() {
        Client client = createTestClient();
        dao.addClient(client);

        client.setName("Nom Modifié");
        dao.updateClient(client);

        List<Client> clients = dao.getAllClients();
        assertEquals(1, clients.size());
        assertEquals("Nom Modifié", clients.get(0).getName());
    }

    @Test
    public void testDeleteClient() {
        Client client = createTestClient();
        dao.addClient(client);

        boolean result = dao.deleteClient(client);
        assertTrue(result);

        List<Client> clients = dao.getAllClients();
        assertEquals(0, clients.size());
    }

    // ======== TESTS POUR LES CONTRATS ========

    @Test
    public void testAddContract() {
        Contrat contrat = createTestContrat();
        int result = dao.addContract(contrat);
        assertEquals(1, result);

        List<Contrat> contrats = dao.getAllContracts();
        assertEquals(1, contrats.size());
    }

    @Test
    public void testUpdateContract() {
        Contrat contrat = createTestContrat();
        dao.addContract(contrat);

        contrat.setStatutContrat(StatutContrat.TERMINE);
        dao.updateContract(contrat);

        List<Contrat> contrats = dao.getAllContracts();
        assertEquals(1, contrats.size());
        assertEquals(StatutContrat.TERMINE, contrats.get(0).getStatutContrat());
    }

    @Test
    public void testDeleteContract() {
        Contrat contrat = createTestContrat();
        dao.addContract(contrat);

        boolean result = dao.deleteContract(contrat);
        assertTrue(result);

        List<Contrat> contrats = dao.getAllContracts();
        assertEquals(0, contrats.size());
    }

    // ======== TESTS POUR LES RÉSERVATIONS ========

    @Test
    public void testAddReservation() {
        Reservation reservation = createTestReservation();
        int reservationId = dao.addReservation(reservation);
        assertTrue(reservationId > 0);

        List<Reservation> reservations = dao.getAllReservations();
        assertEquals(1, reservations.size());
        assertEquals(reservationId, reservations.get(0).getIdReservation());
    }

    @Test
    public void testUpdateReservation() {
        Reservation reservation = createTestReservation();
        dao.addReservation(reservation);

        Car car = createTestCar();
        dao.addCar(car);
        Client client = createTestClient();
        dao.addClient(client);

        reservation.setCar(car);
        reservation.setClient(client);
        dao.updateReservation(reservation);

        List<Reservation> reservations = dao.getAllReservations();
        assertEquals(1, reservations.size());
        assertEquals(car.getIdCar(), reservations.get(0).getCarId());
    }

    @Test
    public void testDeleteReservation() {
        Reservation reservation = createTestReservation();
        dao.addReservation(reservation);

        boolean result = dao.deleteReservation(reservation);
        assertTrue(result);

        List<Reservation> reservations = dao.getAllReservations();
        assertEquals(0, reservations.size());
    }

    // ======== MÉTHODES UTILITAIRES ========

    private Car createTestCar() {
        Car car = new Car();
        car.setBrand("Marque Test");
        car.setModel("Modèle Test");
        car.setYear(2023);
        car.setPriceday(50.0f);
        car.setAvailable(true);
        car.setFuelType("Essence");
        car.setTransmission("Automatique");
        car.setSeats(5);
        car.setMileage(10000);
        car.setImage("test_image.jpg");
        return car;
    }

    private Client createTestClient() {
        Client client = new Client();
        client.setName("Nom Test");
        client.setSurname("Prénom Test");
        client.setBirthDate(LocalDate.of(1990, 1, 1));
        client.setLicenseNumber("LICENCE123");
        client.setAddress("123 Rue de Test");
        client.setPhoneNumber("0123456789");
        return client;
    }

    private Contrat createTestContrat() {
        Contrat contrat = new Contrat();
        contrat.setCaution(500.0);
        contrat.setTypeAssurance("Tous risques");
        contrat.setEstSigne(true);
        contrat.setStatutContrat(StatutContrat.EN_ATTENTE);
        contrat.setPrixTotal(350.0);
        return contrat;
    }

    private Reservation createTestReservation() {
        Reservation reservation = new Reservation();
        reservation.setStartDate(LocalDate.now());
        reservation.setEndDate(LocalDate.now().plusDays(7));
        return reservation;
    }
}

/**
 * Implémentation en mémoire du DAO pour les tests
 */
class MockDAOLocation implements DataAccessLayer {
    private Map<Integer, Reservation> reservations = new HashMap<>();
    private Map<String, Car> cars = new HashMap<>();
    private Map<String, Contrat> contracts = new HashMap<>();
    private Map<Integer, Client> clients = new HashMap<>();

    private int nextReservationId = 1;
    private int nextCarId = 1;
    private int nextContractId = 1;
    private int nextClientId = 1;

    @Override
    public int addReservation(Reservation reservation) {
        if (reservation.getIdReservation() == 0) {
            reservation.setIdReservation(nextReservationId++);
        }
        reservations.put(reservation.getIdReservation(), reservation);
        return reservation.getIdReservation();
    }

    @Override
    public int addCar(Car car) {
        if (car.getIdCar() == null || car.getIdCar().isEmpty()) {
            car.setIdCar("TEST-CAR-" + nextCarId++);
        }
        cars.put(car.getIdCar(), car);
        return 1;
    }

    @Override
    public int addContract(Contrat contrat) {
        if (contrat.getIdContrat() == null || contrat.getIdContrat().isEmpty()) {
            contrat.setIdContrat("TEST-CONTRACT-" + nextContractId++);
        }
        contracts.put(contrat.getIdContrat(), contrat);
        return 1;
    }

    @Override
    public int addClient(Client client) {
        if (client.getIdClient() == 0) {
            client.setIdClient(nextClientId++);
        }
        clients.put(client.getIdClient(), client);
        return client.getIdClient();
    }

    @Override
    public boolean deleteReservation(Reservation reservation) {
        return reservations.remove(reservation.getIdReservation()) != null;
    }

    @Override
    public boolean deleteCar(Car car) {
        return cars.remove(car.getIdCar()) != null;
    }

    @Override
    public boolean deleteContract(Contrat contrat) {
        return contracts.remove(contrat.getIdContrat()) != null;
    }

    @Override
    public boolean deleteClient(Client client) {
        return clients.remove(client.getIdClient()) != null;
    }

    @Override
    public void updateReservation(Reservation reservation) {
        if (reservations.containsKey(reservation.getIdReservation())) {
            reservations.put(reservation.getIdReservation(), reservation);
        }
    }

    @Override
    public void updateCar(Car car) {
        if (cars.containsKey(car.getIdCar())) {
            cars.put(car.getIdCar(), car);
        }
    }

    @Override
    public void updateContract(Contrat contrat) {
        if (contracts.containsKey(contrat.getIdContrat())) {
            contracts.put(contrat.getIdContrat(), contrat);
        }
    }

    @Override
    public void updateClient(Client client) {
        if (clients.containsKey(client.getIdClient())) {
            clients.put(client.getIdClient(), client);
        }
    }

    @Override
    public List<Reservation> getAllReservations() {
        return new ArrayList<>(reservations.values());
    }

    @Override
    public List<Car> getAllCars() {
        return new ArrayList<>(cars.values());
    }

    @Override
    public List<Contrat> getAllContracts() {
        return new ArrayList<>(contracts.values());
    }

    @Override
    public List<Client> getAllClients() {
        return new ArrayList<>(clients.values());
    }

    @Override
    public Car getCarById(String idCar) {
        return cars.get(idCar);
    }

    @Override
    public void importCars(String filePath) throws IOException {
        // Non implémenté pour les tests
    }

    @Override
    public void importClients(String filePath) throws IOException {
        // Non implémenté pour les tests
    }

    @Override
    public void importContracts(String filePath) throws IOException {
        // Non implémenté pour les tests
    }

    @Override
    public void importReservations(String filePath) throws IOException {
        // Non implémenté pour les tests
    }

    @Override
    public void exportCars(String filePath) throws IOException {
        // Non implémenté pour les tests
    }

    @Override
    public void exportClients(String filePath) throws IOException {
        // Non implémenté pour les tests
    }

    @Override
    public void exportContracts(String filePath) throws IOException {
        // Non implémenté pour les tests
    }

    @Override
    public void exportReservations(String filePath) throws IOException {
        // Non implémenté pour les tests
    }

    @Override
    public void loadData() {
        // Non implémenté pour les tests
    }

    @Override
    public void updateContrat(Contrat contrat) {
        updateContract(contrat);
    }
}
