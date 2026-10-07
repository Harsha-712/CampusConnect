package com.campusconnect.dao;
import com.campusconnect.model.User;import com.campusconnect.util.Q;import java.sql.SQLException;import java.util.*;
public class UserDAO{
 static final String COLS="SELECT id,name,email,password_hash,role,phone,active,IF(active,'Active','Inactive') status FROM users ";
 private User first(List<User> l){return l.isEmpty()?null:l.get(0);}
 public User authenticate(String email,String hash)throws SQLException{return first(Q.beans(User.class,COLS+"WHERE email=? AND password_hash=? AND active=TRUE",email,hash));}
 public User find(Object id)throws SQLException{return first(Q.beans(User.class,COLS+"WHERE id=?",id));}
 public boolean emailExists(String email)throws SQLException{return !Q.beans(User.class,COLS+"WHERE email=?",email).isEmpty();}
 public List<User> byRole(String role,String s)throws SQLException{return Q.beans(User.class,COLS+"WHERE role=? AND (name LIKE ? OR email LIKE ?) ORDER BY id",role,s,s);}
 public void insert(String n,String e,String hash,String role,String ph)throws SQLException{Q.upd("INSERT INTO users(name,email,password_hash,role,phone) VALUES(?,?,?,?,?)",n,e,hash,role,ph);}
 public void toggle(String id,Object self)throws SQLException{Q.upd("UPDATE users SET active=NOT active WHERE id=? AND id<>?",id,self);}
 public boolean passwordMatches(Object id,String hash)throws SQLException{return !Q.beans(User.class,"SELECT id FROM users WHERE id=? AND password_hash=?",id,hash).isEmpty();}
 public void setPassword(Object id,String hash)throws SQLException{Q.upd("UPDATE users SET password_hash=? WHERE id=?",hash,id);}
 public void updateProfile(Object id,String n,String ph)throws SQLException{Q.upd("UPDATE users SET name=?,phone=? WHERE id=?",n,ph,id);}
}
