package dtu.calculator;

import io.cucumber.java.en.*;
import dtu.system.*;
import static org.junit.Assert.*;

public class CreateReportSteps
{
    String errorMessage;
    TimeManager manager;
    public CreateReportSteps()
    {
        manager = new TimeManager();
    }

    @Given("that the developer {string} and {string} project {string} with start week {string}, end week {string} exists")
    public void thatTheDeveloperAndProjectWithStartWeekEndWeekAndTimebudgetExists(String dev, String customer, String projName, String startWeek, String endWeek) {
        try
        {
            manager.developerList.add(new Developer(dev));
            manager.createProject(projName,customer.equalsIgnoreCase("customer"),startWeek,endWeek);
        }
        catch (OperationNotAllowedException ONAE)
        {
            errorMessage = ONAE.getMessage();
        }
        assertTrue(manager.projectExists(projName));
    }
    @Given("that {string} is the project manager of the project {string}")
    public void thatIsTheProjectManagerOfTheCustomerProject(String projMngr, String projName)
    {
        try
        {
            manager.getProject(projName).setProjectManager(manager.getDeveloper(projMngr));
            assertTrue(manager.getProject(projName).isProjectManager(projMngr));
        }
        catch (OperationNotAllowedException ONAE)
        {
            errorMessage = ONAE.getMessage();
        }
    }

    @Given("{string} creates a weekly report for project {string}")
    public void createsAWeeklyReportForProject(String projMngr, String projName)
    {
        try
        {
            manager.createReport(manager.getProject(projName),manager.getDeveloper(projMngr));
        }
        catch (OperationNotAllowedException ONAE)
        {
            errorMessage = ONAE.getMessage();
        }
    }
    @Then("a report for {string} is created containing name of the project, project type, time budget, total time spent on the project and the estimated work time that is left on the project")
    public void aReportForIsCreatedContainingNameOfTheProjectProjectTypeTimeBudgetTotalTimeSpentOnTheProjectAndTheEstimatedWorkTimeThatIsLeftOnTheProject(String projName)
    {
        try
        {
            Project project = manager.getProject(projName);
            String proj = project.getName();
            String cust = project.getCustomerProject();
            double timebud = project.timeBudget();
            double timeSpent = project.totalTimeSpent();
            double estTimeLeft = project.timeBudget() - project.totalTimeSpent();

            String reportText = manager.readReport(project.getName());

            assertTrue(reportText.contains(proj));
            assertTrue(reportText.contains(cust));
            assertTrue(reportText.contains(timebud + "."));
            assertTrue(reportText.contains(timeSpent + " hours "));
            assertTrue(reportText.contains(estTimeLeft + " hours."));
        }
        catch (OperationNotAllowedException | FileNotFoundException EX)
        {
            EX.printStackTrace();
        }
    }

    @Given("that {string} is not the project manager of the customer project {string}")
    public void thatIsNotTheProjectManagerOfTheCustomerProject(String dev, String projName) {
        try
        {
            assertFalse(manager.getProject(projName).isProjectManager(dev));
        }
        catch (OperationNotAllowedException ONAE)
        {
            errorMessage = ONAE.getMessage();
        }
    }
    @Then("it returns the error message {string}")
    public void itReturnsTheErrorMessage(String error)
    {
        assertEquals(errorMessage,error);
    }

}