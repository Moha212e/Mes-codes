package org.example.model.dao;

import org.example.model.entity.*;
import org.example.model.DataAccessLayer;
import org.example.model.event.AbstractObservable;
import org.example.model.event.ModelEvent;
import org.example.model.event.ModelListener;
import org.example.model.persistence.DataPersistence;
import org.example.model.persistence.JsonPersistence;

import java.util.ArrayList;
import java.util.Random;

/**
 * Classe DAO pour gérer les opérations CRUD sur les entités de l'application
 * Implémente le principe de Single Responsibility (S de SOLID)
 */
public class DAOLocation extends AbstractObservable implements DataAccessLayer {
    private ArrayList<Car> cars;
    private ArrayList<Client> clients;
    private ArrayList<Contrat> contrats;
    private ArrayList<Reservation> reservations;
    
    private final DataPersistence<Car> carPersistence;
    private final DataPersistence<Client> clientPersistence;
    private final DataPersistence<Reservation> reservationPersistence;
    private final DataPersistence<Contrat> contratPersistence;
    
    /**
     * Constructeur initialisant les listes d'entités
     */
    public DAOLocation() {
        // Initialiser les objets de persistance
        this.carPersistence = JsonPersistence.forCars();
        this.clientPersistence = JsonPersistence.forClients();
        this.reservationPersistence = JsonPersistence.forReservations();
        this.contratPersistence = JsonPersistence.forContrats();
        
        // Charger les données
        loadAllData();
    }
    
    /**
     * Ajoute une nouvelle réservation à la liste
     * @param reservation La réservation à ajouter
     * @return L'identifiant de la réservation ajoutée
     */
    @Override
    public int addReservation(Reservation reservation) {
        // Génération d'un nouvel ID pour la réservation
        int newId = 1;
        if (!reservations.isEmpty()) {
            // Trouver le plus grand ID existant et ajouter 1
            int maxId = 0;
            for (Reservation r : reservations) {
                if (r.getIdReservation() > maxId) {
                    maxId = r.getIdReservation();
                }
            }
            newId = maxId + 1;
        }
        
        // Définir l'ID de la réservation
        reservation.setIdReservation(newId);
        
        // Ajouter la réservation à la liste
        reservations.add(reservation);
        
        // Sauvegarder les données
        reservationPersistence.save(reservations);
        
        // Notifier les écouteurs
        fireEvent(ModelEvent.EventType.ENTITY_ADDED);
        
        System.out.println("✅ Réservation ajoutée avec succès. ID: " + newId);
        return newId;
    }
    
    /**
     * Ajoute un nouveau client à la liste
     * @param client Le client à ajouter
     * @return L'identifiant du client ajouté
     */
    @Override
    public int addClient(Client client) {
        // Génération d'un nouvel ID pour le client
        int newId = 1;
        if (!clients.isEmpty()) {
            // Trouver le plus grand ID existant et ajouter 1
            int maxId = 0;
            for (Client c : clients) {
                if (c.getIdClient() > maxId) {
                    maxId = c.getIdClient();
                }
            }
            newId = maxId + 1;
        }
        
        // Définir l'ID du client
        client.setIdClient(newId);
        
        // Ajouter le client à la liste
        clients.add(client);
        
        // Sauvegarder les données
        clientPersistence.save(clients);
        
        // Notifier les écouteurs
        fireEvent(ModelEvent.EventType.ENTITY_ADDED);
        
        System.out.println("✅ Client ajouté avec succès. ID: " + newId);
        return newId;
    }
    
    /**
     * Ajoute une nouvelle voiture à la liste
     * @param car La voiture à ajouter
     * @return Succès (1) ou échec (autre valeur)
     */
    @Override
    public int addCar(Car car) {
        // Génération d'une nouvelle immatriculation belge pour la voiture
        String newId = generateBelgianLicensePlate();
        
        // Définir l'immatriculation de la voiture
        car.setIdCar(newId);
        
        // Ajouter la voiture à la liste
        cars.add(car);
        
        // Sauvegarder les changements
        carPersistence.save(cars);
        
        // Notifier les écouteurs
        fireEvent(ModelEvent.EventType.ENTITY_ADDED);
        
        return 1; // Succès
    }
    
    /**
     * Génère une immatriculation belge au format standard (1-ABC-123)
     * @return Une nouvelle immatriculation belge unique
     */
    private String generateBelgianLicensePlate() {
        // Format belge standard: 1-ABC-123
        Random random = new Random();
        String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder plate;
        boolean isUnique;
        
        do {
            plate = new StringBuilder("1-");
            
            // Ajouter 3 lettres aléatoires
            for (int i = 0; i < 3; i++) {
                plate.append(letters.charAt(random.nextInt(letters.length())));
            }
            
            plate.append("-");
            
            // Ajouter 3 chiffres aléatoires
            plate.append(String.format("%03d", random.nextInt(1000)));
            
            // Vérifier si cette immatriculation est déjà utilisée
            final String plateToCheck = plate.toString();
            isUnique = cars.stream().noneMatch(c -> plateToCheck.equals(c.getIdCar()));
            
        } while (!isUnique);
        
        return plate.toString();
    }
    
    /**
     * Ajoute un nouveau contrat à la liste
     * @param contrat Le contrat à ajouter
     * @return L'identifiant du contrat ajouté (converti en entier)
     */
    @Override
    public int addContrat(Contrat contrat) {
        // Génération d'un nouvel ID pour le contrat
        String newId = "C-1";
        if (!contrats.isEmpty()) {
            // Trouver le plus grand ID existant et ajouter 1
            int maxId = 0;
            for (Contrat c : contrats) {
                String idStr = c.getIdContrat();
                if (idStr != null && idStr.startsWith("C-")) {
                    try {
                        int id = Integer.parseInt(idStr.substring(2));
                        if (id > maxId) {
                            maxId = id;
                        }
                    } catch (NumberFormatException e) {
                        // Ignorer les ID mal formatés
                    }
                }
            }
            newId = "C-" + (maxId + 1);
        }
        
        // Définir l'ID du contrat
        contrat.setIdContrat(newId);
        
        // Ajouter le contrat à la liste
        contrats.add(contrat);
        
        // Sauvegarder les données
        contratPersistence.save(contrats);
        
        // Notifier les écouteurs
        fireEvent(ModelEvent.EventType.ENTITY_ADDED);
        
        System.out.println("✅ Contrat ajouté avec succès. ID: " + newId);
        
        // Retourner l'ID numérique du contrat
        try {
            return Integer.parseInt(newId.substring(2));
        } catch (NumberFormatException e) {
            return -1; // Valeur par défaut en cas d'erreur
        }
    }
    
    /**
     * Récupère toutes les voitures
     * @return Liste de toutes les voitures
     */
    @Override
    public ArrayList<Car> getAllCars() {
        return new ArrayList<>(cars);
    }
    
    /**
     * Récupère tous les clients
     * @return Liste de tous les clients
     */
    @Override
    public ArrayList<Client> getAllClients() {
        return new ArrayList<>(clients);
    }
    
    /**
     * Récupère toutes les réservations
     * @return Liste de toutes les réservations
     */
    @Override
    public ArrayList<Reservation> getAllReservations() {
        return new ArrayList<>(reservations);
    }
    
    /**
     * Récupère tous les contrats
     * @return Liste de tous les contrats
     */
    @Override
    public ArrayList<Contrat> getAllContrats() {
        return new ArrayList<>(contrats);
    }
    
    /**
     * Met à jour une voiture existante
     * @param car Voiture à mettre à jour
     * @return true si la mise à jour a réussi, false sinon
     */
    @Override
    public boolean updateCar(Car car) {
        for (int i = 0; i < cars.size(); i++) {
            if (cars.get(i).getIdCar().equals(car.getIdCar())) {
                cars.set(i, car);
                carPersistence.save(cars);
                
                // Notifier les écouteurs
                fireEvent(ModelEvent.EventType.ENTITY_MODIFIED);
                
                return true;
            }
        }
        return false;
    }
    
    /**
     * Met à jour un client existant
     * @param client Client à mettre à jour
     * @return true si la mise à jour a réussi, false sinon
     */
    @Override
    public boolean updateClient(Client client) {
        for (int i = 0; i < clients.size(); i++) {
            if (clients.get(i).getIdClient() == client.getIdClient()) {
                clients.set(i, client);
                clientPersistence.save(clients);
                
                // Notifier les écouteurs
                fireEvent(ModelEvent.EventType.ENTITY_MODIFIED);
                
                return true;
            }
        }
        return false;
    }
    
    /**
     * Met à jour une réservation existante
     * @param reservation Réservation à mettre à jour
     * @return true si la mise à jour a réussi, false sinon
     */
    @Override
    public boolean updateReservation(Reservation reservation) {
        for (int i = 0; i < reservations.size(); i++) {
            if (reservations.get(i).getIdReservation() == reservation.getIdReservation()) {
                reservations.set(i, reservation);
                reservationPersistence.save(reservations);
                
                // Notifier les écouteurs
                fireEvent(ModelEvent.EventType.ENTITY_MODIFIED);
                
                return true;
            }
        }
        return false;
    }
    
    /**
     * Met à jour un contrat existant
     * @param contrat Contrat à mettre à jour
     * @return true si la mise à jour a réussi, false sinon
     */
    @Override
    public boolean updateContrat(Contrat contrat) {
        for (int i = 0; i < contrats.size(); i++) {
            if (contrats.get(i).getIdContrat().equals(contrat.getIdContrat())) {
                contrats.set(i, contrat);
                contratPersistence.save(contrats);
                
                // Notifier les écouteurs
                fireEvent(ModelEvent.EventType.ENTITY_MODIFIED);
                
                return true;
            }
        }
        return false;
    }
    
    /**
     * Supprime une voiture
     * @param idCar ID de la voiture à supprimer
     * @return true si la suppression a réussi, false sinon
     */
    @Override
    public boolean deleteCar(String idCar) {
        for (int i = 0; i < cars.size(); i++) {
            if (cars.get(i).getIdCar().equals(idCar)) {
                cars.remove(i);
                carPersistence.save(cars);
                
                // Notifier les écouteurs
                fireEvent(ModelEvent.EventType.ENTITY_DELETED);
                
                return true;
            }
        }
        return false;
    }
    
    /**
     * Supprime un client
     * @param idClient ID du client à supprimer
     * @return true si la suppression a réussi, false sinon
     */
    @Override
    public boolean deleteClient(int idClient) {
        for (int i = 0; i < clients.size(); i++) {
            if (clients.get(i).getIdClient() == idClient) {
                clients.remove(i);
                clientPersistence.save(clients);
                
                // Notifier les écouteurs
                fireEvent(ModelEvent.EventType.ENTITY_DELETED);
                
                return true;
            }
        }
        return false;
    }
    
    /**
     * Supprime une réservation
     * @param idReservation ID de la réservation à supprimer
     * @return true si la suppression a réussi, false sinon
     */
    @Override
    public boolean deleteReservation(int idReservation) {
        for (int i = 0; i < reservations.size(); i++) {
            if (reservations.get(i).getIdReservation() == idReservation) {
                reservations.remove(i);
                reservationPersistence.save(reservations);
                
                // Notifier les écouteurs
                fireEvent(ModelEvent.EventType.ENTITY_DELETED);
                
                return true;
            }
        }
        return false;
    }
    
    /**
     * Supprime un contrat
     * @param idContrat ID du contrat à supprimer
     * @return true si la suppression a réussi, false sinon
     */
    @Override
    public boolean deleteContrat(String idContrat) {
        for (int i = 0; i < contrats.size(); i++) {
            if (contrats.get(i).getIdContrat().equals(idContrat)) {
                contrats.remove(i);
                contratPersistence.save(contrats);
                
                // Notifier les écouteurs
                fireEvent(ModelEvent.EventType.ENTITY_DELETED);
                
                return true;
            }
        }
        return false;
    }
    
    /**
     * Recherche une voiture par son ID
     * @param idCar ID de la voiture
     * @return Voiture trouvée ou null
     */
    @Override
    public Car findCarById(String idCar) {
        for (Car car : cars) {
            if (car.getIdCar().equals(idCar)) {
                return car;
            }
        }
        return null;
    }
    
    /**
     * Recherche un client par son ID
     * @param idClient ID du client
     * @return Client trouvé ou null
     */
    @Override
    public Client findClientById(int idClient) {
        for (Client client : clients) {
            if (client.getIdClient() == idClient) {
                return client;
            }
        }
        return null;
    }
    
    /**
     * Recherche une réservation par son ID
     * @param idReservation ID de la réservation
     * @return Réservation trouvée ou null
     */
    @Override
    public Reservation findReservationById(int idReservation) {
        for (Reservation reservation : reservations) {
            if (reservation.getIdReservation() == idReservation) {
                return reservation;
            }
        }
        return null;
    }
    
    /**
     * Recherche un contrat par son ID
     * @param idContrat ID du contrat
     * @return Contrat trouvé ou null
     */
    @Override
    public Contrat findContratById(String idContrat) {
        for (Contrat contrat : contrats) {
            if (contrat.getIdContrat().equals(idContrat)) {
                return contrat;
            }
        }
        return null;
    }
    
    /**
     * Sauvegarde toutes les données
     * @return true si la sauvegarde a réussi, false sinon
     */
    @Override
    public boolean saveAllData() {
        boolean success = true;
        
        success &= carPersistence.save(cars);
        success &= clientPersistence.save(clients);
        success &= reservationPersistence.save(reservations);
        success &= contratPersistence.save(contrats);
        
        return success;
    }
    
    /**
     * Charge toutes les données
     * @return true si le chargement a réussi, false sinon
     */
    @Override
    public boolean loadAllData() {
        try {
            // Charger les données depuis les fichiers JSON
            this.cars = new ArrayList<>(carPersistence.load());
            this.clients = new ArrayList<>(clientPersistence.load());
            this.reservations = new ArrayList<>(reservationPersistence.load());
            this.contrats = new ArrayList<>(contratPersistence.load());
            
            // Si aucune donnée n'a été chargée, initialiser des listes vides
            if (this.cars == null) this.cars = new ArrayList<>();
            if (this.clients == null) this.clients = new ArrayList<>();
            if (this.contrats == null) this.contrats = new ArrayList<>();
            if (this.reservations == null) this.reservations = new ArrayList<>();
            
            // Notifier les écouteurs
            fireEvent(ModelEvent.EventType.DATA_LOADED);
            
            System.out.println("✅ DAO initialisé avec " + cars.size() + " voitures, " + 
                              clients.size() + " clients, " + 
                              reservations.size() + " réservations et " + 
                              contrats.size() + " contrats");
            
            return true;
        } catch (Exception e) {
            System.err.println("❌ Erreur lors du chargement des données: " + e.getMessage());
            return false;
        }
    }
}
