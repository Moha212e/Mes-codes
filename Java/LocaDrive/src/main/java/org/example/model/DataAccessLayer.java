package org.example.model;

import org.example.model.entity.*;
import org.example.model.event.Observable;

import java.util.ArrayList;
import java.util.List;

/**
 * Interface définissant les opérations d'accès aux données
 * Implémente les principes Interface Segregation et Open/Closed (I et O de SOLID)
 */
public interface DataAccessLayer extends Observable {

    /**
     * Ajoute une nouvelle réservation
     * @param reservation Réservation à ajouter
     * @return ID de la réservation ajoutée
     */
    int addReservation(Reservation reservation);
    
    /**
     * Ajoute un nouveau client
     * @param client Client à ajouter
     * @return ID du client ajouté
     */
    int addClient(Client client);
    
    /**
     * Ajoute une nouvelle voiture
     * @param car Voiture à ajouter
     * @return 1 si l'opération a réussi, autre valeur sinon
     */
    int addCar(Car car);
    
    /**
     * Ajoute un nouveau contrat
     * @param contrat Contrat à ajouter
     * @return ID du contrat ajouté
     */
    int addContrat(Contrat contrat);
    
    /**
     * Récupère toutes les voitures
     * @return Liste de toutes les voitures
     */
    ArrayList<Car> getAllCars();
    
    /**
     * Récupère tous les clients
     * @return Liste de tous les clients
     */
    ArrayList<Client> getAllClients();
    
    /**
     * Récupère toutes les réservations
     * @return Liste de toutes les réservations
     */
    ArrayList<Reservation> getAllReservations();
    
    /**
     * Récupère tous les contrats
     * @return Liste de tous les contrats
     */
    ArrayList<Contrat> getAllContrats();
    
    /**
     * Met à jour une voiture existante
     * @param car Voiture à mettre à jour
     * @return true si la mise à jour a réussi, false sinon
     */
    boolean updateCar(Car car);
    
    /**
     * Met à jour un client existant
     * @param client Client à mettre à jour
     * @return true si la mise à jour a réussi, false sinon
     */
    boolean updateClient(Client client);
    
    /**
     * Met à jour une réservation existante
     * @param reservation Réservation à mettre à jour
     * @return true si la mise à jour a réussi, false sinon
     */
    boolean updateReservation(Reservation reservation);
    
    /**
     * Met à jour un contrat existant
     * @param contrat Contrat à mettre à jour
     * @return true si la mise à jour a réussi, false sinon
     */
    boolean updateContrat(Contrat contrat);
    
    /**
     * Supprime une voiture
     * @param idCar ID de la voiture à supprimer
     * @return true si la suppression a réussi, false sinon
     */
    boolean deleteCar(String idCar);
    
    /**
     * Supprime un client
     * @param idClient ID du client à supprimer
     * @return true si la suppression a réussi, false sinon
     */
    boolean deleteClient(int idClient);
    
    /**
     * Supprime une réservation
     * @param idReservation ID de la réservation à supprimer
     * @return true si la suppression a réussi, false sinon
     */
    boolean deleteReservation(int idReservation);
    
    /**
     * Supprime un contrat
     * @param idContrat ID du contrat à supprimer
     * @return true si la suppression a réussi, false sinon
     */
    boolean deleteContrat(String idContrat);
    
    /**
     * Recherche une voiture par son ID
     * @param idCar ID de la voiture
     * @return Voiture trouvée ou null
     */
    Car findCarById(String idCar);
    
    /**
     * Recherche un client par son ID
     * @param idClient ID du client
     * @return Client trouvé ou null
     */
    Client findClientById(int idClient);
    
    /**
     * Recherche une réservation par son ID
     * @param idReservation ID de la réservation
     * @return Réservation trouvée ou null
     */
    Reservation findReservationById(int idReservation);
    
    /**
     * Recherche un contrat par son ID
     * @param idContrat ID du contrat
     * @return Contrat trouvé ou null
     */
    Contrat findContratById(String idContrat);
    
    /**
     * Sauvegarde toutes les données
     * @return true si la sauvegarde a réussi, false sinon
     */
    boolean saveAllData();
    
    /**
     * Charge toutes les données
     * @return true si le chargement a réussi, false sinon
     */
    boolean loadAllData();
}
