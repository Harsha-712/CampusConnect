```jsp
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="nav.jspf" %>

<div class="wrap student-dashboard">

    <!-- =====================================================
         WELCOME SECTION
         ===================================================== -->

    <section class="student-welcome">

        <div class="welcome-main">

            <div class="welcome-avatar">
                &#128100;
            </div>

            <div>

                <p class="eyebrow">
                    STUDENT ACTIVITY PASSPORT
                </p>

                <h2>
                    Welcome back,
                    <c:out value="${sessionScope.username}"/>
                    &#128075;
                </h2>

                <p class="welcome-text">
                    Keep track of your campus activities, events,
                    achievements and volunteering journey.
                </p>

            </div>

        </div>


        <div class="welcome-action">

            <a class="btn" href="${cp}/clubs">
                Explore Clubs
            </a>

            <a class="btn ghost" href="${cp}/events">
                Browse Events
            </a>

        </div>

    </section>


    <!-- =====================================================
         CLUB MEMBERSHIPS
         ===================================================== -->

    <section class="student-membership">

        <div class="membership-title">

            <span class="membership-icon">
                &#128101;
            </span>

            <div>

                <strong>Your Communities</strong>

                <small>
                    Clubs you're currently part of
                </small>

            </div>

        </div>


        <div class="membership-list">

            <c:forEach items="${clubs}" var="c">

                <span class="membership-pill">
                    <span>&#9679;</span>
                    <c:out value="${c}"/>
                </span>

            </c:forEach>


            <c:if test="${empty clubs}">

                <span class="membership-empty">
                    You haven't joined any clubs yet.
                    <a href="${cp}/clubs">Explore clubs</a>
                </span>

            </c:if>

        </div>

    </section>


    <!-- =====================================================
         STATISTICS
         ===================================================== -->

    <section class="student-overview">

        <div class="dashboard-title-row">

            <div>

                <p class="eyebrow">
                    YOUR PROGRESS
                </p>

                <h3>
                    Activity Overview
                </h3>

            </div>

            <span class="overview-label">
                CAMPUS ENGAGEMENT
            </span>

        </div>


        <div class="student-stat-grid">

            <div class="student-stat">

                <div class="stat-icon">
                    &#128101;
                </div>

                <div class="stat-info">

                    <span>
                        Clubs Joined
                    </span>

                    <strong>
                        ${fn:length(clubs)}
                    </strong>

                </div>

            </div>


            <div class="student-stat">

                <div class="stat-icon">
                    &#128197;
                </div>

                <div class="stat-info">

                    <span>
                        Upcoming Events
                    </span>

                    <strong>
                        ${fn:length(upcoming)}
                    </strong>

                </div>

            </div>


            <div class="student-stat">

                <div class="stat-icon">
                    &#9989;
                </div>

                <div class="stat-info">

                    <span>
                        Events Attended
                    </span>

                    <strong>
                        ${pp.EVENT_PARTICIPATED}
                    </strong>

                </div>

            </div>


            <div class="student-stat">

                <div class="stat-icon">
                    &#128218;
                </div>

                <div class="stat-info">

                    <span>
                        Workshops
                    </span>

                    <strong>
                        ${pp.WORKSHOP}
                    </strong>

                </div>

            </div>


            <div class="student-stat">

                <div class="stat-icon">
                    &#127908;
                </div>

                <div class="stat-info">

                    <span>
                        Events Organized
                    </span>

                    <strong>
                        ${pp.EVENT_ORGANIZED}
                    </strong>

                </div>

            </div>


            <div class="student-stat">

                <div class="stat-icon">
                    &#128220;
                </div>

                <div class="stat-info">

                    <span>
                        Certificates
                    </span>

                    <strong>
                        ${pp.CERTS}
                    </strong>

                </div>

            </div>


            <div class="student-stat">

                <div class="stat-icon">
                    &#129309;
                </div>

                <div class="stat-info">

                    <span>
                        Volunteer Hours
                    </span>

                    <strong>
                        ${pp.HOURS}
                    </strong>

                </div>

            </div>

        </div>

    </section>


    <!-- =====================================================
         MAIN DASHBOARD AREA
         ===================================================== -->

    <section class="student-main-grid">


        <!-- =================================================
             ACTIVITY TIMELINE
             ================================================= -->

        <div class="card student-activity-card">

            <div class="dashboard-card-header">

                <div>

                    <p class="eyebrow">
                        RECENT
                    </p>

                    <h3>
                        Activity Timeline
                    </h3>

                </div>

                <span class="card-header-icon">
                    &#128197;
                </span>

            </div>


            <c:if test="${not empty recent}">

                <div class="activity-timeline">

                    <c:forEach items="${recent}" var="a">

                        <div class="activity-item">

                            <div class="activity-line">

                                <span class="activity-dot"></span>

                            </div>


                            <div class="activity-details">

                                <div class="activity-top">

                                    <strong>
                                        <c:out value="${a.title}"/>
                                    </strong>

                                    <c:choose>

                                        <c:when test="${a.type == 'EVENT_PARTICIPATED'}">

                                            <span class="activity-badge attended">
                                                Attended
                                            </span>

                                        </c:when>

                                        <c:when test="${a.type == 'VOLUNTEER'}">

                                            <span class="activity-badge volunteer">
                                                Volunteer
                                            </span>

                                        </c:when>

                                        <c:otherwise>

                                            <span class="activity-badge">
                                                <c:out value="${a.type}"/>
                                            </span>

                                        </c:otherwise>

                                    </c:choose>

                                </div>


                                <c:if test="${a.hours > 0}">

                                    <span class="activity-hours">
                                        &#9201; ${a.hours} hours
                                    </span>

                                </c:if>

                            </div>

                        </div>

                    </c:forEach>

                </div>

            </c:if>


            <c:if test="${empty recent}">

                <div class="student-empty">

                    <div class="empty-icon">
                        &#128203;
                    </div>

                    <strong>
                        No activity yet
                    </strong>

                    <span>
                        Your recent campus activities will appear here.
                    </span>

                </div>

            </c:if>

        </div>


        <!-- =================================================
             UPCOMING EVENTS
             ================================================= -->

        <div class="card student-events-card">

            <div class="dashboard-card-header">

                <div>

                    <p class="eyebrow">
                        DON'T MISS OUT
                    </p>

                    <h3>
                        Upcoming Events
                    </h3>

                </div>

                <a
                    class="card-view-link"
                    href="${cp}/events"
                >
                    View all
                </a>

            </div>


            <c:if test="${not empty upcoming}">

                <div class="student-events-list">

                    <c:forEach items="${upcoming}" var="e">

                        <div class="student-event">

                            <div class="event-date-box">

                                <span>
                                    &#128197;
                                </span>

                            </div>


                            <div class="student-event-details">

                                <a href="${cp}/events">

                                    <strong>
                                        <c:out value="${e.title}"/>
                                    </strong>

                                </a>

                                <span class="event-club">
                                    &#127970;
                                    <c:out value="${e.club}"/>
                                </span>

                                <span class="event-date">
                                    &#128197;
                                    ${e.event_date}
                                </span>

                            </div>


                            <a
                                class="event-arrow"
                                href="${cp}/events"
                            >
                                &#8594;
                            </a>

                        </div>

                    </c:forEach>

                </div>

            </c:if>


            <c:if test="${empty upcoming}">

                <div class="student-empty">

                    <div class="empty-icon">
                        &#128197;
                    </div>

                    <strong>
                        No upcoming events
                    </strong>

                    <span>
                        Check back later for new campus events.
                    </span>

                </div>

            </c:if>

        </div>


        <!-- =================================================
             ANNOUNCEMENTS
             ================================================= -->

        <div class="card student-announcements-card">

            <div class="dashboard-card-header">

                <div>

                    <p class="eyebrow">
                        CAMPUS UPDATES
                    </p>

                    <h3>
                        Announcements
                    </h3>

                </div>

                <span class="card-header-icon">
                    &#128226;
                </span>

            </div>


            <c:if test="${not empty ann}">

                <div class="announcement-items">

                    <c:forEach items="${ann}" var="a">

                        <div class="announcement-item">

                            <div class="announcement-icon">
                                &#128276;
                            </div>

                            <div class="announcement-content">

                                <div class="announcement-title">

                                    <strong>
                                        <c:out value="${a.title}"/>
                                    </strong>

                                    <span class="announcement-club">
                                        <c:out value="${empty a.club?'General':a.club}"/>
                                    </span>

                                </div>

                                <p>
                                    <c:out value="${a.body}"/>
                                </p>

                            </div>

                        </div>

                    </c:forEach>

                </div>

            </c:if>


            <c:if test="${empty ann}">

                <div class="student-empty">

                    <div class="empty-icon">
                        &#128276;
                    </div>

                    <strong>
                        Nothing new
                    </strong>

                    <span>
                        You're all caught up!
                    </span>

                </div>

            </c:if>

        </div>

    </section>

</div>
```
