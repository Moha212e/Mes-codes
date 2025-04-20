package org.example.view.GUI;

import javax.swing.*;
import java.awt.*;

public class IconProvider {



    private static final int DEFAULT_ICON_SIZE = 16;

    // Icônes pour les actions principales
    public static final Icon REFRESH_ICON = createIcon("🔄", DEFAULT_ICON_SIZE);
    public static final Icon SEARCH_ICON = createIcon("🔍 ", DEFAULT_ICON_SIZE);
    public static final Icon IMPORT_ICON = createIcon("📂",DEFAULT_ICON_SIZE);
    public static final Icon CONTRACT_ICON = createIcon("\uD83D\uDCC5 ", DEFAULT_ICON_SIZE);
    public static final Icon ADD_ICON = createIcon("➕ ", DEFAULT_ICON_SIZE);          // Plus
    public static final Icon EDIT_ICON = createIcon("✏️", DEFAULT_ICON_SIZE);         // Crayon
    public static final Icon DELETE_ICON = createIcon("❌", DEFAULT_ICON_SIZE);       // X
    public static final Icon LOGIN_ICON = createIcon("\uD83D\uDC64", DEFAULT_ICON_SIZE);  // Utilisateur 👤
    public static final Icon LOGOUT_ICON = createIcon("🚪", DEFAULT_ICON_SIZE);       // Porte (déconnexion)
    public static final Icon FILE_ICON = createIcon("\uD83D\uDCC4", DEFAULT_ICON_SIZE);   // Document 📄
    public static final Icon EXIT_ICON = createIcon("🔚", DEFAULT_ICON_SIZE); // Fin
    public static final Icon CAR_ICON = createIcon("\uD83D\uDE97", DEFAULT_ICON_SIZE);   // Voiture 🚗
    public static final Icon CLIENT_ICON = createIcon("\uD83D\uDC65", DEFAULT_ICON_SIZE); // Groupe 👥
    public static final Icon RESERVATION_ICON = createIcon("\uD83D\uDCC5", DEFAULT_ICON_SIZE); // Calendrier 📅
    public static final Icon GALLERY_ICON = createIcon("\uD83D\uDDBC", DEFAULT_ICON_SIZE); // Image 🖼️

    /**
     * Crée une icône à partir d'un caractère Unicode ou d'un emoji.
     * @param unicode Le caractère Unicode ou emoji à dessiner.
     * @param size La taille de l'icône.
     * @return L'icône générée.
     */
    public static Icon createIcon(String unicode, int size) {
        return new Icon() {
            @Override
            public void paintIcon(Component c, Graphics g, int x, int y) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                g2.setFont(new Font("Segoe UI Emoji", Font.PLAIN, size));
                g2.setColor(c.getForeground());
                g2.drawString(unicode, x, y + size - 2);
                g2.dispose();
            }

            @Override
            public int getIconWidth() {
                return size;
            }

            @Override
            public int getIconHeight() {
                return size;
            }
        };
    }
}
