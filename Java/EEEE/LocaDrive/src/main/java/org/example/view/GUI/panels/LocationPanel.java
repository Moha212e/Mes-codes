package org.example.view.GUI.panels;

import org.example.view.GUI.forms.LocationForm;

import javax.swing.*;
import java.awt.*;

/**
 * Panneau pour l'affichage et la gestion des locations
 */
public class LocationPanel extends JPanel {
    private JTable locationTable;
    private JButton addButton, modifyButton, deleteButton;
    private int tailleBorder = 30;
    private int posBouton = 0;

    /**
     * Constructeur du panneau locations
     */
    public LocationPanel() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(tailleBorder, tailleBorder, tailleBorder, tailleBorder));
        
        // Création de la table
        String[] locationColumns = {"ID Reservation", "Véhicule", "Loueur", "Date Début", "Date Fin", "Responsable", "Prix Total", "Notes"};
        Object[][] locationData = {};  // Les données seront ajoutées dynamiquement
        locationTable = new JTable(locationData, locationColumns);
        JScrollPane scrollPane = new JScrollPane(locationTable);
        
        // Création du panneau de boutons
        JPanel buttonPanel = new JPanel(new FlowLayout(posBouton));
        addButton = new JButton("Ajouter");
        addButton.setBackground(Color.GREEN);
        addButton.addActionListener(e -> showAddLocationForm());
        
        modifyButton = new JButton("Modifier");
        modifyButton.setBackground(Color.WHITE);
        modifyButton.addActionListener(e -> showModifyLocationForm());
        
        deleteButton = new JButton("Supprimer");
        deleteButton.setBackground(Color.RED);
        deleteButton.addActionListener(e -> showDeleteLocationForm());
        
        buttonPanel.add(addButton);
        buttonPanel.add(modifyButton);
        buttonPanel.add(deleteButton);
        
        // Ajout des composants au panneau
        add(buttonPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }
    
    /**
     * Affiche le formulaire d'ajout de location
     */
    public void showAddLocationForm() {
        LocationForm.showAddLocationForm(SwingUtilities.getWindowAncestor(this));
    }
    
    /**
     * Affiche le formulaire de modification de location
     */
    public void showModifyLocationForm() {
        int selectedRow = locationTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, 
                "Veuillez sélectionner une location à modifier.",
                "Aucune sélection", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Récupérer les données de la location sélectionnée
        // et les passer au formulaire de modification
        JOptionPane.showMessageDialog(this, 
            "Fonctionnalité de modification de location à implémenter.",
            "Développement en cours", 
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Affiche le formulaire de suppression de location
     */
    public void showDeleteLocationForm() {
        int selectedRow = locationTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, 
                "Veuillez sélectionner une location à supprimer.",
                "Aucune sélection", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this,
            "Êtes-vous sûr de vouloir supprimer cette location ?",
            "Confirmation de suppression",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);
            
        if (confirm == JOptionPane.YES_OPTION) {
            // Supprimer la location
            System.out.println("Location supprimée à l'index: " + selectedRow);
        }
    }
    
    /**
     * Filtre les locations pour un client spécifique
     * 
     * @param clientId ID du client
     */
    public void filterByClient(int clientId) {
        // Filtrer les locations pour afficher uniquement celles du client
        System.out.println("Filtrage des locations pour le client ID: " + clientId);
        // À implémenter avec un modèle de table filtrable
    }
    
    /**
     * Met à jour les données de la table
     */
    public void refreshData() {
        // Mettre à jour les données de la table
        // À implémenter avec un modèle de table
    }
}
