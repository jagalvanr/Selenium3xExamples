package com.db.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.mysql.cj.jdbc.result.ResultSetMetaData;

//import com.mysql.jdbc.ResultSetMetaData;

public class ConnectionManager_MySql
{
	private static String url = "jdbc:mysql://localhost:3306/testdb?serverTimezone=UTC";  
	private static String hostName = "localhost";
	private static String port = "3306";
	private static String dataBase = "maldonado_fox_03";
	private static String driverName = "com.mysql.cj.jdbc.Driver";   
	private static String username = "root";   
	private static String password = "we238185$MSL";
	private static Connection con;
	private String[][] inputArr;
	private static String query;
	/*
	 * 	<dependency>
			<groupId>mysql</groupId>
			<artifactId>mysql-connector-java</artifactId>
			<version>5.1.21</version>
		</dependency>
	 */

	public String[][]  readDB( ) 
	{
		try {
			Class.forName(driverName);
			try {
				Statement stmt=con.createStatement();
				//Executing the Queries
				//stmt.executeUpdate("INSERT INTO testDB.employee VALUES ('john',2000)");
				//stmt.executeUpdate("truncate table testDB.employee");
				
				ResultSet rs = stmt.executeQuery("Select usuario,nombre from usuarios;");
				rs.last();
				int rows = rs.getRow();
				 
				ResultSetMetaData rsmd = (ResultSetMetaData) rs.getMetaData();
				int cols = rsmd.getColumnCount();
				System.out.println(rows +"--" + cols);
				inputArr= new String[rows][cols];

				int i =0;
				rs.beforeFirst();
				//Iterating the data in the Table
				while (rs.next())
				{
					for(int j=0;j<cols;j++)
					{
						inputArr[i][j]=rs.getString(j+1);
						System.out.print("values:: " + inputArr[i][j] +":::"+i +":::"+j); 

					}
					System.out.println();
					i++;
				}
			} catch (SQLException ex) {
				ex.printStackTrace();
				System.out.println("Failed to create the database connection."); 
			}
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
			System.out.println("Driver not found."); 
		}
		return inputArr;

	}

	@DataProvider(name="DP")
	public String[][] feedDP()
	{
		String data[][]=readDB();
		for(int i=0;i<data.length;i++)
		{
			for(int j=0;j<data[i].length;j++)
			{
				System.out.println("in for :: " + data[i][j]);
			}
		}
		
		return data;
	}
	
	
	@BeforeTest
	@Parameters({"hostname","port","database","userdb","password","query"})
	public void setupConnection(@Optional("localhost") String hostName ,
								@Optional("3306") String port,
								@Optional("maldonado_fox_03") String dataBase,
								@Optional("root") String userName,
								@Optional("Stk.001") String password,
								@Optional("select * from users;") String query) throws ClassNotFoundException {
		
		query = query;
		try {
			Class.forName(driverName);

				//Create a connection to DB by passing Url,Username,Password as parameters
				url = "jdbc:mysql://" + hostName + ":" + port + "/" + dataBase + "?serverTimezone=UTC";
				con = DriverManager.getConnection(url, username, password);
			} catch (SQLException ex) {
				ex.printStackTrace();
				System.out.println("Failed to create the database connection."); 
			}
		}
	
	@Test()
	public void login()
	{
		String data[][]=readDB();
		for(int i=0;i<data.length;i++)
		{
			for(int j=0;j<data[i].length;j++)
			{
				System.out.println("in for :: " + data[i][j]);
			}
		}

	}
	
 
}