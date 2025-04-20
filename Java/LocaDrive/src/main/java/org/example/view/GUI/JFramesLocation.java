package org.example.view.GUI;

import org.example.controller.Controller;
import org.example.controller.ControllerEvent;
import org.example.controller.ControllerListener;
import org.example.model.entity.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.example.model.authentication.SimpleLogin;
import org.example.model.*;
import org.example.model.entity.*;

/**
 * Classe principale de l'interface graphique
 * Gère l'affichage et les interactions avec l'utilisateur
 * Implémente le principe de Single Responsibility (S de SOLID)
 */
public class JFramesLocation extends JFrame implements ControllerListener {
    /******************************************************************************
     * DÉCLARATION DES VARIABLES
     ******************************************************************************/
    // Composants principaux
    private JTabbedPane tabbedPane;
    private JPanel panel1, panel2, panel3, panel4, panel5;
    private JTable table1, table2, table3, table4;
    
    // Boutons de gestion des véhicules
    private JButton buttonAddCar, buttonModifyCar, buttonDeleteCar;
    
    // Boutons de gestion des clients
    private JButton buttonAddClient, buttonModifyClient, buttonDeleteClient;
    
    // Boutons de gestion des locations
    private JButton buttonAddLocation, buttonModifyLocation, buttonDeleteLocation;
    
    // Boutons de gestion des contrats
    private JButton buttonAddContrat, buttonModifyContrat, buttonDeleteContrat;
    
    // Système d'authentification
    private SimpleLogin loginSystem;
    private boolean isLoggedIn = false;
    private String currentUser = null;
    
    // Contrôleur pour la gestion des données
    private Controller controller;
    
    // Constantes de mise en page
    private static final int tailleBorder = 20;
    private static final int IMG_WIDTH = 300;
    private static final int IMG_HEIGHT = 220;
    
    // Composants de recherche
    private JTextField searchField1, searchField2, searchField3, searchField4;
    private JButton searchButton1, searchButton2, searchButton3, searchButton4;

    /******************************************************************************
     * CONSTRUCTEUR PRINCIPAL
     ******************************************************************************/
    public JFramesLocation() {
        super("LocalDrive");
        
        // Initialisation du système d'authentification
        Map<String, String> userStore = new HashMap<>();
        loginSystem = new SimpleLogin(userStore);
        
        // Initialisation du contrôleur
        controller = new Controller();
        
        // S'abonner aux événements du contrôleur
        controller.addControllerListener(this);
        
        // Configuration de base de la fenêtre
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(ModernTheme.BACKGROUND_COLOR);

        //----- Initialisation des boutons -----
        initializeButtons();

        //----- Création de la barre de menu -----
        createMenuBar();

        //----- Création des onglets -----
        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(ModernTheme.MAIN_FONT);
        tabbedPane.setBackground(ModernTheme.BACKGROUND_COLOR);
        tabbedPane.setForeground(ModernTheme.TEXT_COLOR);

        // Création des différents onglets
        createVehiclesTab();
        createClientsTab();
        createReservationsTab();
        createContractsTab();
        createGalleryTab();

        // Ajout des onglets avec icônes
        tabbedPane.addTab("Véhicules", IconProvider.CAR_ICON, panel1);
        tabbedPane.addTab("Clients", IconProvider.CLIENT_ICON, panel2);
        tabbedPane.addTab("Réservations", IconProvider.RESERVATION_ICON, panel3);
        tabbedPane.addTab("Contrats", IconProvider.CONTRACT_ICON, panel5);
        tabbedPane.addTab("Galerie", IconProvider.GALLERY_ICON, panel4);

        // Finalisation de la configuration de la fenêtre
        add(tabbedPane, BorderLayout.CENTER);
        setSize(1024, 768);
        setLocationRelativeTo(null);
        updateUIBasedOnLoginState();
        
        // Charger les données dans les tableaux
        updateAllTables();
    }
    
    /**
     * Méthode appelée lorsqu'un événement du contrôleur se produit
     * Implémente le pattern Observer
     * @param event Événement du contrôleur
     */
    @Override
    public void onControllerEvent(ControllerEvent event) {
        // Mettre à jour l'interface en fonction du type d'événement
        switch (event.getEventType()) {
            case DATA_ADDED:
            case DATA_MODIFIED:
            case DATA_DELETED:
            case DATA_LOADED:
                // Mettre à jour tous les tableaux
                SwingUtilities.invokeLater(this::updateAllTables);
                break;
            default:
                // Ne rien faire pour les autres types d'événements
                break;
        }
    }
    
    /**
     * Met à jour tous les tableaux de l'interface
     * Cette méthode est appelée lorsque les données changent
     */
    private void updateAllTables() {
        updateCarsTable();
        updateClientsTable();
        updateReservationsTable();
        updateContratsTable();
    }
    
    /**
     * Met à jour le tableau des voitures
     */
    private void updateCarsTable() {
        // Code existant pour mettre à jour le tableau des voitures
        if (table1 != null) {
            DefaultTableModel model = (DefaultTableModel) table1.getModel();
            model.setRowCount(0);
            
            for (Car car : controller.getAllCars()) {
                model.addRow(new Object[]{
                    car.getIdCar(),
                    car.getBrand(),
                    car.getModel(),
                    car.getYear(),
                    car.getPriceday() + " €"
                });
            }
        }
    }
    
    /**
     * Met à jour le tableau des clients
     */
    private void updateClientsTable() {
        // Code existant pour mettre à jour le tableau des clients
        if (table2 != null) {
            DefaultTableModel model = (DefaultTableModel) table2.getModel();
            model.setRowCount(0);
            
            for (Client client : controller.getAllClients()) {
                model.addRow(new Object[]{
                    client.getIdClient(),
                    client.getName(),
                    client.getSurname(),
                    client.getEmail(),
                    client.getBirthDate() != null ? client.getBirthDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : ""
                });
            }
        }
    }
    
    /**
     * Met à jour le tableau des réservations
     */
    private void updateReservationsTable() {
        // Code existant pour mettre à jour le tableau des réservations
        if (table3 != null) {
            DefaultTableModel model = (DefaultTableModel) table3.getModel();
            model.setRowCount(0);
            
            for (Reservation reservation : controller.getAllReservations()) {
                model.addRow(new Object[]{
                    reservation.getIdReservation(),
                    reservation.getClient() != null ? reservation.getClient().getName() + " " + reservation.getClient().getSurname() : "N/A",
                    reservation.getCar() != null ? reservation.getCar().getBrand() + " " + reservation.getCar().getModel() : "N/A",
                    reservation.getDateDebut() != null ? reservation.getDateDebut().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : "",
                    reservation.getDateFin() != null ? reservation.getDateFin().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : "",
                    reservation.getPrice() + " €",
                    reservation.getStatut().toString()
                });
            }
        }
    }
    
    /**
     * Met à jour le tableau des contrats
     */
    private void updateContratsTable() {
        // Code existant pour mettre à jour le tableau des contrats
        if (table4 != null) {
            DefaultTableModel model = (DefaultTableModel) table4.getModel();
            model.setRowCount(0);
            
            for (Contrat contrat : controller.getAllContrats()) {
                model.addRow(new Object[]{
                    contrat.getIdContrat(),
                    contrat.getClient() != null ? contrat.getClient().getName() + " " + contrat.getClient().getSurname() : "N/A",
                    contrat.getCar() != null ? contrat.getCar().getBrand() + " " + contrat.getCar().getModel() : "N/A",
                    contrat.getDateDebut() != null ? contrat.getDateDebut().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : "",
                    contrat.getDateFin() != null ? contrat.getDateFin().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : "",
                    contrat.getPrice() + " €",
                    contrat.isEstSigne() ? "Oui" : "Non",
                    contrat.getStatutContrat().toString()
                });
            }
        }
    }
    
    /******************************************************************************
     * INITIALISATION DES BOUTONS
     ******************************************************************************/
    private void initializeButtons() {
        // Initialisation des boutons pour les véhicules
        buttonAddCar = new JButton("Ajouter");
        buttonModifyCar = new JButton("Modifier");
        buttonDeleteCar = new JButton("Supprimer");

        // Initialisation des boutons pour les clients
        buttonAddClient = new JButton("Ajouter");
        buttonModifyClient = new JButton("Modifier");
        buttonDeleteClient = new JButton("Supprimer");

        // Initialisation des boutons pour les locations
        buttonAddLocation = new JButton("Ajouter");
        buttonModifyLocation = new JButton("Modifier");
        buttonDeleteLocation = new JButton("Supprimer");

        // Initialisation des boutons pour les contrats
        buttonAddContrat = new JButton("Ajouter");
        buttonModifyContrat = new JButton("Modifier");
        buttonDeleteContrat = new JButton("Supprimer");

        // Ajout des gestionnaires d'événements pour les boutons d'ajout
        buttonAddCar.addActionListener(e -> showAddCarForm());
        buttonAddClient.addActionListener(e -> showAddClientForm());
        buttonAddLocation.addActionListener(e -> showAddLocationForm());
        buttonAddContrat.addActionListener(e -> showAddContratForm());
        
        // Ajout des gestionnaires d'événements pour les boutons de modification
        buttonModifyLocation.addActionListener(e -> showModifyReservationForm());
    }
    
    /******************************************************************************
     * CRÉATION DE LA BARRE DE MENU
     ******************************************************************************/
    private void createMenuBar() {
        // Création de la barre de menu
        JMenuBar menuBar = new JMenuBar();
        ModernTheme.styleMenuBar(menuBar);

        // Menu Fichier
        JMenu fileMenu = new JMenu("Fichier");
        ModernTheme.styleMenu(fileMenu);
        fileMenu.setIcon(IconProvider.FILE_ICON);

        JMenu ImportMenu = new JMenu("Importer");
        ModernTheme.styleMenu(ImportMenu);
        ImportMenu.setIcon(IconProvider.IMPORT_ICON);
        fileMenu.add(ImportMenu);

        JMenuItem exitItem = new JMenuItem("Quitter");
        ModernTheme.styleMenuItem(exitItem);
        exitItem.setIcon(IconProvider.EXIT_ICON);
        exitItem.addActionListener(e -> dispose());
        fileMenu.add(exitItem);

        menuBar.add(fileMenu);

        // Bouton session dans la barre de menu
        JButton sessionButton = new JButton("Connexion");
        sessionButton.setIcon(IconProvider.LOGIN_ICON);
        ModernTheme.styleButton(sessionButton);
        sessionButton.addActionListener(e -> showSessionDialog());

        // Ajout d'un panel à droite de la barre de menu pour le bouton session
        JPanel menuButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        menuButtonPanel.setBackground(ModernTheme.BACKGROUND_COLOR);
        menuButtonPanel.add(sessionButton);

        // Ajout d'un espace flexible entre le menu et le bouton
        menuBar.add(Box.createHorizontalGlue());
        menuBar.add(menuButtonPanel);

        setJMenuBar(menuBar);
    }
    
    /******************************************************************************
     * CRÉATION DE L'ONGLET VÉHICULES
     ******************************************************************************/
    private void createVehiclesTab() {
        panel1 = new JPanel(new BorderLayout());
        ModernTheme.stylePanel(panel1);
        panel1.setBorder(BorderFactory.createEmptyBorder(tailleBorder, tailleBorder, tailleBorder, tailleBorder));

        // Initialisation des composants de recherche
        JPanel searchPanel1 = createSearchPanel("Rechercher un véhicule", 1);

        // Définition des colonnes du tableau
        String[] carColumns = {
            "ID", "Marque", "Modèle", "Année", "Prix/Jour", 
            "Kilométrage", "Carburant", "Transmission", 
            "Nombre de places", "Disponible", "Image"
        };
        Object[][] carData = {};
        DefaultTableModel carModel = new DefaultTableModel(carData, carColumns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Rendre toutes les cellules non-éditables
            }
        };
        table1 = new JTable(carModel);
        ModernTheme.styleTable(table1);
        configureTable(table1);

        // Création du panneau de boutons
        JPanel buttonPanel1 = createButtonPanel(buttonAddCar, buttonModifyCar, buttonDeleteCar);
        
        // Assemblage de l'onglet
        panel1.add(searchPanel1, BorderLayout.NORTH);
        panel1.add(new JScrollPane(table1), BorderLayout.CENTER);
        panel1.add(buttonPanel1, BorderLayout.SOUTH);
    }
    
    /******************************************************************************
     * CRÉATION DE L'ONGLET CLIENTS
     ******************************************************************************/
    private void createClientsTab() {
        panel2 = new JPanel(new BorderLayout());
        ModernTheme.stylePanel(panel2);
        panel2.setBorder(BorderFactory.createEmptyBorder(tailleBorder, tailleBorder, tailleBorder, tailleBorder));

        // Initialisation des composants de recherche
        JPanel searchPanel2 = createSearchPanel("Rechercher un client", 2);

        // Définition des colonnes du tableau
        String[] clientColumns = {
            "ID", "Nom", "Prénom", "Email", "Téléphone",
            "Date de naissance", "Numéro de permis", "Adresse"
        };
        Object[][] clientData = {};
        DefaultTableModel clientModel = new DefaultTableModel(clientData, clientColumns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Rendre toutes les cellules non-éditables
            }
        };
        table2 = new JTable(clientModel);
        ModernTheme.styleTable(table2);
        configureTable(table2);

        // Création du panneau de boutons
        JPanel buttonPanel2 = createButtonPanel(buttonAddClient, buttonModifyClient, buttonDeleteClient);
        
        // Assemblage de l'onglet
        panel2.add(searchPanel2, BorderLayout.NORTH);
        panel2.add(new JScrollPane(table2), BorderLayout.CENTER);
        panel2.add(buttonPanel2, BorderLayout.SOUTH);
    }
    
    /******************************************************************************
     * CRÉATION DE L'ONGLET RÉSERVATIONS
     ******************************************************************************/
    private void createReservationsTab() {
        panel3 = new JPanel(new BorderLayout());
        ModernTheme.stylePanel(panel3);
        panel3.setBorder(BorderFactory.createEmptyBorder(tailleBorder, tailleBorder, tailleBorder, tailleBorder));

        // Initialisation des composants de recherche
        JPanel searchPanel3 = createSearchPanel("Rechercher une réservation", 3);

        // Définition des colonnes du tableau
        String[] locationColumns = {
            "ID", "Client", "Véhicule", "Date Début", "Date Fin",
            "Prix Total", "Statut", "Date Réservation"
        };
        Object[][] locationData = {};
        DefaultTableModel locationModel = new DefaultTableModel(locationData, locationColumns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Rendre toutes les cellules non-éditables
            }
        };
        table3 = new JTable(locationModel);
        ModernTheme.styleTable(table3);
        configureTable(table3);

        // Création du panneau de boutons
        JPanel buttonPanel3 = createButtonPanel(buttonAddLocation, buttonModifyLocation, buttonDeleteLocation);
        
        // Assemblage de l'onglet
        panel3.add(searchPanel3, BorderLayout.NORTH);
        panel3.add(new JScrollPane(table3), BorderLayout.CENTER);
        panel3.add(buttonPanel3, BorderLayout.SOUTH);
    }
    
    /******************************************************************************
     * CRÉATION DE L'ONGLET CONTRATS
     ******************************************************************************/
    private void createContractsTab() {
        panel5 = new JPanel(new BorderLayout());
        ModernTheme.stylePanel(panel5);
        panel5.setBorder(BorderFactory.createEmptyBorder(tailleBorder, tailleBorder, tailleBorder, tailleBorder));

        // Initialisation des composants de recherche
        JPanel searchPanel4 = createSearchPanel("Rechercher un contrat", 4);

        // Définition des colonnes du tableau
        String[] contratColumns = {
            "ID Contrat", "Client", "Véhicule", "Date Début", "Date Fin",
            "Montant Total", "Caution", "Type Assurance", "Options",
            "Statut", "Signé"
        };
        Object[][] contratData = {};
        DefaultTableModel contratModel = new DefaultTableModel(contratData, contratColumns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Rendre toutes les cellules non-éditables
            }
        };
        table4 = new JTable(contratModel);
        ModernTheme.styleTable(table4);
        configureTable(table4);

        // Création du panneau de boutons
        JPanel buttonPanel4 = createButtonPanel(buttonAddContrat, buttonModifyContrat, buttonDeleteContrat);
        
        // Assemblage de l'onglet
        panel5.add(searchPanel4, BorderLayout.NORTH);
        panel5.add(new JScrollPane(table4), BorderLayout.CENTER);
        panel5.add(buttonPanel4, BorderLayout.SOUTH);
    }
    
    /******************************************************************************
     * CRÉATION DE L'ONGLET GALERIE
     ******************************************************************************/
    private void createGalleryTab() {
        panel4 = new JPanel(new BorderLayout());
        ModernTheme.stylePanel(panel4);
        panel4.setBorder(BorderFactory.createEmptyBorder(tailleBorder, tailleBorder, tailleBorder, tailleBorder));

        // Panneau de recherche
        JPanel searchPanel = createSearchPanel("Rechercher une voiture", 4);
        
        // Panneau principal pour la galerie avec défilement
        JPanel galleryMainPanel = new JPanel(new BorderLayout());
        ModernTheme.stylePanel(galleryMainPanel);
        
        // Panneau de galerie avec GridLayout pour afficher les images
        JPanel galleryPanel = new JPanel();
        galleryPanel.setLayout(new WrapLayout(FlowLayout.LEFT, 20, 20)); // Utilisation de WrapLayout pour un affichage flexible
        ModernTheme.stylePanel(galleryPanel);
        
        // Bouton de rafraîchissement
        JButton refreshButton = new JButton("Rafraîchir");
        refreshButton.setIcon(IconProvider.REFRESH_ICON);
        ModernTheme.styleButton(refreshButton);
        refreshButton.addActionListener(e -> updateGallery());
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        ModernTheme.stylePanel(buttonPanel);
        buttonPanel.add(refreshButton);
        
        // Assemblage de l'onglet
        galleryMainPanel.add(galleryPanel, BorderLayout.CENTER);
        JScrollPane scrollPane = new JScrollPane(galleryMainPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUnitIncrement(16); // Vitesse de défilement
        
        panel4.add(searchPanel, BorderLayout.NORTH);
        panel4.add(scrollPane, BorderLayout.CENTER);
        panel4.add(buttonPanel, BorderLayout.SOUTH);
        
        // Initialiser la galerie
        updateGallery();
    }

    /**
     * Met à jour la galerie d'images des voitures
     */
    private void updateGallery() {
        // Récupérer le panneau de galerie
        JPanel galleryMainPanel = (JPanel) ((JScrollPane) panel4.getComponent(1)).getViewport().getView();
        JPanel galleryPanel = (JPanel) galleryMainPanel.getComponent(0);
        
        // Vider le panneau
        galleryPanel.removeAll();
        
        // Récupérer toutes les voitures
        ArrayList<Car> cars = controller.getAllCars();
        
        // Ajouter chaque voiture à la galerie
        for (Car car : cars) {
            // Créer un panneau pour chaque voiture
            JPanel carPanel = new JPanel(new BorderLayout());
            carPanel.setPreferredSize(new Dimension(250, 220));
            carPanel.setBorder(BorderFactory.createLineBorder(ModernTheme.ACCENT_COLOR, 1));
            ModernTheme.stylePanel(carPanel);
            
            // Ajouter l'image de la voiture
            JLabel imageLabel = new JLabel();
            imageLabel.setHorizontalAlignment(JLabel.CENTER);
            
            String imagePath = car.getImage();
            if (imagePath != null && !imagePath.isEmpty()) {
                try {
                    // Charger l'image
                    Image img = ImageIO.read(new File(imagePath));
                    // Redimensionner l'image
                    Image scaledImg = img.getScaledInstance(240, 160, Image.SCALE_SMOOTH);
                    imageLabel.setIcon(new ImageIcon(scaledImg));
                } catch (IOException e) {
                    imageLabel.setText("Image non disponible");
                }
            } else {
                imageLabel.setText("Pas d'image");
            }
            
            // Ajouter les informations de la voiture
            JPanel infoPanel = new JPanel(new GridLayout(2, 1));
            ModernTheme.stylePanel(infoPanel);
            
            JLabel plateLabel = new JLabel("Plaque: " + car.getIdCar());
            plateLabel.setHorizontalAlignment(JLabel.CENTER);
            plateLabel.setFont(ModernTheme.MAIN_FONT.deriveFont(Font.BOLD));
            
            JLabel carInfoLabel = new JLabel(car.getBrand() + " " + car.getModel() + " (" + car.getYear() + ")");
            carInfoLabel.setHorizontalAlignment(JLabel.CENTER);
            
            infoPanel.add(plateLabel);
            infoPanel.add(carInfoLabel);
            
            // Assemblage du panneau de voiture
            carPanel.add(imageLabel, BorderLayout.CENTER);
            carPanel.add(infoPanel, BorderLayout.SOUTH);
            
            // Ajouter un écouteur de clic pour afficher les détails de la voiture
            carPanel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    showCarDetails(car);
                }
                
                @Override
                public void mouseEntered(MouseEvent e) {
                    carPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
                    carPanel.setBorder(BorderFactory.createLineBorder(ModernTheme.HIGHLIGHT_COLOR, 2));
                }
                
                @Override
                public void mouseExited(MouseEvent e) {
                    carPanel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                    carPanel.setBorder(BorderFactory.createLineBorder(ModernTheme.ACCENT_COLOR, 1));
                }
            });
            
            // Ajouter le panneau de voiture à la galerie
            galleryPanel.add(carPanel);
        }
        
        // Rafraîchir l'affichage
        galleryPanel.revalidate();
        galleryPanel.repaint();
    }
    
    /**
     * Affiche les détails d'une voiture dans une fenêtre modale
     */
    private void showCarDetails(Car car) {
        JDialog dialog = new JDialog(this, "Détails de la voiture", true);
        dialog.setLayout(new BorderLayout());
        dialog.setSize(600, 400);
        dialog.setLocationRelativeTo(this);
        
        JPanel mainPanel = new JPanel(new BorderLayout(20, 20));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        ModernTheme.stylePanel(mainPanel);
        
        // Panneau pour l'image
        JPanel imagePanel = new JPanel(new BorderLayout());
        ModernTheme.stylePanel(imagePanel);
        imagePanel.setPreferredSize(new Dimension(300, 200));
        
        JLabel imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        
        String imagePath = car.getImage();
        if (imagePath != null && !imagePath.isEmpty()) {
            try {
                // Charger l'image
                Image img = ImageIO.read(new File(imagePath));
                // Redimensionner l'image
                Image scaledImg = img.getScaledInstance(300, 200, Image.SCALE_SMOOTH);
                imageLabel.setIcon(new ImageIcon(scaledImg));
            } catch (IOException e) {
                imageLabel.setText("Image non disponible");
            }
        } else {
            imageLabel.setText("Pas d'image");
        }
        
        imagePanel.add(imageLabel, BorderLayout.CENTER);
        
        // Panneau pour les informations
        JPanel infoPanel = new JPanel(new GridLayout(8, 2, 10, 10));
        ModernTheme.stylePanel(infoPanel);
        
        infoPanel.add(new JLabel("Plaque:"));
        infoPanel.add(new JLabel(car.getIdCar()));
        
        infoPanel.add(new JLabel("Marque:"));
        infoPanel.add(new JLabel(car.getBrand()));
        
        infoPanel.add(new JLabel("Modèle:"));
        infoPanel.add(new JLabel(car.getModel()));
        
        infoPanel.add(new JLabel("Année:"));
        infoPanel.add(new JLabel(String.valueOf(car.getYear())));
        
        infoPanel.add(new JLabel("Prix par jour:"));
        infoPanel.add(new JLabel(String.valueOf(car.getPriceday()) + " €"));
        
        infoPanel.add(new JLabel("Kilométrage:"));
        infoPanel.add(new JLabel(String.valueOf(car.getMileage()) + " km"));
        
        infoPanel.add(new JLabel("Carburant:"));
        infoPanel.add(new JLabel(car.getFuelType()));
        
        infoPanel.add(new JLabel("Transmission:"));
        infoPanel.add(new JLabel(car.getTransmission()));
        
        // Bouton de fermeture
        JButton closeButton = new JButton("Fermer");
        ModernTheme.styleButton(closeButton);
        closeButton.addActionListener(e -> dialog.dispose());
        
        // Assemblage de la boîte de dialogue
        mainPanel.add(imagePanel, BorderLayout.WEST);
        mainPanel.add(infoPanel, BorderLayout.CENTER);
        
        dialog.add(mainPanel, BorderLayout.CENTER);
        dialog.add(closeButton, BorderLayout.SOUTH);
        
        dialog.setVisible(true);
    }
    
    /**
     * Classe WrapLayout pour afficher les éléments de la galerie de manière flexible
     * Cette classe est basée sur FlowLayout mais permet un retour à la ligne automatique
     */
    private class WrapLayout extends FlowLayout {
        private Dimension preferredLayoutSize;
        
        public WrapLayout() {
            super();
        }
        
        public WrapLayout(int align) {
            super(align);
        }
        
        public WrapLayout(int align, int hgap, int vgap) {
            super(align, hgap, vgap);
        }
        
        @Override
        public Dimension preferredLayoutSize(Container target) {
            return layoutSize(target, true);
        }
        
        @Override
        public Dimension minimumLayoutSize(Container target) {
            Dimension minimum = layoutSize(target, false);
            minimum.width -= (getHgap() + 1);
            return minimum;
        }
        
        private Dimension layoutSize(Container target, boolean preferred) {
            synchronized (target.getTreeLock()) {
                int targetWidth = target.getWidth();
                
                if (targetWidth == 0)
                    targetWidth = Integer.MAX_VALUE;
                
                int hgap = getHgap();
                int vgap = getVgap();
                Insets insets = target.getInsets();
                int horizontalInsetsAndGap = insets.left + insets.right + (hgap * 2);
                int maxWidth = targetWidth - horizontalInsetsAndGap;
                
                Dimension dim = new Dimension(0, 0);
                int rowWidth = 0;
                int rowHeight = 0;
                
                int nmembers = target.getComponentCount();
                
                for (int i = 0; i < nmembers; i++) {
                    Component m = target.getComponent(i);
                    
                    if (m.isVisible()) {
                        Dimension d = preferred ? m.getPreferredSize() : m.getMinimumSize();
                        
                        if (rowWidth + d.width > maxWidth) {
                            addRow(dim, rowWidth, rowHeight);
                            rowWidth = 0;
                            rowHeight = 0;
                        }
                        
                        if (rowWidth != 0) {
                            rowWidth += hgap;
                        }
                        
                        rowWidth += d.width;
                        rowHeight = Math.max(rowHeight, d.height);
                    }
                }
                
                addRow(dim, rowWidth, rowHeight);
                
                dim.width += horizontalInsetsAndGap;
                dim.height += insets.top + insets.bottom + vgap * 2;
                
                Container scrollPane = SwingUtilities.getAncestorOfClass(JScrollPane.class, target);
                
                if (scrollPane != null && target.isValid()) {
                    dim.width = Math.min(dim.width, targetWidth);
                }
                
                return dim;
            }
        }
        
        private void addRow(Dimension dim, int rowWidth, int rowHeight) {
            dim.width = Math.max(dim.width, rowWidth);
            
            if (dim.height > 0) {
                dim.height += getVgap();
            }
            
            dim.height += rowHeight;
        }
    }

    /******************************************************************************
     * MÉTHODES UTILITAIRES POUR LA CRÉATION DE COMPOSANTS
     ******************************************************************************/
    /**
     * Crée un panneau de recherche avec un champ de texte et un bouton
     * @param tooltip Texte d'aide à afficher
     * @param tableIndex Index du tableau (1, 2, 3 ou 4)
     * @return Panneau de recherche configuré
     */
    private JPanel createSearchPanel(String tooltip, int tableIndex) {
        JPanel searchPanel = new JPanel(new BorderLayout(10, 0));
        ModernTheme.stylePanel(searchPanel);
        
        // Création du champ de recherche
        JTextField searchField = new JTextField();
        ModernTheme.styleTextField(searchField);
        searchField.setToolTipText(tooltip);
        
        // Création du bouton de recherche
        JButton searchButton = new JButton("Rechercher");
        ModernTheme.styleButton(searchButton);
        searchButton.setIcon(IconProvider.SEARCH_ICON);
        
        // Ajout des composants au panneau
        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.add(searchButton, BorderLayout.EAST);
        searchPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        
        // Sauvegarde des références aux composants de recherche
        switch (tableIndex) {
            case 1:
                searchField1 = searchField;
                searchButton1 = searchButton;
                break;
            case 2:
                searchField2 = searchField;
                searchButton2 = searchButton;
                break;
            case 3:
                searchField3 = searchField;
                searchButton3 = searchButton;
                break;
            case 4:
                searchField4 = searchField;
                searchButton4 = searchButton;
                break;
        }
        
        // Ajout des écouteurs d'événements pour la recherche
        ActionListener searchAction = e -> {
            JTable targetTable = null;
            switch (tableIndex) {
                case 1:
                    targetTable = table1;
                    break;
                case 2:
                    targetTable = table2;
                    break;
                case 3:
                    targetTable = table3;
                    break;
                case 4:
                    targetTable = table4;
                    break;
            }
            performSearch(searchField.getText(), targetTable);
        };
        
        searchButton.addActionListener(searchAction);
        searchField.addActionListener(searchAction); // Pour rechercher en appuyant sur Entrée
        
        return searchPanel;
    }

    /**
     * Effectue une recherche dans le tableau spécifié
     * @param searchText Texte à rechercher
     * @param table Tableau dans lequel effectuer la recherche
     */
    private void performSearch(String searchText, JTable table) {
        // Vérification que le tableau n'est pas null
        if (table == null) {
            System.err.println("❌ Erreur : le tableau est null");
            return;
        }
        
        // Vérification que le RowSorter n'est pas null
        TableRowSorter<DefaultTableModel> sorter = (TableRowSorter<DefaultTableModel>) table.getRowSorter();
        if (sorter == null) {
            System.err.println("❌ Erreur : le RowSorter est null");
            return;
        }
        
        if (searchText == null || searchText.trim().isEmpty()) {
            // Si la recherche est vide, réinitialiser le filtre
            sorter.setRowFilter(null);
            return;
        }
        
        try {
            // Création d'un filtre qui recherche dans toutes les colonnes
            RowFilter<DefaultTableModel, Integer> filter = new RowFilter<DefaultTableModel, Integer>() {
                @Override
                public boolean include(Entry<? extends DefaultTableModel, ? extends Integer> entry) {
                    DefaultTableModel model = entry.getModel();
                    int rowIndex = entry.getIdentifier();
                    
                    // Recherche dans toutes les colonnes
                    for (int i = 0; i < model.getColumnCount(); i++) {
                        Object value = model.getValueAt(rowIndex, i);
                        if (value != null) {
                            String stringValue = value.toString().toLowerCase();
                            if (stringValue.contains(searchText.toLowerCase())) {
                                return true; // La ligne correspond au critère de recherche
                            }
                        }
                    }
                    return false; // Aucune correspondance trouvée
                }
            };
            
            sorter.setRowFilter(filter);
            System.out.println("✅ Recherche effectuée pour : " + searchText);
        } catch (Exception e) {
            System.err.println("❌ Erreur lors de la recherche : " + e.getMessage());
            e.printStackTrace();
            // En cas d'erreur, réinitialiser le filtre
            sorter.setRowFilter(null);
        }
    }

    /**
     * Crée un panneau de boutons d'action (ajouter, modifier, supprimer)
     * @param addButton Bouton d'ajout
     * @param modifyButton Bouton de modification
     * @param deleteButton Bouton de suppression
     * @return Panneau de boutons configuré
     */
    private JPanel createButtonPanel(JButton addButton, JButton modifyButton, JButton deleteButton) {
        JPanel buttonPanel = new JPanel();
        ModernTheme.stylePanel(buttonPanel);
        
        ModernTheme.styleActionButton(addButton, "add");
        addButton.setIcon(IconProvider.ADD_ICON);
        
        ModernTheme.styleActionButton(modifyButton, "modify");
        modifyButton.setIcon(IconProvider.EDIT_ICON);
        
        ModernTheme.styleActionButton(deleteButton, "delete");
        deleteButton.setIcon(IconProvider.DELETE_ICON);
        
        buttonPanel.add(addButton);
        buttonPanel.add(modifyButton);
        buttonPanel.add(deleteButton);
        
        return buttonPanel;
    }

    /**
     * Configure un tableau avec des paramètres communs
     * @param table Tableau à configurer
     */
    private void configureTable(JTable table) {
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getTableHeader().setReorderingAllowed(false);
        table.setRowHeight(25);
        table.setIntercellSpacing(new Dimension(10, 5));
        table.setShowGrid(true);
        table.setGridColor(ModernTheme.BORDER_COLOR);
        
        // Création d'un sorter personnalisé pour gérer différents types de données
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        
        // Configuration des comparateurs personnalisés pour chaque colonne
        for (int i = 0; i < model.getColumnCount(); i++) {
            final int columnIndex = i;
            sorter.setComparator(columnIndex, (Comparator<Object>) (o1, o2) -> {
                if (o1 == null && o2 == null) {
                    return 0;
                }
                if (o1 == null) {
                    return -1;
                }
                if (o2 == null) {
                    return 1;
                }
                
                // Gestion des types spécifiques
                if (o1 instanceof Integer && o2 instanceof Integer) {
                    return ((Integer) o1).compareTo((Integer) o2);
                } else if (o1 instanceof Float && o2 instanceof Float) {
                    return ((Float) o1).compareTo((Float) o2);
                } else if (o1 instanceof LocalDate && o2 instanceof LocalDate) {
                    return ((LocalDate) o1).compareTo((LocalDate) o2);
                } else if (o1 instanceof String && o2 instanceof String) {
                    // Pour les colonnes avec des valeurs comme "25 €" ou "100 km"
                    String s1 = (String) o1;
                    String s2 = (String) o2;
                    
                    // Extraction des nombres pour les valeurs avec unités
                    if (s1.contains(" €") && s2.contains(" €")) {
                        try {
                            float f1 = Float.parseFloat(s1.replace(" €", "").trim());
                            float f2 = Float.parseFloat(s2.replace(" €", "").trim());
                            return Float.compare(f1, f2);
                        } catch (NumberFormatException e) {
                            return s1.compareTo(s2);
                        }
                    } else if (s1.contains(" km") && s2.contains(" km")) {
                        try {
                            int i1 = Integer.parseInt(s1.replace(" km", "").trim());
                            int i2 = Integer.parseInt(s2.replace(" km", "").trim());
                            return Integer.compare(i1, i2);
                        } catch (NumberFormatException e) {
                            return s1.compareTo(s2);
                        }
                    } else {
                        // Comparaison de chaînes standard
                        return s1.compareTo(s2);
                    }
                } else {
                    // Fallback pour les autres types
                    return o1.toString().compareTo(o2.toString());
                }
            });
        }
        
        // Application du sorter au tableau
        table.setRowSorter(sorter);
        
        // Style de l'en-tête pour indiquer que le tri est disponible
        JTableHeader header = table.getTableHeader();
        header.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        header.setToolTipText("Cliquez pour trier");
        
        // Ajout d'un écouteur d'événements pour l'en-tête du tableau
        header.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int columnIndex = table.columnAtPoint(e.getPoint());
                if (columnIndex != -1) {
                    // Récupération de l'état de tri actuel
                    List<? extends RowSorter.SortKey> sortKeys = sorter.getSortKeys();
                    if (sortKeys.isEmpty() || sortKeys.get(0).getColumn() != columnIndex) {
                        // Premier clic: tri ascendant
                        sorter.setSortKeys(List.of(new RowSorter.SortKey(columnIndex, SortOrder.ASCENDING)));
                    } else {
                        // Clic suivant: basculer entre ascendant et descendant
                        SortOrder currentOrder = sortKeys.get(0).getSortOrder();
                        SortOrder newOrder = (currentOrder == SortOrder.ASCENDING) ? 
                                              SortOrder.DESCENDING : SortOrder.ASCENDING;
                        sorter.setSortKeys(List.of(new RowSorter.SortKey(columnIndex, newOrder)));
                    }
                }
            }
        });
    }
    
    /******************************************************************************
     * FORMULAIRES D'AJOUT
     ******************************************************************************/
    /**
     * Affiche le formulaire d'ajout de véhicule
     */
    private void showAddCarForm() {
        JPanel panel = new JPanel(new GridLayout(11, 2, 10, 10));
        ModernTheme.stylePanel(panel);
        
        // Champ pour la marque
        panel.add(new JLabel("Marque:"));
        ModernTheme.styleLabel((JLabel) panel.getComponent(0));
        JTextField brandField = new JTextField();
        ModernTheme.styleTextField(brandField);
        panel.add(brandField);

        // Champ pour le modèle
        panel.add(new JLabel("Modèle:"));
        ModernTheme.styleLabel((JLabel) panel.getComponent(2));
        JTextField modelField = new JTextField();
        ModernTheme.styleTextField(modelField);
        panel.add(modelField);

        // Champ pour l'année
        panel.add(new JLabel("Année:"));
        ModernTheme.styleLabel((JLabel) panel.getComponent(4));
        JTextField yearField = new JTextField();
        ModernTheme.styleTextField(yearField);
        panel.add(yearField);

        // Champ pour le prix par jour
        panel.add(new JLabel("Prix par jour:"));
        ModernTheme.styleLabel((JLabel) panel.getComponent(6));
        JTextField priceField = new JTextField();
        ModernTheme.styleTextField(priceField);
        panel.add(priceField);
        
        // Champ pour le kilométrage
        panel.add(new JLabel("Kilométrage:"));
        ModernTheme.styleLabel((JLabel) panel.getComponent(8));
        JTextField mileageField = new JTextField();
        ModernTheme.styleTextField(mileageField);
        panel.add(mileageField);
        
        // Champ pour le type de carburant
        panel.add(new JLabel("Carburant:"));
        ModernTheme.styleLabel((JLabel) panel.getComponent(10));
        JComboBox<String> fuelTypeField = new JComboBox<>(new String[]{"Essence", "Diesel", "Électrique", "Hybride", "GPL"});
        ModernTheme.styleComboBox(fuelTypeField);
        panel.add(fuelTypeField);
        
        // Champ pour la transmission
        panel.add(new JLabel("Transmission:"));
        ModernTheme.styleLabel((JLabel) panel.getComponent(12));
        JComboBox<String> transmissionField = new JComboBox<>(new String[]{"Manuelle", "Automatique", "Semi-automatique"});
        ModernTheme.styleComboBox(transmissionField);
        panel.add(transmissionField);
        
        // Champ pour le nombre de places
        panel.add(new JLabel("Nombre de places:"));
        ModernTheme.styleLabel((JLabel) panel.getComponent(14));
        JTextField seatsField = new JTextField();
        ModernTheme.styleTextField(seatsField);
        panel.add(seatsField);
        
        // Champ pour la disponibilité
        panel.add(new JLabel("Disponible:"));
        ModernTheme.styleLabel((JLabel) panel.getComponent(16));
        JCheckBox availableField = new JCheckBox();
        availableField.setSelected(true); // Par défaut, la voiture est disponible
        panel.add(availableField);
        
        // Champ pour l'image
        panel.add(new JLabel("Image:"));
        ModernTheme.styleLabel((JLabel) panel.getComponent(18));
        
        // Panel pour contenir le champ de texte et le bouton de parcourir
        JPanel imagePanel = new JPanel(new BorderLayout(5, 0));
        JTextField imagePathField = new JTextField();
        imagePathField.setEditable(false); // Rendre le champ non éditable
        ModernTheme.styleTextField(imagePathField);
        
        JButton browseButton = new JButton("Parcourir...");
        browseButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(new FileNameExtensionFilter("Images", "jpg", "jpeg", "png", "gif"));
            int result = fileChooser.showOpenDialog(panel);
            if (result == JFileChooser.APPROVE_OPTION) {
                imagePathField.setText(fileChooser.getSelectedFile().getAbsolutePath());
            }
        });
        
        imagePanel.add(imagePathField, BorderLayout.CENTER);
        imagePanel.add(browseButton, BorderLayout.EAST);
        panel.add(imagePanel);

        // Affichage du formulaire
        int option = JOptionPane.showConfirmDialog(this, panel, "Ajouter Véhicule", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (option == JOptionPane.OK_OPTION) {
            try {
                // Récupération des valeurs
                String brand = brandField.getText().trim();
                String model = modelField.getText().trim();
                int year = Integer.parseInt(yearField.getText().trim());
                float price = Float.parseFloat(priceField.getText().trim());
                int mileage = mileageField.getText().trim().isEmpty() ? 0 : Integer.parseInt(mileageField.getText().trim());
                String fuelType = (String) fuelTypeField.getSelectedItem();
                String transmission = (String) transmissionField.getSelectedItem();
                int seats = seatsField.getText().trim().isEmpty() ? 0 : Integer.parseInt(seatsField.getText().trim());
                boolean available = availableField.isSelected();
                String imagePath = imagePathField.getText();
                
                // Validation des données
                if (brand.isEmpty() || model.isEmpty()) {
                    JOptionPane.showMessageDialog(this,
                            "La marque et le modèle sont obligatoires",
                            "Erreur de saisie",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                if (year < 1900 || year > 2100) {
                    JOptionPane.showMessageDialog(this,
                            "L'année doit être comprise entre 1900 et 2100",
                            "Erreur de saisie",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                if (price <= 0) {
                    JOptionPane.showMessageDialog(this,
                            "Le prix doit être supérieur à 0",
                            "Erreur de saisie",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                if (mileage < 0) {
                    JOptionPane.showMessageDialog(this,
                            "Le kilométrage ne peut pas être négatif",
                            "Erreur de saisie",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                if (seats <= 0) {
                    JOptionPane.showMessageDialog(this,
                            "Le nombre de places doit être supérieur à 0",
                            "Erreur de saisie",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                // Création de la voiture
                Car car = new Car();
                car.setBrand(brand);
                car.setModel(model);
                car.setYear(year);
                car.setPriceday(price);
                car.setMileage(mileage);
                car.setFuelType(fuelType);
                car.setTransmission(transmission);
                car.setSeats(seats);
                car.setAvailable(available);
                car.setImage(imagePath);
                
                // Ajout de la voiture via le contrôleur
                String newId = controller.addCar(car);
                
                // Affichage d'un message de confirmation
                JOptionPane.showMessageDialog(this,
                        "Véhicule ajouté avec succès. ID: " + newId,
                        "Succès",
                        JOptionPane.INFORMATION_MESSAGE);
                
                // Mettre à jour le tableau des voitures
                updateCarsTable();
                
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this,
                        "Erreur de format : l'année, le prix, le kilométrage et le nombre de places doivent être des nombres",
                        "Erreur de saisie",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Affiche le formulaire d'ajout de client
     */
    private void showAddClientForm() {
        JPanel panel = new JPanel(new GridLayout(8, 2, 10, 10));
        ModernTheme.stylePanel(panel);
        
        // Champ pour le nom
        panel.add(new JLabel("Nom:"));
        ModernTheme.styleLabel((JLabel) panel.getComponent(0));
        JTextField nameField = new JTextField();
        ModernTheme.styleTextField(nameField);
        panel.add(nameField);

        // Champ pour le prénom
        panel.add(new JLabel("Prénom:"));
        ModernTheme.styleLabel((JLabel) panel.getComponent(2));
        JTextField surnameField = new JTextField();
        ModernTheme.styleTextField(surnameField);
        panel.add(surnameField);

        // Champ pour l'email
        panel.add(new JLabel("Email:"));
        ModernTheme.styleLabel((JLabel) panel.getComponent(4));
        JTextField emailField = new JTextField();
        ModernTheme.styleTextField(emailField);
        panel.add(emailField);
        
        // Champ pour le téléphone
        panel.add(new JLabel("Téléphone:"));
        ModernTheme.styleLabel((JLabel) panel.getComponent(6));
        JTextField phoneField = new JTextField();
        ModernTheme.styleTextField(phoneField);
        panel.add(phoneField);

        // Champ pour la date de naissance
        panel.add(new JLabel("Date de naissance (YYYY-MM-DD):"));
        ModernTheme.styleLabel((JLabel) panel.getComponent(8));
        JTextField birthDateField = new JTextField();
        ModernTheme.styleTextField(birthDateField);
        panel.add(birthDateField);
        
        // Champ pour le numéro de permis
        panel.add(new JLabel("Numéro de permis:"));
        ModernTheme.styleLabel((JLabel) panel.getComponent(10));
        JTextField licenseField = new JTextField();
        ModernTheme.styleTextField(licenseField);
        panel.add(licenseField);
        
        // Champ pour l'adresse
        panel.add(new JLabel("Adresse:"));
        ModernTheme.styleLabel((JLabel) panel.getComponent(12));
        JTextField addressField = new JTextField();
        ModernTheme.styleTextField(addressField);
        panel.add(addressField);

        // Affichage du formulaire
        int option = JOptionPane.showConfirmDialog(this, panel, "Ajouter Client", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (option == JOptionPane.OK_OPTION) {
            try {
                // Récupération des valeurs
                String name = nameField.getText().trim();
                String surname = surnameField.getText().trim();
                String email = emailField.getText().trim();
                String phoneNumber = phoneField.getText().trim();
                String birthDateStr = birthDateField.getText().trim();
                String licenseNumber = licenseField.getText().trim();
                String address = addressField.getText().trim();
                
                // Validation des données
                if (name.isEmpty() || surname.isEmpty()) {
                    JOptionPane.showMessageDialog(this,
                            "Le nom et le prénom sont obligatoires",
                            "Erreur de saisie",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                if (email.isEmpty() || !email.contains("@")) {
                    JOptionPane.showMessageDialog(this,
                            "L'email est obligatoire et doit être valide",
                            "Erreur de saisie",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                // Conversion de la date de naissance
                LocalDate birthDate;
                try {
                    birthDate = LocalDate.parse(birthDateStr, DateTimeFormatter.ISO_LOCAL_DATE);
                    
                    // Vérification que la date n'est pas dans le futur
                    if (birthDate.isAfter(LocalDate.now())) {
                        JOptionPane.showMessageDialog(this,
                                "La date de naissance ne peut pas être dans le futur",
                                "Erreur de saisie",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    
                    // Vérification de l'âge (au moins 18 ans)
                    if (birthDate.plusYears(18).isAfter(LocalDate.now())) {
                        JOptionPane.showMessageDialog(this,
                                "Le client doit avoir au moins 18 ans",
                                "Erreur de saisie",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    
                } catch (DateTimeParseException e) {
                    JOptionPane.showMessageDialog(this,
                            "Format de date invalide. Utilisez le format YYYY-MM-DD",
                            "Erreur de saisie",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                // Création du client
                Client client = new Client();
                client.setName(name);
                client.setSurname(surname);
                client.setEmail(email);
                client.setPhoneNumber(phoneNumber);
                client.setBirthDate(birthDate);
                client.setLicenseNumber(licenseNumber);
                client.setAddress(address);
                
                // Ajout du client via le contrôleur
                int newId = controller.addClient(client);
                
                // Affichage d'un message de confirmation
                JOptionPane.showMessageDialog(this,
                        "Client ajouté avec succès. ID: " + newId,
                        "Succès",
                        JOptionPane.INFORMATION_MESSAGE);
                
                // Mettre à jour le tableau des clients
                updateClientsTable();
                
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                        "Erreur lors de l'ajout du client: " + e.getMessage(),
                        "Erreur",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Affiche le formulaire d'ajout de location
     */
    private void showAddLocationForm() {
        JPanel panel = new JPanel(new GridLayout(9, 2, 10, 10));
        ModernTheme.stylePanel(panel);
        
        // Liste déroulante pour le véhicule
        panel.add(new JLabel("Véhicule:"));
        ModernTheme.styleLabel((JLabel) panel.getComponent(0));
        
        // Récupérer la liste des véhicules
        ArrayList<Car> cars = controller.getAllCars();
        JComboBox<String> carComboBox = new JComboBox<>();
        Map<String, Car> carMap = new HashMap<>(); // Pour stocker les références aux voitures
        
        for (Car car : cars) {
            if (car.isAvailable()) {
                carComboBox.addItem(car.getIdCar() + " - " + car.getBrand() + " " + car.getModel() + " (" + car.getPriceday() + "€/jour)");
                carMap.put(car.getIdCar(), car);
            }
        }
        ModernTheme.styleComboBox(carComboBox);
        panel.add(carComboBox);

        // Liste déroulante pour le client
        panel.add(new JLabel("Client:"));
        ModernTheme.styleLabel((JLabel) panel.getComponent(2));
        
        // Récupérer la liste des clients
        ArrayList<Client> clients = controller.getAllClients();
        JComboBox<String> clientComboBox = new JComboBox<>();
        for (Client client : clients) {
            clientComboBox.addItem(client.getIdClient() + " - " + client.getName() + " " + client.getSurname());
        }
        ModernTheme.styleComboBox(clientComboBox);
        panel.add(clientComboBox);

        // Champ pour la date de début
        panel.add(new JLabel("Date début (yyyy-MM-dd):"));
        ModernTheme.styleLabel((JLabel) panel.getComponent(4));
        JTextField startDateField = new JTextField();
        ModernTheme.styleTextField(startDateField);
        panel.add(startDateField);

        // Champ pour la date de fin
        panel.add(new JLabel("Date fin (yyyy-MM-dd):"));
        ModernTheme.styleLabel((JLabel) panel.getComponent(6));
        JTextField endDateField = new JTextField();
        ModernTheme.styleTextField(endDateField);
        panel.add(endDateField);

        // Champ pour le responsable
        panel.add(new JLabel("Responsable:"));
        ModernTheme.styleLabel((JLabel) panel.getComponent(8));
        JTextField responsableField = new JTextField();
        ModernTheme.styleTextField(responsableField);
        panel.add(responsableField);

        // Champ pour les notes
        panel.add(new JLabel("Notes:"));
        ModernTheme.styleLabel((JLabel) panel.getComponent(10));
        JTextField notesField = new JTextField();
        ModernTheme.styleTextField(notesField);
        panel.add(notesField);

        // Option pour l'assurance de base
        panel.add(new JLabel("Assurance de base:"));
        ModernTheme.styleLabel((JLabel) panel.getComponent(12));
        JCheckBox assuranceBaseBox = new JCheckBox();
        panel.add(assuranceBaseBox);
        
        // Bouton pour calculer le prix
        panel.add(new JLabel("Calculer le prix:"));
        ModernTheme.styleLabel((JLabel) panel.getComponent(14));
        JButton calculateButton = new JButton("Calculer");
        ModernTheme.styleButton(calculateButton);
        panel.add(calculateButton);

        // Champ pour le prix (calculé automatiquement)
        panel.add(new JLabel("Prix:"));
        ModernTheme.styleLabel((JLabel) panel.getComponent(16));
        JTextField priceField = new JTextField();
        priceField.setEditable(false); // Le prix est calculé automatiquement
        ModernTheme.styleTextField(priceField);
        panel.add(priceField);
        
        // Fonction pour calculer le prix
        Runnable calculatePrice = () -> {
            try {
                String carSelection = (String) carComboBox.getSelectedItem();
                if (carSelection == null || startDateField.getText().isEmpty() || endDateField.getText().isEmpty()) {
                    return;
                }
                
                String carId = carSelection.split(" - ")[0];
                Car selectedCar = carMap.get(carId);
                
                if (selectedCar != null) {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    try {
                        LocalDate dateDebut = LocalDate.parse(startDateField.getText().trim(), formatter);
                        LocalDate dateFin = LocalDate.parse(endDateField.getText().trim(), formatter);
                        
                        if (!dateDebut.isAfter(dateFin)) {
                            long nombreJours = ChronoUnit.DAYS.between(dateDebut, dateFin);
                            if (nombreJours < 1) nombreJours = 1;
                            
                            float prixJournalier = selectedCar.getPriceday();
                            float prixBase = prixJournalier * nombreJours;
                            
                            // Ajout du coût de l'assurance de base si sélectionnée
                            if (assuranceBaseBox.isSelected()) {
                                prixBase += 10.0f * nombreJours; // 10€ par jour pour l'assurance de base
                            }
                            
                            priceField.setText(String.format("%.2f", prixBase));
                        }
                    } catch (DateTimeParseException ex) {
                        JOptionPane.showMessageDialog(panel, 
                            "Format de date incorrect. Utilisez le format yyyy-MM-dd", 
                            "Erreur de format", 
                            JOptionPane.ERROR_MESSAGE);
                    }
                }
            } catch (Exception ex) {
                // Ignorer les autres erreurs pendant la saisie
                System.out.println("Erreur lors du calcul du prix: " + ex.getMessage());
            }
        };
        
        // Ajouter l'action au bouton de calcul
        calculateButton.addActionListener(e -> calculatePrice.run());
        
        // Ajouter un écouteur pour la case à cocher d'assurance
        assuranceBaseBox.addActionListener(e -> calculatePrice.run());
        
        // Ajouter un écouteur pour le changement de véhicule
        carComboBox.addActionListener(e -> calculatePrice.run());

        // Affichage du formulaire
        int option = JOptionPane.showConfirmDialog(this, panel, "Ajouter Réservation", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (option == JOptionPane.OK_OPTION) {
            try {
                // Récupération des valeurs
                String carSelection = (String) carComboBox.getSelectedItem();
                String clientSelection = (String) clientComboBox.getSelectedItem();
                
                // Extraire les IDs des sélections
                String carId = carSelection.split(" - ")[0];
                int clientId = Integer.parseInt(clientSelection.split(" - ")[0]);
                
                String startDateStr = startDateField.getText().trim();
                String endDateStr = endDateField.getText().trim();
                String responsable = responsableField.getText().trim();
                String notes = notesField.getText().trim();
                
                // Si le prix n'a pas été calculé, le faire maintenant
                if (priceField.getText().isEmpty()) {
                    calculatePrice.run();
                }
                
                float price = Float.parseFloat(priceField.getText().trim().replace(",", "."));
                boolean assuranceBase = assuranceBaseBox.isSelected();
                
                // Conversion des dates
                LocalDate dateDebut, dateFin;
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                
                try {
                    dateDebut = LocalDate.parse(startDateStr, formatter);
                    dateFin = LocalDate.parse(endDateStr, formatter);
                } catch (DateTimeParseException e) {
                    JOptionPane.showMessageDialog(this,
                            "Format de date incorrect. Utilisez le format yyyy-MM-dd",
                            "Erreur de saisie",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                // Validation des dates
                if (dateDebut.isAfter(dateFin)) {
                    JOptionPane.showMessageDialog(this,
                            "La date de début doit être antérieure à la date de fin",
                            "Erreur de saisie",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                // Validation des données
                if (responsable.isEmpty()) {
                    JOptionPane.showMessageDialog(this,
                            "Le responsable est obligatoire",
                            "Erreur de saisie",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                // Ajout de la réservation via le contrôleur
                int newId = controller.addReservation(carId, clientId, responsable, notes, price, 
                                                     dateDebut, dateFin, assuranceBase);
                
                // Affichage d'un message de confirmation
                JOptionPane.showMessageDialog(this,
                        "Réservation ajoutée avec succès. ID: " + newId,
                        "Succès",
                        JOptionPane.INFORMATION_MESSAGE);
                
                // Mettre à jour le tableau des réservations
                updateReservationsTable();
                
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this,
                        "Erreur de format : vérifiez les valeurs numériques",
                        "Erreur de saisie",
                        JOptionPane.ERROR_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                        "Erreur lors de l'ajout de la réservation: " + e.getMessage(),
                        "Erreur",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Affiche le formulaire d'ajout de contrat
     */
    private void showAddContratForm() {
        JPanel panel = new JPanel(new GridLayout(10, 2, 10, 10));
        ModernTheme.stylePanel(panel);
        
        // Liste déroulante pour les réservations existantes
        panel.add(new JLabel("Réservation existante:"));
        ModernTheme.styleLabel((JLabel) panel.getComponent(0));
        
        // Récupérer la liste des réservations
        ArrayList<Reservation> reservations = controller.getAllReservations();
        JComboBox<String> reservationComboBox = new JComboBox<>();
        reservationComboBox.addItem("-- Nouvelle réservation --");
        
        // Map pour stocker les références aux réservations
        Map<Integer, Reservation> reservationMap = new HashMap<>();
        
        for (Reservation reservation : reservations) {
            // Afficher toutes les réservations qui ne sont pas des contrats
            if (!(reservation instanceof Contrat)) {
                String clientInfo = "N/A";
                String carInfo = "N/A";
                
                if (reservation.getClient() != null) {
                    Client client = reservation.getClient();
                    clientInfo = client.getName() + " " + client.getSurname();
                }
                
                if (reservation.getCar() != null) {
                    Car car = reservation.getCar();
                    carInfo = car.getBrand() + " " + car.getModel();
                }
                
                String dateInfo = "";
                if (reservation.getDateDebut() != null && reservation.getDateFin() != null) {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    dateInfo = " du " + reservation.getDateDebut().format(formatter) + 
                              " au " + reservation.getDateFin().format(formatter);
                }
                
                String statutInfo = " [" + reservation.getStatut().getLibelle() + "]";
                
                reservationComboBox.addItem(reservation.getIdReservation() + " - " + clientInfo + 
                                           " - " + carInfo + dateInfo + statutInfo);
                reservationMap.put(reservation.getIdReservation(), reservation);
            }
        }
        
        ModernTheme.styleComboBox(reservationComboBox);
        panel.add(reservationComboBox);

        // Liste déroulante pour le véhicule
        panel.add(new JLabel("Véhicule:"));
        ModernTheme.styleLabel((JLabel) panel.getComponent(2));
        
        // Récupérer la liste des véhicules
        ArrayList<Car> cars = controller.getAllCars();
        JComboBox<String> carComboBox = new JComboBox<>();
        for (Car car : cars) {
            if (car.isAvailable()) {
                carComboBox.addItem(car.getIdCar() + " - " + car.getBrand() + " " + car.getModel());
            }
        }
        ModernTheme.styleComboBox(carComboBox);
        panel.add(carComboBox);

        // Liste déroulante pour le client
        panel.add(new JLabel("Client:"));
        ModernTheme.styleLabel((JLabel) panel.getComponent(4));
        
        // Récupérer la liste des clients
        ArrayList<Client> clients = controller.getAllClients();
        JComboBox<String> clientComboBox = new JComboBox<>();
        for (Client client : clients) {
            clientComboBox.addItem(client.getIdClient() + " - " + client.getName() + " " + client.getSurname());
        }
        ModernTheme.styleComboBox(clientComboBox);
        panel.add(clientComboBox);

        // Champ pour la date de début
        panel.add(new JLabel("Date début (yyyy-MM-dd):"));
        ModernTheme.styleLabel((JLabel) panel.getComponent(6));
        JTextField startDateField = new JTextField();
        ModernTheme.styleTextField(startDateField);
        panel.add(startDateField);

        // Champ pour la date de fin
        panel.add(new JLabel("Date fin (yyyy-MM-dd):"));
        ModernTheme.styleLabel((JLabel) panel.getComponent(8));
        JTextField endDateField = new JTextField();
        ModernTheme.styleTextField(endDateField);
        panel.add(endDateField);

        // Champ pour le responsable
        panel.add(new JLabel("Responsable:"));
        ModernTheme.styleLabel((JLabel) panel.getComponent(10));
        JTextField responsableField = new JTextField();
        ModernTheme.styleTextField(responsableField);
        panel.add(responsableField);

        // Champ pour la caution
        panel.add(new JLabel("Caution:"));
        ModernTheme.styleLabel((JLabel) panel.getComponent(12));
        JTextField cautionField = new JTextField();
        ModernTheme.styleTextField(cautionField);
        panel.add(cautionField);
        
        // Champ pour le type d'assurance
        panel.add(new JLabel("Type Assurance:"));
        ModernTheme.styleLabel((JLabel) panel.getComponent(14));
        JComboBox<String> assuranceBox = new JComboBox<>(new String[]{"Base", "Premium", "Tous risques"});
        ModernTheme.styleComboBox(assuranceBox);
        panel.add(assuranceBox);
        
        // Champ pour les options
        panel.add(new JLabel("Options (séparées par des virgules):"));
        ModernTheme.styleLabel((JLabel) panel.getComponent(16));
        JTextField optionsField = new JTextField();
        ModernTheme.styleTextField(optionsField);
        panel.add(optionsField);
        
        // Champ pour indiquer si le contrat est signé
        panel.add(new JLabel("Signé:"));
        ModernTheme.styleLabel((JLabel) panel.getComponent(18));
        JCheckBox signedBox = new JCheckBox();
        panel.add(signedBox);
        
        // Ajouter un écouteur pour la sélection de réservation
        reservationComboBox.addActionListener(e -> {
            String selected = (String) reservationComboBox.getSelectedItem();
            if (selected != null && !selected.equals("-- Nouvelle réservation --")) {
                try {
                    // Extraire l'ID de la réservation
                    int reservationId = Integer.parseInt(selected.split(" - ")[0]);
                    Reservation selectedReservation = reservationMap.get(reservationId);
                    
                    if (selectedReservation != null) {
                        // Pré-remplir les champs avec les informations de la réservation
                        
                        // Sélectionner le véhicule
                        if (selectedReservation.getCar() != null) {
                            for (int i = 0; i < carComboBox.getItemCount(); i++) {
                                String item = carComboBox.getItemAt(i);
                                String carId = item.split(" - ")[0];
                                if (carId.equals(selectedReservation.getCar().getIdCar())) {
                                    carComboBox.setSelectedIndex(i);
                                    break;
                                }
                            }
                        }
                        
                        // Sélectionner le client
                        if (selectedReservation.getClient() != null) {
                            for (int i = 0; i < clientComboBox.getItemCount(); i++) {
                                String item = clientComboBox.getItemAt(i);
                                int clientId = Integer.parseInt(item.split(" - ")[0]);
                                if (clientId == selectedReservation.getClient().getIdClient()) {
                                    clientComboBox.setSelectedIndex(i);
                                    break;
                                }
                            }
                        }
                        
                        // Remplir les dates
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                        if (selectedReservation.getDateDebut() != null) {
                            startDateField.setText(selectedReservation.getDateDebut().format(formatter));
                        }
                        
                        if (selectedReservation.getDateFin() != null) {
                            endDateField.setText(selectedReservation.getDateFin().format(formatter));
                        }
                        
                        // Remplir le responsable
                        responsableField.setText(selectedReservation.getResponsable());
                        
                        // Pré-sélectionner l'assurance si c'est une réservation standard avec assurance
                        if (selectedReservation instanceof ReservationStandard) {
                            ReservationStandard stdReservation = (ReservationStandard) selectedReservation;
                            if (stdReservation.isAssuranceBase()) {
                                assuranceBox.setSelectedItem("Base");
                            }
                        }
                        
                        // Désactiver les champs qui ne doivent pas être modifiés
                        carComboBox.setEnabled(false);
                        clientComboBox.setEnabled(false);
                        startDateField.setEnabled(false);
                        endDateField.setEnabled(false);
                        responsableField.setEnabled(false);
                    }
                } catch (Exception ex) {
                    // Ignorer les erreurs lors de la sélection
                    System.out.println("Erreur lors de la sélection de la réservation: " + ex.getMessage());
                }
            } else {
                // Réactiver tous les champs
                carComboBox.setEnabled(true);
                clientComboBox.setEnabled(true);
                startDateField.setEnabled(true);
                endDateField.setEnabled(true);
                responsableField.setEnabled(true);
                
                // Vider les champs
                startDateField.setText("");
                endDateField.setText("");
                responsableField.setText("");
                cautionField.setText("");
                optionsField.setText("");
                signedBox.setSelected(false);
            }
        });

        // Affichage du formulaire
        int option = JOptionPane.showConfirmDialog(this, panel, "Ajouter Contrat", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (option == JOptionPane.OK_OPTION) {
            try {
                // Vérifier si une réservation existante a été sélectionnée
                String reservationSelection = (String) reservationComboBox.getSelectedItem();
                Integer reservationId = null;
                
                if (reservationSelection != null && !reservationSelection.equals("-- Nouvelle réservation --")) {
                    reservationId = Integer.parseInt(reservationSelection.split(" - ")[0]);
                }
                
                // Récupération des valeurs
                String carSelection = (String) carComboBox.getSelectedItem();
                String clientSelection = (String) clientComboBox.getSelectedItem();
                
                // Extraire les IDs des sélections
                String carId = carSelection.split(" - ")[0];
                int clientId = Integer.parseInt(clientSelection.split(" - ")[0]);
                
                String startDateStr = startDateField.getText().trim();
                String endDateStr = endDateField.getText().trim();
                String responsable = responsableField.getText().trim();
                double caution = Double.parseDouble(cautionField.getText().trim());
                String typeAssurance = (String) assuranceBox.getSelectedItem();
                String optionsStr = optionsField.getText().trim();
                boolean estSigne = signedBox.isSelected();
                
                // Conversion des dates
                LocalDate dateDebut, dateFin;
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                
                try {
                    dateDebut = LocalDate.parse(startDateStr, formatter);
                    dateFin = LocalDate.parse(endDateStr, formatter);
                } catch (DateTimeParseException e) {
                    JOptionPane.showMessageDialog(this,
                            "Format de date incorrect. Utilisez le format yyyy-MM-dd",
                            "Erreur de saisie",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                // Validation des dates
                if (dateDebut.isAfter(dateFin)) {
                    JOptionPane.showMessageDialog(this,
                            "La date de début doit être antérieure à la date de fin",
                            "Erreur de saisie",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                // Validation du responsable
                if (responsable.isEmpty()) {
                    JOptionPane.showMessageDialog(this,
                            "Le responsable est obligatoire",
                            "Erreur de saisie",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                // Validation des montants
                if (caution < 0) {
                    JOptionPane.showMessageDialog(this,
                            "La caution doit être positive ou nulle",
                            "Erreur de saisie",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                // Conversion des options en liste
                List<String> options = new ArrayList<>();
                if (!optionsStr.isEmpty()) {
                    options = Arrays.asList(optionsStr.split(","));
                }
                
                // Ajout du contrat via le contrôleur
                int newId;
                if (reservationId != null) {
                    // Créer un contrat à partir d'une réservation existante
                    newId = controller.addContratFromReservation(reservationId, caution, typeAssurance, options, estSigne);
                } else {
                    // Créer un nouveau contrat
                    newId = controller.addContrat(carId, clientId, dateDebut, dateFin, 
                                                caution, typeAssurance, options, estSigne);
                }
                
                // Affichage d'un message de confirmation
                JOptionPane.showMessageDialog(this,
                        "Contrat ajouté avec succès. ID: " + newId,
                        "Succès",
                        JOptionPane.INFORMATION_MESSAGE);
                
                // Mettre à jour le tableau des contrats
                updateContratsTable();
                
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this,
                        "Erreur de format : vérifiez les valeurs numériques",
                        "Erreur de saisie",
                        JOptionPane.ERROR_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                        "Erreur lors de l'ajout du contrat: " + e.getMessage(),
                        "Erreur",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Affiche le formulaire de modification d'une réservation
     */
    private void showModifyReservationForm() {
        // Vérifier qu'une ligne est sélectionnée
        int selectedRow = table3.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                    "Veuillez sélectionner une réservation à modifier",
                    "Aucune sélection",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Récupérer l'ID de la réservation sélectionnée
        int reservationId = (int) table3.getValueAt(selectedRow, 0);
        
        // Récupérer la réservation via le contrôleur
        Reservation reservation = controller.findReservationById(reservationId);
        if (reservation == null) {
            JOptionPane.showMessageDialog(this,
                    "La réservation n'a pas pu être récupérée",
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Créer le formulaire de modification
        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        ModernTheme.stylePanel(panel);
        
        // Informations sur la réservation (non modifiables)
        panel.add(new JLabel("ID Réservation:"));
        ModernTheme.styleLabel((JLabel) panel.getComponent(0));
        JTextField idField = new JTextField(String.valueOf(reservation.getIdReservation()));
        idField.setEditable(false);
        ModernTheme.styleTextField(idField);
        panel.add(idField);
        
        // Informations sur le client et le véhicule
        String clientInfo = "N/A";
        if (reservation.getClient() != null) {
            Client client = reservation.getClient();
            clientInfo = client.getName() + " " + client.getSurname();
        }
        
        panel.add(new JLabel("Client:"));
        ModernTheme.styleLabel((JLabel) panel.getComponent(2));
        JTextField clientField = new JTextField(clientInfo);
        clientField.setEditable(false);
        ModernTheme.styleTextField(clientField);
        panel.add(clientField);
        
        // Champ pour le statut
        panel.add(new JLabel("Statut:"));
        ModernTheme.styleLabel((JLabel) panel.getComponent(4));
        
        // Créer un tableau avec tous les statuts possibles
        String[] statuts = {"En attente", "Confirmée", "Annulée", "Terminée"};
        JComboBox<String> statutComboBox = new JComboBox<>(statuts);
        ModernTheme.styleComboBox(statutComboBox);
        
        // Pré-sélectionner le statut actuel
        String statutActuel = reservation.getStatut().getLibelle();
        for (int i = 0; i < statuts.length; i++) {
            if (statuts[i].equals(statutActuel)) {
                statutComboBox.setSelectedIndex(i);
                break;
            }
        }
        
        panel.add(statutComboBox);
        
        // Champ pour les notes
        panel.add(new JLabel("Notes:"));
        ModernTheme.styleLabel((JLabel) panel.getComponent(6));
        JTextField notesField = new JTextField(reservation.getNotes());
        ModernTheme.styleTextField(notesField);
        panel.add(notesField);
        
        // Champ pour le prix
        panel.add(new JLabel("Prix:"));
        ModernTheme.styleLabel((JLabel) panel.getComponent(8));
        JTextField priceField = new JTextField(String.valueOf(reservation.getPrice()));
        priceField.setEditable(false);
        ModernTheme.styleTextField(priceField);
        panel.add(priceField);
        
        // Affichage du formulaire
        int option = JOptionPane.showConfirmDialog(this, panel, "Modifier Réservation", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (option == JOptionPane.OK_OPTION) {
            try {
                // Récupération des valeurs
                String statut = (String) statutComboBox.getSelectedItem();
                String notes = notesField.getText().trim();
                
                // Mise à jour des propriétés de la réservation
                reservation.setNotes(notes);
                
                // Mise à jour du statut
                switch (statut) {
                    case "En attente":
                        // Pas besoin de changer le statut, c'est la valeur par défaut
                        break;
                    case "Confirmée":
                        reservation.confirmer();
                        break;
                    case "Annulée":
                        reservation.annuler();
                        break;
                    case "Terminée":
                        reservation.terminer();
                        break;
                    default:
                        // Statut non reconnu, ne rien faire
                        break;
                }
                
                // Modification de la réservation via le contrôleur
                boolean success = controller.updateReservation(reservation);
                
                if (success) {
                    // Affichage d'un message de confirmation
                    JOptionPane.showMessageDialog(this,
                            "Réservation modifiée avec succès",
                            "Succès",
                            JOptionPane.INFORMATION_MESSAGE);
                    
                    // Mettre à jour le tableau des réservations
                    updateReservationsTable();
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Erreur lors de la sauvegarde des modifications",
                            "Erreur",
                            JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                        "Erreur lors de la modification de la réservation: " + e.getMessage(),
                        "Erreur",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /******************************************************************************
     * GESTION DE L'AUTHENTIFICATION ET DE L'INTERFACE UTILISATEUR
     ******************************************************************************/
    /**
     * Met à jour l'interface utilisateur en fonction de l'état de connexion
     */
    private void updateUIBasedOnLoginState() {
        // Mettre à jour les boutons et les menus en fonction de l'état de connexion
        if (isLoggedIn) {
            // Mettre à jour les tableaux avec les données
            updateAllTables();
        } else {
            // Effacer les tableaux
            clearAllTables();
        }
    }
    
    /**
     * Affiche la boîte de dialogue de connexion/inscription
     */
    private void showSessionDialog() {
        // Code pour afficher la boîte de dialogue de connexion/inscription
        // ...
    }
    
    /**
     * Efface toutes les données des tableaux
     */
    private void clearAllTables() {
        // Effacer les données du tableau des voitures
        DefaultTableModel model1 = (DefaultTableModel) table1.getModel();
        model1.setRowCount(0);
        
        // Effacer les données du tableau des clients
        DefaultTableModel model2 = (DefaultTableModel) table2.getModel();
        model2.setRowCount(0);
        
        // Effacer les données du tableau des réservations
        DefaultTableModel model3 = (DefaultTableModel) table3.getModel();
        model3.setRowCount(0);
        
        // Effacer les données du tableau des contrats
        DefaultTableModel model4 = (DefaultTableModel) table4.getModel();
        model4.setRowCount(0);
    }
    
    /**
     * Libère les ressources lors de la fermeture de l'application
     * et sauvegarde les données
     */
    @Override
    public void dispose() {
        // Sauvegarder les données
        controller.saveAllData();
        
        // Libérer les ressources
        super.dispose();
    }
    
    /**
     * Point d'entrée de l'application
     * @param args Arguments de ligne de commande (non utilisés)
     */
    public static void main(String[] args) {
        // Définir le look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Lancer l'application
        SwingUtilities.invokeLater(() -> {
            JFramesLocation app = new JFramesLocation();
            app.setVisible(true);
        });
    }
}
