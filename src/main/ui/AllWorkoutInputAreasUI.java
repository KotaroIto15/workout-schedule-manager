package ui;

import javax.swing.*;
import java.awt.*;

// Panel representing the input areas used for searching workouts by name.
public class AllWorkoutInputAreasUI extends JPanel {

    private InputAreaUI name;
    private JButton searchButton;

    // EFFECTS: creates a panel with the input areas used for searching workouts by name.
    public AllWorkoutInputAreasUI(Action act) {
        super();
        setLayout(new FlowLayout());
        name = new InputAreaUI("name");
        add(name);

        searchButton = new JButton(act);
        add(searchButton);
    }

    // EFFECTS: returns the name of the workout the user puts.
    public String getName() {
        return name.getInput();
    }
}
