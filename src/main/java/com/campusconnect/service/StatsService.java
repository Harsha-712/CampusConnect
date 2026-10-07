package com.campusconnect.service;
import com.campusconnect.dao.StatsDAO;import java.sql.SQLException;import java.util.*;
public class StatsService{
 private final StatsDAO d=new StatsDAO();
 private static List<Map<String,Object>> scale(List<Map<String,Object>> l,String k){double m=1;for(Map<String,Object> r:l)m=Math.max(m,((Number)r.get(k)).doubleValue());
  for(Map<String,Object> r:l){r.put("pct",Math.round(100*((Number)r.get(k)).doubleValue()/m));r.put("pct2",Math.round(100*(r.get("att")==null?0:((Number)r.get("att")).doubleValue())/m));}return l;}
 public Map<String,Object> charts()throws SQLException{Map<String,Object> m=new HashMap<>();
  m.put("eventChart",scale(d.eventRegs(),"regs"));m.put("clubChart",scale(d.clubMembers(),"members"));m.put("productChart",scale(d.productSales(),"sold"));return m;}
}
