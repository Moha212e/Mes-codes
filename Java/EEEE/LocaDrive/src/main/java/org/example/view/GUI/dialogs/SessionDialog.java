package org.example.view.GUI.dialogs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Dialogue pour la connexion et l'inscription des utilisateurs
 */
public class SessionDialog extends JDialog {
    private JTextField emailField;
    private JPasswordField passwordField, confirmPasswordField;
    private JButton loginButton, registerButton;
    private int tailleBorder = 30;
    
    /**
     * Constructeur du dialogue de session
     * 
     * @param parent Fenêtre parente
     */
    public SessionDialog(Window parent) {
        super(parent, "Connexion/Inscription", ModalityType.APPLICATION_MODAL);
        setLayout(new BorderLayout());
        
        // Création du panneau de formulaire
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(tailleBorder, tailleBorder, tailleBorder, tailleBorder));
        
        formPanel.add(new JLabel("Email:"));
        emailField = new JTextField();
        formPanel.add(emailField);
        
        formPanel.add(new JLabel("Mot de passe:"));
        passwordField = new JPasswordField();
        formPanel.add(passwordField);
        
        formPanel.add(new JLabel("Confirmer le mot de passe:"));
        confirmPasswordField = new JPasswordField();
        formPanel.add(confirmPasswordField);
        
        // Création des boutons
        loginButton = new JButton("Se connecter");
        registerButton = new JButton("S'inscrire");
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);
        
        // Ajout des écouteurs d'événements
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleLogin();
            }
        });
        
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleRegister();
            }
        });
        
        // Ajout des panneaux au dialogue
        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        
        // Configuration du dialogue
        setSize(400, 300);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
    
    /**
     * Gère la connexion de l'utilisateur
     */
    private void handleLogin() {
        String email = emailField.getText();
        String password = new String(passwordField.getPassword());
        
        // Vérifier que les champs obligatoires sont remplis
        if (email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Veuillez remplir tous les champs obligatoires.",
                "Champs manquants", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Ici, vous implémenteriez la logique d'authentification
        System.out.println("Tentative de connexion avec: " + email);
        
        // Si l'authentification réussit, fermer le dialogue
        // dispose();
        
        // Pour l'instant, afficher un message
        JOptionPane.showMessageDialog(this, 
            "Fonctionnalité de connexion à implémenter.",
            "Développement en cours", 
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Gère l'inscription de l'utilisateur
     */
    private void handleRegister() {
        String email = emailField.getText();
        String password = new String(passwordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());
        
        // Vérifier que les champs obligatoires sont remplis
        if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Veuillez remplir tous les champs obligatoires.",
                "Champs manquants", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Vérifier que les mots de passe correspondent
        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this, 
                "Les mots de passe ne correspondent pas.",
                "Erreur de saisie", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Ici, vous implémenteriez la logique d'inscription
        System.out.println("Tentative d'inscription avec: " + email);
        
        // Si l'inscription réussit, fermer le dialogue
        // dispose();
        
        // Pour l'instant, afficher un message
        JOptionPane.showMessageDialog(this, 
            "Fonctionnalité d'inscription à implémenter.",
            "Développement en cours", 
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Affiche le dialogue de session
     * 
     * @param parent Fenêtre parente
     */
    public static void showDialog(Window parent) {
        SessionDialog dialog = new SessionDialog(parent);
        dialog.setVisible(true);
    }
}
