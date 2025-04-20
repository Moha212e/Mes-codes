package org.example.model;

import org.example.model.entity.*;
import java.util.List;
import java.io.IOException;

public interface DataAccessLayer {
    int addReservation(Reservation reservation);
    int addCar(Car car);
    int addContract(Contrat contrat);
    int addClient(Client client);
    boolean deleteReservation(Reservation reservation);
    boolean deleteCar(Car car);
    boolean deleteContract(Contrat contrat);
    boolean deleteClient(Client client);
    void updateReservation(Reservation reservation);
    void updateCar(Car car);
    void updateContract(Contrat contrat);
    void updateClient(Client client);
    
    // Méthodes pour récupérer toutes les entités
    List<Reservation> getAllReservations();
    List<Car> getAllCars();
    List<Contrat> getAllContracts();
    List<Client> getAllClients();
    
    // Méthodes pour récupérer des entités par ID
    Car getCarById(String idCar);

    void getListCar();

    void saveReservations(List<Reservation> reservations);
    
    // Méthodes d'importation et d'exportation
    void importCars(String filePath) throws IOException;
    
    void importClients(String filePath) throws IOException;
    
    void importContracts(String filePath) throws IOException;
    
    void importReservations(String filePath) throws IOException;
    
    void exportCars(String filePath) throws IOException;
    
    void exportClients(String filePath) throws IOException;
    
    void exportContracts(String filePath) throws IOException;
    
    void exportReservations(String filePath) throws IOException;
}
