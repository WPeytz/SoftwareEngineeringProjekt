Feature: Request Assistance
  Description: A Developer requests assistance from another developer not assigned to
  Actors: Developer

  Scenario: Request Assistance Successfully
    Given that the "customer" project "NEM-ID" with start week "2022-09" and end week "2022-49" exists
    And that the developer "asbg" is assigned a project activity "Debugging" with start week "2022-40", end week "2022-42" and time budget 10 exists
    When "asbg" requests assistance for "Debugging" from developer "nikh"
    Then "nikh" will be able to register hours spent on the activity "Debugging"

  Scenario: Request Assistance from developer who is not available
    Given that the "customer" project "NEM-ID" with start week "2022-09" and end week "2022-49" exists
    And that the developer "asbg" is assigned a project activity "Debugging" with start week "2022-40", end week "2022-42" and time budget 10 exists
    When "asbg" requests assistance for "Debugging" from busy developer "nikh"
    Then the error "Developer is not free in the given time period."  is returned
