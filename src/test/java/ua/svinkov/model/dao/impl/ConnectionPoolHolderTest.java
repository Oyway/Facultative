package ua.svinkov.model.dao.impl;

import static org.junit.jupiter.api.Assertions.*;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ConnectionPoolHolderTest {
	
	@BeforeEach
	void setUp() throws Exception {
		BasicDataSource ds = new BasicDataSource();
        ds.setUrl("jdbc:mysql://localhost:3306/facultative?useSSL=false");
        ds.setUsername("root");
        ds.setPassword("root");
        ds.setMinIdle(5);
        ds.setMaxIdle(10);
        ds.setMaxOpenPreparedStatements(100);
		ConnectionPoolHolder.setDataSource(ds);
	}
	
	@Test
	void testGetDataSource() {
		DataSource dataSource = ConnectionPoolHolder.getDataSource();
		assertNotNull(dataSource);
	}

}
