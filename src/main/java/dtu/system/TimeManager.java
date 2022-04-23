package dtu.system;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.io.*;

public class TimeManager
{
    public ArrayList<Activity> extActList;
    HashSet<Project> projectList;
    public ArrayList<Developer> developerList;
    double estTimeLeft;
    DateTimeFormatter format;
    Scanner sc = new Scanner(System.in);

    public TimeManager()
    {
        extActList = new ArrayList<>();
        projectList = new HashSet<>();
        developerList = new ArrayList<>();
    }

    public void loadCLI() throws Exception
    {
        int userIn = 0;
        Menu mn = new Menu();

        while (true)
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
            System.out.println(p.projectID + " " + p.name + " " + p.projectManager.initials + " " + p.totalTimeSpent() + "h");
        }
    }

    public void viewFreeEmployees(String startWeek, String endWeek) throws OperationNotAllowedException
    {
        format = DateTimeFormatter.ofPattern("YYYY-ww-e");

        for (Developer dev : developerList)
        {
            if (dev.isFree(LocalDate.parse(startWeek + "-1"), LocalDate.parse(endWeek + "-7")))
            {
                System.out.println(dev.initials);
            }
        }
    }

    public Activity getExternalActivity(String actName) throws OperationNotAllowedException
    {
        for (Activity a : extActList)
        {
            if (a.name.equals(actName)) return a;
        }
        throw new OperationNotAllowedException("External activity does not exist");
    }

    public Developer getDeveloper(String initials) throws OperationNotAllowedException
    {
        for (Developer dev : developerList)
        {
            if (initials.equals(dev.initials))
            {
                return dev;
            }
        }
        throw new OperationNotAllowedException("No matching developer with initials " + initials + " found.");
    }

    public String getRepDir()
    {
        String curUser = System.getProperty("user.name");
        String projRepDir = ("C:\\Users\\" + curUser + "\\Documents\\ProjectReports\\");
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
        }

        String report = ("Project Report for project: " + project.name + "\r\n" + "Project type: " + CustomerProject + "\r\n" + "The time budget for project" + project.name + "is" + project.timeBudget() + "." + "\r\n" +
                "There has currently been worked " + project.totalTimeSpent() + " hours on the project." + "\r\n" +
                "The estimated number of hours left on the project is" + estTimeLeft + " hours.");

        try
        {
            String projRepDir = getRepDir();
            File projReport = new File(projRepDir + project.projectID + "_report.txt");
            projReport.createNewFile();
            FileWriter FlWrtr = new FileWriter(projReport);
            FlWrtr.write(report);
            FlWrtr.close();
            System.out.println("Project Report has been saved in: " + getRepDir());
        }
        catch (IOException IOE)
        {
            IOE.printStackTrace();
        }
    }


    public void createProject(String name, boolean customerProject, String startWeek, String endWeek) throws OperationNotAllowedException
    {
        if (projectExists(name))
        {
            throw new OperationNotAllowedException("Project could not be created, as the project name is already in use.");
        }
        else
        {
            projectList.add(new Project(name, customerProject, startWeek, endWeek));
        }
    }

    public void createActivity() throws Exception {

        System.out.print("Project ID (\"0\" if external activity: ");
        int projectID = sc.nextInt();
        System.out.println();

        System.out.print("Initials: ");
        String initials = sc.nextLine();
        System.out.println();
        if (projectID != 0 && !getProject(projectID).isProjectManager(initials))
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

    public boolean projectExists(String name)
    {
        for (Project p : projectList)
        {
            if (p.name.equals(name))
            {
                return true;
            }
        }
        return false;
    }

    public Project getProject(int projectID) throws OperationNotAllowedException
    {
        for (Project p : projectList)
        {
            if (p.projectID == projectID)
            {
                return p;
            }
        }
        throw new OperationNotAllowedException("Project does not exist");
    }

    public Project getProject(String projectName) throws OperationNotAllowedException
    {
        for (Project p : projectList)
        {
            if (p.name.equals(projectName))
            {
                return p;
            }
        }
        throw new OperationNotAllowedException("Project does not exist");
    }

    public void changeEndWeek(String endWeek, String activityName, int projectID) throws OperationNotAllowedException
    {
        format = DateTimeFormatter.ofPattern("YYYY-ww-e");
        Activity activity = null;
        if (projectID == 0)
        {
            for (Activity a : extActList)
            {
                if (a.name.equals(activityName))
                {
                    activity = a;
                }
            }
        }
        else
        {
            for (Activity a : getProject(projectID).activities)
            {
                if (a.name.equals(activityName))
                {
                    activity = a;
                }
            }
        }
        if (activity == null)
        {
            throw new OperationNotAllowedException("Activity does not exist");
        }
        activity.endWeek = LocalDate.parse(endWeek + "-7", format);
        if (activity.endWeek.isAfter(getProject(activity.projectID).endWeek))
        {
            getProject(activity.projectID).endWeek = activity.endWeek;
        }
    }
}