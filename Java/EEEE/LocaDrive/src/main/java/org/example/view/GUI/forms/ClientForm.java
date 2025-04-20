package org.example.view.GUI.forms;

import javax.swing.*;
import java.awt.*;

/**
 * Formulaire pour l'ajout et la modification de clients
 */
public class ClientForm {
    
    /**
     * Affiche le formulaire d'ajout de client
     * 
     * @param parent Composant parent
     */
    public static void showAddClientForm(Window parent) {
        JPanel panel = new JPanel(new GridLayout(7, 2, 10, 10));
        
        panel.add(new JLabel("Name:"));
        JTextField nameField = new JTextField();
        panel.add(nameField);

        panel.add(new JLabel("Surname:"));
        JTextField surnameField = new JTextField();
        panel.add(surnameField);

        panel.add(new JLabel("Email:"));
        JTextField emailField = new JTextField();
        panel.add(emailField);

        panel.add(new JLabel("Password:"));
        JPasswordField passwordField = new JPasswordField();
        panel.add(passwordField);

        panel.add(new JLabel("Birthdate:"));
        JTextField birthdateField = new JTextField();
        panel.add(birthdateField);

        panel.add(new JLabel("Is Deleted:"));
        JCheckBox isDeletedBox = new JCheckBox();
        panel.add(isDeletedBox);

        int option = JOptionPane.showConfirmDialog(parent, panel, "Ajouter Client", 
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                
        if (option == JOptionPane.OK_OPTION) {
            // Logic to add the client to the database or table
            String name = nameField.getText();
            String surname = surnameField.getText();
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());
            String birthdate = birthdateField.getText();
            boolean isDeleted = isDeletedBox.isSelected();
            
            // Vérifier que les champs obligatoires sont remplis
            if (name.isEmpty() || surname.isEmpty() || email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(parent, 
                    "Veuillez remplir tous les champs obligatoires.",
                    "Champs manquants", 
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            // Créer et ajouter le client
            System.out.println("Client ajouté: " + name + " " + surname);
            
            // Ici, vous pourriez ajouter le client à votre modèle de données
        }
    }
    
    /**
     * Affiche le formulaire de modification de client
     * 
     * @param parent Composant parent
     * @param clientId ID du client à modifier
     * @param name Nom du client
     * @param surname Prénom du client
     * @param email Email du client
     * @param birthdate Date de naissance du client
     * @param isDeleted Indique si le client est supprimé
     */
    public static void showModifyClientForm(Window parent, int clientId, String name, String surname, 
            String email, String birthdate, boolean isDeleted) {
        
        JPanel panel = new JPanel(new GridLayout(7, 2, 10, 10));
        
        panel.add(new JLabel("ID Client:"));
        JTextField idField = new JTextField(String.valueOf(clientId));
        idField.setEditable(false);
        panel.add(idField);
        
        panel.add(new JLabel("Name:"));
        JTextField nameField = new JTextField(name);
        panel.add(nameField);

        panel.add(new JLabel("Surname:"));
        JTextField surnameField = new JTextField(surname);
        panel.add(surnameField);

        panel.add(new JLabel("Email:"));
        JTextField emailField = new JTextField(email);
        panel.add(emailField);

        panel.add(new JLabel("Password:"));
        JPasswordField passwordField = new JPasswordField();
        panel.add(passwordField);

        panel.add(new JLabel("Birthdate:"));
        JTextField birthdateField = new JTextField(birthdate);
        panel.add(birthdateField);

        panel.add(new JLabel("Is Deleted:"));
        JCheckBox isDeletedBox = new JCheckBox();
        isDeletedBox.setSelected(isDeleted);
        panel.add(isDeletedBox);

        int option = JOptionPane.showConfirmDialog(parent, panel, "Modifier Client", 
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                
        if (option == JOptionPane.OK_OPTION) {
            // Logic to update the client in the database or table
            String newName = nameField.getText();
            String newSurname = surnameField.getText();
            String newEmail = emailField.getText();
            String newPassword = new String(passwordField.getPassword());
            String newBirthdate = birthdateField.getText();
            boolean newIsDeleted = isDeletedBox.isSelected();
            
            // Vérifier que les champs obligatoires sont remplis
            if (newName.isEmpty() || newSurname.isEmpty() || newEmail.isEmpty()) {
                JOptionPane.showMessageDialog(parent, 
                    "Veuillez remplir tous les champs obligatoires.",
                    "Champs manquants", 
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            // Mettre à jour le client
            System.out.println("Client modifié: " + newName + " " + newSurname);
            
            // Ici, vous pourriez mettre à jour le client dans votre modèle de données
        }
    }
}
