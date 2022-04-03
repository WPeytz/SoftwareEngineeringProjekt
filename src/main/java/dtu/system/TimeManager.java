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
    String report;

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

    public Project getProject(int projectID) {
        for (Project p : projectList) {
            if (p.projectID == projectID) {
                return p;
            }
        }
        System.out.println("Project not found");
        return null;
    }


}
