package com.campusconnect.controller;
import com.campusconnect.util.Q;import jakarta.servlet.*;import jakarta.servlet.annotation.WebServlet;import jakarta.servlet.http.*;import java.io.IOException;import java.sql.SQLException;
@WebServlet({"/student/memberships","/student/certificates","/student/announcements","/student/certificate"})
public class StudentPagesServlet extends HttpServlet{
 protected void doGet(HttpServletRequest q,HttpServletResponse r)throws ServletException,IOException{
  Object uid=q.getSession().getAttribute("userId");String sp=q.getServletPath();
  try{if(sp.endsWith("/certificate")){com.campusconnect.model.Certificate ce=new com.campusconnect.service.CertificateService().find(q.getParameter("id"),uid);if(ce==null){r.sendError(404);return;}q.setAttribute("cert",ce);q.getRequestDispatcher("/WEB-INF/views/certificate.jsp").forward(q,r);return;}
   if(sp.endsWith("memberships")){q.setAttribute("view","memberships");q.setAttribute("title","My Memberships");
    q.setAttribute("items",Q.rows("SELECT c.name,c.category,'APPROVED' status FROM club_members m JOIN clubs c ON c.id=m.club_id WHERE m.user_id=? UNION ALL SELECT c.name,c.category,r.status FROM membership_requests r JOIN clubs c ON c.id=r.club_id WHERE r.user_id=? AND r.status<>'APPROVED'",uid,uid));}
   else if(sp.endsWith("certificates")){q.setAttribute("view","certificates");q.setAttribute("title","My Certificates");q.setAttribute("items",new com.campusconnect.service.CertificateService().byUser(uid));}
   else{q.setAttribute("view","announcements");q.setAttribute("title","Announcements");q.setAttribute("items",Q.rows("SELECT a.title,a.body,c.name club,a.created_at FROM announcements a LEFT JOIN clubs c ON c.id=a.club_id ORDER BY a.id DESC")); }
  }catch(SQLException e){throw new ServletException(e);}
  q.getRequestDispatcher("/WEB-INF/views/student-pages.jsp").forward(q,r);}
}
