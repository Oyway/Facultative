package ua.svinkov.model.dao.impl;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * Extracts data source from context.xml
 * 
 * @author R.Svinkov
 *
 */
public class ConnectionPoolHolder {
	private static volatile DataSource dataSource;

	/**
	 * Returns data source
	 * 
	 * @return data source
	 */
	public static DataSource getDataSource() {
		if (dataSource == null) {
			synchronized (ConnectionPoolHolder.class) {
				if (dataSource == null) {
					DataSource ds = null;
					try {
						Context initContext = new InitialContext();
						Context envContext = (Context) initContext.lookup("java:/comp/env");
						ds = (DataSource) envContext.lookup("jdbc/Facultative");
					} catch (NamingException e) {
						throw new RuntimeException(e);
					}
					dataSource = ds;
				}
			}
		}
		return dataSource;
	}

	public static void setDataSource(DataSource ds) {
		if (dataSource == null)
			dataSource = ds;
	}

}
