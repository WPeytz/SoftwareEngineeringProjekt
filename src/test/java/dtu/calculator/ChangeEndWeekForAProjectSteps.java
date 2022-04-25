package dtu.calculator;
import dtu.system.*;
import io.cucumber.java.cy_gb.Ond;
import io.cucumber.java.en.*;
import io.cucumber.java.hu.De;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.*;

public class ChangeEndWeekForAProjectSteps
{
    TimeManager manager;
    String projName;
    String errorMessage;
    DateTimeFormatter format = DateTimeFormatter.ofPattern("YYYY-ww");
    public ChangeEndWeekForAProjectSteps()
    {
        manager = new TimeManager();
    }

    @Given("that the project {string}, for a {string}, with start week {string} and end week {string} exists")
    public void thatTheProjectForAWithStartWeekAndEndWeekExists(String projectName, String customer, String startWeek, String endWeek)
    {
        projName = projectName;
        try
        {
            manager.createProject(projectName, customer.equalsIgnoreCase("customer"), startWeek, endWeek);
        }
        catch (OperationNotAllowedException ONAE)
        {
            errorMessage = ONAE.getMessage();
        }
        assertTrue(manager.projectExists(projectName));
    }

    @When("developer {string} changes end week to {string} from {string} for project {string}")
    public void developerChangesEndWeekToFrom(String developer, String newWeek, String oldWeek, String projectName)
    {
        try
        {
            manager.developerList.add(new Developer(developer));
            manager.getProject(projectName).changeProjectEndWeek(newWeek);
        }
        catch (OperationNotAllowedException ONAE)
        {
            errorMessage = ONAE.getMessage();
        }
    }


    @Then("end week for project {string} is {string}")
    public void endWeekForProjectIs(String projectName, String newWeek)
    {
        try
        {
            assertEquals(manager.getProject(projectName).endWeek.format(format), newWeek);
        }
        catch (OperationNotAllowedException ONAE)
        {
            errorMessage = ONAE.getMessage();
        }
    }
    @Given("that the developer {string} changes end week to {string} from {string} for project {string}")
    public void thatTheDeveloperChangesEndWeekToFromForProject(String string, String newWeek, String oldweek, String projName)
    {
        try
        {
            manager.getProject(projName).changeProjectEndWeek(newWeek);
        }
        catch (OperationNotAllowedException ONAE)
        {
            errorMessage = ONAE.getMessage();
        }
    }

    @Then("the errormessage {string} is thrown")
    public void theErrormessageIsThrown(String errMsg)
    {
        assertEquals(errorMessage,errMsg);
    }
}
