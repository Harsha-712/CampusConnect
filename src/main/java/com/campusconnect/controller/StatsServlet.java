package com.campusconnect.controller;
import com.campusconnect.util.Q;import jakarta.servlet.*;import jakarta.servlet.annotation.WebServlet;import jakarta.servlet.http.*;import java.io.IOException;import java.net.URLEncoder;import java.sql.SQLException;
@WebServlet({"/admin/stats","/faculty/stats","/coordinator/stats"})
public class StatsServlet extends HttpServlet{
 protected void doGet(HttpServletRequest q,HttpServletResponse r)throws ServletException,IOException{
  try{q.setAttribute("stats",Q.rows("SELECT (SELECT COUNT(*) FROM users) users,(SELECT COUNT(*) FROM clubs) clubs,(SELECT COUNT(*) FROM events) events,(SELECT COUNT(*) FROM event_registrations) registrations,(SELECT COUNT(*) FROM event_registrations WHERE attended) attended,(SELECT COUNT(*) FROM certificates) certificates,(SELECT COALESCE(SUM(total),0) FROM orders) revenue").get(0));
   q.setAttribute("perClub",Q.rows("SELECT c.name,(SELECT COUNT(*) FROM club_members m WHERE m.club_id=c.id) members,(SELECT COUNT(*) FROM events e WHERE e.club_id=c.id) events,(SELECT COUNT(*) FROM event_registrations r JOIN events e ON e.id=r.event_id WHERE e.club_id=c.id) regs FROM clubs c"));
   if("ADMIN".equals(q.getSession().getAttribute("role")))q.setAttribute("users",Q.rows("SELECT id,name,email,role,active FROM users"));
  }catch(SQLException e){throw new ServletException(e);}
  try{q.setAttribute("charts",new com.campusconnect.service.StatsService().charts());}catch(SQLException e){throw new ServletException(e);}q.getRequestDispatcher("/WEB-INF/views/stats.jsp").forward(q,r);}
 protected void doPost(HttpServletRequest q,HttpServletResponse r)throws ServletException,IOException{
  if(!"ADMIN".equals(q.getSession().getAttribute("role"))){r.sendError(403);return;}
  String msg="Done";
  try{if("toggle".equals(q.getParameter("action")))Q.upd("UPDATE users SET active=NOT active WHERE id=? AND id<>?",q.getParameter("id"),q.getSession().getAttribute("userId"));
   else msg=Q.upd("INSERT INTO clubs(name,category,description,coordinator_id,faculty_id) SELECT ?,?,?,u.id,(SELECT id FROM users WHERE role='FACULTY_COORDINATOR' LIMIT 1) FROM users u WHERE u.email=? AND u.role='CLUB_COORDINATOR'",q.getParameter("name"),q.getParameter("category"),q.getParameter("description"),q.getParameter("coordEmail"))>0?"Club created":"Coordinator email not found";
  }catch(SQLException e){msg="Error: "+e.getMessage();}
  r.sendRedirect("stats?msg="+URLEncoder.encode(msg,"UTF-8"));}
}
