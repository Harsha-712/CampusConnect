package com.campusconnect.service;import com.campusconnect.model.*;
import com.campusconnect.dao.ClubDAO;import java.sql.*;import java.util.*;
public class ClubService{
 private final ClubDAO d=new ClubDAO();
 public static final List<String> CATS=List.of("Technical","Cultural","Sports","Social Service","Literary","Entrepreneurship","Arts","Professional Development");
 public List<Club> list()throws SQLException{return d.list();}
 public List<Club> managed(Object uid,int adm)throws SQLException{return d.managed(uid,adm);}
 public String join(Object uid,String cid)throws SQLException{return d.join(uid,cid);}
 public String update(Object uid,int adm,String id,String n,String cat,String desc,String active)throws SQLException{
  if(n==null||n.trim().length()<2)return "Club name is too short";if(!CATS.contains(cat))return "Invalid category";
  try{return d.update(id,n.trim(),cat,desc,"1".equals(active)?"1":"0",uid,adm)>0?"Club updated":"Not allowed";}catch(SQLIntegrityConstraintViolationException e){return "Club name already in use";}}
 public boolean nameAvailable(String n)throws SQLException{return !d.nameExists(n);}
}
