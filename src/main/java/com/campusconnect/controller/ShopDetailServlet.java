package com.campusconnect.controller;
import com.campusconnect.dao.OrderDAO;import com.campusconnect.model.*;import jakarta.servlet.*;import jakarta.servlet.annotation.WebServlet;import jakarta.servlet.http.*;import java.io.IOException;import java.sql.SQLException;
@WebServlet({"/shop/order","/shop/product"})
public class ShopDetailServlet extends HttpServlet{
 protected void doGet(HttpServletRequest q,HttpServletResponse r)throws ServletException,IOException{
  OrderDAO d=new OrderDAO();
  try{if(q.getServletPath().endsWith("product")){Product p=d.product(q.getParameter("id"));if(p==null){r.sendError(404);return;}q.setAttribute("p",p);}
   else{Order o=d.order(q.getParameter("id"),q.getSession().getAttribute("userId"));if(o==null){r.sendError(404);return;}q.setAttribute("o",o);q.setAttribute("items",d.items(q.getParameter("id")));}
  }catch(SQLException e){throw new ServletException(e);}
  q.getRequestDispatcher("/WEB-INF/views/shop-detail.jsp").forward(q,r);}
}
