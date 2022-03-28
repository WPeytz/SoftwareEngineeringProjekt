package dtu.system;

import java.util.ArrayList;
import java.util.Calendar;

public class Project {
    ArrayList<Activity> activities;
    String name;
    String projectID;
    Calendar startDate, endDate;
    DevEmp projectManager;
    boolean customerProject;
    int tracking = 1;

    public Project(String name, boolean customerProject) {
        this.name = name;
        this.customerProject = customerProject;
        activities = new ArrayList<>();
        projectID = Integer.parseInt(String.valueOf(this.startWeek.getYear()).substring(2,4)
                +decFormat.format(incTracking()));
    }

    public void setProjectManager(DevEmp projectManager) {
        this.projectManager = projectManager;
    }

    public double totalTimeSpent() {
        double sum = 0;
        for (Activity a : activities) {
            sum += a.activityTime();
        }
        return sum;
    }

    public ArrayList<DevEmp> getWorkingDevelopers () {
        ArrayList<DevEmp> workingDevs = new ArrayList<>();
        for (Activity a : activities) {
            for (DevEmp dev : a.workingDevelopers) {
                workingDevs.add(dev);
            }
        }
        return workingDevs;
    }
}
