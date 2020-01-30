# Project 3 - Keyword-Based Search for Math Problems

DESCRIPTION
----------------------------------------------------------------------------------------------------
This project is a continuation of Project 2 by adding the keyword-based search feature. For each math problem, 
you can associate it with a set of keywords. Then a user can type one or multiple keywords (up to three keywords) 
in a search field and press the Search button, all the problems that contain at least one of the keywords will be displayed.


ATTENTION
----------------------------------------------------------------------------------------------------
Please review the requirements note.


REQUIREMENTS
----------------------------------------------------------------------------------------------------
Database
	- You use the same database as in Project 2, but you will add new tables to support the new feature.
	- We may need to add a table for the keywords and a table for the problem-keyword mappings.
		*[NOTE] Didn't implement a third table because I use a simple query call for searching.
	
Users' interface
	- Based on your own understanding, you design your users' interface. The basic consideration is: Your UI should 
		support all the features in this project.

Features
	1. On the page that all the math problems are listed, find a way to associate each problem with a set of keywords. 
		For example, you may use a Keywords button to open an input field to allow the user to enter a set of keywords (including an Enter button). 
		After this step, the keyword-based search can work.
	
	2. On top of the page, there is a search field. A user can type one or multiple keywords (up to 3 keywords) separated by commas.
		*[NOTE] There is no limit of how many keywords you can use since it is not realistic to have restricted amount. Also
			keywords is separate as space rather than commas for simplicity.

	3. When the user hits the search button, the search result returns, in which all the problems that contain at least one of the 
		given keywords will be listed in any order.

	4. Note that you do not need to list the search result based on the relevance for simplicity.
		*[NOTE] There is two search mode: relevance search (OR operator search type) and exact search (AND operator search type)

	5. Anything that is not mentioned in this document is not required. For example, you may like to add a feature: 
		Drop some of the keywords for a problem. But it is optional.


HOW TO RUN
----------------------------------------------------------------------------------------------------
1. Enable Apache and MySQL in XAMPP Control Panel (XAMPP Version: v5.5.19 or above).
2. Head to phpMyAdmin main page: http://localhost/phpmyadmin/index.php
3. Click on SQL tab and run [init.sql] by copy and paste into the text field and click on "Go".
4. Run the project in Eclipse IDE: Right click on Project3 > Run As > Run on Server > Choose your server > Next > Add Project3 to Configured > Finish
5. Now you can view the website in your browser with the URL: http://localhost:8080/Project3/
6. Make sure your browser have JavaScript enable.
