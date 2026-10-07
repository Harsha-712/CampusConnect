package com.campusconnect.controller;
import com.campusconnect.service.UserService;import jakarta.servlet.annotation.WebServlet;import jakarta.servlet.http.*;import java.io.IOException;import java.sql.SQLException;
@WebServlet("/ajax/check-email")
public class CheckEmailServlet extends HttpServlet{
 protected void doGet(HttpServletRequest q,HttpServletResponse r)throws IOException{
  r.setContentType("text/plain");try{r.getWriter().print(new UserService().emailAvailable(q.getParameter("email"))?"AVAILABLE":"ALREADY REGISTERED");}catch(SQLException e){r.sendError(500);}}
}
