package com.campusconnect.controller;
import com.campusconnect.util.DBUtil;import jakarta.servlet.annotation.WebServlet;import jakarta.servlet.http.*;import java.io.*;import java.sql.*;
@WebServlet("/xml/clubs")
public class XmlClubsServlet extends HttpServlet{
 private static String x(String s){return s==null?"":s.replace("&","&amp;").replace("<","&lt;").replace(">","&gt;");}
 protected void doGet(HttpServletRequest q,HttpServletResponse r)throws IOException{
  r.setContentType("application/xml;charset=UTF-8");PrintWriter w=r.getWriter();w.print("<?xml version=\"1.0\" encoding=\"UTF-8\"?><clubs>");
  try(Connection c=DBUtil.get();ResultSet rs=c.createStatement().executeQuery("SELECT c.id,c.name,c.category,(SELECT COUNT(*) FROM club_members m WHERE m.club_id=c.id) n FROM clubs c WHERE c.active")){
   while(rs.next())w.print("<club><id>"+rs.getInt(1)+"</id><name>"+x(rs.getString(2))+"</name><category>"+x(rs.getString(3))+"</category><members>"+rs.getInt(4)+"</members></club>");
  }catch(SQLException e){}w.print("</clubs>");}
}
