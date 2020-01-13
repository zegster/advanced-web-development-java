package edu.umsl.math.web;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.umsl.math.dao.CategoryDao;


//http://localhost:8080/Project3/
@WebServlet("/create_category")
public class CreateCategoryServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
	//Handles the GET request. It is invoked by the web container (URL).
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		//Send users back to the home page when trying to access this page through URL
		response.sendRedirect("list_math");
	}

	//Handles the POST request. It is invoked by the web container.
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		//===Variables===
		CategoryDao categorydao = null;
		boolean isError = false;
		String errorMessage = "Request was deny by the server.";
		
		
		//Get the submit new category from the form
		String category = request.getParameter("category");
		
		
		//Trying to access database
		try 
		{	
			//Creating object...
			categorydao = new CategoryDao();
			
			//Inserting new problem into database
			int statusCode = categorydao.setCategory(category);
			
			//Check return status
			if(statusCode == 1)
			{
				isError = true;
				errorMessage = "Empty input for category is not allow!";
			}
			else if(statusCode == 2)
			{
				isError = true;
				errorMessage = "Duplicate category is not allow!";
			}
			else if(statusCode == 3)
			{
				isError = true;
				errorMessage = "Something went wrong... ERROR CODE: 3";
			}
		} 
		catch(Exception e) 
		{
			e.printStackTrace();
		}
		
		
		//ERROR HANDLING: Determine if there's error or not and forward the information appropriately
		if(isError)
		{
			request.setAttribute("isError", 1);
			request.setAttribute("errorMessage", errorMessage);
			request.setAttribute("isGet", "false");
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("/list_math");
			dispatcher.forward(request, response);
		}
		else
		{
			request.setAttribute("isSuccess", 1);
			request.setAttribute("successMessage", "New category has been added.");
			request.setAttribute("isGet", "false");

			RequestDispatcher dispatcher = request.getRequestDispatcher("/list_math");
			dispatcher.forward(request, response);
		}
	}
}
