package dtu.system;

import java.time.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Activity {
    public String name;
    double timeBudget;
    LocalDate startWeek, endWeek;
    public ArrayList<Developer> workingDevelopers;
    boolean externalActivity = false;
    int projectID;
    ArrayList<Double> timeSpent;
    DateTimeFormatter format;

    public Activity(String name, double timeBudget, int projectID, String startWeek, String endWeek)
    {
        format = DateTimeFormatter.ofPattern("YYYY-ww-e");
        this.startWeek = LocalDate.parse(startWeek+"-1", format);
        this.endWeek = LocalDate.parse(endWeek+"-7", format);
        this.name = name;
        this.timeBudget = timeBudget;
        if (projectID == 0)
        {
            this.externalActivity = true;
        }
        else
        {
            this.projectID = projectID;
        }
        timeSpent = new ArrayList<>();
        workingDevelopers = new ArrayList<>();
    }

    public double activityTime()
    {
        double sum = 0;
        for (double d : timeSpent)
        {
            sum += d;
        }
        return sum;
    }

    public void addWorkingDev(Developer developer) throws OperationNotAllowedException {
        if (!workingDevelopers.contains(developer) && developer.isFree(this.startWeek,this.endWeek))
        {
            workingDevelopers.add(developer);
            developer.activities.add(this);
        } else {
            throw new OperationNotAllowedException("Activity could not be created as the developer is not free in the given time period.");
        }

    }

    public void requestAssistance (Developer assistingDev) throws OperationNotAllowedException {
        if (!this.workingDevelopers.contains(assistingDev) && assistingDev.isFree(this.startWeek,this.endWeek))
        {
            return "I request your assitance " + assistingDev.initials + " for " + activity.name;
        }
        else
        {
            throw new OperationNotAllowedException(assistingDev.initials + " is already working on the same activity as you or is busy.");
        }
    }

    public void setName(String name)
    {
        this.name = name;
    }
}
