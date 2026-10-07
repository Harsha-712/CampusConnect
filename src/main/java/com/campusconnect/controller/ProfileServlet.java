package com.campusconnect.controller;
import com.campusconnect.service.UserService;import jakarta.servlet.*;import jakarta.servlet.annotation.WebServlet;import jakarta.servlet.http.*;import java.io.IOException;import java.sql.SQLException;
@WebServlet("/student/profile")
public class ProfileServlet extends HttpServlet{
 private final UserService s=new UserService();
 void show(HttpServletRequest q,HttpServletResponse r)throws ServletException,IOException{
  try{q.setAttribute("u",s.get(q.getSession().getAttribute("userId")));}catch(SQLException e){throw new ServletException(e);}
  q.getRequestDispatcher("/WEB-INF/views/profile.jsp").forward(q,r);}
 protected void doGet(HttpServletRequest q,HttpServletResponse r)throws ServletException,IOException{show(q,r);}
 protected void doPost(HttpServletRequest q,HttpServletResponse r)throws ServletException,IOException{
  String err;try{err=s.updateProfile(q.getSession().getAttribute("userId"),q.getParameter("name"),q.getParameter("phone"),q.getParameter("current"),q.getParameter("newPassword"));
   if(err==null){q.getSession().setAttribute("username",q.getParameter("name").trim());q.setAttribute("msg","Profile updated");}}catch(SQLException e){throw new ServletException(e);}
  q.setAttribute("err",err);show(q,r);}
}
