package org.example.view.GUI.forms;

import javax.swing.*;
import java.awt.*;

/**
 * Formulaire pour l'ajout et la modification de locations
 */
public class LocationForm {
    
    /**
     * Affiche le formulaire d'ajout de location
     * 
     * @param parent Composant parent
     */
    public static void showAddLocationForm(Window parent) {
        JPanel panel = new JPanel(new GridLayout(8, 2, 10, 10));
        
        panel.add(new JLabel("Véhicule ID:"));
        JTextField carIdField = new JTextField();
        panel.add(carIdField);

        panel.add(new JLabel("Client ID:"));
        JTextField clientIdField = new JTextField();
        panel.add(clientIdField);

        panel.add(new JLabel("Start Date:"));
        JTextField startDateField = new JTextField();
        panel.add(startDateField);

        panel.add(new JLabel("End Date:"));
        JTextField endDateField = new JTextField();
        panel.add(endDateField);

        panel.add(new JLabel("Responsible Person:"));
        JTextField responsibleField = new JTextField();
        panel.add(responsibleField);

        panel.add(new JLabel("Total Price:"));
        JTextField priceField = new JTextField();
        panel.add(priceField);

        panel.add(new JLabel("Notes:"));
        JTextArea notesArea = new JTextArea(3, 20);
        JScrollPane notesScrollPane = new JScrollPane(notesArea);
        panel.add(notesScrollPane);

        int option = JOptionPane.showConfirmDialog(parent, panel, "Ajouter Location", 
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                
        if (option == JOptionPane.OK_OPTION) {
            // Logic to add the location to the database or table
            try {
                int carId = Integer.parseInt(carIdField.getText());
                int clientId = Integer.parseInt(clientIdField.getText());
                String startDate = startDateField.getText();
                String endDate = endDateField.getText();
                String responsible = responsibleField.getText();
                double price = Double.parseDouble(priceField.getText());
                String notes = notesArea.getText();
                
                // Vérifier que les champs obligatoires sont remplis
                if (startDate.isEmpty() || endDate.isEmpty()) {
                    JOptionPane.showMessageDialog(parent, 
                        "Veuillez remplir les dates de début et de fin.",
                        "Champs manquants", 
                        JOptionPane.WARNING_MESSAGE);
                    return;
                }
                
                // Créer et ajouter la location
                System.out.println("Location ajoutée pour le véhicule ID: " + carId);
                
                // Ici, vous pourriez ajouter la location à votre modèle de données
                
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(parent, 
                    "Erreur de format: Assurez-vous que les IDs et le prix sont des nombres valides.",
                    "Erreur de saisie", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    /**
     * Affiche le formulaire de modification de location
     * 
     * @param parent Composant parent
     * @param reservationId ID de la réservation à modifier
     * @param carId ID du véhicule
     * @param clientId ID du client
     * @param startDate Date de début
     * @param endDate Date de fin
     * @param responsible Responsable
     * @param totalPrice Prix total
     * @param notes Notes
     */
    public static void showModifyLocationForm(Window parent, int reservationId, int carId, int clientId, 
            String startDate, String endDate, String responsible, double totalPrice, String notes) {
        
        JPanel panel = new JPanel(new GridLayout(9, 2, 10, 10));
        
        panel.add(new JLabel("ID Reservation:"));
        JTextField idField = new JTextField(String.valueOf(reservationId));
        idField.setEditable(false);
        panel.add(idField);
        
        panel.add(new JLabel("Véhicule ID:"));
        JTextField carIdField = new JTextField(String.valueOf(carId));
        panel.add(carIdField);

        panel.add(new JLabel("Client ID:"));
        JTextField clientIdField = new JTextField(String.valueOf(clientId));
        panel.add(clientIdField);

        panel.add(new JLabel("Start Date:"));
        JTextField startDateField = new JTextField(startDate);
        panel.add(startDateField);

        panel.add(new JLabel("End Date:"));
        JTextField endDateField = new JTextField(endDate);
        panel.add(endDateField);

        panel.add(new JLabel("Responsible Person:"));
        JTextField responsibleField = new JTextField(responsible);
        panel.add(responsibleField);

        panel.add(new JLabel("Total Price:"));
        JTextField priceField = new JTextField(String.valueOf(totalPrice));
        panel.add(priceField);

        panel.add(new JLabel("Notes:"));
        JTextArea notesArea = new JTextArea(notes, 3, 20);
        JScrollPane notesScrollPane = new JScrollPane(notesArea);
        panel.add(notesScrollPane);

        int option = JOptionPane.showConfirmDialog(parent, panel, "Modifier Location", 
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                
        if (option == JOptionPane.OK_OPTION) {
            // Logic to update the location in the database or table
            try {
                int newCarId = Integer.parseInt(carIdField.getText());
                int newClientId = Integer.parseInt(clientIdField.getText());
                String newStartDate = startDateField.getText();
                String newEndDate = endDateField.getText();
                String newResponsible = responsibleField.getText();
                double newPrice = Double.parseDouble(priceField.getText());
                String newNotes = notesArea.getText();
                
                // Vérifier que les champs obligatoires sont remplis
                if (newStartDate.isEmpty() || newEndDate.isEmpty()) {
                    JOptionPane.showMessageDialog(parent, 
                        "Veuillez remplir les dates de début et de fin.",
                        "Champs manquants", 
                        JOptionPane.WARNING_MESSAGE);
                    return;
                }
                
                // Mettre à jour la location
                System.out.println("Location modifiée: ID " + reservationId);
                
                // Ici, vous pourriez mettre à jour la location dans votre modèle de données
                
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(parent, 
                    "Erreur de format: Assurez-vous que les IDs et le prix sont des nombres valides.",
                    "Erreur de saisie", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
