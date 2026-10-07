package com.campusconnect.controller;
import com.campusconnect.util.Q;import jakarta.servlet.*;import jakarta.servlet.annotation.WebServlet;import jakarta.servlet.http.*;import java.io.IOException;import java.net.URLEncoder;import java.sql.SQLException;
@WebServlet({"/coordinator/members","/coordinator/attendance","/coordinator/proposals"})
public class CoordPagesServlet extends HttpServlet{
 Object uid(HttpServletRequest q){return q.getSession().getAttribute("userId");}int adm(HttpServletRequest q){return "ADMIN".equals(q.getSession().getAttribute("role"))?1:0;}
 protected void doGet(HttpServletRequest q,HttpServletResponse r)throws ServletException,IOException{
  String sp=q.getServletPath();
  try{if(sp.endsWith("members")){q.setAttribute("view","members");q.setAttribute("title","Club Members");q.setAttribute("items",Q.rows("SELECT u.name,u.email,c.name club,m.joined_at FROM club_members m JOIN users u ON u.id=m.user_id JOIN clubs c ON c.id=m.club_id WHERE c.coordinator_id=? OR 1=? ORDER BY c.name,u.name",uid(q),adm(q)));}
   else if(sp.endsWith("attendance")){q.setAttribute("view","attendance");q.setAttribute("title","Registrations & Attendance");q.setAttribute("items",Q.rows("SELECT e.id event_id,r.user_id,e.title,u.name,u.email,IF(r.attended,'PRESENT','REGISTERED') status FROM event_registrations r JOIN events e ON e.id=r.event_id JOIN users u ON u.id=r.user_id JOIN clubs c ON c.id=e.club_id WHERE c.coordinator_id=? OR 1=? ORDER BY e.id,u.name",uid(q),adm(q)));}
   else{q.setAttribute("view","proposals");q.setAttribute("title","Activity Proposals");q.setAttribute("items",new com.campusconnect.service.ProposalService().forCoordinator(uid(q),adm(q)));
    q.setAttribute("clubs",Q.rows("SELECT id,name FROM clubs WHERE coordinator_id=? OR 1=?",uid(q),adm(q)));}
  }catch(SQLException e){throw new ServletException(e);}
  q.getRequestDispatcher("/WEB-INF/views/coord-pages.jsp").forward(q,r);}
 protected void doPost(HttpServletRequest q,HttpServletResponse r)throws ServletException,IOException{
  String sp=q.getServletPath(),msg="Not allowed";
  try{if(sp.endsWith("attendance")){if(!Q.rows("SELECT 1 FROM events e JOIN clubs c ON c.id=e.club_id WHERE e.id=? AND (c.coordinator_id=? OR 1=?)",q.getParameter("eventId"),uid(q),adm(q)).isEmpty()
     &&Q.upd("UPDATE event_registrations SET attended=TRUE WHERE event_id=? AND user_id=? AND NOT attended",q.getParameter("eventId"),q.getParameter("userId"))>0){
     Q.upd("INSERT INTO student_activities(user_id,type,title,hours) SELECT ?,'EVENT_PARTICIPATED',title,0 FROM events WHERE id=?",q.getParameter("userId"),q.getParameter("eventId"));msg="Attendance recorded";}}
   else if(sp.endsWith("proposals")&&!Q.rows("SELECT 1 FROM clubs WHERE id=? AND (coordinator_id=? OR 1=?)",q.getParameter("clubId"),uid(q),adm(q)).isEmpty()){
    new com.campusconnect.service.ProposalService().submit(q.getParameter("clubId"),q.getParameter("title"),q.getParameter("description"));msg="Submitted for faculty review";}
  }catch(SQLException e){throw new ServletException(e);}
  r.sendRedirect(sp.substring(sp.lastIndexOf('/')+1)+"?msg="+URLEncoder.encode(msg,"UTF-8"));}
}
