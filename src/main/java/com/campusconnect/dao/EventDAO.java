package com.campusconnect.dao;import com.campusconnect.model.*;
import com.campusconnect.util.Q;import java.sql.SQLException;import java.util.*;
public class EventDAO{
 static final String BASE="SELECT e.id,e.club_id,e.title,e.description,e.event_date,e.event_time,e.location,e.reg_deadline,e.capacity,e.status,e.attendance_code code,c.name club,(SELECT COUNT(*) FROM event_registrations r WHERE r.event_id=e.id) taken,(SELECT COUNT(*) FROM event_registrations r WHERE r.event_id=e.id AND r.user_id=?) mine FROM events e JOIN clubs c ON c.id=e.club_id ";
 public List<Event> list(Object uid)throws SQLException{return Q.beans(Event.class,BASE+"ORDER BY e.event_date",uid);}
 public List<Event> managed(Object uid,int adm)throws SQLException{return Q.beans(Event.class,BASE+"WHERE c.coordinator_id=? OR 1=? ORDER BY e.event_date",uid,uid,adm);}
 public Event find(Object uid,String id)throws SQLException{List<Event> l=Q.beans(Event.class,BASE+"WHERE e.id=?",uid,id);return l.isEmpty()?null:l.get(0);}
 public List<Announcement> announcements()throws SQLException{return Q.beans(Announcement.class,"SELECT a.id,a.title,a.body,c.name club,a.created_at FROM announcements a LEFT JOIN clubs c ON c.id=a.club_id ORDER BY a.id DESC LIMIT 5");}
 public boolean ownsClub(String club,Object uid,int adm)throws SQLException{return !Q.beans(Event.class,"SELECT 1 FROM clubs WHERE id=? AND (coordinator_id=? OR 1=?)",club,uid,adm).isEmpty();}
 public boolean ownsEvent(String id,Object uid,int adm)throws SQLException{return !Q.beans(Event.class,"SELECT 1 FROM events e JOIN clubs c ON c.id=e.club_id WHERE e.id=? AND (c.coordinator_id=? OR 1=?)",id,uid,adm).isEmpty();}
 public void insert(Map<String,String> p,String code)throws SQLException{Q.upd("INSERT INTO events(club_id,title,description,event_date,event_time,location,reg_deadline,capacity,attendance_code) VALUES(?,?,?,?,?,?,?,?,?)",p.get("clubId"),p.get("title"),p.get("description"),p.get("date"),p.get("time"),p.get("location"),p.get("deadline"),p.get("capacity"),code);}
 public void update(Map<String,String> p)throws SQLException{Q.upd("UPDATE events SET title=?,description=?,event_date=?,event_time=?,location=?,reg_deadline=?,capacity=? WHERE id=?",p.get("title"),p.get("description"),p.get("date"),p.get("time"),p.get("location"),p.get("deadline"),p.get("capacity"),p.get("id"));}
 public void cancel(String id)throws SQLException{Q.upd("UPDATE events SET status='CANCELLED' WHERE id=?",id);}
 public int register(String id,Object uid)throws SQLException{
  return Q.upd("INSERT IGNORE INTO event_registrations(event_id,user_id) SELECT e.id,? FROM events e WHERE e.id=? AND e.status<>'CANCELLED' AND e.event_date>=CURDATE() AND (e.reg_deadline IS NULL OR e.reg_deadline>=CURDATE()) AND (SELECT COUNT(*) FROM event_registrations r WHERE r.event_id=e.id)<e.capacity",uid,id);
 }
 public int unregister(String id,Object uid)throws SQLException{return Q.upd("DELETE FROM event_registrations WHERE event_id=? AND user_id=? AND NOT attended",id,uid);}
}
