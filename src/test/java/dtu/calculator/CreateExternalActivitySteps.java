package dtu.calculator;

import dtu.system.*;
import io.cucumber.java.en.*;
import io.cucumber.java.hu.De;
import static org.junit.Assert.*;
import java.time.LocalDate;


public class CreateExternalActivitySteps {
    Developer dev, demp1, demp2, demp3, dev_WB;
    Activity a;
    TimeManager manager = new TimeManager();
    String actName, errorMessage;
    String stW, edW;
    boolean status;

    @Given("that the developer {string} wants to create an external activity called {string}.")
    public void that_the_developer_wants_to_create_an_external_activity_called(String devName, String chosenActName) throws OperationNotAllowedException {
        dev = new Developer(devName);
        if (!manager.developerList.contains(dev)) {
            manager.developerList.add(dev);
        }
        actName = chosenActName;
    }

    @When("the developer {string} chooses to create an activity with name {string} as an external activity, with start week {string} and end week {string}.")
    public void the_developer_chooses_to_create_an_activity_with_name_as_an_external_activity_with_start_week_and_end_week(String devName, String chosenActName, String startWeek, String endWeek) {
        stW = startWeek;
        edW = endWeek;
        a = new Activity(actName, 0, 0, startWeek, endWeek);
        manager.extActList.add(a);
    }

    @Then("developer writes {string}, {string} and {string}")
    public void developer_writes_and(String emp1, String emp2, String emp3) throws OperationNotAllowedException {
        manager.developerList.add(new Developer(emp1));
        manager.developerList.add(new Developer(emp2));
        manager.developerList.add(new Developer(emp3));
        try {
            demp1 = manager.getDeveloper(emp1);
            demp2 = manager.getDeveloper(emp2);
            demp3 = manager.getDeveloper(emp3);
        } catch (OperationNotAllowedException ONAE) {
            errorMessage = ONAE.getMessage();
        }
    }

    @Given("that the chosen developers are free employees in the time period")
    public void that_the_chosen_developers_are_free_employees_in_the_time_period() {
        try {
            assertTrue(demp1.isFree(a.startWeek, a.endWeek));
            assertTrue(demp2.isFree(a.startWeek, a.endWeek));
            assertTrue(demp3.isFree(a.startWeek, a.endWeek));
        } catch (OperationNotAllowedException ONAE) {
            errorMessage = ONAE.getMessage();
        }

    }

    @Then("the external activity is created")
    public void the_external_activity_is_created() {
        try {
            assertEquals(a.name, manager.getExternalActivity(a.name).name);
        } catch (OperationNotAllowedException ONAE) {
            errorMessage = ONAE.getMessage();
        }
    }

    @Then("the three developers are added to the external activity")
    public void the_three_developers_are_added_to_the_external_activity() {
        try {
            manager.getExternalActivity(a.name).workingDevelopers.add(demp1);
            manager.getExternalActivity(a.name).workingDevelopers.add(demp2);
            manager.getExternalActivity(a.name).workingDevelopers.add(demp3);
        } catch (OperationNotAllowedException ONAE) {
            errorMessage = ONAE.getMessage();
        }
    }

    @Given("that at least one of the developers are not a free employee in the chosen time period")
    public void thatAtLeastOneOfTheDevelopersAreNotAFreeEmployeeInTheChosenTimePeriod() {
        Activity[] dummies = new Activity[21];

        for (int i = 0; i < 21; i++) {
            dummies[i] = new Activity("dummy" + i, 20, 0, stW, edW);
            try {
                dummies[i].addWorkingDev(demp1);
                dummies[i].addWorkingDev(demp2);
                dummies[i].addWorkingDev(demp3);
            } catch (OperationNotAllowedException ONAE) {
                errorMessage = ONAE.getMessage();
            }
        }
        try {
            assertTrue(demp1.isFree(a.startWeek, a.endWeek));
            assertTrue(demp2.isFree(a.startWeek, a.endWeek));
            assertTrue(demp3.isFree(a.startWeek, a.endWeek));
        } catch (OperationNotAllowedException ONAE) {
            errorMessage = ONAE.getMessage();
        }

    }

    @Then("developer {string} will be met with the error message {string}")
    public void developer_will_be_met_with_the_error_message(String init, String err)
    {
        assertEquals(errorMessage, err);
    }

    //______________________________________________________________________________________________________________________
    // White box test 2
    @When("the developer {string} is checked if he is free in the time period from {string} to {string}")
    public void theDeveloperIsCheckedIfHeIsFreeInTheTimePeriodFromTo(String dev, String startWeek, String endWeek) {
        try {
            dev_WB = new Developer(dev);
        } catch (OperationNotAllowedException e) {
            throw new RuntimeException(e);
        }
        manager.developerList.add(dev_WB);
        Activity[] dummies = new Activity[21];

        for (int i = 0; i < 21; i++) {
            dummies[i] = new Activity("Meeting" + i, 20, 220001, startWeek, endWeek);
            try {
                dummies[i].addWorkingDev(dev_WB);
            } catch (OperationNotAllowedException ONAE) {
                errorMessage = ONAE.getMessage();
            }
        }
        try {
            assertTrue(dev_WB.isFree(dummies[1].startWeek, dummies[1].endWeek));
        } catch (OperationNotAllowedException ONAE) {
            errorMessage = ONAE.getMessage();
        }
    }
    @When("the developer {string} is checked if he is free in the given time period from {string} to {string}")
    public void theDeveloperIsCheckedIfHeIsFreeInTheGivenTimePeriodFromTo(String dev, String startWeek, String endWeek) {
        try
        {
            dev_WB = new Developer(dev);
        }
        catch (OperationNotAllowedException e)
        {
            throw new RuntimeException(e);
        }
        manager.developerList.add(dev_WB);
        Activity[] dummies = new Activity[21];

        for (int i = 0; i < 2; i++)
        {
            dummies[i] = new Activity("Meeting" + i, 20, 220001, startWeek, endWeek);
            try
            {
                dummies[i].addWorkingDev(dev_WB);
            }
            catch (OperationNotAllowedException ONAE)
            {
                errorMessage = ONAE.getMessage();
            }
        }
        try
        {
            this.status = dev_WB.isFree(dummies[1].startWeek, dummies[1].endWeek);
            assertTrue(dev_WB.isFree(dummies[1].startWeek, dummies[1].endWeek));
        }
        catch (OperationNotAllowedException ONAE)
        {
            errorMessage = ONAE.getMessage();
        }
    }
    @Then("return {string}")
    public void stringReturn(String bool)
    {
        assertEquals(String.valueOf(this.status), bool);
    }
    @When("the developer {string} is checked if he is free in the selected time period from {string} to {string}")
    public void theDeveloperIsCheckedIfHeIsFreeInTheSelectedTimePeriodFromTo(String dev, String startWeek, String endWeek) {
        try
        {
            dev_WB = new Developer(dev);
        }
        catch (OperationNotAllowedException e)
        {
            throw new RuntimeException(e);
        }
        manager.developerList.add(dev_WB);
        try
        {
            LocalDate stWeek = manager.toLocalDate(startWeek);
            LocalDate enWeek = manager.toLocalDate(endWeek);
            this.status = dev_WB.isFree(stWeek, enWeek);
            assertTrue(dev_WB.isFree(stWeek, enWeek));
        }
        catch (OperationNotAllowedException ONAE)
        {
            errorMessage = ONAE.getMessage();
        }
    }
}
