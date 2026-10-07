<%@ include file="/WEB-INF/views/nav.jspf" %><%@ page contentType="text/html;charset=UTF-8" %>
<div class="wrap"><div class="card"><h2>Clubs (XML → JavaScript → HTML table)</h2><table id="t"><thead><tr><th>Club ID</th><th>Club Name</th><th>Category</th><th>Members</th></tr></thead><tbody></tbody></table></div></div>
<script>
fetch('xml/clubs').then(r=>r.text()).then(s=>{const d=new DOMParser().parseFromString(s,'application/xml'),b=document.querySelector('#t tbody');
 d.querySelectorAll('club').forEach(c=>{const tr=b.insertRow();['id','name','category','members'].forEach(k=>tr.insertCell().textContent=c.querySelector(k).textContent)})});
</script>
