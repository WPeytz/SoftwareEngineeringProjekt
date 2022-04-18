Feature: Assign developer to project
  Description: A feature that allows a project manager of a project to assign a
  developer to one of their projects
  Actors: Project Manager

  Scenario: Assign developer to a customer project
    Given that the developer "whkp" is the project manger of the "customer" project "NEM-ID" with start week "2022-19" and end week "2022-43"
    And developer "dprk" exists
    And developer "dprk" is not already a developer on the "customer" project "NEM-ID"
    Then project manager "whkp" adds developer "dprk" to "customer" project "NEM-ID"

  Scenario: Assign developer to a customer project when they have already been added to the project
    Given that the developer "whkp" is the project manger of the "customer" project "NEM-ID" with start week "2022-19" and end week "2022-43"
    And developer "dprk" exists
    And developer "dprk" is already a developer on the "customer" project "NEM-ID"
    Then return the error message "This developer has already been assigned to the project"