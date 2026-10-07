<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="nav.jspf" %>

<div class="wrap role-dashboard">

    <c:set var="isAdmin" value="${role=='ADMIN'}"/>
    <c:set var="isFaculty" value="${role=='FACULTY_COORDINATOR'}"/>
    <c:set var="isCoordinator" value="${role=='CLUB_COORDINATOR'}"/>

    <!-- HERO -->

    <section class="role-hero">

        <div class="role-hero-content">

            <div class="role-eyebrow">
                <c:choose>
                    <c:when test="${isAdmin}">ADMIN CONTROL CENTER</c:when>
                    <c:when test="${isFaculty}">FACULTY COORDINATION</c:when>
                    <c:otherwise>CLUB COORDINATION</c:otherwise>
                </c:choose>
            </div>

            <h1>
                <c:choose>
                    <c:when test="${isAdmin}">
                        Welcome, Admin
                    </c:when>

                    <c:when test="${isFaculty}">
                        Welcome, Faculty Coordinator
                    </c:when>

                    <c:otherwise>
                        Welcome, Coordinator
                    </c:otherwise>
                </c:choose>
            </h1>

            <p>
                <c:choose>
                    <c:when test="${isAdmin}">
                        Monitor CampusConnect and manage users, clubs, events and platform activities.
                    </c:when>

                    <c:when test="${isFaculty}">
                        Review activities, monitor student participation and oversee campus events.
                    </c:when>

                    <c:otherwise>
                        Manage your club activities, members, events and attendance from one place.
                    </c:otherwise>
                </c:choose>
            </p>

            <div class="role-user">
                <span class="role-user-icon">&#128100;</span>
                <span>
                    Signed in as
                    <strong><c:out value="${sessionScope.username}"/></strong>
                </span>
            </div>

        </div>

        <div class="role-hero-icon">
            <c:choose>
                <c:when test="${isAdmin}">&#9881;</c:when>
                <c:when test="${isFaculty}">&#127891;</c:when>
                <c:otherwise>&#9733;</c:otherwise>
            </c:choose>
        </div>

    </section>


    <!-- STATISTICS -->

    <section class="dashboard-section">

        <div class="section-heading">

            <div>
                <p class="section-eyebrow">OVERVIEW</p>

                <h2>
                    <c:choose>
                        <c:when test="${isAdmin}">System Overview</c:when>
                        <c:when test="${isFaculty}">Activity Overview</c:when>
                        <c:otherwise>Club Overview</c:otherwise>
                    </c:choose>
                </h2>
            </div>

            <a class="dashboard-outline-btn"
               href="${cp}/${role=='ADMIN'?'admin':role=='FACULTY_COORDINATOR'?'faculty':'coordinator'}/stats">
                View Analytics
            </a>

        </div>


        <div class="role-stat-grid">

            <c:forEach items="${stats}" var="s">

                <div class="role-stat-card">

                    <div class="role-stat-top">

                        <span class="role-stat-label">
                            <c:out value="${s.key}"/>
                        </span>

                        <span class="role-stat-icon">
                            &#9670;
                        </span>

                    </div>

                    <div class="role-stat-value">
                        <c:out value="${s.value}"/>
                    </div>

                </div>

            </c:forEach>

        </div>

    </section>


    <!-- QUICK ACTIONS -->

    <section class="dashboard-section">

        <div class="section-heading">

            <div>
                <p class="section-eyebrow">SHORTCUTS</p>
                <h2>Quick Actions</h2>
            </div>

        </div>


        <div class="quick-action-grid">


            <!-- COORDINATOR -->

            <c:if test="${isCoordinator}">

                <a class="quick-action-card"
                   href="${cp}/coordinator/manage">

                    <div class="quick-action-icon purple">
                        &#9881;
                    </div>

                    <div>
                        <h3>Manage Activities</h3>
                        <p>
                            Manage your club members, events and certificates.
                        </p>
                    </div>

                    <span class="quick-arrow">&#8594;</span>

                </a>


                <a class="quick-action-card"
                   href="${cp}/coordinator/proposals">

                    <div class="quick-action-icon blue">
                        &#128221;
                    </div>

                    <div>
                        <h3>Submit Activity</h3>
                        <p>
                            Submit club activities for faculty approval.
                        </p>
                    </div>

                    <span class="quick-arrow">&#8594;</span>

                </a>

            </c:if>


            <!-- FACULTY -->

            <c:if test="${isFaculty}">

                <a class="quick-action-card"
                   href="${cp}/faculty/review">

                    <div class="quick-action-icon purple">
                        &#128203;
                    </div>

                    <div>
                        <h3>Review Activities</h3>
                        <p>
                            Review and approve submitted club activities.
                        </p>
                    </div>

                    <span class="quick-arrow">&#8594;</span>

                </a>


                <a class="quick-action-card"
                   href="${cp}/faculty/stats">

                    <div class="quick-action-icon blue">
                        &#128202;
                    </div>

                    <div>
                        <h3>View Reports</h3>
                        <p>
                            Analyse participation and campus activities.
                        </p>
                    </div>

                    <span class="quick-arrow">&#8594;</span>

                </a>

            </c:if>


            <!-- ADMIN -->

            <c:if test="${isAdmin}">

                <a class="quick-action-card"
                   href="${cp}/admin/users">

                    <div class="quick-action-icon purple">
                        &#128101;
                    </div>

                    <div>
                        <h3>Manage Users</h3>
                        <p>
                            Manage students, coordinators and faculty accounts.
                        </p>
                    </div>

                    <span class="quick-arrow">&#8594;</span>

                </a>


                <a class="quick-action-card"
                   href="${cp}/admin/products">

                    <div class="quick-action-icon blue">
                        &#128722;
                    </div>

                    <div>
                        <h3>Manage Products</h3>
                        <p>
                            Manage products available in the campus shop.
                        </p>
                    </div>

                    <span class="quick-arrow">&#8594;</span>

                </a>

            </c:if>


            <!-- COMMON -->

            <a class="quick-action-card"
               href="${cp}/events">

                <div class="quick-action-icon green">
                    &#128197;
                </div>

                <div>
                    <h3>Campus Events</h3>
                    <p>
                        View upcoming and ongoing campus events.
                    </p>
                </div>

                <span class="quick-arrow">&#8594;</span>

            </a>


            <a class="quick-action-card"
               href="${cp}/${role=='ADMIN'?'admin':role=='FACULTY_COORDINATOR'?'faculty':'coordinator'}/stats">

                <div class="quick-action-icon orange">
                    &#128200;
                </div>

                <div>
                    <h3>Analytics</h3>
                    <p>
                        View detailed statistics and performance data.
                    </p>
                </div>

                <span class="quick-arrow">&#8594;</span>

            </a>

        </div>

    </section>


    <!-- ROLE INFORMATION -->

    <section class="role-info-card">

        <div class="role-info-icon">
            <c:choose>
                <c:when test="${isAdmin}">&#9881;</c:when>
                <c:when test="${isFaculty}">&#127891;</c:when>
                <c:otherwise>&#9733;</c:otherwise>
            </c:choose>
        </div>

        <div>

            <p class="section-eyebrow">YOUR ROLE</p>

            <h3>
                <c:choose>
                    <c:when test="${isAdmin}">
                        System Administrator
                    </c:when>

                    <c:when test="${isFaculty}">
                        Faculty Coordinator
                    </c:when>

                    <c:otherwise>
                        Club Coordinator
                    </c:otherwise>
                </c:choose>
            </h3>

            <p>
                <c:choose>
                    <c:when test="${isAdmin}">
                        You have access to platform-level management and administrative functions.
                    </c:when>

                    <c:when test="${isFaculty}">
                        You can review club activities and monitor student participation.
                    </c:when>

                    <c:otherwise>
                        You can manage your club activities, members and event participation.
                    </c:otherwise>
                </c:choose>
            </p>

        </div>

    </section>

</div>