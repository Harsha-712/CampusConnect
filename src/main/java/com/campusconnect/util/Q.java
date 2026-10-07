package com.campusconnect.util;
import java.lang.reflect.Method;import java.sql.*;import java.util.*;
public class Q{
 public static List<Map<String,Object>> rows(String sql,Object... a)throws SQLException{
  try(Connection c=DBUtil.get();PreparedStatement p=c.prepareStatement(sql)){for(int i=0;i<a.length;i++)p.setObject(i+1,a[i]);
   ResultSet r=p.executeQuery();ResultSetMetaData m=r.getMetaData();List<Map<String,Object>> l=new ArrayList<>();
   while(r.next()){Map<String,Object> x=new LinkedHashMap<>();for(int i=1;i<=m.getColumnCount();i++)x.put(m.getColumnLabel(i),r.getObject(i));l.add(x);}return l;}}
 public static int upd(String sql,Object... a)throws SQLException{
  try(Connection c=DBUtil.get();PreparedStatement p=c.prepareStatement(sql)){for(int i=0;i<a.length;i++)p.setObject(i+1,a[i]);return p.executeUpdate();}}

 /** Maps each row to a JavaBean; column labels (snake_case or camelCase) match setter names. */
 public static <T> List<T> beans(Class<T> c,String sql,Object... a)throws SQLException{
  try(Connection cn=DBUtil.get();PreparedStatement p=cn.prepareStatement(sql)){for(int i=0;i<a.length;i++)p.setObject(i+1,a[i]);
   ResultSet r=p.executeQuery();ResultSetMetaData m=r.getMetaData();List<T> l=new ArrayList<>();
   while(r.next()){T o=c.getDeclaredConstructor().newInstance();
    for(int i=1;i<=m.getColumnCount();i++){Object v=r.getObject(i);if(v==null)continue;String n=camel(m.getColumnLabel(i)),set="set"+Character.toUpperCase(n.charAt(0))+n.substring(1);
     for(Method s:c.getMethods())if(s.getName().equals(set)&&s.getParameterCount()==1){s.invoke(o,conv(v,s.getParameterTypes()[0]));break;}}
    l.add(o);}
   return l;}catch(ReflectiveOperationException e){throw new SQLException(e);}}
 static String camel(String s){StringBuilder b=new StringBuilder();boolean u=false;for(char ch:s.toCharArray()){if(ch=='_')u=true;else{b.append(u?Character.toUpperCase(ch):ch);u=false;}}return b.toString();}
 static Object conv(Object v,Class<?> t){if(t==int.class)return ((Number)v).intValue();if(t==double.class)return ((Number)v).doubleValue();
  if(t==boolean.class)return v instanceof Boolean?v:(Object)(((Number)v).intValue()!=0);if(t==String.class)return v.toString();
  if(t==Timestamp.class&&v instanceof java.time.LocalDateTime)return Timestamp.valueOf((java.time.LocalDateTime)v);return v;}
}
