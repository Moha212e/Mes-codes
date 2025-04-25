package org.example.view.GUI;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatIconColors;
import com.formdev.flatlaf.FlatLightLaf;
import org.example.controller.Controller;
import org.example.controller.ControllerActions;
import org.example.model.entity.Contrat;
import org.example.model.entity.Car;
import org.example.model.entity.Client;
import org.example.model.entity.Reservation;
import org.example.utils.DateFormatter;
import org.example.utils.WrapLayout;
import org.example.view.ViewLocation;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.ArrayList;
import java.io.File;

import javax.swing.plaf.basic.BasicButtonUI;

public class JFramesLocation extends JFrame implements ViewLocation {
    private JTabbedPane tabbedPane;
    private JPanel panel1, panel2, panel3, panel4, panel5;
    private JTable table1, table2, table3, table4;
    private JButton buttonAddCar, buttonModifyCar, buttonDeleteCar;
    private JButton buttonAddClient, buttonModifyClient, buttonDeleteClient;
    private JButton buttonAddLocation, buttonModifyLocation, buttonDeleteLocation;
    private JButton buttonAddContrat, buttonModifyContrat, buttonDeleteContrat;
    private JLabel labelImage;
    private int tailleBorder = 30;
    private int posBouton = 0;
    private Controller controller;
    private JButton sessionButton; // Ajout de la référence directe
    private JButton lightThemeButton, darkThemeButton; // Boutons pour changer de thème
    private JTextField searchField; // Champ de recherche
    private JButton searchButton; // Bouton de recherche

    // Formulaires et dialogues
    private ImprovedSessionDialog sessionDialog;
    private AddCarForm addCarForm;
    private AddClientForm addClientForm;
    private AddLocationForm addLocationForm;
    private AddContratForm addContratForm;

    public JFramesLocation() {
        super("LocaDrive");
        UIManager.put("control", new Color(245, 247, 250));
        UIManager.put("info", new Color(245, 247, 250));
        UIManager.put("nimbusBase", new Color(80, 90, 170));
        UIManager.put("nimbusBlueGrey", new Color(100, 110, 180));
        UIManager.put("nimbusLightBackground", Color.WHITE);
        UIManager.put("text", new Color(40, 40, 40));
        UIManager.put("Table.alternateRowColor", new Color(240, 242, 250));
        UIManager.put("Table.font", new Font("Segoe UI", Font.PLAIN, 15));
        UIManager.put("TableHeader.font", new Font("Segoe UI", Font.BOLD, 16));
        UIManager.put("TableHeader.background", new Color(80, 90, 170));
        UIManager.put("TableHeader.foreground", Color.WHITE);
        UIManager.put("Button.font", new Font("Segoe UI", Font.BOLD, 15));
        UIManager.put("TabbedPane.selected", new Color(80, 90, 170));
        UIManager.put("TabbedPane.background", new Color(230, 232, 240));
        UIManager.put("TabbedPane.foreground", new Color(80, 90, 170));

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // --- BARRE SUPERIEURE MODERNE ---
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(80, 90, 170));
        JLabel titleLabel = new JLabel("LocaDrive", SwingConstants.LEFT);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        topPanel.add(titleLabel, BorderLayout.WEST);

        // Bouton de session avec style moderne
        sessionButton = createModernButton("Se connecter", new Color(80, 90, 170), Color.WHITE, new Color(120, 130, 200), "login");
        JPanel sessionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        sessionPanel.setOpaque(false);
        sessionPanel.add(sessionButton);
        topPanel.add(sessionPanel, BorderLayout.EAST);
        
        // Ajout du panel de recherche
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        searchPanel.setOpaque(false);
        
        // Création du champ de recherche avec placeholder
        searchField = new JTextField(20);
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        searchField.setToolTipText("Rechercher dans tous les onglets");
        
        // Bouton de recherche avec icône
        searchButton = createModernButton("Rechercher", new Color(80, 90, 170), Color.WHITE, new Color(120, 130, 200), "search");
        searchButton.setActionCommand(ControllerActions.SEARCH);
        searchButton.addActionListener(controller);
        
        // Ajout d'un listener pour la touche Entrée
        searchField.addActionListener(e -> {
            if (controller != null) {
                controller.actionPerformed(new ActionEvent(searchField, ActionEvent.ACTION_PERFORMED, ControllerActions.SEARCH));
            }
        });
        
        searchPanel.add(new JLabel("Rechercher: "));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        
        topPanel.add(searchPanel, BorderLayout.CENTER);
        
        add(topPanel, BorderLayout.NORTH);

        // Création de la barre de menu
        JMenuBar menuBar = new JMenuBar();

        // Menu Fichier
        JMenu fileMenu = new JMenu("Fichier");
        fileMenu.setFont(new Font("Segoe UI", Font.BOLD, 14));

        // Sous-menu Importer
        JMenu importMenu = new JMenu("Importer");
        importMenu.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JMenuItem importCars = new JMenuItem("Voitures");
        importCars.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        importCars.setActionCommand(ControllerActions.IMPORT_CARS);
        importCars.addActionListener(controller);

        JMenuItem importClients = new JMenuItem("Clients");
        importClients.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        importClients.setActionCommand(ControllerActions.IMPORT_CLIENTS);
        importClients.addActionListener(controller);

        JMenuItem importContracts = new JMenuItem("Contrats");
        importContracts.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        importContracts.setActionCommand(ControllerActions.IMPORT_CONTRACTS);
        importContracts.addActionListener(controller);

        JMenuItem importReservations = new JMenuItem("Réservations");
        importReservations.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        importReservations.setActionCommand(ControllerActions.IMPORT_RESERVATIONS);
        importReservations.addActionListener(controller);

        importMenu.add(importCars);
        importMenu.add(importClients);
        importMenu.add(importContracts);
        importMenu.add(importReservations);

        // Sous-menu Exporter
        JMenu exportMenu = new JMenu("Exporter");
        exportMenu.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JMenuItem exportCars = new JMenuItem("Voitures");
        exportCars.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        exportCars.setActionCommand(ControllerActions.EXPORT_CARS);
        exportCars.addActionListener(controller);

        JMenuItem exportClients = new JMenuItem("Clients");
        exportClients.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        exportClients.setActionCommand(ControllerActions.EXPORT_CLIENTS);
        exportClients.addActionListener(controller);

        JMenuItem exportContracts = new JMenuItem("Contrats");
        exportContracts.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        exportContracts.setActionCommand(ControllerActions.EXPORT_CONTRACTS);
        exportContracts.addActionListener(controller);

        JMenuItem exportReservations = new JMenuItem("Réservations");
        exportReservations.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        exportReservations.setActionCommand(ControllerActions.EXPORT_RESERVATIONS);
        exportReservations.addActionListener(controller);

        exportMenu.add(exportCars);
        exportMenu.add(exportClients);
        exportMenu.add(exportContracts);
        exportMenu.add(exportReservations);

        fileMenu.add(importMenu);
        fileMenu.add(exportMenu);
        fileMenu.addSeparator();

        JMenuItem exitItem = new JMenuItem("Quitter");
        exitItem.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        exitItem.setActionCommand("EXIT");
        exitItem.addActionListener(e -> System.exit(0));
        fileMenu.add(exitItem);



        // Menu Thèmes
        JMenu themeMenu = new JMenu("Thèmes");
        themeMenu.setFont(new Font("Segoe UI", Font.BOLD, 14));

        JMenuItem lightThemeItem = new JMenuItem("Thème Clair");
        lightThemeItem.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lightThemeItem.addActionListener(e -> changeTheme("light"));

        JMenuItem darkThemeItem = new JMenuItem("Thème Dracula");
        darkThemeItem.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        darkThemeItem.addActionListener(e -> changeTheme("dark"));

        themeMenu.add(lightThemeItem);
        themeMenu.add(darkThemeItem);

        // Menu Paramètres
        JMenu settingsMenu = new JMenu("Paramètres");
        settingsMenu.setFont(new Font("Segoe UI", Font.BOLD, 14));

        // Sous-menu Format de date
        JMenu dateFormatMenu = new JMenu("Format de date");
        dateFormatMenu.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JMenuItem defaultFormatItem = new JMenuItem("Standard (dd/MM/yyyy)");
        defaultFormatItem.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        defaultFormatItem.addActionListener(e -> changeDateFormat(DateFormatter.FORMAT_DEFAULT));

        JMenuItem isoFormatItem = new JMenuItem("ISO (yyyy-MM-dd)");
        isoFormatItem.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        isoFormatItem.addActionListener(e -> changeDateFormat(DateFormatter.FORMAT_ISO));

        JMenuItem longFormatItem = new JMenuItem("Long (dd MMMM yyyy)");
        longFormatItem.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        longFormatItem.addActionListener(e -> changeDateFormat(DateFormatter.FORMAT_LONG));

        JMenuItem shortFormatItem = new JMenuItem("Court (dd/MM/yy)");
        shortFormatItem.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        shortFormatItem.addActionListener(e -> changeDateFormat(DateFormatter.FORMAT_SHORT));

        dateFormatMenu.add(defaultFormatItem);
        dateFormatMenu.add(isoFormatItem);
        dateFormatMenu.add(longFormatItem);
        dateFormatMenu.add(shortFormatItem);

        settingsMenu.add(dateFormatMenu);

        menuBar.add(fileMenu);
        menuBar.add(themeMenu);
        menuBar.add(settingsMenu);

        setJMenuBar(menuBar);

        // Création du JTabbedPane
        tabbedPane = new JTabbedPane();

        // Onglet 1: Lister véhicules
        panel1 = createModernPanel();
        String[] carColumns = {"ID Car", "Marque", "Modèle", "Année", "Prix/jour", "Kilometrages", "Carburant", "Transmission", "Places", "Disponibilité"};
        Object[][] carData = {};  // Les données seront ajoutées dynamiquement
        table1 = new JTable(carData, carColumns);
        stylizeTable(table1);
        JScrollPane scrollPane1 = new JScrollPane(table1);
        JPanel buttonPanel1 = new JPanel(new FlowLayout(posBouton));
        buttonPanel1.setOpaque(false);
        buttonAddCar = createModernButton("Ajouter", new Color(46, 204, 113), Color.WHITE, new Color(39, 174, 96), "add");
        buttonAddCar.setActionCommand(ControllerActions.ADD_CAR);
        buttonModifyCar = createModernButton("Modifier", new Color(241, 196, 15), Color.WHITE, new Color(243, 156, 18), "edit");
        buttonModifyCar.setActionCommand(ControllerActions.MODIFY_CAR);
        buttonDeleteCar = createModernButton("Supprimer", new Color(231, 76, 60), Color.WHITE, new Color(192, 57, 43), "delete");
        buttonDeleteCar.setActionCommand(ControllerActions.DELETE_CAR);
        buttonPanel1.add(buttonAddCar);
        buttonPanel1.add(buttonModifyCar);
        buttonPanel1.add(buttonDeleteCar);
        panel1.add(buttonPanel1, BorderLayout.NORTH);
        panel1.add(scrollPane1, BorderLayout.CENTER);

        // Onglet 2: Lister clients
        panel2 = createModernPanel();
        String[] clientColumns = {"ID Client", "Name", "Surname", "Email", "Password", "Birthdate", "Phone"};
        Object[][] clientData = {};  // Les données seront ajoutées dynamiquement
        table2 = new JTable(clientData, clientColumns);
        stylizeTable(table2);
        JScrollPane scrollPane2 = new JScrollPane(table2);
        JPanel buttonPanel2 = new JPanel(new FlowLayout(posBouton));
        buttonPanel2.setOpaque(false);
        buttonAddClient = createModernButton("Ajouter", new Color(46, 204, 113), Color.WHITE, new Color(39, 174, 96), "add");
        buttonAddClient.setActionCommand(ControllerActions.ADD_CLIENT);
        buttonModifyClient = createModernButton("Modifier", new Color(241, 196, 15), Color.WHITE, new Color(243, 156, 18), "edit");
        buttonModifyClient.setActionCommand(ControllerActions.MODIFY_CLIENT);
        buttonDeleteClient = createModernButton("Supprimer", new Color(231, 76, 60), Color.WHITE, new Color(192, 57, 43), "delete");
        buttonDeleteClient.setActionCommand(ControllerActions.DELETE_CLIENT);
        buttonPanel2.add(buttonAddClient);
        buttonPanel2.add(buttonModifyClient);
        buttonPanel2.add(buttonDeleteClient);
        panel2.add(buttonPanel2, BorderLayout.NORTH);
        panel2.add(scrollPane2, BorderLayout.CENTER);

        // Onglet 3: Lister réservations
        panel3 = createModernPanel();
        String[] locationColumns = {"ID Reservation", "Immatriculation", "Nom Complet", "Date Début", "Date Fin", "Responsable", "Prix Total", "Notes"};
        Object[][] locationData = {};  // Les données seront ajoutées dynamiquement
        table3 = new JTable(locationData, locationColumns);
        stylizeTable(table3);
        JScrollPane scrollPane3 = new JScrollPane(table3);
        JPanel buttonPanel3 = new JPanel(new FlowLayout(posBouton));
        buttonPanel3.setOpaque(false);
        buttonAddLocation = createModernButton("Ajouter", new Color(46, 204, 113), Color.WHITE, new Color(39, 174, 96), "add");
        buttonAddLocation.setActionCommand(ControllerActions.ADD_LOCATION);
        buttonModifyLocation = createModernButton("Modifier", new Color(241, 196, 15), Color.WHITE, new Color(243, 156, 18), "edit");
        buttonModifyLocation.setActionCommand(ControllerActions.MODIFY_LOCATION);
        buttonDeleteLocation = createModernButton("Supprimer", new Color(231, 76, 60), Color.WHITE, new Color(192, 57, 43), "delete");
        buttonDeleteLocation.setActionCommand(ControllerActions.DELETE_LOCATION);
        buttonPanel3.add(buttonAddLocation);
        buttonPanel3.add(buttonModifyLocation);
        buttonPanel3.add(buttonDeleteLocation);
        panel3.add(buttonPanel3, BorderLayout.NORTH);
        panel3.add(scrollPane3, BorderLayout.CENTER);

        // Onglet 4: Afficher images véhicules
        panel4 = new JPanel(new GridLayout(2, 3, 10, 10));
        panel4.setBorder(BorderFactory.createEmptyBorder(tailleBorder, tailleBorder, tailleBorder, tailleBorder));
        panel4.setBackground(new Color(245, 247, 250));

        // Onglet 5: Lister contrats
        panel5 = createModernPanel();
        String[] contratColumns = {"ID Contrat", "Véhicule", "Client", "Caution", "Type Assurance", "Options", "Signé", "Statut"};
        Object[][] contratData = {}; // À remplir dynamiquement
        table4 = new JTable(contratData, contratColumns);
        stylizeTable(table4);
        JScrollPane scrollPane4 = new JScrollPane(table4);
        JPanel buttonPanel4 = new JPanel(new FlowLayout(posBouton));
        buttonPanel4.setOpaque(false);
        buttonAddContrat = createModernButton("Ajouter", new Color(46, 204, 113), Color.WHITE, new Color(39, 174, 96), "add");
        buttonAddContrat.setActionCommand(ControllerActions.ADD_CONTRAT);
        buttonModifyContrat = createModernButton("Modifier", new Color(241, 196, 15), Color.WHITE, new Color(243, 156, 18), "edit");
        buttonModifyContrat.setActionCommand(ControllerActions.MODIFY_CONTRAT);
        buttonDeleteContrat = createModernButton("Supprimer", new Color(231, 76, 60), Color.WHITE, new Color(192, 57, 43), "delete");
        buttonDeleteContrat.setActionCommand(ControllerActions.DELETE_CONTRAT);
        buttonPanel4.add(buttonAddContrat);
        buttonPanel4.add(buttonModifyContrat);
        buttonPanel4.add(buttonDeleteContrat);
        panel5.add(buttonPanel4, BorderLayout.NORTH);
        panel5.add(scrollPane4, BorderLayout.CENTER);

        // Ajout des onglets
        tabbedPane.addTab("Lister Véhicules", panel1);
        tabbedPane.addTab("Lister Clients", panel2);
        tabbedPane.addTab("Lister Locations", panel3);
        tabbedPane.addTab("Véhicule", panel4);
        tabbedPane.addTab("Contrats", panel5);
        tabbedPane.setSelectedIndex(0);
        add(tabbedPane, BorderLayout.CENTER);

        // Maximiser la fenêtre pour qu'elle occupe tout l'écran
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Taille par défaut si l'utilisateur restaure la fenêtre
        setSize(1200, 800);
        setLocationRelativeTo(null);
    }

    // Implémentation des méthodes de l'interface ViewLocation
    @Override
    public void run() {
        // S'assurer que tous les composants sont correctement initialisés avant d'afficher la fenêtre
        SwingUtilities.invokeLater(() -> {
            this.setVisible(true);
        });
    }

    @Override
    public void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Erreur", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Information", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void setController(Controller controller) {
        this.controller = controller;

        // Initialiser les formulaires et dialogues
        sessionDialog = new ImprovedSessionDialog(this, controller);
        addCarForm = new AddCarForm(this, controller);
        addClientForm = new AddClientForm(this, controller);
        addLocationForm = new AddLocationForm(this, controller);

        // Configurer les écouteurs d'événements
        setupEventListeners();

        // Associer le controller aux menus d'import/export
        updateMenuListeners();
    }

    /**
     * Configure les écouteurs d'événements pour tous les boutons
     */
    private void setupEventListeners() {
        // Configurer les écouteurs pour les boutons de voitures
        buttonAddCar.addActionListener(controller);
        buttonModifyCar.addActionListener(controller);
        buttonDeleteCar.addActionListener(controller);

        // Configurer les écouteurs pour les boutons de clients
        buttonAddClient.addActionListener(controller);
        buttonModifyClient.addActionListener(controller);
        buttonDeleteClient.addActionListener(controller);

        // Configurer les écouteurs pour les boutons de réservations
        buttonAddLocation.addActionListener(controller);
        buttonModifyLocation.addActionListener(controller);
        buttonDeleteLocation.addActionListener(controller);

        // Configurer les écouteurs pour les boutons de contrats
        buttonAddContrat.addActionListener(controller);
        buttonModifyContrat.addActionListener(controller);
        buttonDeleteContrat.addActionListener(controller);

        // Configurer l'écouteur pour le bouton de session
        sessionButton.addActionListener(e -> {
            if (sessionButton.getText().equals("Se connecter")) {
                controller.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, ControllerActions.SESSION));
            } else {
                controller.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, ControllerActions.LOGOUT));
            }
        });
    }

    // Méthodes pour exposer les fonctionnalités au controller
    public void showSessionDialogFromController() {
        sessionDialog.showDialog();
    }
    // Implémentation des méthodes pour mettre à jour les tables
    @Override
    public void updateCarTable(List<Car> cars) {
        // Créer un nouveau modèle de table avec les données des voitures
        Object[][] data = new Object[cars.size()][10];
        int i = 0;
        for (Car car : cars) {

            data[i][0] = car.getIdCar();
            data[i][1] = car.getBrand();
            data[i][2] = car.getModel();
            data[i][3] = car.getYear();
            data[i][4] = car.getPriceday() + " €/jour";
            data[i][5] = car.getMileage() + " km";
            data[i][6] = car.getFuelType();
            data[i][7] = car.getTransmission();
            data[i][8] = car.getSeats();
            data[i][9] = car.isAvailable() ? "Disponible" : "Non disponible";
            i++;

        }

        // Mettre à jour la table avec les nouvelles données
        String[] columns = {"ID Car", "Marque", "Modèle", "Année", "Prix/jour", "Kilometrages", "Carburant", "Transmission", "Places", "Disponibilité"};
        table1.setModel(new javax.swing.table.DefaultTableModel(data, columns));
        System.out.println("Table des voitures mise à jour avec " + i + " voitures");

        // Mettre à jour les images des voitures dans l'onglet Véhicule
        updateCarImages(cars);
    }

    @Override
    public void updateClientTable(List<Client> clients) {
        // Créer un nouveau modèle de table avec les données des clients
        Object[][] data = new Object[clients.size()][7];
        int i = 0;
        for (Client client : clients) {
            data[i][0] = client.getIdClient();
            data[i][1] = client.getName();
            data[i][2] = client.getSurname();
            data[i][3] = client.getEmail();
            data[i][4] = client.getLicenseNumber();
            // Utiliser DateFormatter pour formater la date de naissance
            data[i][5] = client.getBirthDate() != null ? DateFormatter.format(client.getBirthDate()) : "N/A";
            data[i][6] = client.getPhoneNumber();
            i++;
        }

        // Mettre à jour la table avec les nouvelles données
        String[] columns = {"ID Client", "Nom", "Prénom", "Email","Numéro de permis", "Date de naissance", "Téléphone"};
        table2.setModel(new javax.swing.table.DefaultTableModel(data, columns));
        System.out.println("Table des clients mise à jour avec " + i + " clients");
    }

    @Override
    public void updateReservationTable(List<Reservation> reservations) {
        // Créer un nouveau modèle de table avec les données des réservations
        Object[][] data = new Object[reservations.size()][8];
        int i = 0;
        for (Reservation reservation : reservations) {
            data[i][0] = reservation.getIdReservation();


            // Immatriculation de la voiture (nouvel attribut)
            data[i][1] = reservation.getCarRegistration() != null ? reservation.getCarRegistration() : "N/A";

            // Nom complet du client (nouvel attribut)
            data[i][2] = reservation.getClientFullName() != null ? reservation.getClientFullName() : "N/A";

            // Dates et autres informations - Utiliser DateFormatter pour formater les dates
            data[i][3] = reservation.getStartDate() != null ? DateFormatter.format(reservation.getStartDate()) : "N/A";
            data[i][4] = reservation.getEndDate() != null ? DateFormatter.format(reservation.getEndDate()) : "N/A";
            data[i][5] = reservation.getResponsable();
            data[i][6] = reservation.getPrice();
            data[i][7] = reservation.getNotes();
            i++;
        }

        // Mettre à jour la table avec les nouvelles données
        String[] columns = {"ID Réservation", "Immatriculation",  "Nom Complet", "Date Début", "Date Fin", "Responsable", "Prix Total", "Notes"};
        table3.setModel(new javax.swing.table.DefaultTableModel(data, columns));
        System.out.println("Table des réservations mise à jour avec " + i + " réservations");
    }

    /**
     * Récupère la voiture sélectionnée dans la table des voitures
     * @return La voiture sélectionnée ou null si aucune sélection
     */
    @Override
    public Car getSelectedCar() {
        int selectedRow = table1.getSelectedRow();
        if (selectedRow >= 0) {
            // Récupérer l'ID de la voiture sélectionnée
            String idCar = (String) table1.getValueAt(selectedRow, 0);

            // Demander au contrôleur de récupérer la voiture complète
            return controller.getCarById(idCar);
        }
        return null;
    }

    /**
     * Affiche le formulaire de modification d'une voiture
     * @param car La voiture à modifier
     */
    @Override
    public void showModifyCarFormFromController(Car car) {
        if (car != null) {
            ModifyCarForm modifyCarForm = new ModifyCarForm(this, controller, car);
            boolean carModified = modifyCarForm.showForm();

            if (carModified) {
                // Mettre à jour la table des voitures
                List<Car> cars = controller.getAllCars();
                updateCarTable(cars);
            }
        }
    }

    @Override
    public Car promptAddCar() {
        // Créer une nouvelle instance du formulaire pour éviter les problèmes de données
        addCarForm = new AddCarForm(this, controller);
        addCarForm.showForm(); // Afficher le formulaire
        addCarForm.setVisible(true);
        Car car = addCarForm.getCar();
        return car;
    }

    /**
     * Affiche le formulaire d'ajout de client et retourne le client créé
     * @return Le client créé ou null si annulé
     */
    public Client promptAddClient() {
        // Créer une nouvelle instance du formulaire pour éviter les problèmes de données
        addClientForm = new AddClientForm(this, controller);
        addClientForm.showForm();
        addClientForm.setVisible(true);
        Client client = addClientForm.getClient();
        return client;
    }

    /**
     * Affiche le formulaire d'ajout de location et retourne la réservation créée
     * @return La réservation créée ou null si annulée
     */
    public Reservation promptAddLocation() {
        // Créer une nouvelle instance du formulaire pour éviter les problèmes de données
        addLocationForm = new AddLocationForm(this, controller);
        addLocationForm.showForm();
        addLocationForm.setVisible(true);
        Reservation reservation = addLocationForm.getReservation();
        return reservation;
    }

    @Override
    public Contrat promptAddContrat() {
        addContratForm = new AddContratForm(this, controller);
        addContratForm.showForm();

        // Récupérer les réservations existantes et les charger dans le formulaire
        List<Reservation> reservations = controller.getAllReservations();
        addContratForm.loadReservations(reservations);

        addContratForm.setVisible(true);
        Contrat contrat = addContratForm.getContrat();

        // Si un contrat a été créé et associé à une réservation, mettre à jour la réservation
        if (contrat != null && addContratForm.getSelectedReservation() != null) {
            Reservation selectedReservation = addContratForm.getSelectedReservation();
            selectedReservation.setContrat(contrat);
            controller.updateReservation(selectedReservation);
        }

        return contrat;
    }

    @Override
    public void updateContratTable(List<Contrat> contrats) {
        // Créer un modèle de table pour les contrats
        DefaultTableModel model = new DefaultTableModel();

        // Définir les colonnes
        model.addColumn("ID Contrat");
        model.addColumn("Véhicule");
        model.addColumn("Client");
        model.addColumn("Caution");
        model.addColumn("Type Assurance");
        model.addColumn("Options");
        model.addColumn("Signé");
        model.addColumn("Statut");
        model.addColumn("Prix Total");

        // Ajouter les données des contrats
        for (Contrat contrat : contrats) {
            // Préparer les informations du véhicule et du client
            String vehiculeInfo = contrat.getCarBrand() + " " + contrat.getCarModel();
            String clientInfo = contrat.getClientName() + " " + contrat.getClientSurname();

            // Formater les options en une chaîne lisible
            String optionsStr = String.join(", ", contrat.getOptions());

            // Ajouter une ligne pour chaque contrat
            model.addRow(new Object[] {
                    contrat.getIdContrat(),
                    vehiculeInfo,
                    clientInfo,
                    contrat.getCaution() + " €",
                    contrat.getTypeAssurance(),
                    optionsStr,
                    contrat.isEstSigne() ? "Oui" : "Non",
                    contrat.getStatutContrat().toString(),
                    contrat.getPrixTotal()
            });
        }

        // Appliquer le modèle à la table
        table4.setModel(model);

        // Ajuster la largeur des colonnes
        table4.getColumnModel().getColumn(0).setPreferredWidth(80);  // ID Contrat
        table4.getColumnModel().getColumn(1).setPreferredWidth(150); // Véhicule
        table4.getColumnModel().getColumn(2).setPreferredWidth(150); // Client
        table4.getColumnModel().getColumn(3).setPreferredWidth(80);  // Caution
        table4.getColumnModel().getColumn(4).setPreferredWidth(120); // Type Assurance
        table4.getColumnModel().getColumn(5).setPreferredWidth(200); // Options
        table4.getColumnModel().getColumn(6).setPreferredWidth(60);  // Signé
        table4.getColumnModel().getColumn(7).setPreferredWidth(100); // Statut

        // Appliquer le style à la table
        stylizeTable(table4);
    }

    /**
     * Récupère le contrat sélectionné dans la table des contrats
     * @return Le contrat sélectionné ou null si aucun contrat n'est sélectionné
     */
    public Contrat getSelectedContrat() {
        int selectedRow = table4.getSelectedRow();
        if (selectedRow >= 0) {
            // Récupérer l'ID du contrat sélectionné
            String idContrat = table4.getValueAt(selectedRow, 0).toString();

            // Demander au contrôleur de récupérer le contrat complet
            return controller.getContratById(idContrat);
        }
        return null;
    }

    /**
     * Affiche le formulaire de modification d'un contrat
     * @param contrat Le contrat à modifier
     */
    public void showModifyContratFormFromController(Contrat contrat) {
        if (contrat != null) {
            // Créer une instance du formulaire de modification
            ModifyContratForm modifyContratForm = new ModifyContratForm(this, controller, contrat);

            // Initialiser le formulaire d'abord pour que les composants soient créés
            modifyContratForm.showForm();

            // Récupérer les réservations et les charger dans le formulaire après l'initialisation
            List<Reservation> reservations = controller.getAllReservations();
            modifyContratForm.loadReservations(reservations);

            // Afficher le formulaire
            modifyContratForm.setVisible(true);

            // Récupérer le contrat modifié
            Contrat modifiedContrat = modifyContratForm.getContrat();

            // Si un contrat a été modifié, mettre à jour la réservation associée
            if (modifiedContrat != null && modifyContratForm.getSelectedReservation() != null) {
                Reservation selectedReservation = modifyContratForm.getSelectedReservation();
                controller.updateContrat(modifiedContrat);
            }
        }
    }

    /**
     * Affiche le formulaire de modification d'un client
     * @param client Le client à modifier
     */
    public void showModifyClientFormFromController(Client client) {
        if (client != null) {
            ModifyClientForm modifyClientForm = new ModifyClientForm(this, controller, client);
            boolean clientModified = modifyClientForm.showForm();

            if (clientModified) {
                // Mettre à jour la table des clients
                List<Client> clients = controller.getAllClients();
                updateClientTable(clients);
            }
        }
    }

    /**
     * Affiche le formulaire de modification d'une réservation
     * @param reservation La réservation à modifier
     */
    @Override
    public void showModifyLocationFormFromController(Reservation reservation) {
        if (reservation != null) {
            // Créer et afficher le formulaire de modification
            ModifyLocationForm modifyLocationForm = new ModifyLocationForm(this, controller, reservation);
            Reservation modifiedReservation = modifyLocationForm.showForm();

            // Si une réservation modifiée est retournée, mettre à jour la réservation
            if (modifiedReservation != null) {
                // Mettre à jour la réservation dans le modèle
                controller.updateReservation(modifiedReservation);
            }
        }
    }

    /**
     * Récupère la liste des voitures pour les formulaires
     * @return La liste des voitures
     */
    public List<Car> getCarList() {
        List<Car> cars = new ArrayList<>();
        // Récupérer les données du tableau des voitures
        if (table1 != null && table1.getModel() instanceof DefaultTableModel) {
            DefaultTableModel model = (DefaultTableModel) table1.getModel();
            int rowCount = model.getRowCount();
            System.out.println("Nombre de lignes dans le tableau des voitures: " + rowCount);

            // Afficher les noms des colonnes pour débogage
            int columnCount = model.getColumnCount();
            System.out.println("Nombre de colonnes: " + columnCount);
            for (int j = 0; j < columnCount; j++) {
                System.out.println("Colonne " + j + ": " + model.getColumnName(j));
            }

            for (int i = 0; i < rowCount; i++) {
                try {
                    // Créer une voiture à partir des données du tableau
                    String idCar = (String) model.getValueAt(i, 0);
                    String brand = (String) model.getValueAt(i, 1);
                    String modelName = (String) model.getValueAt(i, 2);
                    int year = Integer.parseInt(model.getValueAt(i, 3).toString());

                    // Extraction du prix numérique de la chaîne (en supprimant "€/jour")
                    String priceString = model.getValueAt(i, 4).toString();
                    float priceday;
                    if (priceString.contains("€")) {
                        priceString = priceString.replace("€/jour", "").trim();
                        priceday = Float.parseFloat(priceString);
                    } else {
                        priceday = Float.parseFloat(priceString);
                    }

                    // Correction: conversion de String en boolean
                    boolean available = false;
                    Object availableObj = model.getValueAt(i, 9);
                    if (availableObj instanceof Boolean) {
                        available = (Boolean) availableObj;
                    } else if (availableObj instanceof String) {
                        String availableStr = availableObj.toString();
                        available = availableStr.equalsIgnoreCase("Disponible") || availableStr.equalsIgnoreCase("true");
                    }

                    Car car = new Car(idCar, brand, modelName, year, priceday);
                    car.setAvailable(available);
                    cars.add(car);

                    System.out.println("Voiture ajoutée: " + idCar + " - " + brand + " " + modelName);
                } catch (Exception e) {
                    System.out.println("Erreur lors de la récupération de la voiture à la ligne " + i + ": " + e.getMessage());
                    e.printStackTrace();
                }
            }
        } else {
            System.out.println("Le tableau des voitures est vide ou n'est pas un DefaultTableModel");
        }
        System.out.println("Nombre total de voitures récupérées: " + cars.size());
        return cars;
    }

    /**
     * Récupère la liste des clients pour les formulaires
     * @return La liste des clients
     */
    public List<Client> getClientList() {
        List<Client> clients = new ArrayList<>();
        // Récupérer les données du tableau des clients
        if (table2 != null && table2.getModel() instanceof DefaultTableModel) {
            DefaultTableModel model = (DefaultTableModel) table2.getModel();
            int rowCount = model.getRowCount();
            for (int i = 0; i < rowCount; i++) {
                // Créer un client à partir des données du tableau
                int idClient = Integer.parseInt(model.getValueAt(i, 0).toString());
                String name = (String) model.getValueAt(i, 1);
                String surname = (String) model.getValueAt(i, 2);
                String email = (String) model.getValueAt(i, 3);
                String password = (String) model.getValueAt(i, 4);

                Client client = new Client();
                client.setIdClient(idClient);
                client.setName(name);
                client.setSurname(surname);
                client.setEmail(email);
                clients.add(client);
            }
        }
        return clients;
    }

    private JButton createModernButton(String text, Color bg, Color fg, Color hover, String iconName) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setBackground(bg);
        button.setForeground(fg);
        button.setFont(new Font("Segoe UI", Font.BOLD, 15));
        button.setBorder(BorderFactory.createEmptyBorder(10, 24, 10, 24));
        button.setUI(new BasicButtonUI());
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setOpaque(true);
        button.setBorder(BorderFactory.createLineBorder(bg.darker(), 2, true));

        // Ajouter une icône si spécifiée
        if (iconName != null && !iconName.isEmpty()) {
            try {
                String iconPath = "/icons/" + iconName + ".png";
                ImageIcon originalIcon = new ImageIcon(getClass().getResource(iconPath));

                // Redimensionner l'icône pour qu'elle s'adapte au bouton
                Image originalImage = originalIcon.getImage();
                Image resizedImage = originalImage.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
                button.setIcon(new ImageIcon(resizedImage));
                button.setIconTextGap(10);
            } catch (Exception e) {
                System.out.println("Erreur lors du chargement de l'icône " + iconName + ": " + e.getMessage());
            }
        }

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(hover);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(bg);
            }
        });
        return button;
    }

    // --- UTILITAIRE : Panel moderne ---
    private JPanel createModernPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(tailleBorder, tailleBorder, tailleBorder, tailleBorder),
                BorderFactory.createLineBorder(new Color(230, 232, 240), 2, true)));
        panel.setBackground(new Color(245, 247, 250));
        return panel;
    }

    // --- UTILITAIRE : JTable moderne ---
    private void stylizeTable(JTable table) {
        table.setRowHeight(28);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 16));
        table.getTableHeader().setBackground(new Color(80, 90, 170));
        table.getTableHeader().setForeground(Color.WHITE);
        table.setGridColor(new Color(230, 232, 240));
        table.setSelectionBackground(new Color(120, 130, 200));
        table.setSelectionForeground(Color.WHITE);
        table.setShowHorizontalLines(true);
        table.setShowVerticalLines(false);
        table.setIntercellSpacing(new Dimension(0, 0));
    }

    @Override
    public void updateCarImages(List<Car> cars) {
        panel4.removeAll();

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new WrapLayout(FlowLayout.CENTER, 30, 30));
        contentPanel.setBackground(new Color(245, 247, 250));

        for (Car car : cars) {
            JPanel carPanel = new JPanel(new BorderLayout());
            carPanel.setBackground(Color.WHITE);
            carPanel.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(230, 232, 240), 1, true),
                    BorderFactory.createEmptyBorder(10, 10, 10, 10)
            ));
            carPanel.setPreferredSize(new Dimension(330, 280));

            // Image
            JLabel imageLabel = new JLabel();
            imageLabel.setHorizontalAlignment(JLabel.CENTER);
            imageLabel.setOpaque(true);
            imageLabel.setBackground(new Color(248, 249, 250));
            imageLabel.setPreferredSize(new Dimension(330, 200));

            String imagePath = car.getImage();
            if (imagePath != null && !imagePath.isEmpty()) {
                try {
                    ImageIcon originalIcon = new ImageIcon(imagePath);
                    Image resizedImage = originalIcon.getImage().getScaledInstance(330, 200, Image.SCALE_SMOOTH);
                    imageLabel.setIcon(new ImageIcon(resizedImage));
                } catch (Exception e) {
                    imageLabel.setText("Image non disponible");
                }
            } else {
                imageLabel.setText("Pas d'image");
            }

            // Titre + sous-titre
            JLabel title = new JLabel(car.getBrand() + " " + car.getModel() + " (" + car.getYear() + ")");
            title.setFont(new Font("Segoe UI", Font.BOLD, 16));
            title.setHorizontalAlignment(JLabel.CENTER);

            JLabel subtitle = new JLabel(car.getPriceday() + " €/jour · " + (car.isAvailable() ? "Disponible" : "Indisponible"));
            if (car.isAvailable()) {
                subtitle.setForeground(new Color(0, 153, 0)); // Vert
            } else {
                subtitle.setForeground(Color.RED);
            }
            subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 13));

            subtitle.setHorizontalAlignment(JLabel.CENTER);

            JPanel textPanel = new JPanel();
            textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
            textPanel.setBackground(Color.WHITE);
            textPanel.add(Box.createVerticalStrut(10));
            textPanel.add(title);
            textPanel.add(Box.createVerticalStrut(5));
            textPanel.add(subtitle);

            carPanel.add(imageLabel, BorderLayout.NORTH);
            carPanel.add(textPanel, BorderLayout.CENTER);

            // Effet hover
            carPanel.addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) {
                    carPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
                    carPanel.setBorder(BorderFactory.createLineBorder(new Color(160, 180, 255), 2));
                }

                public void mouseExited(MouseEvent e) {
                    carPanel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                    carPanel.setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createLineBorder(new Color(230, 232, 240), 1, true),
                            BorderFactory.createEmptyBorder(10, 10, 10, 10)
                    ));
                }

                public void mouseClicked(MouseEvent e) {
                    showCarDetails(car);
                }
            });

            contentPanel.add(carPanel);
        }

        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUnitIncrement(25);

        panel4.setLayout(new BorderLayout());
        panel4.add(scrollPane, BorderLayout.CENTER);
        panel4.revalidate();
        panel4.repaint();
    }

    /**
     * Affiche les détails d'une voiture dans une fenêtre modale
     * @param car La voiture dont on veut afficher les détails
     */
    @Override
    public void showCarDetails(Car car) {
        if (car == null) return;

        JDialog detailsDialog = new JDialog(this, "Détails du véhicule", true);
        detailsDialog.setSize(850, 550);
        detailsDialog.setLocationRelativeTo(this);
        detailsDialog.setLayout(new BorderLayout());

        // Bandeau supérieur
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        headerPanel.setBackground(new Color(65, 105, 225));
        headerPanel.setPreferredSize(new Dimension(800, 60));

        JLabel headerTitle = new JLabel(car.getBrand() + " " + car.getModel());
        headerTitle.setForeground(Color.WHITE);
        headerTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        headerPanel.add(headerTitle);
        detailsDialog.add(headerPanel, BorderLayout.NORTH);

        // Panel principal
        JPanel mainPanel = new JPanel(new BorderLayout(20, 20));
        mainPanel.setBackground(new Color(245, 247, 250));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Panel gauche : image
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBackground(Color.WHITE);
        leftPanel.setPreferredSize(new Dimension(350, 300));
        leftPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        JLabel imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        imageLabel.setOpaque(true);
        imageLabel.setBackground(new Color(248, 249, 250));
        imageLabel.setPreferredSize(new Dimension(330, 250));

        String imagePath = car.getImage();
        if (imagePath != null && !imagePath.isEmpty()) {
            try {
                ImageIcon originalIcon = new ImageIcon(imagePath);
                Image resizedImage = originalIcon.getImage().getScaledInstance(330, 250, Image.SCALE_SMOOTH);
                imageLabel.setIcon(new ImageIcon(resizedImage));
            } catch (Exception e) {
                imageLabel.setText("Image non disponible");
            }
        } else {
            imageLabel.setText("Pas d'image");
        }
        leftPanel.add(imageLabel, BorderLayout.CENTER);

        // Panel droit : infos
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBackground(Color.WHITE);
        rightPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(230, 232, 240), 1, true),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        String[][] data = {
                {"Immatriculation", car.getIdCar()},
                {"Année", String.valueOf(car.getYear())},
                {"Prix par jour", car.getPriceday() + " €"},
                {"Kilométrage", car.getMileage() + " km"},
                {"Carburant", car.getFuelType()},
                {"Transmission", car.getTransmission()},
                {"Nombre de places", String.valueOf(car.getSeats())},
                {"Disponibilité", car.isAvailable() ? "Disponible" : "Indisponible"}
        };

        JPanel infoPanel = new JPanel(new GridLayout(data.length, 2, 10, 15));
        infoPanel.setBackground(Color.WHITE);

        for (String[] row : data) {
            JLabel keyLabel = new JLabel(row[0]);
            keyLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
            keyLabel.setForeground(new Color(80, 80, 80));

            JLabel valueLabel = new JLabel(row[1]);
            valueLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            valueLabel.setForeground(new Color(50, 50, 50));

            infoPanel.add(keyLabel);
            infoPanel.add(valueLabel);
        }

        rightPanel.add(infoPanel);
        mainPanel.add(leftPanel, BorderLayout.WEST);
        mainPanel.add(rightPanel, BorderLayout.CENTER);

        // Panel boutons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(new Color(245, 247, 250));

        JButton closeButton = new JButton("Fermer");
        closeButton.setFocusPainted(false);
        closeButton.setBackground(new Color(220, 53, 69));
        closeButton.setForeground(Color.WHITE);
        closeButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        closeButton.setPreferredSize(new Dimension(100, 35));
        closeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        closeButton.addActionListener(e -> detailsDialog.dispose());
        buttonPanel.add(closeButton);

        detailsDialog.add(mainPanel, BorderLayout.CENTER);
        detailsDialog.add(buttonPanel, BorderLayout.SOUTH);
        detailsDialog.setVisible(true);
    }


    /**
     * Récupère le client sélectionné dans la table des clients
     * @return Le client sélectionné ou null si aucun client n'est sélectionné
     */
    @Override
    public Client getSelectedClient() {
        int selectedRow = table2.getSelectedRow();
        if (selectedRow >= 0) {
            // Récupérer l'ID du client sélectionné
            int idClient = Integer.parseInt(table2.getValueAt(selectedRow, 0).toString());

            // Demander au contrôleur de récupérer le client complet
            return controller.getClientById(idClient);
        }
        return null;
    }

    /**
     * Récupère la réservation sélectionnée dans la table des réservations
     * @return La réservation sélectionnée ou null si aucune réservation n'est sélectionnée
     */
    @Override
    public Reservation getSelectedReservation() {
        int selectedRow = table3.getSelectedRow();
        if (selectedRow >= 0) {
            // Récupérer l'ID de la réservation sélectionnée
            int idReservation = Integer.parseInt(table3.getValueAt(selectedRow, 0).toString());

            // Demander au contrôleur de récupérer la réservation complète
            return controller.getReservationById(idReservation);
        }
        return null;
    }

    @Override
    public ImprovedSessionDialog getSessionDialog() {
        if (sessionDialog == null) {
            sessionDialog = new ImprovedSessionDialog(this, controller);
        }
        return sessionDialog;
    }

    @Override
    public void lockInterface() {
        // Garder l'interface visible mais désactiver les fonctionnalités

        // Désactiver les boutons d'action
        buttonAddCar.setEnabled(false);
        buttonModifyCar.setEnabled(false);
        buttonDeleteCar.setEnabled(false);
        buttonAddClient.setEnabled(false);
        buttonModifyClient.setEnabled(false);
        buttonDeleteClient.setEnabled(false);
        buttonAddLocation.setEnabled(false);
        buttonModifyLocation.setEnabled(false);
        buttonDeleteLocation.setEnabled(false);
        buttonAddContrat.setEnabled(false);
        buttonModifyContrat.setEnabled(false);
        buttonDeleteContrat.setEnabled(false);

        // Désactiver les tables pour qu'elles ne soient pas interactives
        table1.setEnabled(false);
        table2.setEnabled(false);
        table3.setEnabled(false);
        table4.setEnabled(false);

        // Changer le texte du bouton de session
        sessionButton.setText("Se connecter");
    }

    @Override
    public void unlockInterface() {
        // Activer les boutons d'action
        buttonAddCar.setEnabled(true);
        buttonModifyCar.setEnabled(true);
        buttonDeleteCar.setEnabled(true);
        buttonAddClient.setEnabled(true);
        buttonModifyClient.setEnabled(true);
        buttonDeleteClient.setEnabled(true);
        buttonAddLocation.setEnabled(true);
        buttonModifyLocation.setEnabled(true);
        buttonDeleteLocation.setEnabled(true);
        buttonAddContrat.setEnabled(true);
        buttonModifyContrat.setEnabled(true);
        buttonDeleteContrat.setEnabled(true);

        // Activer les tables pour qu'elles soient interactives
        table1.setEnabled(true);
        table2.setEnabled(true);
        table3.setEnabled(true);
        table4.setEnabled(true);

        // Changer le texte du bouton de session
        sessionButton.setText("Déconnexion");

        // Rafraîchir l'interface
        revalidate();
        repaint();
    }

    @Override
    public void clearAllTables() {
        try {
            // Créer des modèles vides pour chaque table
            String[] carColumns = {"ID Car", "Marque", "Modèle", "Année", "Prix/jour", "Kilometrages", "Carburant", "Transmission", "Places", "Disponibilité"};
            table1.setModel(new DefaultTableModel(new Object[0][carColumns.length], carColumns));

            String[] clientColumns = {"ID Client", "Nom", "Prénom", "Email","Numéro de permis", "Date de naissance", "Téléphone"};
            table2.setModel(new DefaultTableModel(new Object[0][clientColumns.length], clientColumns));

            String[] locationColumns = {"ID Réservation", "Véhicule", "Immatriculation", "Client", "Nom Complet", "Date Début", "Date Fin", "Responsable", "Prix Total", "Notes"};
            table3.setModel(new DefaultTableModel(new Object[0][locationColumns.length], locationColumns));

            String[] contratColumns = {"ID Contrat", "Type Assurance", "Caution", "Détails"};
            table4.setModel(new DefaultTableModel(new Object[0][contratColumns.length], contratColumns));

            // Vider également la section d'affichage des voitures (panel4)
            if (panel4 != null) {
                panel4.removeAll();
                panel4.revalidate();
                panel4.repaint();
                System.out.println("Panel d'affichage des images de voitures vidé");
            }

            // Vider également la section d'affichage des voitures dans panel5 si elle existe
            if (panel5 != null && panel5.getComponentCount() > 0) {
                Component comp = panel5.getComponent(0);
                if (comp instanceof JPanel) {
                    JPanel carDisplayPanel = (JPanel) comp;
                    carDisplayPanel.removeAll();
                    carDisplayPanel.revalidate();
                    carDisplayPanel.repaint();
                }
            }

            System.out.println("Toutes les tables ont été vidées");
        } catch (Exception e) {
            System.err.println("Erreur lors du vidage des tables : " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Met à jour les listeners des menus pour utiliser le controller courant
     * Cette méthode est appelée après l'initialisation du controller
     */
    private void updateMenuListeners() {
        // Vérifier si le controller est disponible
        if (controller == null) return;

        // Récupérer la barre de menu
        JMenuBar menuBar = getJMenuBar();
        if (menuBar == null) return;

        // Parcourir tous les menus et sous-menus pour trouver les éléments d'import/export
        for (int i = 0; i < menuBar.getMenuCount(); i++) {
            JMenu menu = menuBar.getMenu(i);
            if (menu != null) {
                // Traiter chaque menu de façon récursive
                attachListenersToMenu(menu);
            }
        }

        System.out.println("Listeners des menus mis à jour avec le controller");
    }

    /**
     * Attache les listeners aux éléments de menu d'import/export
     */
    private void attachListenersToMenu(JMenu menu) {
        // Parcourir tous les éléments du menu
        for (int i = 0; i < menu.getItemCount(); i++) {
            JMenuItem item = menu.getItem(i);

            // Ignorer les éléments null
            if (item == null) continue;

            // Traiter les sous-menus récursivement
            if (item instanceof JMenu) {
                attachListenersToMenu((JMenu) item);
            }
            // Traiter les éléments d'import/export
            else {
                String cmd = item.getActionCommand();
                if (cmd != null && (cmd.startsWith("EXPORT_") || cmd.startsWith("IMPORT_"))) {
                    // Ajouter le controller comme listener
                    item.addActionListener(controller);
                    System.out.println("Listener ajouté pour: " + cmd);
                }
            }
        }
    }

    /**
     * Récupère les valeurs de connexion (email, mot de passe)
     * @return Un tableau contenant l'email et le mot de passe
     */
    @Override
    public String[] getLoginValues() {
        if (sessionDialog != null) {
            String email = sessionDialog.getEmail();
            String password = sessionDialog.getPassword();
            if (email != null && !email.isEmpty() && password != null && !password.isEmpty()) {
                return new String[]{email, password};
            }
        }
        return null;
    }

    /**
     * Récupère les valeurs d'inscription (email, mot de passe, confirmation)
     * @return Un tableau contenant l'email, le mot de passe et la confirmation
     */
    @Override
    public String[] getRegisterValues() {
        if (sessionDialog != null) {
            String email = sessionDialog.getEmail();
            String password = sessionDialog.getPassword();
            String confirmPassword = sessionDialog.getConfirmPassword();
            if (email != null && !email.isEmpty() &&
                    password != null && !password.isEmpty() &&
                    confirmPassword != null && !confirmPassword.isEmpty()) {
                return new String[]{email, password, confirmPassword};
            }
        }
        return null;
    }

    /**
     * Affiche une boîte de dialogue pour sélectionner un fichier
     * @param title Titre de la boîte de dialogue
     * @param extensions Extensions de fichiers acceptées
     * @return Le chemin du fichier sélectionné ou null si annulé
     */
    @Override
    public String promptForFilePath(String title, String... extensions) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle(title);
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setAcceptAllFileFilterUsed(false);

        // Créer un filtre pour les extensions spécifiées
        StringBuilder description = new StringBuilder("Fichiers (");
        for (int i = 0; i < extensions.length; i++) {
            description.append("*.").append(extensions[i]);
            if (i < extensions.length - 1) {
                description.append(", ");
            }
        }
        description.append(")");

        javax.swing.filechooser.FileNameExtensionFilter filter =
                new javax.swing.filechooser.FileNameExtensionFilter(description.toString(), extensions);
        fileChooser.addChoosableFileFilter(filter);

        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile().getAbsolutePath();
        }
        return null;
    }

    /**
     * Affiche une boîte de dialogue pour sauvegarder un fichier
     * @param title Titre de la boîte de dialogue
     * @param extension Extension de fichier par défaut
     * @return Le chemin du fichier à sauvegarder ou null si annulé
     */
    @Override
    public String promptForSaveFilePath(String title, String extension) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle(title);
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setAcceptAllFileFilterUsed(false);

        // Créer un filtre pour l'extension spécifiée
        String description = "Fichiers (*." + extension + ")";
        javax.swing.filechooser.FileNameExtensionFilter filter =
                new javax.swing.filechooser.FileNameExtensionFilter(description, extension);
        fileChooser.addChoosableFileFilter(filter);

        int result = fileChooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile().getAbsolutePath();
        }
        return null;
    }

    /**
     * Change le thème de l'application
     * @param themeName Le nom du thème à appliquer ("light" ou "dark")
     */
    private void changeTheme(String themeName) {
        try {
            if ("dark".equals(themeName)) {
                // Appliquer le thème Dracula (sombre)
                FlatDarculaLaf.setup();
            } else {
                // Appliquer le thème clair (par défaut)
                FlatLightLaf.setup();
                FlatIconColors.values();
            }

            // Mettre à jour tous les composants de l'interface
            SwingUtilities.updateComponentTreeUI(this);

            // Mettre à jour les dialogues s'ils sont ouverts
            if (sessionDialog != null) {
                SwingUtilities.updateComponentTreeUI(sessionDialog);
            }

            // Comme les formulaires ne sont pas des composants Swing directs,
            // nous devons recréer les formulaires si nécessaire lors de leur prochaine utilisation

            // Informer l'utilisateur du changement de thème
            JOptionPane.showMessageDialog(this,
                    "Le thème " + (themeName.equals("dark") ? "Dracula" : "Clair") + " a été appliqué avec succès.",
                    "Changement de thème", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Erreur lors du changement de thème: " + ex.getMessage(),
                    "Erreur", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    /**
     * Change le format d'affichage des dates dans l'application
     * @param format Le nouveau format à utiliser
     */
    private void changeDateFormat(String format) {
        // Changer le format dans la classe utilitaire
        DateFormatter.setDateFormat(format);

        // Mettre à jour les tables pour refléter le nouveau format
        if (controller != null) {
            controller.updateAllTables();
        }

        // Informer l'utilisateur
        showMessage("Format de date changé en : " + format);
    }

    /**
     * Retourne le texte de recherche
     * @return Le texte de recherche
     */
    public String getSearchQuery() {
        return searchField != null ? searchField.getText() : "";
    }
    
    /**
     * Retourne l'index de l'onglet actif
     * @return L'index de l'onglet actif
     */
    public int getActiveTabIndex() {
        return tabbedPane != null ? tabbedPane.getSelectedIndex() : -1;
    }
    
    /**
     * Affiche les voitures filtrées dans le tableau
     * @param cars Liste des voitures à afficher
     */
    @Override
    public void displayCars(List<Car> cars) {
        DefaultTableModel model = (DefaultTableModel) table1.getModel();
        model.setRowCount(0); // Effacer toutes les lignes
        
        for (Car car : cars) {
            model.addRow(new Object[]{
                car.getId(),
                car.getBrand(),
                car.getModel(),
                car.(),
                car.getYear(),
                car.getPrice(),
                car.isAvailable() ? "Disponible" : "Indisponible"
            });
        }
    }
    
    /**
     * Affiche les clients filtrés dans le tableau
     * @param clients Liste des clients à afficher
     */
    @Override
    public void displayClients(List<Client> clients) {
        DefaultTableModel model = (DefaultTableModel) table2.getModel();
        model.setRowCount(0); // Effacer toutes les lignes
        
        for (Client client : clients) {
            model.addRow(new Object[]{
                client.getId(),
                client.getFirstName(),
                client.getLastName(),
                client.getEmail(),
                client.getPhone(),
                client.getAddress()
            });
        }
    }
    
    /**
     * Affiche les réservations filtrées dans le tableau
     * @param reservations Liste des réservations à afficher
     */
    @Override
    public void displayReservations(List<Reservation> reservations) {
        DefaultTableModel model = (DefaultTableModel) table3.getModel();
        model.setRowCount(0); // Effacer toutes les lignes
        
        for (Reservation reservation : reservations) {
            model.addRow(new Object[]{
                reservation.getId(),
                DateFormatter.format(reservation.getStartDate()),
                DateFormatter.format(reservation.getEndDate()),
                reservation.getClientId(),
                reservation.getCarId(),
                reservation.getStatus()
            });
        }
    }
    
    /**
     * Affiche les contrats filtrés dans le tableau
     * @param contrats Liste des contrats à afficher
     */
    @Override
    public void displayContrats(List<Contrat> contrats) {
        DefaultTableModel model = (DefaultTableModel) table4.getModel();
        model.setRowCount(0); // Effacer toutes les lignes
        
        for (Contrat contrat : contrats) {
            model.addRow(new Object[]{
                contrat.getId(),
                contrat.getReservationId(),
                DateFormatter.format(contrat.getSignatureDate()),
                contrat.getTotalPrice()
            });
        }
    }
}
