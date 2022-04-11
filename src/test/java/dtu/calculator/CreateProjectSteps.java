package dtu.calculator;

import dtu.system.*;
import io.cucumber.java.en.*;
import static org.junit.Assert.*;

public class CreateProjectSteps {
    TimeManager manager;
    Activity a;
    Developer dev;
    double l, prevTotal;
    String errorMessage;

    public CreateProjectSteps()
    {
        manager = new TimeManager();
    }

    @Given("that a project with name {string} does not exist.")
    public void that_a_project_with_name_does_not_exist(String projectName)
    {
        assertFalse(manager.projectExists(projectName));
    }

    @Then("the project with name {string} is created.")
    public void the_project_is_created(String projName)
    {
        assertTrue(manager.projectExists(projName));
    }

    //______________________________________________________________________________________________________________________

    @Given("that a project with name {string} already exists")
    public void thatAProjectWithNameAlreadyExists(String projName)
    {
        try
        {
            manager.createProject(projName,"customer".equalsIgnoreCase("customer"), "2022-10", "2022-13");
        }
        catch (OperationNotAllowedException ONAE)
        {
            errorMessage = ONAE.getMessage();
        }
        assertTrue(manager.projectExists(projName));
    }
    @When("developer {string} creates a project with name {string}, as a {string} project, with start week {string} and end week {string}")
    public void developerCreatesAProjectWithNameAsACustomerProjectWithStartWeekAndEndWeek(String devName, String projName, String custProj, String stWeek, String enWeek)
    {
        try
        {
            manager.createProject(projName,custProj.equalsIgnoreCase("customer"), stWeek, enWeek);
        }
        catch (OperationNotAllowedException ONAE)
        {
            errorMessage = ONAE.getMessage();
        }
    }
    @Then("developer {string} is given the error message {string}")
    public void developerIsGivenTheErrorMessage(String devName, String errMsg)
    {
        assertEquals(errMsg,errorMessage);
    }
}
