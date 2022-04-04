Feature: Register Time
	Description: Developer records time spent on project activity on a given date.
	Actors: Developer

	Scenario: Register time for activity on a given date.
		Given that the developer "alew" is assigned an project activity "Debugging"
		When "alew" registers start time as "15-03-2022 12.00"
		And registers end time as "15-03-2022 15.35" to project activity "Debugging"
		Then the total time is rounded to 3.5 hours
		And 3.5 hours is added to total time spent on project activity "Debugging"