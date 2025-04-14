package org.example.view.GUI;
import org.example.model.authentication.*;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class JFramesLocation extends JFrame {
    private JTabbedPane tabbedPane;
    private JPanel panel1, panel2, panel3, panel4;
    private JTable table1, table2, table3;
    private JButton buttonAddCar, buttonModifyCar, buttonDeleteCar;
    private JButton buttonAddClient, buttonModifyClient, buttonDeleteClient;
    private JButton buttonAddLocation, buttonModifyLocation, buttonDeleteLocation;
    private JLabel labelImage;
    private int tailleBorder = 30;
    private int posBouton = 0;


    public JFramesLocation() {
        super("LocaDrive");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Bouton session
        JButton sessionButton = new JButton("Session");
        sessionButton.addActionListener(e -> showSessionDialog());
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        topPanel.add(sessionButton);
        add(topPanel, BorderLayout.NORTH);

        // Création du JTabbedPane
        tabbedPane = new JTabbedPane();

        // Onglet 1: Lister véhicules
        panel1 = new JPanel(new BorderLayout());
        panel1.setBorder(BorderFactory.createEmptyBorder(tailleBorder, tailleBorder, tailleBorder, tailleBorder));
        String[] carColumns = {"ID Car", "Brand", "Model", "Year", "Price per Day", "Is Deleted"};
        Object[][] carData = {};  // Les données seront ajoutées dynamiquement
        table1 = new JTable(carData, carColumns);
        JScrollPane scrollPane1 = new JScrollPane(table1);

        // Panel des boutons pour l'onglet 1
        JPanel buttonPanel1 = new JPanel(new FlowLayout(posBouton));
        buttonAddCar = new JButton("Ajouter");
        buttonAddCar.setBackground(Color.GREEN);
        buttonAddCar.addActionListener(e -> showAddCarForm()); // Ouvrir le formulaire d'ajout de véhicule
        buttonModifyCar = new JButton("Modifier");
        buttonModifyCar.setBackground(Color.WHITE);
        buttonDeleteCar = new JButton("Supprimer");
        buttonDeleteCar.setBackground(Color.RED);
        buttonPanel1.add(buttonAddCar);
        buttonPanel1.add(buttonModifyCar);
        buttonPanel1.add(buttonDeleteCar);

        panel1.add(buttonPanel1, BorderLayout.NORTH);
        panel1.add(scrollPane1, BorderLayout.CENTER);

        // Onglet 2: Lister clients
        panel2 = new JPanel(new BorderLayout());
        panel2.setBorder(BorderFactory.createEmptyBorder(tailleBorder, tailleBorder, tailleBorder, tailleBorder));
        String[] clientColumns = {"ID Client", "Name", "Surname", "Email", "Password", "Birthdate", "Is Deleted"};
        Object[][] clientData = {};  // Les données seront ajoutées dynamiquement
        table2 = new JTable(clientData, clientColumns);
        JScrollPane scrollPane2 = new JScrollPane(table2);

        // Panel des boutons pour l'onglet 2
        JPanel buttonPanel2 = new JPanel(new FlowLayout(posBouton));
        buttonAddClient = new JButton("Ajouter");
        buttonAddClient.setBackground(Color.GREEN);
        buttonAddClient.addActionListener(e -> showAddClientForm()); // Ouvrir le formulaire d'ajout de client
        buttonModifyClient = new JButton("Modifier");
        buttonModifyClient.setBackground(Color.WHITE);
        buttonDeleteClient = new JButton("Supprimer");
        buttonDeleteClient.setBackground(Color.RED);
        buttonPanel2.add(buttonAddClient);
        buttonPanel2.add(buttonModifyClient);
        buttonPanel2.add(buttonDeleteClient);

        panel2.add(buttonPanel2, BorderLayout.NORTH);
        panel2.add(scrollPane2, BorderLayout.CENTER);

        // Onglet 3: Lister réservations
        panel3 = new JPanel(new BorderLayout());
        panel3.setBorder(BorderFactory.createEmptyBorder(tailleBorder, tailleBorder, tailleBorder, tailleBorder));
        String[] locationColumns = {"ID Reservation", "Véhicule", "Loueur", "Date Début", "Date Fin", "Responsable", "Prix Total", "Notes"};
        Object[][] locationData = {};  // Les données seront ajoutées dynamiquement
        table3 = new JTable(locationData, locationColumns);
        JScrollPane scrollPane3 = new JScrollPane(table3);

        // Panel des boutons pour l'onglet 3
        JPanel buttonPanel3 = new JPanel(new FlowLayout(posBouton));
        buttonAddLocation = new JButton("Ajouter");
        buttonAddLocation.setBackground(Color.GREEN);
        buttonAddLocation.addActionListener(e -> showAddLocationForm()); // Ouvrir le formulaire d'ajout de location
        buttonModifyLocation = new JButton("Modifier");
        buttonModifyLocation.setBackground(Color.WHITE);
        buttonDeleteLocation = new JButton("Supprimer");
        buttonDeleteLocation.setBackground(Color.RED);
        buttonPanel3.add(buttonAddLocation);
        buttonPanel3.add(buttonModifyLocation);
        buttonPanel3.add(buttonDeleteLocation);

        panel3.add(buttonPanel3, BorderLayout.NORTH);
        panel3.add(scrollPane3, BorderLayout.CENTER);

        // Onglet 4: Afficher images véhicules
        panel4 = new JPanel(new GridLayout(2, 3, 10, 10));
        panel4.setBorder(BorderFactory.createEmptyBorder(tailleBorder, tailleBorder, tailleBorder, tailleBorder));

        // Définir les images des véhicules
        String[] carImages = {
                "aston.jpg", "coccinelle.jpg", "labo.jpg","mustang.jpg","nissan.jpg","toyota.jpg"
        };
        String[] plateNumbers = {
                "AB123CD", "EF456GH", "IJ789KL", "MN012OP", "QR345ST","QR345ST"
        };

        // Ajouter les images avec les numéros d'immatriculation
        for (int i = 0; i < carImages.length; i++) {
            JPanel carPanel = new JPanel(new BorderLayout());
            carPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

            // Charger l'image
            try {
                String imagePath = "/images/" + carImages[i];
                InputStream is = getClass().getResourceAsStream(imagePath);

                if (is == null) {
                    System.err.println("⚠️ Image non trouvée : " + imagePath);
                    continue;
                }

                BufferedImage img = ImageIO.read(is);
                ImageIcon imageIcon = new ImageIcon(img.getScaledInstance(200, 150, Image.SCALE_SMOOTH));
                JLabel imageLabel = new JLabel(imageIcon);
                carPanel.add(imageLabel, BorderLayout.CENTER);

                // Ajouter le numéro d'immatriculation
                JLabel plateLabel = new JLabel("Immat: " + plateNumbers[i], SwingConstants.CENTER);
                carPanel.add(plateLabel, BorderLayout.SOUTH);

                panel4.add(carPanel);

            } catch (IOException ex) {
                System.err.println("❌ Erreur de lecture d’image : " + ex.getMessage());
                ex.printStackTrace();
            }

        }

        // Ajout des onglets
        tabbedPane.addTab("Lister Véhicules", panel1);
        tabbedPane.addTab("Lister Clients", panel2);
        tabbedPane.addTab("Lister Locations", panel3);
        tabbedPane.addTab("Véhicule", panel4);
        tabbedPane.setSelectedIndex(0);

        add(tabbedPane, BorderLayout.CENTER);

        setSize(1000, 700);
        setLocationRelativeTo(null);
    }

    // Afficher le dialog de session
    private void showSessionDialog() {
        JDialog sessionDialog = new JDialog(this, "Connexion/Inscription", true);
        sessionDialog.setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(tailleBorder, tailleBorder, tailleBorder, tailleBorder));

        formPanel.add(new JLabel("Email:"));
        JTextField emailField = new JTextField();
        formPanel.add(emailField);

        formPanel.add(new JLabel("Mot de passe:"));
        JPasswordField passwordField = new JPasswordField();
        formPanel.add(passwordField);

        formPanel.add(new JLabel("Confirmer le mot de passe:"));
        JPasswordField confirmPasswordField = new JPasswordField();
        formPanel.add(confirmPasswordField);

        JButton loginButton = new JButton("Se connecter");
        JButton registerButton = new JButton("S'inscrire");

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);

        // Connexion
        loginButton.addActionListener(e -> {
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());


        });

        // Inscription
        registerButton.addActionListener(e -> {
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());


        });

        sessionDialog.add(formPanel, BorderLayout.CENTER);
        sessionDialog.add(buttonPanel, BorderLayout.SOUTH);

        sessionDialog.setSize(300, 250);
        sessionDialog.setLocationRelativeTo(this);
        sessionDialog.setVisible(true);
    }

    // Formulaire d'ajout de véhicule
    private void showAddCarForm() {
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

        int option = JOptionPane.showConfirmDialog(this, panel, "Ajouter Véhicule", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (option == JOptionPane.OK_OPTION) {
            // Logic to add the car to the database or table
            System.out.println("Véhicule ajouté: " + brandField.getText() + " " + modelField.getText());
        }
    }

    // Formulaire d'ajout de client
    private void showAddClientForm() {
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

        int option = JOptionPane.showConfirmDialog(this, panel, "Ajouter Client", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (option == JOptionPane.OK_OPTION) {
            // Logic to add the client to the database or table
            System.out.println("Client ajouté: " + nameField.getText() + " " + surnameField.getText());
        }
    }

    // Formulaire d'ajout de location
    private void showAddLocationForm() {
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
        panel.add(notesArea);

        int option = JOptionPane.showConfirmDialog(this, panel, "Ajouter Location", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (option == JOptionPane.OK_OPTION) {
            // Logic to add the location to the database or table
            System.out.println("Location ajoutée pour le véhicule ID: " + carIdField.getText());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFramesLocation frame = new JFramesLocation();
            frame.setVisible(true);
        });
    }
}
