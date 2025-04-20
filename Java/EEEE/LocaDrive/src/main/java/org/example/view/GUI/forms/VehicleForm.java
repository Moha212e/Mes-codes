package org.example.view.GUI.forms;

import javax.swing.*;
import java.awt.*;

/**
 * Formulaire pour l'ajout et la modification de véhicules
 */
public class VehicleForm {
    
    /**
     * Affiche le formulaire d'ajout de véhicule
     * 
     * @param parent Composant parent
     */
    public static void showAddVehicleForm(Window parent) {
        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));
        panel.add(new JLabel("Brand:"));
        JTextField brandField = new JTextField();
        panel.add(brandField);

        panel.add(new JLabel("Model:"));
        JTextField modelField = new JTextField();
        panel.add(modelField);

        panel.add(new JLabel("Year:"));
        JTextField yearField = new JTextField();
        panel.add(yearField);

        panel.add(new JLabel("Price per Day:"));
        JTextField priceField = new JTextField();
        panel.add(priceField);

        panel.add(new JLabel("Is Deleted:"));
        JCheckBox isDeletedBox = new JCheckBox();
        panel.add(isDeletedBox);

        int option = JOptionPane.showConfirmDialog(parent, panel, "Ajouter Véhicule", 
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                
        if (option == JOptionPane.OK_OPTION) {
            // Logic to add the car to the database or table
            try {
                String brand = brandField.getText();
                String model = modelField.getText();
                int year = Integer.parseInt(yearField.getText());
                double price = Double.parseDouble(priceField.getText());
                boolean isDeleted = isDeletedBox.isSelected();
                
                // Créer et ajouter le véhicule
                System.out.println("Véhicule ajouté: " + brand + " " + model);
                
                // Ici, vous pourriez ajouter le véhicule à votre modèle de données
                
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(parent, 
                    "Erreur de format: Assurez-vous que l'année et le prix sont des nombres valides.",
                    "Erreur de saisie", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    /**
     * Affiche le formulaire de modification de véhicule
     * 
     * @param parent Composant parent
     * @param carId ID du véhicule à modifier
     * @param brand Marque du véhicule
     * @param model Modèle du véhicule
     * @param year Année du véhicule
     * @param pricePerDay Prix par jour
     * @param isDeleted Indique si le véhicule est supprimé
     */
    public static void showModifyVehicleForm(Window parent, int carId, String brand, String model, 
            int year, double pricePerDay, boolean isDeleted) {
        
        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));
        
        panel.add(new JLabel("ID Car:"));
        JTextField idField = new JTextField(String.valueOf(carId));
        idField.setEditable(false);
        panel.add(idField);
        
        panel.add(new JLabel("Brand:"));
        JTextField brandField = new JTextField(brand);
        panel.add(brandField);

        panel.add(new JLabel("Model:"));
        JTextField modelField = new JTextField(model);
        panel.add(modelField);

        panel.add(new JLabel("Year:"));
        JTextField yearField = new JTextField(String.valueOf(year));
        panel.add(yearField);

        panel.add(new JLabel("Price per Day:"));
        JTextField priceField = new JTextField(String.valueOf(pricePerDay));
        panel.add(priceField);

        panel.add(new JLabel("Is Deleted:"));
        JCheckBox isDeletedBox = new JCheckBox();
        isDeletedBox.setSelected(isDeleted);
        panel.add(isDeletedBox);

        int option = JOptionPane.showConfirmDialog(parent, panel, "Modifier Véhicule", 
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                
        if (option == JOptionPane.OK_OPTION) {
            // Logic to update the car in the database or table
            try {
                String newBrand = brandField.getText();
                String newModel = modelField.getText();
                int newYear = Integer.parseInt(yearField.getText());
                double newPrice = Double.parseDouble(priceField.getText());
                boolean newIsDeleted = isDeletedBox.isSelected();
                
                // Mettre à jour le véhicule
                System.out.println("Véhicule modifié: " + newBrand + " " + newModel);
                
                // Ici, vous pourriez mettre à jour le véhicule dans votre modèle de données
                
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(parent, 
                    "Erreur de format: Assurez-vous que l'année et le prix sont des nombres valides.",
                    "Erreur de saisie", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
