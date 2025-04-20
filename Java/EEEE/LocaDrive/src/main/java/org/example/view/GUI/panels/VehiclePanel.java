package org.example.view.GUI.panels;

import org.example.view.GUI.forms.VehicleForm;

import javax.swing.*;
import java.awt.*;

/**
 * Panneau pour l'affichage et la gestion des véhicules
 */
public class VehiclePanel extends JPanel {
    private JTable vehicleTable;
    private JButton addButton, modifyButton, deleteButton;
    private int tailleBorder = 30;
    private int posBouton = 0;

    /**
     * Constructeur du panneau véhicules
     */
    public VehiclePanel() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(tailleBorder, tailleBorder, tailleBorder, tailleBorder));
        
        // Création de la table
        String[] carColumns = {"ID Car", "Brand", "Model", "Year", "Price per Day", "Is Deleted"};
        Object[][] carData = {};  // Les données seront ajoutées dynamiquement
        vehicleTable = new JTable(carData, carColumns);
        JScrollPane scrollPane = new JScrollPane(vehicleTable);
        
        // Création du panneau de boutons
        JPanel buttonPanel = new JPanel(new FlowLayout(posBouton));
        addButton = new JButton("Ajouter");
        addButton.setBackground(Color.GREEN);
        addButton.addActionListener(e -> showAddVehicleForm());
        
        modifyButton = new JButton("Modifier");
        modifyButton.setBackground(Color.WHITE);
        modifyButton.addActionListener(e -> showModifyVehicleForm());
        
        deleteButton = new JButton("Supprimer");
        deleteButton.setBackground(Color.RED);
        deleteButton.addActionListener(e -> showDeleteVehicleForm());
        
        buttonPanel.add(addButton);
        buttonPanel.add(modifyButton);
        buttonPanel.add(deleteButton);
        
        // Ajout des composants au panneau
        add(buttonPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }
    
    /**
     * Affiche le formulaire d'ajout de véhicule
     */
    private void showAddVehicleForm() {
        VehicleForm.showAddVehicleForm(SwingUtilities.getWindowAncestor(this));
    }
    
    /**
     * Affiche le formulaire de modification de véhicule
     */
    private void showModifyVehicleForm() {
        int selectedRow = vehicleTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, 
                "Veuillez sélectionner un véhicule à modifier.",
                "Aucune sélection", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Récupérer les données du véhicule sélectionné
        // et les passer au formulaire de modification
        JOptionPane.showMessageDialog(this, 
            "Fonctionnalité de modification de véhicule à implémenter.",
            "Développement en cours", 
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Affiche le formulaire de suppression de véhicule
     */
    private void showDeleteVehicleForm() {
        int selectedRow = vehicleTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, 
                "Veuillez sélectionner un véhicule à supprimer.",
                "Aucune sélection", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this,
            "Êtes-vous sûr de vouloir supprimer ce véhicule ?",
            "Confirmation de suppression",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);
            
        if (confirm == JOptionPane.YES_OPTION) {
            // Supprimer le véhicule
            System.out.println("Véhicule supprimé à l'index: " + selectedRow);
        }
    }
    
    /**
     * Met à jour les données de la table
     */
    public void refreshData() {
        // Mettre à jour les données de la table
        // À implémenter avec un modèle de table
    }
}
