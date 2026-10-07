package com.campusconnect.dao;import com.campusconnect.model.*;
import com.campusconnect.util.*;import java.sql.*;import java.util.*;
public class OrderDAO{
 public List<Product> products()throws SQLException{return Q.beans(Product.class,"SELECT p.id,p.name,p.price,p.stock,p.description,p.category,c.name club FROM products p JOIN clubs c ON c.id=p.club_id");}
 public Product product(String id)throws SQLException{List<Product> l=Q.beans(Product.class,"SELECT p.id,p.name,p.price,p.stock,p.description,p.category,c.name club FROM products p JOIN clubs c ON c.id=p.club_id WHERE p.id=?",id);return l.isEmpty()?null:l.get(0);}
 public List<Order> orders(Object uid)throws SQLException{return Q.beans(Order.class,"SELECT id,total,status,payment_ref,created_at FROM orders WHERE user_id=? ORDER BY id DESC",uid);}
 public Order order(String id,Object uid)throws SQLException{List<Order> l=Q.beans(Order.class,"SELECT id,total,status,payment_ref,created_at FROM orders WHERE id=? AND user_id=?",id,uid);return l.isEmpty()?null:l.get(0);}
 public List<OrderItem> items(String id)throws SQLException{return Q.beans(OrderItem.class,"SELECT p.name,oi.qty,oi.price,oi.qty*oi.price line FROM order_items oi JOIN products p ON p.id=oi.product_id WHERE oi.order_id=?",id);}
 public int place(Object uid,Map<Integer,Integer> cart)throws SQLException{
  try(Connection c=DBUtil.get()){c.setAutoCommit(false);
   try{double total=0;List<Object[]> it=new ArrayList<>();
    for(Map.Entry<Integer,Integer> e:cart.entrySet()){PreparedStatement p=c.prepareStatement("SELECT price,stock FROM products WHERE id=? FOR UPDATE");p.setInt(1,e.getKey());ResultSet rs=p.executeQuery();
     if(!rs.next()||rs.getInt(2)<e.getValue())throw new IllegalStateException("Insufficient stock");total+=rs.getDouble(1)*e.getValue();it.add(new Object[]{e.getKey(),e.getValue(),rs.getDouble(1)});}
    PreparedStatement o=c.prepareStatement("INSERT INTO orders(user_id,total,status,payment_ref) VALUES(?,?,'PAID',?)",Statement.RETURN_GENERATED_KEYS);o.setObject(1,uid);o.setDouble(2,total);o.setString(3,"SIM-"+System.currentTimeMillis());o.executeUpdate();
    ResultSet k=o.getGeneratedKeys();k.next();int oid=k.getInt(1);
    for(Object[] i:it){PreparedStatement p=c.prepareStatement("INSERT INTO order_items(order_id,product_id,qty,price) VALUES(?,?,?,?)");p.setInt(1,oid);p.setObject(2,i[0]);p.setObject(3,i[1]);p.setObject(4,i[2]);p.executeUpdate();
     PreparedStatement s=c.prepareStatement("UPDATE products SET stock=stock-? WHERE id=?");s.setObject(1,i[1]);s.setObject(2,i[0]);s.executeUpdate();}
    c.commit();return oid;
   }catch(SQLException|RuntimeException e){c.rollback();throw e;}}}
}
