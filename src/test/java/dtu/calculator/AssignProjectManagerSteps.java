package dtu.calculator;

import dtu.system.*;
import io.cucumber.java.en.*;
import io.cucumber.java.hu.De;
import static org.junit.Assert.*;
public class AssignProjectManagerSteps {
    TimeManager manager;
    String errorMessage;
    Project proj;
    public AssignProjectManagerSteps() {
        manager = new TimeManager();
    }


    @Given("that the {string} project {string} with start week {string} and end week {string} exists.")
    public void thatTheProjectWithStartWeekAndEndWeekExists(String custom, String projName, String startWeek, String endWeek) {
        try {
            manager.createProject(projName,custom.equals("custom"),startWeek,endWeek);
            proj = manager.getProject(projName);
        } catch (OperationNotAllowedException ONAE) {
            errorMessage = ONAE.getMessage();
        }
        assertTrue(manager.projectExists(projName));
    }

    @And("that the developer {string} and {string} exists")
    public void thatTheDeveloperAndExists(String user, String dev) {
        manager.developerList.add(new Developer(user));
        manager.developerList.add(new Developer(dev));
    }

    @Then("the developer {string} assigns {string} as project manager for project {string}")
    public void theDeveloperAddsAsProjectManagerForProject(String user, String dev, String ID) {
        try {
            proj.setProjectManager(manager.getDeveloper(dev));
        } catch (OperationNotAllowedException ONAE) {
            errorMessage = ONAE.getMessage();
        }

    }

    @And("that the developer {string}, {string} and {string} exists")
    public void thatTheDeveloperAndExists(String user, String dev1, String dev2) {
        manager.developerList.add(new Developer(user));
        manager.developerList.add(new Developer(dev1));
        manager.developerList.add(new Developer(dev2));
    }

    @And("that {string} is project manager for {string} project {string}")
    public void thatIsProjectManagerForProject(String dev1, String custom, String projName) {

        try {
            manager.getProject(projName).setProjectManager(manager.getDeveloper(dev1));
            assertEquals(manager.getProject(projName).projectManager.initials,dev1);
        } catch (OperationNotAllowedException ONAE) {
            errorMessage = ONAE.getMessage();
        }
    }

    @Then("the new project manager for {string} project {string} is {string}")
    public void theNewProjectManagerForProjectIs(String cust, String projName, String dev2) {
        try {
            manager.getProject(projName).setProjectManager(manager.getDeveloper(dev2));
            assertEquals(manager.getProject(projName).projectManager.initials,dev2);
        } catch (OperationNotAllowedException ONAE) {
            errorMessage = ONAE.getMessage();
        }
    }
}
