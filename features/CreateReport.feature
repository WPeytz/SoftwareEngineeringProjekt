Feature: Create Report
  Description: A weekly text containing a summary of a projects' status
  Actors: Project manager or developer

  Scenario: Create a report for the customer project "DTU Website"
    Given that the developer "whkp" and "customer" project "DTU Website" with start week "2022-02", end week "2022-32" exists
    And that "whkp" is the project manager of the project "DTU Website"
    And the project "DTU Website" contains the first activity "Create UI" with start week "2022-04" and end week "2022-07" and time budget 50
    And the project "DTU Website" contains the second activity "Optimise server" with start week "2022-25" and end week "2022-30" and time budget 80
    And "whkp" creates a weekly report for project "DTU Website"
    Then a report for "DTU Website" is created containing name of the project, project type, time budget, total time spent on the project and the estimated work time that is left on the project

  Scenario: Create a report for the customer project "DTU Website" if you are not a project manager
    Given that the developer "nikh" and "customer" project "DTU Website" with start week "2022-02", end week "2022-32" exists
    And that "nikh" is not the project manager of the customer project "DTU Website"
    And "nikh" creates a weekly report for project "DTU Website"
    Then it returns the error message "You are not project manager"

    Scenario: Create a report for the internal project "Fix SOFTWAREHUSET's website"
      Given that the developer "asbg" and "internal" project "Fix SOFTWAREHUSET's website" with start week "2022-01", end week "2022-03" exists
      And that "asbg" is the project manager of the project "Fix SOFTWAREHUSET's website"
      And "asbg" creates a weekly report for project "Fix SOFTWAREHUSET's website"
      Then a report for "Fix SOFTWAREHUSET's website" is created containing name of the project, project type, time budget, total time spent on the project and the estimated work time that is left on the project
