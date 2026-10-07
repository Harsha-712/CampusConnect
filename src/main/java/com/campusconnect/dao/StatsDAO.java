package com.campusconnect.dao;
import com.campusconnect.util.Q;import java.sql.SQLException;import java.util.*;
public class StatsDAO{
 public List<Map<String,Object>> eventRegs()throws SQLException{return Q.rows("SELECT e.title,COUNT(r.user_id) regs,COALESCE(SUM(r.attended),0) att FROM events e LEFT JOIN event_registrations r ON r.event_id=e.id GROUP BY e.id,e.title ORDER BY e.id");}
 public List<Map<String,Object>> clubMembers()throws SQLException{return Q.rows("SELECT c.name,COUNT(m.user_id) members FROM clubs c LEFT JOIN club_members m ON m.club_id=c.id GROUP BY c.id,c.name");}
 public List<Map<String,Object>> productSales()throws SQLException{return Q.rows("SELECT p.name,COALESCE(SUM(oi.qty),0) sold FROM products p LEFT JOIN order_items oi ON oi.product_id=p.id GROUP BY p.id,p.name");}
}
