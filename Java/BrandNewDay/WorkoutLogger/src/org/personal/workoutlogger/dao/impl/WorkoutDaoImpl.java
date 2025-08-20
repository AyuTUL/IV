package org.personal.workoutlogger.dao.impl;
import org.personal.workoutlogger.dao.WorkoutDao;
import org.personal.workoutlogger.model.Workout;
import org.personal.workoutlogger.model.WorkoutItem;
import org.personal.workoutlogger.connection.ConnectionFactory;
import java.sql.*; import java.util.*;
public class WorkoutDaoImpl implements WorkoutDao{
  public int createWorkout(Workout w){
    String sql="INSERT INTO workouts(user_id,date,notes) VALUES(?,?,?)";
    try(Connection c=ConnectionFactory.getConnection(); PreparedStatement p=c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
      p.setInt(1,w.getUserId()); p.setDate(2, java.sql.Date.valueOf(w.getDate())); p.setString(3,w.getNotes()); p.executeUpdate();
      try(ResultSet g=p.getGeneratedKeys()){ if(g.next()) return g.getInt(1); }
    }catch(Exception e){e.printStackTrace();}
    return -1;
  }
  public void addWorkoutItem(WorkoutItem i){
    String sql="INSERT INTO workout_items(workout_id,exercise_id,sets,reps,weight_used,rest_time) VALUES(?,?,?,?,?,?)";
    try(Connection c=ConnectionFactory.getConnection(); PreparedStatement p=c.prepareStatement(sql)){
      p.setInt(1,i.getWorkoutId()); p.setInt(2,i.getExerciseId()); p.setInt(3,i.getSets()); p.setInt(4,i.getReps()); p.setDouble(5,i.getWeightUsed()); p.setInt(6,i.getRestTime()); p.executeUpdate();
    }catch(Exception e){e.printStackTrace();}
  }
  public java.util.List<Workout> getWorkoutsByUser(int uid){
    String sql="SELECT * FROM workouts WHERE user_id=? ORDER BY date DESC";
    java.util.List<Workout> list=new ArrayList<>();
    try(Connection c=ConnectionFactory.getConnection(); PreparedStatement p=c.prepareStatement(sql)){
      p.setInt(1,uid); try(ResultSet r=p.executeQuery()){
        while(r.next()) list.add(new Workout(r.getInt("id"),r.getInt("user_id"), r.getDate("date").toLocalDate(), r.getString("notes")));
      }
    }catch(Exception e){e.printStackTrace();}
    return list;
  }
  public java.util.List<WorkoutItem> getItemsByWorkout(int wid){
    String sql="SELECT * FROM workout_items WHERE workout_id=?";
    java.util.List<WorkoutItem> list=new ArrayList<>();
    try(Connection c=ConnectionFactory.getConnection(); PreparedStatement p=c.prepareStatement(sql)){
      p.setInt(1,wid); try(ResultSet r=p.executeQuery()){
        while(r.next()) list.add(new WorkoutItem(r.getInt("id"), r.getInt("workout_id"), r.getInt("exercise_id"),
          r.getInt("sets"), r.getInt("reps"), r.getDouble("weight_used"), r.getInt("rest_time")));
      }
    }catch(Exception e){e.printStackTrace();}
    return list;
  }
  public void deleteWorkout(int wid,int requesterUserId,boolean isTrainer){
    String ownerSql="SELECT user_id FROM workouts WHERE id=?";
    try(Connection c=ConnectionFactory.getConnection()){
      int owner=-1; try(PreparedStatement p=c.prepareStatement(ownerSql)){ p.setInt(1,wid); try(ResultSet r=p.executeQuery()){ if(r.next()) owner=r.getInt(1);} }
      if(owner==-1) return; if(!isTrainer && owner!=requesterUserId) return;
      try(PreparedStatement p1=c.prepareStatement("DELETE FROM workout_items WHERE workout_id=?");
          PreparedStatement p2=c.prepareStatement("DELETE FROM workouts WHERE id=?")){
        p1.setInt(1,wid); p1.executeUpdate(); p2.setInt(1,wid); p2.executeUpdate();
      }
    }catch(Exception e){e.printStackTrace();}
  }
}
