package dtu.calculator;

import dtu.system.*;
import io.cucumber.java.en.*;
import static org.junit.Assert.*;



public class RequestAssistanceSteps
{
    TimeManager manager;
    String errorMessage, projName;
    String startWeek, endWeek;
    String devName;
    String actName;

    public RequestAssistanceSteps()
    {
        manager = new TimeManager();
    }

    @Given("that the {string} project {string} with start week {string} and end week {string} exists")
    public void thatTheProjectWithStartWeekAndEndWeekExists(String cust, String projName, String stW, String edW)
    {
        this.projName = projName;
        try
        {
            manager.createProject(projName,cust.equals("customer"),stW,edW);
        }
        catch (OperationNotAllowedException ONAE)
        {
            errorMessage = ONAE.getMessage();
        }
    }

    @And("that the developer {string} is assigned a project activity {string} with start week {string}, end week {string} and time budget {int} exists")
    public void thatTheDeveloperIsAssignedAProjectActivityWithStartWeekEndWeekAndTimeBudget(String init, String name, String stW, String edW, int tb)
    {
        actName = name;
        startWeek = stW;
        endWeek = edW;

        try
        {
            manager.developerList.add(new Developer(init));
            manager.getProject(projName).activities.add(new Activity(name,tb,manager.getProject(projName).projectID,stW,edW));
            manager.getProject(projName).getActivity(name).addWorkingDev(manager.getDeveloper(init));
            assertTrue(manager.getProject(projName).getActivity(name).workingDevelopers.contains(manager.getDeveloper(init)));
        }
        catch (OperationNotAllowedException ONAE)
        {
            errorMessage = ONAE.getMessage();
        }
    }

    @When("{string} requests assistance for {string} from developer {string}")
    public void requestsAssistanceForFromDeveloper(String user, String name, String dev)
    {
        devName = dev;

        try
        {
            manager.developerList.add(new Developer(dev));
            manager.getProject(projName).getActivity(name).requestAssistance(manager.getDeveloper(dev));
        }
        catch (OperationNotAllowedException ONAE)
        {
            errorMessage = ONAE.getMessage();
        }
    }

    @Then("{string} will be able to register hours spent on the activity {string}")
    public void willBeAbleToRegisterHoursSpentInTheActivity(String dev, String name)
    {
        try
        {
            assertTrue(manager.getProject(projName).getActivity(name).workingDevelopers.contains(manager.getDeveloper(dev)));
        }
        catch (OperationNotAllowedException ONAE)
        {
            errorMessage = ONAE.getMessage();
        }
    }

    @When("{string} requests assistance for {string} from busy developer {string}")
    public void requestsAssistanceForFromBusyDeveloper(String user, String name, String dev) throws OperationNotAllowedException
    {
        manager.developerList.add(new Developer(dev));
        Activity[] dummies = new Activity[21];
        for (int i = 0; i < 21; i++)
        {
            try
            {
                dummies[i] = new Activity("dummy"+i, 20, manager.getProject(projName).projectID, startWeek, endWeek);
                dummies[i].addWorkingDev(manager.getDeveloper(dev));
            }
            catch (OperationNotAllowedException ONAE)
            {
                errorMessage = ONAE.getMessage();
            }
        }
        try
        {
            manager.getProject(projName).getActivity(name).requestAssistance(manager.getDeveloper(dev));
        }
        catch (OperationNotAllowedException ONAE)
        {
            errorMessage = ONAE.getMessage();
        }
    }

    @Then("the error {string}  is returned")
    public void theErrorIsReturned(String error) {
        assertEquals(error,errorMessage);
    }


    @And("that the developers {string} and {string} are assigned a project activity {string} with start week {string}, end week {string} and time budget {int} exists")
    public void thatTheDevelopersAndAreAssignedAProjectActivityWithStartWeekEndWeekAndTimeBudgetExists(String dev1, String dev2, String activity, String stW, String edW, int tb) {
        try {
            manager.getProject(projName).activities.add(new Activity(activity,tb,manager.getProject(projName).projectID,stW,edW));
            manager.developerList.add(new Developer(dev1));
            manager.developerList.add(new Developer(dev2));
            manager.getProject(projName).getActivity(activity).addWorkingDev(manager.getDeveloper(dev1));
            manager.getProject(projName).getActivity(activity).addWorkingDev(manager.getDeveloper(dev2));

        } catch (OperationNotAllowedException ONAE) {
            errorMessage = ONAE.getMessage();
        }
    }
}
