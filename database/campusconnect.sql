DROP DATABASE IF EXISTS campusconnect; CREATE DATABASE campusconnect; USE campusconnect;
CREATE TABLE users(id INT AUTO_INCREMENT PRIMARY KEY,name VARCHAR(100) NOT NULL,email VARCHAR(120) NOT NULL UNIQUE,password_hash CHAR(64) NOT NULL,
 role ENUM('STUDENT','CLUB_COORDINATOR','FACULTY_COORDINATOR','ADMIN') NOT NULL,phone VARCHAR(15),active BOOLEAN NOT NULL DEFAULT TRUE,INDEX(role));
CREATE TABLE clubs(id INT AUTO_INCREMENT PRIMARY KEY,name VARCHAR(100) NOT NULL UNIQUE,category VARCHAR(40) NOT NULL,description TEXT,
 faculty_id INT,coordinator_id INT,active BOOLEAN NOT NULL DEFAULT TRUE,FOREIGN KEY(faculty_id) REFERENCES users(id),FOREIGN KEY(coordinator_id) REFERENCES users(id));
CREATE TABLE club_members(club_id INT,user_id INT,joined_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,PRIMARY KEY(club_id,user_id),FOREIGN KEY(club_id) REFERENCES clubs(id),FOREIGN KEY(user_id) REFERENCES users(id));
CREATE TABLE membership_requests(id INT AUTO_INCREMENT PRIMARY KEY,club_id INT NOT NULL,user_id INT NOT NULL,status ENUM('PENDING','APPROVED','REJECTED') DEFAULT 'PENDING',
 UNIQUE(club_id,user_id),FOREIGN KEY(club_id) REFERENCES clubs(id),FOREIGN KEY(user_id) REFERENCES users(id));
CREATE TABLE events(id INT AUTO_INCREMENT PRIMARY KEY,club_id INT NOT NULL,title VARCHAR(150) NOT NULL,event_date DATE NOT NULL,capacity INT NOT NULL,attendance_code VARCHAR(10),fee DECIMAL(8,2) DEFAULT 0,FOREIGN KEY(club_id) REFERENCES clubs(id));
CREATE TABLE event_registrations(event_id INT,user_id INT,attended BOOLEAN DEFAULT FALSE,PRIMARY KEY(event_id,user_id),FOREIGN KEY(event_id) REFERENCES events(id),FOREIGN KEY(user_id) REFERENCES users(id));
CREATE TABLE student_activities(id INT AUTO_INCREMENT PRIMARY KEY,user_id INT NOT NULL,type ENUM('EVENT_PARTICIPATED','EVENT_ORGANIZED','WORKSHOP','VOLUNTEER') NOT NULL,
 title VARCHAR(150),hours DECIMAL(5,1) DEFAULT 0,verified BOOLEAN DEFAULT TRUE,INDEX(user_id),FOREIGN KEY(user_id) REFERENCES users(id));
CREATE TABLE certificates(id INT AUTO_INCREMENT PRIMARY KEY,user_id INT NOT NULL,event_id INT,title VARCHAR(150),issued_on DATE,FOREIGN KEY(user_id) REFERENCES users(id));
INSERT INTO users(name,email,password_hash,role,phone) VALUES
('Admin','admin@campus.edu',SHA2('password123',256),'ADMIN','9000000001'),
('Dr. Faculty','faculty@campus.edu',SHA2('password123',256),'FACULTY_COORDINATOR','9000000002'),
('Club Coordinator','coord@campus.edu',SHA2('password123',256),'CLUB_COORDINATOR','9000000003'),
('Harshavardhini','student@campus.edu',SHA2('password123',256),'STUDENT','9000000004');
INSERT INTO clubs(name,category,description,faculty_id,coordinator_id) VALUES
('Coding Club','Technical','Hackathons and workshops',2,3),('Rotaract Club','Social Service','Community service drives',2,3),('Drama Club','Cultural','Stage and street plays',2,3);
INSERT INTO club_members(club_id,user_id) VALUES(1,4),(2,4);
INSERT INTO events(club_id,title,event_date,capacity,attendance_code,fee) VALUES(1,'Web Dev Workshop','2026-11-10',60,'WEB123',100),(2,'Blood Donation Camp','2026-11-20',100,'BLD456',0);
INSERT INTO event_registrations VALUES(1,4,TRUE),(2,4,FALSE);
INSERT INTO student_activities(user_id,type,title,hours) VALUES(4,'EVENT_PARTICIPATED','Hackathon',0),(4,'EVENT_PARTICIPATED','Tech Talk',0),(4,'EVENT_ORGANIZED','Freshers Meet',0),
(4,'WORKSHOP','Java Workshop',0),(4,'WORKSHOP','SQL Workshop',0),(4,'VOLUNTEER','Blood Camp',5),(4,'VOLUNTEER','Tree Plantation',8);
INSERT INTO certificates(user_id,event_id,title,issued_on) VALUES(4,1,'Web Dev Workshop Participation','2026-09-01');
ALTER TABLE events ADD reg_deadline DATE; UPDATE events SET reg_deadline=event_date;
CREATE TABLE announcements(id INT AUTO_INCREMENT PRIMARY KEY,club_id INT,title VARCHAR(120) NOT NULL,body VARCHAR(500),created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,FOREIGN KEY(club_id) REFERENCES clubs(id));
CREATE TABLE products(id INT AUTO_INCREMENT PRIMARY KEY,club_id INT NOT NULL,name VARCHAR(100) NOT NULL,price DECIMAL(8,2) NOT NULL,stock INT NOT NULL,FOREIGN KEY(club_id) REFERENCES clubs(id));
CREATE TABLE orders(id INT AUTO_INCREMENT PRIMARY KEY,user_id INT NOT NULL,total DECIMAL(10,2) NOT NULL,status VARCHAR(20) NOT NULL,payment_ref VARCHAR(40),created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,FOREIGN KEY(user_id) REFERENCES users(id));
CREATE TABLE order_items(id INT AUTO_INCREMENT PRIMARY KEY,order_id INT NOT NULL,product_id INT NOT NULL,qty INT NOT NULL,price DECIMAL(8,2) NOT NULL,FOREIGN KEY(order_id) REFERENCES orders(id),FOREIGN KEY(product_id) REFERENCES products(id));
INSERT INTO announcements(club_id,title,body) VALUES(1,'Workshop','Bring your laptop to the Web Dev Workshop.');
INSERT INTO products(club_id,name,price,stock) VALUES(1,'Coding Club T-Shirt',499,50),(2,'Rotaract Badge',99,200);
INSERT INTO users(name,email,password_hash,role,phone) VALUES('Arun Kumar','arun@campus.edu',SHA2('password123',256),'STUDENT','9000000005'),('Meera Nair','meera@campus.edu',SHA2('password123',256),'STUDENT','9000000006'),('Karthik R','karthik@campus.edu',SHA2('password123',256),'STUDENT','9000000007');
INSERT INTO club_members(club_id,user_id) VALUES(1,5),(1,6),(3,6),(2,7),(3,7);
INSERT INTO membership_requests(club_id,user_id,status) VALUES(3,4,'PENDING'),(1,7,'PENDING');
INSERT INTO events(club_id,title,event_date,capacity,attendance_code,fee,reg_deadline) VALUES(1,'Hackathon 2026','2026-12-05',80,'HACK99',0,'2026-12-01'),(2,'Tree Plantation Drive','2026-11-15',50,'TREE77',0,'2026-11-12'),(3,'Annual Drama Night','2026-12-12',120,'DRAMA5',50,'2026-12-10');
INSERT INTO event_registrations(event_id,user_id) VALUES(3,4),(3,5),(3,6),(4,7),(5,6);
INSERT INTO announcements(club_id,title,body) VALUES(2,'Plantation Drive','Meet at the main gate at 7 AM.'),(3,'Auditions open','Drama night auditions this Friday.'),(NULL,'Welcome','Welcome to CampusConnect!');
INSERT INTO certificates(user_id,event_id,title,issued_on) VALUES(5,1,'Web Dev Workshop Participation','2026-09-01');
INSERT INTO student_activities(user_id,type,title,hours) VALUES(5,'EVENT_PARTICIPATED','Web Dev Workshop',0),(6,'VOLUNTEER','Library Drive',4);
INSERT INTO products(club_id,name,price,stock) VALUES(3,'Drama Club Mug',199,40),(2,'Rotaract Tote Bag',249,60);
INSERT INTO orders(user_id,total,status,payment_ref) VALUES(4,499,'PAID','SIM-DEMO1');INSERT INTO order_items(order_id,product_id,qty,price) VALUES(1,1,1,499);
CREATE TABLE activity_proposals(id INT AUTO_INCREMENT PRIMARY KEY,club_id INT NOT NULL,title VARCHAR(150) NOT NULL,description VARCHAR(500),status ENUM('PENDING','APPROVED','REJECTED') DEFAULT 'PENDING',created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,FOREIGN KEY(club_id) REFERENCES clubs(id));
INSERT INTO activity_proposals(club_id,title,description) VALUES(1,'Inter-college Hackathon','48-hour hackathon with 10 colleges'),(3,'Annual Fest Street Play','Street play series during the fest');
ALTER TABLE products ADD description VARCHAR(255), ADD category VARCHAR(40) DEFAULT 'Merchandise'; UPDATE products SET description=CONCAT('Official club merchandise: ',name);
ALTER TABLE events ADD description VARCHAR(500), ADD event_time TIME NOT NULL DEFAULT '10:00:00', ADD location VARCHAR(100) NOT NULL DEFAULT 'Main Auditorium', ADD status VARCHAR(12) NOT NULL DEFAULT 'ACTIVE', ADD created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP;
UPDATE events SET description=CONCAT('Join us for ',title,'.'),location=ELT(1+MOD(id,3),'Main Auditorium','Seminar Hall','Open Ground'),event_time=ELT(1+MOD(id,2),'10:00:00','14:30:00');
INSERT INTO events(club_id,title,description,event_date,event_time,location,capacity,attendance_code,reg_deadline,status) VALUES(1,'Cancelled Demo Event','Example of a cancelled event.','2026-12-20','11:00:00','Lab 2',30,'CNL000','2026-12-15','CANCELLED');
