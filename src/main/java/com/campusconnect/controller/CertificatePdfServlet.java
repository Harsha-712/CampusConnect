package com.campusconnect.controller;
import com.campusconnect.model.Certificate;import com.campusconnect.service.CertificateService;import com.lowagie.text.*;import com.lowagie.text.pdf.PdfWriter;
import jakarta.servlet.annotation.WebServlet;import jakarta.servlet.http.*;import java.io.IOException;import java.sql.SQLException;
@WebServlet("/student/certificate-pdf")
public class CertificatePdfServlet extends HttpServlet{
 protected void doGet(HttpServletRequest q,HttpServletResponse r)throws IOException{
  Certificate c;try{c=new CertificateService().find(q.getParameter("id"),q.getSession().getAttribute("userId"));}catch(SQLException e){r.sendError(500);return;}
  if(c==null){r.sendError(404);return;}
  r.setContentType("application/pdf");r.setHeader("Content-Disposition","attachment; filename=\"Certificate_"+c.getId()+".pdf\"");
  Document d=new Document(PageSize.A4.rotate(),50,50,60,50);
  try{PdfWriter.getInstance(d,r.getOutputStream());d.open();
   Font big=FontFactory.getFont(FontFactory.HELVETICA_BOLD,30),mid=FontFactory.getFont(FontFactory.HELVETICA_BOLD,22),sm=FontFactory.getFont(FontFactory.HELVETICA,15);
   String[] t={"CampusConnect","Certificate of Participation","This is to certify that",c.getStudentName(),"has successfully participated in",c.getTitle(),"Issued on "+c.getIssuedOn()};
   Font[] f={sm,big,sm,mid,sm,mid,sm};
   for(int i=0;i<t.length;i++){Paragraph p=new Paragraph(t[i],f[i]);p.setAlignment(Element.ALIGN_CENTER);p.setSpacingAfter(18);d.add(p);}
  }catch(DocumentException e){throw new IOException(e);}finally{if(d.isOpen())d.close();}}
}
