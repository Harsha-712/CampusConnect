<%@ page contentType="text/html;charset=UTF-8" %><%@ include file="nav.jspf" %>
<div class="wrap"><h2>Manage Clubs</h2><p class="ok"><c:out value="${param.msg}"/></p>
<c:forEach items="${clubs}" var="c"><div class="card"><form method="post" action="${cp}/coordinator/clubs"><input type="hidden" name="id" value="${c.id}"><input name="name" value="<c:out value='${c.name}'/>" required minlength="2">
<select name="category"><c:forEach items="${cats}" var="k"><option ${k==c.category?'selected':''}>${k}</option></c:forEach></select><select name="active"><option value="1" ${c.active?'selected':''}>Active</option><option value="0" ${c.active?'':'selected'}>Inactive</option></select>
<input name="description" value="<c:out value='${c.description}'/>" style="width:300px"> <small>${c.members} members</small> <button class="btn sm">Save</button></form></div></c:forEach><c:if test="${empty clubs}"><div class="card empty">No clubs assigned</div></c:if></div>
