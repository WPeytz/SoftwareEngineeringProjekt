package dtu.system;


import java.time.format.DateTimeFormatter;
import java.util.*;
import java.time.*;

public class  Developer
{
    public String initials;
    ArrayList<Activity> activities;

    public Developer(String initials) throws OperationNotAllowedException
    {
        if(initials.matches("[a-zA-Z]+"))
        {
            if (initials.length() > 0 && initials.length() <= 4)
            {
                this.initials = initials;
                activities = new ArrayList<>();
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
    
    public double registerTimeSpent (Activity activity, String StDt, String EnDt) throws OperationNotAllowedException
    {
        if (!activity.workingDevelopers.contains(this))
        {
            throw new OperationNotAllowedException("Developer is not on the activity");
        }
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH.mm");
        LocalDateTime startTime = LocalDateTime.parse(StDt, format);
        LocalDateTime endTime = LocalDateTime.parse(EnDt, format);
        double l = Duration.between(startTime, endTime).toMinutes();
        l = (double) Math.round(l/30)/2;
        activity.timeSpent.add(l);
        return l;
    }


    public boolean isFree(LocalDate newStartWeek, LocalDate newEndWeek) throws OperationNotAllowedException
    {
        for (LocalDate i = newStartWeek; i.isBefore(newEndWeek);i = i.plusWeeks(1))
        {
            int activityCount = 0;
            for (Activity a : activities)
            {
                if (activityCount >= 20)
                {
                    throw new OperationNotAllowedException("Developer is not free in the given time period.");
                }
                else if ((i.isAfter(a.startWeek)) && (i.isBefore(a.endWeek)))
                {
                    activityCount++;
                }
            }
        }
        return true;
    }
}
