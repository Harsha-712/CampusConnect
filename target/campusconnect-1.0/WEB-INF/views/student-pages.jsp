<%@ page contentType="text/html;charset=UTF-8" %><%@ include file="nav.jspf" %>
<div class="wrap"><h2>${title}</h2><c:if test="${empty items}"><div class="card empty">Nothing here yet.</div></c:if>
<c:forEach items="${items}" var="i"><div class="card"><c:choose>
<c:when test="${view=='memberships'}"><b><c:out value="${i.name}"/></b> <small><c:out value="${i.category}"/></small> <span class="badge ${i.status=='APPROVED'?'ok':i.status=='PENDING'?'warn':'err'}">${i.status}</span></c:when>
<c:when test="${view=='certificates'}">🏅 <b><c:out value="${i.title}"/></b> <small>Issued ${i.issuedOn}</small> <span class="badge ok">VERIFIED</span> <a class="btn sm" href="${cp}/student/certificate?id=${i.id}" target="_blank">View</a> <a class="btn sm ghost" href="${cp}/student/certificate-pdf?id=${i.id}">Download PDF</a></c:when>
<c:otherwise><b><c:out value="${i.title}"/></b> <span class="badge"><c:out value="${empty i.club?'General':i.club}"/></span> <small>${i.created_at}</small><br><c:out value="${i.body}"/></c:otherwise></c:choose></div></c:forEach></div>
