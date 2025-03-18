import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddEventDialog extends JDialog {
    private JTextField eventNameField;
    private JTextField eventDateField;
    private JTextArea eventDescriptionArea;
    private JButton addButton;
    private JButton cancelButton;

    public AddEventDialog(Frame parent) {
        super(parent, "Ajouter un Événement", true);
        setLayout(new BorderLayout());

        // Panel for input fields
        JPanel inputPanel = new JPanel(new GridLayout(3, 2));
        inputPanel.add(new JLabel("Nom de l'Événement:"));
        eventNameField = new JTextField();
        inputPanel.add(eventNameField);

        inputPanel.add(new JLabel("Date de l'Événement:"));
        eventDateField = new JTextField();
        inputPanel.add(eventDateField);

        inputPanel.add(new JLabel("Description:"));
        eventDescriptionArea = new JTextArea(5, 20);
        inputPanel.add(new JScrollPane(eventDescriptionArea));

        add(inputPanel, BorderLayout.CENTER);

        // Panel for buttons
        JPanel buttonPanel = new JPanel();
        addButton = new JButton("Ajouter");
        cancelButton = new JButton("Annuler");

        buttonPanel.add(addButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Add action listeners
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Logic to add the event
                // This should call the EventController to handle the addition
                dispose(); // Close the dialog
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the dialog without adding
            }
        });

        setSize(300, 200);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
}