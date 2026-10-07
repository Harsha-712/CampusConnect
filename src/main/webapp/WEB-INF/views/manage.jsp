<%@ page contentType="text/html;charset=UTF-8" %><%@ include file="nav.jspf" %>
<div class="wrap"><h2>Club Management</h2><p class="ok"><c:out value="${param.msg}"/></p>
<div class="card"><h3>Pending membership requests</h3><table><c:forEach items="${pending}" var="p"><tr><td><c:out value="${p.name}"/></td><td><c:out value="${p.club}"/></td><td>
<form method="post" action="manage"><input type="hidden" name="id" value="${p.id}"><button name="action" value="approve">Approve</button> <button name="action" value="reject">Reject</button></form></td></tr></c:forEach></table></div>
<div class="card"><h3>Announcement</h3><form method="post" action="manage"><input type="hidden" name="action" value="announce"><select name="clubId"><c:forEach items="${clubs}" var="c"><option value="${c.id}"><c:out value="${c.name}"/></option></c:forEach></select>
<input name="title" placeholder="Title" required><br><textarea name="body" id="b" maxlength="500" oninput="n.textContent=this.value.length+'/500'"></textarea> <span id="n">0/500</span><br><button>Post</button></form></div>
<div class="card"><h3>Events, attendance &amp; certificates</h3><table><tr><th>Event</th><th>Code</th><th>Registered</th><th>Attended</th><th></th></tr><c:forEach items="${events}" var="e"><tr><td><c:out value="${e.title}"/></td><td>${e.code}</td><td>${e.regs}</td><td>${e.att}</td>
<td><form method="post" action="manage"><input type="hidden" name="id" value="${e.id}"><button name="action" value="certify">Issue certificates</button></form></td></tr></c:forEach></table></div></div>

