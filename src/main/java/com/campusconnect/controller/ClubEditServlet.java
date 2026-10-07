package com.campusconnect.controller;
import com.campusconnect.service.ClubService;import jakarta.servlet.*;import jakarta.servlet.annotation.WebServlet;import jakarta.servlet.http.*;import java.io.IOException;import java.net.URLEncoder;import java.sql.SQLException;
@WebServlet("/coordinator/clubs")
public class ClubEditServlet extends HttpServlet{
 int adm(HttpServletRequest q){return "ADMIN".equals(q.getSession().getAttribute("role"))?1:0;}
 protected void doGet(HttpServletRequest q,HttpServletResponse r)throws ServletException,IOException{
  try{q.setAttribute("clubs",new ClubService().managed(q.getSession().getAttribute("userId"),adm(q)));}catch(SQLException e){throw new ServletException(e);}
  q.setAttribute("cats",ClubService.CATS);q.getRequestDispatcher("/WEB-INF/views/club-manage.jsp").forward(q,r);}
 protected void doPost(HttpServletRequest q,HttpServletResponse r)throws ServletException,IOException{
  String msg;try{msg=new ClubService().update(q.getSession().getAttribute("userId"),adm(q),q.getParameter("id"),q.getParameter("name"),q.getParameter("category"),q.getParameter("description"),q.getParameter("active"));}catch(SQLException e){throw new ServletException(e);}
  r.sendRedirect("clubs?msg="+URLEncoder.encode(msg,"UTF-8"));}
}
