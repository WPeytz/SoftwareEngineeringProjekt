Feature: Change end week for a project
  Description: Change end week for either a customer project or an internal project
  Actors: Project manager or Developer

  Scenario: Change end week for a customer project
    Given that the project "NEM-ID", for a "customer", with start week "2022-05" and end week "2022-50" exists
    When developer "asbg" changes end week to "2022-09" from "2022-08" for project "NEM-ID"
    Then end week for project "NEM-ID" is "2022-09"

  Scenario: Change end week for a non-existent customer project
    Given that the project "Togbillet", for a "customer", with start week "2022-05" and end week "2022-45" exists
    But that the developer "whkp" changes end week to "2022-45" from "2022-04" for project "Rejsekort"
    Then the errormessage "Project does not exist" is thrown

  Scenario: Enter the desired end week in a wrong format
    Given that the project "Firewall", for a "customer", with start week "2022-05" and end week "2022-45" exists
    But that the developer "alew" by accident changes end week to "2022-440", instead of "2022-40", from "2022-45" for project "Firewall"
    Then an error with errormessage "Wrong date format" is thrown