package org.personal.workoutlogger.model;

public class Exercise {
    private int id;
    private String name;
    private String muscleGroup;

    public Exercise() {}

    public Exercise(int id, String name, String muscleGroup) {
        this.id = id;
        this.name = name;
        this.muscleGroup = muscleGroup;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String v) { this.name = v; }

    public String getMuscleGroup() { return muscleGroup; }
    public void setMuscleGroup(String v) { this.muscleGroup = v; }

    @Override
    public String toString() {
        return name != null ? name : "Unnamed Exercise";
    }
}
