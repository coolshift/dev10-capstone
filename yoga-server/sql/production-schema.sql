drop database if exists yoga_studio;
create database yoga_studio;
use yoga_studio;


create table app_user (
	app_user_id int primary key auto_increment,
    
    first_name varchar(50) not null,
    last_name varchar(50) not null,
    dob date null,
    phone_number varchar(15) null,
    email_address varchar(512) not null unique,
    user_type enum ('STUDENT','INSTRUCTOR'),
    password_hash varchar(1024) not null
);

create table app_authority (
	app_authority_id int primary key auto_increment,
    `name` varchar(25) not null
);

create table app_user_authority (
	app_user_id int not null,
    app_authority_id int not null,
    constraint pk_app_user_authority
		primary key (app_user_id, app_authority_id),
	constraint fk_app_user_authority_app_user_id
		foreign key (app_user_id)
		references app_user(app_user_id),
	constraint fk_app_user_authority_app_authority_id
		foreign key (app_authority_id)
		references app_authority(app_authority_id)
);

create table location (

location_id int primary key auto_increment,
`name` varchar(50) not null,
size enum ('SMALL','MEDIUM','LARGE') null,
`description` varchar(1000) null
);

create table `session` (

session_id int primary key auto_increment,
start_time datetime not null,
end_time datetime not null,
capacity int,
instructor_id int null,
location_id int null,
constraint fk_session_instructor_user_id
        foreign key (instructor_id)
        references app_user(app_user_id),
constraint fk_session_location_id
	foreign key (location_id)
	references location (location_id)
);

create table reservation
(
reservation_id int primary key auto_increment,

session_id int not null,
student_id int not null,

constraint fk_reservation_session_id
		foreign key (session_id)
        references `session`(session_id),
constraint fk_reservation_guest_id        
		foreign key (student_id)
        references app_user(app_user_id)

);

INSERT INTO app_user (first_name, last_name, dob, phone_number, email_address, user_type, password_hash)
VALUES
('Barbara', 'Johnson', '1991-01-01', '555-555-5551', 'barbara.johnson@email.com', 'STUDENT', '0'),
('Stella', 'Smith', '1992-02-02', '555-555-5552', 'dave.smith@email.com', 'INSTRUCTOR', '0'),
('Emily', 'Brown', '1993-03-03', '555-555-5553', 'emily.brown@email.com', 'STUDENT', '0'),
('Michael', 'Davis', '1994-04-04', '555-555-5554', 'michael.davis@email.com', 'INSTRUCTOR', '0'),
('Olivia', 'Martinez', '1995-05-05', '555-555-5555', 'olivia.martinez@email.com', 'STUDENT', '0'),
('William', 'Garcia', '1996-06-06', '555-555-5556', 'william.garcia@email.com', 'INSTRUCTOR', '0'),
('Sophia', 'Harris', '1997-07-07', '555-555-5557', 'sophia.harris@email.com', 'STUDENT', '0');

insert into location (`name`, size, `description`)

values
('Room 1','SMALL','Upstairs'),
('Room 2','MEDIUM','Upstairs'),
('Central Park','LARGE','100 Park Ave.'),
('Location4','SMALL','Description4'),
('Location5','MEDIUM','Description5'),
('Location6','LARGE','Description6'),
('Location7','SMALL','Description7');



insert into `session` (start_time, end_time, capacity, instructor_id, location_id)
values
('2023-10-27 13:00:00','2023-10-27 14:00:00',3,2,1),
('2023-10-27 14:00:00','2023-10-27 15:00:00',10,2,2),
('2023-10-27 10:00:00','2023-10-27 11:00:00',12,4,3),
('2023-10-28 13:00:00','2023-10-27 14:00:00',3,2,1),
('2023-10-28 14:00:00','2023-10-27 15:00:00',10,2,2),
('2023-10-28 10:00:00','2023-10-27 11:00:00',12,5,3),
('2023-10-28 15:00:00','2023-10-27 16:00:00',12,6,3);

insert into reservation (session_id, student_id)
values 
(1,1),
(1,3),
(1,5);