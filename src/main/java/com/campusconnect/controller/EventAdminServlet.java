package com.campusconnect.controller;
import com.campusconnect.service.*;import com.campusconnect.model.*;import jakarta.servlet.*;import jakarta.servlet.annotation.WebServlet;import jakarta.servlet.http.*;import java.io.IOException;import java.net.URLEncoder;import java.sql.SQLException;import java.util.*;
@WebServlet("/coordinator/events")
public class EventAdminServlet extends HttpServlet{
 private final EventService s=new EventService();
 protected void doGet(HttpServletRequest q,HttpServletResponse r)throws ServletException,IOException{
  Object uid=q.getSession().getAttribute("userId");int adm="ADMIN".equals(q.getSession().getAttribute("role"))?1:0;
  try{List<Event> l=s.managed(uid,adm);q.setAttribute("events",l);q.setAttribute("clubs",new ClubService().managed(uid,adm));
   String ed=q.getParameter("edit");if(ed!=null)for(Event e:l)if(String.valueOf(e.getId()).equals(ed))q.setAttribute("ev",e);
  }catch(SQLException e){throw new ServletException(e);}
  q.getRequestDispatcher("/WEB-INF/views/event-manage.jsp").forward(q,r);}
 protected void doPost(HttpServletRequest q,HttpServletResponse r)throws ServletException,IOException{
  Object uid=q.getSession().getAttribute("userId");int adm="ADMIN".equals(q.getSession().getAttribute("role"))?1:0;String a=q.getParameter("action"),msg;
  Map<String,String> p=new HashMap<>();for(String k:new String[]{"id","clubId","title","description","date","time","location","deadline","capacity"})p.put(k,q.getParameter(k)==null?"":q.getParameter(k));
  try{msg="create".equals(a)?s.create(uid,adm,p):"update".equals(a)?s.update(uid,adm,p):"cancel".equals(a)?s.cancel(uid,adm,p.get("id")):"Unknown action";}catch(SQLException e){throw new ServletException(e);}
  r.sendRedirect("events?msg="+URLEncoder.encode(msg,"UTF-8"));}
}
