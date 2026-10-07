package com.campusconnect.dao;
import com.campusconnect.model.Certificate;import com.campusconnect.util.Q;import java.sql.SQLException;import java.util.List;
public class CertificateDAO{
 static final String BASE="SELECT ce.id,ce.user_id,ce.event_id,ce.title,ce.issued_on,u.name student_name FROM certificates ce JOIN users u ON u.id=ce.user_id ";
 public List<Certificate> byUser(Object uid)throws SQLException{return Q.beans(Certificate.class,BASE+"WHERE ce.user_id=? ORDER BY ce.id DESC",uid);}
 public Certificate find(String id,Object uid)throws SQLException{List<Certificate> l=Q.beans(Certificate.class,BASE+"WHERE ce.id=? AND ce.user_id=?",id,uid);return l.isEmpty()?null:l.get(0);}
 public List<Certificate> recent(int n)throws SQLException{return Q.beans(Certificate.class,BASE+"ORDER BY ce.id DESC LIMIT "+n);}
 public int issue(String eventId)throws SQLException{return Q.upd("INSERT INTO certificates(user_id,event_id,title,issued_on) SELECT r.user_id,e.id,CONCAT(e.title,' Participation'),CURDATE() FROM event_registrations r JOIN events e ON e.id=r.event_id WHERE e.id=? AND r.attended AND NOT EXISTS(SELECT 1 FROM certificates x WHERE x.user_id=r.user_id AND x.event_id=e.id)",eventId);}
}
