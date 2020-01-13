<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="java.math.BigInteger" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
	<title>Result Page</title>
</head>
<body style="background-color: #CEF6CE">
	<% 
		//Get attribute from [Calculate]
		String city = (String)request.getAttribute("city");
		String rate = (String)request.getAttribute("rate");
		String inputValue = (String)request.getAttribute("inputValue");
		String result = (String)request.getAttribute("result");
		String invoice = (String)request.getAttribute("invoice");
	%>
	<div align="center" style="padding:50px">
		<h2 style="margin:0px"><b>-----RESULT-----</b></h2>
		<div style="display: inline-block; padding:10px" align="left">
			<pre style="margin:0px"><b>Your Input: </b><%= inputValue %> <br /></pre>
			<pre style="margin:0px"><b>Output:     </b><%= result %> <br /></pre>
		</div><br />
		
		
		<h2 style="margin:0px"><b>-----INVOICE-----</b></h2>
		<div style="display: inline-block; padding:10px" align="left">
			<pre style="margin:0px"><b>Selected City: </b><%= city %> <br /></pre>
			<pre style="margin:0px"><b>Tax Rate:      </b><%= rate %>%<br /></pre>
			<pre style="margin:0px"><b>Amount Due:    </b>$<%= invoice %> <br /></pre>
		</div><br /><br />
		
		<a href="<%= request.getContextPath() %>/Start" class="btn btn-secondary">Purchase Again</a>
	</div>
</body>
</html>
