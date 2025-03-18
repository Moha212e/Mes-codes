package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Création de la fenêtre principale
        JFrame frame = new JFrame("Gestionnaire d'Événements");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Liste pour stocker les événements
        List<String> events = new ArrayList<>();
        DefaultListModel<String> listModel = new DefaultListModel<>();
        JList<String> eventList = new JList<>(listModel);

        // Champs de saisie pour les détails de l'événement
        JTextField eventNameField = new JTextField();
        JTextField eventDateField = new JTextField();
        JTextField eventDurationField = new JTextField();
        JTextField eventLocationField = new JTextField();

        // Panel pour les détails de l'événement
        JPanel eventDetailPanel = new JPanel(new GridLayout(4, 2));
        eventDetailPanel.setBorder(BorderFactory.createTitledBorder("Détails de l'Événement"));
        eventDetailPanel.add(new JLabel("Nom de l'événement:"));
        eventDetailPanel.add(eventNameField);
        eventDetailPanel.add(new JLabel("Date:"));
        eventDetailPanel.add(eventDateField);
        eventDetailPanel.add(new JLabel("Durée:"));
        eventDetailPanel.add(eventDurationField);
        eventDetailPanel.add(new JLabel("Lieu:"));
        eventDetailPanel.add(eventLocationField);

        // Panel pour les boutons d'action
        JPanel actionPanel = new JPanel();
        JButton addButton = new JButton("Ajouter");
        JButton removeButton = new JButton("Supprimer");
        actionPanel.add(addButton);
        actionPanel.add(removeButton);

        // Ajouter un écouteur pour le bouton "Ajouter"
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String eventName = eventNameField.getText();
                String eventDate = eventDateField.getText();
                String eventDuration = eventDurationField.getText();
                String eventLocation = eventLocationField.getText();

                // Vérifier que tous les champs sont remplis
                if (!eventName.isEmpty() && !eventDate.isEmpty() && !eventDuration.isEmpty() && !eventLocation.isEmpty()) {
                    String eventDetails = eventName + " - " + eventDate + " - " + eventDuration + " - " + eventLocation;
                    events.add(eventDetails); // Ajouter à la liste
                    listModel.addElement(eventDetails); // Ajouter à la JList
                    clearFields(eventNameField, eventDateField, eventDurationField, eventLocationField); // Effacer les champs
                } else {
                    JOptionPane.showMessageDialog(frame, "Veuillez remplir tous les champs.", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Ajouter un écouteur pour le bouton "Supprimer"
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = eventList.getSelectedIndex();
                if (selectedIndex != -1) {
                    listModel.remove(selectedIndex); // Supprimer de la JList
                    events.remove(selectedIndex); // Supprimer de la liste
                } else {
                    JOptionPane.showMessageDialog(frame, "Veuillez sélectionner un événement à supprimer.", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Ajouter les panels à la fenêtre
        frame.add(eventDetailPanel, BorderLayout.NORTH);
        frame.add(new JScrollPane(eventList), BorderLayout.CENTER);
        frame.add(actionPanel, BorderLayout.SOUTH);

        // Afficher la fenêtre
        frame.setVisible(true);
    }

    // Méthode pour effacer les champs de saisie
    private static void clearFields(JTextField... fields) {
        for (JTextField field : fields) {
            field.setText("");
        }
    }
}