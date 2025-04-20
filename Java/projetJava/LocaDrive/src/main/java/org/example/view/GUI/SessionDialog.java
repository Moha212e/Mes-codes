package org.example.view.GUI;

import org.example.controller.Controller;
import org.example.controller.ControllerActions;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class SessionDialog extends JDialog {
    private JTextField emailField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JButton loginButton;
    private JButton registerButton;
    private Controller controller;
    private final Color PRIMARY_COLOR = new Color(70, 130, 180); // Steel Blue
    private final Color SECONDARY_COLOR = new Color(240, 248, 255); // Alice Blue
    private final Font LABEL_FONT = new Font("Arial", Font.BOLD, 12);
    private final Font FIELD_FONT = new Font("Arial", Font.PLAIN, 12);
    private final int PADDING = 20;

    public SessionDialog(JFrame parent, Controller controller) {
        super(parent, "Connexion/Inscription", true);
        this.controller = controller;
        
        setLayout(new BorderLayout());
        
        // Panel principal avec un fond coloré
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(SECONDARY_COLOR);
        mainPanel.setBorder(new EmptyBorder(PADDING, PADDING, PADDING, PADDING));
        
        // Titre en haut
        JLabel titleLabel = new JLabel("LocaDrive - Accès", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(PRIMARY_COLOR);
        titleLabel.setBorder(new EmptyBorder(0, 0, PADDING, 0));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Panel du formulaire avec espacement amélioré
        JPanel formPanel = new JPanel(new GridLayout(3, 1, 10, 15));
        formPanel.setBackground(SECONDARY_COLOR);
        formPanel.setBorder(new EmptyBorder(PADDING, PADDING, PADDING, PADDING));

        // Email field avec style amélioré
        JPanel emailPanel = new JPanel(new BorderLayout(5, 0));
        emailPanel.setBackground(SECONDARY_COLOR);
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(LABEL_FONT);
        emailLabel.setForeground(PRIMARY_COLOR);
        emailField = new JTextField();
        emailField.setFont(FIELD_FONT);
        emailField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(PRIMARY_COLOR, 1, true),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)));
        emailPanel.add(emailLabel, BorderLayout.NORTH);
        emailPanel.add(emailField, BorderLayout.CENTER);
        formPanel.add(emailPanel);

        // Password field avec style amélioré
        JPanel passwordPanel = new JPanel(new BorderLayout(5, 0));
        passwordPanel.setBackground(SECONDARY_COLOR);
        JLabel passwordLabel = new JLabel("Mot de passe:");
        passwordLabel.setFont(LABEL_FONT);
        passwordLabel.setForeground(PRIMARY_COLOR);
        passwordField = new JPasswordField();
        passwordField.setFont(FIELD_FONT);
        passwordField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(PRIMARY_COLOR, 1, true),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)));
        passwordPanel.add(passwordLabel, BorderLayout.NORTH);
        passwordPanel.add(passwordField, BorderLayout.CENTER);
        formPanel.add(passwordPanel);

        // Confirm Password field avec style amélioré
        JPanel confirmPanel = new JPanel(new BorderLayout(5, 0));
        confirmPanel.setBackground(SECONDARY_COLOR);
        JLabel confirmLabel = new JLabel("Confirmer le mot de passe:");
        confirmLabel.setFont(LABEL_FONT);
        confirmLabel.setForeground(PRIMARY_COLOR);
        confirmPasswordField = new JPasswordField();
        confirmPasswordField.setFont(FIELD_FONT);
        confirmPasswordField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(PRIMARY_COLOR, 1, true),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)));
        confirmPanel.add(confirmLabel, BorderLayout.NORTH);
        confirmPanel.add(confirmPasswordField, BorderLayout.CENTER);
        formPanel.add(confirmPanel);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        // Boutons stylisés
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.setBackground(SECONDARY_COLOR);
        buttonPanel.setBorder(new EmptyBorder(PADDING, 0, 0, 0));
        
        loginButton = new JButton("Se connecter");
        styleButton(loginButton, ControllerActions.LOGIN);
        
        registerButton = new JButton("S'inscrire");
        styleButton(registerButton, ControllerActions.REGISTER);
        
        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(mainPanel);

        setSize(400, 450);
        setLocationRelativeTo(parent);
        setResizable(false);
    }
    
    private void styleButton(JButton button, String actionCommand) {
        button.setActionCommand(actionCommand);
        button.addActionListener(controller);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setForeground(Color.WHITE);
        button.setBackground(PRIMARY_COLOR);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createRaisedBevelBorder(),
                BorderFactory.createEmptyBorder(8, 15, 8, 15)));
        
        // Effet de survol
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(100, 149, 237)); // Cornflower Blue
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(PRIMARY_COLOR);
            }
        });
    }
    
    public String getEmail() {
        return emailField.getText();
    }
    
    public String getPassword() {
        return new String(passwordField.getPassword());
    }
    
    public String getConfirmPassword() {
        return new String(confirmPasswordField.getPassword());
    }
    
    public void showDialog() {
        setVisible(true);
    }
}
