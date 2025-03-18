import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddParticipantDialog extends JDialog {
    private JTextField nameField;
    private JButton addButton;
    private JButton cancelButton;

    public AddParticipantDialog(Frame parent) {
        super(parent, "Ajouter un Participant", true);
        setLayout(new GridLayout(3, 2));

        add(new JLabel("Nom:"));
        nameField = new JTextField();
        add(nameField);

        addButton = new JButton("Ajouter");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Logique pour ajouter un participant
                String name = nameField.getText();
                // Appeler le DAO pour ajouter le participant
                // ParticipantDAO.add(new Participant(name));
                dispose();
            }
        });
        add(addButton);

        cancelButton = new JButton("Annuler");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        add(cancelButton);

        setSize(300, 150);
        setLocationRelativeTo(parent);
    }
}