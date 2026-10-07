package com.campusconnect.controller;
import com.campusconnect.util.*;import jakarta.servlet.*;import jakarta.servlet.annotation.WebServlet;import jakarta.servlet.http.*;import java.io.IOException;import java.net.URLEncoder;import java.sql.*;
@WebServlet({"/admin/users","/admin/products"})
public class AdminServlet extends HttpServlet{
 protected void doGet(HttpServletRequest q,HttpServletResponse r)throws ServletException,IOException{
  try{if(q.getServletPath().endsWith("products")){q.setAttribute("items",Q.rows("SELECT p.id,p.name,p.price,p.stock,p.description,p.category,c.name club FROM products p JOIN clubs c ON c.id=p.club_id ORDER BY p.id"));q.setAttribute("clubs",Q.rows("SELECT id,name FROM clubs"));q.getRequestDispatcher("/WEB-INF/views/admin-products.jsp").forward(q,r);return;}
   String role=q.getParameter("role")==null?"STUDENT":q.getParameter("role"),s="%"+(q.getParameter("s")==null?"":q.getParameter("s"))+"%";
   q.setAttribute("targetRole",role);q.setAttribute("title",role.equals("STUDENT")?"Student Management":role.equals("CLUB_COORDINATOR")?"Coordinator Management":"Faculty Management");
   q.setAttribute("items",new com.campusconnect.service.UserService().list(role,q.getParameter("s")));
  }catch(SQLException e){throw new ServletException(e);}
  q.getRequestDispatcher("/WEB-INF/views/admin-users.jsp").forward(q,r);}
 protected void doPost(HttpServletRequest q,HttpServletResponse r)throws ServletException,IOException{
  String a=q.getParameter("action"),msg="Done",back,ok="";
  try{if(q.getServletPath().endsWith("products")){back="products";
    if("add".equals(a))Q.upd("INSERT INTO products(club_id,name,price,stock,description,category) VALUES(?,?,?,?,?,?)",q.getParameter("clubId"),q.getParameter("name"),q.getParameter("price"),q.getParameter("stock"),q.getParameter("description"),q.getParameter("category"));
    else if("update".equals(a))Q.upd("UPDATE products SET price=?,stock=? WHERE id=?",q.getParameter("price"),q.getParameter("stock"),q.getParameter("id"));
    else msg=Q.upd("DELETE FROM products WHERE id=? AND NOT EXISTS(SELECT 1 FROM order_items WHERE product_id=?)",q.getParameter("id"),q.getParameter("id"))>0?"Deleted":"Cannot delete: product has orders";
   }else{String role=q.getParameter("role");back="users?role="+role;
    if("toggle".equals(a)){new com.campusconnect.service.UserService().toggle(q.getParameter("id"),q.getSession().getAttribute("userId"));msg="User status updated";ok="1";}
    else{String n=q.getParameter("name"),em=q.getParameter("email"),pw=q.getParameter("password"),ph=q.getParameter("phone");
     if(!"CLUB_COORDINATOR".equals(role)&&!"FACULTY_COORDINATOR".equals(role))msg="Invalid user role";
     else if(n==null||n.trim().length()<2)msg="Name is required";
     else if(em==null||!em.matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$"))msg="Invalid email address";
     else if(pw==null||pw.length()<8)msg="Password must contain at least 8 characters";
     else if(ph!=null&&!ph.isEmpty()&&!ph.matches("\\d{10}"))msg="Phone must be 10 digits";
     else{new com.campusconnect.service.UserService().addStaff(n.trim(),em.trim(),pw,role,ph);msg="User added successfully";ok="1";}}}
  }catch(SQLIntegrityConstraintViolationException e){back=q.getServletPath().endsWith("products")?"products":"users?role="+q.getParameter("role");msg=q.getServletPath().endsWith("products")?"Duplicate or invalid reference":"Email already exists";}catch(SQLException e){throw new ServletException(e);}
  r.sendRedirect(back+(back.contains("?")?"&":"?")+"ok="+ok+"&msg="+URLEncoder.encode(msg,"UTF-8"));}
}
