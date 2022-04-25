Feature: Assign developer to project activity
  Description: A feature that allows a project manager of a project to assign a developer
  to a project activity in one of their projects.
  Actors: Project Manager

  Scenario: Assign developer to a customer activity
    Given that developer "whkp" exists
    And "whkp" is the project manger of the "customer" project "NEM-ID" with start week "2022-32" and end week "2023-09"
    And the project activity "UX-Design" with start week "2022-52", end week "2023-08" and time budget 20 exists for the project.
    Given that "dprk" is assigned to the project "NEM-ID"
    And that "dprk" is a free employee in the period from "2022-52" to "2023-08"
    Then developer "dprk" will be assigned to the project activity "UX-Design"

  Scenario: Attempt to assign developer to a customer activity that doesn't exist
    Given that developer "asbg" exists
    And "asbg" is the project manger of the "internal" project "Rejsekort" with start week "2022-03" and end week "2023-02"
    Given that "whkp" is assigned to the project "Rejsekort"
    And that "whkp" is a free employee in the period from "2022-40" to "2023-45"
    When developer "whkp" is assigned to the project activity "Payment transfer" that does not exist
    Then the errormessage "Activity \"Payment transfer\" could not be found"