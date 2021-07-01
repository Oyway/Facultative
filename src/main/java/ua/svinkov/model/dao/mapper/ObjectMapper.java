package ua.svinkov.model.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

/**
 * 
 * Defines general contract for mapping database result set rows to application
 * entities. Implementations are not supposed to move cursor of the resultSet
 * via next() method, but only extract information from the row in current
 * cursor position.
 *
 * 
 * @author R.Svinkov
 *
 */
public interface ObjectMapper<T> {

	T extractFromResultSet(ResultSet rs) throws SQLException;

	T makeUnique(Map<Long, T> cache, T teacher);
}
