Feature: Register Time spent on activity
	Description: Developer records time spent on project activity on a given date.
	Actors: Developer

	Scenario: Register time for activity on a given date.
		Given that a "customer" project "NEM-ID" with start week "2022-04" and end week "2022-45" exists
		And the project activity "Debugging" with start week "2022-05" and end week "2022-07" and time budget 20 exists
		And that the developer "alew" is assigned to the project activity "Debugging" in project ID 220001
		When "alew" registers start time as "15-03-2022 12.00" and end time as "15-03-2022 15.35" to project activity "Debugging"
		Then the total time is rounded to 3.5 hours
		And 3.5 hours is added to total time spent on project activity "Debugging"

	Scenario: Register time for activity on a given date for a developer not on the activity.
		Given that a "customer" project "NEM-ID" with start week "2022-04" and end week "2022-45" exists
		And the project activity "Debugging" with start week "2022-05" and end week "2022-07" and time budget 20 exists
		And that the developer "alew" is not assigned to the project activity "Debugging" in project ID 220001
		When "alew" registers start time as "15-03-2022 12.00" and end time as "15-03-2022 15.35" to project activity "Debugging"
		Then return error message "Developer is not on the activity"

	Scenario: Register time for activity on a given date with invalid developer initials
		Given that a "customer" project "NEM-ID" with start week "2022-04" and end week "2022-45" exists
		And the project activity "Debugging" with start week "2022-05" and end week "2022-07" and time budget 20 exists
		And that the developer "alews" is assigned to the project activity "Debugging" in project ID 220001
		Then return error message "Invalid amount of initials for developer name. Initials length must be between 1 and 4 characters (inclusive)"

	Scenario: Register time for activity on a given date with invalid developer initials
		Given that a "customer" project "NEM-ID" with start week "2022-04" and end week "2022-45" exists
		And the project activity "Debugging" with start week "2022-05" and end week "2022-07" and time budget 20 exists
		And that the developer "a.ex" is assigned to the project activity "Debugging" in project ID 220001
		Then return error message "Illegal character(s) found. Only letters are allowed in developer initials"

	Scenario: Register time for activity on a given date and edit time afterwards
		Given that a "customer" project "NEM-ID" with start week "2022-04" and end week "2022-45" exists
		And the project activity "Debugging" with start week "2022-05" and end week "2022-07" and time budget 20 exists
		And that the developer "alew" is assigned to the project activity "Debugging" in project ID 220001
		When "alew" registers start time as "15-03-2022 12.00" and end time as "15-03-2022 15.35" to project activity "Debugging"
		Then the total time is rounded to 3.5 hours
		And 3.5 hours is added to total time spent on project activity "Debugging"
		When "alew" wants to edit registered time on project activity "Debugging"
		Then the previous time registration for project activity "Debugging" of 3.5 hours with start time "15-03-2022 12.00" and end time "15-03-2022 15.35" is deleted in project ID 220001
		Then "alew" registers start time as "15-03-2022 13.00" and end time as "15-03-2022 15.35" to project activity "Debugging"
		Then the total time is rounded to 2.5 hours


		#Vil gerne ændre tid -> Tidsregistrering med forket tid slettes -> Developer laver ny tidsregistrering i stedet for den gamle