package org.personal.workoutlogger.model;
import java.time.LocalDate;
public class BodyMetric {
  private int id,userId,age; private double weight,height; private Double bodyFat; private LocalDate date;
  public BodyMetric(){}
  public BodyMetric(int id,int userId,double weight,Double bodyFat,double height,int age,LocalDate date){
    this.id=id;this.userId=userId;this.weight=weight;this.bodyFat=bodyFat;this.height=height;this.age=age;this.date=date;
  }
  public int getId(){return id;} public void setId(int id){this.id=id;}
  public int getUserId(){return userId;} public void setUserId(int v){this.userId=v;}
  public double getWeight(){return weight;} public void setWeight(double v){this.weight=v;}
  public Double getBodyFat(){return bodyFat;} public void setBodyFat(Double v){this.bodyFat=v;}
  public double getHeight(){return height;} public void setHeight(double v){this.height=v;}
  public int getAge(){return age;} public void setAge(int v){this.age=v;}
  public LocalDate getDate(){return date;} public void setDate(LocalDate v){this.date=v;}
}
