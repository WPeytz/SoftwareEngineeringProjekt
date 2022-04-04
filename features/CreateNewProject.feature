Feature: Create new project
  Description: Create either a new internal or customer project.
  Actors: Developer

  Scenario: Create a customer project
    Given that a project with name "NEM-ID" does not exist.
    When developer "whkp" creates a project with name "NEM-ID", as a "customer" project, with start week "2022-10" and end week "2022-13"
    Then the project with name "NEM-ID" is created.

  Scenario: Create a customer project with a name that already exists
    Given that a project with name "NEM-ID" already exists
    When developer "whkp" creates a project with name "NEM-ID", as a "customer" project, with start week "2022-10" and end week "2022-13"
    Then developer "whkp" is given the error message "Project could not be created, as the project name is already in use."