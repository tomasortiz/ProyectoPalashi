package com.amclos.dao;

import java.sql.*;
import java.util.Vector;



/**
 * An SqlHandler is responsible for establishing connection to the database and executing any SQL statements on that connection.
 * @author Ava Prieto, Dominic P. Diamante
 * @version 1.6
 * @since 1.1
 */
public class SqlHandler {
	
	public static final String TIPO_VARCHAR = "VARCHAR";
	public static final String TIPO_NUMBER = "NUMBER";
	
	private String driverName;
	private String url;
	private String username;
	private String password;
	private Statement SQLSelect;
	private Connection conn;
		
	/**
	 * Instantiates a new SqlHandler object.
	 */
	public SqlHandler() {
	}
	
	/**
	 * Establish connection to the database using the values in 'ini.properties' file.
	 */
	public void establishConnection()  {
		try {
			driverName = "oracle.jdbc.OracleDriver";
			url = "jdbc:oracle:thin:@54.187.36.111:1521:XE";
			username = "palashi";
			password = "palashi";			
			Class.forName(driverName);
			conn = DriverManager.getConnection(url, username, password);
			conn.setAutoCommit(false);
				
		} catch (Exception e) {
                    e.printStackTrace();
		}
	}
	
	/**
	 * Executes an insert statement to the database.
	 * @param insertStatement The SQL Insert Statement to execute.
	 * @return <CODE>true</CODE> if the insert statement is successful,
	 * <CODE>false</CODE> otherwise.
	 */
	public boolean insertStatement(String insertStatement) {
		
		try {
			Statement SQLInsert = conn.createStatement();

			int c = SQLInsert.executeUpdate(insertStatement);
			SQLInsert.close();	
			if (c>0) return true;
			else return false;
			
		} catch (SQLException e){
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Executes an SQL Delete statement to the database.
	 * @param deleteStatement The delete statement to be executed.
	 * @return <CODE>true</CODE> if the delete statement is successfully executed, 
	 * <CODE>false</CODE> otherwise.
	 */
	public boolean deleteStatement(String deleteStatement) {
		try {
			Statement SQLDelete = conn.createStatement();
			SQLDelete.executeUpdate(deleteStatement);
			SQLDelete.close();
			commitTransactions();
			
			return true;
			
		} catch (SQLException e){
			e.printStackTrace();
			commitTransactions();
			return false;
		} catch (Exception e){
			e.printStackTrace();
			commitTransactions();
			return false;
		}
	}
	
	public void closeSelectStatement(){
		try {
			SQLSelect.close();
		} catch (SQLException e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Executes an SQL Select statement to the database.
	 * @param selectStatement The select statement to be executed.
	 * @return <CODE>ResultSet</CODE> the resultset containing the data produced by the query 
	 */
	public ResultSet selectStatement(String selectStatement) {
		ResultSet rs = null;
		try {
			SQLSelect = conn.createStatement();
			SQLSelect.setFetchSize(1000);
			rs = SQLSelect.executeQuery(selectStatement);
			return rs;
			
		} catch (SQLException e){
			e.printStackTrace();
			return rs;
		} catch (Exception e) {
			e.printStackTrace();
			return rs;
		}
	}
	
	/**
	 * Commits the transactions made by this SqlHandler object.
	 */
	public void commitTransactions() {
		try {
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Rolls back the transactions made by this SqlHandler object.
	 *
	 */
	public void rollbackTransactions() {
		try {
			conn.rollback();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Closes the database connection made by this SqlHandler object.
	 *
	 */
	public void closeConnection() {
		try {
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	/**
		 * Retrieves the number of records that will be retrieved from the database.
		 * 
		 * @param query Sql statement used for the retrieval of number of records
		 * @return the number of records for retrieval
		 * @throws Exception
		 */

	public int getTotalRecordCount(String query) throws Exception {		
		
		ResultSet rs = null;
		int count=0;
		try {
			SQLSelect = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = SQLSelect.executeQuery(query);
			while(rs.next()) {
				count = rs.getInt(1);
			}
			
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		
		return count;
	}
	
	public Connection getConnection() {
		return conn;
	}
	
	public boolean updateStatement(String statement) {
		try {
			Statement SQLUpdate = conn.createStatement();
		
			SQLUpdate.executeUpdate(statement);
			SQLUpdate.close();
			
			return true;
			
		} catch (SQLException e){
			e.printStackTrace();
			return false;
		} catch (Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	public void executeBatchStatement(Vector batchStmt) throws Exception {
		String sql;
		int statements;
		
		
		Statement sqlStmt = conn.createStatement();
		statements = 0;
		
		for (int i = 0; i < batchStmt.size(); i++) {
			sql = batchStmt.get(i).toString();
			sqlStmt.addBatch(sql);
			statements++;
		}
			
		System.out.println("....." + statements + " statements collected as batch.  Executing statements");
		
		sqlStmt.executeBatch();
		sqlStmt.close();
	}
	
	public void executeBatchPreparedStatement(String statement, Vector parameterLists, String[] columnTypes) throws Exception {
		System.out.println("....." + statement);
		String value;
		
		PreparedStatement pstmt = conn.prepareStatement(statement);
		Vector parameters = new Vector();
				
		for (int listCnt = 0; listCnt < parameterLists.size(); listCnt++) {
			
			parameters = (Vector)parameterLists.get(listCnt);
						
			for (int paramCnt = 0; paramCnt < parameters.size(); paramCnt++) {
				value = parameters.get(paramCnt).toString();
				
				if (columnTypes[paramCnt].equals(TIPO_VARCHAR)) {
					pstmt.setString(paramCnt + 1, value);
				} else if (columnTypes[paramCnt].equals(TIPO_NUMBER)) {
					pstmt.setDouble(paramCnt + 1, Double.parseDouble(value));
				}
				
			}
			
			pstmt.addBatch();
			
		}
		
		pstmt.executeBatch();
		pstmt.close();
	}
}
