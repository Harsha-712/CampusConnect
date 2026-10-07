package com.campusconnect.controller;
import jakarta.servlet.*;import jakarta.servlet.annotation.WebFilter;import jakarta.servlet.http.*;import java.io.IOException;
@WebFilter({"/student/*","/coordinator/*","/faculty/*","/admin/*","/clubs","/clubs/*","/events","/events/*","/shop","/shop/*","/ajax/event-availability","/ajax/verify-code","/ajax/check-club"})
public class AuthFilter implements Filter{
 public void doFilter(ServletRequest rq,ServletResponse rs,FilterChain c)throws IOException,ServletException{
  HttpServletRequest q=(HttpServletRequest)rq;HttpServletResponse r=(HttpServletResponse)rs;HttpSession s=q.getSession(false);
  if(s==null||s.getAttribute("userId")==null){r.sendRedirect(q.getContextPath()+"/login");return;}
  String p=q.getServletPath(),role=(String)s.getAttribute("role"),need=p.startsWith("/student")?"STUDENT":p.startsWith("/coordinator")?"CLUB_COORDINATOR":p.startsWith("/faculty")?"FACULTY_COORDINATOR":p.startsWith("/admin")?"ADMIN":role;
  if(!need.equals(role)&&!"ADMIN".equals(role)){r.sendError(403,"Access denied for role "+role);return;}
  c.doFilter(rq,rs);}
}
