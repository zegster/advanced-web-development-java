package edu.umsl.math.web;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.umsl.math.dao.ProblemDao;


//http://localhost:8080/Project3/
@WebServlet("/create_problem")
public class CreateProblemServlet extends HttpServlet 
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
		ProblemDao problemdao = null;
		String content = request.getParameter("content");
		String category = request.getParameter("category");
		int category_id = 0;
		boolean isError = false;
		String errorMessage = "Request was deny by the server.";
		String successMessage = null;
	
		
		//Primary check for null OR invalid submission
		if(content == null || content.isEmpty())
		{
			if(category == null)
			{
				errorMessage = "Empty input for problem AND invalid input for category is not allow!";
			}
			else
			{
				errorMessage = "Empty input for problem is not allow!";
			}
			
			request.setAttribute("isError", 1);
			request.setAttribute("errorMessage", errorMessage);
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("/list_math");
			dispatcher.forward(request, response);
			return;
		}
		else if(category == null)
		{
			errorMessage = "Invalid input for category is not allow!";
			request.setAttribute("isError", 1);
			request.setAttribute("errorMessage", errorMessage);
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("/list_math");
			dispatcher.forward(request, response);
			return;
		}
		 
		
		//Get the submit new problem from the form. Convert from String to integer type: [category]
		try 
		{
			category_id = Integer.parseInt(category);
		} 
		catch (Exception e) 
		{
			category_id = 0;
			e.printStackTrace();
		}
		
		
		//DATEBASE CONNECTION: Trying to access database
		try 
		{	
			//Inserting new problem into database
			problemdao = new ProblemDao();
			int statusCode = problemdao.setProblem(content, category_id);

			
			//Check return status, secondary check for null OR invalid submission
			if(statusCode == 1)
			{
				isError = true;
				errorMessage = "Empty input for problem AND invalid input for category is not allow!";
			}
			else if(statusCode == 2)
			{
				isError = true;
				errorMessage = "Empty input for problem is not allow!";
			}
			else if(statusCode == 3)
			{
				isError = true;
				errorMessage = "Invalid input for category is not allow!";
			}
			else if(statusCode == 4)
			{
				isError = true;
				errorMessage = "Duplicate problem in same category is not allow!";
			}
			else if(statusCode == 5)
			{
				isError = true;
				errorMessage = "Something went wrong... ERROR CODE: 5";
			}
			else
			{
				successMessage = "New problem has been added.";
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
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("/list_math");
			dispatcher.forward(request, response);
		}
		else
		{
			if(successMessage != null)
			{
				request.setAttribute("isSuccess", 1);
				request.setAttribute("successMessage", successMessage);
			}
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("/list_math");
			dispatcher.forward(request, response);
		}
	}
}
