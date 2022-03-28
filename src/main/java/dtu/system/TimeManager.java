package dtu.system;

import java.nio.file.Paths;
import java.util.*;
import java.io.*;

public class TimeManager {
    ArrayList<Activity> extActList;
    HashSet<Project> projectList;
    ArrayList<DevEmp> devEmpList;

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

        //List<String> lines = Arrays.asList(reportLine1, reportLine2, reportLine3);
        //Path file = Paths.get("the-file-name.txt");
        try
        {
            String curUser = System.getProperty("user.name");
            String projRepDir = ("C:\\Users\\"+curUser+"\\Documents\\ProjectReports\\");
            File projDir = new File(projRepDir);
            projDir.mkdir();
            File projReport = new File(projRepDir+"project.name"+"_report.txt");
            projReport.createNewFile();
            FileWriter FlWrtr = new FileWriter(projReport);
            FlWrtr.write(report);
            FlWrtr.close();
        }
        catch (IOException IOE)
        {
            IOE.printStackTrace();
        }
        //Files.write(file, report, StandardCharsets.UTF_8);

    }


    public void createProject(String name, boolean customerProject)
    {
        projectList.add(new Project(name, customerProject));
    }


}
