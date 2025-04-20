package org.example.model.persistence;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.example.model.entity.*;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe utilitaire pour la persistance des données en JSON
 * Implémente le principe de Single Responsibility (S de SOLID)
 */
public class JsonPersistence {
    private static final String DATA_DIR = "data";
    
    /**
     * Obtient une instance de persistance JSON pour les voitures
     * @return Instance de persistance pour les voitures
     */
    public static DataPersistence<Car> forCars() {
        return new JsonPersistenceImpl<>(DATA_DIR, "cars.json", new TypeToken<ArrayList<Car>>(){}.getType());
    }
    
    /**
     * Obtient une instance de persistance JSON pour les clients
     * @return Instance de persistance pour les clients
     */
    public static DataPersistence<Client> forClients() {
        return new JsonPersistenceImpl<>(DATA_DIR, "clients.json", new TypeToken<ArrayList<Client>>(){}.getType());
    }
    
    /**
     * Obtient une instance de persistance JSON pour les réservations
     * @return Instance de persistance pour les réservations
     */
    public static DataPersistence<Reservation> forReservations() {
        return new ReservationJsonPersistence(DATA_DIR, "reservations.json");
    }
    
    /**
     * Obtient une instance de persistance JSON pour les contrats
     * @return Instance de persistance pour les contrats
     */
    public static DataPersistence<Contrat> forContrats() {
        return new JsonPersistenceImpl<>(DATA_DIR, "contrats.json", new TypeToken<ArrayList<Contrat>>(){}.getType());
    }
    
    /**
     * Implémentation générique de la persistance JSON
     * @param <T> Type d'entité à persister
     */
    private static class JsonPersistenceImpl<T> extends AbstractPersistence<T> {
        private final Type type;
        private static final Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .excludeFieldsWithoutExposeAnnotation()
                .registerTypeAdapter(Reservation.class, new ReservationTypeAdapter())
                .create();
        
        // Gson pour la lecture des anciens fichiers JSON sans annotations @Expose
        private static final Gson legacyGson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .registerTypeAdapter(Reservation.class, new ReservationTypeAdapter())
                .create();
        
        /**
         * Constructeur
         * @param dataDir Répertoire de données
         * @param fileName Nom du fichier
         * @param type Type des entités à persister
         */
        public JsonPersistenceImpl(String dataDir, String fileName, Type type) {
            super(dataDir, fileName);
            this.type = type;
        }
        
        @Override
        protected boolean saveData(List<T> data) {
            try (Writer writer = new FileWriter(filePath)) {
                gson.toJson(data, writer);
                return true;
            } catch (IOException e) {
                System.err.println("❌ Erreur lors de la sauvegarde des données: " + e.getMessage());
                return false;
            }
        }
        
        @Override
        protected List<T> loadData() {
            try {
                String json = new String(Files.readAllBytes(Paths.get(filePath)));
                ArrayList<T> data = gson.fromJson(json, type);
                if (data == null) {
                    data = legacyGson.fromJson(json, type);
                }
                return data;
            } catch (IOException e) {
                System.err.println("❌ Erreur lors du chargement des données: " + e.getMessage());
                return new ArrayList<>();
            }
        }
    }
    
    /**
     * Implémentation spécifique pour les réservations
     * Gère le filtrage des contrats et la conversion des types
     */
    private static class ReservationJsonPersistence extends JsonPersistenceImpl<Reservation> {
        /**
         * Constructeur
         * @param dataDir Répertoire de données
         * @param fileName Nom du fichier
         */
        public ReservationJsonPersistence(String dataDir, String fileName) {
            super(dataDir, fileName, new TypeToken<ArrayList<Reservation>>(){}.getType());
        }
        
        @Override
        protected List<Reservation> prepareDataForSave(List<Reservation> data) {
            // Filtrer les réservations pour exclure les contrats
            List<Reservation> pureReservations = new ArrayList<>();
            for (Reservation r : data) {
                if (!(r instanceof Contrat)) {
                    pureReservations.add(r);
                }
            }
            return pureReservations;
        }
        
        @Override
        protected List<Reservation> processLoadedData(List<Reservation> data) {
            if (data == null) {
                return new ArrayList<>();
            }
            
            // Convertir les réservations génériques en types spécifiques si nécessaire
            for (int i = 0; i < data.size(); i++) {
                Reservation r = data.get(i);
                if (r.getClass() == Reservation.class) {
                    // Si c'est une réservation générique, la convertir en ReservationStandard
                    ReservationStandard rs = new ReservationStandard(
                        r.getIdReservation(),
                        r.getResponsable(),
                        r.getNotes(),
                        r.getPrice()
                    );
                    rs.setDateDebut(r.getDateDebut());
                    rs.setDateFin(r.getDateFin());
                    rs.setCar(r.getCar());
                    rs.setClient(r.getClient());
                    rs.setStatut(r.getStatut());
                    
                    data.set(i, rs);
                }
            }
            
            return data;
        }
    }
    
    // Méthodes de compatibilité pour l'ancienne API
    
    /**
     * Sauvegarde la liste des voitures dans un fichier JSON
     * @param cars Liste des voitures à sauvegarder
     * @return true si la sauvegarde a réussi, false sinon
     * @deprecated Utiliser forCars().save(cars) à la place
     */
    @Deprecated
    public static boolean saveCars(List<Car> cars) {
        return forCars().save(cars);
    }
    
    /**
     * Sauvegarde la liste des clients dans un fichier JSON
     * @param clients Liste des clients à sauvegarder
     * @return true si la sauvegarde a réussi, false sinon
     * @deprecated Utiliser forClients().save(clients) à la place
     */
    @Deprecated
    public static boolean saveClients(List<Client> clients) {
        return forClients().save(clients);
    }
    
    /**
     * Sauvegarde la liste des réservations dans un fichier JSON
     * @param reservations Liste des réservations à sauvegarder
     * @return true si la sauvegarde a réussi, false sinon
     * @deprecated Utiliser forReservations().save(reservations) à la place
     */
    @Deprecated
    public static boolean saveReservations(List<Reservation> reservations) {
        return forReservations().save(reservations);
    }
    
    /**
     * Sauvegarde la liste des contrats dans un fichier JSON
     * @param contrats Liste des contrats à sauvegarder
     * @return true si la sauvegarde a réussi, false sinon
     * @deprecated Utiliser forContrats().save(contrats) à la place
     */
    @Deprecated
    public static boolean saveContrats(List<Contrat> contrats) {
        return forContrats().save(contrats);
    }
    
    /**
     * Charge la liste des voitures depuis un fichier JSON
     * @return Liste des voitures chargées ou une liste vide si le fichier n'existe pas
     * @deprecated Utiliser forCars().load() à la place
     */
    @Deprecated
    public static ArrayList<Car> loadCars() {
        return new ArrayList<>(forCars().load());
    }
    
    /**
     * Charge la liste des clients depuis un fichier JSON
     * @return Liste des clients chargés ou une liste vide si le fichier n'existe pas
     * @deprecated Utiliser forClients().load() à la place
     */
    @Deprecated
    public static ArrayList<Client> loadClients() {
        return new ArrayList<>(forClients().load());
    }
    
    /**
     * Charge la liste des réservations depuis un fichier JSON
     * @return Liste des réservations chargées ou une liste vide si le fichier n'existe pas
     * @deprecated Utiliser forReservations().load() à la place
     */
    @Deprecated
    public static ArrayList<Reservation> loadReservations() {
        return new ArrayList<>(forReservations().load());
    }
    
    /**
     * Charge la liste des contrats depuis un fichier JSON
     * @return Liste des contrats chargés ou une liste vide si le fichier n'existe pas
     * @deprecated Utiliser forContrats().load() à la place
     */
    @Deprecated
    public static ArrayList<Contrat> loadContrats() {
        return new ArrayList<>(forContrats().load());
    }
}
