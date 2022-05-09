package dtu.system;

import io.cucumber.datatable.internal.difflib.StringUtills;

import java.sql.Time;
import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.time.*;

public class Project
{
    public ArrayList<Activity> activities;
    String name;
    public final int projectID;
    public LocalDate startWeek, endWeek;
    public Developer projectManager;
    public ArrayList<Developer> workingProjectDevelopers;
    boolean customerProject;
    DecimalFormat decFormat = new DecimalFormat("0000");
    DateTimeFormatter format;

    //William
    public Project(String name, boolean customerProject, String startWeek, String endWeek) throws OperationNotAllowedException
    {
        format = DateTimeFormatter.ofPattern("YYYY-ww-e");
        this.name = name;
        this.customerProject = customerProject;
        this.startWeek = LocalDate.parse(startWeek+"-1",format);
        this.endWeek = LocalDate.parse(endWeek+"-7",format);
        if (this.endWeek.isBefore(this.startWeek))
        {
            throw new OperationNotAllowedException("End week of project is before start week");
        }
        activities = new ArrayList<>();
        workingProjectDevelopers = new ArrayList<>();
        projectID = Integer.parseInt(String.valueOf(this.startWeek.getYear()).substring(2,4)
                +decFormat.format(TimeManager.projectList.size()+1));
    }

    //Gosden
    public void setProjectManager(Developer projectManager) throws OperationNotAllowedException
    {
        this.projectManager = projectManager;
        if (!workingProjectDevelopers.contains(projectManager))
        {
            workingProjectDevelopers.add(projectManager);
        }
    }

    //Wind
    public void addWorkingDev(Developer developer) throws OperationNotAllowedException
    {
        if (!workingProjectDevelopers.contains(developer))
        {
            workingProjectDevelopers.add(developer);
        }
        else
        {
            throw new OperationNotAllowedException("This developer has already been assigned to the project");
        }
    }

    //Nikolai
    public boolean isProjectManager (String init) throws OperationNotAllowedException
    {
        if (this.projectManager == null || !this.projectManager.initials.equals(init))
        {
            throw new OperationNotAllowedException("You are not project manager");
        }
        else return true;

    }

    //Wind
    public double totalTimeSpent()
    {
        double sum = 0;
        for (Activity a : activities)
        {
            sum += a.activityTime();
        }

        return sum;
    }

    //Gosden
    public double timeBudget()
    {
        double sum = 0;
        for (Activity a : activities)
        {
            sum += a.timeBudget;
        }
        return sum;
    }

    //William
    public Activity getActivity (String name) throws OperationNotAllowedException
    {
        Activity act = null;

        for (Activity a : activities)
        {
            if (a.name.equals(name))
            {
                act = a;
                break;
            }
        }
        if (act == null)
        {
            throw new OperationNotAllowedException("Activity \"" + name + "\" could not be found");
        }
        return act;

    }

    //Wind
    public void changeProjectEndWeek(String endWeek)
    {
        this.endWeek = LocalDate.parse(endWeek+"-7",format);
    }

    //William
    public int getProjectID ()
    {
        return projectID;
    }

    //Nikolai
    public String getCustomerProject()
    {
        if (customerProject)
        {
            return "Customer Project";
        }
        else
        {
            return "Internal Project";
        }
    }

    //William
    public String getName()
    {
        return name;
    }

    //Wind
    public String setName(String name)
    {
        this.name = name;
        return "The new name of the project is: " + this.name;
    }

}
