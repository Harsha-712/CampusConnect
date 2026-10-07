package com.campusconnect.service;
import com.campusconnect.dao.UserDAO;import com.campusconnect.model.User;import com.campusconnect.util.DBUtil;import java.sql.*;import java.util.List;
public class UserService{
 private final UserDAO d=new UserDAO();
 static final String EMAIL="^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$",PW="^(?=.*[A-Za-z])(?=.*\\d).{8,}$";
 public User login(String email,String pw)throws SQLException{return email==null||pw==null?null:d.authenticate(email,DBUtil.sha256(pw));}
 public boolean emailAvailable(String e)throws SQLException{return !d.emailExists(e);}
 public User get(Object id)throws SQLException{return d.find(id);}
 public List<User> list(String role,String s)throws SQLException{return d.byRole(role,"%"+(s==null?"":s)+"%");}
 public void toggle(String id,Object self)throws SQLException{d.toggle(id,self);}
 public void addStaff(String n,String e,String pw,String role,String ph)throws SQLException{d.insert(n,e,DBUtil.sha256(pw),role,ph);}
 /** Returns an error message or null on success. */
 public String register(String n,String e,String pw,String ph)throws SQLException{
  if(n==null||n.trim().length()<2)return "Name is required";if(e==null||!e.matches(EMAIL))return "Invalid email";if(ph==null||!ph.matches("\\d{10}"))return "Phone must be 10 digits";
  if(pw==null||!pw.matches(PW))return "Weak password";
  try{d.insert(n.trim(),e,DBUtil.sha256(pw),"STUDENT",ph);return null;}catch(SQLIntegrityConstraintViolationException x){return "Email already registered";}}
 public String updateProfile(Object id,String n,String ph,String cur,String np)throws SQLException{
  if(n==null||n.trim().length()<2)return "Name is required";if(ph==null||!ph.matches("\\d{10}"))return "Phone must be 10 digits";
  if(np!=null&&!np.isEmpty()){if(!np.matches(PW))return "New password needs 8+ characters with a letter and a digit";if(!d.passwordMatches(id,DBUtil.sha256(cur==null?"":cur)))return "Current password is incorrect";d.setPassword(id,DBUtil.sha256(np));}
  d.updateProfile(id,n.trim(),ph);return null;}
}
