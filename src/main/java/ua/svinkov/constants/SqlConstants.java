package ua.svinkov.constants;

/**
 * SQL command holder
 * 
 * @author R.Svinkov
 *
 */
public class SqlConstants {

	public final static String FIND_ALL_COURSE = "SELECT courses.*, topics.topic, users.* "
			+ " FROM courses inner join topics on courses.topicid=topics.topicid "
			+ " inner join users on courses.teacherid=users.userid";

	public final static String FIND_ALL_BY_TEACHER_ID = "SELECT users_courses.*, courses.*, topics.topic, student.* "
			+ " FROM users_courses inner join courses on users_courses.courseid=courses.courseid "
			+ " inner join users as student on users_courses.studentid=student.userid "
			+ " inner join topics on courses.topicid=topics.topicid " + " where courses.teacherid=?";

	public final static String FIND_USER_BY_ID = "SELECT * FROM users where userid=?";

	public final static String FIND_ALL_USER_COURSES_BY_USER_ID = "SELECT users_courses.*, courses.*, student.*, topics.*"
			+ " FROM users_courses inner join courses on users_courses.courseid=courses.courseid "
			+ " inner join users as student ON users_courses.studentid=student.userid "
			+ " inner join users as teacher ON courses.teacherid=teacher.userid "
			+ " inner join topics ON courses.topicid=topics.topicid WHERE users_courses.studentid=?";

	public final static String UPDATE_USER_COURSES_MARK = "UPDATE users_courses SET mark=? where studentid=? and courseid=?";

	public final static String FIND_USER_COURSES_BY_USER_AND_COURSE = "SELECT * FROM users_courses where studentid=? and courseid=?";

	public final static String INSERT_USER_COURSES = "insert into users_courses(studentid,courseid) values(?,?)";

	public final static String INSERT_COURSE = "insert into courses(course,topicid,teacherid,date_start,date_stop,descr)"
			+ " values(?,?,?,?,?,?)";

	public final static String FIND_COURSE_BY_ID = "select * "
			+ "from courses inner join topics on courses.topicid=topics.topicid "
			+ "inner join users on users.userid=courses.teacherid where courseid = ?";

	public final static String UPDATE_COURSE = "UPDATE courses "
			+ " SET course=?, topicid=?,teacherid=?,date_start=?,date_stop=?,descr=? " + " where courseid=?";

	public final static String DELETE_COURSE = "DELETE FROM courses WHERE courseid=?";

	public final static String FIND_COURSE_BY_ID_SORTED = "SELECT courses.courseid "
			+ " FROM courses inner join users_courses on courses.courseid=users_courses.courseid "
			+ " where teacherid=? " + " group by courseid " + " having count(*)>0 " + " order by count(*)";

	public final static String FIND_ALL_TOPICS = "SELECT * FROM topics";

	public final static String INSERT_USER = "insert into users(login,pass,email,firstname,surname)"
			+ " values(?,?,?,?,?)";

	public final static String FIND_ALL_USERS = "select * from users";

	public final static String UPDATE_USER = "UPDATE users "
			+ " SET login=?, pass=?,email=?,firstname=?,surname=?,role=?, status=?" + " where userid=?";

	public final static String FIND_USER_BY_LOGIN = "select * from users where login = ?";
}
