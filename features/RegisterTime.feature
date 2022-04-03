Feature: Register Time
	Description: Development Employee records time spent on activity on a given date.
	Actors: Development Employee

	Scenario: Register time for activity on a given date.
		Given that the Development Employee is assigned an Activity "Debugging"
		When the Development Employee registers start time as 15-03-2022 12.00
		And end time as 15-03-2022 15:35 to Activity "Debugging"
		Then the total time is rounded to 3.5 hours
		And 3.5 hours is added to total time spent on Activity "Debugging" on the date 15-03-2022

