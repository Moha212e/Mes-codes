package org.example.view.GUI.panels;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

/**
 * Panneau pour l'affichage des images de véhicules
 */
public class VehicleImagesPanel extends JPanel {
    private int tailleBorder = 30;

    /**
     * Constructeur du panneau d'images de véhicules
     */
    public VehicleImagesPanel() {
        setLayout(new GridLayout(2, 3, 10, 10));
        setBorder(BorderFactory.createEmptyBorder(tailleBorder, tailleBorder, tailleBorder, tailleBorder));
        
        // Définir les images des véhicules
        String[] carImages = {
                "aston.jpg", "coccinelle.jpg", "labo.jpg", "mustang.jpg", "nissan.jpg", "toyota.jpg"
        };
        String[] plateNumbers = {
                "AB123CD", "EF456GH", "IJ789KL", "MN012OP", "QR345ST", "UV678WX"
        };
        
        // Ajouter les images avec les numéros d'immatriculation
        loadVehicleImages(carImages, plateNumbers);
    }
    
    /**
     * Charge et affiche les images des véhicules
     * 
     * @param carImages Tableau des noms de fichiers d'images
     * @param plateNumbers Tableau des numéros d'immatriculation
     */
    private void loadVehicleImages(String[] carImages, String[] plateNumbers) {
        for (int i = 0; i < carImages.length; i++) {
            JPanel carPanel = new JPanel(new BorderLayout());
            carPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            
            // Charger l'image
            try {
                String imagePath = "/images/" + carImages[i];
                InputStream is = getClass().getResourceAsStream(imagePath);
                
                if (is == null) {
                    System.err.println("⚠️ Image non trouvée : " + imagePath);
                    
                    // Créer une image par défaut
                    JLabel defaultImageLabel = new JLabel("Image non disponible", SwingConstants.CENTER);
                    defaultImageLabel.setPreferredSize(new Dimension(200, 150));
                    defaultImageLabel.setBackground(Color.LIGHT_GRAY);
                    defaultImageLabel.setOpaque(true);
                    carPanel.add(defaultImageLabel, BorderLayout.CENTER);
                } else {
                    BufferedImage img = ImageIO.read(is);
                    ImageIcon imageIcon = new ImageIcon(img.getScaledInstance(200, 150, Image.SCALE_SMOOTH));
                    JLabel imageLabel = new JLabel(imageIcon);
                    carPanel.add(imageLabel, BorderLayout.CENTER);
                }
                
                // Ajouter le numéro d'immatriculation
                JLabel plateLabel = new JLabel("Immat: " + plateNumbers[i], SwingConstants.CENTER);
                carPanel.add(plateLabel, BorderLayout.SOUTH);
                
                add(carPanel);
                
            } catch (IOException ex) {
                System.err.println("❌ Erreur de lecture d'image : " + ex.getMessage());
                ex.printStackTrace();
            }
        }
    }
    
    /**
     * Met à jour les images affichées
     */
    public void refreshImages() {
        // Mettre à jour les images
        // À implémenter si besoin de rafraîchir dynamiquement
    }
}
