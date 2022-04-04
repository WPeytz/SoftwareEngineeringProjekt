package dtu.system;

import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.time.*;

public class Project {
    public ArrayList<Activity> activities;
    String name;
    int projectID;
    LocalDate startWeek, endWeek;
    Developer projectManager;
    boolean customerProject;
    int tracking = 0;
    DecimalFormat decFormat = new DecimalFormat("0000");
    DateTimeFormatter format;

    public Project(String name, boolean customerProject, String startWeek, String endWeek)
    {
        format = DateTimeFormatter.ofPattern("YYYY-ww-EEE");

        this.name = name;
        this.customerProject = customerProject;
        this.startWeek = LocalDate.parse(startWeek+"-Mon",format);
        this.endWeek = LocalDate.parse(endWeek+"-Sun",format);
        activities = new ArrayList<>();
        projectID = Integer.parseInt(String.valueOf(this.startWeek.getYear()).substring(2,4)
                +decFormat.format(incTracking()));
    }

    public void setProjectManager(Developer projectManager) {
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

    public Activity getActivity (String name) throws Exception{
        for (Activity a : activities) {
            if (a.name.equals(name)){
                return a;
            }
        }
        throw new Exception("Activity \"" + name + "\" could not be found");
    }

    public void changeProjectEndWeek(String endWeek)
    {
        this.endWeek = LocalDate.parse(endWeek+"-Sun",format);
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
