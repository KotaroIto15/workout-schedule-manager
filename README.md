# Workout Manager

## Manage your workout menu, schedule, and protein recipe!

### Who will use this app?
This is the best app for those:
- who wants to work out **regularly**
- who wants to **get big** by constantly changing menus and increasing the load
- who has lots of **protein powders**, and can't make up their mind to choose which one to drink each day

### What this application does:
- *Manage your daily workout schedule*
- *You can add menus when you think you want to do more*
- *You can change the sets, reps, or rest period for each menu if you want*
- *Also you can schedule which type of protein you want to drink on each day*

### Why is this project of interest to me?
*I recently started working out at the gym, and I personally found it difficult to manage workout menu every day. 
Therefore, I thought this app might help me work out constantly.*

### User Stories 
- *As a user, I want to be able to add a workout menu to the daily workout schedule*
- *As a user, I want to be able to change the sets, reps, or rest*
- *As a user, I want to be able to change the remaining amount of protein powder when I consume it*
- *As a user, I want to be able to see the schedule information, including workouts and protein powder.*
- *As a user, I want to be able to set today's choice of protein powder*
- *As a user, I want to be able to know if my workout plan is too much work when adding or changing workouts.*
- *As a user, I want to be able to save my daily workout schedule.*
- *As a user, I want to be able to reload the entire schedule from file when I log in.*
- *As a user, I want to be able to search a workout by name.*

### Phase 4: Task 2
- *As a user, I want to be able to add a workout menu to the daily workout schedule*
- *As a user, I want to be able to search a workout by name.*

**These two user stories, related to adding workouts to a schedule, are logged and printed out when the application closes.**

**Representative sample of the events that occur** <br>
Thu Mar 31 21:01:57 PDT 2022
Workout A is added.
Thu Mar 31 21:02:05 PDT 2022
Workout B is added.
Thu Mar 31 21:02:14 PDT 2022
Workout C is added.
Thu Mar 31 21:02:18 PDT 2022
Search workouts by name: B
Thu Mar 31 21:02:34 PDT 2022
Workout D is added.
Thu Mar 31 21:02:39 PDT 2022
Search workouts by name: D

### Phase 4: Task 3
*If I had more time, is there any refactoring that I would do to improve the design?*
- *There are several InputAreasUI, and they have similar behaviour, such as having a method to get an input as a String, or having fields with type of InputAreaUI. Thus, I would create an abstract class with fields and methods they have in common.*
- *With my current design, I have to update each panel separately when I make changes to my schedule. Therefore, I would introduce the observer design pattern so that the methods to make change to my schedule can also notify panels.*