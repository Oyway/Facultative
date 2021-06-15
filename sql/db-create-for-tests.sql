CREATE SCHEMA IF NOT EXISTS `facultative_test` DEFAULT CHARACTER SET utf8 ;
USE `facultative_test`;


CREATE TABLE IF NOT EXISTS users (
  userid int NOT NULL AUTO_INCREMENT,
  login varchar(16) NOT NULL,
  pass varchar(20) NOT NULL,
  email varchar(25) NOT NULL,
  firstname varchar(15) NOT NULL,
  surname varchar(15) NOT NULL,
  role varchar(10) DEFAULT 'student',
  status tinyint DEFAULT '1',
  PRIMARY KEY (userid),
  UNIQUE KEY login (login)
);

CREATE TABLE IF NOT EXISTS topics (
  topicid int NOT NULL AUTO_INCREMENT,
  topic varchar(30) NOT NULL,
  PRIMARY KEY (topicid)
);

CREATE TABLE courses (
	courseid int not null auto_increment,
    course varchar(30) not null,
	topicid int not null,
    teacherid int,
    date_start date,
    date_stop date,
    descr varchar(100),
    FOREIGN KEY (teacherid) REFERENCES users(userid),
    FOREIGN KEY (topicid) REFERENCES topics(topicid),
    primary key(courseid)
);

CREATE TABLE IF NOT EXISTS users_courses (
	studentid int not null,
    courseid int not null,
    mark int,
    check(mark between 0 and 100),
    FOREIGN KEY (studentid) REFERENCES users(userid) ON DELETE CASCADE,
    FOREIGN KEY (courseid) REFERENCES courses(courseid) ON DELETE CASCADE,
    primary key(studentid, courseid)
);