package org.example.view.GUI;

import org.example.model.entity.Client;
import org.example.view.ViewLocation;
import org.example.view.GUI.dialogs.SessionDialog;
import org.example.view.GUI.panels.*;

import javax.swing.*;
import java.awt.*;

/**
 * Version alternative de la fenêtre principale de l'application LocaDrive
 * Cette classe est fournie à titre d'exemple et n'est pas utilisée dans l'application
 */
public class JFramesLocation2 extends JFrame implements ViewLocation {
    private JTabbedPane tabbedPane;
    private VehiclePanel vehiclePanel;
    private ClientPanel clientPanel;
    private LocationPanel locationPanel;
    private VehicleImagesPanel vehicleImagesPanel;
    private ContratPanel contratPanel;

    /**
     * Constructeur de la fenêtre principale
     */
    public JFramesLocation2() {
        super("LocaDrive");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Bouton session
        JButton sessionButton = new JButton("Session");
        sessionButton.addActionListener(e -> SessionDialog.showDialog(this));
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        topPanel.add(sessionButton);
        add(topPanel, BorderLayout.NORTH);

        // Création des panneaux
        vehiclePanel = new VehiclePanel();
        clientPanel = new ClientPanel();
        locationPanel = new LocationPanel();
        vehicleImagesPanel = new VehicleImagesPanel();
        contratPanel = new ContratPanel();

        // Création du JTabbedPane
        tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Lister Véhicules", vehiclePanel);
        tabbedPane.addTab("Lister Clients", clientPanel);
        tabbedPane.addTab("Lister Locations", locationPanel);
        tabbedPane.addTab("Véhicule", vehicleImagesPanel);
        tabbedPane.addTab("Lister Contrats", contratPanel);
        tabbedPane.setSelectedIndex(0);

        add(tabbedPane, BorderLayout.CENTER);

        setSize(1000, 700);
        setLocationRelativeTo(null);
    }

    /**
     * Implémentation des méthodes de l'interface ViewLocation
     */
    @Override
    public void showLocation(Client client) {
        // Afficher les locations pour un client spécifique
        JOptionPane.showMessageDialog(this, 
            "Affichage des locations pour le client: " + client.getName() + " " + client.getSurname(),
            "Locations du client", 
            JOptionPane.INFORMATION_MESSAGE);
        
        // Filtrer les locations pour n'afficher que celles du client
        locationPanel.filterByClient(client.getIdClient());
        
        // Sélectionner l'onglet des locations
        tabbedPane.setSelectedIndex(2);
    }

    @Override
    public void showAddLocationForm() {
        locationPanel.showAddLocationForm();
    }

    @Override
    public void showUpdateLocationForm() {
        locationPanel.showModifyLocationForm();
    }

    @Override
    public void showDeleteLocationForm() {
        locationPanel.showDeleteLocationForm();
    }

    /**
     * Méthode principale pour lancer l'application
     * 
     * @param args Arguments de ligne de commande
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFramesLocation2 frame = new JFramesLocation2();
            frame.setVisible(true);
        });
    }
}
