package dtu.calculator;

import dtu.system.*;
import io.cucumber.java.en.*;

import java.sql.Time;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.*;

public class RegisterTimeSteps
{
    TimeSpent check;
    TimeManager manager;
    Activity a;
    Developer dev;
    String startTime, endTime;
    String projName;
    String errorMessage;
    String devName;
    double l, prevTotal;

    public RegisterTimeSteps()
    {
        manager = new TimeManager();
    }


    @Given("that a {string} project {string} with start week {string} and end week {string} exists")
    public void that_the_project_with_start_week_and_end_week_exists(String customer, String projectName, String startWeek, String endWeek)
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

    @And("the project activity {string} with start week {string} and end week {string} and time budget {int} exists")
    public void theProjectActivityWithStartWeekAndEndWeekAndTimeBudget(String name, String startWeek, String endWeek, int timeBudget)
    {
        try
        {
            manager.getProject(projName).activities.add(new Activity(name,timeBudget,manager.getProject(projName).projectID,startWeek,endWeek));
        }
        catch (OperationNotAllowedException ONAE)
        {
            errorMessage = ONAE.getMessage();
        }
    }

    @And("that the developer {string} is assigned to the project activity {string} in project ID {int}")
    public void that_the_developer_is_assigned_an_project_activity(String developer, String activity, int projectID) throws OperationNotAllowedException
    {
        try
        {
            manager.developerList.add(new Developer(developer));
            devName = developer;
            //dev = manager.getDeveloper(developer);
            Project project = manager.getProject(projectID);
            a = project.getActivity(activity);
            a.addWorkingDev(manager.getDeveloper(developer));
            assertTrue(a.workingDevelopers.contains(manager.getDeveloper(developer)));
        }
        catch (OperationNotAllowedException ONAE)
        {
            errorMessage = ONAE.getMessage();
        }
    }

    @Given("that the developer {string} is not assigned to the project activity {string} in project ID {int}")
    public void thatTheDeveloperIsNotAssignedToTheProjectActivityInProjectID(String developer, String activity, Integer projectID) {
        try
        {
            manager.developerList.add(new Developer(developer));
            devName = developer;
            dev = manager.getDeveloper(developer);
            Project project = manager.getProject(projectID);
            a = project.getActivity(activity);
            assertFalse(a.workingDevelopers.contains(manager.getDeveloper(developer)));
        }
        catch (OperationNotAllowedException ONAE)
        {
            errorMessage = ONAE.getMessage();
        }
    }

    @When("{string} registers start time as {string} and end time as {string} to project activity {string}")
    public void registers_start_time_as(String developer, String startTime, String endTime, String actName)
    {
        try
        {
            l = manager.getDeveloper(devName).registerTimeSpent(a,startTime,endTime);
        }
        catch (OperationNotAllowedException ONAE)
        {
            errorMessage = ONAE.getMessage();
        }
    }

    @Then("the total time is rounded to {double} hours")
    public void the_total_time_is_rounded_to_hours(double roundedTime)
    {
        assertEquals(l, roundedTime, 0.0);
    }
    @Then("{double} hours is added to total time spent on project activity {string}")
    public void hours_is_added_to_total_time_spent_on_project_activity(Double roundedTime, String activity)
    {
        assertEquals(a.activityTime()-roundedTime,a.activityTime(),roundedTime);
    }

    @Then("return error message {string}")
    public void returnErrorMessage(String error)
    {
        assertEquals(error,errorMessage);
    }

}
