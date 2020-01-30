# Project 2 - Math Question Bank

DESCRIPTION
----------------------------------------------------------------------------------------------------
In this project, you build a math question bank by entering math questions into a database.
You can also create a list of categories, such as Algebra, Geometry, Trigonometry, etc. 
For each math question, you can assign it to one of the categories. Then you can list all the questions in any category.


REQUIREMENTS
----------------------------------------------------------------------------------------------------
Database
	- You need to create a table to store all the math questions.
	- You need to create a table to store all the categories.
	- You need to create a table to store the mappings between the math questions and the categories through their IDs.
	
Users' interface
	- Based on your own understanding, you design your users' interface. The basic consideration is: Your UI should 
		support all the features in this project.

Features
	1. When a user comes to the web application, you display the whole page of the math questions. For simplicity, 
		we do not require you use pagination to display questions in multiple pages − all in one page.
		
	2. There is a textarea on the page so that any user can enter a math question into the database.

	3. When a question is entered, you need to makes sure that it is not empty (as our data validation).

	4. You also need to provide an input field to allow the users to enter a new category.

	5. When a user enters a new category, check if it already exists in the table. If it exists, do not duplicate it. 
		Only when it is a new category and it is not an empty string, write it into the table.

	6. For each math question, associate it with a dropdown list of the categories and an Assign button. When the user 
		selects a category, and clicks the Assign button, this question belongs to this category.

	7. If a question has been assigned to a category, find a way to mark this assignment to make the user aware of it, 
		so as to avoid the user putting a question into the same category twice.

	8. On top of the page, list all the categories as menu items. When a category is selected, only the questions in 
		this category are listed.


HOW TO RUN
----------------------------------------------------------------------------------------------------
1. Enable Apache and MySQL in XAMPP Control Panel (XAMPP Version: v5.5.19 or above).
2. Head to phpMyAdmin main page: http://localhost/phpmyadmin/index.php
3. Click on SQL tab and run [init.sql] by copy and paste into the text field and click on "Go".
4. Run the project in Eclipse IDE: Right click on Project2 > Run As > Run on Server > Choose your server > Next > Add Project2 to Configured > Finish
5. Now you can view the website in your browser with the URL: http://localhost:8080/Project2/
6. Make sure your browser have JavaScript enable.
