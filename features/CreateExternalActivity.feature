Feature: Create external activity
  Description: Create an external activity which is not linked to a project
  Actors: Developer

  Scenario: Create an external activity where all chosen developers are free employees
    Given that the developer "whkp" wants to create an external activity called "Software Course".
    When the developer "whkp" chooses to create an activity with name "Software Course" as an external activity, with start week "2022-15" and end week "2022-17".
    Then developer writes "pmka", "dskw" and "vmrr"
    Given that the chosen developers are free employees in the time period
    Then the external activity is created
    And the three developers are added to the external activity

   Scenario: Create external activity where a developer is not a free employee
     Given that the developer "whkp" wants to create an external activity called "Hardware Course".
     When the developer "whkp" chooses to create an activity with name "Hardware Course" as an external activity, with start week "2022-15" and end week "2022-17".
     Then developer writes "pmka", "dskw" and "vmrr"
     Given that at least one of the developers are not a free employee in the chosen time period
     Then developer "whkp" will be met with the error message "Developer is not free in the given time period."