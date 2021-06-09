package ua.svinkov.model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ua.svinkov.constants.Constants;
import ua.svinkov.model.dao.UserCoursesDao;
import ua.svinkov.model.dao.mapper.UserCoursesMapper;
import ua.svinkov.model.dao.mapper.UserMapper;
import ua.svinkov.model.entity.UserCourses;

public class JDBCUserCoursesDao implements UserCoursesDao {
	
	private Connection connection;


    public JDBCUserCoursesDao(Connection connection) {
        this.connection = connection;
    }

	@Override
	public void create(UserCourses entity) {
		try (PreparedStatement pstmt = connection.prepareStatement(Constants.INSERT_USER_COURSES)) {

			pstmt.setInt(1, entity.getUser().getUserid());
			pstmt.setInt(2, entity.getCourse().getCourseid());
			pstmt.execute();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public UserCourses findById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<UserCourses> findAllById(int id) {
		List<UserCourses> user = new ArrayList<>();
        ResultSet rs = null;
        ResultSet rs1 = null;
        try (PreparedStatement st = connection.prepareStatement(Constants.FIND_ALL_USER_COURSES_BY_USER_ID);
        		PreparedStatement st1 = connection.prepareStatement(Constants.FIND_USER_BY_ID)) {
        	connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            UserCoursesMapper mapper = new UserCoursesMapper();
            st.setInt(1, id);
            rs = st.executeQuery();
            while (rs.next()) {
            	UserCourses us = mapper.extractFromResultSet(rs);
            	st1.setInt(1, us.getCourse().getTeacher().getUserid());
            	rs1 = st1.executeQuery();
            	if(rs1.next())
            		us.getCourse().setTeacher(new UserMapper().extractFromResultSet(rs1));
            	user.add(us);
            }
            
            rs.close();
            rs1.close();
        } catch (SQLException ex) {
        	
            ex.printStackTrace();
        } finally {
        	
        }
		return user;
	}
	
	public List<UserCourses> findAllByTeacherId(int id) {
		List<UserCourses> user = new ArrayList<>();
        ResultSet rs = null;
        
        try (PreparedStatement st = connection.prepareStatement(Constants.FIND_ALL_BY_TEACHER_ID)) {
            UserCoursesMapper mapper = new UserCoursesMapper();
            st.setInt(1, id);
            rs = st.executeQuery();
            while (rs.next()) {
            	user.add(mapper.extractFromResultSet(rs));
            }
            rs.close();
        } catch (SQLException ex) {
        	
            ex.printStackTrace();
        } finally {
        	
        }
		return user;
	}
	
	public boolean findUserCourse(int studentid, int courseid) {
		boolean result = false;
        ResultSet rs = null;
        try (PreparedStatement st = connection.prepareStatement(Constants.FIND_ALL_USER_COURSES_BY_USER_ID)) {
            st.setInt(1, studentid);
            st.setInt(2, courseid);
            rs = st.executeQuery();
            if (rs.next())
            	result = true;
            
            rs.close();
            st.close();
        } catch (SQLException e) {
        	throw new RuntimeException(e);
        }
		return result;
	}

	@Override
	public List<UserCourses> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(UserCourses entity) {
        try (PreparedStatement st = connection.prepareStatement(Constants.UPDATE_USER_COURSES_MARK)) {
            st.setInt(1, entity.getMark());
            st.setInt(2, entity.getUser().getUserid());
            st.setInt(3, entity.getCourse().getCourseid());
            st.executeUpdate();
        } catch (SQLException ex) {
        	throw new RuntimeException(ex);
        }
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		
	}

	@Override
    public void close()  {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

	@Override
	public void setAutoCommit(boolean commit) {
		try {
			connection.setAutoCommit(commit);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
	}

	@Override
	public void rollBackAndClose() {
		try {
			connection.rollback();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
	}

	@Override
	public void commit() {
		try {
			connection.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
	}

}
