package ui;

import model.Workout;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

// Panel representing the graph of the intensities of each workout.
public class GraphUI extends JPanel {

    private List<Integer> workoutIntensities;
    private List<String> workoutNames;

    // EFFECTS: creates a panel, and set the fields based on the given list of workouts.
    //          creates new lists of Integer and String, and assign them to the fields.
    public GraphUI(List<Workout> workouts) {
        super();
        setLayout(null);
        workoutIntensities = getWorkoutIntensities(workouts);
        workoutNames = getWorkoutNames(workouts);
    }

    // EFFECTS: from the given list of workouts, computes the intensity of each workout and add it to the list.
    //          Adds the total intensity of all workouts at the end of the list, and returns it.
    public List<Integer> getWorkoutIntensities(List<Workout> workouts) {

        List<Integer> intensities = new ArrayList<>();
        int total = 0;

        for (Workout w : workouts) {
            int set = w.getSet();
            int rep = w.getRep();

            intensities.add(set * rep);
            total += set * rep;
        }

        intensities.add(total);

        return intensities;
    }

    // EFFECTS: from the given list of workouts, takes the names of each workout and add it to the new list.
    //          Adds the "Total Intensity" at the end of the list, and returns it.
    public List<String> getWorkoutNames(List<Workout> workouts) {
        List<String> names = new ArrayList<>();

        for (Workout w : workouts) {
            names.add(w.getName());
        }

        names.add("Total Intensity");

        return names;
    }

    // MODIFIES: this
    // EFFECTS: creates a bar chart at the center of the panel, each bar representing the intensity of each workout.
    public void paintComponent(Graphics myg) {
        super.paintComponent(myg);

        int pw = getWidth();
        int ph = getHeight();
        int yzero = ph - 50;
        int left = 50;
        int top = 20;
        int right = 20;
        int dx = (pw - left - right) / getDataSize();
        myg.setColor(new Color(0,0,0));
        myg.drawLine(left, yzero,left + dx * getDataSize(), yzero);
        myg.drawLine(left, yzero, left, top);
        myg.setColor(new Color(128,128,128));

        for (Integer data : workoutIntensities) {
            int x = (int)(left + dx * workoutIntensities.indexOf(data) + (dx * 0.8) / 2);
            if (data * 3 >= 690) {
                myg.setColor(Color.RED);
                myg.fillRect(x, yzero - (data * 3), (int)(dx * 0.2), data * 3);
                myg.setColor(new Color(128,128,128));
            } else {
                myg.fillRect(x, yzero - (data * 3), (int)(dx * 0.2), data * 3);
            }

        }
        paintLabels(pw,ph);

    }

    // MODIFIES: this
    // EFFECTS: places labels of each workout under the associated bar.
    public void paintLabels(int pw, int ph) {
        int yzero = ph - 40;
        int left = 50;
        int right = 20;
        int dx = (pw - left - right) / getDataSize();

        for (String name : workoutNames) {
            int x = (int)(left + dx * workoutNames.indexOf(name) + dx * 0.45);
            int y = yzero;

            JLabel nameLabel = new JLabel(name);
            Dimension d = nameLabel.getPreferredSize();

            nameLabel.setBounds(x, y, d.width, d.height);
            add(nameLabel);
        }
    }

    // EFFECTS: returns the number of the elements contained in the list of workout intensities,
    //          or returns 1 if the list is empty.
    public int getDataSize() {
        if (workoutIntensities.isEmpty()) {
            return 1;
        } else {
            return workoutIntensities.size();
        }
    }
}
