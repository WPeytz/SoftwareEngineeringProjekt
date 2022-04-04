package dtu.system;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.io.*;

/*
 * 1. Create Project
 * 2. View Projects
 * 3. Create Report
 * 4. Create Activity
 * 5. View Free Employees
 *
 */



public class TimeManager {
    ArrayList<Activity> extActList;
    HashSet<Project> projectList;
    ArrayList<DevEmp> devEmpList;
    double estTimeLeft;
    DateTimeFormatter format;
    Scanner sc;

    public TimeManager () {

        extActList = new ArrayList<>();
        projectList = new HashSet<>();
        devEmpList = new ArrayList<>();
        sc = new Scanner(System.in);
    }

    public void loadCLI() throws Exception {
        menu();
        int userIn = 0;
        while(true) {
            if (sc.hasNextLine()) {
                userIn = sc.nextInt();
            }
            if (userIn == 1) {
                clearScreen();
                System.out.print("Project Name: ");
                String projectName = sc.nextLine();
                System.out.println();

                System.out.print("External Project? y/n: ");
                String extProject = sc.next();
                System.out.println();
                boolean extPrjct;
                if (extProject.contains("n")) {
                    extPrjct = false;
                } else {
                    extPrjct = true;
                }

                System.out.print("Start Date (yyyy-ww): ");
                String startDate = sc.nextLine();
                System.out.println();
                System.out.print("End Date (yyyy-ww): ");
                String endDate = sc.nextLine();
                System.out.println();
                createProject(projectName,extPrjct,startDate,endDate);
                System.out.println("Project " + projectName + " created.");
                Thread.sleep(500);
                clearScreen();
                menu();
            } else if (userIn == 2) {
                clearScreen();
                viewProjects();
                sc.next();
                clearScreen();
                menu();
            } else if (userIn == 3) {
                clearScreen();
                System.out.print("Enter Project ID: ");
                int projID = sc.nextInt();
                createReport(getProject(projID));

            }
        }
    }
    public void menu() {
        System.out.println("1. Create Project");
        System.out.println("2. View Projects");
        System.out.println("3. Create Report");
        System.out.println("4. Create Activity");
        System.out.println("5. View Free Employees");
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public void viewProjects() {
        for (Project p : projectList) {
            System.out.println(p.projectID + " " + p.name + " " + p.projectManager.Initials + " " + p.totalTimeSpent()+"h");
        }
    }

    public void viewFreeEmployees (String startWeek, String endWeek) {
        format = DateTimeFormatter.ofPattern("yyyy-ww-EEE");

        for (DevEmp dev : devEmpList) {
            if (dev.isFree(LocalDateTime.parse(startWeek+"-Mon"),LocalDateTime.parse(endWeek+"-Sun"))) {
                System.out.println(dev.Initials);
            }
        }
    }

    //createReport(getProject(projectID))
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


    public void createProject(String name, boolean customerProject, String startWeek, String endWeek)
    {
        projectList.add(new Project(name, customerProject, startWeek, endWeek));
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
        throw new Exception("Project does not exist");
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
