package org.personal.workoutlogger.dao.impl;
import org.personal.workoutlogger.dao.BodyMetricDao;
import org.personal.workoutlogger.model.BodyMetric;
import org.personal.workoutlogger.connection.ConnectionFactory;
import java.sql.*; import java.util.*; 
public class BodyMetricDaoImpl implements BodyMetricDao{
  public void add(BodyMetric bm){
    String sql="INSERT INTO body_metrics(user_id,weight,body_fat,height,age,date) VALUES(?,?,?,?,?,?)";
    try(Connection c=ConnectionFactory.getConnection(); PreparedStatement p=c.prepareStatement(sql)){
      p.setInt(1,bm.getUserId()); p.setDouble(2,bm.getWeight());
      if(bm.getBodyFat()==null) p.setNull(3,Types.DOUBLE); else p.setDouble(3,bm.getBodyFat());
      p.setDouble(4,bm.getHeight()); p.setInt(5,bm.getAge()); p.setDate(6, java.sql.Date.valueOf(bm.getDate())); p.executeUpdate();
    }catch(Exception e){e.printStackTrace();}
  }
  public java.util.List<BodyMetric> findByUser(int uid){
    String sql="SELECT * FROM body_metrics WHERE user_id=? ORDER BY date DESC";
    java.util.List<BodyMetric> list=new ArrayList<>();
    try(Connection c=ConnectionFactory.getConnection(); PreparedStatement p=c.prepareStatement(sql)){
      p.setInt(1,uid); try(ResultSet r=p.executeQuery()){
        while(r.next()){
          list.add(new BodyMetric(r.getInt("id"), r.getInt("user_id"), r.getDouble("weight"),
            (r.getObject("body_fat")==null?null:r.getDouble("body_fat")),
            r.getDouble("height"), r.getInt("age"), r.getDate("date").toLocalDate()));
        }
      }
    }catch(Exception e){e.printStackTrace();}
    return list;
  }
}
