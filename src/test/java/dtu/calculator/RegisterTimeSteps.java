package dtu.calculator;

import dtu.system.Activity;
import dtu.system.Developer;
import dtu.system.TimeManager;

public class RegisterTimeSteps
{
    TimeManager manager;
    Activity a;
    Developer dev;
    String startTime, endTime;
    double l, prevTotal;

    public RegisterTimeSteps() {
        manager = new TimeManager();
    }



//    @Given("that the developer {string} is assigned an activity {string} in project {string}")
//    public void that_the_developer_is_assigned_an_project_activity(String developer, String activity, String projectID) throws Exception{
//        manager.developerList.add(new Developer(developer));
//        dev = manager.getDeveloper(developer);
//        Project project = manager.getProject(Integer.parseInt(projectID));
//        a = project.getActivity(activity);
//        assertTrue(a.workingDevelopers.contains(dev));
//    }
//    @When("{string} registers start time as {string}")
//    public void registers_start_time_as(String developer, String starttime) {
//        startTime = starttime;
//    }
//    @When("registers end time as {string} to project activity {string}")
//    public void registers_end_time_as_to_project_activity(String endtime, String activity) throws Exception {
//         endTime = endtime;
//         double prevTotal = a.activityTime();
//         l = dev.registerTimeSpent(a,startTime,endTime);
//    }
//    @Then("the total time is rounded to {double} hours")
//    public void the_total_time_is_rounded_to_hours(double roundedTime) {
//        assertEquals(l, roundedTime, 0.0);
//    }
//    @Then("{double} hours is added to total time spent on project activity {string}")
//    public void hours_is_added_to_total_time_spent_on_project_activity(Double roundedTime, String activity) {
//        assertEquals(prevTotal,a.activityTime(),roundedTime);
//    }
}
