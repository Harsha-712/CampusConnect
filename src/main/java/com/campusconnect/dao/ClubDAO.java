package com.campusconnect.dao;import com.campusconnect.model.*;
import com.campusconnect.util.Q;import java.sql.SQLException;import java.util.*;
public class ClubDAO{
 static final String BASE="SELECT c.id,c.name,c.category,c.description,c.active,IF(c.active,'ACTIVE','INACTIVE') status,(SELECT COUNT(*) FROM club_members m WHERE m.club_id=c.id) members FROM clubs c ";
 public List<Club> list()throws SQLException{return Q.beans(Club.class,BASE+"ORDER BY c.name");}
 public List<Club> managed(Object uid,int adm)throws SQLException{return Q.beans(Club.class,BASE+"WHERE c.coordinator_id=? OR 1=? ORDER BY c.name",uid,adm);}
 public int update(String id,String n,String cat,String d,String active,Object uid,int adm)throws SQLException{return Q.upd("UPDATE clubs SET name=?,category=?,description=?,active=? WHERE id=? AND (coordinator_id=? OR 1=?)",n,cat,d,active,id,uid,adm);}
 public String join(Object uid,String cid)throws SQLException{
  if(Q.beans(Club.class,"SELECT 1 FROM clubs WHERE id=? AND active",cid).isEmpty())return "Club is inactive";
  if(!Q.beans(Club.class,"SELECT 1 FROM club_members WHERE club_id=? AND user_id=?",cid,uid).isEmpty())return "Already a member";
  return Q.upd("INSERT IGNORE INTO membership_requests(club_id,user_id) VALUES(?,?)",cid,uid)>0?"Request sent (PENDING)":"Request already exists";}
 public boolean nameExists(String n)throws SQLException{return !Q.beans(Club.class,"SELECT id FROM clubs WHERE name=?",n).isEmpty();}
}
