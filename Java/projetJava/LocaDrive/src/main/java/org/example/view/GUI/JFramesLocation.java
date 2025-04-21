package org.example.view.GUI;

import org.example.controller.Controller;
import org.example.controller.ControllerActions;
import org.example.model.entity.Contrat;
import org.example.model.entity.Car;
import org.example.model.entity.Client;
import org.example.model.entity.Reservation;
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

    // Formulaires et dialogues
    private ImprovedSessionDialog sessionDialog;
    private AddCarForm addCarForm;
    private AddClientForm addClientForm;
    private AddLocationForm addLocationForm;
    private AddContratForm addContratForm;

    public JFramesLocation() {
        super("LocaDrive");
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) { e.printStackTrace(); }
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
        add(topPanel, BorderLayout.NORTH);

        // Création de la barre de menu
        createMenuBar();

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
            // Forcer le rafraîchissement de l'onglet Véhicule
            if (controller != null) {
                List<Car> cars = controller.getAllCars();
                if (cars != null) {
                    updateCarImages(cars);
                }
            }
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

    public void showAddCarFormFromController() {
        addCarForm.showForm();
    }

    public void showAddClientFormFromController() {
        addClientForm.showForm();
    }

    public void showAddLocationFormFromController() {
        addLocationForm.showForm();
    }

    // Implémentation des méthodes pour mettre à jour les tables
    @Override
    public void updateCarTable(List<Car> cars) {
        // Créer un nouveau modèle de table avec les données des voitures
        if (cars == null || cars.isEmpty()) {
            // Si la liste est vide ou null, vider la table
            String[] columns = {"ID Car", "Marque", "Modèle", "Année", "Prix/jour", "Kilometrages", "Carburant", "Transmission", "Places", "Disponibilité"};
            table1.setModel(new javax.swing.table.DefaultTableModel(new Object[0][10], columns));
            System.out.println("Table des voitures vidée (utilisateur déconnecté ou aucune voiture disponible)");

            // Vider également les images
            panel4.removeAll();
            panel4.revalidate();
            panel4.repaint();
            return;
        }

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
            data[i][5] = client.getBirthDate();
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
        Object[][] data = new Object[reservations.size()][8]; // Augmenter le nombre de colonnes pour inclure les nouveaux attributs
        int i = 0;
        for (Reservation reservation : reservations) {
            data[i][0] = reservation.getIdReservation();


            // Immatriculation de la voiture (nouvel attribut)
            data[i][1] = reservation.getCarRegistration() != null ? reservation.getCarRegistration() : "N/A";

            // Nom complet du client (nouvel attribut)
            data[i][2] = reservation.getClientFullName() != null ? reservation.getClientFullName() : "N/A";

            // Dates et autres informations
            data[i][3] = reservation.getStartDate();
            data[i][4] = reservation.getEndDate();
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
        addCarForm.showForm();
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
            try {
                // Récupérer l'ID du contrat sélectionné
                String idContrat = table4.getValueAt(selectedRow, 0).toString();

                // Afficher des informations de débogage
                System.out.println("Contrat sélectionné : ID = " + idContrat);

                // Demander au contrôleur de récupérer le contrat complet
                List<Contrat> contrats = controller.getAllContracts();
                System.out.println("Nombre de contrats récupérés : " + contrats.size());

                for (Contrat contrat : contrats) {
                    System.out.println("Comparaison avec contrat ID : " + contrat.getIdContrat());
                    if (contrat.getIdContrat().equals(idContrat)) {
                        return contrat;
                    }
                }

                // Si on arrive ici, c'est qu'on n'a pas trouvé le contrat
                System.out.println("Contrat non trouvé avec ID : " + idContrat);
            } catch (Exception e) {
                System.err.println("Erreur lors de la récupération du contrat sélectionné : " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.out.println("Aucune ligne sélectionnée dans la table des contrats");
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
            modifyContratForm.showForm();

            // Récupérer les réservations et les charger dans le formulaire
            List<Reservation> reservations = controller.getAllReservations();
            modifyContratForm.loadReservations(reservations);

            // Afficher le formulaire
            modifyContratForm.setVisible(true);

            // Récupérer le contrat modifié
            Contrat modifiedContrat = modifyContratForm.getContrat();

            // Si un contrat a été modifié, mettre à jour la réservation associée
            if (modifiedContrat != null && modifyContratForm.getSelectedReservation() != null) {
                Reservation selectedReservation = modifyContratForm.getSelectedReservation();
                controller.updateReservation(selectedReservation);
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

    // --- UTILITAIRE : Création de boutons modernes ---
    private JButton createModernButton(String text, Color bg, Color fg, Color hover) {
        return createModernButton(text, bg, fg, hover, null);
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
                ImageIcon resizedIcon = new ImageIcon(resizedImage);
                
                button.setIcon(resizedIcon);
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

    /**
     * Met à jour l'affichage des images des voitures dans l'onglet Véhicule
     * @param cars Liste des voitures à afficher
     */
    @Override
    public void updateCarImages(List<Car> cars) {
        // Vider le panel avant d'ajouter les nouvelles images
        panel4.removeAll();

        // Utiliser un JScrollPane pour permettre le défilement
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new WrapLayout(FlowLayout.LEFT, 15, 15));
        contentPanel.setBackground(new Color(245, 247, 250));

        for (Car car : cars) {
            // Créer un panel pour chaque voiture avec un effet de carte moderne
            JPanel carPanel = new JPanel(new BorderLayout());
            carPanel.setBackground(Color.WHITE);
            carPanel.setBorder(BorderFactory.createLineBorder(new Color(230, 232, 240), 1, true));
            carPanel.setPreferredSize(new Dimension(250, 280));

            // Ajouter un effet d'ombre (simulation avec des bordures)
            carPanel.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(230, 232, 240), 1, true),
                    BorderFactory.createCompoundBorder(
                            BorderFactory.createLineBorder(new Color(240, 242, 245), 2, true),
                            BorderFactory.createEmptyBorder(8, 8, 8, 8)
                    )
            ));

            // Créer un label pour l'image
            JLabel imageLabel = new JLabel();
            imageLabel.setHorizontalAlignment(JLabel.CENTER);
            imageLabel.setPreferredSize(new Dimension(230, 150));
            imageLabel.setBackground(new Color(248, 249, 250));
            imageLabel.setOpaque(true);

            // Charger l'image si elle existe
            String imagePath = car.getImage();
            if (imagePath != null && !imagePath.isEmpty()) {
                try {
                    // Charger l'image depuis le chemin
                    ImageIcon originalIcon = new ImageIcon(imagePath);

                    // Redimensionner l'image pour qu'elle s'adapte au panel
                    Image originalImage = originalIcon.getImage();
                    Image resizedImage = originalImage.getScaledInstance(230, 150, Image.SCALE_SMOOTH);
                    ImageIcon resizedIcon = new ImageIcon(resizedImage);

                    imageLabel.setIcon(resizedIcon);
                } catch (Exception e) {
                    imageLabel.setText("Image non disponible");
                    System.out.println("Erreur lors du chargement de l'image: " + e.getMessage());
                }
            } else {
                imageLabel.setText("Pas d'image");
            }

            // Créer un panel pour les informations de la voiture
            JPanel infoPanel = new JPanel();
            infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
            infoPanel.setBackground(Color.WHITE);

            // Titre (marque et modèle)
            JLabel titleLabel = new JLabel(car.getBrand() + " " + car.getModel());
            titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
            titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

            // Créer un tableau pour afficher toutes les informations
            String[][] data = {
                    {"Immatriculation", car.getIdCar()},
                    {"Année", String.valueOf(car.getYear())},
                    {"Prix par jour", car.getPriceday() + " €"},
                    {"Kilométrage", String.valueOf(car.getMileage()) + " km"},
                    {"Carburant", car.getFuelType()},
                    {"Transmission", car.getTransmission()},
                    {"Nombre de places", String.valueOf(car.getSeats())},
                    {"Disponibilité", car.isAvailable() ? "Disponible" : "Non disponible"}
            };

            // Panel pour contenir les informations
            JPanel infoContentPanel = new JPanel(new GridLayout(data.length, 2, 10, 10));
            infoContentPanel.setBackground(Color.WHITE);

            // Ajouter les informations au panel
            for (String[] row : data) {
                JLabel keyLabel = new JLabel(row[0] + ":");
                keyLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));

                JLabel valueLabel = new JLabel(row[1]);
                valueLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));

                infoContentPanel.add(keyLabel);
                infoContentPanel.add(valueLabel);
            }

            // Ajouter les composants au panel d'informations
            infoPanel.add(Box.createVerticalStrut(5));
            infoPanel.add(titleLabel);
            infoPanel.add(Box.createVerticalStrut(10));
            infoPanel.add(infoContentPanel);

            // Ajouter les composants au panel de la voiture
            carPanel.add(imageLabel, BorderLayout.NORTH);
            carPanel.add(infoPanel, BorderLayout.CENTER);

            // Créer un bouton invisible pour gérer l'action via le contrôleur
            JButton actionButton = new JButton();
            actionButton.setVisible(false);
            actionButton.setActionCommand(ControllerActions.SHOW_CAR_DETAILS);
            actionButton.putClientProperty("car", car);
            actionButton.addActionListener(controller);
            carPanel.add(actionButton);

            // Ajouter un effet de survol
            carPanel.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    carPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
                    carPanel.setBackground(new Color(250, 250, 255));
                    infoPanel.setBackground(new Color(250, 250, 255));
                }

                public void mouseExited(java.awt.event.MouseEvent evt) {
                    carPanel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                    carPanel.setBackground(Color.WHITE);
                    infoPanel.setBackground(Color.WHITE);
                }

                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    // Déclencher l'action via le bouton invisible pour que le contrôleur la gère
                    actionButton.doClick();
                }
            });

            // Ajouter le panel de la voiture au panel principal
            contentPanel.add(carPanel);
        }

        // Ajouter le panel de contenu au JScrollPane
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUnitIncrement(16); // Vitesse de défilement

        // Ajouter le JScrollPane au panel principal
        panel4.setLayout(new BorderLayout());
        panel4.add(scrollPane, BorderLayout.CENTER);

        // Rafraîchir l'affichage
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

        // Créer une boîte de dialogue modale pour afficher les détails
        JDialog detailsDialog = new JDialog(this, "Détails du véhicule", true);
        detailsDialog.setLayout(new BorderLayout());
        detailsDialog.setSize(800, 500);
        detailsDialog.setLocationRelativeTo(this);

        // Panel principal avec un fond clair
        JPanel mainPanel = new JPanel(new BorderLayout(20, 20));
        mainPanel.setBackground(new Color(245, 247, 250));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Panel gauche pour l'image
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBackground(Color.WHITE);
        leftPanel.setBorder(BorderFactory.createLineBorder(new Color(230, 232, 240), 1, true));
        leftPanel.setPreferredSize(new Dimension(350, 300));

        // Ajouter un effet d'ombre (simulation avec des bordures)
        leftPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(230, 232, 240), 1, true),
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(240, 242, 245), 2, true),
                        BorderFactory.createEmptyBorder(8, 8, 8, 8)
                )
        ));

        // Créer un label pour l'image
        JLabel imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        imageLabel.setPreferredSize(new Dimension(330, 250));
        imageLabel.setBackground(new Color(248, 249, 250));
        imageLabel.setOpaque(true);

        // Charger l'image si elle existe
        String imagePath = car.getImage();
        if (imagePath != null && !imagePath.isEmpty()) {
            try {
                // Charger l'image depuis le chemin
                ImageIcon originalIcon = new ImageIcon(imagePath);

                // Redimensionner l'image pour qu'elle s'adapte au panel
                Image originalImage = originalIcon.getImage();
                Image resizedImage = originalImage.getScaledInstance(330, 250, Image.SCALE_SMOOTH);
                ImageIcon resizedIcon = new ImageIcon(resizedImage);

                imageLabel.setIcon(resizedIcon);
            } catch (Exception e) {
                imageLabel.setText("Image non disponible");
                System.out.println("Erreur lors du chargement de l'image: " + e.getMessage());
            }
        } else {
            imageLabel.setText("Pas d'image");
        }

        leftPanel.add(imageLabel, BorderLayout.CENTER);

        // Panel droit pour les informations détaillées
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBackground(Color.WHITE);
        rightPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(230, 232, 240), 1, true),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        // Titre (marque et modèle)
        JLabel titleLabel = new JLabel(car.getBrand() + " " + car.getModel());
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Créer un tableau pour afficher toutes les informations
        String[][] data = {
                {"Immatriculation", car.getIdCar()},
                {"Année", String.valueOf(car.getYear())},
                {"Prix par jour", car.getPriceday() + " €"},
                {"Kilométrage", String.valueOf(car.getMileage()) + " km"},
                {"Carburant", car.getFuelType()},
                {"Transmission", car.getTransmission()},
                {"Nombre de places", String.valueOf(car.getSeats())},
                {"Disponibilité", car.isAvailable() ? "Disponible" : "Non disponible"}
        };

        // Panel pour contenir les informations
        JPanel infoPanel = new JPanel(new GridLayout(data.length, 2, 10, 10));
        infoPanel.setBackground(Color.WHITE);

        // Ajouter les informations au panel
        for (String[] row : data) {
            JLabel keyLabel = new JLabel(row[0] + ":");
            keyLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));

            JLabel valueLabel = new JLabel(row[1]);
            valueLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));

            infoPanel.add(keyLabel);
            infoPanel.add(valueLabel);
        }

        // Ajouter les composants au panel droit
        rightPanel.add(titleLabel);
        rightPanel.add(Box.createVerticalStrut(20));
        rightPanel.add(infoPanel);

        // Panel pour les boutons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(new Color(245, 247, 250));


        // Ajouter les panels au panel principal
        mainPanel.add(leftPanel, BorderLayout.WEST);
        mainPanel.add(rightPanel, BorderLayout.CENTER);

        // Ajouter le panel principal et le panel de boutons à la boîte de dialogue
        detailsDialog.add(mainPanel, BorderLayout.CENTER);
        detailsDialog.add(buttonPanel, BorderLayout.SOUTH);

        // Afficher la boîte de dialogue
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
            try {
                // Récupérer l'ID du client sélectionné
                int idClient = Integer.parseInt(table2.getValueAt(selectedRow, 0).toString());

                // Afficher des informations de débogage
                System.out.println("Client sélectionné : ID = " + idClient);

                // Demander au contrôleur de récupérer le client complet
                List<Client> clients = controller.getAllClients();
                System.out.println("Nombre de clients récupérés : " + clients.size());

                for (Client client : clients) {
                    System.out.println("Comparaison avec client ID : " + client.getIdClient());
                    if (client.getIdClient() == idClient) {
                        return client;
                    }
                }

                // Si on arrive ici, c'est qu'on n'a pas trouvé le client
                System.out.println("Client non trouvé avec ID : " + idClient);
            } catch (Exception e) {
                System.err.println("Erreur lors de la récupération du client sélectionné : " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.out.println("Aucune ligne sélectionnée dans la table des clients");
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
            try {
                // Récupérer l'ID de la réservation sélectionnée
                int idReservation = Integer.parseInt(table3.getValueAt(selectedRow, 0).toString());

                // Afficher des informations de débogage
                System.out.println("Réservation sélectionnée : ID = " + idReservation);

                // Demander au contrôleur de récupérer la réservation complète
                List<Reservation> reservations = controller.getAllReservations();
                System.out.println("Nombre de réservations récupérées : " + reservations.size());

                for (Reservation reservation : reservations) {
                    System.out.println("Comparaison avec réservation ID : " + reservation.getIdReservation());
                    if (reservation.getIdReservation() == idReservation) {
                        return reservation;
                    }
                }

                // Si on arrive ici, c'est qu'on n'a pas trouvé la réservation
                System.out.println("Réservation non trouvée avec ID : " + idReservation);
            } catch (Exception e) {
                System.err.println("Erreur lors de la récupération de la réservation sélectionnée : " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.out.println("Aucune ligne sélectionnée dans la table des réservations");
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

            String[] clientColumns = {"ID Client", "Nom", "Prénom", "Email", "Numéro de permis", "Date de naissance", "Téléphone"};
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
        if (controller == null) return;

        // Parcourir tous les menus pour trouver les éléments d'export et d'import
        JMenuBar menuBar = getJMenuBar();
        if (menuBar != null) {
            for (int i = 0; i < menuBar.getMenuCount(); i++) {
                JMenu menu = menuBar.getMenu(i);
                if (menu != null) {
                    updateMenuItemListeners(menu);
                }
            }
        }

        System.out.println("Listeners des menus mis à jour avec le controller");
    }

    /**
     * Met à jour récursivement les listeners des éléments de menu
     */
    private void updateMenuItemListeners(JMenu menu) {
        for (int i = 0; i < menu.getItemCount(); i++) {
            JMenuItem item = menu.getItem(i);

            if (item instanceof JMenu) {
                // Si c'est un sous-menu, appel récursif
                updateMenuItemListeners((JMenu) item);
            } else if (item != null) {
                // Si le nom de commande commence par "EXPORT_" ou "IMPORT_", mettre à jour le listener
                String actionCommand = item.getActionCommand();
                if (actionCommand != null &&
                        (actionCommand.startsWith("EXPORT_") || actionCommand.startsWith("IMPORT_"))) {

                    // Supprimer les anciens listeners
                    for (ActionListener listener : item.getActionListeners()) {
                        item.removeActionListener(listener);
                    }

                    // Ajouter le controller comme listener
                    item.addActionListener(controller);
                    System.out.println("Listener ajouté pour: " + actionCommand);
                }
            }
        }
    }

    /**
     * Crée la barre de menu avec les options Fichier, Importer et Exporter
     */
    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(new Color(245, 247, 250));

        // Menu Fichier
        JMenu fileMenu = new JMenu("Fichier");
        fileMenu.setFont(new Font("Segoe UI", Font.BOLD, 14));

        // Sous-menu Importer
        JMenu importMenu = new JMenu("Importer");
        importMenu.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        // Essayer de charger l'icône, mais ne pas échouer si elle n'est pas trouvée
        try {
            ImageIcon importIcon = new ImageIcon(getClass().getResource("/icons/import.png"));
            if (importIcon.getIconWidth() > 0) {
                importMenu.setIcon(importIcon);
            }
        } catch (Exception e) {
            System.out.println("Icône d'importation non trouvée");
        }

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

        // Essayer de charger l'icône, mais ne pas échouer si elle n'est pas trouvée
        try {
            ImageIcon exportIcon = new ImageIcon(getClass().getResource("/icons/export.png"));
            if (exportIcon.getIconWidth() > 0) {
                exportMenu.setIcon(exportIcon);
            }
        } catch (Exception e) {
            System.out.println("Icône d'exportation non trouvée");
        }

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

        // Menu Session
        JMenu sessionMenu = new JMenu("Session");
        JMenuItem sessionItem = new JMenuItem("Connexion / Inscription");
        sessionItem.setActionCommand(ControllerActions.SESSION);
        sessionItem.addActionListener(controller); // Déléguer au contrôleur
        sessionMenu.add(sessionItem);

        menuBar.add(fileMenu);
        menuBar.add(sessionMenu);

        setJMenuBar(menuBar);
    }

    @Override
    public JFrame getFrame() {
        return this;
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
}
