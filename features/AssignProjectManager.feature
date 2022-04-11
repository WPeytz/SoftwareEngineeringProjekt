Feature: Assign a project manager to a project
  Description: Assign a project manager to customer project
  Actors: Developer
  Scenario: A developer adds a project manager to a customer project
    Given that the "customer" project "NEM-ID" with start week "2022-15" and end week "2022-45" exists.
    And that the developer "asdg" and "whkp" exists
    Then the developer "asdg" assigns "wkhp" as project manager for project "NEM-ID"

  Scenario: A developer adds a new project manager to a customer project with an existing project manager
    Given that the "customer" project "NEM-ID" with start week "2022-15" and end week "2022-45" exists.
    And that the developer "asdg", "whkp" and "niha" exists
    And that "wkhp" is project manager for "customer" project "NEM-ID"
    When the developer "asdg" assigns "niha" as project manager for project "NEM-ID"
    Then the new project manager for "customer" project "NEM-ID" is "niha"