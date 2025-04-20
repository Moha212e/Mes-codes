package org.example.controller;

import org.example.model.dao.DAOLocation;
import org.example.model.entity.*;
import org.example.model.event.ModelEvent;
import org.example.model.event.ModelListener;
import org.example.model.DataAccessLayer;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Contrôleur principal de l'application
 * Fait le lien entre l'interface utilisateur et la couche d'accès aux données
 * Implémente le principe de Single Responsibility (S de SOLID)
 */
public class Controller implements ModelListener {
    private final DataAccessLayer daoLocation;
    private final List<ControllerListener> listeners = new ArrayList<>();
    
    /**
     * Constructeur initialisant le DAO
     */
    public Controller() {
        this.daoLocation = new DAOLocation();
        // S'abonner aux événements du modèle
        this.daoLocation.addModelListener(this);
    }
    
    /**
     * Ajoute un écouteur d'événements du contrôleur
     * @param listener Écouteur à ajouter
     */
    public void addControllerListener(ControllerListener listener) {
        if (listener != null && !listeners.contains(listener)) {
            listeners.add(listener);
        }
    }
    
    /**
     * Supprime un écouteur d'événements du contrôleur
     * @param listener Écouteur à supprimer
     */
    public void removeControllerListener(ControllerListener listener) {
        listeners.remove(listener);
    }
    
    /**
     * Notifie tous les écouteurs d'un événement
     * @param event Événement à notifier
     */
    protected void notifyListeners(ControllerEvent event) {
        // Créer une copie de la liste pour éviter les problèmes de concurrence
        List<ControllerListener> listenersCopy = new ArrayList<>(listeners);
        
        // Notifier tous les écouteurs
        for (ControllerListener listener : listenersCopy) {
            listener.onControllerEvent(event);
        }
    }
    
    /**
     * Méthode appelée lorsqu'un événement du modèle se produit
     * Implémente le pattern Observer
     * @param event Événement du modèle
     */
    @Override
    public void onModelEvent(ModelEvent event) {
        // Convertir l'événement du modèle en événement du contrôleur
        ControllerEvent.EventType controllerEventType;
        
        switch (event.getEventType()) {
            case ENTITY_ADDED:
                controllerEventType = ControllerEvent.EventType.DATA_ADDED;
                break;
            case ENTITY_MODIFIED:
                controllerEventType = ControllerEvent.EventType.DATA_MODIFIED;
                break;
            case ENTITY_DELETED:
                controllerEventType = ControllerEvent.EventType.DATA_DELETED;
                break;
            case DATA_LOADED:
                controllerEventType = ControllerEvent.EventType.DATA_LOADED;
                break;
            default:
                controllerEventType = ControllerEvent.EventType.UNKNOWN;
        }
        
        // Notifier les écouteurs du contrôleur
        notifyListeners(new ControllerEvent(this, controllerEventType));
    }
    
    /**
     * Ajoute une nouvelle voiture
     * @param car Voiture à ajouter
     * @return Immatriculation de la voiture ajoutée
     */
    public String addCar(Car car) {
        daoLocation.addCar(car);
        return car.getIdCar();
    }
    
    /**
     * Ajoute une nouvelle voiture avec les attributs de base
     * @param brand Marque de la voiture
     * @param model Modèle de la voiture
     * @param year Année de fabrication
     * @param pricePerDay Prix par jour de location
     * @return Immatriculation de la voiture ajoutée
     */
    public String addCar(String brand, String model, int year, float pricePerDay) {
        Car car = new Car();
        car.setBrand(brand);
        car.setModel(model);
        car.setYear(year);
        car.setPriceday(pricePerDay);
        
        daoLocation.addCar(car);
        return car.getIdCar();
    }
    
    /**
     * Ajoute un nouveau client
     * @param client Client à ajouter
     * @return ID du client ajouté
     */
    public int addClient(Client client) {
        return daoLocation.addClient(client);
    }
    
    /**
     * Ajoute un nouveau client avec les attributs de base
     * @param name Nom du client
     * @param surname Prénom du client
     * @param email Email du client
     * @param birthDate Date de naissance du client
     * @return ID du client ajouté
     */
    public int addClient(String name, String surname, String email, LocalDate birthDate) {
        Client client = new Client();
        client.setName(name);
        client.setSurname(surname);
        client.setEmail(email);
        client.setBirthDate(birthDate);
        
        return daoLocation.addClient(client);
    }
    
    /**
     * Ajoute une nouvelle réservation standard
     * @param carId ID de la voiture (immatriculation belge)
     * @param clientId ID du client
     * @param responsable Personne responsable
     * @param notes Notes additionnelles
     * @param price Prix total
     * @param dateDebut Date de début de la réservation
     * @param dateFin Date de fin de la réservation
     * @param assuranceBase Si l'assurance de base est incluse
     * @return ID de la réservation ajoutée
     */
    public int addReservation(String carId, int clientId, String responsable, String notes, float price,
                             LocalDate dateDebut, LocalDate dateFin, boolean assuranceBase) {
        // Création de la réservation standard
        ReservationStandard reservation = new ReservationStandard();
        reservation.setResponsable(responsable);
        reservation.setNotes(notes);
        reservation.setPrice(price);
        reservation.setDateDebut(dateDebut);
        reservation.setDateFin(dateFin);
        reservation.setAssuranceBase(assuranceBase);
        
        // Définir la date de réservation à aujourd'hui
        reservation.setDateReservation(LocalDate.now());
        
        // Définir le statut initial (EN_ATTENTE par défaut)
        reservation.setStatut(Reservation.StatutReservation.EN_ATTENTE);
        
        // Récupération de la voiture et du client
        Car car = findCarById(carId);
        Client client = findClientById(clientId);
        
        if (car != null) {
            reservation.setCar(car);
        }
        
        if (client != null) {
            reservation.setClient(client);
        }
        
        // Calcul du prix en fonction des dates et de l'assurance
        reservation.calculerPrix();
        
        return daoLocation.addReservation(reservation);
    }
    
    /**
     * Ajoute un nouveau contrat
     * @param carId ID de la voiture (immatriculation belge)
     * @param clientId ID du client
     * @param dateDebut Date de début
     * @param dateFin Date de fin
     * @param caution Montant de la caution
     * @param typeAssurance Type d'assurance
     * @param options Options supplémentaires
     * @param estSigne Si le contrat est signé
     * @return ID du contrat ajouté
     */
    public int addContrat(String carId, int clientId, LocalDate dateDebut, LocalDate dateFin, 
                         double caution, String typeAssurance, 
                         List<String> options, boolean estSigne) {
        // Création du contrat
        Contrat contrat = new Contrat();
        contrat.setDateDebut(dateDebut);
        contrat.setDateFin(dateFin);
        contrat.setCaution(caution);
        contrat.setTypeAssurance(typeAssurance);
        contrat.setOptions(options);
        contrat.setEstSigne(estSigne);
        contrat.setStatutContrat(Contrat.StatutContrat.EN_ATTENTE);
        
        // Récupération de la voiture et du client
        Car car = findCarById(carId);
        Client client = findClientById(clientId);
        
        if (car != null) {
            contrat.setCar(car);
        }
        
        if (client != null) {
            contrat.setClient(client);
        }
        
        // Calculer le prix
        contrat.calculerPrix();
        
        return daoLocation.addContrat(contrat);
    }
    
    /**
     * Ajoute un nouveau contrat à partir d'une réservation existante
     * @param reservationId ID de la réservation existante
     * @param caution Montant de la caution
     * @param typeAssurance Type d'assurance
     * @param options Options supplémentaires
     * @param estSigne Si le contrat est signé
     * @return ID du contrat ajouté
     */
    public int addContratFromReservation(int reservationId, double caution, String typeAssurance, 
                                        List<String> options, boolean estSigne) {
        // Récupérer la réservation existante
        Reservation existingReservation = daoLocation.findReservationById(reservationId);
        
        if (existingReservation == null) {
            throw new IllegalArgumentException("Réservation introuvable avec l'ID: " + reservationId);
        }
        
        // Créer un nouveau contrat à partir de la réservation
        Contrat contrat = new Contrat();
        
        // Copier les propriétés de la réservation
        contrat.setIdReservation(existingReservation.getIdReservation());
        contrat.setResponsable(existingReservation.getResponsable());
        contrat.setNotes(existingReservation.getNotes());
        contrat.setDateDebut(existingReservation.getDateDebut());
        contrat.setDateFin(existingReservation.getDateFin());
        contrat.setCar(existingReservation.getCar());
        contrat.setClient(existingReservation.getClient());
        
        // Définir les propriétés spécifiques au contrat
        contrat.setCaution(caution);
        contrat.setTypeAssurance(typeAssurance);
        contrat.setOptions(options);
        contrat.setEstSigne(estSigne);
        contrat.setStatutContrat(Contrat.StatutContrat.EN_ATTENTE);
        
        // Calculer le prix
        contrat.calculerPrix();
        
        return daoLocation.addContrat(contrat);
    }
    
    /**
     * Récupère toutes les voitures
     * @return Liste de toutes les voitures
     */
    public ArrayList<Car> getAllCars() {
        return daoLocation.getAllCars();
    }
    
    /**
     * Récupère tous les clients
     * @return Liste de tous les clients
     */
    public ArrayList<Client> getAllClients() {
        return daoLocation.getAllClients();
    }
    
    /**
     * Récupère toutes les réservations
     * @return Liste de toutes les réservations
     */
    public ArrayList<Reservation> getAllReservations() {
        return daoLocation.getAllReservations();
    }
    
    /**
     * Récupère tous les contrats
     * @return Liste de tous les contrats
     */
    public ArrayList<Contrat> getAllContrats() {
        return daoLocation.getAllContrats();
    }
    
    /**
     * Met à jour une voiture existante
     * @param car Voiture à mettre à jour
     * @return true si la mise à jour a réussi, false sinon
     */
    public boolean updateCar(Car car) {
        return daoLocation.updateCar(car);
    }
    
    /**
     * Met à jour un client existant
     * @param client Client à mettre à jour
     * @return true si la mise à jour a réussi, false sinon
     */
    public boolean updateClient(Client client) {
        return daoLocation.updateClient(client);
    }
    
    /**
     * Met à jour une réservation existante
     * @param reservation Réservation à mettre à jour
     * @return true si la mise à jour a réussi, false sinon
     */
    public boolean updateReservation(Reservation reservation) {
        return daoLocation.updateReservation(reservation);
    }
    
    /**
     * Met à jour un contrat existant
     * @param contrat Contrat à mettre à jour
     * @return true si la mise à jour a réussi, false sinon
     */
    public boolean updateContrat(Contrat contrat) {
        return daoLocation.updateContrat(contrat);
    }
    
    /**
     * Supprime une voiture
     * @param idCar ID de la voiture à supprimer
     * @return true si la suppression a réussi, false sinon
     */
    public boolean deleteCar(String idCar) {
        return daoLocation.deleteCar(idCar);
    }
    
    /**
     * Supprime un client
     * @param idClient ID du client à supprimer
     * @return true si la suppression a réussi, false sinon
     */
    public boolean deleteClient(int idClient) {
        return daoLocation.deleteClient(idClient);
    }
    
    /**
     * Supprime une réservation
     * @param idReservation ID de la réservation à supprimer
     * @return true si la suppression a réussi, false sinon
     */
    public boolean deleteReservation(int idReservation) {
        return daoLocation.deleteReservation(idReservation);
    }
    
    /**
     * Supprime un contrat
     * @param idContrat ID du contrat à supprimer
     * @return true si la suppression a réussi, false sinon
     */
    public boolean deleteContrat(String idContrat) {
        return daoLocation.deleteContrat(idContrat);
    }
    
    /**
     * Recherche une voiture par son ID
     * @param idCar ID de la voiture
     * @return Voiture trouvée ou null
     */
    public Car findCarById(String idCar) {
        return daoLocation.findCarById(idCar);
    }
    
    /**
     * Recherche un client par son ID
     * @param idClient ID du client
     * @return Client trouvé ou null
     */
    public Client findClientById(int idClient) {
        return daoLocation.findClientById(idClient);
    }
    
    /**
     * Recherche une réservation par son ID
     * @param idReservation ID de la réservation
     * @return Réservation trouvée ou null
     */
    public Reservation findReservationById(int idReservation) {
        return daoLocation.findReservationById(idReservation);
    }
    
    /**
     * Recherche un contrat par son ID
     * @param idContrat ID du contrat
     * @return Contrat trouvé ou null
     */
    public Contrat findContratById(String idContrat) {
        return daoLocation.findContratById(idContrat);
    }
    
    /**
     * Sauvegarde toutes les données
     * @return true si la sauvegarde a réussi, false sinon
     */
    public boolean saveAllData() {
        return daoLocation.saveAllData();
    }
    
    /**
     * Charge toutes les données
     * @return true si le chargement a réussi, false sinon
     */
    public boolean loadAllData() {
        return daoLocation.loadAllData();
    }
}
