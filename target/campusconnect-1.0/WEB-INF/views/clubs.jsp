```jsp
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="nav.jspf" %>

<div class="wrap clubs-page">

    <div class="clubs-header">

        <div>
            <p class="eyebrow">CAMPUS COMMUNITY</p>

            <h2>Explore Clubs</h2>

            <p class="clubs-subtitle">
                Discover student communities, join clubs and get involved
                in campus activities.
            </p>
        </div>

        <div class="clubs-header-icon">
            &#128101;
        </div>

    </div>


    <p class="ok">
        <c:out value="${param.msg}"/>
    </p>


    <c:if test="${not empty lastClub}">

        <div class="recent-club">

            <div class="recent-club-icon">
                &#128065;
            </div>

            <div>
                <span>Recently viewed club</span>

                <a href="${cp}/clubs/details?id=${lastClub}">
                    Club #${lastClub}
                </a>
            </div>

        </div>

    </c:if>


    <div class="club-filter-card">

        <div class="club-search">

            <span class="search-icon">
                &#128269;
            </span>

            <input
                id="q"
                type="text"
                placeholder="Search clubs..."
                oninput="filterClubs()"
            >

        </div>


        <div class="club-filter">

            <label for="fc">Category</label>

            <select id="fc" onchange="filterClubs()">

                <option value="">All categories</option>

                <c:forEach items="${cats}" var="k">

                    <option value="${k}">
                        <c:out value="${k}"/>
                    </option>

                </c:forEach>

            </select>

        </div>


        <div class="club-filter">

            <label for="fs">Status</label>

            <select id="fs" onchange="filterClubs()">

                <option value="">All statuses</option>

                <option value="ACTIVE">ACTIVE</option>

                <option value="INACTIVE">INACTIVE</option>

            </select>

        </div>

    </div>


    <div class="clubs-result-heading">

        <div>

            <span class="result-label">
                AVAILABLE CLUBS
            </span>

            <strong id="clubCount">
                ${fn:length(clubs)}
            </strong>

        </div>

        <span class="result-description">
            Find a community that matches your interests.
        </span>

    </div>


    <div class="clubs-grid">

        <c:forEach items="${clubs}" var="c">

            <article
                class="club-card"
                data-c="${c.category}"
                data-s="${c.status}"
            >

                <div class="club-card-top">

                    <div class="club-icon">
                        &#127891;
                    </div>

                    <div class="club-status">

                        <span class="badge">
                            <c:out value="${c.category}"/>
                        </span>

                        <span class="badge ${c.active?'ok':'err'}">
                            <c:out value="${c.status}"/>
                        </span>

                    </div>

                </div>


                <div class="club-card-body">

                    <h3>
                        <a href="${cp}/clubs/details?id=${c.id}">
                            <c:out value="${c.name}"/>
                        </a>
                    </h3>


                    <p class="club-description">

                        <c:choose>

                            <c:when test="${not empty c.description}">
                                <c:out value="${c.description}"/>
                            </c:when>

                            <c:otherwise>
                                No description available for this club.
                            </c:otherwise>

                        </c:choose>

                    </p>


                    <div class="club-members">

                        <span class="members-icon">
                            &#128101;
                        </span>

                        <span>
                            <strong>${c.members}</strong>
                            members
                        </span>

                    </div>

                </div>


                <div class="club-card-actions">

                    <a
                        class="btn ghost sm"
                        href="${cp}/clubs/details?id=${c.id}"
                    >
                        View Details
                    </a>


                    <c:if test="${c.active}">

                        <form
                            method="post"
                            action="${cp}/clubs"
                            onsubmit="return confirm('Request to join this club?')"
                        >

                            <input
                                type="hidden"
                                name="clubId"
                                value="${c.id}"
                            >

                            <button
                                class="btn sm"
                                type="submit"
                            >
                                Join Club
                            </button>

                        </form>

                    </c:if>

                </div>

            </article>

        </c:forEach>

    </div>


    <c:if test="${empty clubs}">

        <div
            id="no-clubs"
            class="empty clubs-empty"
        >

            <div class="clubs-empty-icon">
                &#128269;
            </div>

            <b>No clubs found</b>

            <span>
                Try changing your search or filters.
            </span>

        </div>

    </c:if>


    <div
        id="no-filter-results"
        class="empty clubs-empty"
        style="display:none"
    >

        <div class="clubs-empty-icon">
            &#128269;
        </div>

        <b>No matching clubs</b>

        <span>
            Try changing your search or filters.
        </span>

    </div>

</div>


<script>
function filterClubs() {

    var search =
        document.getElementById("q").value.toLowerCase().trim();

    var category =
        document.getElementById("fc").value;

    var status =
        document.getElementById("fs").value;

    var clubs =
        document.querySelectorAll(".club-card");

    var visible = 0;


    for (var i = 0; i < clubs.length; i++) {

        var card = clubs[i];

        var text =
            card.textContent.toLowerCase();

        var cardCategory =
            card.getAttribute("data-c");

        var cardStatus =
            card.getAttribute("data-s");


        var matchesSearch =
            search === "" ||
            text.indexOf(search) !== -1;

        var matchesCategory =
            category === "" ||
            cardCategory === category;

        var matchesStatus =
            status === "" ||
            cardStatus === status;


        if (
            matchesSearch &&
            matchesCategory &&
            matchesStatus
        ) {

            card.style.display = "";
            visible++;

        } else {

            card.style.display = "none";

        }

    }


    var count =
        document.getElementById("clubCount");

    if (count) {
        count.textContent = visible;
    }


    var noResults =
        document.getElementById("no-filter-results");


    if (noResults) {

        if (visible === 0 && clubs.length > 0) {
            noResults.style.display = "flex";
        } else {
            noResults.style.display = "none";
        }

    }

}
</script>
```
