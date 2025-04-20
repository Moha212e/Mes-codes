package org.example.view.GUI.forms;

import org.example.model.entity.Contrat;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Formulaire pour l'ajout et la modification de contrats
 */
public class ContratForm {
    
    /**
     * Affiche le formulaire d'ajout de contrat
     * 
     * @param parent Composant parent
     */
    public static void showAddContratForm(Window parent) {
        JPanel panel = new JPanel(new GridLayout(7, 2, 10, 10));
        
        panel.add(new JLabel("ID Contrat:"));
        JTextField idContratField = new JTextField();
        panel.add(idContratField);
        
        panel.add(new JLabel("Caution (€):"));
        JTextField cautionField = new JTextField();
        panel.add(cautionField);
        
        panel.add(new JLabel("Type Assurance:"));
        String[] assuranceTypes = {"Basique", "Intermédiaire", "Premium", "Tous Risques"};
        JComboBox<String> assuranceCombo = new JComboBox<>(assuranceTypes);
        panel.add(assuranceCombo);
        
        panel.add(new JLabel("Est Signé:"));
        JCheckBox estSigneBox = new JCheckBox();
        panel.add(estSigneBox);
        
        panel.add(new JLabel("Options:"));
        JPanel optionsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JCheckBox gpsBox = new JCheckBox("GPS");
        JCheckBox siegeEnfantBox = new JCheckBox("Siège Enfant");
        JCheckBox chainesBox = new JCheckBox("Chaînes Neige");
        optionsPanel.add(gpsBox);
        optionsPanel.add(siegeEnfantBox);
        optionsPanel.add(chainesBox);
        JScrollPane optionsScrollPane = new JScrollPane(optionsPanel);
        panel.add(optionsScrollPane);
        
        panel.add(new JLabel("Statut Contrat:"));
        String[] statutTypes = {"En attente", "Validé", "En cours", "Terminé", "Annulé"};
        JComboBox<String> statutCombo = new JComboBox<>(statutTypes);
        panel.add(statutCombo);
        
        int option = JOptionPane.showConfirmDialog(parent, panel, "Ajouter Contrat", 
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                
        if (option == JOptionPane.OK_OPTION) {
            // Récupérer les options sélectionnées
            List<String> optionsSelected = new ArrayList<>();
            if (gpsBox.isSelected()) optionsSelected.add("GPS");
            if (siegeEnfantBox.isSelected()) optionsSelected.add("Siège Enfant");
            if (chainesBox.isSelected()) optionsSelected.add("Chaînes Neige");
            
            // Créer un nouveau contrat
            try {
                int idContrat = Integer.parseInt(idContratField.getText());
                double caution = Double.parseDouble(cautionField.getText());
                String typeAssurance = (String) assuranceCombo.getSelectedItem();
                boolean estSigne = estSigneBox.isSelected();
                String statut = (String) statutCombo.getSelectedItem();
                
                Contrat contrat = new Contrat(idContrat, caution, typeAssurance, estSigne, optionsSelected, statut);
                
                // Ajouter à la table (à implémenter avec un modèle de table)
                System.out.println("Contrat ajouté: " + contrat);
                
                // Ici, vous pourriez ajouter le contrat à votre modèle de données
                // et mettre à jour la table
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(parent, 
                    "Erreur de format: Assurez-vous que l'ID et la caution sont des nombres valides.",
                    "Erreur de saisie", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    /**
     * Affiche le formulaire de modification de contrat
     * 
     * @param parent Composant parent
     * @param contrat Contrat à modifier
     */
    public static void showModifyContratForm(Window parent, Contrat contrat) {
        JPanel panel = new JPanel(new GridLayout(7, 2, 10, 10));
        
        panel.add(new JLabel("ID Contrat:"));
        JTextField idContratField = new JTextField(String.valueOf(contrat.getIdContrat()));
        idContratField.setEditable(false);
        panel.add(idContratField);
        
        panel.add(new JLabel("Caution (€):"));
        JTextField cautionField = new JTextField(String.valueOf(contrat.getCaution()));
        panel.add(cautionField);
        
        panel.add(new JLabel("Type Assurance:"));
        String[] assuranceTypes = {"Basique", "Intermédiaire", "Premium", "Tous Risques"};
        JComboBox<String> assuranceCombo = new JComboBox<>(assuranceTypes);
        // Sélectionner le type d'assurance actuel
        for (int i = 0; i < assuranceTypes.length; i++) {
            if (assuranceTypes[i].equals(contrat.getTypeAssurance())) {
                assuranceCombo.setSelectedIndex(i);
                break;
            }
        }
        panel.add(assuranceCombo);
        
        panel.add(new JLabel("Est Signé:"));
        JCheckBox estSigneBox = new JCheckBox();
        estSigneBox.setSelected(contrat.isEstSigne());
        panel.add(estSigneBox);
        
        panel.add(new JLabel("Options:"));
        JPanel optionsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JCheckBox gpsBox = new JCheckBox("GPS");
        JCheckBox siegeEnfantBox = new JCheckBox("Siège Enfant");
        JCheckBox chainesBox = new JCheckBox("Chaînes Neige");
        
        // Cocher les options déjà sélectionnées
        List<String> options = contrat.getOptions();
        if (options.contains("GPS")) gpsBox.setSelected(true);
        if (options.contains("Siège Enfant")) siegeEnfantBox.setSelected(true);
        if (options.contains("Chaînes Neige")) chainesBox.setSelected(true);
        
        optionsPanel.add(gpsBox);
        optionsPanel.add(siegeEnfantBox);
        optionsPanel.add(chainesBox);
        JScrollPane optionsScrollPane = new JScrollPane(optionsPanel);
        panel.add(optionsScrollPane);
        
        panel.add(new JLabel("Statut Contrat:"));
        String[] statutTypes = {"En attente", "Validé", "En cours", "Terminé", "Annulé"};
        JComboBox<String> statutCombo = new JComboBox<>(statutTypes);
        // Sélectionner le statut actuel
        for (int i = 0; i < statutTypes.length; i++) {
            if (statutTypes[i].equals(contrat.getStatutContrat())) {
                statutCombo.setSelectedIndex(i);
                break;
            }
        }
        panel.add(statutCombo);
        
        int option = JOptionPane.showConfirmDialog(parent, panel, "Modifier Contrat", 
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                
        if (option == JOptionPane.OK_OPTION) {
            // Récupérer les options sélectionnées
            List<String> optionsSelected = new ArrayList<>();
            if (gpsBox.isSelected()) optionsSelected.add("GPS");
            if (siegeEnfantBox.isSelected()) optionsSelected.add("Siège Enfant");
            if (chainesBox.isSelected()) optionsSelected.add("Chaînes Neige");
            
            // Mettre à jour le contrat
            try {
                double caution = Double.parseDouble(cautionField.getText());
                String typeAssurance = (String) assuranceCombo.getSelectedItem();
                boolean estSigne = estSigneBox.isSelected();
                String statut = (String) statutCombo.getSelectedItem();
                
                // Mettre à jour les propriétés du contrat
                contrat.setCaution(caution);
                contrat.setTypeAssurance(typeAssurance);
                contrat.setEstSigne(estSigne);
                contrat.setOptions(optionsSelected);
                contrat.setStatutContrat(statut);
                
                // Mettre à jour dans la base de données ou la table
                System.out.println("Contrat modifié: " + contrat);
                
                // Ici, vous pourriez mettre à jour le contrat dans votre modèle de données
                
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(parent, 
                    "Erreur de format: Assurez-vous que la caution est un nombre valide.",
                    "Erreur de saisie", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
