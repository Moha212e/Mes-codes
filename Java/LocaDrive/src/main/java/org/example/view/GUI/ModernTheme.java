package org.example.view.GUI;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

/**
 * Classe définissant le thème moderne de l'application
 * Implémente un design Dracula avec fond sombre et couleurs vives
 */
public class ModernTheme {
    // Palette de couleurs Dracula
    public static final Color PRIMARY_COLOR = new Color(139, 233, 253);    // Cyan
    public static final Color PRIMARY_DARK_COLOR = new Color(98, 114, 164); // Bleu-violet
    public static final Color PRIMARY_LIGHT_COLOR = new Color(189, 147, 249); // Violet clair
    public static final Color ACCENT_COLOR = new Color(255, 121, 198);      // Rose
    public static final Color WARNING_COLOR = new Color(255, 85, 85);     // Rouge
    public static final Color BACKGROUND_COLOR = new Color(40, 42, 54); // Fond Dracula
    public static final Color CARD_COLOR = new Color(68, 71, 90);      // Gris foncé
    public static final Color TEXT_COLOR = new Color(248, 248, 242);         // Blanc cassé
    public static final Color TEXT_SECONDARY_COLOR = new Color(186, 187, 181); // Gris clair
    public static final Color DIVIDER_COLOR = new Color(98, 114, 164);   // Bleu-violet
    public static final Color DARKER_BACKGROUND = new Color(33, 34, 44);   // Fond encore plus sombre
    
    // Couleurs supplémentaires
    public static final Color SUCCESS_COLOR = new Color(80, 250, 123);     // Vert vif
    public static final Color INFO_COLOR = new Color(139, 233, 253);        // Cyan
    public static final Color ORANGE_COLOR = new Color(255, 184, 108);      // Orange
    public static final Color PURPLE_COLOR = new Color(189, 147, 249);     // Violet
    public static final Color TEAL_COLOR = new Color(0, 215, 186);        // Teal
    public static final Color PINK_COLOR = new Color(255, 121, 198);        // Rose
    public static final Color YELLOW_COLOR = new Color(241, 250, 140);       // Jaune
    
    // Polices modernes
    public static final Font MAIN_FONT = new Font("Fira Code", Font.PLAIN, 14);
    public static final Font TITLE_FONT = new Font("Fira Code", Font.BOLD, 18);
    public static final Font SUBTITLE_FONT = new Font("Fira Code", Font.BOLD, 16);
    public static final Font SMALL_FONT = new Font("Fira Code", Font.PLAIN, 12);
    
    // Bordures et ombres
    public static final int BORDER_RADIUS = 12;
    public static final int PADDING = 14;
    public static final int SMALL_PADDING = 8;
    
    // Bordures prédéfinies
    public static final Border CARD_BORDER = BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(PRIMARY_DARK_COLOR, 1, true),
            BorderFactory.createEmptyBorder(PADDING, PADDING, PADDING, PADDING)
    );
    
    public static final Border FIELD_BORDER = BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(PRIMARY_COLOR, 1, true),
            BorderFactory.createEmptyBorder(SMALL_PADDING, SMALL_PADDING, SMALL_PADDING, SMALL_PADDING)
    );
    public static final Color HIGHLIGHT_COLOR = new Color(255, 184, 108, 180); // Orange semi-transparent
    public static final Color BORDER_COLOR = new Color(98, 114, 164);        // Bleu-violet
    
    /**
     * Applique un style moderne à un bouton
     * @param button Bouton à styliser
     */
    public static void styleButton(JButton button) {
        button.setFont(MAIN_FONT);
        button.setForeground(BACKGROUND_COLOR);
        button.setBackground(PRIMARY_COLOR);
        button.setBorder(new RoundedBorder(BORDER_RADIUS, PRIMARY_COLOR));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        
        // Animation au survol
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(PRIMARY_LIGHT_COLOR);
                button.setBorder(new RoundedBorder(BORDER_RADIUS, PRIMARY_LIGHT_COLOR));
                button.repaint();
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(PRIMARY_COLOR);
                button.setBorder(new RoundedBorder(BORDER_RADIUS, PRIMARY_COLOR));
                button.repaint();
            }
            
            @Override
            public void mousePressed(MouseEvent e) {
                button.setBackground(PRIMARY_DARK_COLOR);
                button.setBorder(new RoundedBorder(BORDER_RADIUS, PRIMARY_DARK_COLOR));
                button.repaint();
            }
            
            @Override
            public void mouseReleased(MouseEvent e) {
                button.setBackground(PRIMARY_LIGHT_COLOR);
                button.setBorder(new RoundedBorder(BORDER_RADIUS, PRIMARY_LIGHT_COLOR));
                button.repaint();
            }
        });
        
        // Personnaliser le rendu du bouton
        button.setUI(new javax.swing.plaf.basic.BasicButtonUI() {
            @Override
            public void paint(Graphics g, JComponent c) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                AbstractButton b = (AbstractButton) c;
                ButtonModel model = b.getModel();
                
                // Déterminer la couleur de fond en fonction de l'état du bouton
                Color baseColor = b.getBackground();
                
                // Créer un gradient pour un effet 3D subtil
                GradientPaint gradient;
                if (model.isPressed()) {
                    gradient = new GradientPaint(
                            0, 0, darken(baseColor, 0.1f),
                            0, c.getHeight(), baseColor);
                } else {
                    gradient = new GradientPaint(
                            0, 0, lighten(baseColor, 0.1f),
                            0, c.getHeight(), baseColor);
                }
                
                // Dessiner le fond arrondi avec gradient
                RoundRectangle2D.Float shape = new RoundRectangle2D.Float(
                        1, 1, c.getWidth() - 2, c.getHeight() - 2, BORDER_RADIUS, BORDER_RADIUS);
                
                g2d.setPaint(gradient);
                g2d.fill(shape);
                
                // Ajouter un effet de lueur (glow)
                if (!model.isPressed()) {
                    g2d.setColor(new Color(baseColor.getRed(), baseColor.getGreen(), baseColor.getBlue(), 50));
                    g2d.setStroke(new BasicStroke(2.5f));
                    g2d.drawRoundRect(2, 2, c.getWidth() - 4, c.getHeight() - 4, BORDER_RADIUS, BORDER_RADIUS);
                }
                
                // Assurer que le texte est visible
                g2d.dispose();
                super.paint(g, c);
            }
        });
    }
    
    /**
     * Applique un style moderne à un bouton d'action (ajouter, modifier, supprimer)
     * @param button Bouton à styliser
     * @param type Type d'action ("add", "edit", "delete")
     */
    public static void styleActionButton(JButton button, String type) {
        button.setFont(MAIN_FONT);
        button.setForeground(BACKGROUND_COLOR);
        
        Color baseColor;
        Color hoverColor;
        Color pressedColor;
        
        switch (type.toLowerCase()) {
            case "add":
                baseColor = SUCCESS_COLOR;
                hoverColor = lighten(SUCCESS_COLOR, 0.2f);
                pressedColor = darken(SUCCESS_COLOR, 0.2f);
                break;
            case "delete":
                baseColor = WARNING_COLOR;
                hoverColor = lighten(WARNING_COLOR, 0.2f);
                pressedColor = darken(WARNING_COLOR, 0.2f);
                break;
            case "edit":
            case "modify":
                baseColor = INFO_COLOR;
                hoverColor = lighten(INFO_COLOR, 0.2f);
                pressedColor = darken(INFO_COLOR, 0.2f);
                break;
            default:
                baseColor = PRIMARY_COLOR;
                hoverColor = PRIMARY_LIGHT_COLOR;
                pressedColor = PRIMARY_DARK_COLOR;
        }
        
        button.setBackground(baseColor);
        button.setBorder(new RoundedBorder(BORDER_RADIUS, baseColor));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        
        // Animation au survol
        Color finalHoverColor = hoverColor;
        Color finalPressedColor = pressedColor;
        Color finalBaseColor = baseColor;
        
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(finalHoverColor);
                button.setBorder(new RoundedBorder(BORDER_RADIUS, finalHoverColor));
                button.repaint();
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(finalBaseColor);
                button.setBorder(new RoundedBorder(BORDER_RADIUS, finalBaseColor));
                button.repaint();
            }
            
            @Override
            public void mousePressed(MouseEvent e) {
                button.setBackground(finalPressedColor);
                button.setBorder(new RoundedBorder(BORDER_RADIUS, finalPressedColor));
                button.repaint();
            }
            
            @Override
            public void mouseReleased(MouseEvent e) {
                button.setBackground(finalHoverColor);
                button.setBorder(new RoundedBorder(BORDER_RADIUS, finalHoverColor));
                button.repaint();
            }
        });
        
        // Personnaliser le rendu du bouton
        button.setUI(new javax.swing.plaf.basic.BasicButtonUI() {
            @Override
            public void paint(Graphics g, JComponent c) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                AbstractButton b = (AbstractButton) c;
                ButtonModel model = b.getModel();
                
                // Déterminer la couleur de fond en fonction de l'état du bouton
                Color baseColor = b.getBackground();
                
                // Créer un gradient pour un effet 3D subtil
                GradientPaint gradient;
                if (model.isPressed()) {
                    gradient = new GradientPaint(
                            0, 0, darken(baseColor, 0.1f),
                            0, c.getHeight(), baseColor);
                } else {
                    gradient = new GradientPaint(
                            0, 0, lighten(baseColor, 0.1f),
                            0, c.getHeight(), baseColor);
                }
                
                // Dessiner le fond arrondi avec gradient
                RoundRectangle2D.Float shape = new RoundRectangle2D.Float(
                        1, 1, c.getWidth() - 2, c.getHeight() - 2, BORDER_RADIUS, BORDER_RADIUS);
                
                g2d.setPaint(gradient);
                g2d.fill(shape);
                
                // Ajouter un effet de lueur (glow)
                if (!model.isPressed()) {
                    g2d.setColor(new Color(baseColor.getRed(), baseColor.getGreen(), baseColor.getBlue(), 50));
                    g2d.setStroke(new BasicStroke(2.5f));
                    g2d.drawRoundRect(2, 2, c.getWidth() - 4, c.getHeight() - 4, BORDER_RADIUS, BORDER_RADIUS);
                }
                
                // Assurer que le texte est visible
                g2d.dispose();
                super.paint(g, c);
            }
        });
    }
    
    /**
     * Applique un style moderne à un champ de texte
     * @param textField Champ de texte à styliser
     */
    public static void styleTextField(JTextField textField) {
        textField.setFont(MAIN_FONT);
        textField.setBackground(CARD_COLOR);
        textField.setForeground(TEXT_COLOR);
        textField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 2, 0, PRIMARY_COLOR),
                BorderFactory.createEmptyBorder(SMALL_PADDING, SMALL_PADDING, SMALL_PADDING, SMALL_PADDING)
        ));
        textField.setCaretColor(PRIMARY_COLOR);
        
        // Ajouter un peu de padding
        textField.setMargin(new Insets(SMALL_PADDING, SMALL_PADDING, SMALL_PADDING, SMALL_PADDING));
    }
    
    /**
     * Applique un style moderne à un label
     * @param label Label à styliser
     */
    public static void styleLabel(JLabel label) {
        label.setFont(MAIN_FONT);
        label.setForeground(TEXT_COLOR);
    }
    
    /**
     * Applique un style moderne à un titre
     * @param label Label à styliser comme titre
     */
    public static void styleTitleLabel(JLabel label) {
        label.setFont(TITLE_FONT);
        label.setForeground(PRIMARY_COLOR);
    }
    
    /**
     * Applique un style moderne à un sous-titre
     * @param label Label à styliser comme sous-titre
     */
    public static void styleSubtitleLabel(JLabel label) {
        label.setFont(SUBTITLE_FONT);
        label.setForeground(TEXT_COLOR);
    }
    
    /**
     * Applique un style moderne à un panneau
     * @param panel Panneau à styliser
     */
    public static void stylePanel(JPanel panel) {
        panel.setBackground(BACKGROUND_COLOR);
        panel.setBorder(BorderFactory.createEmptyBorder(PADDING, PADDING, PADDING, PADDING));
    }
    
    /**
     * Applique un style moderne à une liste déroulante
     * @param comboBox Liste déroulante à styliser
     */
    public static void styleComboBox(JComboBox<?> comboBox) {
        comboBox.setFont(MAIN_FONT);
        comboBox.setBackground(CARD_COLOR);
        comboBox.setForeground(TEXT_COLOR);
        comboBox.setBorder(FIELD_BORDER);
    }
    
    /**
     * Applique un style moderne à une zone de texte
     * @param textArea Zone de texte à styliser
     */
    public static void styleTextArea(JTextArea textArea) {
        textArea.setFont(MAIN_FONT);
        textArea.setBackground(CARD_COLOR);
        textArea.setForeground(TEXT_COLOR);
        textArea.setBorder(FIELD_BORDER);
        textArea.setCaretColor(PRIMARY_COLOR);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
    }
    
    /**
     * Applique un style moderne à une case à cocher
     * @param checkBox Case à cocher à styliser
     */
    public static void styleCheckBox(JCheckBox checkBox) {
        checkBox.setFont(MAIN_FONT);
        checkBox.setForeground(TEXT_COLOR);
        checkBox.setBackground(BACKGROUND_COLOR);
        checkBox.setFocusPainted(false);
    }
    
    /**
     * Applique un style moderne à un tableau
     * @param table Tableau à styliser
     */
    public static void styleTable(JTable table) {
        table.setFont(MAIN_FONT);
        table.setForeground(TEXT_COLOR);
        table.setBackground(CARD_COLOR);
        table.setGridColor(BORDER_COLOR);
        table.setSelectionBackground(PRIMARY_DARK_COLOR);
        table.setSelectionForeground(TEXT_COLOR);
        table.setRowHeight(30);
        table.setIntercellSpacing(new Dimension(5, 5));
        table.setShowGrid(true);
        table.setFillsViewportHeight(true);
        
        // Style de l'en-tête
        JTableHeader header = table.getTableHeader();
        header.setFont(SUBTITLE_FONT);
        header.setForeground(TEXT_COLOR);
        header.setBackground(DARKER_BACKGROUND);
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, PRIMARY_COLOR));
        
        // Style des lignes alternées
        table.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                
                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? CARD_COLOR : new Color(58, 60, 78));
                    c.setForeground(TEXT_COLOR);
                } else {
                    c.setBackground(PRIMARY_DARK_COLOR);
                    c.setForeground(TEXT_COLOR);
                }
                
                setBorder(BorderFactory.createEmptyBorder(2, 5, 2, 5));
                return c;
            }
        });
    }
    
    /**
     * Applique un style moderne à un onglet
     * @param tabbedPane Panneau à onglets à styliser
     */
    public static void styleTabbedPane(JTabbedPane tabbedPane) {
        tabbedPane.setFont(MAIN_FONT);
        tabbedPane.setBackground(BACKGROUND_COLOR);
        tabbedPane.setForeground(TEXT_COLOR);
        tabbedPane.setBorder(BorderFactory.createEmptyBorder());
        
        // Style des onglets
        UIManager.put("TabbedPane.selected", PRIMARY_LIGHT_COLOR);
        UIManager.put("TabbedPane.contentAreaColor", BACKGROUND_COLOR);
        UIManager.put("TabbedPane.highlight", TEXT_COLOR);
        UIManager.put("TabbedPane.light", TEXT_COLOR);
        UIManager.put("TabbedPane.borderHightlightColor", TEXT_COLOR);
        UIManager.put("TabbedPane.focus", PRIMARY_COLOR);
        UIManager.put("TabbedPane.contentBorderInsets", new Insets(0, 0, 0, 0));
        UIManager.put("TabbedPane.selectedTabPadInsets", new Insets(0, 0, 0, 0));
    }
    
    /**
     * Applique un style moderne à une barre de menu
     * @param menuBar Barre de menu à styliser
     */
    public static void styleMenuBar(JMenuBar menuBar) {
        menuBar.setBackground(DARKER_BACKGROUND);
        menuBar.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, PRIMARY_DARK_COLOR));
        menuBar.setOpaque(true);
    }
    
    /**
     * Applique un style moderne à un menu
     * @param menu Menu à styliser
     */
    public static void styleMenu(JMenu menu) {
        menu.setFont(MAIN_FONT);
        menu.setForeground(TEXT_COLOR);
        menu.setBackground(DARKER_BACKGROUND);
        menu.setOpaque(true);
        menu.setBorderPainted(false);
        
        // Animation au survol
        menu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                menu.setForeground(PRIMARY_COLOR);
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                menu.setForeground(TEXT_COLOR);
            }
        });
    }
    
    /**
     * Applique un style moderne à un élément de menu
     * @param menuItem Élément de menu à styliser
     */
    public static void styleMenuItem(JMenuItem menuItem) {
        menuItem.setFont(MAIN_FONT);
        menuItem.setForeground(TEXT_COLOR);
        menuItem.setBackground(DARKER_BACKGROUND);
        menuItem.setOpaque(true);
        menuItem.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        
        // Animation au survol
        menuItem.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                menuItem.setBackground(CARD_COLOR);
                menuItem.setForeground(PRIMARY_COLOR);
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                menuItem.setBackground(DARKER_BACKGROUND);
                menuItem.setForeground(TEXT_COLOR);
            }
        });
    }
    
    // Méthode utilitaire pour éclaircir une couleur
    private static Color lighten(Color color, float amount) {
        int r = Math.min(255, (int)(color.getRed() * (1 + amount)));
        int g = Math.min(255, (int)(color.getGreen() * (1 + amount)));
        int b = Math.min(255, (int)(color.getBlue() * (1 + amount)));
        return new Color(r, g, b);
    }
    
    // Méthode utilitaire pour assombrir une couleur
    private static Color darken(Color color, float amount) {
        int r = Math.max(0, (int)(color.getRed() * (1 - amount)));
        int g = Math.max(0, (int)(color.getGreen() * (1 - amount)));
        int b = Math.max(0, (int)(color.getBlue() * (1 - amount)));
        return new Color(r, g, b);
    }
    
    // Classe pour créer des bordures arrondies
    static class RoundedBorder extends LineBorder {
        private int radius;
        
        RoundedBorder(int radius, Color color) {
            super(color);
            this.radius = radius;
        }
        
        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setColor(lineColor);
            g2d.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
            g2d.dispose();
        }
    }
}
