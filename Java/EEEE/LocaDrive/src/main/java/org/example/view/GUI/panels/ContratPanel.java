package org.example.view.GUI.panels;

import org.example.view.GUI.forms.ContratForm;

import javax.swing.*;
import java.awt.*;

/**
 * Panneau pour l'affichage et la gestion des contrats
 */
public class ContratPanel extends JPanel {
    private JTable contratTable;
    private JButton addButton, modifyButton, deleteButton;
    private int tailleBorder = 30;
    private int posBouton = 0;

    /**
     * Constructeur du panneau contrats
     */
    public ContratPanel() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(tailleBorder, tailleBorder, tailleBorder, tailleBorder));
        
        // Création de la table
        String[] contratColumns = {"ID Contrat", "Caution", "Type Assurance", "Est Signé", "Options", "Statut"};
        Object[][] contratData = {};  // Les données seront ajoutées dynamiquement
        contratTable = new JTable(contratData, contratColumns);
        JScrollPane scrollPane = new JScrollPane(contratTable);
        
        // Création du panneau de boutons
        JPanel buttonPanel = new JPanel(new FlowLayout(posBouton));
        addButton = new JButton("Ajouter");
        addButton.setBackground(Color.GREEN);
        addButton.addActionListener(e -> showAddContratForm());
        
        modifyButton = new JButton("Modifier");
        modifyButton.setBackground(Color.WHITE);
        modifyButton.addActionListener(e -> showModifyContratForm());
        
        deleteButton = new JButton("Supprimer");
        deleteButton.setBackground(Color.RED);
        deleteButton.addActionListener(e -> showDeleteContratForm());
        
        buttonPanel.add(addButton);
        buttonPanel.add(modifyButton);
        buttonPanel.add(deleteButton);
        
        // Ajout des composants au panneau
        add(buttonPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }
    
    /**
     * Affiche le formulaire d'ajout de contrat
     */
    private void showAddContratForm() {
        ContratForm.showAddContratForm(SwingUtilities.getWindowAncestor(this));
    }
    
    /**
     * Affiche le formulaire de modification de contrat
     */
    private void showModifyContratForm() {
        int selectedRow = contratTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, 
                "Veuillez sélectionner un contrat à modifier.",
                "Aucune sélection", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Récupérer les données du contrat sélectionné
        // et les passer au formulaire de modification
        JOptionPane.showMessageDialog(this, 
            "Fonctionnalité de modification de contrat à implémenter.",
            "Développement en cours", 
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Affiche le formulaire de suppression de contrat
     */
    private void showDeleteContratForm() {
        int selectedRow = contratTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, 
                "Veuillez sélectionner un contrat à supprimer.",
                "Aucune sélection", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this,
            "Êtes-vous sûr de vouloir supprimer ce contrat ?",
            "Confirmation de suppression",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);
            
        if (confirm == JOptionPane.YES_OPTION) {
            // Supprimer le contrat
            System.out.println("Contrat supprimé à l'index: " + selectedRow);
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
