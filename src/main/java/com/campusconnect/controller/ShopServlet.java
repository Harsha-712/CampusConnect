package com.campusconnect.controller;
import com.campusconnect.dao.OrderDAO;import com.campusconnect.model.*;import com.campusconnect.service.OrderService;import jakarta.servlet.*;import jakarta.servlet.annotation.WebServlet;import jakarta.servlet.http.*;import java.io.IOException;import java.net.URLEncoder;import java.sql.SQLException;import java.util.*;
@WebServlet("/shop")
public class ShopServlet extends HttpServlet{
 private final OrderService svc=new OrderService();
 @SuppressWarnings("unchecked") static Map<Integer,Integer> cart(HttpSession s){Map<Integer,Integer> c=(Map<Integer,Integer>)s.getAttribute("cart");if(c==null){c=new LinkedHashMap<>();s.setAttribute("cart",c);}return c;}
 protected void doGet(HttpServletRequest q,HttpServletResponse r)throws ServletException,IOException{
  OrderDAO d=svc.dao();
  try{q.setAttribute("products",d.products());List<CartItem> lines=new ArrayList<>();double sum=0;
   for(Map.Entry<Integer,Integer> e:cart(q.getSession()).entrySet()){Product p=d.product(String.valueOf(e.getKey()));if(p==null)continue;CartItem ci=new CartItem();ci.setId(p.getId());ci.setName(p.getName());ci.setPrice(p.getPrice());ci.setQty(e.getValue());ci.setLine(p.getPrice()*e.getValue());sum+=ci.getLine();lines.add(ci);}
   q.setAttribute("lines",lines);q.setAttribute("sum",sum);q.setAttribute("orders",d.orders(q.getSession().getAttribute("userId")));
  }catch(SQLException e){throw new ServletException(e);}
  q.getRequestDispatcher("/WEB-INF/views/shop.jsp").forward(q,r);}
 protected void doPost(HttpServletRequest q,HttpServletResponse r)throws ServletException,IOException{
  Map<Integer,Integer> cart=cart(q.getSession());String a=q.getParameter("action"),msg="OK";
  if("add".equals(a)||"inc".equals(a))cart.merge(Integer.parseInt(q.getParameter("id")),1,Integer::sum);
  else if("dec".equals(a))cart.computeIfPresent(Integer.parseInt(q.getParameter("id")),(x,v)->v>1?v-1:null);
  else if("remove".equals(a))cart.remove(Integer.parseInt(q.getParameter("id")));
  else if("checkout".equals(a)){try{int oid=svc.checkout(q.getSession().getAttribute("userId"),cart,q.getParameter("card"));cart.clear();r.sendRedirect("shop/order?id="+oid+"&confirmed=1");return;}
   catch(IllegalStateException e){msg=e.getMessage();}catch(SQLException e){throw new ServletException(e);}}
  r.sendRedirect("shop?msg="+URLEncoder.encode(msg,"UTF-8"));}
}
