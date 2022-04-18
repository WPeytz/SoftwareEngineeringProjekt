package dtu.system;

import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.time.*;

public class Project
{
    public ArrayList<Activity> activities;
    String name;
    public int projectID;
    public LocalDate startWeek, endWeek;
    public Developer projectManager;
    boolean customerProject;
    int tracking = 0;
    DecimalFormat decFormat = new DecimalFormat("0000");
    DateTimeFormatter format;

    public Project(String name, boolean customerProject, String startWeek, String endWeek)
    {
        format = DateTimeFormatter.ofPattern("YYYY-ww-e");

        this.name = name;
        this.customerProject = customerProject;
        this.startWeek = LocalDate.parse(startWeek+"-1",format);
        this.endWeek = LocalDate.parse(endWeek+"-7",format);
        activities = new ArrayList<>();
        workingProjectDevelopers = new ArrayList<>();
        projectID = Integer.parseInt(String.valueOf(this.startWeek.getYear()).substring(2,4)
                +decFormat.format(incTracking()));
    }

    public void setProjectManager(Developer projectManager)
    {
        this.projectManager = projectManager;
    }

    public void addWorkingDev(Developer developer) throws OperationNotAllowedException {
        if (!workingProjectDevelopers.contains(developer)) {
            workingProjectDevelopers.add(developer);
        } else {
            throw new OperationNotAllowedException("This developer has already been assigned to the project");
        }
    }

    public boolean isProjectManager (String init) throws OperationNotAllowedException
    {
        if (this.projectManager == null || !this.projectManager.initials.equals(init))
        {
            throw new OperationNotAllowedException("You are not project manager");
        }
        else return true;

    }

    public double totalTimeSpent()
    {
        double sum = 0;
        for (Activity a : activities)
        {
            sum += a.activityTime();
        }
        return sum;
    }
    public double timeBudget()
    {
        double sum = 0;
        for (Activity a : activities)
        {
            sum += a.timeBudget;
        }
        return sum;
    }

    public int incTracking()
    {
        tracking++;
        return tracking;
    }

    public Activity getActivity (String name) throws OperationNotAllowedException
    {
        for (Activity a : activities)
        {
            if (a.name.equals(name))
            {
                return a;
            }
        }
        throw new OperationNotAllowedException("Activity \"" + name + "\" could not be found");
    }

    public void changeProjectEndWeek(String endWeek)
    {
        this.endWeek = LocalDate.parse(endWeek+"-7",format);
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
