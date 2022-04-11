Feature: Create project activity
  Description: Add a project activity to an existing project.
  Actors: Project manager

  Scenario: Add activity "UX-Design" to the project "NEM-ID" as the project manager of the project "NEM-ID"
    Given that the project named "NEM-ID" is chosen
    And create project activity is chosen
    When user "whkp" writes their ID
    Then the system checks if they are the project manager of the project "NEM-ID"
    Given that the user is the project manager of the project "NEM-ID"
    Then the user creates a new activity with the name "UX-Design"
    And set the start week to "2022-14"
    And set the end week to "2022-16"
    And set time budget to 20 hours
    Then the new project activity is created

  Scenario: Add project activity "UX-Design" to the project "MIT-ID" when the user is not the project manager of "MIT-ID"
    Given that the "customer" project named "NEM-ID" with start week "2022-09" and end week "2022-50" exists
    And that the user "whkp" is not the project manager of the project "NEM-ID"
    Then the user will receive error message "You are not project manager"