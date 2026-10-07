package com.campusconnect.service;
import com.campusconnect.dao.OrderDAO;import java.sql.SQLException;import java.util.*;
public class OrderService{
 private final OrderDAO d=new OrderDAO();
 public OrderDAO dao(){return d;}
 /** Simulated payment: 16 digits, cards ending 0000 are declined. Returns new order id. */
 public int checkout(Object uid,Map<Integer,Integer> cart,String card)throws SQLException{
  if(cart.isEmpty())throw new IllegalStateException("Cart is empty");if(card==null||!card.matches("\\d{16}"))throw new IllegalStateException("Payment failed: enter a 16-digit card");
  if(card.endsWith("0000"))throw new IllegalStateException("Payment declined (simulated)");return d.place(uid,cart);}
}
