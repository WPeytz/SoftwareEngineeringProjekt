package dtu.calculator;

import dtu.system.TimeManager;
import io.cucumber.java.en.*;
import static org.junit.Assert.*;

public class CreateProjectActivity_Steps 
{
    TimeManager manager;
    public CreateProjectActivity_Steps() 
    {
        manager = new TimeManager();
    }
    
    @Given("that the project named {string} is chosen")
    public void thatTheProjectNamedIsChosen(String project) 
    {
        assertTrue(manager.projectExists(project));
    }
    @Given("create project activity is chosen")
    public void createProjectActivityIsChosen() 
    {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
    @When("user {string} writes their ID")
    public void userWritesTheirID(String string) 
    {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
    @Then("the system checks if they are the project manager of the project {string}")
    public void theSystemChecksIfTheyAreTheProjectManagerOfTheProject(String string) 
    {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
    @Given("that the user is the project manager of the project {string}")
    public void thatTheUserIsTheProjectManagerOfTheProject(String string)
    {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
    @Then("the user creates a new activity with the name {string}")
    public void theUserCreatesANewActivityWithTheName(String string)
    {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
    @Then("set the start week to {string}")
    public void setTheStartWeekTo(String string) 
    {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
    @Then("set the end week to {string}")
    public void setTheEndWeekTo(String string)
    {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
    @Then("set time budget to {int} hours")
    public void setTimeBudgetToHours(Integer int1)
    {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
    @Then("the new project activity is created")
    public void theNewProjectActivityIsCreated() 
    {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

}
