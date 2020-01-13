<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
		<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
		<title>Input Page</title>
	</head>
	
	<body style="background-color: #E0E0F8">
		<%
			//Get attribute from [Start] and [Calculate] (when there is an error from validation)
			String os = (String)request.getAttribute("os");
			String osSrc = "";
			String[] cities = (String[])request.getAttribute("cities");
			int idx = 0;
			int[] errors = (int[])request.getAttribute("errors");
			String inputValue = (String)request.getAttribute("inputValue");
			
			//Get appropriate operating system image
			if(os == "Windows")
			{
		
				osSrc = "https://upload.wikimedia.org/wikipedia/commons/thumb/5/5f/Windows_logo_-_2012.svg/768px-Windows_logo_-_2012.svg.png";
		
			} 
			else if(os == "Mac")
			{
		
				osSrc = "https://admission.enrollment.cmu.edu/media/W1siZiIsIjIwMTYvMDMvMTYvcXo3OHZhbzBsX0FwcGxlX0xvZ29fUG5nXzA5LnBuZyJdXQ/Apple_Logo_Png_09.png";
		
			}
			else
			{
				osSrc = "https://cdn.pixabay.com/photo/2013/03/30/00/09/operating-system-97849_640.png";
			}
			
			//Try to get city index (will fail when it is null)
			try 
			{
				idx = Integer.parseInt((String)request.getAttribute("idx"));
			} 
			catch (Exception e) 
			{
			}
			
			//Generate appropriate error message
			String error_message = "";
			if(errors != null && errors[0] == 1) 
			{
				error_message = "EMPTY INPUT!";
			}
			else if(errors != null && errors[0] == 2) 
			{
				error_message = "Must be a postive whole integer!";
			}
			
			if(inputValue == null)
			{
				inputValue = "";
			}
		%>
	
		<div align="center">
			<div align="center" style="padding:50px">
				<img class="mx-auto d-block" height="200" width="200" src=<%= osSrc %>>
				<br />
				
				<a href="<%= request.getContextPath() %>/Start" class="btn btn-primary">Return Home</a>
				<br /><br />
				
				<form action="Calculate" method="post">
					<b>Select a city</b><br />
					<select name="city" class="form-control">
						<%
							//Generate option list base on city array
							for (int i = 0; i < cities.length; i++) 
							{
						%>
							<option value="<%= i %>" <% if(i == idx){ %> selected <% } %>><%= cities[i] %></option>
						<%
							}
						%>
					</select>
					<br />
					
					<%
						//Display error message when invalid input
						if(!error_message.isEmpty())
						{
					%>
						<p class="alert alert-danger"><%= error_message %></p>
					<%
						}
					%>
					
					<b>Enter a number you want to factor below.</b><br />
					<input type="text" name="inputValue" class="form-control" value="<%= inputValue%>">
					<br />
					<input type="submit" name="submit" value="Calculate" class="btn btn-success"/>
				</form>
			</div>
		</div>
	</body>
</html>
