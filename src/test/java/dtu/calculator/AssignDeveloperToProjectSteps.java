package dtu.calculator;

import io.cucumber.java.en.*;
import dtu.system.*;
import static org.junit.Assert.*;

public class AssignDeveloperToProjectSteps
{
    TimeManager manager;
    String projectName, errorMessage;
    public AssignDeveloperToProjectSteps()
    {
        manager = new TimeManager();
    }
    @Given("that the developer {string} is the project manger of the {string} project {string} with start week {string} and end week {string}")
    public void thatTheDeveloperIsTheProjectMangerOfTheProjectWithStartWeekAndEndWeek(String devName, String customer, String projName, String startWeek, String endWeek)
    {
        projectName = projName;
        try
        {
            manager.developerList.add(new Developer(devName));
            manager.createProject(projName,customer.equalsIgnoreCase("customer"), startWeek, endWeek);
            manager.getProject(projectName).setProjectManager(manager.getDeveloper(devName));
        }
        catch (OperationNotAllowedException ONAE)
        {
            errorMessage = ONAE.getMessage();
        }
    }
    @Given("developer {string} exists")
    public void developerExists(String devName)
    {
        try
        {
            manager.developerList.add(new Developer(devName));

            assertTrue(manager.developerList.contains(manager.getDeveloper(devName)));
        }
        catch (OperationNotAllowedException ONAE)
        {
            errorMessage = ONAE.getMessage();
        }
    }
    @Given("developer {string} is not already a developer on the {string} project {string}")
    public void developerIsNotAlreadyADeveloperOnTheProject(String dev2, String customer, String projName) {
        try
        {
            assertFalse(manager.getProject(projName).workingProjectDevelopers.contains(manager.getDeveloper(dev2)));
        }
        catch (OperationNotAllowedException ONAE)
        {
            errorMessage = ONAE.getMessage();
        }
    }
    @Then("project manager {string} adds developer {string} to {string} project {string}")
    public void developerAddsDeveloperToProject(String projManager, String newDev, String customer, String projName) {
        try {
            manager.getProject(projName).addWorkingDev(manager.getDeveloper(newDev));
        } catch (OperationNotAllowedException ONAE) {
            errorMessage = ONAE.getMessage();
        }
    }

    @And("developer {string} is already a developer on the {string} project {string}")
    public void developerIsAlreadyADeveloperOnTheProject(String devName, String customer, String projName) {

        try {
            manager.getProject(projName).addWorkingDev(manager.getDeveloper(devName));
            manager.getProject(projName).addWorkingDev(manager.getDeveloper(devName));
        } catch (OperationNotAllowedException ONAE) {
            errorMessage = ONAE.getMessage();
        }

    }

    @Then("return the error message {string}")
    public void returnTheErrorMessage(String error) {
        assertEquals(error,errorMessage);
    }
}
