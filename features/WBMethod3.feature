Feature: Register Time spent on activity
  Description: Developer records time spent on project activity on a given date.
  Actors: Developer
#A
  Scenario: Register time for activity on a given date for a developer not on the activity.
    Given that a "customer" project "NEM-ID" with start week "2022-04" and end week "2022-45" exists
    And the project activity "Debugging" with start week "2022-05" and end week "2022-07" and time budget 20 exists
    And that the developer "alew" is not assigned to the project activity "Debugging" in project ID 220001
    When "alew" registers start time as "15-03-2022 12.00" and end time as "15-03-2022 15.35" to project activity "Debugging"
    Then return error message "Developer is not on the activity"

#B
  Scenario: Register time for activity on a given date.
    Given that a "customer" project "NEM-ID" with start week "2022-04" and end week "2022-45" exists
    And the project activity "Debugging" with start week "2022-05" and end week "2022-07" and time budget 20 exists
    And that the developer "alew" is assigned to the project activity "Debugging" in project ID 220001
    When "alew" registers start time as "15-03-2022 12.00" and end time as "15-03-2022 15.35" to project activity "Debugging"
    Then the total time is rounded to 3.5 hours
    And 3.5 hours is added to total time spent on project activity "Debugging"

#C
  Scenario: Register time but start time is after end time
    Given that a "customer" project "NEM-ID" with start week "2022-04" and end week "2022-45" exists
    And the project activity "Debugging" with start week "2022-05" and end week "2022-07" and time budget 20 exists
    And that the developer "alew" is assigned to the project activity "Debugging" in project ID 220001
    When "alew" registers start time as "15-03-2022 15.35" and end time as "15-03-2022 12.00" to project activity "Debugging"
    Then there is returned an error message "Start time is after end time"

#D
  Scenario: Register time but the format of start time is wrong
    Given that a "customer" project "NEM-ID" with start week "2022-04" and end week "2022-45" exists
    And the project activity "Debugging" with start week "2022-05" and end week "2022-07" and time budget 20 exists
    And that the developer "alew" is assigned to the project activity "Debugging" in project ID 220001
    When "alew" registers start time as "15-03-22 12" and end time as "15-03-2022 15.35" to project activity "Debugging"
    Then there is returned an error message "The entered dates are not of the right format."

#E
  Scenario: Register time for activity on a given date.
    Given that a "customer" project "NEM-ID" with start week "2022-04" and end week "2022-45" exists
    And the project activity "Debugging" with start week "2022-05" and end week "2022-07" and time budget 20 exists
    And that the developer "alew" is assigned to the project activity "Debugging" in project ID 220001
    When "alew" registers start time as "15-03-2022 12.00" and end time as "15-03-22 15" to project activity "Debugging"
    Then there is returned an error message "The entered dates are not of the right format."




