package ui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import model.Event;
import model.EventLog;
import model.Protein;
import model.Schedule;
import model.Workout;
import persistence.JsonReader;
import persistence.JsonWriter;

// Graphical interface of the workout manager application
public class ManagerApp extends JFrame {
    // some data fields and constants are retrieved from JsonSerializationDemo.java
    private static final String JSON_STORE = "./data/schedule.json";
    private Schedule schedule;
    private Scanner input;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    private CardLayout layout;
    private JPanel cardPanel;
    private WelcomeUI home;
    private AddWorkoutUI addWorkoutUI;
    private AddProteinUI addProteinUI;
    private WorkoutStatisticsUI statisticsUI;

    // < code partially retrieved from JsonSerializationDemo.java >
    // EFFECTS: runs application
    //          throws FileNotFoundException if the file is not found.
    public ManagerApp() throws FileNotFoundException {
        super("Workout Planner");
        input = new Scanner(System.in);
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        init();
    }

    public void initializeGraphics() {
        layout = new CardLayout();
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Container contentPane = getContentPane();
        contentPane.setBackground(Color.WHITE);
        contentPane.setLayout(new BorderLayout());

        cardPanel = createCardPanel();
        contentPane.add(cardPanel, BorderLayout.CENTER);
        contentPane.add(createButtonPanel(), BorderLayout.PAGE_END);

        Runtime.getRuntime().addShutdownHook(new Shutdown());
    }

    public JPanel createCardPanel() {
        JPanel cardPanel = new JPanel(layout);

        WelcomeUI welcomePanel = new WelcomeUI();
        AddWorkoutUI addWorkoutPanel = new AddWorkoutUI(new AddWorkoutAction(), new SearchWorkoutAction(), schedule);
        AddProteinUI addProteinPanel = new AddProteinUI(new AddProteinAction(), schedule);
        WorkoutStatisticsUI workoutStatisticsUI = new WorkoutStatisticsUI(schedule.getMenus());

        home = welcomePanel;
        addWorkoutUI = addWorkoutPanel;
        addProteinUI = addProteinPanel;
        statisticsUI = workoutStatisticsUI;

        cardPanel.add(home, "Home");
        cardPanel.add(addWorkoutUI, "Add Workout Menu");
        cardPanel.add(addProteinUI, "Add Protein");
        cardPanel.add(statisticsUI, "Show Your Workout Statistics");

        return cardPanel;
    }

    public JPanel createButtonPanel() {
        ButtonUI buttonPanel = new ButtonUI();

        buttonPanel.addButton("Home", new PageChangeAction("Home"));
        buttonPanel.addButton("Add Workout Menu", new PageChangeAction("Add Workout"));
        buttonPanel.addButton("Add Protein", new PageChangeAction("Add Protein"));
        buttonPanel.addButton("Show Your Workout Statistics", new PageChangeAction("Show Your Workout Statistics"));
        buttonPanel.addButton("Save", new SaveAction());
        buttonPanel.addButton("Load", new LoadAction());
        buttonPanel.addButton("Quit", new QuitAction());

        return buttonPanel;
    }

// Unused codes are commented out

//    // <some parts of this method was retrieved from TellerApp.java>
//    // REQUIRES: command has to be in interval [0, 5]
//    // MODIFIES: this
//    // EFFECTS: processes user input. If the user enters 0, then the program produces the message "Thank you".
//    //          and stops. Otherwise, if the command is in the correct interval, it transfers to the proper method
//    //          based on the command.
//    private void runManagerApp() {
//        //boolean completed = false;
//
//        init();
//
//        while (!completed) {
//            display();
//            int command = input.nextInt();
//
//            if (command == 0) {
//                completed = true;
//            } else {
//                processCommand(command);
//            }
//        }
//
//        System.out.println("Thank you.");
//    }

//    // <some parts of this method was retrieved from TellerApp.java>
//    // MODIFIES: this
//    // EFFECTS: processes user command, and transfer to the proper method based on the command
//    private void processCommand(int command) {
//        switch (command) {
//            case (1):
//                addWorkout();
//                break;
//            case (2):
//                changeWorkoutInfo();
//                break;
//            case (3):
//                viewSchedule();
//                break;
//            case (4):
//                addProtein();
//                break;
//            case (5):
//                consumeProtein();
//                break;
//            case (6):
//                trySaving();
//                break;
//            case (7):
//                loadSchedule();
//                break;
//        }
//    }
//
//    // <some parts of this method was retrieved from TellerApp.java>
//    // EFFECTS: displays menu of options to user
//    private void display() {
//        System.out.println("To quit, select 0");
//        System.out.println("To add a workout menu, select 1");
//        System.out.println("If you have added a workout and want to change the setting of the workout, select 2");
//        System.out.println("To see today's schedule, select 3");
//        System.out.println("To add the information of protein powder, select 4");
//        System.out.println("To consume the protein, select 5");
//        System.out.println("To save your schedule, select 6");
//        System.out.println("To load your saved schedule, select 7\n");
//    }

    // <some parts of this method was retrieved from TellerApp.java>
    // MODIFIES: this
    // EFFECTS: initializes the daily schedule, with the same set to "Today's schedule"
    //          then initializes the graphics
    private void init() {
        schedule = new Schedule("Today's schedule");
//        input = new Scanner(System.in);
//        input.useDelimiter("\n");
        initializeGraphics();
    }

    // REQUIRES: when prompting the user to enter the information about the workout,
    //           name has to be a non-empty String, and set, rep, and rest has to be integers that are >=0.
    // MODIFIES: this
    // EFFECTS: adds workout menu to the list of workouts of a daily schedule,
    //          and checks if adding the menu results in too much work
    //          if so, pop-up message asks if the user wants to cancel the change.
    //                 based on the response, either cancel the change or add the workout.
    //          update the graphical components that is based on the information about the workouts,
    //          and produces the pop-up message telling that the workout is successfully added.
    private void addWorkout() {

        AddWorkoutInputAreasUI inputAreasUI = addWorkoutUI.getInputAreas();

        String name = inputAreasUI.getName();
        int set = inputAreasUI.getSet();
        int rep = inputAreasUI.getRep();
        int rest = inputAreasUI.getRest();

        schedule.addMenu(new Workout(name, set, rep, rest));

        if (schedule.isTooMuchWork()) {
            int choice = JOptionPane.showConfirmDialog(addWorkoutUI,
                    "Adding this workout might lead to overwork. Do you still want to add it?");

            if (choice == 0) {
                updateWorkoutUI();
                updateStatisticsUI();
                JOptionPane.showMessageDialog(addWorkoutUI, "Added!");
            } else {
                cancelAdding();
            }

        } else {
            updateWorkoutUI();
            updateStatisticsUI();
            JOptionPane.showMessageDialog(addWorkoutUI, "Added!");
        }
    }

    // MODIFIES: this
    // EFFECTS: cancel the addition of the workout, and shows the pop-up message telling that the change was cancelled.
    private void cancelAdding() {

        ArrayList<Workout> menus = schedule.getMenus();
        menus.remove(menus.size() - 1);
        JOptionPane.showMessageDialog(addWorkoutUI,"This change was cancelled.\n");

    }

    // REQUIRES: index should be in interval [1, menus.size()], where menus is schedule.getMenus()
    //           and selection is in interval [1, 3]
    // MODIFIES: this
    // EFFECTS: changes the information of a workout in the schedule, such as set, rep, and rest
    //          user will first select which workout to change,
    //          and choose which information to change, by pressing 1,2 or 3
    //          then transfers to the proper methods based on the input
    private void changeWorkoutInfo() {
        System.out.println("Please select the workout that you want to modify the information");
        listWorkoutNames(); // list all the names of workouts, numbered from 1
        int index = input.nextInt();
        Workout workout = schedule.selectWorkout(index - 1);
        // subtract 1 from index here, because the ArrayList starts from index 0

        System.out.println("Which information do you want to change?");
        System.out.println("If you want to modify its sets, select 1");
        System.out.println("If you want to modify its reps, select 2");
        System.out.println("If you want to modify its rest period, select 3");

        int selection = input.nextInt();
        switch (selection) {
            case (1):
                changeWorkoutSet(workout);
                break;
            case (2):
                changeWorkoutRep(workout);
                break;
            case (3):
                changeWorkoutRest(workout);
                break;
        }
    }


    // EFFECTS: shows the numbered list of names of workouts in the list, starting from 1
    //          ex) 1. Name
    //              2. Name
    //              3. Name
    private void listWorkoutNames() {
    	
    	Iterator<Workout> it = schedule.iterator();
    	int i = 1;
    	
    	while (it.hasNext()) {
    		Workout w = it.next();
    		String name = w.getName();
    		System.out.println(i + ", " + name);
    		i++;
    	}
    }

    // REQUIRES: newSet is a non-negative integer (newSet >= 0)
    // MODIFIES: this
    // EFFECTS: change the set count of the selected workout in the list to the entered new sets
    //          then check if the change results in too much work
    private void changeWorkoutSet(Workout workout) {
        int set = workout.getSet();
        System.out.print("Please enter new sets: ");
        int newSet = input.nextInt();

        workout.changeSet(newSet);
        confirmChange(workout, set, 0);
    }

    // REQUIRES: newRep is a non-negative integer (newRep >= 0)
    // MODIFIES: this
    // EFFECTS: changes the rep of the selected workout in the list to the entered new repetitions
    //          then check if the change results in too much work
    private void changeWorkoutRep(Workout workout) {
        int rep = workout.getRep();
        System.out.print("Please enter new reps: ");
        int newRep = input.nextInt();

        workout.changeRep(newRep);
        confirmChange(workout, rep, 1);
    }

    // REQUIRES: setOrRep should be either 0 or 1, and choice should also be either 0 or 1
    //           we can assume that original >= 0, which is the valid value.
    // MODIFIES: this
    // EFFECTS: is called when the user modifies the information about the workout in the list.
    //          checks whether changing the information about the workout results in overwork,
    //          and if so, tells the user that changing the information leads to overwork, and asks if the user still
    //          want to make change.
    //          If the user answers Yes(1), then it lets the change happen.
    //          If the user answers No(0), then it undoes the modification of sets if setOrRep is 0,
    //          or undoes the modification of reps if setOrRep is 1.
    //
    //          when the change does not result in overwork, tells the user that the change was made successfully.
    private void confirmChange(Workout workout, int original, int setOrRep) {
        if (schedule.isTooMuchWork()) {
            System.out.println("This change might lead to overwork. Do you still want to make the change?");
            System.out.println("Yes - press 1      No - press 0");
            int choice = input.nextInt();

            if (choice == 0) {
                undo(workout, original, setOrRep);
                System.out.println("This change was cancelled.\n");
            } else {
                System.out.println("Change was made successfully\n");
            }
        } else {
            System.out.println("Change was made successfully\n");
        }
    }

    // REQUIRES: setOrRep should either be 0 or 1.
    //           we can assume that original >= 0, which is the valid value.
    // MODIFIES: this
    // EFFECTS: if setOrRep is 0, then it changes the set value back to its original value.
    //          if setOrRep is 1, then it changes back the rep value to its original value.
    private void undo(Workout workout, int original, int setOrRep) {
        if (setOrRep == 0) {
            workout.changeSet(original);
        } else {
            workout.changeRep(original);
        }
    }

    // REQUIRES: newRest has to be a non-negative integer (newRest >= 0)
    // MODIFIES: this
    // EFFECTS: changes the rest period of the selected workout in the list with the newRest entered from console
    //          input
    private void changeWorkoutRest(Workout workout) {
        System.out.print("Please enter new rest period: ");
        int newRest = input.nextInt();

        workout.changeRest(newRest);

        System.out.println("Change was made successfully.\n");
    }

// Unused codes are commented out
//    // EFFECTS: shows the list of the information of schedule
//    //          displays the name, set, rep and rest periods of each workout
//    //         & flavor and remaining amount of the protein powder
//    private void viewSchedule() {
//        ArrayList<Workout> workouts = schedule.getMenus();
//        System.out.println("<<" + schedule.getName() + ">>\n");
//        System.out.println("<Workout>");
//        for (Workout w: workouts) {
//            String name = w.getName();
//            int set = w.getSet();
//            int rep = w.getRep();
//            int rest = w.getRest();
//
//            System.out.println("Name: " + name + ", Set: " + set + ", Rep: " + rep + ", Rest: " + rest + " seconds");
//        }
//        Protein protein = schedule.getProtein();
//        System.out.println("\n<Protein>");
//        if (schedule.isProteinInitialState()) {
//            System.out.println("has not been added yet\n");
//        } else {
//            String flavor = protein.getFlavor();
//            int amount = protein.getRemainingAmount();
//            System.out.println("Flavor: " + flavor + ", Remaining amount: " + amount + "g");
//            System.out.println("\n");
//        }
//    }

    // REQUIRES: flavor has to be String, and amount has to be a non-negative integer (amount >= 0)
    // MODIFIES: this
    // EFFECTS: prompts the user to enter the information about the protein powder to consume today.
    //          based on the information given, create a new Protein object and set it to the daily schedule.
    //          update the graphical components that are based on the information about the protein,
    //          and produces the pop-up message telling that the protein was successfully added.
    private void addProtein() {
        ProteinInputAreasUI inputAreasUI = addProteinUI.getInputAreas();

        String flavor = inputAreasUI.getFlavor();
        int amount  = inputAreasUI.getRemainingAmount();

        Protein p = new Protein(flavor, amount);
        schedule.setProtein(p);
        updateProteinUI();

        JOptionPane.showMessageDialog(addProteinUI, "Added!");
    }

    // REQUIRES: 0 <= consumedAmount <= remaining amount of the protein powder
    // MODIFIES: this
    // EFFECTS: consumes protein, and subtract the amount of consumption from the remaining amount of protein
    //          then displays the consumed amount and the remaining amount
    private void consumeProtein() {
        Protein protein = schedule.getProtein();

        System.out.print("how many grams did you consume?: ");
        int consumedAmount = input.nextInt();

        protein.consume(consumedAmount);
        int amount = protein.getRemainingAmount();
        System.out.println("You just consumed " + consumedAmount + " g.");
        System.out.println("Now the remaining amount is " + amount + " g.\n");
    }

    // EFFECTS: check if both of workout menu and protein are added.
    //          if they are added, proceed to save schedule.
    //          if not, shows the pop-up message asking the user if they want to save anyway.
    //          if the user answers "yes", then proceed to save schedule.
    private void trySaving() {
        if (schedule.getMenus().isEmpty() || schedule.isProteinInitialState()) {
            int choice = JOptionPane.showConfirmDialog(home, "You haven't added either workout or protein."
                    + "Do you still want to save your schedule?");

            switch (choice) {
                case 0:
                    saveSchedule();
                default:
                    break;
            }
        } else {
            saveSchedule();
        }
    }

    // <code retrieved from JsonSerializationDemo.java>
    // EFFECTS: if file is not found, shows the message telling that it's unable to write to the file.
    //          otherwise, saves the schedule to file.
    //          then shows the popup message telling that the schedule is saved.
    private void saveSchedule() {

        try {
            jsonWriter.open();
            jsonWriter.write(schedule);
            jsonWriter.close();
            JOptionPane.showMessageDialog(home,"Saved " + schedule.getName() + " to " + JSON_STORE + "\n");
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(home, "Unable to write to file: " + JSON_STORE + "\n");
        }
    }

    // MODIFIES: this
    // EFFECTS: load the information about the workout schedule from the JSON file.
    //          update all the graphical components that depend on the information about the schedule,
    //          then shows the popup message telling that the file was successfully loaded.
    private void loadSchedule() {
        try {
            schedule = jsonReader.read();

            updateWorkoutUI();
            updateProteinUI();
            updateStatisticsUI();

            JOptionPane.showMessageDialog(home,"Loaded " + schedule.getName() + " from " + JSON_STORE + "\n");

        } catch (IOException e) {
            JOptionPane.showMessageDialog(home,"Unable to read from file: " + JSON_STORE + "\n");
        }
    }

    // MODIFIES: this
    // EFFECTS: updates the addWorkoutUI
    private void updateWorkoutUI() {
        cardPanel.remove(addWorkoutUI);
        addWorkoutUI = new AddWorkoutUI(new AddWorkoutAction(), new SearchWorkoutAction(), schedule);
        cardPanel.add(addWorkoutUI, "Add Workout Menu");
    }

    // MODIFIES: this
    // EFFECTS: updates the addProteinUI
    private void updateProteinUI() {
        cardPanel.remove(addProteinUI);
        addProteinUI = new AddProteinUI(new AddProteinAction(), schedule);
        cardPanel.add(addProteinUI, "Add Protein");
    }

    // MODIFIES: this
    // EFFECTS: updates the statisticsUI
    private void updateStatisticsUI() {
        cardPanel.remove(statisticsUI);
        statisticsUI = new WorkoutStatisticsUI(schedule.getMenus());
        cardPanel.add(statisticsUI, "Show Your Workout Statistics");
    }


    // Represents an action to jump to a different Panel.
    private class PageChangeAction extends AbstractAction {

        // EFFECTS: constructs an action with the given action name;
        public PageChangeAction(String msg) {
            super(msg);
        }

        // EFFECTS: jumps to a Panel with the associated name.
        @Override
        public void actionPerformed(ActionEvent e) {
            String cmd = e.getActionCommand();
            layout.show(cardPanel, cmd);
        }
    }

    // Represents an action to add a workout when the button is pressed.
    private class AddWorkoutAction extends AbstractAction {

        // EFFECTS: constructs an action with the given action name;
        public AddWorkoutAction() {
            super("Add");
        }

        // MODIFIES: this
        // EFFECTS: adds a workout to the schedule when "Add workout" button is pressed.
        @Override
        public void actionPerformed(ActionEvent e) {
            addWorkout();
        }
    }

    // Represents an action to add a protein when the button is pressed.
    private class AddProteinAction extends AbstractAction {

        // EFFECTS: constructs an action with the given action name;
        public AddProteinAction() {
            super("Add Protein");
        }

        // MODIFIES: this
        // EFFECTS: adds a protein to the schedule when "Add Protein" button is pressed.
        @Override
        public void actionPerformed(ActionEvent act) {
            addProtein();
        }
    }

    // Represents an action to save the entire state of the schedule when the button is pressed.
    private class SaveAction extends AbstractAction {

        // EFFECTS: constructs an action with the given action name;
        public SaveAction() {
            super("Save");
        }

        // <code retrieved from JsonSerializationDemo.java>
        // EFFECTS: saves the schedule to file when "Save" button is pressed
        @Override
        public void actionPerformed(ActionEvent act) {
            trySaving();
        }
    }

    // Represents an action to load the entire state of the schedule from the file
    private class LoadAction extends AbstractAction {

        // EFFECTS: constructs an action with the given action name;
        public LoadAction() {
            super("Load");
        }

        // <code retrieved from JsonSerializationDemo.java>
        // MODIFIES: this
        // EFFECTS: loads schedule from file when "Load" button is pressed.
        @Override
        public void actionPerformed(ActionEvent act) {
            loadSchedule();
        }
    }

    // Represents an action to search for a workout by name
    private class SearchWorkoutAction extends AbstractAction {

        // EFFECTS: constructs an action with the given action name;
        public SearchWorkoutAction() {
            super("Search Workout By Name");
        }

        // EFFECTS: searches for a workout with the entered name, and show a popup message
        //          listing all workouts that match with the name.
        @Override
        public void actionPerformed(ActionEvent e) {
            AllWorkoutsUI allWorkouts = addWorkoutUI.getAllWorkouts();
            String name = allWorkouts.getInputArea().getName();

            List<Workout> workoutList = schedule.searchWorkout(name);

            if (workoutList.isEmpty()) {
                JOptionPane.showMessageDialog(addWorkoutUI, "Name " + name + " was not found.");
            } else {
                String result = "";
                for (Workout w : workoutList) {
                    result += "Name: " + w.getName() + ", Set: " + w.getSet() + ", Rep: " + w.getRep() + ", Rest: "
                            + w.getRest() + " seconds\n";
                }

                JOptionPane.showMessageDialog(addWorkoutUI, result);
            }
        }
    }

    // Represents an action to quit the application.
    private class QuitAction extends AbstractAction {
        public QuitAction() {
            super("Quit Application");
        }

        // EFFECTS: prints out the event logs onto the console, then closes the application.
        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }

    // Class controlling the behaviour when the application closes.
    private class Shutdown extends Thread {

        // EFFECTS: prints out the event logs onto the console.
        public void run() {
            for (Event event : EventLog.getInstance()) {
                System.out.println(event.toString());
            }
        }
    }
}
