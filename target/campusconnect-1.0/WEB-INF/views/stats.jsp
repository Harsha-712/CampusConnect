<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="nav.jspf" %>

<div class="wrap analytics-page">

    <div class="analytics-hero">
        <div>
            <span class="eyebrow">INSIGHTS &amp; REPORTS</span>
            <h1>Analytics Dashboard</h1>
            <p>Track participation, events, clubs and overall campus activity.</p>
        </div>

        <div class="analytics-icon">▥</div>
    </div>

    <c:if test="${not empty param.msg}">
        <div class="ok analytics-message">
            <c:out value="${param.msg}"/>
        </div>
    </c:if>


    <!-- STATISTICS -->

    <div class="analytics-stats">

        <c:forEach items="${stats}" var="s">
            <div class="analytics-stat-card">

                <div class="analytics-stat-label">
                    <c:out value="${s.key}"/>
                </div>

                <div class="analytics-stat-value">
                    <c:out value="${s.value}"/>
                </div>

            </div>
        </c:forEach>

    </div>


    <!-- CHARTS -->

    <c:set var="C" value="${charts}"/>

    <div class="analytics-chart-grid">

        <!-- EVENT ATTENDANCE -->

        <div class="analytics-card">

            <div class="analytics-card-header">
                <div>
                    <span class="analytics-label">EVENT PERFORMANCE</span>
                    <h3>Registrations vs Attendance</h3>
                </div>

                <span class="analytics-card-icon">◷</span>
            </div>

            <c:choose>

                <c:when test="${not empty C.eventChart}">

                    <c:forEach items="${C.eventChart}" var="r">

                        <div class="chart-item">

                            <div class="chart-title">
                                <span>
                                    <c:out value="${r.title}"/>
                                </span>

                                <span>
                                    ${r.regs} registrations
                                </span>
                            </div>

                            <div class="bar">
                                <i data-w="${r.pct}"></i>
                            </div>

                            <div class="chart-title chart-attendance">
                                <span>Attendance</span>

                                <span>
                                    ${r.att} present
                                </span>
                            </div>

                            <div class="bar attendance-bar">
                                <i data-w="${r.pct2}"></i>
                            </div>

                        </div>

                    </c:forEach>

                </c:when>

                <c:otherwise>

                    <div class="empty analytics-empty">
                        No event data available yet.
                    </div>

                </c:otherwise>

            </c:choose>

        </div>


        <!-- CLUB MEMBERS -->

        <div class="analytics-card">

            <div class="analytics-card-header">
                <div>
                    <span class="analytics-label">CLUB ACTIVITY</span>
                    <h3>Members per Club</h3>
                </div>

                <span class="analytics-card-icon">♙</span>
            </div>

            <c:choose>

                <c:when test="${not empty C.clubChart}">

                    <c:forEach items="${C.clubChart}" var="r">

                        <div class="chart-item">

                            <div class="chart-title">
                                <span>
                                    <c:out value="${r.name}"/>
                                </span>

                                <span>
                                    ${r.members} members
                                </span>
                            </div>

                            <div class="bar">
                                <i data-w="${r.pct}"></i>
                            </div>

                        </div>

                    </c:forEach>

                </c:when>

                <c:otherwise>

                    <div class="empty analytics-empty">
                        No club data available yet.
                    </div>

                </c:otherwise>

            </c:choose>

        </div>


        <!-- PRODUCTS -->

        <div class="analytics-card">

            <div class="analytics-card-header">
                <div>
                    <span class="analytics-label">PRODUCT SALES</span>
                    <h3>Units Sold per Product</h3>
                </div>

                <span class="analytics-card-icon">◈</span>
            </div>

            <c:choose>

                <c:when test="${not empty C.productChart}">

                    <c:forEach items="${C.productChart}" var="r">

                        <div class="chart-item">

                            <div class="chart-title">
                                <span>
                                    <c:out value="${r.name}"/>
                                </span>

                                <span>
                                    ${r.sold} sold
                                </span>
                            </div>

                            <div class="bar">
                                <i data-w="${r.pct}"></i>
                            </div>

                        </div>

                    </c:forEach>

                </c:when>

                <c:otherwise>

                    <div class="empty analytics-empty">
                        No product sales data available yet.
                    </div>

                </c:otherwise>

            </c:choose>

        </div>

    </div>


    <!-- CLUB SUMMARY -->

    <div class="analytics-card analytics-table-card">

        <div class="analytics-card-header">

            <div>
                <span class="analytics-label">CLUB OVERVIEW</span>
                <h3>Club Performance</h3>
            </div>

        </div>

        <div class="table-scroll">

            <table class="analytics-table">

                <thead>

                    <tr>
                        <th>Club</th>
                        <th>Members</th>
                        <th>Events</th>
                        <th>Registrations</th>
                    </tr>

                </thead>

                <tbody>

                    <c:choose>

                        <c:when test="${not empty perClub}">

                            <c:forEach items="${perClub}" var="c">

                                <tr>

                                    <td>
                                        <strong>
                                            <c:out value="${c.name}"/>
                                        </strong>
                                    </td>

                                    <td>
                                        <c:out value="${c.members}"/>
                                    </td>

                                    <td>
                                        <c:out value="${c.events}"/>
                                    </td>

                                    <td>
                                        <c:out value="${c.regs}"/>
                                    </td>

                                </tr>

                            </c:forEach>

                        </c:when>

                        <c:otherwise>

                            <tr>
                                <td colspan="4">
                                    <div class="empty">
                                        No club information available.
                                    </div>
                                </td>
                            </tr>

                        </c:otherwise>

                    </c:choose>

                </tbody>

            </table>

        </div>

    </div>


    <!-- ADMIN SECTION -->

    <c:if test="${sessionScope.role == 'ADMIN'}">

        <!-- CREATE CLUB -->

        <div class="analytics-card admin-management-card">

            <div class="analytics-card-header">

                <div>
                    <span class="analytics-label">ADMIN MANAGEMENT</span>
                    <h3>Create New Club</h3>
                    <p>Add a new club and assign its coordinator.</p>
                </div>

                <span class="analytics-card-icon">+</span>

            </div>

            <form method="post" action="stats" class="admin-form">

                <div class="form-field">

                    <label for="cn">Club Name</label>

                    <input
                        name="name"
                        id="cn"
                        placeholder="Enter club name"
                        required
                    >

                </div>

                <div class="form-field">

                    <label>Category</label>

                    <input
                        name="category"
                        placeholder="Technical / Cultural / Sports"
                        required
                    >

                </div>

                <div class="form-field">

                    <label>Description</label>

                    <input
                        name="description"
                        placeholder="Short description"
                    >

                </div>

                <div class="form-field">

                    <label>Coordinator Email</label>

                    <input
                        name="coordEmail"
                        placeholder="coordinator@example.com"
                        required
                    >

                </div>

                <div class="club-check">
                    <span id="cs"></span>
                </div>

                <button type="submit" class="btn">
                    Create Club
                </button>

            </form>

        </div>


        <!-- USER MANAGEMENT -->

        <div class="analytics-card admin-management-card">

            <div class="analytics-card-header">

                <div>
                    <span class="analytics-label">USER MANAGEMENT</span>
                    <h3>Users</h3>
                    <p>View and manage registered CampusConnect users.</p>
                </div>

                <span class="analytics-card-icon">♙</span>

            </div>

            <div class="table-scroll">

                <table class="analytics-table">

                    <thead>

                        <tr>
                            <th>Name</th>
                            <th>Email</th>
                            <th>Role</th>
                            <th>Status</th>
                            <th>Action</th>
                        </tr>

                    </thead>

                    <tbody>

                        <c:choose>

                            <c:when test="${not empty users}">

                                <c:forEach items="${users}" var="u">

                                    <tr>

                                        <td>
                                            <strong>
                                                <c:out value="${u.name}"/>
                                            </strong>
                                        </td>

                                        <td>
                                            <c:out value="${u.email}"/>
                                        </td>

                                        <td>
                                            <span class="analytics-role">
                                                <c:out value="${u.role}"/>
                                            </span>
                                        </td>

                                        <td>

                                            <c:choose>

                                                <c:when test="${u.active}">
                                                    <span class="badge ok">
                                                        Active
                                                    </span>
                                                </c:when>

                                                <c:otherwise>
                                                    <span class="badge warn">
                                                        Inactive
                                                    </span>
                                                </c:otherwise>

                                            </c:choose>

                                        </td>

                                        <td>

                                            <form
                                                method="post"
                                                action="stats"
                                                class="inline-form"
                                            >

                                                <input
                                                    type="hidden"
                                                    name="action"
                                                    value="toggle"
                                                >

                                                <input
                                                    type="hidden"
                                                    name="id"
                                                    value="${u.id}"
                                                >

                                                <button
                                                    type="submit"
                                                    class="btn sm"
                                                >
                                                    Toggle Status
                                                </button>

                                            </form>

                                        </td>

                                    </tr>

                                </c:forEach>

                            </c:when>

                            <c:otherwise>

                                <tr>

                                    <td colspan="5">

                                        <div class="empty">
                                            No users available.
                                        </div>

                                    </td>

                                </tr>

                            </c:otherwise>

                        </c:choose>

                    </tbody>

                </table>

            </div>

        </div>

    </c:if>

</div>


<script>

document.querySelectorAll('.bar i[data-w]').forEach(function(i) {

    var width = i.getAttribute('data-w');

    i.style.width = '0%';

    i.style.transition = 'width 0.8s ease';

    setTimeout(function() {
        i.style.width = width + '%';
    }, 100);

});


var clubName = document.getElementById('cn');
var clubStatus = document.getElementById('cs');

if (clubName && clubStatus) {

    clubName.addEventListener('input', function() {

        var value = clubName.value.trim();

        if (!value) {
            clubStatus.textContent = '';
            return;
        }

        fetch(
            '${cp}/ajax/check-club?name=' +
            encodeURIComponent(value)
        )
        .then(function(response) {
            return response.text();
        })
        .then(function(text) {
            clubStatus.textContent = text;
        })
        .catch(function() {
            clubStatus.textContent = '';
        });

    });

}

</script>