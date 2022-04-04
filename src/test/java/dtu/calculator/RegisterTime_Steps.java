package dtu.calculator;

import io.cucumber.java.en.*;

public class RegisterTime_Steps
{
    TimeManager manager;
    Activity a;
    DevEmp dev;
    String startTime, endTime;

    public RegisterTime_Steps () {
        manager = new TimeManager();
    }



    @Given("that the developer {string} is assigned an project {int} activity {string}")
    public void that_the_developer_is_assigned_an_project_activity(String devEmp, int projectID, String activity) throws Exception{
        dev = manager.getDevEmp(devEmp);
        Project project = manager.getProject(projectID);
        a = project.getActivity(activity);
        assertTrue(a.workingDevelopers.contains(dev));
    }
    @When("{string} registers start time as {string}")
    public void registers_start_time_as(String string, String string2) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
    @When("registers end time as {string} to project activity {string}")
    public void registers_end_time_as_to_project_activity(String string, String string2) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
    @Then("the total time is rounded to {double} hours")
    public void the_total_time_is_rounded_to_hours(Double double1) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
    @Then("{double} hours is added to total time spent on project activity {string}")
    public void hours_is_added_to_total_time_spent_on_project_activity(Double double1, String string) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Given("the first input is {int}")
    public void the_first_input_is(Integer int1) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
    @Given("the second input is {int}")
    public void the_second_input_is(Integer int1) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
    @When("the add button is pressed")
    public void the_add_button_is_pressed() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
    @Then("{int} is shown on the display.")
    public void is_shown_on_the_display(Integer int1) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

}
