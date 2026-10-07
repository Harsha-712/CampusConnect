<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="nav.jspf" %>

<main class="wrap events-page">
    <div class="page-heading">
        <div>
            <p class="eyebrow">CAMPUS CONNECT</p>
            <h2>Campus events</h2>
            <p class="muted">Discover activities, manage your registration, and record your attendance.</p>
        </div>
        <c:if test="${role == 'CLUB_COORDINATOR' || role == 'ADMIN'}">
            <a class="btn" href="${cp}/coordinator/events">Manage events</a>
        </c:if>
    </div>

    <c:if test="${not empty param.msg}"><p class="alert ${fn:contains(param.msg, 'Registered') || fn:contains(param.msg, 'cancelled') || fn:contains(param.msg, 'created') ? 'ok' : 'err'}"><c:out value="${param.msg}"/></p></c:if>

    <c:if test="${not empty ann}">
        <section class="card announcements-card">
            <h3>📢 Latest announcements</h3>
            <c:forEach items="${ann}" var="a">
                <article class="announcement-item">
                    <div><strong><c:out value="${a.title}"/></strong> <span class="badge"><c:out value="${empty a.club ? 'General' : a.club}"/></span></div>
                    <p><c:out value="${a.body}"/></p>
                </article>
            </c:forEach>
        </section>
    </c:if>

    <section class="card event-filters" aria-label="Filter events">
        <label class="search-field" for="event-search">Search events
            <input id="event-search" type="search" placeholder="Search by event, club, or location" autocomplete="off">
        </label>
        <label for="event-status">Status
            <select id="event-status">
                <option value="">All statuses</option><option value="OPEN">Open</option><option value="FULL">Full</option>
                <option value="CLOSED">Registration closed</option><option value="COMPLETED">Completed</option><option value="CANCELLED">Cancelled</option>
            </select>
        </label>
        <label for="event-club">Club
            <select id="event-club"><option value="">All clubs</option></select>
        </label>
    </section>

    <section class="event-list" id="event-list" aria-live="polite">
        <c:forEach items="${events}" var="e">
            <article class="card event-card" data-status="${e.state}" data-club="<c:out value='${e.club}'/>" data-search="<c:out value='${e.title} ${e.club} ${e.location}'/>" data-event-id="${e.id}">
                <div class="event-card-head">
                    <div>
                        <span class="eyebrow"><c:out value="${e.club}"/></span>
                        <h3><a href="${cp}/events/details?id=${e.id}"><c:out value="${e.title}"/></a></h3>
                    </div>
                    <span class="badge ${e.state == 'OPEN' ? 'ok' : e.state == 'FULL' || e.state == 'CLOSED' ? 'warn' : e.state == 'CANCELLED' ? 'err' : ''}"><c:out value="${e.state}"/></span>
                </div>
                <c:if test="${not empty e.description}"><p class="event-description"><c:out value="${e.description}"/></p></c:if>
                <div class="event-meta">
                    <span>📅 <c:out value="${e.eventDate}"/> <c:out value="${e.eventTime}"/></span>
                    <span>📍 <c:out value="${e.location}"/></span>
                    <span>🪑 <b class="availability" data-event-id="${e.id}">Checking seats…</b></span>
                    <span>🕒 Register by <c:out value="${e.regDeadline}"/></span>
                </div>

                <div class="event-actions">
                    <a class="btn sm ghost" href="${cp}/events/details?id=${e.id}">View details</a>
                    <c:if test="${role == 'STUDENT'}">
                        <c:choose>
                            <c:when test="${e.mine > 0}">
                                <span class="badge ok">You are registered</span>
                                <c:if test="${e.state != 'CANCELLED' && e.state != 'COMPLETED'}">
                                    <form method="post" action="${cp}/events" class="inline-form" onsubmit="return confirm('Cancel your registration for this event?')">
                                        <input type="hidden" name="eventId" value="${e.id}">
                                        <button type="submit" name="action" value="cancel" class="btn sm danger-btn">Cancel registration</button>
                                    </form>
                                </c:if>
                                <c:if test="${e.state != 'CANCELLED' && e.state != 'COMPLETED'}">
                                    <div class="attendance-box">
                                        <label for="attendance-code-${e.id}">Attendance code</label>
                                        <input id="attendance-code-${e.id}" type="text" maxlength="10" placeholder="Enter code" autocomplete="off">
                                        <button type="button" class="btn sm verify-attendance" data-event-id="${e.id}">Verify attendance</button>
                                        <span class="attendance-result" id="attendance-result-${e.id}" role="status"></span>
                                    </div>
                                </c:if>
                            </c:when>
                            <c:when test="${e.state == 'OPEN'}">
                                <form method="post" action="${cp}/events" class="inline-form">
                                    <input type="hidden" name="eventId" value="${e.id}">
                                    <button type="submit" name="action" value="register" class="btn sm">Register</button>
                                </form>
                            </c:when>
                            <c:otherwise><span class="muted">Registration unavailable</span></c:otherwise>
                        </c:choose>
                    </c:if>
                </div>
            </article>
        </c:forEach>
        <c:if test="${empty events}"><div class="card empty">No events have been published yet.</div></c:if>
        <div id="no-matching-events" class="card empty" hidden>No events match your filters.</div>
    </section>
</main>

<script>
(() => {
    const contextPath = '<c:out value="${cp}"/>';
    const searchInput = document.getElementById('event-search');
    const statusSelect = document.getElementById('event-status');
    const clubSelect = document.getElementById('event-club');
    const eventCards = Array.from(document.querySelectorAll('.event-card'));
    const noMatches = document.getElementById('no-matching-events');

    const clubs = [...new Set(eventCards.map(card => card.dataset.club).filter(Boolean))].sort((a, b) => a.localeCompare(b));
    clubs.forEach(club => clubSelect.add(new Option(club, club)));

    function filterEvents() {
        const query = searchInput.value.trim().toLocaleLowerCase();
        let visibleCount = 0;
        eventCards.forEach(card => {
            const matches = (!query || card.dataset.search.toLocaleLowerCase().includes(query))
                && (!statusSelect.value || card.dataset.status === statusSelect.value)
                && (!clubSelect.value || card.dataset.club === clubSelect.value);
            card.hidden = !matches;
            if (matches) visibleCount++;
        });
        noMatches.hidden = visibleCount !== 0 || eventCards.length === 0;
    }

    searchInput.addEventListener('input', filterEvents);
    statusSelect.addEventListener('change', filterEvents);
    clubSelect.addEventListener('change', filterEvents);

    document.querySelectorAll('.availability').forEach(async element => {
        try {
            const response = await fetch(contextPath + '/ajax/event-availability?eventId=' + encodeURIComponent(element.dataset.eventId), { headers: { 'Accept': 'text/plain' } });
            if (!response.ok) throw new Error('Availability request failed');
            element.textContent = await response.text();
        } catch (error) {
            element.textContent = 'Availability unavailable';
        }
    });

    document.querySelectorAll('.verify-attendance').forEach(button => {
        button.addEventListener('click', async () => {
            const eventId = button.dataset.eventId;
            const codeInput = document.getElementById('attendance-code-' + eventId);
            const result = document.getElementById('attendance-result-' + eventId);
            const code = codeInput.value.trim();
            if (!/^[A-Za-z0-9]{4,10}$/.test(code)) {
                result.textContent = 'Enter a 4–10 character alphanumeric code.';
                result.className = 'attendance-result err';
                return;
            }
            button.disabled = true;
            result.textContent = 'Verifying…';
            result.className = 'attendance-result';
            try {
                const response = await fetch(contextPath + '/ajax/verify-code', {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8', 'Accept': 'text/plain' },
                    body: new URLSearchParams({ eventId, code })
                });
                const message = await response.text();
                result.textContent = message || (response.ok ? 'No response from server.' : 'Verification failed.');
                result.className = 'attendance-result ' + (response.ok && message.startsWith('VALID') ? 'ok' : 'err');
                if (response.ok && message.startsWith('VALID')) button.textContent = 'Attendance verified';
            } catch (error) {
                result.textContent = 'Could not contact the server. Please try again.';
                result.className = 'attendance-result err';
            } finally {
                button.disabled = false;
            }
        });
    });
})();
</script>
