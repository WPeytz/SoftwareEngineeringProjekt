package dtu.system;


import java.nio.file.Paths;
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
 * 6. View report list
 */



public class TimeManager {
    ArrayList<Activity> extActList;
    HashSet<Project> projectList;
    public ArrayList<DevEmp> devEmpList;
    double estTimeLeft;
    DateTimeFormatter format;
    Scanner sc = new Scanner(System.in);
    public TimeManager ()
    {
        extActList = new ArrayList<>();
        projectList = new HashSet<>();
        devEmpList = new ArrayList<>();
        System.out.println("Test");
    }

    public void loadCLI() throws Exception
    {
        int userIn = 0;
        Menu mn = new Menu();

        while(true)
        {
            mn.menu();
            if (sc.hasNextLine())
            {
                userIn = sc.nextInt();
            }
            switch (userIn)
            {
                case 1 -> mn.case1();
                case 2 -> mn.case2();
                case 3 -> mn.case3();
                case 4 -> mn.case4();
                case 5 -> mn.case5();
                case 6 -> mn.case6();
                case 666 -> mn.case666();
                case 0 -> System.exit(0);
                default -> {
                    System.out.println("Undefined input. The program will close...");
                    System.exit(69);
                }
            }
        }

    }


    public static void clearScreen() throws IOException, InterruptedException
    {
        //System.out.print("\033[H\033[2J");
        //System.out.flush();
        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();

    }

    public void viewProjects()
    {
        for (Project p : projectList)
        {
            System.out.println(p.projectID + " " + p.name + " " + p.projectManager.initials + " " + p.totalTimeSpent()+"h");
        }
    }

    public void viewFreeEmployees (String startWeek, String endWeek)
    {
        format = DateTimeFormatter.ofPattern("yyyy-ww-EEE");

        for (DevEmp dev : devEmpList)
        {
            if (dev.isFree(LocalDateTime.parse(startWeek+"-Mon"),LocalDateTime.parse(endWeek+"-Sun")))
            {
                System.out.println(dev.initials);
            }
        }
    }

    public DevEmp getDevEmp (String initials) throws Exception
    {
        for (DevEmp dev : devEmpList)
        {
            if (initials.equals(dev.initials))
            {
                return dev;
            }
        }
        throw new Exception("No matching developer with initials "+initials+" found.");
    }

    public String getRepDir()
    {
        String curUser = System.getProperty("user.name");
        String projRepDir = ("C:\\Users\\"+curUser+"\\Documents\\ProjectReports\\");
        File projDir = new File(projRepDir);
        projDir.mkdir();
        return projRepDir;
    }

    //createReport(getProject(projectID))
    public void createReport(Project project)
    {

        estTimeLeft = project.timeBudget() - project.totalTimeSpent();
        String CustomerProject;
        if (project.customerProject)
        {
            CustomerProject = "Cutomer Project";
        }
        else
        {
            CustomerProject = "Internal Project";
;       }

        String report = ("Project Report for project: "+project.name+"\r\n"+"Project type: "+CustomerProject+"\r\n"+"The time budget for project" + project.name +  "is" + project.timeBudget() +"."+"\r\n"+
        "There has currently been worked " + project.totalTimeSpent() + " hours on the project."+"\r\n"+
        "The estimated number of hours left on the project is" + estTimeLeft + " hours.");

        try
        {
            String projRepDir = getRepDir();
            File projReport = new File(projRepDir+project.projectID+"_report.txt");
            projReport.createNewFile();
            FileWriter FlWrtr = new FileWriter(projReport);
            FlWrtr.write(report);
            FlWrtr.close();
            System.out.println("Project Report has been saved in: "+getRepDir());
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

    public void createActivity() throws Exception
    {
        System.out.print("Project ID (\"0\" if external activity: ");
        int projectID = sc.nextInt();
        System.out.println();

        System.out.print("Initials: ");
        String initials = sc.nextLine();
        System.out.println();
        if (projectID != 0 && !initials.equals(getProject(projectID).projectManager.initials))
        {
            System.out.println("Credentials do not match.");
            return;
        }
        System.out.print("Activity Name: ");
        String activityName = sc.nextLine();
        System.out.println();

        System.out.print("Time Budget: ");
        double timeBudget = sc.nextDouble();
        System.out.println();

        System.out.print("Start Date (yyyy-ww): ");
        String startDate = sc.nextLine();
        System.out.println();
        System.out.print("End Date (yyyy-ww): ");
        String endDate = sc.nextLine();
        System.out.println();

        if (projectID == 0)
        {
            extActList.add(new Activity(activityName, timeBudget, projectID, startDate, endDate));
        }
        else
        {
            getProject(projectID).activities.add(new Activity(activityName, timeBudget, projectID, startDate, endDate));
        }
        System.out.println("Activity \"" + activityName + "\" has been created");
    }

    public Project getProject(int projectID) throws Exception
    {
        for (Project p : projectList)
        {
            if (p.projectID == projectID)
            {
                return p;
            }
        }
        throw new Exception("Project does not exist");
    }

    public void changeEndWeek(String endWeek, String activityName, int projectID) throws Exception
    {
        format = DateTimeFormatter.ofPattern("yyyy-ww-EEE");
        Activity activity = null;
        for (Activity a : getProject(projectID).activities)
        {
            if (a.name.equals(activityName))
            {
                activity = a;
            }
        }
        if (activity == null)
        {
            throw new Exception("Activity does not exist");
        }
        activity.endWeek = LocalDateTime.parse(endWeek+"-Sun",format);
        if (activity.endWeek.isAfter(getProject(activity.projectID).endWeek))
        {
            getProject(activity.projectID).endWeek = activity.endWeek;
        }
    }
}
