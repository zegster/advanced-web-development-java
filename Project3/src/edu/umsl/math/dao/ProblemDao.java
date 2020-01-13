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
import edu.umsl.math.beans.Problem;

public class ProblemDao 
{
	private Connection connection;
	private PreparedStatement allProblem;
	private PreparedStatement allCategory;
	private PreparedStatement results;
	private PreparedStatement specificResults;
	private PreparedStatement problemCount;
	private PreparedStatement specificProblemCount;
	private PreparedStatement categoryCount;
	private PreparedStatement insertProblem;
	private PreparedStatement reassignCategory;
	private PreparedStatement keywordSearch;
	private PreparedStatement exactKeywordSearch;
	
	
	/* CONSTRUCTOR: Connecting to database and preparing a prepare statement */
	public ProblemDao() throws Exception 
	{
		try 
		{
			//Establish connection to our database
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mathprobdb", "root", "");

			//Getting all problems in our database
			allProblem = connection.prepareStatement("SELECT * FROM problem");
			
			//Getting all problems in our database
			allCategory = connection.prepareStatement("SELECT * FROM category");
			
			//Getting problems in the database base on given page range
			results = connection.prepareStatement("SELECT * FROM problem ORDER BY pid LIMIT ?, ?");
			
			//Getting specific problems in our database
			specificResults = connection.prepareStatement("SELECT * FROM problem, category WHERE problem.category_id = category.cid AND category.cid = ? ORDER BY pid LIMIT ?, ?");
			
			//Counting total number of problems in our database
			problemCount = connection.prepareStatement("SELECT COUNT(pid) FROM problem");
			
			//Counting total number of specific problem in our database
			specificProblemCount = connection.prepareStatement("SELECT COUNT(*) FROM problem, category WHERE problem.category_id = category.cid AND category.cid = ?");
			
			//Counting total number of categories in our database
			categoryCount = connection.prepareStatement("SELECT COUNT(cid) FROM category");
			
			//Inserting new problem to the database
			insertProblem = connection.prepareStatement("INSERT INTO problem (pid, content, category_id) VALUES (NULL, ?, ?)");
			
			//Reassigning existing category to a specific problem
			reassignCategory = connection.prepareStatement("UPDATE problem SET category_id = ? WHERE pid = ?");
			
			//Search by relevance
			keywordSearch = connection.prepareStatement("SELECT * FROM problem WHERE LOWER(content) REGEXP ?");
			
			//Search by exactly
			exactKeywordSearch = connection.prepareStatement("SELECT * FROM problem WHERE LOWER(content) LIKE ?");
		} 
		catch(Exception exception) 
		{
			exception.printStackTrace();
			throw new UnavailableException(exception.getMessage());
		}
	}
	
	
	/* getProblemCount(): Getting total number of problem in our database */
	public int getProblemCount(int category_id)
	{
		//===Variables===
		int count = 0;
		
		//Invoking query call: getting total number of problem in our database
		try
		{
			ResultSet rs = null;
			
			if(category_id <= 0)
			{
				//SELECT COUNT(pid) FROM problem
				rs = problemCount.executeQuery();
			}
			else
			{
				//SELECT COUNT(*) FROM problem, category WHERE problem.category_id = category.cid AND category.cid = ?
				specificProblemCount.setInt(1, category_id);
				rs = specificProblemCount.executeQuery();
			}
			
			rs.next();
			count = rs.getInt(1);
		}
		catch(SQLException sqlException)
		{
			sqlException.printStackTrace();
		}
		
		return count;
	}
	
	
	/* getProblemListByPage(): Getting problems in the database base on given page range */
	public List<Problem> getProblemListByPage(int page) throws SQLException 
	{
		//===Variables===
		List<Problem> problemlist = new ArrayList<Problem>();
		int startingPoint = 10 * (page - 1);	//NOTE: May start with zero
		
		//Invoking query call: getting math problems in the database base on given page range
		try
		{
			//SELECT * FROM problem ORDER BY pid LIMIT ?, ?
			results.setInt(1, startingPoint);
			results.setInt(2, 10);
			ResultSet resultsRS = results.executeQuery();

			while(resultsRS.next()) 
			{
				//NOTE: parameter is your database column index OR column name
				Problem problem = new Problem();
				problem.setPid(resultsRS.getInt("pid"));
				problem.setContent(resultsRS.getString("content"));
				problem.setCategory_id(resultsRS.getInt("category_id"));
				problemlist.add(problem);
			}
		} 
		catch(SQLException sqlException) 
		{
			sqlException.printStackTrace();
		}
		
		return problemlist;
	}
	
	
	/* getProblemListByPage(): Getting problems in the database base on given page range and category filter */
	public List<Problem> getProblemListByPageByFilter(int page, int filter) throws SQLException 
	{
		//===Variables===
		List<Problem> problemlist = new ArrayList<Problem>();
		int startingPoint = 10 * (page - 1);	//NOTE: May start with zero
		
		//Invoking query call: getting problems in the database base on given page range and category filter
		try
		{
			//SELECT * FROM problem, category WHERE problem.category_id = category.cid AND category.cid = ? ORDER BY pid LIMIT ?, ?
			specificResults.setInt(1, filter);
			specificResults.setInt(2, startingPoint);
			specificResults.setInt(3, 10);
			ResultSet resultsRS = specificResults.executeQuery();

			while(resultsRS.next()) 
			{
				//NOTE: parameter is your database column index OR column name
				Problem problem = new Problem();
				problem.setPid(resultsRS.getInt("pid"));
				problem.setContent(resultsRS.getString("content"));
				problem.setCategory_id(resultsRS.getInt("category_id"));
				problemlist.add(problem);
			}
		} 
		catch(SQLException sqlException) 
		{
			sqlException.printStackTrace();
		}
		
		return problemlist;
	}
	
	
	/* setProblem(): insert new problem into database */
	public int setProblem(String content, int category_id)
	{
		//===Variables===
		/* ERROR CODE:
			[0] No error
			[1] Empty content and invalid category_id
			[2] Empty content
			[3] Invalid category_id
			[3] Empty content and invalid category_id
			[4] Same question in same category
			[5] Query fail */
		List<Problem> problemlist = new ArrayList<Problem>();
		List<Category> categorylist = new ArrayList<Category>();
		String inputContent = content;
		int ccount = 0;
		
		//Invoking query call: getting total number of categories in our database
		try
		{
			ResultSet rs = null;
			
			//SELECT COUNT(cid) FROM category
			rs = categoryCount.executeQuery();
			rs.next();
			ccount = rs.getInt(1);
		}
		catch(SQLException sqlException)
		{
			sqlException.printStackTrace();
		}
		
		//Check if content is empty AND category_id is valid
		//Check if category_id is valid
		//Check if content is empty
		if(inputContent.trim().isEmpty() && (category_id <= 0 || category_id > ccount)) 
		{
			return 1;
		}
		else if(inputContent.trim().isEmpty())
		{
			return 2;
		}
		else if(category_id <= 0 || category_id > ccount)
		{
			return 3;
		}
		
		
		//Invoking query call: Getting all problems in our database 
		try 
		{
			ResultSet resultsRS = allProblem.executeQuery();

			while(resultsRS.next()) 
			{
				//NOTE: parameter is your database column index OR column name
				Problem problem = new Problem();
				problem.setPid(resultsRS.getInt("pid"));
				problem.setContent(resultsRS.getString("content"));
				problem.setCategory_id(resultsRS.getInt("category_id"));
				problemlist.add(problem);
			}
		} 
		catch(SQLException sqlException) 
		{
			sqlException.printStackTrace();
		}
		
		
		//Invoking query call: Getting all category name and category id in the database 
		try 
		{
			ResultSet resultsRS = allCategory.executeQuery();

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
		
		
		//Check if content is in same category is submitted twice
		for(Problem problem : problemlist)
		{
			for(Category category : categorylist)
			{
				if(content.equals(problem.getContent()) && category_id == category.getCid())
				{
					return 4;
				}
			}
		}
		
		
		//Invoking query call: insert new problem into database
		try
		{
			//INSERT INTO problem (pid, content, category_id) VALUES (NULL, ?, ?)
			insertProblem.setString(1, content);
			insertProblem.setInt(2, category_id);
			int affected = insertProblem.executeUpdate();
			
			if(affected <= 0)
			{
				return 5;
			}
		}
		catch(SQLException sqlException)
		{
			sqlException.printStackTrace();
		}
		
		return 0;
	}
	
	
	/* resetCategory(): update category of a specific problem */
	public int resetCategory(int pid, int category_id)
	{
		//===Variables===
		/* ERROR CODE:
			[0] No error
			[1] Same question in same category
			[2] Query fail */
		List<Problem> problemlist = new ArrayList<Problem>();
		List<Category> categorylist = new ArrayList<Category>();
		
		
		//Invoking query call: Getting all problems in our database 
		try 
		{
			ResultSet resultsRS = allProblem.executeQuery();

			while(resultsRS.next()) 
			{
				//NOTE: parameter is your database column index OR column name
				Problem problem = new Problem();
				problem.setPid(resultsRS.getInt("pid"));
				problem.setContent(resultsRS.getString("content"));
				problem.setCategory_id(resultsRS.getInt("category_id"));
				problemlist.add(problem);
			}
		} 
		catch(SQLException sqlException) 
		{
			sqlException.printStackTrace();
		}
		
		
		//Invoking query call: Getting all category name and category id in the database 
		try 
		{
			ResultSet resultsRS = allCategory.executeQuery();

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
		
		
		//Check if problem is in same category
		for(Problem problem : problemlist)
		{
			if(pid == problem.getPid() && category_id == problem.getCategory_id())
			{
				return 1;
			}
		}
		
		
		//Invoking query call: insert new problem into database
		try
		{
			//UPDATE problem SET category_id = ? WHERE pid = ?
			reassignCategory.setInt(1, category_id);
			reassignCategory.setInt(2, pid);
			int affected = reassignCategory.executeUpdate();
			
			if(affected <= 0)
			{
				return 2;
			}
		}
		catch(SQLException sqlException)
		{
			sqlException.printStackTrace();
		}
				
		return 0;
	}


	/* getProblemListByKeyword(): Search by relevance for problems in the database base on given keyword */
	public List<Problem> getProblemListByKeyword(String keyword) throws SQLException 
	{
		//===Variables===
		List<Problem> problemlist = new ArrayList<Problem>();
		
		
		//Invoking query call: search by relevance for problems in the database base on given keyword
		try
		{
			//SELECT * FROM problem WHERE LOWER(content) REGEXP ?
			String toSearch = keyword.trim();
			toSearch = toSearch.replace(' ', '|');
			keywordSearch.setString(1, toSearch);
			ResultSet resultsRS = keywordSearch.executeQuery();

			while(resultsRS.next()) 
			{
				//NOTE: parameter is your database column index OR column name
				Problem problem = new Problem();
				problem.setPid(resultsRS.getInt("pid"));
				problem.setContent(resultsRS.getString("content"));
				problem.setCategory_id(resultsRS.getInt("category_id"));
				problemlist.add(problem);
			}
		} 
		catch(SQLException sqlException) 
		{
			sqlException.printStackTrace();
		}
		
		return problemlist;
	}
	
	
	/* getProblemListByExactKeyword(): Search by exact for problems in the database base on given keyword */
	public List<Problem> getProblemListByExactKeyword(String keyword) throws SQLException 
	{
		//===Variables===
		List<Problem> problemlist = new ArrayList<Problem>();
		
		
		//Invoking query call: search by exact for problems in the database base on given keyword
		try
		{
			//SELECT * FROM problem WHERE LOWER(content) LIKE ?
			String toSearch = "%" + keyword + "%";
			exactKeywordSearch.setString(1, toSearch);
			ResultSet resultsRS = exactKeywordSearch.executeQuery();

			while(resultsRS.next()) 
			{
				//NOTE: parameter is your database column index OR column name
				Problem problem = new Problem();
				problem.setPid(resultsRS.getInt("pid"));
				problem.setContent(resultsRS.getString("content"));
				problem.setCategory_id(resultsRS.getInt("category_id"));
				problemlist.add(problem);
			}
		} 
		catch(SQLException sqlException) 
		{
			sqlException.printStackTrace();
		}
		
		return problemlist;
	}
}
