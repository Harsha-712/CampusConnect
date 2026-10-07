package com.campusconnect.controller;
import jakarta.servlet.annotation.WebServlet;import jakarta.servlet.http.*;import java.io.IOException;
@WebServlet("/logout")
public class LogoutServlet extends HttpServlet{protected void doGet(HttpServletRequest q,HttpServletResponse r)throws IOException{HttpSession s=q.getSession(false);if(s!=null)s.invalidate();r.sendRedirect(q.getContextPath()+"/login");}}
