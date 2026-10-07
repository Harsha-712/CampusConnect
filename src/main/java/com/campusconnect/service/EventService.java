package com.campusconnect.service;
import com.campusconnect.dao.EventDAO;import com.campusconnect.model.*;import java.sql.SQLException;import java.time.LocalDate;import java.util.*;
public class EventService{
 private final EventDAO d=new EventDAO();
 public static String state(Event e){
  String stored=e.getStatus()==null?"ACTIVE":e.getStatus().trim().toUpperCase(Locale.ROOT);
  if("CANCELLED".equals(stored))return "CANCELLED";
  LocalDate today=LocalDate.now();
  if(e.getEventDate()!=null&&e.getEventDate().toLocalDate().isBefore(today))return "COMPLETED";
  if("COMPLETED".equals(stored))return "COMPLETED";
  if("CLOSED".equals(stored))return "CLOSED";
  if(e.getRegDeadline()!=null&&e.getRegDeadline().toLocalDate().isBefore(today))return "CLOSED";
  return e.getTaken()>=e.getCapacity()?"FULL":"OPEN";
 }
 private List<Event> mark(List<Event> l){for(Event e:l)e.setState(state(e));return l;}
 public List<Event> list(Object uid)throws SQLException{return mark(d.list(uid));}
 public List<Event> managed(Object uid,int adm)throws SQLException{return mark(d.managed(uid,adm));}
 public Event get(Object uid,String id)throws SQLException{Event e=d.find(uid,id);if(e!=null)e.setState(state(e));return e;}
 public List<Announcement> announcements()throws SQLException{return d.announcements();}
 public String avail(String id)throws SQLException{Event e=get(0,id);if(e==null)return "NOT FOUND";return e.getState().equals("OPEN")?"OPEN: "+(e.getCapacity()-e.getTaken())+" seats left":e.getState();}
 public String register(Object uid,String id)throws SQLException{
  if(uid==null||id==null||!id.matches("\\d+"))return "Invalid event";
  Event e=get(uid,id);if(e==null)return "Event not found";
  if(e.getMine()>0)return "Already registered";
  if(!"OPEN".equals(e.getState()))return "Registration "+e.getState();
  int inserted=d.register(id,uid);
  if(inserted>0)return "Registered";
  Event latest=get(uid,id);
  if(latest==null)return "Event not found";
  if(latest.getMine()>0)return "Already registered";
  return "Registration "+latest.getState();
 }
 public String unregister(Object uid,String id)throws SQLException{
  if(uid==null||id==null||!id.matches("\\d+"))return "Invalid event";
  Event e=get(uid,id);if(e==null)return "Event not found";
  if(e.getMine()==0)return "You are not registered for this event";
  if("CANCELLED".equals(e.getState())||"COMPLETED".equals(e.getState()))return "Registration can no longer be cancelled";
  return d.unregister(id,uid)>0?"Registration cancelled":"Cannot cancel an attended registration";
 }
 private String check(Map<String,String> p,boolean create){
  try{if(p.get("title").trim().length()<3)return "Title is too short";LocalDate dt=LocalDate.parse(p.get("date")),dl=LocalDate.parse(p.get("deadline"));
   if(create&&dt.isBefore(LocalDate.now()))return "Date cannot be in the past";if(dl.isAfter(dt))return "Deadline must be on or before the event date";
   if(!p.get("time").matches("\\d{2}:\\d{2}(:\\d{2})?"))return "Invalid time";if(p.get("location").trim().isEmpty())return "Location is required";
   if(!p.get("capacity").matches("[1-9]\\d*"))return "Capacity must be a positive number";}catch(RuntimeException x){return "Invalid date";}return null;}
 public String create(Object uid,int adm,Map<String,String> p)throws SQLException{String e=check(p,true);if(e!=null)return e;if(!d.ownsClub(p.get("clubId"),uid,adm))return "Not allowed";
  d.insert(p,UUID.randomUUID().toString().substring(0,6).toUpperCase());return "Event created";}
 public String update(Object uid,int adm,Map<String,String> p)throws SQLException{String e=check(p,false);if(e!=null)return e;if(!d.ownsEvent(p.get("id"),uid,adm))return "Not allowed";
  if(d.find(uid,p.get("id")).getTaken()>Integer.parseInt(p.get("capacity")))return "Capacity is below current registrations";d.update(p);return "Event updated";}
 public String cancel(Object uid,int adm,String id)throws SQLException{if(!d.ownsEvent(id,uid,adm))return "Not allowed";d.cancel(id);return "Event cancelled";}
}
