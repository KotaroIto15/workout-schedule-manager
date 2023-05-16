package ui;

import javax.swing.*;
import java.awt.*;

// Panel representing an input area.
public class InputAreaUI extends JPanel {

    private JLabel label;
    private JTextField input;

    // EFFECTS: creates a panel with the given name of the input field, and the input field itself.
    public InputAreaUI(String l) {
        super();
        setLayout(new FlowLayout());
        label = new JLabel(l + ":");
        input = new JTextField(10);
        add(label);
        add(input);
    }

    // EFFECTS: get the information the user puts into the text fields.
    public String getInput() {
        return input.getText();
    }
}
