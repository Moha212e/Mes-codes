package org.example.model.entity;

import com.google.gson.annotations.Expose;
import java.time.LocalDate;

/**
 * Classe abstraite représentant une réservation dans le système
 * Sert de base pour différents types de réservations
 * Intègre les attributs importants de Client et Car
 */
public abstract class Reservation extends AbstractEntity implements Identifiable {
    @Expose
    protected int idReservation;
    @Expose
    protected String responsable;
    @Expose
    protected String notes;
    @Expose
    protected float price;
    @Expose
    protected LocalDate dateDebut;
    @Expose
    protected LocalDate dateFin;
    @Expose
    protected LocalDate dateReservation; // Date à laquelle la réservation a été créée
    @Expose
    protected Car car;
    @Expose
    protected Client client;
    @Expose
    protected StatutReservation statut;
    
    // Attributs directement hérités de Client
    @Expose
    protected String clientName;
    @Expose
    protected String clientSurname;
    @Expose
    protected String clientEmail;
    @Expose
    protected String clientPhone;
    
    // Attributs directement hérités de Car
    @Expose
    protected String carBrand;
    @Expose
    protected String carModel;
    @Expose
    protected int carYear;
    @Expose
    protected float carPricePerDay;

    /**
     * Énumération pour le statut de la réservation
     */
    public enum StatutReservation {
        EN_ATTENTE("En attente"),
        CONFIRMEE("Confirmée"),
        ANNULEE("Annulée"),
        TERMINEE("Terminée");

        private final String libelle;

        StatutReservation(String libelle) {
            this.libelle = libelle;
        }

        public String getLibelle() {
            return libelle;
        }
    }

    /**
     * Constructeur par défaut
     */
    public Reservation() {
        this.statut = StatutReservation.EN_ATTENTE;
        this.dateReservation = LocalDate.now();
    }

    /**
     * Constructeur avec paramètres de base
     */
    public Reservation(int idReservation, String responsable, String notes, float price) {
        this.idReservation = idReservation;
        this.responsable = responsable;
        this.notes = notes;
        this.price = price;
        this.statut = StatutReservation.EN_ATTENTE;
        this.dateReservation = LocalDate.now();
    }

    /**
     * Constructeur complet
     */
    public Reservation(int idReservation, String responsable, String notes, float price, 
                      LocalDate dateDebut, LocalDate dateFin, Car car, Client client) {
        this.idReservation = idReservation;
        this.responsable = responsable;
        this.notes = notes;
        this.price = price;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        setCar(car);
        setClient(client);
        this.statut = StatutReservation.EN_ATTENTE;
        this.dateReservation = LocalDate.now();
    }

    /**
     * Méthode abstraite pour calculer le prix de la réservation
     * Chaque sous-classe doit implémenter sa propre logique de calcul
     */
    public abstract void calculerPrix();

    /**
     * Méthode abstraite pour valider la réservation
     * Chaque sous-classe peut avoir des règles de validation spécifiques
     * @return true si la réservation est valide, false sinon
     */
    public abstract boolean validerReservation();

    /**
     * Confirme la réservation
     */
    public void confirmer() {
        this.statut = StatutReservation.CONFIRMEE;
    }

    /**
     * Annule la réservation
     */
    public void annuler() {
        this.statut = StatutReservation.ANNULEE;
    }

    /**
     * Termine la réservation
     */
    public void terminer() {
        this.statut = StatutReservation.TERMINEE;
    }

    /**
     * Synchronise les attributs hérités de Client avec l'objet Client
     */
    protected void synchroniserAttributsClient() {
        if (client != null) {
            this.clientName = client.getName();
            this.clientSurname = client.getSurname();
            this.clientEmail = client.getEmail();
            this.clientPhone = client.getPhoneNumber();
        }
    }

    /**
     * Synchronise les attributs hérités de Car avec l'objet Car
     */
    protected void synchroniserAttributsCar() {
        if (car != null) {
            this.carBrand = car.getBrand();
            this.carModel = car.getModel();
            this.carYear = car.getYear();
            this.carPricePerDay = car.getPriceday();
        }
    }

    public int getIdReservation() {
        return idReservation;
    }

    public void setIdReservation(int idReservation) {
        this.idReservation = idReservation;
    }

    @Override
    public Object getId() {
        return idReservation;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public LocalDate getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
        calculerPrix();
    }

    public LocalDate getDateFin() {
        return dateFin;
    }

    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
        calculerPrix();
    }

    public LocalDate getDateReservation() {
        return dateReservation;
    }

    public void setDateReservation(LocalDate dateReservation) {
        this.dateReservation = dateReservation;
    }

    public StatutReservation getStatut() {
        return statut;
    }

    public void setStatut(StatutReservation statut) {
        this.statut = statut;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
        synchroniserAttributsClient();
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
        synchroniserAttributsCar();
        calculerPrix();
    }

    // Getters pour les attributs hérités de Client
    public String getClientName() {
        return clientName;
    }

    public String getClientSurname() {
        return clientSurname;
    }

    public String getClientEmail() {
        return clientEmail;
    }

    public String getClientPhone() {
        return clientPhone;
    }

    // Getters pour les attributs hérités de Car
    public String getCarBrand() {
        return carBrand;
    }

    public String getCarModel() {
        return carModel;
    }

    public int getCarYear() {
        return carYear;
    }

    public float getCarPricePerDay() {
        return carPricePerDay;
    }

    // Méthode utilitaire pour obtenir le nom complet du client
    public String getClientFullName() {
        return clientName + " " + clientSurname;
    }

    // Méthode utilitaire pour obtenir la description complète de la voiture
    public String getCarFullDescription() {
        return carBrand + " " + carModel + " (" + carYear + ")";
    }

    @Override
    public String toString() {
        return "Reservation{" + 
               "idReservation=" + idReservation + 
               ", responsable='" + responsable + '\'' + 
               ", dateDebut=" + dateDebut + 
               ", dateFin=" + dateFin + 
               ", dateReservation=" + dateReservation + 
               ", client=" + getClientFullName() + 
               ", voiture=" + getCarFullDescription() + 
               ", statut=" + statut.getLibelle() + 
               '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return idReservation == that.idReservation;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(idReservation);
    }
}
