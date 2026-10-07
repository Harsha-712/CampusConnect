package com.campusconnect.service;
import com.campusconnect.dao.AttendanceDAO;import com.campusconnect.model.Attendance;import java.sql.SQLException;
public class AttendanceService{
 private final AttendanceDAO d=new AttendanceDAO();
 public String verify(Object uid,String eventId,String code)throws SQLException{
  if(uid==null||eventId==null||!eventId.matches("\\d+"))return "INVALID EVENT";
  Attendance a=d.find(eventId,uid);if(a==null)return "NOT REGISTERED";
  com.campusconnect.model.Event event=new EventService().get(uid,eventId);
  if(event==null)return "INVALID EVENT";
  if("CANCELLED".equals(event.getStatus()))return "EVENT CANCELLED";
  if("PRESENT".equals(a.getStatus()))return "ALREADY MARKED";
  if(code==null||a.getCode()==null||!a.getCode().equalsIgnoreCase(code.trim()))return "INVALID CODE";
  d.mark(eventId,uid);return "VALID - attendance recorded, passport updated";
 }
}
