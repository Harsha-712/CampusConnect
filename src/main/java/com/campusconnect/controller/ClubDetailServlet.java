package com.campusconnect.controller;
import com.campusconnect.util.Q;import jakarta.servlet.*;import jakarta.servlet.annotation.WebServlet;import jakarta.servlet.http.*;import java.io.IOException;import java.sql.SQLException;import java.util.*;
@WebServlet("/clubs/details")
public class ClubDetailServlet extends HttpServlet{
 protected void doGet(HttpServletRequest q,HttpServletResponse r)throws ServletException,IOException{
  String id=q.getParameter("id");Object uid=q.getSession().getAttribute("userId");
  try{List<Map<String,Object>> l=Q.rows("SELECT c.id,c.name,c.category,c.description,f.name faculty,co.name coordinator,(SELECT COUNT(*) FROM club_members m WHERE m.club_id=c.id) members FROM clubs c LEFT JOIN users f ON f.id=c.faculty_id LEFT JOIN users co ON co.id=c.coordinator_id WHERE c.id=?",id);
   if(l.isEmpty()){r.sendError(404);return;}
   List<Map<String,Object>> s=Q.rows("SELECT 'APPROVED' s FROM club_members WHERE club_id=? AND user_id=? UNION SELECT status FROM membership_requests WHERE club_id=? AND user_id=?",id,uid,id,uid);
   q.setAttribute("club",l.get(0));q.setAttribute("status",s.isEmpty()?"NONE":s.get(0).get("s"));
   q.setAttribute("events",Q.rows("SELECT id,title,event_date FROM events WHERE club_id=? ORDER BY event_date",id));
  }catch(SQLException e){throw new ServletException(e);}
  if(id.matches("\\d+")){Cookie c=new Cookie("lastClub",id);c.setMaxAge(7*24*3600);c.setPath("/");r.addCookie(c);}
  q.getRequestDispatcher("/WEB-INF/views/club-details.jsp").forward(q,r);}
}
