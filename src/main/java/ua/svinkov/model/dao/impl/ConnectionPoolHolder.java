package ua.svinkov.model.dao.impl;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ConnectionPoolHolder {
    private static volatile DataSource dataSource;
    
    public static DataSource getDataSource() {
        if (dataSource == null){
            synchronized (ConnectionPoolHolder.class) {
                if (dataSource == null) {
        			DataSource ds = null;
					try {
						Context initContext = new InitialContext();
	        			Context envContext  = (Context)initContext.lookup("java:/comp/env");
						ds = (DataSource)envContext.lookup("jdbc/ST4DB");
					} catch (NamingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                    dataSource = ds;
                }
            }
        }
        return dataSource;

    }


}
