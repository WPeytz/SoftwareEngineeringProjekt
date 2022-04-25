Feature: Change end week for an activity
  Description: Change end week for either a project activity or a personal activity.
  Actors: Project manager or Developer

  Scenario: Change end week for a external activity
    Given that external activity "Vacation to New York" with start week "2022-04" and end week "2022-10" with time budget 0 exists
    When developer "asbg" changes end week to "2022-09"
    Then end week for "Vaction to New York" is "2022-09"

  Scenario: Change end week for a project activity
    Given that project activity "Programming UI" exists in the "customer" project "NEM-ID" with start week "2022-09" and end week "2022-11"
    And that the project activity "Programming UI" has start week "2022-13", end week "2022-19" and time budget 20
    When project manager "asbg" changes end week to "2022-45"
    Then the end week of project "NEM-ID" is changed to match the new end week of activity "Programming UI"
    