package dtu.system;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.io.*;

public class TimeManager {
    ArrayList<Activity> extActList;
    HashSet<Project> projectList;
    ArrayList<DevEmp> devEmpList;
    double estTimeLeft;
    DateTimeFormatter format;

    public TimeManager () {

        extActList = new ArrayList<>();
        projectList = new HashSet<>();
        devEmpList = new ArrayList<>();

    }


    public void createReport(Project project)
    {
        estTimeLeft = project.timeBudget() - project.totalTimeSpent();

        String report = ("The time budget for project" + project.name +  "is" + project.timeBudget() +"."+"\r\n"+
        "There has currently been worked " + project.totalTimeSpent() + " hours on the project."+"\r\n"+
        "The estimated number of hours left on the project is" + estTimeLeft + " hours.");

        try
        {
            String curUser = System.getProperty("user.name");
            String projRepDir = ("C:\\Users\\"+curUser+"\\Documents\\ProjectReports\\");
            File projDir = new File(projRepDir);
            projDir.mkdir();
            File projReport = new File(projRepDir+project.projectID+"_report.txt");
            projReport.createNewFile();
            FileWriter FlWrtr = new FileWriter(projReport);
            FlWrtr.write(report);
            FlWrtr.close();
        }
        catch (IOException IOE)
        {
            IOE.printStackTrace();
        }
    }


    public void createProject(String name, boolean customerProject, LocalDate startWeek, LocalDate endWeek)
    {
        projectList.add(new Project(name, customerProject,startWeek, endWeek));
    }

    public void createActivity(String name, double timeBudget, int projectID, String startWeek, String endWeek) throws Exception
    {
        if (projectID == 0)
        {
            extActList.add(new Activity(name, timeBudget, projectID, startWeek, endWeek));
        } else {
            getProject(projectID).activities.add(new Activity(name, timeBudget, projectID, startWeek, endWeek));
        }
    }

    public Project getProject(int projectID) throws Exception {
        for (Project p : projectList) {
            if (p.projectID == projectID) {
                return p;
            }
        }
        System.out.println("Project not found");
        return null;
    }

    public void changeEndWeek(String endWeek, String activityName, int projectID) throws Exception{
        format = DateTimeFormatter.ofPattern("yyyy-ww-EEE");
        Activity activity = null;
        for (Activity a : getProject(projectID).activities) {
            if (a.name.equals(activityName)) {
                activity = a;
            }
        }
        if (activity == null) {
            throw new Exception("Activity does not exist");
        }
        activity.endWeek = LocalDateTime.parse(endWeek+"-Sun",format);
        if (activity.endWeek.isAfter(getProject(activity.projectID).endWeek)) {
            getProject(activity.projectID).endWeek = activity.endWeek;
        }
    }
}
