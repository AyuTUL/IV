package org.personal.workoutlogger.model;
public class WorkoutItem {
  private int id,workoutId,exerciseId,sets,reps,restTime; private double weightUsed;
  public WorkoutItem(){}
  public WorkoutItem(int id,int workoutId,int exerciseId,int sets,int reps,double weightUsed,int restTime){
    this.id=id;this.workoutId=workoutId;this.exerciseId=exerciseId;this.sets=sets;this.reps=reps;this.weightUsed=weightUsed;this.restTime=restTime;
  }
  public int getId(){return id;} public void setId(int id){this.id=id;}
  public int getWorkoutId(){return workoutId;} public void setWorkoutId(int v){this.workoutId=v;}
  public int getExerciseId(){return exerciseId;} public void setExerciseId(int v){this.exerciseId=v;}
  public int getSets(){return sets;} public void setSets(int v){this.sets=v;}
  public int getReps(){return reps;} public void setReps(int v){this.reps=v;}
  public double getWeightUsed(){return weightUsed;} public void setWeightUsed(double v){this.weightUsed=v;}
  public int getRestTime(){return restTime;} public void setRestTime(int v){this.restTime=v;}
}
