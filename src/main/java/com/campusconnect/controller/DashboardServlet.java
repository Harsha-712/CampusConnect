package com.campusconnect.controller;
import com.campusconnect.util.*;import jakarta.servlet.*;import jakarta.servlet.annotation.WebServlet;import jakarta.servlet.http.*;import java.io.IOException;import java.sql.*;import java.util.*;
@WebServlet({"/student/dashboard","/coordinator/dashboard","/faculty/dashboard","/admin/dashboard"})
public class DashboardServlet extends HttpServlet{
 protected void doGet(HttpServletRequest q,HttpServletResponse r)throws ServletException,IOException{
  int uid=(Integer)q.getSession().getAttribute("userId");
  if(!"STUDENT".equals(q.getSession().getAttribute("role"))){try{q.setAttribute("stats",Q.rows("SELECT (SELECT COUNT(*) FROM users WHERE role='STUDENT') students,(SELECT COUNT(*) FROM clubs) clubs,(SELECT COUNT(*) FROM events) events,(SELECT COUNT(*) FROM event_registrations) registrations,(SELECT COUNT(*) FROM users WHERE active) active_users").get(0));}catch(SQLException e){throw new ServletException(e);}q.getRequestDispatcher("/WEB-INF/views/dashboard-other.jsp").forward(q,r);return;}
  Map<String,Object> pp=new LinkedHashMap<>();List<String> clubs=new ArrayList<>();
  try(Connection c=DBUtil.get()){
   String[] types={"EVENT_PARTICIPATED","EVENT_ORGANIZED","WORKSHOP","VOLUNTEER"};for(String t:types)pp.put(t,0);double hrs=0;
   try(PreparedStatement ps=c.prepareStatement("SELECT type,COUNT(*),SUM(hours) FROM student_activities WHERE user_id=? AND verified GROUP BY type")){ps.setInt(1,uid);ResultSet rs=ps.executeQuery();while(rs.next()){pp.put(rs.getString(1),rs.getInt(2));hrs+=rs.getDouble(3);}}
   pp.put("HOURS",hrs);
   try(PreparedStatement ps=c.prepareStatement("SELECT COUNT(*) FROM certificates WHERE user_id=?")){ps.setInt(1,uid);ResultSet rs=ps.executeQuery();rs.next();pp.put("CERTS",rs.getInt(1));}
   try(PreparedStatement ps=c.prepareStatement("SELECT c.name FROM club_members m JOIN clubs c ON c.id=m.club_id WHERE m.user_id=?")){ps.setInt(1,uid);ResultSet rs=ps.executeQuery();while(rs.next())clubs.add(rs.getString(1));}
  }catch(SQLException e){throw new ServletException(e);}
  q.setAttribute("pp",pp);try{q.setAttribute("upcoming",Q.rows("SELECT e.id,e.title,e.event_date,c.name club FROM events e JOIN clubs c ON c.id=e.club_id WHERE e.event_date>=CURDATE() ORDER BY e.event_date LIMIT 5"));q.setAttribute("ann",Q.rows("SELECT a.title,a.body,c.name club FROM announcements a LEFT JOIN clubs c ON c.id=a.club_id ORDER BY a.id DESC LIMIT 3"));q.setAttribute("recent",Q.rows("SELECT type,title,hours FROM student_activities WHERE user_id=? ORDER BY id DESC LIMIT 6",uid));}catch(SQLException e){throw new ServletException(e);}q.setAttribute("clubs",clubs);q.getRequestDispatcher("/WEB-INF/views/dashboard-student.jsp").forward(q,r);}
}
