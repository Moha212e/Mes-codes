package org.example.model;

import org.example.model.entity.*;

public interface DataAccessLayer {
    int addClient(Client client);
    int addReservation(Reservation reservation);
    int addCar(Car car);
    int addContrat(Contrat contrat);
    boolean updateClient(Client client);
    boolean updateReservation(Reservation reservation);
    boolean updateCar(Car car);
    boolean updateContrat(Contrat contrat);
    boolean deleteClient(int idClient);
    boolean deleteReservation(int idReservation);
    boolean deleteCar(int idCar);
    boolean deleteContrat(int idContrat);
}
