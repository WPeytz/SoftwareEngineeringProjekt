package dtu.system;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;



public class Activity {
    String name;
    double timeBudget;
    LocalDateTime startWeek, endWeek;
    ArrayList<DevEmp> workingDevelopers;
    boolean externalActivity;
    ArrayList<Double> timeSpent;


    public Activity(String name, double timeBudget, int projectID, String startWeek, String endWeek) {
        format = DateTimeFormatter.ofPattern("yyyy-ww-EEE");
        this.startWeek = LocalDateTime.parse(startWeek+"-Mon", format);
        this.endWeek = LocalDateTime.parse(endWeek+"-Sun", format);
        this.name = name;
        this.timeBudget = timeBudget;
        this.externalActivity = externalActivity;
        timeSpent = new ArrayList<>();
        workingDevelopers = new ArrayList<>();
    }

    public double activityTime() {
        double sum = 0;
        for (double d : timeSpent) {
            sum += d;
        }
        return sum;
    }

    public void addWorkingDev(DevEmp devEmp){
        if (!workingDevelopers.contains(devEmp)) {
            workingDevelopers.add(devEmp);
        }
    }

    public String requestAssistance (DevEmp assistingDev, Activity activity){
        if (!activity.workingDevelopers.contains(assistingDev) && assistingDev.isFree(activity.startWeek,activity.endWeek))
        {
            return "I request your assitance " + assistingDev.Initials + " for " + activity.name;
        } else
            return assistingDev.Initials + " is already working on the same activity as you or is busy.";
    }
}
