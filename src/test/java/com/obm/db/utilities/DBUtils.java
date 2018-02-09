package com.obm.db.utilities;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.RowProcessor;

public class DBUtils {

	public static List<String> getStringResultRows(String query) throws ConfigurationException {
		Connection connection = null;
		Statement stmt = null;
		ResultSet rs = null;
		List<String> resultStringRows = new ArrayList<String>();
		try {
			XMLConfiguration config = new XMLConfiguration("configurations/object-config.xml");
			query = config.getString(query);
			System.out.println("query:" + query);
			connection = DBConnectionFactory.getConnection();
			stmt = connection.createStatement();
			rs = stmt.executeQuery(query);
			RowProcessor rp = new BasicRowProcessor();
			while (rs.next()) {
				Map<String, Object> m = rp.toMap(rs);

				List<String> sortedKeys = new ArrayList<String>(m.keySet());
				Collections.sort(sortedKeys);
				System.out.println("sorted Keys:" + sortedKeys);
				String rowValue = "";
				for (String key : sortedKeys) {
					rowValue = rowValue + " " + m.get(key);
				}
				rowValue = rowValue.trim();
				System.out.println("rowValue:"+rowValue);
				// String s = m.toString();
				// TODO: below line needs to be refactored. What if a field has comma as a value
				String values = m.values().toString();

				// System.out.println(s);
				System.out.println(values);
				values = values.replaceAll("\\[", "").replaceAll("\\]", "");
				System.out.println(values);
				resultStringRows.add(rowValue);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				stmt.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return resultStringRows;
	}

	public static void main(String args[]) throws ConfigurationException {
		getStringResultRows("select id, name, created_at, updated_at from admin_roles");
	}
}
