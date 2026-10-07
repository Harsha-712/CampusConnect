# CampusConnect review notes

## Changes made in this revision
- Rebuilt the Events page with responsive cards, search, status/club filters, clear availability text, and role-aware registration controls.
- Removed inline JavaScript event handlers from event attendance buttons; this avoids the `${e.id}` editor/parser error and uses `data-event-id` listeners instead.
- Rebuilt Event Details with the same registration/attendance rules and safe DOM event listeners.
- Registration/cancellation now validates the event ID and registration action, checks that the logged-in role is STUDENT, and returns clearer messages for missing/already-registered/attended cases.
- Registration insert checks event date, registration deadline, cancellation state, and capacity at the database statement.
- Attendance verification handles missing/invalid event IDs, cancelled events, and a null attendance code without a NullPointerException.
- Updated the Maven compiler release to Java 21 to match the JDK reported in the user's environment.
- Removed the unused imports reported in VS Code and added responsive event-page styling.

## Checks performed here
- `pom.xml` and `WEB-INF/web.xml` parse as valid XML.
- JavaScript extracted from `events.jsp` and `event-details.jsp` passes `node --check`.
- Maven build and Tomcat/MySQL runtime tests were not run here: Maven, Tomcat, and the local MySQL server are unavailable in this environment.

## Required local verification
1. In the project folder, run `mvn clean package`.
2. Deploy the generated WAR to Tomcat 10.1 and confirm `/campusconnect/` loads.
3. Log in as `student@campus.edu` / `password123`; test event register, cancel, invalid attendance code, valid attendance code, and full/cancelled event states.
4. Test coordinator, faculty, and admin pages using their demo accounts in `README.md`.
5. Check Tomcat logs for JSP compilation, SQL, and runtime errors.

The demo database script begins with `DROP DATABASE IF EXISTS campusconnect`. Back up any existing `campusconnect` database before importing it; importing the script deletes and recreates that database.
