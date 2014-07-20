package com.martin.zkedit.dao;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import org.flywaydb.core.api.FlywayException;
import org.flywaydb.core.internal.util.jdbc.DriverDataSource;

public class OpenConnectionCountDriverDataSource extends DriverDataSource {
	/**
     * The number of connections currently open.
     */
    private int openConnectionCount = 0;
    private static OpenConnectionCountDriverDataSource openConnectionCountDriverDataSource = null;

    private OpenConnectionCountDriverDataSource() {
        super(Thread.currentThread().getContextClassLoader(), null, "jdbc:h2:mem:flyway_db_open_connection;DB_CLOSE_DELAY=-1", "sa", "");
    }

	private OpenConnectionCountDriverDataSource(ClassLoader classLoader, String driverClass, String url, String user, String password,
			String... initSqls) throws FlywayException {
		super(classLoader, driverClass, url, user, password, initSqls);
	}
	public static OpenConnectionCountDriverDataSource getInstance(Object ...args) {
		if (null != openConnectionCountDriverDataSource) {
			return openConnectionCountDriverDataSource;
		}
		
		if (null != args && args.length > 0) {
			openConnectionCountDriverDataSource = new OpenConnectionCountDriverDataSource((ClassLoader)args[0], (null != args[1]) ? (String.valueOf(args[1])) : null, String.valueOf(args[2]), String.valueOf(args[3]), String.valueOf(args[4]));
		}else {
			openConnectionCountDriverDataSource = new OpenConnectionCountDriverDataSource();
		}
		return openConnectionCountDriverDataSource;
	}
	/**
	 * @return The number of connections currently open.
	 */
	public int getOpenConnectionCount() {
		return openConnectionCount;
	}
	
	@Override
    protected Connection getConnectionFromDriver(String username, String password) throws SQLException {
        final Connection connection = super.getConnectionFromDriver(username, password);

        openConnectionCount++;

        return (Connection) Proxy.newProxyInstance(getClass().getClassLoader(), new Class[]{Connection.class}, new InvocationHandler() {
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                if ("close".equals(method.getName())) {
                    openConnectionCount--;
                }
                return method.invoke(connection, args);
            }
        });
    }
}