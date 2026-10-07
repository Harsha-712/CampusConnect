package com.campusconnect.controller;
import com.campusconnect.service.*;import jakarta.servlet.annotation.WebServlet;import jakarta.servlet.http.*;import java.io.IOException;import java.sql.SQLException;
@WebServlet({"/ajax/event-availability","/ajax/verify-code","/ajax/check-club"})
public class AjaxServlet extends HttpServlet{
 protected void doGet(HttpServletRequest q,HttpServletResponse r)throws IOException{
  r.setContentType("text/plain");
  try{if(q.getServletPath().endsWith("check-club"))r.getWriter().print(new ClubService().nameAvailable(q.getParameter("name"))?"AVAILABLE":"NAME TAKEN");
   else r.getWriter().print(new EventService().avail(q.getParameter("eventId")));}catch(SQLException e){r.sendError(500);}}
 protected void doPost(HttpServletRequest q,HttpServletResponse r)throws IOException{
  r.setContentType("text/plain");String out;
  try{out=new AttendanceService().verify(q.getSession().getAttribute("userId"),q.getParameter("eventId"),q.getParameter("code"));}catch(SQLException e){out="ERROR";}
  r.getWriter().print(out);}
}
