package org.example.view.GUI;

import org.example.controller.Controller;
import org.example.model.entity.Car;
import org.example.model.entity.Client;
import org.example.model.entity.Contrat;
import org.example.model.entity.Reservation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Formulaire de modification d'une location
 */
public class ModifyLocationForm {
    private JFrame parent;
    private Controller controller;
    private JComboBox<CarItem> carComboBox;
    private JComboBox<ClientItem> clientComboBox;
    private JTextField startDateField;
    private JTextField endDateField;
    private JButton startDateButton;
    private JButton endDateButton;
    private JTextField responsableField;
    private JTextField priceField;
    private JTextArea notesArea;
    private JDialog dialog;
    private Reservation originalReservation;
    private Reservation result;
    private List<Car> availableCars;
    private List<Client> availableClients;

    /**
     * Constructeur du formulaire de modification d'une location
     * @param parent La fenêtre parente
     * @param controller Le contrôleur
     * @param reservation La réservation à modifier
     */
    public ModifyLocationForm(JFrame parent, Controller controller, Reservation reservation) {
        this.parent = parent;
        this.controller = controller;
        this.originalReservation = reservation;
        
        // Récupérer la liste des voitures et des clients disponibles
        if (controller != null) {
            // Utiliser les méthodes disponibles dans JFramesLocation pour récupérer les données
            if (parent instanceof JFramesLocation) {
                JFramesLocation jFramesLocation = (JFramesLocation) parent;
                this.availableCars = jFramesLocation.getCarList();
                this.availableClients = jFramesLocation.getClientList();
            }
        }
    }

    /**
     * Affiche le formulaire de modification
     * @return La réservation modifiée ou null si annulée
     */
    public Reservation showForm() {
        JPanel panel = new JPanel(new GridLayout(8, 2, 16, 16));
        panel.setBackground(new Color(245, 247, 250));
        panel.setBorder(BorderFactory.createEmptyBorder(24, 32, 24, 32));
        Font labelFont = new Font("Segoe UI", Font.BOLD, 15);
        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 15);

        JLabel carLabel = new JLabel("Véhicule :");
        carLabel.setFont(labelFont);
        carComboBox = new JComboBox<>();
        carComboBox.setFont(fieldFont);
        
        // Remplir la liste déroulante des voitures
        if (availableCars != null) {
            for (Car car : availableCars) {
                CarItem carItem = new CarItem(car);
                carComboBox.addItem(carItem);
                // Sélectionner la voiture actuelle
                if (car.getIdCar().equals(originalReservation.getCarId())) {
                    carComboBox.setSelectedItem(carItem);
                }
            }
        }
        
        // Ajouter un écouteur pour mettre à jour le prix quand la voiture change
        carComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTotalPrice();
            }
        });
        
        panel.add(carLabel);
        panel.add(carComboBox);

        JLabel clientLabel = new JLabel("Client :");
        clientLabel.setFont(labelFont);
        clientComboBox = new JComboBox<>();
        clientComboBox.setFont(fieldFont);
        
        // Remplir la liste déroulante des clients
        if (availableClients != null) {
            for (Client client : availableClients) {
                ClientItem clientItem = new ClientItem(client);
                clientComboBox.addItem(clientItem);
                // Sélectionner le client actuel
                if (client.getIdClient() == originalReservation.getClientId()) {
                    clientComboBox.setSelectedItem(clientItem);
                }
            }
        }
        
        panel.add(clientLabel);
        panel.add(clientComboBox);

        JLabel startDateLabel = new JLabel("Date début (AAAA-MM-JJ) :");
        startDateLabel.setFont(labelFont);
        startDateField = new JTextField();
        startDateField.setFont(fieldFont);
        
        // Formater et afficher la date de début actuelle
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if (originalReservation.getStartDate() != null) {
            startDateField.setText(sdf.format(originalReservation.getStartDate()));
        }
        
        startDateField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                updateTotalPrice();
            }

            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                updateTotalPrice();
            }

            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                updateTotalPrice();
            }
        });
        
        startDateButton = new JButton("...");
        startDateButton.setFont(fieldFont);
        startDateButton.setPreferredSize(new Dimension(30, 25));
        startDateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showDatePicker(startDateField);
            }
        });
        
        JPanel startDatePanel = new JPanel(new BorderLayout(5, 0));
        startDatePanel.setBackground(new Color(245, 247, 250));
        startDatePanel.add(startDateField, BorderLayout.CENTER);
        startDatePanel.add(startDateButton, BorderLayout.EAST);
        
        panel.add(startDateLabel);
        panel.add(startDatePanel);

        JLabel endDateLabel = new JLabel("Date fin (AAAA-MM-JJ) :");
        endDateLabel.setFont(labelFont);
        endDateField = new JTextField();
        endDateField.setFont(fieldFont);
        
        // Formater et afficher la date de fin actuelle
        if (originalReservation.getEndDate() != null) {
            endDateField.setText(sdf.format(originalReservation.getEndDate()));
        }
        
        endDateField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                updateTotalPrice();
            }

            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                updateTotalPrice();
            }

            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                updateTotalPrice();
            }
        });
        
        endDateButton = new JButton("...");
        endDateButton.setFont(fieldFont);
        endDateButton.setPreferredSize(new Dimension(30, 25));
        endDateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showDatePicker(endDateField);
            }
        });
        
        JPanel endDatePanel = new JPanel(new BorderLayout(5, 0));
        endDatePanel.setBackground(new Color(245, 247, 250));
        endDatePanel.add(endDateField, BorderLayout.CENTER);
        endDatePanel.add(endDateButton, BorderLayout.EAST);
        
        panel.add(endDateLabel);
        panel.add(endDatePanel);

        JLabel responsableLabel = new JLabel("Responsable :");
        responsableLabel.setFont(labelFont);
        responsableField = new JTextField(originalReservation.getResponsable());
        responsableField.setFont(fieldFont);
        panel.add(responsableLabel);
        panel.add(responsableField);

        JLabel priceLabel = new JLabel("Prix total :");
        priceLabel.setFont(labelFont);
        priceField = new JTextField(String.valueOf(originalReservation.getPrice()));
        priceField.setFont(fieldFont);
        panel.add(priceLabel);
        panel.add(priceField);

        JLabel notesLabel = new JLabel("Notes :");
        notesLabel.setFont(labelFont);
        notesArea = new JTextArea(originalReservation.getNotes());
        notesArea.setFont(fieldFont);
        notesArea.setLineWrap(true);
        notesArea.setWrapStyleWord(true);
        JScrollPane notesScrollPane = new JScrollPane(notesArea);
        notesScrollPane.setPreferredSize(new Dimension(300, 100));
        panel.add(notesLabel);
        panel.add(notesScrollPane);

        // Boutons OK et Annuler
        JButton okButton = new JButton("Modifier");
        okButton.setFont(fieldFont);
        okButton.setBackground(new Color(46, 204, 113));
        okButton.setForeground(Color.WHITE);
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validateForm()) {
                    dialog.dispose();
                }
            }
        });
        
        JButton cancelButton = new JButton("Annuler");
        cancelButton.setFont(fieldFont);
        cancelButton.setBackground(new Color(231, 76, 60));
        cancelButton.setForeground(Color.WHITE);
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                result = null;
                dialog.dispose();
            }
        });
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(new Color(245, 247, 250));
        buttonPanel.add(cancelButton);
        buttonPanel.add(okButton);
        
        // Créer le panneau principal
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(245, 247, 250));
        mainPanel.add(panel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        // Créer et afficher la boîte de dialogue
        dialog = new JDialog(parent, "Modifier une location", true);
        dialog.setContentPane(mainPanel);
        dialog.pack();
        dialog.setLocationRelativeTo(parent);
        dialog.setResizable(false);
        dialog.setVisible(true);
        
        return result;
    }
    
    /**
     * Valide le formulaire et crée la réservation modifiée
     * @return true si le formulaire est valide, false sinon
     */
    private boolean validateForm() {
        // Récupérer les valeurs du formulaire
        CarItem selectedCarItem = (CarItem) carComboBox.getSelectedItem();
        ClientItem selectedClientItem = (ClientItem) clientComboBox.getSelectedItem();
        String startDateStr = startDateField.getText().trim();
        String endDateStr = endDateField.getText().trim();
        String responsable = responsableField.getText().trim();
        String priceStr = priceField.getText().trim();
        String notes = notesArea.getText();
        
        // Vérifier que tous les champs obligatoires sont remplis
        if (selectedCarItem == null || selectedClientItem == null || 
            startDateStr.isEmpty() || endDateStr.isEmpty() || 
            responsable.isEmpty() || priceStr.isEmpty()) {
            JOptionPane.showMessageDialog(dialog, 
                "Veuillez remplir tous les champs obligatoires", 
                "Erreur de validation", 
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        // Convertir les dates
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate, endDate;
        try {
            startDate = sdf.parse(startDateStr);
            endDate = sdf.parse(endDateStr);
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(dialog, 
                "Format de date invalide. Utilisez le format AAAA-MM-JJ", 
                "Erreur de validation", 
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        // Vérifier que la date de début est avant la date de fin
        if (startDate.after(endDate)) {
            JOptionPane.showMessageDialog(dialog, 
                "La date de début doit être antérieure à la date de fin", 
                "Erreur de validation", 
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        // Convertir le prix
        float price;
        try {
            price = Float.parseFloat(priceStr);
            if (price <= 0) {
                throw new NumberFormatException("Le prix doit être positif");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(dialog, 
                "Prix invalide. Veuillez entrer un nombre positif", 
                "Erreur de validation", 
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        // Créer la réservation modifiée avec le même constructeur que celui utilisé dans l'application
        result = new Reservation(
            originalReservation.getIdReservation(),
            startDate,
            endDate,
            responsable,
            notes,
            price
        );
        
        // Définir les relations avec les objets
        result.setCar(selectedCarItem.getCar());
        result.setClient(selectedClientItem.getClient());
        result.setContrat(originalReservation.getContrat());
        
        return true;
    }
    
    /**
     * Met à jour le prix total en fonction de la voiture et des dates sélectionnées
     */
    private void updateTotalPrice() {
        CarItem selectedCarItem = (CarItem) carComboBox.getSelectedItem();
        String startDateStr = startDateField.getText().trim();
        String endDateStr = endDateField.getText().trim();
        
        if (selectedCarItem != null && !startDateStr.isEmpty() && !endDateStr.isEmpty()) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date startDate = sdf.parse(startDateStr);
                Date endDate = sdf.parse(endDateStr);
                
                // Calculer le nombre de jours
                long diffInMillies = Math.abs(endDate.getTime() - startDate.getTime());
                long diffInDays = diffInMillies / (24 * 60 * 60 * 1000);
                
                // Calculer le prix total
                float pricePerDay = selectedCarItem.getCar().getPriceday();
                float totalPrice = pricePerDay * diffInDays;
                
                // Mettre à jour le champ de prix
                priceField.setText(String.format("%.2f", totalPrice));
            } catch (ParseException e) {
                // Ne rien faire en cas d'erreur de parsing
            }
        }
    }
    
    /**
     * Affiche un sélecteur de date pour le champ spécifié
     * @param textField Le champ de texte à mettre à jour
     */
    private void showDatePicker(final JTextField textField) {
        // Créer un panneau pour le calendrier
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        
        // Créer le calendrier
        final JCalendar calendar = new JCalendar();
        
        // Si le champ contient déjà une date, sélectionner cette date dans le calendrier
        String dateStr = textField.getText().trim();
        if (!dateStr.isEmpty()) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date date = sdf.parse(dateStr);
                calendar.setDate(date);
            } catch (ParseException e) {
                // Ignorer l'erreur et utiliser la date actuelle
            }
        }
        
        panel.add(calendar, BorderLayout.CENTER);
        
        // Créer les boutons OK et Annuler
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton okButton = new JButton("OK");
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Formater la date sélectionnée
                Date selectedDate = calendar.getDate();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                textField.setText(sdf.format(selectedDate));
                
                // Fermer la boîte de dialogue
                Window window = SwingUtilities.getWindowAncestor(panel);
                if (window instanceof JDialog) {
                    ((JDialog) window).dispose();
                }
            }
        });
        
        JButton cancelButton = new JButton("Annuler");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Fermer la boîte de dialogue sans rien faire
                Window window = SwingUtilities.getWindowAncestor(panel);
                if (window instanceof JDialog) {
                    ((JDialog) window).dispose();
                }
            }
        });
        
        buttonPanel.add(cancelButton);
        buttonPanel.add(okButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        // Créer et afficher la boîte de dialogue
        JDialog dialog = new JDialog(this.dialog, "Sélectionner une date", true);
        dialog.setContentPane(panel);
        dialog.pack();
        dialog.setLocationRelativeTo(this.dialog);
        dialog.setVisible(true);
    }
    
    /**
     * Classe interne pour représenter un élément de la liste déroulante des voitures
     */
    private class CarItem {
        private Car car;
        
        public CarItem(Car car) {
            this.car = car;
        }
        
        public Car getCar() {
            return car;
        }
        
        @Override
        public String toString() {
            return car.getBrand() + " " + car.getModel() + " (" + car.getIdCar() + ")";
        }
    }
    
    /**
     * Classe interne pour représenter un élément de la liste déroulante des clients
     */
    private class ClientItem {
        private Client client;
        
        public ClientItem(Client client) {
            this.client = client;
        }
        
        public Client getClient() {
            return client;
        }
        
        @Override
        public String toString() {
            return client.getName() + " " + client.getSurname() + " (" + client.getIdClient() + ")";
        }
    }
    
    /**
     * Classe interne pour le calendrier
     */
    private class JCalendar extends JPanel {
        private JComboBox<String> monthComboBox;
        private JSpinner yearSpinner;
        private JPanel daysPanel;
        private Calendar calendar;
        
        public JCalendar() {
            setLayout(new BorderLayout());
            calendar = Calendar.getInstance();
            
            // Panneau supérieur pour le mois et l'année
            JPanel topPanel = new JPanel(new BorderLayout());
            
            // Liste déroulante des mois
            String[] months = {"Janvier", "Février", "Mars", "Avril", "Mai", "Juin", 
                              "Juillet", "Août", "Septembre", "Octobre", "Novembre", "Décembre"};
            monthComboBox = new JComboBox<>(months);
            monthComboBox.setSelectedIndex(calendar.get(Calendar.MONTH));
            monthComboBox.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    updateCalendar();
                }
            });
            
            // Spinner pour l'année
            yearSpinner = new JSpinner(new SpinnerNumberModel(calendar.get(Calendar.YEAR), 1900, 2100, 1));
            yearSpinner.addChangeListener(e -> updateCalendar());
            
            topPanel.add(monthComboBox, BorderLayout.WEST);
            topPanel.add(yearSpinner, BorderLayout.EAST);
            add(topPanel, BorderLayout.NORTH);
            
            // Panneau des jours
            daysPanel = new JPanel(new GridLayout(7, 7));
            add(daysPanel, BorderLayout.CENTER);
            
            // Initialiser le calendrier
            updateCalendar();
        }
        
        /**
         * Met à jour l'affichage du calendrier
         */
        private void updateCalendar() {
            daysPanel.removeAll();
            
            // Mettre à jour le calendrier avec le mois et l'année sélectionnés
            calendar.set(Calendar.MONTH, monthComboBox.getSelectedIndex());
            calendar.set(Calendar.YEAR, (Integer) yearSpinner.getValue());
            
            // Ajouter les en-têtes des jours de la semaine
            String[] dayNames = {"Dim", "Lun", "Mar", "Mer", "Jeu", "Ven", "Sam"};
            for (String dayName : dayNames) {
                JLabel label = new JLabel(dayName, SwingConstants.CENTER);
                label.setFont(new Font("Segoe UI", Font.BOLD, 12));
                daysPanel.add(label);
            }
            
            // Déterminer le premier jour du mois
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            int firstDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1; // 0 = dimanche
            
            // Ajouter des cases vides pour les jours avant le premier jour du mois
            for (int i = 0; i < firstDayOfWeek; i++) {
                daysPanel.add(new JLabel());
            }
            
            // Ajouter les jours du mois
            int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
            for (int day = 1; day <= daysInMonth; day++) {
                final int currentDay = day;
                JButton dayButton = new JButton(String.valueOf(day));
                dayButton.setFont(new Font("Segoe UI", Font.PLAIN, 12));
                dayButton.setFocusPainted(false);
                
                // Mettre en évidence le jour actuel
                Calendar today = Calendar.getInstance();
                if (calendar.get(Calendar.YEAR) == today.get(Calendar.YEAR) &&
                    calendar.get(Calendar.MONTH) == today.get(Calendar.MONTH) &&
                    day == today.get(Calendar.DAY_OF_MONTH)) {
                    dayButton.setBackground(new Color(80, 90, 170));
                    dayButton.setForeground(Color.WHITE);
                }
                
                // Ajouter un écouteur pour sélectionner le jour
                dayButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        calendar.set(Calendar.DAY_OF_MONTH, currentDay);
                    }
                });
                
                daysPanel.add(dayButton);
            }
            
            daysPanel.revalidate();
            daysPanel.repaint();
        }
        
        /**
         * Récupère la date sélectionnée
         * @return La date sélectionnée
         */
        public Date getDate() {
            return calendar.getTime();
        }
        
        /**
         * Définit la date à afficher
         * @param date La date à afficher
         */
        public void setDate(Date date) {
            calendar.setTime(date);
            monthComboBox.setSelectedIndex(calendar.get(Calendar.MONTH));
            yearSpinner.setValue(calendar.get(Calendar.YEAR));
            updateCalendar();
        }
    }
}
