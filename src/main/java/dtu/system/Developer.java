package dtu.system;


import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.time.*;

public class  Developer
{
    public String initials;
    ArrayList<Activity> activities;
    public ArrayList<TimeSpent> workTimes;

    //William
    public Developer(String initials) throws OperationNotAllowedException
    {
        if(initials.matches("[a-zA-Z]+"))
        {
            if (initials.length() > 0 && initials.length() <= 4)
            {
                this.initials = initials;
                activities = new ArrayList<>();
                workTimes = new ArrayList<>();
            }
            else
            {
                throw new OperationNotAllowedException("Invalid amount of initials for developer name. Initials length must be between 1 and 4 characters (inclusive)");
            }
        }
        else
        {
            throw new OperationNotAllowedException("Illegal character(s) found. Only letters are allowed in developer initials");
        }
    }

    //Nikolai
    public double registerTimeSpent(Activity activity, String StDt, String EnDt) throws OperationNotAllowedException
    {
        assert (true);
        LocalDateTime startTime;
        LocalDateTime endTime;
        double prevActTime = activity.activityTime();
        if (!activity.workingDevelopers.contains(this)) //1
        {
            throw new OperationNotAllowedException("Developer is not on the activity"); //2
        }
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH.mm");
        try
        {
            startTime = LocalDateTime.parse(StDt, format); //3
            endTime = LocalDateTime.parse(EnDt, format); //4
        }
        catch (DateTimeParseException ONAE)
        {
            throw new OperationNotAllowedException("The entered dates are not of the right format."); //5
        }

        double l = Duration.between(startTime, endTime).toMinutes(); //6
        if (l <0) //7
        {
            throw new OperationNotAllowedException("Start time is after end time"); //8
        }
        l = (double) Math.round(l/30)/2; //9
        activity.timeSpent.add(l); //10
        workTimes.add(new TimeSpent(activity, startTime, endTime, l)); //11
        assert (activity.activityTime()-l == prevActTime);
        return l; //12
    }

    //Wind
    public boolean isFree(LocalDate newStartWeek, LocalDate newEndWeek) throws OperationNotAllowedException
    {
        assert (true); // Precondition
        for (LocalDate i = newStartWeek; i.isBefore(newEndWeek); i = i.plusWeeks(1))
        {
            int activityCount = 0;
            for (Activity a : activities)
            {
                if (activityCount >= 20)
                {
                    assert (activityCount >= 20); // Postcondition
                    throw new OperationNotAllowedException("Developer is not free in the given time period.");
                } else if ((i.isAfter(a.startWeek)) && (i.isBefore(a.endWeek))) {
                    activityCount++;
                }
            }
        }
        return true;
    }
}
