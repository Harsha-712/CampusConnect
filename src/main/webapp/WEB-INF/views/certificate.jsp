<%@ page contentType="text/html;charset=UTF-8" %>
<% request.setAttribute("bare",true); %>
<%@ include file="nav.jspf" %>

<div class="certificate-page">

```
<div class="certificate-paper">

    <div class="certificate-top">
        <div class="certificate-logo">&#127891;</div>
        <div class="certificate-brand">CAMPUSCONNECT</div>
        <div class="certificate-label">STUDENT ACHIEVEMENT</div>
    </div>

    <div class="certificate-divider"></div>

    <div class="certificate-content">

        <p class="certificate-eyebrow">CERTIFICATE OF PARTICIPATION</p>

        <h1>Certificate of Participation</h1>

        <p class="certificate-intro">
            This certificate is proudly presented to
        </p>

        <h2 class="certificate-student">
            <c:out value="${cert.studentName}"/>
        </h2>

        <div class="certificate-line"></div>

        <p class="certificate-description">
            for successfully participating in
        </p>

        <h3 class="certificate-event">
            <c:out value="${cert.title}"/>
        </h3>

        <p class="certificate-recognition">
            Your active participation and contribution are
            sincerely appreciated.
        </p>

    </div>

    <div class="certificate-footer">

        <div class="certificate-signature">
            <div class="signature-line"></div>
            <strong>CampusConnect</strong>
            <span>Student Activities</span>
        </div>

        <div class="certificate-seal">
            <div class="seal-inner">
                <span>&#10003;</span>
                <small>VERIFIED</small>
            </div>
        </div>

        <div class="certificate-issued">
            <div class="issued-line"></div>
            <strong>Issued On</strong>
            <span>${cert.issuedOn}</span>
        </div>

    </div>

    <div class="certificate-bottom">
        <span>CampusConnect Student Activity Platform</span>
        <span>Official Participation Certificate</span>
    </div>

</div>

<div class="certificate-actions noprint">
    <button class="btn" type="button" onclick="window.print()">
        &#128438; Print Certificate
    </button>

    <a class="btn ghost"
       href="${cp}/student/certificate-pdf?id=${param.id}">
        &#128190; Download PDF
    </a>

    <a class="btn ghost"
       href="${cp}/student/dashboard">
        &#8592; Back to Dashboard
    </a>
</div>
```

</div>
