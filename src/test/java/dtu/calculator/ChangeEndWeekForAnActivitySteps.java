package dtu.calculator;

import dtu.system.*;
import io.cucumber.java.en.*;
import static org.junit.Assert.*;

public class ChangeEndWeekForAnActivitySteps
{
    Activity a;
    TimeManager manager;
    String actName;
    String stW, edW;
    String errorMessage;

    public ChangeEndWeekForAnActivitySteps()
    {
        manager = new TimeManager();
    }

    @Given("that external activity {string} with start week {string} and end week {string} with time budget {int} exists")
    public void thatExternalActivityWithStartWeekAndEndWeekWithTimeBudgetExists(String activityName, String startWeek, String endWeek, int timeBudget)
    {
        stW = startWeek;
        edW = endWeek;
        a = new Activity(activityName,timeBudget,0,startWeek,endWeek);
        manager.extActList.add(a);
    }

    @When("developer {string} changes end week to {string}")
    public void developerChangesEndWeekTo(String devName, String newWeek)
    {
        try
        {
            manager.changeEndWeek(newWeek,a.name,0);
        }
        catch (OperationNotAllowedException ONAE)
        {
            errorMessage = ONAE.getMessage();
        }
    }
    @Then("end week for {string} is {string}")
    public void endWeekForIs(String string, String string2)
    {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }


}

