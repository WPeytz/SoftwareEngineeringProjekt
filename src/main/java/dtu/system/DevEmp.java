package dtu.system;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.IsoFields;
import java.util.*;
import java.time.*;

public class DevEmp {
    String Initials;
    ArrayList<Activity> activities;

    public DevEmp (String initials)
    {
        this.Initials = initials;
        activities = new ArrayList<>();
    }
    
    public void registerTimeSpent (Activity activity, String StDt, String EnDt) throws ParseException
    {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH.mm");
        LocalDateTime startTime = LocalDateTime.parse(StDt, format);
        LocalDateTime endTime = LocalDateTime.parse(EnDt, format);
        double l = Duration.between(startTime, endTime).toMinutes();
        l = (double) Math.round(l/30)/2;
        activity.timeSpent.add(l);
    }


    public boolean isFree(LocalDateTime newStartWeek, LocalDateTime newEndWeek)
    {
        for (LocalDateTime i = newStartWeek; i.isBefore(newEndWeek);i = i.plusWeeks(1))
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
