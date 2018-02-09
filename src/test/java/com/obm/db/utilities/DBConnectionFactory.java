package com.obm.db.utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;

public class DBConnectionFactory {

	private static Connection connection;

	private DBConnectionFactory() {
	}

	private static XMLConfiguration config = null;

	public static Connection getConnection() {

		try {

			if (connection != null && (!connection.isClosed()))
				return connection;

			config = new XMLConfiguration("configurations/object-config.xml");

			String url = config.getString("database.jdbcurl");
			System.out.println("url:" + url);
			String username = config.getString("database.username");
			System.out.println("username:" + username);

			String password = config.getString("database.password");
			System.out.println("password:" + password);
			Class.forName(config.getString("database.driverclass"));
			connection = DriverManager.getConnection(url, username, password);

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (ConfigurationException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}
}
