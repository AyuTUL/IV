package org.personal.workoutlogger.dao.impl;
import org.personal.workoutlogger.dao.UserDao;
import org.personal.workoutlogger.model.User;
import org.personal.workoutlogger.connection.ConnectionFactory;
import java.sql.*; import java.security.MessageDigest;
public class UserDaoImpl implements UserDao{
  private String hash(String s){ try{ MessageDigest md=MessageDigest.getInstance("SHA-256");
    byte[] b=md.digest(s.getBytes("UTF-8")); StringBuilder sb=new StringBuilder();
    for(byte x:b) sb.append(String.format("%02x", x)); return sb.toString(); }catch(Exception e){throw new RuntimeException(e);} }
  public User findByUsername(String u){
    String sql="SELECT id,username,password_hash,role FROM users WHERE username=?";
    try(Connection c=ConnectionFactory.getConnection(); PreparedStatement p=c.prepareStatement(sql)){
      p.setString(1,u); try(ResultSet r=p.executeQuery()){ if(r.next()) return new User(r.getInt(1),r.getString(2),r.getString(3),r.getString(4));}
    }catch(Exception e){e.printStackTrace();} return null;
  }
  public User login(String u,String raw){ User x=findByUsername(u); if(x==null) return null; return hash(raw).equals(x.getPasswordHash())?x:null; }
  public boolean register(String u,String raw,String role){
    if(findByUsername(u)!=null) return false;
    String sql="INSERT INTO users(username,password_hash,role) VALUES(?,?,?)";
    try(Connection c=ConnectionFactory.getConnection(); PreparedStatement p=c.prepareStatement(sql)){
      p.setString(1,u); p.setString(2,hash(raw)); p.setString(3,role); return p.executeUpdate()==1;
    }catch(Exception e){e.printStackTrace();} return false;
  }
}
