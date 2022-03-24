package dtu.system;



import java.util.ArrayList;
import java.util.Calendar;

public class Activity /* extends Project */ {
    String name;
    Calendar startDate, endDate;
    double timeBudget;
    int weekNumber;
    ArrayList<DevEmp> workingDevelopers;
    boolean externalActivity;
    ArrayList<Double> timeSpent;


    public Activity(String name, double timeBudget, boolean externalActivity) {
        /* super(???); */
        this.name  = name;
        this.timeBudget = timeBudget;
        startDate = Calendar.getInstance();
        endDate = Calendar.getInstance();
        weekNumber = startDate.get(Calendar.WEEK_OF_YEAR);
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

    public void requestAssistance (DevEmp assistingDev) {
        if (assistingDev.free) {
            workingDevelopers.add(assistingDev);
        }
    }



}
