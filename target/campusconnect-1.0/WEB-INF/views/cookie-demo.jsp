<%@ page contentType="text/html;charset=UTF-8" %><%@ include file="nav.jspf" %>
<div class="wrap"><div class="card"><h2>Cookie Demo</h2>
<p><a href="cookie-demo?action=set&theme=dark">Create cookie (theme=dark)</a> | <a href="cookie-demo?action=set&theme=light">theme=light</a> | <a href="cookie-demo?action=delete">Delete cookie</a></p>
<h3>Cookies read from request</h3><table><tr><th>Name</th><th>Value</th></tr><c:forEach items="${pageContext.request.cookies}" var="ck"><tr><td><c:out value="${ck.name}"/></td><td><c:out value="${ck.value}"/></td></tr></c:forEach></table></div></div>
