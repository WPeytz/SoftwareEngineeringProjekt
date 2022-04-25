package dtu.system;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Activity
{
    public String name;
    double timeBudget;
    public LocalDate startWeek, endWeek;
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

        this.externalActivity = (projectID == 0);

        this.projectID = projectID;
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

    public void addWorkingDev(Developer developer) throws OperationNotAllowedException
    {
        developer.isFree(this.startWeek,this.endWeek);

        if (workingDevelopers.contains(developer))
        {
            throw new OperationNotAllowedException("Developer is already working on the activity");
        }
        workingDevelopers.add(developer);
        developer.activities.add(this);
    }
}
