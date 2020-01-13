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
@WebServlet("/reassign_category")
public class ReassignCategoryServlet extends HttpServlet 
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
		boolean isError = false;
		String errorMessage = "Request was deny by the server.";
		String successMessage = null;
		
		//Get the reassign category submission from the form
		String[] reassignCategory = request.getParameter("reassigncategory").split(",");
		int pid = 0;
		int category_id = 0;
		
		
		//getParameter always result as string type, thus converting string to integer type
		try 
		{
			pid = Integer.parseInt(reassignCategory[0]);
			category_id = Integer.parseInt(reassignCategory[1]);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			return;
		}
		
		
		//Trying to access database
		try 
		{	
			//Creating object...
			problemdao = new ProblemDao();
			
			//Inserting new problem into database
			int statusCode = problemdao.resetCategory(pid, category_id);
			
			//Check return status
			if(statusCode == 1)
			{
				isError = true;
				errorMessage = "Problem in same category is not allow!";
			}
			else if(statusCode == 2)
			{
				isError = true;
				errorMessage = "Something went wrong... ERROR CODE: 2";
			}
			else
			{
				successMessage = "Problem's category was reassigned.";
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
			request.setAttribute("successMessage", successMessage);
			request.setAttribute("isGet", "false");

			RequestDispatcher dispatcher = request.getRequestDispatcher("/list_math");
			dispatcher.forward(request, response);
		}
	}
}
