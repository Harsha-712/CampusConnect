package com.campusconnect.service;
import com.campusconnect.dao.ProposalDAO;import com.campusconnect.model.ActivityProposal;import java.sql.SQLException;import java.util.List;
public class ProposalService{
 private final ProposalDAO d=new ProposalDAO();
 public List<ActivityProposal> all()throws SQLException{return d.all();}
 public List<ActivityProposal> forCoordinator(Object uid,int adm)throws SQLException{return d.forCoordinator(uid,adm);}
 public void submit(String club,String title,String desc)throws SQLException{if(title!=null&&title.trim().length()>=3)d.insert(club,title.trim(),desc);}
 public void decide(String id,boolean approve)throws SQLException{d.setStatus(id,approve?"APPROVED":"REJECTED");}
}
