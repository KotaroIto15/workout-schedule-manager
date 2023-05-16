package ui;

import javax.swing.*;

// Panel that contains buttons representing each menu of the application.
public class ButtonUI extends JPanel {

    // EFFECTS: creates a JPanel
    public ButtonUI() {
        super();
    }

    // MODIFIES: this
    // EFFECTS: creates a new button with the given message and action, then adds it to the panel
    public void addButton(String msg, AbstractAction action) {
        JButton btn = new JButton(msg);
        btn.setAction(action);
        btn.setActionCommand(msg);

        add(btn);
    }
}
