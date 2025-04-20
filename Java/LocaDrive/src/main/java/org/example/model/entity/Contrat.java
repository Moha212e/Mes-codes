package org.example.model.entity;

import com.google.gson.annotations.Expose;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe représentant un contrat de location dans le système
 * Hérite de Reservation pour réutiliser les informations de base de la réservation
 */
public class Contrat extends Reservation {
    // Identifiant spécifique au contrat
    @Expose
    private String idContrat;
    
    // Attributs spécifiques au contrat
    @Expose
    private double caution;
    @Expose
    private String typeAssurance;
    @Expose
    private List<String> options;
    @Expose
    private boolean estSigne;
    @Expose
    private StatutContrat statutContrat;

    /**
     * Énumération pour le statut du contrat
     */
    public enum StatutContrat {
        EN_ATTENTE("En attente"),
        EN_COURS("En cours"),
        TERMINE("Terminé"),
        ANNULE("Annulé");

        private final String libelle;

        StatutContrat(String libelle) {
            this.libelle = libelle;
        }

        public String getLibelle() {
            return libelle;
        }

        public Object getLabel() {
            return libelle;
        }
    }

    /**
     * Constructeur par défaut
     */
    public Contrat() {
        super();
        this.options = new ArrayList<>();
        this.estSigne = false;
        this.statutContrat = StatutContrat.EN_ATTENTE;
    }

    /**
     * Constructeur à partir d'une réservation existante
     * @param reservation La réservation à partir de laquelle créer le contrat
     * @param idContrat L'identifiant unique du contrat
     */
    public Contrat(Reservation reservation, String idContrat) {
        // Copier les propriétés de la réservation
        super(reservation.getIdReservation(), 
              reservation.getResponsable(), 
              reservation.getNotes(), 
              reservation.getPrice(),
              reservation.getDateDebut(),
              reservation.getDateFin(),
              reservation.getCar(),
              reservation.getClient());
        
        // Initialiser les propriétés spécifiques au contrat
        this.idContrat = idContrat;
        this.options = new ArrayList<>();
        this.estSigne = false;
        this.statutContrat = StatutContrat.EN_ATTENTE;
        
        // Calculer le montant total initial
        calculerPrix();
    }

    /**
     * Constructeur complet
     */
    public Contrat(String idContrat, int idReservation, Client client, Car voiture, 
                  LocalDate dateDebut, LocalDate dateFin, String responsable) {
        // Initialiser les propriétés de la réservation
        super(idReservation, responsable, "", 0, dateDebut, dateFin, voiture, client);
        
        // Initialiser les propriétés spécifiques au contrat
        this.idContrat = idContrat;
        this.options = new ArrayList<>();
        this.estSigne = false;
        this.statutContrat = StatutContrat.EN_ATTENTE;
        
        // Calculer le montant total initial
        calculerPrix();
    }

    /**
     * Calcule le prix total du contrat
     * Surcharge la méthode de Reservation pour ajouter les options spécifiques au contrat
     */
    @Override
    public void calculerPrix() {
        if (car != null && dateDebut != null && dateFin != null) {
            long nombreJours = ChronoUnit.DAYS.between(dateDebut, dateFin);
            // Assurer au moins 1 jour de location
            if (nombreJours < 1) nombreJours = 1;
            
            // Prix de base - utilise le prix par jour de la voiture
            float prixJournalier = car.getPriceday();
            float prixBase = prixJournalier * nombreJours;
            
            // Ajout du coût des options si présentes
            float coutOptions = options.size() * 5.0f; // 5€ par option
            prixBase += coutOptions;
            
            // Ajout du coût de l'assurance si présente
            if (typeAssurance != null && !typeAssurance.isEmpty()) {
                switch (typeAssurance) {
                    case "Base":
                        prixBase += 10.0f * nombreJours; // 10€ par jour pour l'assurance de base
                        break;
                    case "Premium":
                        prixBase += 25.0f * nombreJours; // 25€ par jour pour l'assurance premium
                        break;
                    case "Tous risques":
                        prixBase += 40.0f * nombreJours; // 40€ par jour pour l'assurance tous risques
                        break;
                }
            }
            
            this.price = prixBase;
        }
    }

    /**
     * Valide le contrat
     * @return true si le contrat est valide, false sinon
     */
    @Override
    public boolean validerReservation() {
        // Un contrat est valide si la réservation sous-jacente est valide
        if (car == null || client == null || dateDebut == null || dateFin == null) {
            return false;
        }
        
        // Vérifier que la période est valide
        if (dateDebut.isAfter(dateFin) || dateDebut.isEqual(dateFin)) {
            return false;
        }
        
        // Vérifications spécifiques au contrat
        // (à implémenter selon les règles métier)
        
        return true;
    }

    /**
     * Signe le contrat et le met en cours
     */
    public void signer() {
        this.estSigne = true;
        this.statutContrat = StatutContrat.EN_COURS;
        // Mettre également à jour le statut de la réservation
        this.statut = StatutReservation.CONFIRMEE;
    }

    /**
     * Termine le contrat
     */
    public void terminerContrat() {
        this.statutContrat = StatutContrat.TERMINE;
        // Mettre également à jour le statut de la réservation
        this.statut = StatutReservation.TERMINEE;
    }

    /**
     * Annule le contrat
     */
    public void annulerContrat() {
        this.statutContrat = StatutContrat.ANNULE;
        // Mettre également à jour le statut de la réservation
        this.statut = StatutReservation.ANNULEE;
    }

    /**
     * Ajoute une option au contrat
     * @param option l'option à ajouter
     */
    public void ajouterOption(String option) {
        if (!options.contains(option)) {
            options.add(option);
            calculerPrix();
        }
    }

    /**
     * Retire une option du contrat
     * @param option l'option à retirer
     */
    public void retirerOption(String option) {
        if (options.remove(option)) {
            calculerPrix();
        }
    }

    // Getters et Setters spécifiques au contrat
    
    public String getIdContrat() {
        return idContrat;
    }

    public void setIdContrat(String idContrat) {
        this.idContrat = idContrat;
    }

    public double getCaution() {
        return caution;
    }

    public void setCaution(double caution) {
        this.caution = caution;
    }

    public String getTypeAssurance() {
        return typeAssurance;
    }

    public void setTypeAssurance(String typeAssurance) {
        this.typeAssurance = typeAssurance;
        calculerPrix();
    }

    public List<String> getOptions() {
        return new ArrayList<>(options);
    }

    public void setOptions(List<String> options) {
        this.options = new ArrayList<>(options);
        calculerPrix();
    }

    public boolean isEstSigne() {
        return estSigne;
    }

    public void setEstSigne(boolean estSigne) {
        this.estSigne = estSigne;
    }
    
    public StatutContrat getStatutContrat() {
        return statutContrat;
    }
    
    public void setStatutContrat(StatutContrat statutContrat) {
        this.statutContrat = statutContrat;
    }

    /**
     * Retourne l'identifiant du contrat comme identifiant principal
     */
    @Override
    public Object getId() {
        return idContrat;
    }

    @Override
    public String toString() {
        return "Contrat{" +
                "idContrat='" + idContrat + '\'' +
                ", idReservation=" + idReservation +
                ", client=" + getClientFullName() +
                ", voiture=" + getCarFullDescription() +
                ", dateDebut=" + dateDebut +
                ", dateFin=" + dateFin +
                ", montantTotal=" + price +
                ", caution=" + caution +
                ", typeAssurance='" + typeAssurance + '\'' +
                ", options=" + options +
                ", statutContrat=" + statutContrat.getLibelle() +
                ", estSigne=" + estSigne +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        Contrat contrat = (Contrat) obj;
        return idContrat.equals(contrat.idContrat);
    }

    @Override
    public int hashCode() {
        return idContrat.hashCode();
    }
}
