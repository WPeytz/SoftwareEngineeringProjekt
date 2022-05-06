package dtu.system;


import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.time.*;

public class  Developer
{
    public String initials;
    ArrayList<Activity> activities;
    ArrayList<TimeSpent> workTimes;

    public Developer(){}

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
    
    public double registerTimeSpent(Activity activity, String StDt, String EnDt) throws OperationNotAllowedException, DateTimeParseException
    {
        assert (true);
        double prevActTime = activity.activityTime();
        if (!activity.workingDevelopers.contains(this)) //1
        {
            throw new OperationNotAllowedException("Developer is not on the activity"); //2
        }
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH.mm");
        LocalDateTime startTime = LocalDateTime.parse(StDt, format);
        LocalDateTime endTime = LocalDateTime.parse(EnDt, format);
        double l = Duration.between(startTime, endTime).toMinutes(); //3
        if (l <0) //4
        {
            throw new OperationNotAllowedException("Start time is after end time");
        }
        l = (double) Math.round(l/30)/2; //5
        activity.timeSpent.add(l); //6
        workTimes.add(new TimeSpent(activity, startTime, endTime, l));
        assert (activity.activityTime()-l == prevActTime);
        return l; //7
    }




    public boolean isFree(LocalDate newStartWeek, LocalDate newEndWeek) throws OperationNotAllowedException {
        assert (true); // Precondition
        for (LocalDate i = newStartWeek; i.isBefore(newEndWeek); i = i.plusWeeks(1)) {
            int activityCount = 0;
            for (Activity a : activities) {
                if (activityCount >= 20) {
                    assert (activityCount >=20); // Postcondition
                    throw new OperationNotAllowedException("Developer is not free in the given time period.");
                } else if ((i.isAfter(a.startWeek)) && (i.isBefore(a.endWeek))) {
                    activityCount++;
                }
            }
        }
        return true;
    }
}
