package ui;

import model.Workout;

import javax.swing.*;
import java.awt.*;
import java.util.List;

// Panel representing the workout statistics
public class WorkoutStatisticsUI extends JPanel {

    private GraphUI graph;
    private JLabel description;

    // EFFECTS: creates a panel with the graph, and description that explains what the graph represents.
    public WorkoutStatisticsUI(List<Workout> workouts) {
        super();
        setLayout(new BorderLayout());
        graph = new GraphUI(workouts);
        description = getDescription();
        add(description,BorderLayout.PAGE_START);
        add(graph, BorderLayout.CENTER);
    }

    // EFFECTS: creates a JLabel with the description of the graph.
    public JLabel getDescription() {
        String description = "* This graph shows the intensities of each workout you added. The intensity is derived "
                + "by" + "multiplying the set count by reps. If your total intensity exceeds 230, then your workout "
                + "Schedule would be considered as 'Over work'.";

        return new JLabel(description);
    }
}
