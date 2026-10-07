package com.campusconnect.controller;
import com.campusconnect.util.Q;import jakarta.servlet.*;import jakarta.servlet.annotation.WebServlet;import jakarta.servlet.http.*;import java.io.IOException;import java.net.URLEncoder;import java.sql.SQLException;import java.util.*;
@WebServlet("/coordinator/manage")
public class ManageServlet extends HttpServlet{
 int uid(HttpServletRequest q){return (Integer)q.getSession().getAttribute("userId");}
 int adm(HttpServletRequest q){return "ADMIN".equals(q.getSession().getAttribute("role"))?1:0;}
 boolean owns(HttpServletRequest q,String club)throws SQLException{return !Q.rows("SELECT 1 FROM clubs WHERE id=? AND (coordinator_id=? OR 1=?)",club,uid(q),adm(q)).isEmpty();}
 protected void doGet(HttpServletRequest q,HttpServletResponse r)throws ServletException,IOException{
  try{q.setAttribute("pending",Q.rows("SELECT r.id,u.name,c.name club FROM membership_requests r JOIN users u ON u.id=r.user_id JOIN clubs c ON c.id=r.club_id WHERE r.status='PENDING' AND (c.coordinator_id=? OR 1=?)",uid(q),adm(q)));
   q.setAttribute("clubs",Q.rows("SELECT id,name FROM clubs WHERE active AND (coordinator_id=? OR 1=?)",uid(q),adm(q)));
   q.setAttribute("events",Q.rows("SELECT e.id,e.title,e.attendance_code code,(SELECT COUNT(*) FROM event_registrations r WHERE r.event_id=e.id) regs,(SELECT COUNT(*) FROM event_registrations r WHERE r.event_id=e.id AND r.attended) att FROM events e JOIN clubs c ON c.id=e.club_id WHERE c.coordinator_id=? OR 1=?",uid(q),adm(q)));
  }catch(SQLException e){throw new ServletException(e);}
  q.getRequestDispatcher("/WEB-INF/views/manage.jsp").forward(q,r);}
 protected void doPost(HttpServletRequest q,HttpServletResponse r)throws ServletException,IOException{
  String a=q.getParameter("action"),msg="Done";
  try{switch(a){
   case "approve":case "reject":{List<Map<String,Object>> l=Q.rows("SELECT r.club_id,r.user_id FROM membership_requests r JOIN clubs c ON c.id=r.club_id WHERE r.id=? AND r.status='PENDING' AND (c.coordinator_id=? OR 1=?)",q.getParameter("id"),uid(q),adm(q));
     if(l.isEmpty()){msg="Not allowed";break;}
     Q.upd("UPDATE membership_requests SET status=? WHERE id=?",a.equals("approve")?"APPROVED":"REJECTED",q.getParameter("id"));
     if(a.equals("approve"))Q.upd("INSERT IGNORE INTO club_members(club_id,user_id) VALUES(?,?)",l.get(0).get("club_id"),l.get(0).get("user_id"));break;}
   case "announce":{if(!owns(q,q.getParameter("clubId"))){msg="Not allowed";break;}
     Q.upd("INSERT INTO announcements(club_id,title,body) VALUES(?,?,?)",q.getParameter("clubId"),q.getParameter("title"),q.getParameter("body"));msg="Announcement posted";break;}
   case "certify":{if(Q.rows("SELECT e.id FROM events e JOIN clubs c ON c.id=e.club_id WHERE e.id=? AND (c.coordinator_id=? OR 1=?)",q.getParameter("id"),uid(q),adm(q)).isEmpty()){msg="Not allowed";break;}
     msg=new com.campusconnect.service.CertificateService().issue(q.getParameter("id"))+" certificates issued";break;}
   default:msg="Unknown action";}
  }catch(SQLException e){throw new ServletException(e);}
  r.sendRedirect("manage?msg="+URLEncoder.encode(msg,"UTF-8"));}
}
