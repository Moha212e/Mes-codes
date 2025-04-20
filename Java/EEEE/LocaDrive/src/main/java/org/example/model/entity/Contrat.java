package org.example.model.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe représentant un contrat de location
 */
public class Contrat {
    private int idContrat;
    private double caution;
    private String typeAssurance;
    private boolean estSigne;
    private List<String> options;
    private String statutContrat;

    /**
     * Constructeur par défaut
     */
    public Contrat() {
        this.options = new ArrayList<>();
    }

    /**
     * Constructeur avec paramètres
     * 
     * @param idContrat     Identifiant unique du contrat
     * @param caution       Montant de la caution
     * @param typeAssurance Type d'assurance choisi
     * @param estSigne      Indique si le contrat est signé
     * @param options       Liste des options choisies
     * @param statutContrat Statut actuel du contrat
     */
    public Contrat(int idContrat, double caution, String typeAssurance, boolean estSigne, List<String> options, String statutContrat) {
        this.idContrat = idContrat;
        this.caution = caution;
        this.typeAssurance = typeAssurance;
        this.estSigne = estSigne;
        this.options = options != null ? options : new ArrayList<>();
        this.statutContrat = statutContrat;
    }

    /**
     * @return l'identifiant du contrat
     */
    public int getIdContrat() {
        return idContrat;
    }

    /**
     * @param idContrat l'identifiant du contrat à définir
     */
    public void setIdContrat(int idContrat) {
        this.idContrat = idContrat;
    }

    /**
     * @return le montant de la caution
     */
    public double getCaution() {
        return caution;
    }

    /**
     * @param caution le montant de la caution à définir
     */
    public void setCaution(double caution) {
        this.caution = caution;
    }

    /**
     * @return le type d'assurance
     */
    public String getTypeAssurance() {
        return typeAssurance;
    }

    /**
     * @param typeAssurance le type d'assurance à définir
     */
    public void setTypeAssurance(String typeAssurance) {
        this.typeAssurance = typeAssurance;
    }

    /**
     * @return true si le contrat est signé, false sinon
     */
    public boolean isEstSigne() {
        return estSigne;
    }

    /**
     * @param estSigne définit si le contrat est signé
     */
    public void setEstSigne(boolean estSigne) {
        this.estSigne = estSigne;
    }

    /**
     * @return la liste des options
     */
    public List<String> getOptions() {
        return options;
    }

    /**
     * @param options la liste des options à définir
     */
    public void setOptions(List<String> options) {
        this.options = options != null ? options : new ArrayList<>();
    }
    
    /**
     * Ajoute une option à la liste
     * 
     * @param option l'option à ajouter
     */
    public void ajouterOption(String option) {
        if (option != null && !option.isEmpty()) {
            this.options.add(option);
        }
    }
    
    /**
     * Supprime une option de la liste
     * 
     * @param option l'option à supprimer
     * @return true si l'option a été supprimée, false sinon
     */
    public boolean supprimerOption(String option) {
        return this.options.remove(option);
    }

    /**
     * @return le statut du contrat
     */
    public String getStatutContrat() {
        return statutContrat;
    }

    /**
     * @param statutContrat le statut du contrat à définir
     */
    public void setStatutContrat(String statutContrat) {
        this.statutContrat = statutContrat;
    }

    @Override
    public String toString() {
        return "Contrat{" +
                "idContrat=" + idContrat +
                ", caution=" + caution +
                ", typeAssurance='" + typeAssurance + '\'' +
                ", estSigne=" + estSigne +
                ", options=" + options +
                ", statutContrat='" + statutContrat + '\'' +
                '}';
    }
}
