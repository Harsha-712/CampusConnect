package com.campusconnect.controller;
import com.campusconnect.service.EventService;import com.campusconnect.model.*;import jakarta.servlet.*;import jakarta.servlet.annotation.WebServlet;import jakarta.servlet.http.*;import java.io.IOException;import java.sql.SQLException;
@WebServlet("/events/details")
public class EventDetailServlet extends HttpServlet{
 protected void doGet(HttpServletRequest q,HttpServletResponse r)throws ServletException,IOException{
  try{Event e=new EventService().get(q.getSession().getAttribute("userId"),q.getParameter("id"));if(e==null){r.sendError(404);return;}q.setAttribute("ev",e);}catch(SQLException x){throw new ServletException(x);}
  q.getRequestDispatcher("/WEB-INF/views/event-details.jsp").forward(q,r);}
}
