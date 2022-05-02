Feature: Create new project white box test
  Description: Create a new project
  Actors: Developer
  #A
  Scenario: Create a customer project with a name that already exists
    Given that a project with name "NEM-ID" already exists
    When developer "whkp" creates a project with name "NEM-ID", as a "customer" project, with start week "2022-10" and end week "2022-13"
    Then developer "whkp" is given the error message "Project could not be created, as the project name is already in use."
  #B
  Scenario: Create a customer project
    Given that a project with name "NEM-ID" does not exist.
    When developer "whkp" creates a project with name "NEM-ID", as a "customer" project, with start week "2022-10" and end week "2022-13"
    Then the project with name "NEM-ID" is created.
  #C
  Scenario: Create a customer project where end week is before start week
    Given that a project with name "NEM-ID" does not exist.
    When developer "whkp" creates a project with name "NEM-ID", as a "customer" project, with start week "2022-13" and end week "2022-10"
    Then the developer "whkp" is given the error message "End week of project is before start week"