package com.campusconnect.util;
import java.security.MessageDigest;import java.sql.*;
public class DBUtil{
 public static Connection get() throws SQLException{
  try{Class.forName("com.mysql.cj.jdbc.Driver");}catch(ClassNotFoundException e){throw new SQLException(e);}
  String u=System.getenv().getOrDefault("DB_URL","jdbc:mysql://localhost:3306/campusconnect");
  return DriverManager.getConnection(u,System.getenv().getOrDefault("DB_USER","root"),System.getenv().getOrDefault("DB_PASS","root"));}
 public static String sha256(String s){try{StringBuilder sb=new StringBuilder();for(byte b:MessageDigest.getInstance("SHA-256").digest(s.getBytes("UTF-8")))sb.append(String.format("%02x",b));return sb.toString();}catch(Exception e){throw new RuntimeException(e);}}
}
