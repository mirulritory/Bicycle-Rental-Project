Project Documentation: Bicycle Rental System

Overview
The Bicycle Rental System is designed to facilitate bicycle rentals and returns for students. It involves two applications that interact with a central database.

1. How many apps involved
	- User (Students)
	- Staff

2. Brief explanation each apps 
	- User :
		- Login page: Where user need to enter the username (matric No.) and password and the system will validate either the username and password 			  is valid or invalid.
		- Home page: User can choose either click button "Rent bicycle", "Return biycle" or "Logout"
		- Rent page: Display bicycle (bicycleID) which status is "available" on the database and user can choose the bicycle based on their 		  		  preferences. Once rented, it will pop-up message and send user back to Home page. The database will insert data of username, bicycleID, 		  	  and the return status either "not return" or "returned".
		- Return page: It will display bicycle with the status of "Not return" based on the username. Once the user click on the bicycleID, there 		 	  will be pop-up messages and send user back to Home page. The database will update bicycle status back to "available".

	- Staff :
		- Login page: Where staff need to enter the username (matric No.) and password and the system will validate either the username and password 		  is valid or invalid.
		- Home page: Staff can choose either click button "View History" or "Logout".
		- View History page: It will display entire data of bicycle rental history (userID, name, phone No. and return status).


3. Architecture/Layer diagram for each of the apps including the middleware

4. List of URL end points middleware RESTful/SOAP/Socket 

5. Functions/Features in the middleware

6. The database and tables involve in the projects
