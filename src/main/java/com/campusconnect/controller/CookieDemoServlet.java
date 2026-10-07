package com.campusconnect.controller;
import jakarta.servlet.*;import jakarta.servlet.annotation.WebServlet;import jakarta.servlet.http.*;import java.io.IOException;
@WebServlet("/cookie-demo")
public class CookieDemoServlet extends HttpServlet{
 protected void doGet(HttpServletRequest q,HttpServletResponse r)throws ServletException,IOException{
  String a=q.getParameter("action");
  if("set".equals(a)){Cookie c=new Cookie("theme",q.getParameter("theme")==null?"dark":q.getParameter("theme").replaceAll("[^a-z]",""));c.setMaxAge(7*24*3600);c.setPath("/");r.addCookie(c);r.sendRedirect("cookie-demo");return;}
  if("delete".equals(a)){Cookie c=new Cookie("theme","");c.setMaxAge(0);c.setPath("/");r.addCookie(c);r.sendRedirect("cookie-demo");return;}
  q.getRequestDispatcher("/WEB-INF/views/cookie-demo.jsp").forward(q,r);}
}
