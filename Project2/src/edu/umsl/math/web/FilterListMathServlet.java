package edu.umsl.math.web;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


//http://localhost:8080/Project3/
@WebServlet("/filter_list_math")
public class FilterListMathServlet extends HttpServlet 
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
		String categoryFilter = request.getParameter("categoryfilter");
		request.setAttribute("filter", categoryFilter);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/list_math");
		dispatcher.forward(request, response);
	}
}
