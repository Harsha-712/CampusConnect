package com.campusconnect.service;
import com.campusconnect.dao.CertificateDAO;import com.campusconnect.model.Certificate;import java.sql.SQLException;import java.util.List;
public class CertificateService{
 private final CertificateDAO d=new CertificateDAO();
 public List<Certificate> byUser(Object uid)throws SQLException{return d.byUser(uid);}
 public Certificate find(String id,Object uid)throws SQLException{return d.find(id,uid);}
 public List<Certificate> recent(int n)throws SQLException{return d.recent(n);}
 public int issue(String eventId)throws SQLException{return d.issue(eventId);}
}
