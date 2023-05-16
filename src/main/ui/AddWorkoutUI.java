package ui;

import model.Schedule;
import model.Workout;

import javax.swing.*;
import java.awt.*;
import java.util.List;

// Panel representing the input areas and the information of all workouts that the schedule contains.
public class AddWorkoutUI extends JPanel {

    private AddWorkoutInputAreasUI inputAreas;
    private AllWorkoutsUI allWorkouts;

    // EFFECTS: creates a panel with the input areas used for adding a workout,
    //          and the information of all workouts contained in the schedule.
    public AddWorkoutUI(Action add, Action search, Schedule sc) {
        super();
        setPreferredSize(new Dimension(50, 50));
        setLayout(new FlowLayout());

        inputAreas = new AddWorkoutInputAreasUI(add);
        add(inputAreas);

        List<Workout> menus = sc.getMenus();
        allWorkouts = new AllWorkoutsUI(search, menus);
        add(allWorkouts);

    }

    public AddWorkoutInputAreasUI getInputAreas() {
        return inputAreas;
    }

    public AllWorkoutsUI getAllWorkouts() {
        return allWorkouts;
    }

}
