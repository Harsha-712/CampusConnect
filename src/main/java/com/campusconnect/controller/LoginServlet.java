package com.campusconnect.controller;
import com.campusconnect.model.User;import com.campusconnect.service.UserService;import jakarta.servlet.*;import jakarta.servlet.annotation.WebServlet;import jakarta.servlet.http.*;import java.io.IOException;import java.sql.SQLException;
@WebServlet("/login")
public class LoginServlet extends HttpServlet{
 protected void doGet(HttpServletRequest q,HttpServletResponse r)throws ServletException,IOException{
  if(q.getCookies()!=null)for(Cookie c:q.getCookies())if(c.getName().equals("rememberEmail"))q.setAttribute("email",c.getValue()); // cookie READ
  q.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(q,r);}
 protected void doPost(HttpServletRequest q,HttpServletResponse r)throws ServletException,IOException{
  String email=q.getParameter("email");User u;
  try{u=new UserService().login(email,q.getParameter("password"));}catch(SQLException e){throw new ServletException(e);}
  if(u!=null){HttpSession s=q.getSession(true);s.setAttribute("userId",u.getId());s.setAttribute("username",u.getName());s.setAttribute("role",u.getRole());s.setMaxInactiveInterval(1800);
   Cookie ck=new Cookie("rememberEmail",email); // cookie CREATE / DELETE
   ck.setMaxAge(q.getParameter("remember")!=null?30*24*3600:0);ck.setPath("/");r.addCookie(ck);
   String role=u.getRole();r.sendRedirect(q.getContextPath()+"/"+(role.equals("STUDENT")?"student":role.equals("CLUB_COORDINATOR")?"coordinator":role.equals("FACULTY_COORDINATOR")?"faculty":"admin")+"/dashboard");return;}
  q.setAttribute("error","Invalid email or password");q.setAttribute("email",email);q.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(q,r);}
}
