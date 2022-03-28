package dtu.system;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.time.*;

public class Project {
    ArrayList<Activity> activities;
    String name;
    int projectID;
    LocalDate startWeek, endWeek;
    DevEmp projectManager;
    boolean customerProject;
    int tracking = 0;
    DecimalFormat decFormat = new DecimalFormat("0000");

    public Project(String name, boolean customerProject,LocalDate startWeek, LocalDate endWeek) {
        this.name = name;
        this.customerProject = customerProject;
        this.startWeek = startWeek;
        this.endWeek = endWeek;
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
    public double timeBudget() {
        double sum = 0;
        for (Activity a : activities) {
            sum += a.timeBudget;
        }
        return sum;
    }

    public int incTracking () {
        tracking++;
        return tracking;
    }

/*
    public int getProjectID () {
        return projectID;
    }

    public String getName () {
        return name;
    }

    public String getProjectManager () {
        return projectManager.Initials;
    }
    */

}
