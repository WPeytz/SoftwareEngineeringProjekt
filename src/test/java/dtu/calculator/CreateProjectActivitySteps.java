package dtu.calculator;

import dtu.system.Activity;
import dtu.system.Developer;
import dtu.system.OperationNotAllowedException;
import dtu.system.TimeManager;
import io.cucumber.java.en.*;

import static org.junit.Assert.*;

public class CreateProjectActivitySteps {
    TimeManager manager;
    Developer dev;
    String errorMessage;
    String projectName;

    public CreateProjectActivitySteps() {
        manager = new TimeManager();
    }

    @Given("that the {string} project named {string} with start week {string} and end week {string} exists")
    public void thatTheStringProjectNamedWithStartWeekAndEndWeek(String custProj, String projName, String stW, String edW) {

        try {
            manager.createProject(projName, custProj.equals("customer"),stW,edW);
        } catch (OperationNotAllowedException ONAE) {
            errorMessage = ONAE.getMessage();
        }
    }


    @And("that the user {string} is the project manager of the project {string}")
    public void thatTheUserIsTheProjectManagerOfTheProject(String init, String project) {
        try {
            manager.getProject(project).setProjectManager(manager.getDeveloper(init));
            assertEquals(manager.getProject(project).projectManager.initials, manager.getDeveloper(init).initials);
        } catch (OperationNotAllowedException ONAE) {
            errorMessage = ONAE.getMessage();
        }
    }

    @Then("the user {string} creates a new project activity {string}, start week {string}, end week {string} and time budget {int} hours for project {string}")
    public void theUserCreatesANewActivityWithTheNameStartWeekToEndWeekToAndTimeBudgetToHours(String ID, String name, String stw, String enw, int tb, String projName) {
        projectName = projName;
        try {
            manager.getProject(projName).activities.add(new Activity(name,tb,manager.getProject(projName).projectID,stw,enw));
        } catch (OperationNotAllowedException ONAE) {
            errorMessage = ONAE.getMessage();
        }
    }

    @Then("the new project activity {string} with start week {string}, end week {string} and time budget {int} hours for project {string} is created")
    public void theNewProjectActivityWithStartWeekEndWeekAndTimeBudgetHoursForProjectIsCreated(String name, String stw, String enw, int tb, String project) {
        try {
            Activity act = new Activity(name,tb,manager.getProject(project).projectID,stw,enw);
            assertEquals(act.name, manager.getProject(project).getActivity(name).name);
        } catch (OperationNotAllowedException ONAE) {
            errorMessage = ONAE.getMessage();
        }
    }


    @And("that the user {string} is not the project manager of the project {string}")
    public void thatTheUserIsNotTheProjectManagerOfTheProject(String init, String project) {
        try {
            assertFalse(manager.getProject(project).isProjectManager(init));
        } catch (OperationNotAllowedException ONAE) {
            errorMessage = ONAE.getMessage();
        }
    }

    @Then("the user will receive error message {string}")
    public void theUserWillReceiveErrorMessage(String err) {
        assertEquals(err,errorMessage);
    }
}
