package dbConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import base.Testbase;

public class DataBaseConnection extends Testbase {

	public static Connection con;

	public static Connection getDBConnection(String dbUserName,
			String dbPassword) throws SQLException, ClassNotFoundException {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		con = DriverManager.getConnection("jdbc:oracle:thin:@" + tmdbServerIp
				+ ":" + tmdbServerPort + "" + tmdbServiceName, dbUserName,
				dbPassword);
		con.setAutoCommit(true);
		System.out.println("DB Connection Created" + tmdbServerIp
				+ tmdbServiceName + "" + tmdbUserName + "" + tmdbPassword);
		return con;
	}

	public static Connection getDBConnectionUniversal(String dbIP,
			String dbPort, String dbUserName, String dbPassword,
			String serviceName) throws SQLException, ClassNotFoundException {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		con = DriverManager.getConnection("jdbc:oracle:thin:@" + dbIP + ":"
				+ dbPort + "" + serviceName, dbUserName, dbPassword);
		con.setAutoCommit(true);
		System.out.println("DB Connection Created" + dbIP + serviceName + ""
				+ dbUserName + "" + dbPassword);
		return con;
	}

	public static void closeConnection() throws SQLException {
		try {
			con.close();
		} finally {
			con.close();
		}

	}

	public static Map<String, String> executeSelectQuery(String sqlQuery,
			String tmUserName, String tmPassword) throws SQLException {

		Map<String, String> resultData = new HashMap<String, String>();
		Statement stmt = null;
		ResultSet resultset2 = null;
		ResultSetMetaData resultColumn = null;
		try {
			stmt = DataBaseConnection.getDBConnection(tmUserName, tmPassword)
					.createStatement();
			resultset2 = stmt.executeQuery(sqlQuery);
			// resultset2.next();
			resultColumn = resultset2.getMetaData();
			int size=resultset2.getFetchSize();
			System.out.println("RESULT COLUMN==" + resultColumn);
			int columns = resultColumn.getColumnCount();
			System.out.println("COLUMNS==" + size);
			resultset2.next();
			for (int x = 1; x <= columns; x++) {
				/*System.out.println(resultColumn.getColumnName(x) + " ------ "
						+ resultset2.getString(x));*/
				resultData.put(resultColumn.getColumnName(x),
						resultset2.getString(x));
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		} finally {
			stmt.close();
			resultset2.close();
			DataBaseConnection.closeConnection();
		}
		return resultData;

	}

	public static Map<String, String> executeSelectQueryUniversal(
			String sqlQuery, String dbIP, String dbPort, String tmUserName,
			String tmPassword, String serviceName) throws SQLException {

		Map<String, String> resultData = new HashMap<String, String>();
		Statement stmt = null;
		ResultSet resultset2 = null;
		ResultSetMetaData resultColumn = null;
		try {
			stmt = DataBaseConnection.getDBConnectionUniversal(dbIP, dbPort,
					tmUserName, tmPassword, serviceName).createStatement();
			resultset2 = stmt.executeQuery(sqlQuery);
			// resultset2.next();
			resultColumn = resultset2.getMetaData();
			System.out.println("RESULT COLUMN==" + resultColumn);
			int columns = resultColumn.getColumnCount();
			System.out.println("COLUMNS==" + columns);
			resultset2.next();
			for (int x = 1; x <= columns; x++) {
				System.out.println(resultColumn.getColumnName(x) + " ------ "
						+ resultset2.getString(x));
				resultData.put(resultColumn.getColumnName(x),
						resultset2.getString(x));
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		} finally {
			stmt.close();
			resultset2.close();
			DataBaseConnection.closeConnection();
		}
		return resultData;

	}

	public static void main(String args[]) throws SQLException {
		DataBaseConnection ds=new DataBaseConnection();
		ds.executeSelectQuery(SqlQueries.Queries.macEquipmentQuery, "SVT_TMLIVE_184_27NOV17", "SVT_TMLIVE_184_27NOV17");
	}
}
