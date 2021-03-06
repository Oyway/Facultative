package ua.svinkov.model.dao.impl;

import ua.svinkov.constants.SqlConstants;
import ua.svinkov.model.dao.TopicDao;
import ua.svinkov.model.dao.mapper.TopicMapper;
import ua.svinkov.model.entity.Topic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Data access object for topic entity
 * 
 * @author R.Svinkov
 *
 */
public class JDBCTopicDao implements TopicDao {
	private Connection connection;

	public JDBCTopicDao(Connection connection) {
		this.connection = connection;
	}

	@Override
	public void create(Topic entity) {

	}

	@Override
	public Topic findById(Long id) {
		return null;
	}

	@Override
	public List<Topic> findAll() {
		List<Topic> topics = new ArrayList<>();
		ResultSet rs = null;
		try (PreparedStatement st = connection.prepareStatement(SqlConstants.FIND_ALL_TOPICS)) {
			TopicMapper mapper = new TopicMapper();
			rs = st.executeQuery();
			while (rs.next())
				topics.add(mapper.extractFromResultSet(rs));

			rs.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return topics;
	}

	@Override
	public void update(Topic entity) {

	}

	@Override
	public void delete(Long id) {

	}

	@Override
	public void close() {
		try {
			connection.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
