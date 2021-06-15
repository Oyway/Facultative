insert into users(userid,login,pass,email,firstname, surname,role,status) values ('1', 'user', 'user', 'user@gmail.com', 'Ivan', 'Ivanov', 'student', '1');
insert into users(userid,login,pass,email,firstname, surname,role,status) values ('2', 'teacher', 't', 'teacher@gmail.com', 'Oleg', 'Boid', 'teacher', '1');
insert into users(userid,login,pass,email,firstname, surname,role,status) values ('3', 'admin', 'admin', 'admin@gmai.com', 'Admin', 'Admin', 'admin', '1');
insert into users(userid,login,pass,email,firstname, surname,role,status) values ('4', 'user2', 'user2', 'user2@gmail.com', 'Olia', 'Miwa', 'student', '1');
insert into users(userid,login,pass,email,firstname, surname,role,status) values ('5', 'teacher2', 't2', 'teacher2@gmail.com', 'Yulia', 'Smirnova', 'teacher', '1');
insert into users(userid,login,pass,email,firstname, surname,role,status) values ('6', 'user3', 'user3', 'user3@gmail.com', 'Leonid', 'Spartanskii', 'student', '1');

insert into topics(topicid,topic) values('7', 'Java');
insert into topics(topicid,topic) values('8', 'C++');
insert into topics(topicid,topic) values('9', 'C#');
insert into topics(topicid,topic) values('10', 'JavaScript');
insert into topics(topicid,topic) values('11', 'Ruby');
insert into topics(topicid,topic) values('12', 'Android');


insert into courses(courseid, course, topicid, teacherid, date_start,date_stop,descr) values('3', 'Java Core', '7', '2', '2021-10-23', '2022-02-12', 'Java Core');
insert into courses(courseid, course, topicid, teacherid, date_start,date_stop,descr) values('4', 'Java Spring', '7', '5', '2021-05-12', '2021-10-13', 'Java Spring course');

insert into users_courses(studentid,courseid) values('1', '3');
insert into users_courses(studentid,courseid) values('1', '4');
insert into users_courses(studentid,courseid) values('4', '4');