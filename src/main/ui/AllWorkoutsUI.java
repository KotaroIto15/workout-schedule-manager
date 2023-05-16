package ui;

import java.awt.Dimension;
import java.util.List;

import javax.swing.Action;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Workout;

// Panel representing the information of all workouts contained in the schedule.
public class AllWorkoutsUI extends JPanel {

    private AllWorkoutInputAreasUI inputArea;

    // EFFECTS: creates a panel with the input areas used for searching a workout by name,
    //          and information about all workouts contained in the schedule.
    public AllWorkoutsUI(Action a, List<Workout> menus) {
        super();
        setPreferredSize(new Dimension(600, 600));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        addAllWorkouts(menus);

        inputArea = new AllWorkoutInputAreasUI(a);
        add(inputArea);
    }

    // MODIFIES: this
    // EFFECTS: adds the information of all workouts to the panel.
    public void addAllWorkouts(List<Workout> menus) {

        for (Workout w : menus) {
            addWorkout(w);
        }
    }

    // MODIFIES: this
    // EFFECTS: adds the information of the given workout to the panel.
    public void addWorkout(Workout w) {
        String info = "Name: " + w.getName() + ", Set: " + w.getSet() + ", Rep: " + w.getRep() + ", Rest: "
                + w.getRest() + " seconds";
        add(new JLabel(info));
    }

    public AllWorkoutInputAreasUI getInputArea() {
        return inputArea;
    }




}
