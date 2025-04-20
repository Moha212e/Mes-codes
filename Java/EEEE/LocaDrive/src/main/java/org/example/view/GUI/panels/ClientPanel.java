package org.example.view.GUI.panels;

import org.example.view.GUI.forms.ClientForm;

import javax.swing.*;
import java.awt.*;

/**
 * Panneau pour l'affichage et la gestion des clients
 */
public class ClientPanel extends JPanel {
    private JTable clientTable;
    private JButton addButton, modifyButton, deleteButton;
    private int tailleBorder = 30;
    private int posBouton = 0;

    /**
     * Constructeur du panneau clients
     */
    public ClientPanel() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(tailleBorder, tailleBorder, tailleBorder, tailleBorder));
        
        // Création de la table
        String[] clientColumns = {"ID Client", "Name", "Surname", "Email", "Password", "Birthdate", "Is Deleted"};
        Object[][] clientData = {};  // Les données seront ajoutées dynamiquement
        clientTable = new JTable(clientData, clientColumns);
        JScrollPane scrollPane = new JScrollPane(clientTable);
        
        // Création du panneau de boutons
        JPanel buttonPanel = new JPanel(new FlowLayout(posBouton));
        addButton = new JButton("Ajouter");
        addButton.setBackground(Color.GREEN);
        addButton.addActionListener(e -> showAddClientForm());
        
        modifyButton = new JButton("Modifier");
        modifyButton.setBackground(Color.WHITE);
        modifyButton.addActionListener(e -> showModifyClientForm());
        
        deleteButton = new JButton("Supprimer");
        deleteButton.setBackground(Color.RED);
        deleteButton.addActionListener(e -> showDeleteClientForm());
        
        buttonPanel.add(addButton);
        buttonPanel.add(modifyButton);
        buttonPanel.add(deleteButton);
        
        // Ajout des composants au panneau
        add(buttonPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }
    
    /**
     * Affiche le formulaire d'ajout de client
     */
    private void showAddClientForm() {
        ClientForm.showAddClientForm(SwingUtilities.getWindowAncestor(this));
    }
    
    /**
     * Affiche le formulaire de modification de client
     */
    private void showModifyClientForm() {
        int selectedRow = clientTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, 
                "Veuillez sélectionner un client à modifier.",
                "Aucune sélection", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Récupérer les données du client sélectionné
        // et les passer au formulaire de modification
        JOptionPane.showMessageDialog(this, 
            "Fonctionnalité de modification de client à implémenter.",
            "Développement en cours", 
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Affiche le formulaire de suppression de client
     */
    private void showDeleteClientForm() {
        int selectedRow = clientTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, 
                "Veuillez sélectionner un client à supprimer.",
                "Aucune sélection", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this,
            "Êtes-vous sûr de vouloir supprimer ce client ?",
            "Confirmation de suppression",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);
            
        if (confirm == JOptionPane.YES_OPTION) {
            // Supprimer le client
            System.out.println("Client supprimé à l'index: " + selectedRow);
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
