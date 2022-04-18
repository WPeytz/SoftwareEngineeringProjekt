package dtu.calculator;

import io.cucumber.java.en.*;
import dtu.system.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.*;

public class AssignDeveloperToProjectActivitySteps
{
        TimeManager manager;
        String projectName, errorMessage;
        DateTimeFormatter format;
        public AssignDeveloperToProjectActivitySteps()
        {
            manager = new TimeManager();
            format = DateTimeFormatter.ofPattern("YYYY-ww-e");
        }

        @Given("that developer {string} exists")
        public void thatDeveloperExists(String devName)
        {
                try
                {

                        manager.developerList.add(new Developer(devName));
                }
                catch (OperationNotAllowedException ONAE)
                {
                        errorMessage = ONAE.getMessage();
                }
        }
        @And("{string} is the project manger of the {string} project {string} with start week {string} and end week {string}")
        public void isTheProjectMangerOfTheProjectWithStartWeekAndEndWeek(String devName, String customer, String projName, String startWeek, String endWeek)
        {
                try
                {
                        projectName = projName;
                        manager.createProject(projName,customer.equalsIgnoreCase("customer"),startWeek,endWeek);
                        manager.getProject(projName).setProjectManager(manager.getDeveloper(devName));
                }
                catch (OperationNotAllowedException ONAE)
                {
                        errorMessage = ONAE.getMessage();
                }
        }
        @And("the project activity {string} with start week {string}, end week {string} and time budget {int} exists for the project.")
        public void theProjectActivityWithStartWeekEndWeekAndTimeBudgetExistsForTheProject(String actName, String startWeek, String endWeek, Integer timeBudget)
        {
                try
                {
                        manager.getProject(projectName).activities.
                                add(new Activity(actName,timeBudget,manager.
                                        getProject(projectName).projectID,startWeek,endWeek));
                }
                catch (OperationNotAllowedException ONAE)
                {
                        errorMessage = ONAE.getMessage();
                }
        }
        @Given("that {string} is assigned to the project {string}")
        public void thatIsAssignedToTheProject(String devName, String projName)
        {
                try {
                        manager.getProject(projectName).addWorkingDev(new Developer(devName));
                } catch (OperationNotAllowedException ONAE) {
                        errorMessage = ONAE.getMessage();
                }
        }
        @Given("that {string} is a free employee in the period from {string} to {string}")
        public void thatIsAFreeEmployeeForTheDurationOfTheActivity(String devName, String startWeek, String endWeek)
        {
                try
                {
                        manager.getDeveloper(devName).isFree(LocalDate.parse(startWeek,format),LocalDate.parse(endWeek,format));
                }
                catch (OperationNotAllowedException ONAE)
                {
                        errorMessage = ONAE.getMessage();
                }
        }
        @Then("developer {string} will be assigned to the project activity {string}")
        public void developerWillBeAssignedToTheProjectActivity(String devName, String actName)
        {
                try
                {
                        manager.getProject(projectName).getActivity(actName).addWorkingDev(new Developer(devName));
                        assertTrue(manager.getProject(projectName).getActivity(actName).
                                workingDevelopers.contains(manager.getDeveloper(devName)));
                }
                catch (OperationNotAllowedException ONAE)
                {
                        errorMessage = ONAE.getMessage();
                }

        }
}

