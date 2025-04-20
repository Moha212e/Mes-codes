package org.example;

import org.example.model.dao.DAOLocation;
import org.example.model.entity.*;
import org.example.model.persistence.JsonPersistence;
import org.example.view.GUI.JFramesLocation;

import java.util.ArrayList;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        // Si des arguments sont passés et que le premier est "update-json", exécuter la mise à jour des fichiers JSON
        if (args.length > 0 && args[0].equals("update-json")) {
            updateJsonFiles();
            return;
        }
        
        // Sinon, lancer l'application normalement
        JFramesLocation.main(args);
    }
    
    /**
     * Met à jour les fichiers JSON pour les rendre compatibles avec la nouvelle structure des classes
     */
    private static void updateJsonFiles() {
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