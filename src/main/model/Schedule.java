package model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import persistence.Writable;

// Represents a schedule, having a list of workouts and protein powder of the day
public class Schedule implements Writable, Iterable<Workout> {

    private static final int MAX_INTENSITY = 230;

    private String name;
    private ArrayList<Workout> menus; // list of workout menus
    private Protein protein;          // protein powder to drink on the day


    // EFFECTS: creates a new Schedule object with an empty list of workout menus
    //          and protein powder to drink with an empty flavor name, and remaining amount set to be 0
    public Schedule(String name) {
        this.name = name;
        this.menus = new ArrayList<>();
        this.protein = new Protein("", 0);
    }


    // MODIFIES: this
    // EFFECTS: given a workout menu, add it to the workout list of the daily schedule
    public void addMenu(Workout menu) {
        menus.add(menu);
        EventLog.getInstance().logEvent(new Event("Workout " + menu.getName() + " is added."));
    }

    // REQUIRES: amount >= 0
    // MODIFIES: this
    // EFFECTS: given a protein object, set it to the daily workout schedule
    public void setProtein(Protein protein) {
        this.protein = protein;
    }

    // EFFECTS: returns true if the protein has not been added yet (= is in initial state)
    public boolean isProteinInitialState() {
        Protein protein = getProtein();
        return protein.getFlavor().equals("") && protein.getRemainingAmount() == 0;
    }

    // EFFECTS: determines if the schedule is too hard to achieve
    //          intensity is derived by set * rep
    //          produces true if total intensity of all workouts in the list is
    //          more than MAX_INTENSITY
    public boolean isTooMuchWork() {
        int totalIntensity = 0;

        for (Workout menu: menus) {
            totalIntensity += menu.getSet() * menu.getRep();
        }

        if (totalIntensity >= MAX_INTENSITY) {
            return true;
        }

        return false;
    }

    // EFFECTS: searches for the workouts with the given name from the list of workouts,
    //          and returns them.
    public List<Workout> searchWorkout(String name) {
        List<Workout> workoutsWithGivenName = new ArrayList<>();
        
        Iterator<Workout> it = this.iterator();
        while(it.hasNext()) {
        	Workout w = it.next();
        	if (w.getName().equals(name)) {
        		workoutsWithGivenName.add(w);
        	}
        }

        EventLog.getInstance().logEvent(new Event("Search workouts by name: " + name));
        return workoutsWithGivenName;
    }

    // REQUIRES: i should be in interval [0, menus.size()-1]
    // EFFECTS: given the index, select the workout from the list of workouts
    public Workout selectWorkout(int i) {
        return menus.get(i);
    }

    public String getName() {
        return name;
    }

    // EFFECTS: returns the list of the workout menus
    public ArrayList<Workout> getMenus() {
        return menus;
    }

    // EFFECTS: returns the today's choice of protein powder
    public Protein getProtein() {
        return protein;
    }

    // <code retrieved from JsonSerializationDemo.java>
    // EFFECTS: returns this schedule as a JsonObject
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("menus", menusToJson());
        json.put("protein", protein.toJson());
        return json;
    }

    // <code retrieved from JsonSerializationDemo.java>
    // EFFECTS: returns menus in this schedule as a JSON array
    private JSONArray menusToJson() {
        JSONArray jsonArray = new JSONArray();
        
        Iterator<Workout> it = iterator();
        while (it.hasNext()) {
        	Workout w = it.next();
        	jsonArray.put(w.toJson());
        }

        return jsonArray;
    }


	@Override
	public Iterator<Workout> iterator() {
		return new ScheduleIterator(this);
	}
	
	private class ScheduleIterator implements Iterator<Workout> {
		
		private Schedule sc;
		private int index;
		
		public ScheduleIterator(Schedule sc) {
			this.sc = sc;
			this.index = 0;
		}

		@Override
		public boolean hasNext() {
			if (index < sc.getMenus().size()) {
				return true;
			} else {
				return false;
			}
		}

		@Override
		public Workout next() {
			Workout w = sc.getMenus().get(index);
			index++;
			return w;
		}

	}

}
