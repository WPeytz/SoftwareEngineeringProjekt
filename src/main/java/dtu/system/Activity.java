package dtu.system;

import java.time.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Activity {
    String name;
    double timeBudget;
    LocalDate startWeek, endWeek;
    public ArrayList<Developer> workingDevelopers;
    boolean externalActivity = false;
    int projectID;
    ArrayList<Double> timeSpent;
    DateTimeFormatter format;

    public Activity(String name, double timeBudget, int projectID, String startWeek, String endWeek)
    {
        format = DateTimeFormatter.ofPattern("yyyy-ww-EEE");
        this.startWeek = LocalDate.parse(startWeek+"-Mon", format);
        this.endWeek = LocalDate.parse(endWeek+"-Sun", format);
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

    public void addWorkingDev(Developer developer)
    {
        if (!workingDevelopers.contains(developer) && developer.isFree(this.startWeek,this.endWeek))
        {
            workingDevelopers.add(developer);
            developer.activities.add(this);
        }
    }

    public String requestAssistance (Developer assistingDev, Activity activity)
    {
        if (!activity.workingDevelopers.contains(assistingDev) && assistingDev.isFree(activity.startWeek,activity.endWeek))
        {
            return "I request your assitance " + assistingDev.initials + " for " + activity.name;
        }
        else
        {
            return assistingDev.initials + " is already working on the same activity as you or is busy.";
        }
    }

    public void setName(String name)
    {
        this.name = name;
    }
}
