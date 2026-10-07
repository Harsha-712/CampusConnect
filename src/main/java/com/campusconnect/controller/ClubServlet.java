package com.campusconnect.controller;
import com.campusconnect.service.ClubService;import jakarta.servlet.*;import jakarta.servlet.annotation.WebServlet;import jakarta.servlet.http.*;import java.io.IOException;import java.net.URLEncoder;import java.sql.SQLException;
@WebServlet("/clubs")
public class ClubServlet extends HttpServlet{
 protected void doGet(HttpServletRequest q,HttpServletResponse r)throws ServletException,IOException{
  if(q.getCookies()!=null)for(Cookie c:q.getCookies())if(c.getName().equals("lastClub"))q.setAttribute("lastClub",c.getValue());
  try{q.setAttribute("clubs",new ClubService().list());}catch(SQLException e){throw new ServletException(e);}
  q.setAttribute("cats",ClubService.CATS);q.getRequestDispatcher("/WEB-INF/views/clubs.jsp").forward(q,r);}
 protected void doPost(HttpServletRequest q,HttpServletResponse r)throws ServletException,IOException{
  String msg;try{msg=new ClubService().join(q.getSession().getAttribute("userId"),q.getParameter("clubId"));}catch(SQLException e){throw new ServletException(e);}
  r.sendRedirect("clubs?msg="+URLEncoder.encode(msg,"UTF-8"));}
}
