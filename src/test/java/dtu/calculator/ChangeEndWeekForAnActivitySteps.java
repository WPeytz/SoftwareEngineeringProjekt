package dtu.calculator;

import dtu.system.*;
import io.cucumber.java.en.*;

import java.time.format.DateTimeFormatter;

import static org.junit.Assert.*;

public class ChangeEndWeekForAnActivitySteps
{
    Activity a;
    TimeManager manager;
    String projName, actName;
    String stW, edW;
    String errorMessage;
    DateTimeFormatter format = DateTimeFormatter.ofPattern("YYYY-ww");

    public ChangeEndWeekForAnActivitySteps()
    {
        manager = new TimeManager();
    }

    @Given("that external activity {string} with start week {string} and end week {string} with time budget {int} exists")
    public void thatExternalActivityWithStartWeekAndEndWeekWithTimeBudgetExists(String activityName, String startWeek, String endWeek, int timeBudget) throws OperationNotAllowedException {

        if(manager.checkDateFormat(startWeek) && manager.checkDateFormat(endWeek))
        {
            stW = startWeek;
            edW = endWeek;
        }
        if (manager.checkDateFormat(startWeek) && manager.checkDateFormat(endWeek))
        {
            a = new Activity(activityName,timeBudget,0,startWeek,endWeek);
        }
        else
        {
            System.out.println("Error, wrong format of dates");
            throw new OperationNotAllowedException("Error, wrong format of dates");
        }
        manager.extActList.add(a);
    }


    @Then("end week for {string} is {string}")
    public void endWeekForIs(String activityName, String newWeek)
    {
        assertEquals(a.endWeek.format(format),newWeek);
    }


    @Given("that project activity {string} exists in the {string} project {string} with start week {string} and end week {string}")
    public void thatProjectActivityExistsInTheCustomerProject(String activityName, String customer, String projectName, String startWeek, String endWeek)
    {
        projName = projectName;
        try
        {
            manager.createProject(projectName, customer.equalsIgnoreCase("customer"), startWeek, endWeek);
            //a = new Activity(actName,0,manager.getProject(projectName).projectID,startWeek,endWeek);
            this.actName = activityName;
        }
        catch (OperationNotAllowedException ONAE)
        {
            errorMessage = ONAE.getMessage();
        }
        assertTrue(manager.projectExists(projectName));
    }

    @And("that the project activity {string} has start week {string}, end week {string} and time budget {int}")
    public void thatTheProjectActivityHasStartWeekAndEndWeek(String activityName, String startWeek, String endWeek, int timeBudget) {
        try
        {
            manager.getProject(projName).activities
                        .add(new Activity(activityName,timeBudget, manager.getProject(projName)
                                    .projectID,startWeek,endWeek));

            a = manager.getProject(projName).getActivity(activityName);

            assertTrue(manager.getProject(projName)
                    .activities.contains(manager.getProject(projName)
                            .getActivity(activityName)));
        }
        catch (OperationNotAllowedException ONAE)
        {
            errorMessage = ONAE.getMessage();
        }
    }

    @When("project manager {string} changes end week to {string}")
    public void projectManagerChangesEndWeekToFrom(String projMan, String newWeek)
    {
        try
        {
            manager.changeEndWeek(newWeek,actName,manager.getProject(projName).projectID);
        }
        catch (OperationNotAllowedException ONAE)
        {
            errorMessage = ONAE.getMessage();
        }
    }


    @When("developer {string} changes end week to {string} for activity {string}")
    public void developerChangesEndWeekToForActivity(String dev, String endWeek, String activityName)
    {
        try
        {
            manager.changeEndWeek(endWeek,activityName,0);
        }
        catch (OperationNotAllowedException ONAE)
        {
            errorMessage = ONAE.getMessage();
        }

    }

    @Then("the error message {string} is returned")
    public void theErrorMessageIsReturned(String err)
    {
        assertEquals(err,errorMessage);
    }

    @Then("the end week of project {string} is changed to match the new end week of activity {string}")
    public void theEndWeekOfProjectIsChangedToMatchTheNewEndWeekOfActivity(String project, String activity) {
        try {
            assertEquals(manager.getProject(project).endWeek,manager.getProject(project).getActivity(activity).endWeek);
        } catch (OperationNotAllowedException ONAE) {
            errorMessage = ONAE.getMessage();
        }
    }
}

