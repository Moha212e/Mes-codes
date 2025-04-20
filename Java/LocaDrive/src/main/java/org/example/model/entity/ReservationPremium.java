package org.example.model.entity;

import com.google.gson.annotations.Expose;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe représentant une réservation premium dans le système
 * Offre des fonctionnalités et services supplémentaires par rapport à une réservation standard
 */
public class ReservationPremium extends Reservation {
    
    @Expose
    private boolean assuranceTousCasques;
    
    @Expose
    private List<String> servicesAdditionnels;
    
    @Expose
    private float remiseSpeciale;
    
    /**
     * Constructeur par défaut
     */
    public ReservationPremium() {
        super();
        this.assuranceTousCasques = true;
        this.servicesAdditionnels = new ArrayList<>();
        this.remiseSpeciale = 0.0f;
    }
    
    /**
     * Constructeur avec paramètres de base
     */
    public ReservationPremium(int idReservation, String responsable, String notes, float price) {
        super(idReservation, responsable, notes, price);
        this.assuranceTousCasques = true;
        this.servicesAdditionnels = new ArrayList<>();
        this.remiseSpeciale = 0.0f;
    }
    
    /**
     * Constructeur complet
     */
    public ReservationPremium(int idReservation, String responsable, String notes, float price,
                             LocalDate dateDebut, LocalDate dateFin, Car car, Client client,
                             boolean assuranceTousCasques, List<String> servicesAdditionnels, float remiseSpeciale) {
        super(idReservation, responsable, notes, price, dateDebut, dateFin, car, client);
        this.assuranceTousCasques = assuranceTousCasques;
        this.servicesAdditionnels = servicesAdditionnels != null ? new ArrayList<>(servicesAdditionnels) : new ArrayList<>();
        this.remiseSpeciale = remiseSpeciale;
    }
    
    /**
     * Calcule le prix de la réservation premium
     * Le prix est basé sur le prix journalier de la voiture multiplié par le nombre de jours
     * Une assurance tous risques est incluse par défaut
     * Des services additionnels peuvent être ajoutés pour un coût supplémentaire
     * Une remise spéciale peut être appliquée
     */
    @Override
    public void calculerPrix() {
        if ((car != null || carPricePerDay > 0) && dateDebut != null && dateFin != null) {
            long nombreJours = ChronoUnit.DAYS.between(dateDebut, dateFin);
            // Assurer au moins 1 jour de location
            if (nombreJours < 1) nombreJours = 1;
            
            // Prix de base - utilise le prix par jour de la voiture (soit de l'objet car, soit de l'attribut hérité)
            float prixJournalier = (car != null) ? car.getPriceday() : carPricePerDay;
            float prixBase = prixJournalier * nombreJours;
            
            // Ajout du coût de l'assurance tous risques
            if (assuranceTousCasques) {
                prixBase += 25.0f * nombreJours; // 25€ par jour pour l'assurance tous risques
            }
            
            // Ajout du coût des services additionnels
            float coutServices = servicesAdditionnels.size() * 15.0f; // 15€ par service additionnel
            prixBase += coutServices;
            
            // Application de la remise spéciale
            float remise = prixBase * (remiseSpeciale / 100.0f);
            prixBase -= remise;
            
            this.price = prixBase;
        }
    }
    
    /**
     * Valide la réservation premium
     * Une réservation premium est valide si:
     * - La voiture est disponible pour la période demandée
     * - Le client a un permis de conduire valide
     * - La période de réservation est d'au moins 1 jour
     * - La voiture est de catégorie premium ou supérieure
     * @return true si la réservation est valide, false sinon
     */
    @Override
    public boolean validerReservation() {
        // Vérifier que les objets nécessaires sont présents
        boolean objetsPrésents = (car != null || (carBrand != null && carModel != null)) &&
                                (client != null || (clientName != null && clientSurname != null)) &&
                                dateDebut != null && dateFin != null;
        
        if (!objetsPrésents) {
            return false;
        }
        
        // Vérifier que la période est valide
        if (dateDebut.isAfter(dateFin) || dateDebut.isEqual(dateFin)) {
            return false;
        }
        
        // Vérifier que la voiture est de catégorie premium ou supérieure
        // Cette vérification dépend de la structure de la classe Car
        // Exemple: if (!car.getCategorie().equals("Premium") && !car.getCategorie().equals("Luxe"))
        //             return false;
        
        // Autres validations spécifiques à la réservation premium
        // (à implémenter selon les règles métier)
        
        return true;
    }
    
    /**
     * Ajoute un service additionnel à la réservation
     * @param service le service à ajouter
     */
    public void ajouterService(String service) {
        if (!servicesAdditionnels.contains(service)) {
            servicesAdditionnels.add(service);
            calculerPrix();
        }
    }
    
    /**
     * Retire un service additionnel de la réservation
     * @param service le service à retirer
     */
    public void retirerService(String service) {
        if (servicesAdditionnels.remove(service)) {
            calculerPrix();
        }
    }
    
    /**
     * Vérifie si l'assurance tous risques est incluse
     * @return true si l'assurance tous risques est incluse, false sinon
     */
    public boolean isAssuranceTousCasques() {
        return assuranceTousCasques;
    }
    
    /**
     * Définit si l'assurance tous risques est incluse
     * @param assuranceTousCasques true pour inclure l'assurance tous risques, false sinon
     */
    public void setAssuranceTousCasques(boolean assuranceTousCasques) {
        this.assuranceTousCasques = assuranceTousCasques;
        calculerPrix();
    }
    
    /**
     * Retourne la liste des services additionnels
     * @return la liste des services additionnels
     */
    public List<String> getServicesAdditionnels() {
        return new ArrayList<>(servicesAdditionnels);
    }
    
    /**
     * Définit la liste des services additionnels
     * @param servicesAdditionnels la liste des services additionnels
     */
    public void setServicesAdditionnels(List<String> servicesAdditionnels) {
        this.servicesAdditionnels = servicesAdditionnels != null ? new ArrayList<>(servicesAdditionnels) : new ArrayList<>();
        calculerPrix();
    }
    
    /**
     * Retourne la remise spéciale
     * @return la remise spéciale en pourcentage
     */
    public float getRemiseSpeciale() {
        return remiseSpeciale;
    }
    
    /**
     * Définit la remise spéciale
     * @param remiseSpeciale la remise spéciale en pourcentage
     */
    public void setRemiseSpeciale(float remiseSpeciale) {
        this.remiseSpeciale = remiseSpeciale;
        calculerPrix();
    }
    
    @Override
    public String toString() {
        return "ReservationPremium{" +
               "idReservation=" + idReservation +
               ", responsable='" + responsable + '\'' +
               ", client=" + getClientFullName() +
               ", voiture=" + getCarFullDescription() +
               ", dateDebut=" + dateDebut +
               ", dateFin=" + dateFin +
               ", assuranceTousCasques=" + assuranceTousCasques +
               ", servicesAdditionnels=" + servicesAdditionnels +
               ", remiseSpeciale=" + remiseSpeciale + "%" +
               ", statut=" + statut.getLibelle() +
               ", prix=" + price +
               '}';
    }
}
