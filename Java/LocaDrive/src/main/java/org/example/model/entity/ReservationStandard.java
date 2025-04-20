package org.example.model.entity;

import com.google.gson.annotations.Expose;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Classe représentant une réservation standard dans le système
 * Implémente les fonctionnalités spécifiques aux réservations de base
 */
public class ReservationStandard extends Reservation {
    
    @Expose
    private boolean assuranceBase;
    
    /**
     * Constructeur par défaut
     */
    public ReservationStandard() {
        super();
        this.assuranceBase = false;
    }
    
    /**
     * Constructeur avec paramètres de base
     */
    public ReservationStandard(int idReservation, String responsable, String notes, float price) {
        super(idReservation, responsable, notes, price);
        this.assuranceBase = false;
    }
    
    /**
     * Constructeur complet
     */
    public ReservationStandard(int idReservation, String responsable, String notes, float price,
                              LocalDate dateDebut, LocalDate dateFin, Car car, Client client, 
                              boolean assuranceBase) {
        super(idReservation, responsable, notes, price, dateDebut, dateFin, car, client);
        this.assuranceBase = assuranceBase;
    }
    
    /**
     * Calcule le prix de la réservation standard
     * Le prix est basé sur le prix journalier de la voiture multiplié par le nombre de jours
     * Une assurance de base peut être ajoutée pour un coût supplémentaire
     */
    @Override
    public void calculerPrix() {
        if (car != null && dateDebut != null && dateFin != null) {
            long nombreJours = ChronoUnit.DAYS.between(dateDebut, dateFin);
            // Assurer au moins 1 jour de location
            if (nombreJours < 1) nombreJours = 1;
            
            // Prix de base - utilise le prix par jour de la voiture (soit de l'objet car, soit de l'attribut hérité)
            float prixJournalier = (car != null) ? car.getPriceday() : carPricePerDay;
            float prixBase = prixJournalier * nombreJours;
            
            // Ajout du coût de l'assurance de base si sélectionnée
            if (assuranceBase) {
                prixBase += 10.0f * nombreJours; // 10€ par jour pour l'assurance de base
            }
            
            this.price = prixBase;
        }
    }
    
    /**
     * Valide la réservation standard
     * Une réservation standard est valide si:
     * - La voiture est disponible pour la période demandée
     * - Le client a un permis de conduire valide
     * - La période de réservation est d'au moins 1 jour
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
        
        // Autres validations spécifiques à la réservation standard
        // (à implémenter selon les règles métier)
        
        return true;
    }
    
    /**
     * Vérifie si l'assurance de base est incluse
     * @return true si l'assurance de base est incluse, false sinon
     */
    public boolean isAssuranceBase() {
        return assuranceBase;
    }
    
    /**
     * Définit si l'assurance de base est incluse
     * @param assuranceBase true pour inclure l'assurance de base, false sinon
     */
    public void setAssuranceBase(boolean assuranceBase) {
        this.assuranceBase = assuranceBase;
        calculerPrix();
    }
    
    @Override
    public String toString() {
        return "ReservationStandard{" +
               "idReservation=" + idReservation +
               ", responsable='" + responsable + '\'' +
               ", client=" + getClientFullName() +
               ", voiture=" + getCarFullDescription() +
               ", dateDebut=" + dateDebut +
               ", dateFin=" + dateFin +
               ", assuranceBase=" + assuranceBase +
               ", statut=" + statut.getLibelle() +
               ", prix=" + price +
               '}';
    }
}
