Feature: Change end week for a project
  Description: Change end week for either a customer project or an internal project
  Actors: Project manager or Developer

  Scenario: Change end week for a customer project
    Given that the project "NEM-ID", for a "customer", with start week "2022-05" and end week "2022-50" exists
    When developer "asbg" changes end week to "2022-09" from "2022-08" for project "NEM-ID"
    Then end week for project "NEM-ID" is "2022-09"