package com.campusconnect.dao;
import com.campusconnect.model.ActivityProposal;import com.campusconnect.util.Q;import java.sql.SQLException;import java.util.List;
public class ProposalDAO{
 static final String B="SELECT p.id,p.title,p.description,p.status,p.created_at,c.name club FROM activity_proposals p JOIN clubs c ON c.id=p.club_id ";
 public List<ActivityProposal> all()throws SQLException{return Q.beans(ActivityProposal.class,B+"ORDER BY p.status='PENDING' DESC,p.id DESC");}
 public List<ActivityProposal> forCoordinator(Object uid,int adm)throws SQLException{return Q.beans(ActivityProposal.class,B+"WHERE c.coordinator_id=? OR 1=? ORDER BY p.id DESC",uid,adm);}
 public void insert(String club,String t,String d)throws SQLException{Q.upd("INSERT INTO activity_proposals(club_id,title,description) VALUES(?,?,?)",club,t,d);}
 public void setStatus(String id,String st)throws SQLException{Q.upd("UPDATE activity_proposals SET status=? WHERE id=?",st,id);}
}
