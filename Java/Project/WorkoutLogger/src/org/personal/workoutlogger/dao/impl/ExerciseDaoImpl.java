package org.personal.workoutlogger.dao.impl;
import org.personal.workoutlogger.dao.ExerciseDao;
import org.personal.workoutlogger.model.Exercise;
import org.personal.workoutlogger.connection.ConnectionFactory;
import java.sql.*; import java.util.*;
public class ExerciseDaoImpl implements ExerciseDao{
  public void add(Exercise ex){
    String sql="INSERT INTO exercises(name,muscle_group) VALUES(?,?)";
    try(Connection c=ConnectionFactory.getConnection(); PreparedStatement p=c.prepareStatement(sql)){
      p.setString(1,ex.getName()); p.setString(2,ex.getMuscleGroup()); p.executeUpdate();
    }catch(Exception e){e.printStackTrace();}
  }
  public void update(Exercise ex){
    String sql="UPDATE exercises SET name=?, muscle_group=? WHERE id=?";
    try(Connection c=ConnectionFactory.getConnection(); PreparedStatement p=c.prepareStatement(sql)){
      p.setString(1,ex.getName()); p.setString(2,ex.getMuscleGroup()); p.setInt(3,ex.getId()); p.executeUpdate();
    }catch(Exception e){e.printStackTrace();}
  }
  public void delete(int id){
    String sql="DELETE FROM exercises WHERE id=?";
    try(Connection c=ConnectionFactory.getConnection(); PreparedStatement p=c.prepareStatement(sql)){
      p.setInt(1,id); p.executeUpdate();
    }catch(Exception e){e.printStackTrace();}
  }
  public Exercise get(int id){
    String sql="SELECT * FROM exercises WHERE id=?";
    try(Connection c=ConnectionFactory.getConnection(); PreparedStatement p=c.prepareStatement(sql)){
      p.setInt(1,id); try(ResultSet r=p.executeQuery()){ if(r.next()) return new Exercise(r.getInt("id"),r.getString("name"),r.getString("muscle_group")); }
    }catch(Exception e){e.printStackTrace();}
    return null;
  }
  public java.util.List<Exercise> searchByName(String q){
    String sql="SELECT * FROM exercises WHERE name LIKE ? ORDER BY name";
    java.util.List<Exercise> list=new ArrayList<>();
    try(Connection c=ConnectionFactory.getConnection(); PreparedStatement p=c.prepareStatement(sql)){
      p.setString(1,"%"+q+"%"); try(ResultSet r=p.executeQuery()){ while(r.next()) list.add(new Exercise(r.getInt("id"),r.getString("name"),r.getString("muscle_group"))); }
    }catch(Exception e){e.printStackTrace();}
    return list;
  }
  public java.util.List<Exercise> getAll(){
    String sql="SELECT * FROM exercises ORDER BY name";
    java.util.List<Exercise> list=new ArrayList<>();
    try(Connection c=ConnectionFactory.getConnection(); PreparedStatement p=c.prepareStatement(sql); ResultSet r=p.executeQuery()){
      while(r.next()) list.add(new Exercise(r.getInt("id"),r.getString("name"),r.getString("muscle_group")));
    }catch(Exception e){e.printStackTrace();}
    return list;
  }
}
