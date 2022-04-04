package dtu.system;

import java.text.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.time.*;

public class Developer {
    String initials;
    ArrayList<Activity> activities;

    public Developer(String initials)
    {
        this.initials = initials;
        activities = new ArrayList<>();
    }
    
    public double registerTimeSpent (Activity activity, String StDt, String EnDt) throws ParseException
    {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH.mm");
        LocalDateTime startTime = LocalDateTime.parse(StDt, format);
        LocalDateTime endTime = LocalDateTime.parse(EnDt, format);
        double l = Duration.between(startTime, endTime).toMinutes();
        l = (double) Math.round(l/30)/2;
        activity.timeSpent.add(l);
        return l;
    }


    public boolean isFree(LocalDate newStartWeek, LocalDate newEndWeek)
    {
        for (LocalDate i = newStartWeek; i.isBefore(newEndWeek);i = i.plusWeeks(1))
        {
            int activityCount = 0;
            for (Activity a : activities)
            {
                if (activityCount >= 20)
                {
                    return false;
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
