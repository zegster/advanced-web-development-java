package edu.umsl.java.servlet;

import java.io.IOException;
import java.math.BigInteger;
import java.util.LinkedList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//Servlet implementation class CalculateServlet
@WebServlet("/Calculate")
public class CalculateServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
	//Handles the GET request. It is invoked by the web container.
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		//Send users back to the home page when trying to access this page through URL
		response.sendRedirect("Start");
	}

	//Handles the POST request. It is invoked by the web container.
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		//Get the parameters from the web.xml
		ServletContext ctx = this.getServletContext();
		
		//Split the parameters string into array for cities and remove trailing whitespace
		String cities = ctx.getInitParameter("cities");
		String[] cityArray = cities.split(",");
		for(int i = 0; i < cityArray.length; i++)
		{
			cityArray[i] = cityArray[i].trim();
		}
		
		//Split the parameters string into array for tax rates and remove trailing whitespace
		String rates = ctx.getInitParameter("tax rates");
		String[] rateArray = rates.split(",");
		for(int i = 0; i < rateArray.length; i++)
		{
			rateArray[i] = rateArray[i].trim();
		}
		
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
		
		//Get data from [input.jsp] using request scope
		String cityIndex = request.getParameter("city");
		String inputValue = request.getParameter("inputValue");
		int idx = Integer.parseInt(cityIndex);
		
		
		//--------------------------------------------------
		//Validation check: check if empty input and if it positive whole number
		int errors[] = new int[1];
		if(inputValue.isEmpty())
		{
			errors[0] = 1;
			request.setAttribute("os", os);
			request.setAttribute("cities", cityArray);
			request.setAttribute("idx", String.valueOf(idx));
			request.setAttribute("errors", errors);
			request.setAttribute("inputValue", inputValue);
			
			RequestDispatcher view = request.getRequestDispatcher("input.jsp");
			view.forward(request, response);
			return;
		}
		else if(!inputValue.matches("\\d+"))
		{
			errors[0] = 2;
			request.setAttribute("os", os);
			request.setAttribute("cities", cityArray);
			request.setAttribute("idx", String.valueOf(idx));
			request.setAttribute("errors", errors);
			request.setAttribute("inputValue", inputValue);
			
			RequestDispatcher view = request.getRequestDispatcher("input.jsp");
			view.forward(request, response);
			return;
		}
		
		
		//--------------------------------------------------
		//Factorization Procedure
		BigInteger n = new BigInteger(inputValue);
		LinkedList<BigInteger> factorList = null;
		LinkedList<BigInteger> factorResult = null;
		BigInteger biggestFactor = BigInteger.TWO;
		String factorResultStr = "";
		
		//Check if n is at least greater than 1
		if(n.compareTo(BigInteger.TWO) < 0)
		{
			factorResultStr = "No possible factor when less than 2!";
		}
		else
		{
			//Check whether given number is a prime
			if(isPrime(n))
			{
				factorResultStr = "Given input is a prime!";
			}
			else
			{
				//Get a list of factor
				factorList = factorization(n);
				
				//Find the largest factor in the list
				for(int i = 0; i < factorList.size(); i++)
				{
					if(biggestFactor.compareTo(factorList.get(i)) <= 0)
					{
						biggestFactor = factorList.get(i);
					}
				}
				
				//Reformat the list of factor (For example: 2^4 = 16)
				factorResult = factorizationResult(factorList);
				for(int i = 0; i < factorResult.size(); i++)
				{
					factorResultStr += factorResult.get(i).toString();
					
					if(i < factorResult.size() - 1)
					{
						factorResultStr += " x ";
					}
				}
			}
		}
		
		
		//--------------------------------------------------
		//Invoice
		//Get and calculate tax rate value
		double tax = Double.parseDouble(rateArray[idx]);
		tax = (100 + tax) / 100;
		
		//Calculate the invoice price
		double price = Double.parseDouble(biggestFactor.mod(BigInteger.valueOf(100)).toString());
		price = price * tax;
		String invoice = String.format("%.2f", price);
		
		//Send all data as a String type to the next page [result.jsp]
		request.setAttribute("city", String.valueOf(cityArray[idx]));
		request.setAttribute("rate", String.valueOf(rateArray[idx]));
		request.setAttribute("inputValue", String.valueOf(inputValue));
		request.setAttribute("result", String.valueOf(factorResultStr));
		request.setAttribute("tax", String.valueOf(tax));
		request.setAttribute("invoice", String.valueOf(invoice));
		
		//DEBUGGING
		/*
		System.out.println("Selected city: " + cityArray[idx]);
		System.out.println("Tax rate: " + rateArray[idx]);
		System.out.println("Input: " + inputValue); 
		System.out.println("Result: " + factorResultStr); 
		System.out.println("Biggest: " + biggestFactor);
		System.out.println("Invoice: " + invoice);
		*/
		 
		
		//Forwards a request from a servlet to [result.jsp] on the server.
		RequestDispatcher view = request.getRequestDispatcher("WEB-INF/result.jsp");
		view.forward(request, response);
	}
	
	/* ===========================================================================
	isPrime
		Check the given number is a prime or not. Return true if it is.
	=========================================================================== */
	
	//Check whether BigInteger is a prime or not
	private static boolean isPrime(BigInteger n)
	{
        //Check via BigInteger.isProbablePrime(certainty)
        if (!n.isProbablePrime(3))
            return false;

        //Check if even
        BigInteger two = new BigInteger("2");
        if (!two.equals(n) && BigInteger.ZERO.equals(n.mod(two)))
            return false;

        //Find divisor if any from 3 to 'number'
        for (BigInteger i = new BigInteger("3"); i.multiply(i).compareTo(n) < 1; i = i.add(two)) 
        { 
        	//Start from 3, 5, etc. the odd number, and look for a divisor if any
        	//Check if 'i' is divisor of 'number'
        	if (BigInteger.ZERO.equals(n.mod(i))) 
        	{
                return false;
        	}
        }
        return true;
	}
	
	
	/* ===========================================================================
	factorization
		Return a list of all factor of the given number.
	=========================================================================== */
	private static LinkedList<BigInteger> factorization(BigInteger n)
	{
		LinkedList<BigInteger> factor = new LinkedList<BigInteger>();
		if(n.compareTo(BigInteger.ONE) < 0)
		{
		    return null;
		}
		
		while(n.mod(BigInteger.TWO).equals(BigInteger.ZERO))
		{
			factor.add(BigInteger.TWO);
		    n = n.divide(BigInteger.TWO);
		}
		
		if(n.compareTo(BigInteger.ONE) >= 0)
		{
		    BigInteger f = BigInteger.valueOf(3);
		    while (f.multiply(f).compareTo(n) <= 0)
		    {
		        if (n.mod(f).equals(BigInteger.ZERO))
		        {
		        	factor.add(f);
		            n = n.divide(f);
		        }
		        else
		        {
		            f = f.add(BigInteger.TWO);
		        }
		    }
		    factor.add(n);
		}

		return factor;
	}
	
	
	/* ===========================================================================
	factorizationResult
		Reformat the list of factor number.
	=========================================================================== */
	private static LinkedList<BigInteger> factorizationResult(LinkedList<BigInteger> f)
	{
		LinkedList<BigInteger> result = new LinkedList<BigInteger>();
		int powerCount = 0;
		for(int i = 0; i < f.size(); i++)
		{
			if(f.get(i).equals(BigInteger.TWO))
			{
				powerCount++;
			}
			else
			{
				result.add(f.get(i));
			}
		}
		result.add(BigInteger.TWO.pow(powerCount));
		
		return result;
	}
}
