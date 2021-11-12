package com.WorkoutPlanner.domain;

// import java.util.ArrayList;
// import java.util.List;

// import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Exercise {
    // TODO: validatorit exerciseihin?
    @Id
    @GeneratedValue(strategy =  GenerationType.AUTO)
    private Long exerciseId;
    private String name;
    private String targetWeight; // e.g. "60kg"
    private Integer targetSets;
    private Integer targetReps; // i.e. reps per set

    /**
     * This is commented out for future improvement.
     * Initially I tried to make resultReps an ElementCollection List, so as to not having to make it an Entity
     * and create a table for DB. But, alas, I wasn't successful, so it will be a String for now.
     */
    // @ElementCollection
    // private List<Integer> resultReps = new ArrayList<Integer>(targetSets != null ? targetSets.intValue() : 0);
    
    /** 
     * This will only be String for now. Not the best solution for storing results,
     * I will hopefully have time to develop this further.
    */
    private String resultReps;
    // results for each set, e.g. if target sets is 3 and target reps is 8, result reps could be 8, 8, 7, so the last set fell one short of targeted rep amount
    // intended/example input form with commas and spaces: "8, 8, 8"
    
    private String comments; // Field for comments on exercise, e.g. "poor form" or "too easy, add more weights next time"

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "workoutid")
    private Workout workout;

    public Exercise() {

    }

    public Exercise(String name, String targetWeight, Integer targetSets, Integer targetReps, String comments, Workout workout) {
        super();
        this.name = name;
        this.targetWeight = targetWeight;
        this.targetSets = targetSets;
        this.targetReps = targetReps;
        this.comments = comments;
        this.workout = workout;
    }

    public Exercise(String name, String targetWeight, Integer targetSets, Integer targetReps, String resultReps, String comments, Workout workout) {
        super();
        this.name = name;
        this.targetWeight = targetWeight;
        this.targetSets = targetSets;
        this.targetReps = targetReps;
        this.comments = comments;
        this.resultReps = resultReps;
        this.workout = workout;
    }

    public Long getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(Long exerciseId) {
        this.exerciseId = exerciseId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTargetWeight() {
        return targetWeight;
    }

    public void setTargetWeight(String targetWeight) {
        this.targetWeight = targetWeight;
    }

    public Integer getTargetSets() {
        return targetSets;
    }

    public void setTargetSets(Integer targetSets) {
        this.targetSets = targetSets;
    }

    public Integer getTargetReps() {
        return targetReps;
    }

    public void setTargetReps(Integer targetReps) {
        this.targetReps = targetReps;
    }

    public String getResultReps() {
        return resultReps;
    }

    public void setResultReps(String resultReps) {
        this.resultReps = resultReps;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Workout getWorkout() {
        return workout;
    }

    public void setWorkout(Workout workout) {
        this.workout = workout;
    }

    @Override
    public String toString() {
        if (this.workout != null) {
            return "Exercise: " + name +
                    ", target weight: " + targetWeight +
                    ", target sets and reps: " + targetSets + " x " + targetReps +
                    ", results: " + resultReps +
                    ", comments: " + comments +
                    "; workout: " + workout.getDate() + " " + workout.getRoutine();
        } else {
            return "Exercise: " + name +
                    ", target weight: " + targetWeight +
                    ", target sets and reps: " + targetSets + " x " + targetReps +
                    ", results: " + resultReps +
                    ", comments: " + comments;
        }
    }
}
