package edu.umsl.math.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.UnavailableException;

import edu.umsl.math.beans.Category;

public class CategoryDao 
{
	private Connection connection;
	private PreparedStatement results;
	private PreparedStatement insertCategory;
	
	
	/* CONSTRUCTOR: Connecting to database and preparing a prepare statement */
	public CategoryDao() throws Exception 
	{
		try 
		{
			//Establish connection to our database
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mathprobdb", "root", "");

			//Getting all categories in our database
			results = connection.prepareStatement("SELECT * FROM category");
			
			//Inserting new problem to the database
			insertCategory = connection.prepareStatement("INSERT INTO category (cid, name) VALUES (NULL, ?)");
		} 
		catch(Exception exception) 
		{
			exception.printStackTrace();
			throw new UnavailableException(exception.getMessage());
		}
	}
	
	
	/* getCategoryList(): Getting all category name and category id in the database  */
	public List<Category> getCategoryList() throws SQLException 
	{
		//===Variables===
		List<Category> categorylist = new ArrayList<Category>();
		
		//Invoking query call: Getting all category name and category id in the database 
		try 
		{
			ResultSet resultsRS = results.executeQuery();

			while(resultsRS.next()) 
			{
				//NOTE: parameter is your database column index OR column name
				Category category = new Category();
				category.setCid(resultsRS.getInt("cid"));
				category.setName(resultsRS.getString("name"));
				categorylist.add(category);
			}
		} 
		catch(SQLException sqlException) 
		{
			sqlException.printStackTrace();
		}
		
		
		return categorylist;
	}
	
	
	/* setCategory(): insert new category into database */
	public int setCategory(String name)
	{
		//===Variables===
		/* ERROR CODE:
			[0] No error
			[1] Empty content
			[2] Duplicate category
			[3] Query fail */
		List<Category> categorylist = new ArrayList<Category>();
		String inputName = name;
		
		//Invoking query call: Getting all category name and category id in the database 
		try 
		{
			ResultSet resultsRS = results.executeQuery();

			while(resultsRS.next()) 
			{
				//NOTE: parameter is your database column index OR column name
				Category category = new Category();
				category.setCid(resultsRS.getInt("cid"));
				category.setName(resultsRS.getString("name"));
				categorylist.add(category);
			}
		} 
		catch(SQLException sqlException) 
		{
			sqlException.printStackTrace();
		}
				
		//Checking for empty
		if(inputName.trim().isEmpty()) 
		{
			return 1;
		}
		
		//Checking for duplicate
		for(Category category : categorylist)
		{
			if(name.equalsIgnoreCase(category.getName()))
			{
				return 2;
			}
		}
		
		//Invoking query call: insert new category name to database
		try
		{
			insertCategory.setString(1, name.toLowerCase());
			int affected = insertCategory.executeUpdate();
			
			if(affected <= 0)
			{
				return 3;
			}
		}
		catch(SQLException sqlException)
		{
			sqlException.printStackTrace();
		}
		
		return 0;
	}
}
