package org.example.view.GUI;

import org.example.controller.Controller;
import org.example.model.entity.Car;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Formulaire de modification d'une voiture
 */
public class ModifyCarForm extends JDialog {
    private final Controller controller;
    private final Car originalCar;
    private boolean carModified = false;

    // Composants du formulaire
    private JTextField marqueField;
    private JTextField modeleField;
    private JTextField couleurField;
    private JTextField immatriculationField;
    private JTextField prixJournalierField;
    private JTextField urlImageField;
    private JCheckBox disponibleCheckBox;

    /**
     * Constructeur du formulaire de modification d'une voiture
     * @param parent La fenêtre parente
     * @param controller Le contrôleur
     * @param car La voiture à modifier
     */
    public ModifyCarForm(JFrame parent, Controller controller, Car car) {
        super(parent, "Modifier une voiture", true);
        this.controller = controller;
        this.originalCar = car;
        
        // Configuration de la fenêtre
        setSize(500, 400);
        setLocationRelativeTo(parent);
        setResizable(false);
        
        // Initialisation des composants
        initComponents();
        
        // Remplir le formulaire avec les données de la voiture
        fillFormWithCar(car);
    }
    
    /**
     * Initialise les composants du formulaire
     */
    private void initComponents() {
        // Création du panneau principal
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Panneau pour les champs de saisie
        JPanel fieldsPanel = new JPanel(new GridLayout(7, 2, 10, 10));
        
        // Création des champs de saisie
        marqueField = new JTextField(20);
        modeleField = new JTextField(20);
        couleurField = new JTextField(20);
        immatriculationField = new JTextField(20);
        prixJournalierField = new JTextField(20);
        urlImageField = new JTextField(20);
        disponibleCheckBox = new JCheckBox("Disponible");
        
        // Ajout des champs au panneau
        fieldsPanel.add(new JLabel("Marque :"));
        fieldsPanel.add(marqueField);
        fieldsPanel.add(new JLabel("Modèle :"));
        fieldsPanel.add(modeleField);
        fieldsPanel.add(new JLabel("Couleur :"));
        fieldsPanel.add(couleurField);
        fieldsPanel.add(new JLabel("Immatriculation :"));
        fieldsPanel.add(immatriculationField);
        fieldsPanel.add(new JLabel("Prix journalier :"));
        fieldsPanel.add(prixJournalierField);
        fieldsPanel.add(new JLabel("URL de l'image :"));
        fieldsPanel.add(urlImageField);
        fieldsPanel.add(new JLabel("Disponibilité :"));
        fieldsPanel.add(disponibleCheckBox);
        
        // Panneau pour les boutons
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        
        // Création des boutons
        JButton saveButton = new JButton("Enregistrer");
        JButton cancelButton = new JButton("Annuler");
        
        // Ajout des boutons au panneau
        buttonsPanel.add(saveButton);
        buttonsPanel.add(cancelButton);
        
        // Ajout des panneaux au panneau principal
        mainPanel.add(fieldsPanel, BorderLayout.CENTER);
        mainPanel.add(buttonsPanel, BorderLayout.SOUTH);
        
        // Ajout du panneau principal à la fenêtre
        setContentPane(mainPanel);
        
        // Gestion des événements
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveCar();
            }
        });
        
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }
    
    /**
     * Remplit le formulaire avec les données de la voiture
     * @param car La voiture dont on veut afficher les données
     */
    private void fillFormWithCar(Car car) {
        if (car != null) {
            marqueField.setText(car.getBrand());
            modeleField.setText(car.getModel());
            couleurField.setText(car.getFuelType()); // Utilisation du champ fuelType pour la couleur
            immatriculationField.setText(car.getIdCar());
            prixJournalierField.setText(String.valueOf(car.getPriceday()));
            urlImageField.setText(car.getImage());
            disponibleCheckBox.setSelected(car.isAvailable());
        }
    }
    
    /**
     * Enregistre les modifications de la voiture
     */
    private void saveCar() {
        // Vérification des champs obligatoires
        if (marqueField.getText().trim().isEmpty() || 
            modeleField.getText().trim().isEmpty() || 
            couleurField.getText().trim().isEmpty() || 
            immatriculationField.getText().trim().isEmpty() || 
            prixJournalierField.getText().trim().isEmpty()) {
            
            JOptionPane.showMessageDialog(this, 
                "Les champs Marque, Modèle, Couleur, Immatriculation et Prix journalier sont obligatoires.", 
                "Erreur de saisie", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try {
            // Conversion du prix journalier
            float prixJournalier = Float.parseFloat(prixJournalierField.getText().trim());
            
            // Création d'une nouvelle voiture avec les données modifiées
            Car modifiedCar = new Car(
                immatriculationField.getText().trim(),
                marqueField.getText().trim(),
                modeleField.getText().trim(),
                originalCar.getYear(),
                prixJournalier,
                originalCar.getMileage(),
                couleurField.getText().trim(), // Utilisation du champ couleur pour fuelType
                originalCar.getTransmission(),
                originalCar.getSeats(),
                disponibleCheckBox.isSelected(),
                urlImageField.getText().trim()
            );
            
            // Mise à jour de la voiture dans le modèle
            controller.updateCar(modifiedCar);
            
            // Marquer la voiture comme modifiée
            carModified = true;
            
            // Fermer la fenêtre
            dispose();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, 
                "Le prix journalier doit être un nombre valide.", 
                "Erreur de saisie", 
                JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Erreur lors de la modification de la voiture : " + e.getMessage(), 
                "Erreur", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Affiche le formulaire
     * @return true si la voiture a été modifiée, false sinon
     */
    public boolean showForm() {
        setVisible(true);
        return carModified;
    }
}
