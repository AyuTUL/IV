package org.personal.workoutlogger.model;
import java.time.LocalDate;
public class Workout {
  private int id,userId; private LocalDate date; private String notes;
  public Workout(){}
  public Workout(int id,int userId,LocalDate date,String notes){this.id=id;this.userId=userId;this.date=date;this.notes=notes;}
  public int getId(){return id;} public void setId(int id){this.id=id;}
  public int getUserId(){return userId;} public void setUserId(int v){this.userId=v;}
  public LocalDate getDate(){return date;} public void setDate(LocalDate v){this.date=v;}
  public String getNotes(){return notes;} public void setNotes(String v){this.notes=v;}
}
