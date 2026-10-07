package com.campusconnect.dao;
import com.campusconnect.model.Attendance;import com.campusconnect.util.Q;import java.sql.SQLException;import java.util.List;
public class AttendanceDAO{
 public Attendance find(String eventId,Object uid)throws SQLException{
  List<Attendance> l=Q.beans(Attendance.class,"SELECT e.id event_id,r.user_id,e.title event_title,e.attendance_code code,IF(r.attended,'PRESENT','REGISTERED') status FROM events e JOIN event_registrations r ON r.event_id=e.id WHERE e.id=? AND r.user_id=?",eventId,uid);return l.isEmpty()?null:l.get(0);}
 /** Marks attendance and records the verified activity in the Activity Passport. */
 public void mark(String eventId,Object uid)throws SQLException{
  if(Q.upd("UPDATE event_registrations SET attended=TRUE WHERE event_id=? AND user_id=? AND NOT attended",eventId,uid)>0)
   Q.upd("INSERT INTO student_activities(user_id,type,title,hours) SELECT ?,'EVENT_PARTICIPATED',title,0 FROM events WHERE id=?",uid,eventId);}
}
