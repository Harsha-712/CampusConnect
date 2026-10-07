**Verification note:** Static checks were run on the revised event-page JavaScript and XML configuration. The full Maven build, JSP compilation by Tomcat, and database-backed end-to-end cases still need to be run on your Windows machine because Maven, Tomcat, and the MySQL server are not available in this review environment.

# TESTING (demo password for all accounts: password123). "Actual" columns must be filled by you after running; none were executed in the authoring sandbox.
| ID | Feature | Input | Expected | Actual | Status |
|---|---|---|---|---|---|
| T01 | JavaScript | Register with weak password / mismatched confirm | Inline error, no submit | | Not run |
| T02 | AJAX | Type student@campus.edu on /register | "✗ ALREADY REGISTERED" | | Not run |
| T03 | Authentication | Login student@campus.edu | Redirect /student/dashboard | | Not run |
| T04 | Session | Open /student/dashboard after logout | Redirect to /login | | Not run |
| T05 | Authorization | Student opens /admin/stats | 403 error page | | Not run |
| T06 | Cookies | Tick Remember email, logout, reopen /login | Email prefilled; /cookie-demo lists cookie | | Not run |
| T07 | XML | Open /xml/clubs and /xml-clubs.jsp | XML served; table rendered | | Not run |
| T08 | Event registration | Register on full / past-deadline event | Refused with message | | Not run |
| T09 | Attendance | Enter wrong then correct code (e.g. WEB123) | INVALID CODE, then VALID; passport +1 | | Not run |
| T10 | Certificates | Coordinator "Issue certificates"; student opens /student/certificates | Certificate listed; printable view opens | | Not run |
| T11 | Coordinator | Approve pending request in /coordinator/manage | Student appears in My Memberships as APPROVED | | Not run |
| T12 | E-commerce | Add item, pay card 4111111111111111 / ending 0000 | Order created, stock down / declined | | Not run |
| T13 | Admin | Create club, deactivate user | Club listed; user cannot log in | | Not run |
| T14 | Faculty | Open /faculty/stats | Read-only analytics | | Not run |
| T15 | Profile | Edit name/phone, change password | Saved; session name updated | To be tested locally | Not executed |
| T16 | Club details | Open /clubs/details?id=1 | Info, events, status badge | To be tested locally | Not executed |
| T17 | Cart | Use +/- on cart rows | Quantity changes, total updates | To be tested locally | Not executed |
| T18 | Admin users/products | /admin/users?role=STUDENT, /admin/products add/update/delete | Search, toggle, add, stock edits | To be tested locally | Not executed |
| T19 | Coordinator pages | /coordinator/members, /attendance (mark present), /proposals | Lists load; passport +1 on mark | To be tested locally | Not executed |
| T20 | Faculty | /faculty/review approve a proposal | Status changes to APPROVED | To be tested locally | Not executed |
| T21 | Events | Coordinator edits/cancels event at /coordinator/events | Updated; cancelled event shows CANCELLED and rejects registration | To be tested locally | Not executed |
| T22 | Filters | Club category/status and event status/club filters | Lists filter live | To be tested locally | Not executed |
| T23 | Orders | Checkout; open /shop/order?id=… | Confirmation + item details from DB | To be tested locally | Not executed |
| T24 | Charts | Open /admin/stats | Bars reflect DB counts | To be tested locally | Not executed |
