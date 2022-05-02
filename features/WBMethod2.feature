Feature: Free employee white box test
  Description: Check if employee is free
  Actors: Developer
  #A
  Scenario: Check if developer is free in given time period with too many activities
    When the developer "whkp" is checked if he is free in the time period from "2022-05" to "2022-25"
    Then developer "whkp" will be met with the error message "Developer is not free in the given time period."
  #B
  Scenario: Check if developer is free in given time period with 2 activities
    When the developer "whkp" is checked if he is free in the given time period from "2022-05" to "2022-25"
    Then return "true"
  #C
  Scenario: Check if developer is free in given time period with 0 activities
    When the developer "whkp" is checked if he is free in the selected time period from "2022-05" to "2022-25"
    Then return "true"