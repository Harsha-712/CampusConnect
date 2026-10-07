<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="nav.jspf" %>

<div class="wrap profile-page">

```
<section class="profile-hero">

    <div class="profile-identity">

        <div class="profile-avatar">
            &#128100;
        </div>

        <div class="profile-heading">
            <p class="eyebrow">STUDENT ACCOUNT</p>

            <h2>
                <c:out value="${u.name}"/>
            </h2>

            <p>
                Manage your personal information and account security.
            </p>
        </div>

    </div>

    <div class="profile-status">
        <span class="profile-status-dot"></span>
        Active Account
    </div>

</section>


<c:if test="${not empty msg}">
    <div class="profile-alert success-alert">
        <span>&#10003;</span>
        <c:out value="${msg}"/>
    </div>
</c:if>

<c:if test="${not empty err}">
    <div class="profile-alert error-alert">
        <span>&#9888;</span>
        <c:out value="${err}"/>
    </div>
</c:if>

<p class="profile-validation-error" id="ce"></p>


<div class="profile-grid">

    <!-- ACCOUNT INFORMATION -->

    <section class="card profile-card">

        <div class="profile-card-header">

            <div class="profile-card-icon">
                &#128100;
            </div>

            <div>
                <p class="eyebrow">ACCOUNT INFORMATION</p>
                <h3>Personal Details</h3>
                <span>
                    Keep your contact information up to date.
                </span>
            </div>

        </div>


        <form method="post"
              action="${cp}/student/profile"
              id="f"
              class="profile-form">

            <div class="profile-field">

                <label for="email">
                    Email Address
                </label>

                <div class="profile-input-wrap readonly">

                    <span class="input-icon">
                        &#9993;
                    </span>

                    <input id="email"
                           type="email"
                           value="<c:out value='${u.email}'/>"
                           disabled>

                    <span class="readonly-label">
                        VERIFIED
                    </span>

                </div>

                <small>
                    Your email address cannot be changed.
                </small>

            </div>


            <div class="profile-field">

                <label for="n">
                    Full Name
                </label>

                <div class="profile-input-wrap">

                    <span class="input-icon">
                        &#128100;
                    </span>

                    <input name="name"
                           id="n"
                           value="<c:out value='${u.name}'/>"
                           placeholder="Enter your full name"
                           autocomplete="name"
                           required>

                </div>

            </div>


            <div class="profile-field">

                <label for="ph">
                    Phone Number
                </label>

                <div class="profile-input-wrap">

                    <span class="input-icon">
                        &#128222;
                    </span>

                    <input name="phone"
                           id="ph"
                           value="<c:out value='${u.phone}'/>"
                           placeholder="10-digit phone number"
                           maxlength="10"
                           inputmode="numeric"
                           autocomplete="tel"
                           required>

                </div>

                <small>
                    Enter a valid 10-digit phone number.
                </small>

            </div>


            <div class="profile-divider"></div>


            <!-- PASSWORD -->

            <div class="profile-section-title">

                <div class="security-icon">
                    &#128274;
                </div>

                <div>
                    <h3>Account Security</h3>
                    <span>
                        Change your password when needed.
                    </span>
                </div>

            </div>


            <div class="profile-field">

                <label for="current">
                    Current Password
                </label>

                <div class="profile-input-wrap">

                    <span class="input-icon">
                        &#128273;
                    </span>

                    <input type="password"
                           name="current"
                           id="current"
                           placeholder="Enter current password"
                           autocomplete="current-password">

                </div>

            </div>


            <div class="profile-field">

                <label for="np">
                    New Password
                </label>

                <div class="profile-input-wrap">

                    <span class="input-icon">
                        &#128273;
                    </span>

                    <input type="password"
                           name="newPassword"
                           id="np"
                           placeholder="Enter new password"
                           autocomplete="new-password">

                </div>

                <small>
                    Minimum 8 characters with at least one letter
                    and one number.
                </small>

            </div>


            <div class="password-tip">

                <span>&#128161;</span>

                <div>
                    <strong>Password tip</strong>
                    <p>
                        Use a combination of letters and numbers
                        that is difficult for others to guess.
                    </p>
                </div>

            </div>


            <div class="profile-actions">

                <button class="btn profile-save"
                        type="submit">

                    &#128190; Save Changes

                </button>

                <a class="btn ghost"
                   href="${cp}/student/dashboard">

                    Cancel

                </a>

            </div>

        </form>

    </section>


    <!-- PROFILE SUMMARY -->

    <aside class="profile-sidebar">

        <section class="card profile-summary-card">

            <div class="summary-avatar">
                &#128100;
            </div>

            <h3>
                <c:out value="${u.name}"/>
            </h3>

            <p class="summary-role">
                Student
            </p>

            <div class="summary-divider"></div>

            <div class="summary-item">

                <span class="summary-icon">
                    &#9993;
                </span>

                <div>
                    <small>Email</small>
                    <strong>
                        <c:out value="${u.email}"/>
                    </strong>
                </div>

            </div>


            <div class="summary-item">

                <span class="summary-icon">
                    &#128222;
                </span>

                <div>
                    <small>Phone</small>
                    <strong>
                        <c:choose>
                            <c:when test="${not empty u.phone}">
                                <c:out value="${u.phone}"/>
                            </c:when>
                            <c:otherwise>
                                Not provided
                            </c:otherwise>
                        </c:choose>
                    </strong>
                </div>

            </div>


            <div class="summary-item">

                <span class="summary-icon">
                    &#128274;
                </span>

                <div>
                    <small>Account</small>
                    <strong>Active</strong>
                </div>

            </div>

        </section>


        <section class="profile-security-card">

            <div class="security-card-icon">
                &#128737;
            </div>

            <h3>Your information is protected</h3>

            <p>
                Your account information is accessible only to
                authorized users of CampusConnect.
            </p>

        </section>

    </aside>

</div>
```

</div>

<script>

document.getElementById("f").onsubmit = function () {

    var name = document.getElementById("n");
    var phone = document.getElementById("ph");
    var password = document.getElementById("np");
    var error = document.getElementById("ce");

    var message = "";

    if (name.value.trim().length < 2) {

        message = "Name is required.";

    } else if (!/^\d{10}$/.test(phone.value.trim())) {

        message = "Phone must be exactly 10 digits.";

    } else if (
        password.value &&
        !/^(?=.*[A-Za-z])(?=.*\d).{8,}$/.test(password.value)
    ) {

        message =
            "New password must contain at least 8 characters, including a letter and a number.";

    }

    if (message) {

        error.textContent = message;
        error.style.display = "block";

        window.scrollTo({
            top: 0,
            behavior: "smooth"
        });

        return false;
    }

    error.style.display = "none";

    return true;
};

</script>
