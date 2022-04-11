Feature: Create project activity
  Description: Add a project activity to an existing project.
  Actors: Project manager

  Scenario: Add project activity "UX-Design" to the project "NEM-ID" as the project manager of the project "NEM-ID"
    Given that the "customer" project named "NEM-ID" with start week "2022-09" and end week "2022-50" exists
    And that the user "whkp" is the project manager of the project "NEM-ID"
    Then the user "whkp" creates a new project activity "UX-Design", start week "2022-14", end week "2022-16" and time budget 20 hours for project "NEM-ID"
    Then the new project activity "UX-Design" with start week "2022-14", end week "2022-16" and time budget 20 hours for project "NEM-ID" is created

  Scenario: Add project activity "UX-Design" to the project "MIT-ID" when the user is not the project manager of "MIT-ID"
    Given that the "customer" project named "NEM-ID" with start week "2022-09" and end week "2022-50" exists
    And that the user "whkp" is not the project manager of the project "NEM-ID"
    Then the user will receive error message "You are not project manager"