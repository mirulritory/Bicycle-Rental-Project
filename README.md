
[Project Documentation: Bicycle Rental System]

Overview:
The Bicycle Rental System is designed to facilitate bicycle rentals and returns for students. It involves two applications that interact with a central database.

1. How many apps involved
	- User (Students)
	- Staff

2. Brief explanation each apps 
	- User :
	- Login page: Where user need to enter the username (matric No.) and password and the system will validate either the username and password is valid or invalid.
	- Home page: User can choose either click button "Rent bicycle", "Return biycle" or "Logout"
	- Rent page: Display bicycle (bicycleID) which status is "available" on the database and user can choose the bicycle based on their preferences. Once rented, it will pop-up message and send user back to Home page. The database will insert data of username, bicycleID, and the return status either "not return" or "returned".
	- Return page: It will display bicycle with the status of "Not return" based on the username. Once the user click on the bicycleID, there will be pop-up messages and send user back to Home page. The database will update bicycle status back to "available".

	- Staff :
	- Login page: Where staff need to enter the username (matric No.) and password and the system will validate either the username and password 		  is valid or invalid.
	- Home page: Staff can choose either click button "View History" or "Logout".
	- View History page: It will display entire data of bicycle rental history (userID, name, phone No. and return status).


3. Architecture/Layer diagram for each of the apps including the middleware
   
   ![diagrammmm' drawio](https://github.com/user-attachments/assets/05a427c2-046d-4fc7-84ec-bfaa86299c65)
   

5. List of URL end points middleware RESTful/SOAP/Socket
	- http://10.200.116.32/Bicycle-Rental-Project/login.php
 	- http://10.200.116.32/Bicycle-Rental-Project/fetchbicycle.php
 	- http://10.200.116.32/Bicycle-Rental-Project/rentBicycle.php
 	- http://10.200.116.32/Bicycle-Rental-Project/fetchRentedBicycle.php?username=
 	- http://10.200.116.32/Bicycle-Rental-Project/returnBicycle.php
 	- http://localhost/Bicycle-Rental-Project/fetchRentalHistory.php

5. Functions/Features in the middleware
	- User Authentication: Validates user credentials against the database.
 	- Fetch Available Bicycles: Retrieves a list of bicycles available for rent.
 	- Fetch Rented Bicycles: Retrieves bicycles currently rented by a user.
 	- Rent Bicycle: Updates database to mark a bicycle as rented.
 	- Return Bicycle: Updates database to mark a bicycle as returned.
 	- Fetch Rental History: Retrieves the rental history for a user or bicycle.


7. The database and tables involve in the projects
	- user: Contains user details (userID, Name, phoneNo, passwordU)
 	- staff: Contains staff details (userID, name, phoneNo, password)
 	- bicycle: Contains bicycle details (bicycleID, status)
 	- rental: Logs rental transactions (rentalID, userID, bicycleID, rentalTime, returnStat

