package com.campusconnect.controller;
import com.campusconnect.util.Q;import jakarta.servlet.*;import jakarta.servlet.annotation.WebServlet;import jakarta.servlet.http.*;import java.io.IOException;import java.net.URLEncoder;import java.sql.SQLException;
@WebServlet("/faculty/review")
public class FacultyServlet extends HttpServlet{
 protected void doGet(HttpServletRequest q,HttpServletResponse r)throws ServletException,IOException{
  try{q.setAttribute("proposals",new com.campusconnect.service.ProposalService().all());
   q.setAttribute("events",Q.rows("SELECT e.title,c.name club,e.event_date,(SELECT COUNT(*) FROM event_registrations r WHERE r.event_id=e.id) regs,(SELECT COUNT(*) FROM event_registrations r WHERE r.event_id=e.id AND r.attended) att FROM events e JOIN clubs c ON c.id=e.club_id ORDER BY e.event_date"));
   q.setAttribute("certs",new com.campusconnect.service.CertificateService().recent(10));
  }catch(SQLException e){throw new ServletException(e);}
  q.getRequestDispatcher("/WEB-INF/views/faculty-review.jsp").forward(q,r);}
 protected void doPost(HttpServletRequest q,HttpServletResponse r)throws ServletException,IOException{
  try{new com.campusconnect.service.ProposalService().decide(q.getParameter("id"),"approve".equals(q.getParameter("action")));}catch(SQLException e){throw new ServletException(e);}
  r.sendRedirect("review?msg="+URLEncoder.encode("Decision saved","UTF-8"));}
}
