package com.campusconnect.controller;
import com.campusconnect.service.UserService;import jakarta.servlet.*;import jakarta.servlet.annotation.WebServlet;import jakarta.servlet.http.*;import java.io.IOException;import java.sql.SQLException;
@WebServlet("/register")
public class RegisterServlet extends HttpServlet{
 protected void doGet(HttpServletRequest q,HttpServletResponse r)throws ServletException,IOException{q.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(q,r);}
 protected void doPost(HttpServletRequest q,HttpServletResponse r)throws ServletException,IOException{
  String err;try{err=new UserService().register(q.getParameter("name"),q.getParameter("email"),q.getParameter("password"),q.getParameter("phone"));}catch(SQLException e){throw new ServletException(e);}
  if(err==null){r.sendRedirect(q.getContextPath()+"/login");return;}q.setAttribute("error",err);doGet(q,r);}
}
