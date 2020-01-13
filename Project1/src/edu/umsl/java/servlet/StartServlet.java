package edu.umsl.java.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//http://localhost:8080/Project1/Start
//Servlet implementation class StartServlet
@WebServlet("/Start")
public class StartServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;

	//Handles the GET request. It is invoked by the web container.
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		//Retrieve the user's operating system information from the request headers
		String userAgent = request.getHeader("User-Agent");
		String os = "";

		if (userAgent.toLowerCase().indexOf("windows") >= 0) 
		{
			os = "Windows";
		} 
		else if (userAgent.toLowerCase().indexOf("mac") >= 0) 
		{
			os = "Mac";
		} 
		else 
		{
			os = "Unknown";
		}
		
		//Get the parameters from the web.xml
		ServletContext ctx = this.getServletContext();
		String cities = ctx.getInitParameter("cities");
		
		//Split the parameters string into array for cities and remove trailing whitespace
		String[] cityArray = cities.split(",");
		for(int i = 0; i < cityArray.length; i++)
		{
			cityArray[i] = cityArray[i].trim();
		}
		
		//Send all data to the next page [input.jsp]
		request.setAttribute("os", os);
		request.setAttribute("cities", cityArray);

		//Forwards a request from a servlet to [input.jsp] on the server.
		RequestDispatcher view = request.getRequestDispatcher("input.jsp");
		view.forward(request, response);
	}
	
	//Handles the POST request. It is invoked by the web container.
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		//Invoke doGet if receive POST request through some mean.
		doGet(request, response);
	}
}
