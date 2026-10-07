<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="nav.jspf" %>
<main class="wrap event-detail-page">
    <a class="back-link" href="${cp}/events">← All events</a>
    <c:if test="${not empty param.msg}"><p class="alert ${fn:contains(param.msg, 'Registered') || fn:contains(param.msg, 'cancelled') ? 'ok' : 'err'}"><c:out value="${param.msg}"/></p></c:if>

    <section class="hero event-detail-hero">
        <p class="eyebrow">EVENT DETAILS · <c:out value="${ev.club}"/></p>
        <h2><c:out value="${ev.title}"/></h2>
        <span class="badge ${ev.state == 'OPEN' ? 'ok' : ev.state == 'FULL' || ev.state == 'CLOSED' ? 'warn' : ev.state == 'CANCELLED' ? 'err' : ''}"><c:out value="${ev.state}"/></span>
        <p><c:out value="${ev.description}"/></p>
    </section>

    <section class="grid event-detail-stats">
        <div class="stat">Date<b><c:out value="${ev.eventDate}"/></b></div>
        <div class="stat">Time<b><c:out value="${ev.eventTime}"/></b></div>
        <div class="stat">Location<b><c:out value="${ev.location}"/></b></div>
        <div class="stat">Registration deadline<b><c:out value="${ev.regDeadline}"/></b></div>
        <div class="stat">Availability<b id="event-availability" data-event-id="${ev.id}">Checking…</b></div>
        <div class="stat">Registered<b><c:out value="${ev.taken}"/> / <c:out value="${ev.capacity}"/></b></div>
    </section>

    <section class="card event-detail-actions">
        <c:if test="${role == 'STUDENT'}">
            <c:choose>
                <c:when test="${ev.mine > 0}">
                    <p class="alert ok">You are registered for this event.</p>
                    <c:if test="${ev.state != 'CANCELLED' && ev.state != 'COMPLETED'}">
                        <form method="post" action="${cp}/events" class="inline-form" onsubmit="return confirm('Cancel your registration for this event?')">
                            <input type="hidden" name="eventId" value="${ev.id}"><input type="hidden" name="next" value="details">
                            <button type="submit" name="action" value="cancel" class="btn danger-btn">Cancel registration</button>
                        </form>
                        <div class="attendance-box detail-attendance">
                            <label for="detail-attendance-code">Attendance code</label>
                            <input id="detail-attendance-code" type="text" maxlength="10" placeholder="Enter code" autocomplete="off">
                            <button id="detail-verify-attendance" type="button" class="btn">Verify attendance</button>
                            <span id="detail-attendance-result" class="attendance-result" role="status"></span>
                        </div>
                    </c:if>
                </c:when>
                <c:when test="${ev.state == 'OPEN'}">
                    <form method="post" action="${cp}/events" class="inline-form">
                        <input type="hidden" name="eventId" value="${ev.id}"><input type="hidden" name="next" value="details">
                        <button type="submit" name="action" value="register" class="btn">Register for this event</button>
                    </form>
                </c:when>
                <c:otherwise><p class="muted">Registration is not available for this event.</p></c:otherwise>
            </c:choose>
        </c:if>
        <c:if test="${role != 'STUDENT'}"><p class="muted">You are viewing this event as ${role}. Student registration controls are not available for this account.</p></c:if>
    </section>
</main>
<script>
(() => {
    const contextPath = '<c:out value="${cp}"/>';
    const availability = document.getElementById('event-availability');
    if (availability) {
        fetch(contextPath + '/ajax/event-availability?eventId=' + encodeURIComponent(availability.dataset.eventId), { headers: { 'Accept': 'text/plain' } })
            .then(response => { if (!response.ok) throw new Error('Availability request failed'); return response.text(); })
            .then(text => availability.textContent = text)
            .catch(() => availability.textContent = 'Availability unavailable');
    }
    const verifyButton = document.getElementById('detail-verify-attendance');
    if (!verifyButton) return;
    verifyButton.addEventListener('click', async () => {
        const codeInput = document.getElementById('detail-attendance-code');
        const result = document.getElementById('detail-attendance-result');
        const code = codeInput.value.trim();
        if (!/^[A-Za-z0-9]{4,10}$/.test(code)) { result.textContent = 'Enter a 4–10 character alphanumeric code.'; result.className = 'attendance-result err'; return; }
        verifyButton.disabled = true;
        try {
            const response = await fetch(contextPath + '/ajax/verify-code', {
                method: 'POST',
                headers: { 'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8', 'Accept': 'text/plain' },
                body: new URLSearchParams({ eventId: '<c:out value="${ev.id}"/>', code })
            });
            const message = await response.text();
            result.textContent = message || 'No response from server.';
            result.className = 'attendance-result ' + (response.ok && message.startsWith('VALID') ? 'ok' : 'err');
            if (response.ok && message.startsWith('VALID')) verifyButton.textContent = 'Attendance verified';
        } catch (error) { result.textContent = 'Could not contact the server. Please try again.'; result.className = 'attendance-result err'; }
        finally { verifyButton.disabled = false; }
    });
})();
</script>
