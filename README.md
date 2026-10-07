# CampusConnect – College Club Management Portal
Java 21 · Jakarta Servlet/JSP (Tomcat 10.1) · JDBC · MySQL 8 · Maven · HTML/CSS/JS · AJAX · XML. No frameworks.
## Setup
1. `mysql -u root -p < database/campusconnect.sql` (creates DB `campusconnect`, tables, sample data).
2. DB credentials: env vars `DB_URL` (default `jdbc:mysql://localhost:3306/campusconnect`), `DB_USER`, `DB_PASS` (default root/root). Set them before starting Tomcat.
3. Run `mvn clean package` with JDK 21 installed. Copy `target/campusconnect.war` to `<TOMCAT>/webapps/`, start Tomcat 10.1, and open `http://localhost:8080/campusconnect/`.
## Demo accounts (password `password123`)
student@campus.edu · coord@campus.edu · faculty@campus.edu · admin@campus.edu (also arun@/meera@/karthik@campus.edu students)
## Architecture
`controller/` servlets + `AuthFilter` (session + role check) → `service/` (validation, business rules: EventService, ClubService, OrderService, StatsService) → `dao/` (JDBC, PreparedStatements: EventDAO, ClubDAO, OrderDAO, StatsDAO) → MySQL. `util/DBUtil` (connection, SHA-256) and `util/Q` (JDBC helper used by DAOs). Views: `WEB-INF/views/*.jsp` with shared `nav.jspf`; `css/style.css` design system.
`model/` holds JavaBean entities (User, Club, Event, Product, Order, …) filled by `Q.beans` (reflection mapper). Auth, users, events, clubs, shop, proposals and attendance-code checks go through DAO+service classes. Still calling the `Q` helper directly from servlets: dashboards, stats, XML clubs, memberships, certificates, announcements, coordinator members/attendance pages, manage-page actions, product admin.
## Roles
Student: clubs, join, events, register/cancel, attendance code, Activity Passport, certificates (printable), announcements, shop/cart/checkout/orders, profile. Coordinator: members, requests, events (create/edit/cancel), attendance, announcements, certificates, club edit, proposals, analytics. Faculty: review proposals, events/attendance/certificates, stats. Admin: all of the above plus user/club/product management.
## Web Tech concepts
AJAX: `/ajax/check-email`, `/ajax/check-club`, `/ajax/event-availability`, `/ajax/verify-code`, live search/filters. XML: `/xml/clubs` + `/xml-clubs.jsp` (XML → JS → HTML table). Cookies: remember email, last viewed club, theme (`/cookie-demo`). Sessions: HttpSession (userId, username, role, cart), 30-min timeout, logout invalidates. Passport: `student_activities` + `certificates`, updated on attendance. E-commerce: cart in session, simulated payment (card ending 0000 declined), orders/order_items, stock update, confirmation and order detail pages. Charts: CSS bars built from DB queries on `/…/stats`.
## Testing
See TESTING.md (manual cases; results to be filled after local execution).
