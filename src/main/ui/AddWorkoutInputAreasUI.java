package ui;

import model.Schedule;

import javax.swing.*;

// Panel representing input areas used for adding a workout
public class AddWorkoutInputAreasUI extends JPanel {

    private InputAreaUI name;
    private InputAreaUI set;
    private InputAreaUI rep;
    private InputAreaUI rest;
    private JButton addButton;


    // EFFECTS: creates a panel with the input areas of each piece of information about hte workout.
    public AddWorkoutInputAreasUI(Action a) {
        super();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        name = new InputAreaUI("name");
        set  = new InputAreaUI("set");
        rep  = new InputAreaUI("rep");
        rest = new InputAreaUI("rest");

        addButton = new JButton(a);

        add(name);
        add(set);
        add(rep);
        add(rest);
        add(addButton);


    }

    // EFFECTS: returns the name of the workout the user puts.
    public String getName() {
        return name.getInput();
    }

    // EFFECTS: returns the set of the workout the user puts.
    public int getSet() {
        return Integer.parseInt(set.getInput());
    }

    // EFFECTS: returns the rep of the workout the user puts.
    public int getRep() {
        return Integer.parseInt(rep.getInput());
    }

    // EFFECTS: returns the rest period of the workout the user puts.
    public int getRest() {
        return Integer.parseInt(rest.getInput());
    }

}
