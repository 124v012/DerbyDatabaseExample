package com.unknown.derbydbxmpl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.NumberFormat;

/*
 * download derby from: http://db.apache.org/derby/derby_downloads.htm
 * to use this drivermanager in eclipse go to Project > Properties, the go to
 * Java Build Path menu. In Libraries tab click Add External JARs.. button and
 * search dearby.jar on your computer, to add it to libraries
 */

public class DerbyDBExample {

	public static void main(String[] args) {

		/*
		 * use this line to define which database protocol want to use
		 * create=true line means if the ExampleDatabase is not exists,
		 * it will be created.
		 */
		final String CONNECTION = "jdbc:derby:ExampleDatabase;create=true";

		/*
		 * use try-resources to handle the database connection easly.
		 * if you declare your connection and your statement in the
		 * try-resource parentheses it will be closed all connetion
		 * automatically after the program is quit from the try-catch block 
		 */
		try(Connection connection = DriverManager.getConnection(CONNECTION);
				Statement statement = connection.createStatement()){
			//create database with executeUpdate
			statement.executeUpdate("CREATE TABLE exampledb (name VARCHAR(45) NOT NULL PRIMARY KEY, address VARCHAR(45), balance FLOAT)");
			System.out.println("Database created");
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		 /*
		  * put data in the database, use the same try-resource
		  */
		try(Connection connection = DriverManager.getConnection(CONNECTION);
				Statement statement = connection.createStatement()){
			//put data in database with executeUpdate
			statement.executeUpdate("INSERT INTO exampledb VALUES ('Example Bob', 'Example street 222', 23.0)");
			statement.executeUpdate("INSERT INTO exampledb VALUES ('Example John', 'Example street 21', 54.3)");
			System.out.println("Database updated");
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		/*
		 * query data from database, use the same try-resource with executeQuery
		 */
		try(Connection connection = DriverManager.getConnection(CONNECTION);
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery("SELECT * FROM exampledb")){
			//query data from database into a ResultSet
			while(resultSet.next()) {
				System.out.println(resultSet.getString("name"));
				System.out.println(resultSet.getString("address"));
				//change the currency
				System.out.println(NumberFormat.getCurrencyInstance().format(resultSet.getFloat("balance")));
			}
			System.out.println("Database query completed");
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		/*
		 * delete table from database with executeUpdate
		 */
		try(Connection connection = DriverManager.getConnection(CONNECTION);
				Statement statement = connection.createStatement()){
			statement.executeUpdate("DROP TABLE exampledb");
			System.out.println("Database deleted");
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}

}
