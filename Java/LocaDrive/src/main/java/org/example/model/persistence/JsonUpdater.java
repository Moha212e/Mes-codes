package org.example.model.persistence;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.example.model.entity.*;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Utilitaire pour mettre à jour les fichiers JSON existants
 * au format compatible avec les annotations @Expose
 */
public class JsonUpdater {

    public static void main(String[] args) {
        System.out.println("Démarrage de la mise à jour des fichiers JSON...");
        
        // Chargement des données avec l'ancien format
        ArrayList<Car> cars = JsonPersistence.loadCars();
        ArrayList<Client> clients = JsonPersistence.loadClients();
        ArrayList<Reservation> reservations = JsonPersistence.loadReservations();
        ArrayList<Contrat> contrats = JsonPersistence.loadContrats();
        
        // Affichage des statistiques
        System.out.println("Données chargées:");
        System.out.println("- " + cars.size() + " voitures");
        System.out.println("- " + clients.size() + " clients");
        System.out.println("- " + reservations.size() + " réservations");
        System.out.println("- " + contrats.size() + " contrats");
        
        // Sauvegarde des données avec le nouveau format
        boolean carsUpdated = JsonPersistence.saveCars(cars);
        boolean clientsUpdated = JsonPersistence.saveClients(clients);
        boolean reservationsUpdated = JsonPersistence.saveReservations(reservations);
        boolean contratsUpdated = JsonPersistence.saveContrats(contrats);
        
        // Affichage des résultats
        System.out.println("\nRésultats de la mise à jour:");
        System.out.println("- Voitures: " + (carsUpdated ? "OK" : "ÉCHEC"));
        System.out.println("- Clients: " + (clientsUpdated ? "OK" : "ÉCHEC"));
        System.out.println("- Réservations: " + (reservationsUpdated ? "OK" : "ÉCHEC"));
        System.out.println("- Contrats: " + (contratsUpdated ? "OK" : "ÉCHEC"));
        
        System.out.println("\nMise à jour terminée.");
    }
}
