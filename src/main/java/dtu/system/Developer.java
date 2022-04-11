package dtu.system;

import java.text.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.time.*;

public class Developer {
    public String initials;
    ArrayList<Activity> activities;

    public Developer(String initials)
    {
        this.initials = initials;
        activities = new ArrayList<>();
    }
    
    public double registerTimeSpent (Activity activity, String StDt, String EnDt) throws OperationNotAllowedException
    {
        if (!activity.workingDevelopers.contains(this)) {
            throw new OperationNotAllowedException("Developer" + this.initials + "is not on the activity" + activity.name);
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
                    throw new OperationNotAllowedException("Activity could not be created as the developer is not free in the given time period.");
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
