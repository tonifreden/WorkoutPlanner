# Workout Planner
## Final project for Haaga-Helia backend/server programming course

The application is live at [https://project-workoutplanner.herokuapp.com/]

This simple diary-like application lets users plan and keep track of their workouts, helping them to achieve greater results faster.

### Login
- Test **admin**, password "admin"
- Test **user**, password "user"
- Users can sign up and create their own private account with which to use the application

### Basic usage
- User first creates a *workout*, defining:
  - **Date** on which the workout was - or will be - done
  - Workout **routine** - essentially a so-called "name" of the workout, e.g. "Chest" or "Circuit training"
  - **Location** - this is mostly for personal reference, if a user wants to keep track of where each workout takes/took place
- User can then add *exercises* to said workout, defining *for each exercise*:
  - **Name** of exercise, e.g. "Bench press"
  - **Target weights** with which the exercise was or will be carried out
  - **Target sets**, i.e. how many sets of the exercise was or will be carried out with the given weights
  - **Target reps** per each set, i.e. how many repetitions was or will be done during each set
- Finally, after adding an *exercise* and its targets, user can save post-workout *results* and *comments*:
  - **Result reps** in text form - if target sets was 3 and target reps for each set was 8, results could be "8, 8, 7", meaning that the last set fell one short of the intended target
  - **Comments** on exercise, e.g. "Too easy, add more weights next time" or "Poor form, concentrate on proper technique with smaller weights"

### Features
- Workout Planner is private, meaning that users only have access to their own workouts and exercises, and their respective functionalities
- For easier chronological tracking, user's Workout list is by default sorted by the date on which a workout takes/took place
- Users can view, add, edit and delete their workouts
- Users can add/remove exercises to/from their workouts
- Users can save post-workout results and comments to each exercise
- Admin(s) can view all users in a list and delete them, if they misbehave