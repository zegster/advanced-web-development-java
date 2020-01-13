package edu.umsl.math.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.umsl.math.beans.Category;
import edu.umsl.math.beans.Problem;
import edu.umsl.math.dao.CategoryDao;
import edu.umsl.math.dao.ProblemDao;



//http://localhost:8080/Project3/
@WebServlet("/list_math")
public class ListMathServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;

	//Handles the GET request. It is invoked by the web container (URL).
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		//===Variables===
		ProblemDao problemdao = null;
		CategoryDao categorydao = null;
		String initPage = request.getParameter("page");
		int page = 0;
		String filter = null;
		int filterId = 0;
		
		
		//getParameter always result as string type, thus converting string to integer type
		if(initPage != null && !initPage.isEmpty()) 
		{
			try 
			{
				page = Integer.parseInt(initPage);
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
		

		//Trying to access database
		try 
		{
			//Creating object...
			problemdao = new ProblemDao();
			categorydao = new CategoryDao();
			
			
			//First check if there is filter attribute from doPost. If not, look for filter parameter
			filter = (String)request.getAttribute("filter");
			if(filter == null)
			{	
				filter = request.getParameter("filter");
			}
			
			
			//Convert from String to integer type: [filter]
			if(filter != null && !filter.isEmpty())
			{
				try 
				{
					filterId = Integer.parseInt(filter);
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
					return;
				}
			}
			
			
			//Getting total number of problems in our database
			int count = problemdao.getProblemCount(filterId);
			int totalPage = (int)Math.ceil(count/ 10.0);
			request.setAttribute("maxpage", totalPage);
			
			
			//Limit the page range
			if(page < 1)
			{
				page = 1;
			}
			else if(page > totalPage)
			{
				page = totalPage;
			}
			request.setAttribute("currentpage", page);
			
			
			//Getting appropriate problem base on given parameter
			//Check if filtering or searching
			//If searching, check to see if it exact searching and check if it empty keyword
			List<Problem> problemlist = null;
			if(filter != null)
			{
				if(filterId <= 0)
				{
					problemlist = problemdao.getProblemListByPage(page);
				}
				else
				{
					problemlist = problemdao.getProblemListByPageByFilter(page, filterId);
				}
			}
			else
			{
				problemlist = problemdao.getProblemListByPage(page);
			}
			request.setAttribute("problemlist", problemlist);
			request.setAttribute("currentcategory", filterId);
			
			
			//Getting category
			List<Category> categorylist = categorydao.getCategoryList();
			request.setAttribute("categorylist", categorylist);
		} 
		catch(Exception e) 
		{
			e.printStackTrace();
		}

		
		RequestDispatcher dispatcher = request.getRequestDispatcher("list.jsp");
		dispatcher.forward(request, response);
	}
	
	//Handles the POST request. It is invoked by the web container.
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		//===Variables===
		String categoryFilter = request.getParameter("categoryfilter");
		request.setAttribute("filter", categoryFilter);
		doGet(request, response);
	}
}
