package com.campusconnect.controller;
import com.campusconnect.service.EventService;import jakarta.servlet.*;import jakarta.servlet.annotation.WebServlet;import jakarta.servlet.http.*;import java.io.IOException;import java.net.URLEncoder;import java.sql.SQLException;
@WebServlet("/events")
public class EventServlet extends HttpServlet{
 private final EventService s=new EventService();
 static String avail(String id)throws SQLException{return new EventService().avail(id);}
 protected void doGet(HttpServletRequest q,HttpServletResponse r)throws ServletException,IOException{
  try{q.setAttribute("events",s.list(q.getSession().getAttribute("userId")));q.setAttribute("ann",s.announcements());}catch(SQLException e){throw new ServletException(e);}
  q.getRequestDispatcher("/WEB-INF/views/events.jsp").forward(q,r);}
 protected void doPost(HttpServletRequest q,HttpServletResponse r)throws ServletException,IOException{
  Object uid=q.getSession().getAttribute("userId");
  String id=q.getParameter("eventId"),action=q.getParameter("action"),msg;
  if(!"STUDENT".equals(q.getSession().getAttribute("role"))){r.sendError(HttpServletResponse.SC_FORBIDDEN,"Only students can register for events");return;}
  try{
   if("cancel".equals(action))msg=s.unregister(uid,id);
   else if("register".equals(action))msg=s.register(uid,id);
   else msg="Invalid event action";
  }catch(SQLException e){throw new ServletException(e);}
  String context=q.getContextPath();
  String safeId=id!=null&&id.matches("\\d+")?id:"";
  String to="details".equals(q.getParameter("next"))?context+"/events/details?id="+safeId+"&":context+"/events?";
  r.sendRedirect(to+"msg="+URLEncoder.encode(msg,"UTF-8"));
 }
}
