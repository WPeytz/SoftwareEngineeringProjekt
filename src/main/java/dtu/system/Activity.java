package dtu.system;

import java.text.ParseException;
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


    public Activity(String name, double timeBudget, boolean externalActivity, String startWeek, String endWeek) throws ParseException {
        this.name  = name;
        this.timeBudget = timeBudget;
        this.externalActivity = externalActivity;
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH.mm");
        this.startWeek = LocalDateTime.parse(startWeek, format);
        this.endWeek = LocalDateTime.parse(endWeek, format);
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
        workingDevelopers.add(devEmp);
    }

    public String requestAssistance (DevEmp assistingDev, Activity activity){
        if (!activity.workingDevelopers.contains(assistingDev))
        {
            return "I request your assitance " + assistingDev.Initials + " for " + activity.name;
        } else
            return assistingDev.Initials + " is already working on the same activity as you";
    }


     /*
     Nyt projekt fra uge 2-4
     [0] GammelAktivitet 1-3
     [1] GammelAktivitet 3-5
     [2] GammelAktivitet 7-9
     [3] GammelAktivitet 100-103


     int newStartWeek = 202150
     int newEndWeek = 202202

     yyyyww = 202215
     yyyy = yyyyww/100
     ww = yyyyww % yyyy

     int WeeksAfter1970 = (yyyy-1970)*52 + ww
     yyyy = WeeksAfter1970 / 52
     ww = WeeksAfter1970 % 52
     int newyyyyww = Integer.parseInt(String.valueOf(yyyy)+String.valueOf(ww));

     newStartWeek = 100
     newEndWeek = 105

     System.out.println(LocalDate.parse("1999-01-01").get(IsoFields.WEEK_OF_WEEK_BASED_YEAR ));



    for (int i=newStartWeek; i <= newEndWeek; i++)
    {
        int activityCount = 0;
        for (int j=0; j < activities.length; j++)
        {
            if (activityCount >= 20)
            {
               return isFree = false;
            }
            else if ((i => activity[j].startWeek) && (i <= activity[j].EndWeek))
            {
                activityCount++;
            }
        }
    }
    return isFree = true;



     */

}
