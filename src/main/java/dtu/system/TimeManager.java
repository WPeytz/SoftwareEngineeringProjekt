package dtu.system;

import java.nio.file.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.io.*;

public class TimeManager
{
    public ArrayList<Activity> extActList;
    public static HashSet<Project> projectList;
    public ArrayList<Developer> developerList;
    double estTimeLeft;
    DateTimeFormatter format;
    Scanner sc = new Scanner(System.in);
    public static ArrayList<int[]> years;

    public TimeManager()
    {
        extActList = new ArrayList<>();
        projectList = new HashSet<>();
        developerList = new ArrayList<>();
        years = new ArrayList<>();
        years.add(new int[2]);
    }

    public Activity getExternalActivity(String actName) throws OperationNotAllowedException
    {
        Activity act = null;
        boolean actFound = false;
        for (Activity a : extActList)
        {
            if (a.name.equals(actName) || !actFound)
            {
                act = a;
                actFound = true;
            }
        }

        if (act == null)
        {
            throw new OperationNotAllowedException("External activity does not exist");
        }
        return act;
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

    public String getReportFromProjectID(int projID) throws OperationNotAllowedException, FileNotFoundException
    {
        File RepDirs = new File(getRepDir());
        String[] ReportList = RepDirs.list();
        String fileName = "";

        if (ReportList != null)
        {
            for (String file : ReportList)
            {
                if (file.equals(getProject(projID).getProjectID()+"_report.txt"))
                {
                    fileName = file;
                    break;
                }
            }
        }

        return getFileContents(Paths.get(getRepDir(), fileName));
    }

    public String getFileContents(Path path) throws FileNotFoundException
    {
        StringBuilder sb = new StringBuilder();
        File file = new File(String.valueOf(path));
        Scanner sc = new Scanner(file);
        while (sc.hasNextLine())
        {
            sb.append(sc.nextLine()+"\r\n");
        }

        return sb.toString();
    }

    public String readReportFromName(String projName) throws FileNotFoundException, OperationNotAllowedException
    {
        return (getReportFromProjectID(getProject(projName).getProjectID()));
    }

    public String getRepDir()
    {
        String curUser = System.getProperty("user.name");
        String projRepDir = ("C:\\Users\\" + curUser + "\\Documents\\ProjectReports\\");
        File projDir = new File(projRepDir);
        projDir.mkdir();
        return projRepDir;
    }

    public String reportText(Project project)
    {
        estTimeLeft = project.timeBudget() - project.totalTimeSpent();
        String CustomerProject;
        if (project.customerProject)
        {
            CustomerProject = "Customer Project";
        }
        else
        {
            CustomerProject = "Internal Project";
        }

        return ("Project Report for project: " + project.name + "\r\n" + "Project type: " + CustomerProject +
                "\r\n" + "The time budget for project " + project.name + " is " + project.timeBudget() + "." + "\r\n" +
                "There has currently been worked " + project.totalTimeSpent() + " hours on the project." + "\r\n" +
                "The estimated number of hours left on the project is " + estTimeLeft + " hours.");
    }

    public void createReport(Project project, Developer dev) throws OperationNotAllowedException, IOException
    {
        assert(true); // Precondition
        project.isProjectManager(dev.initials); //1
        String report = reportText(project); //2
        String projRepDir = getRepDir(); //3
        File projReport = new File(projRepDir + project.projectID + "_report.txt"); //4
        projReport.createNewFile(); //5
        FileWriter FlWrtr = new FileWriter(projReport); //6
        FlWrtr.write(report); //7
        FlWrtr.close(); //8
        System.out.println("Project report has been saved in: " + getRepDir());
        assert(getReportFromProjectID(project.getProjectID()) != null); // Postcondition
    }


    public void createProject(String name, boolean customerProject, String startWeek, String endWeek) throws OperationNotAllowedException
    {
        if (!projectExists(name)) //1
        {
            assert(true); //Precondition
            projectList.add(new Project(name, customerProject, startWeek, endWeek)); //2
            assert(projectExists(name) || !toLocalDate(startWeek).isBefore(toLocalDate(endWeek))); // Postcondition
        }
        else
        {
            throw new OperationNotAllowedException("Project could not be created, as the project name is already in use."); //3
        }

    }

    public boolean projectExists(String name)
    {
        boolean projExists = false;
        for (Project p : projectList)
        {
            if (p.name.equals(name))
            {
                projExists = true;
            }
        }
        return projExists;
    }

    public boolean projectExists(int projID)
    {
        boolean projExists = false;
        for (Project p : projectList)
        {
            if (p.projectID == projID)
            {
                projExists = true;
            }
        }
        return projExists;
    }

    public Project getProject(int projectID) throws OperationNotAllowedException
    {
        Project proj = null;
        for (Project p : projectList)
        {
            if (p.projectID == projectID)
            {
                proj = p;
            }
        }
        if (proj == null)
        {
            throw new OperationNotAllowedException("Project does not exist");
        }
        return proj;
    }

    public Project getProject(String projectName) throws OperationNotAllowedException
    {
        Project proj = null;
        for (Project p : projectList)
        {
            if (p.name.equals(projectName))
            {
                proj = p;
            }
        }
        if (proj == null)
        {
            throw new OperationNotAllowedException("Project does NOT exist");
        }
        return proj;
    }

    public void changeEndWeek(String endWeek, String activityName, int projectID) throws OperationNotAllowedException
    {
        format = DateTimeFormatter.ofPattern("YYYY-ww-e");
        Activity activity = null;

        if (projectID == 0)
        {
            if(extActList.contains(getExternalActivity(activityName)))
            {
                activity = getExternalActivity(activityName);
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

        activity.endWeek = LocalDate.parse(endWeek + "-7", format);

        if (activity.endWeek.isAfter(getProject(activity.projectID).endWeek))
        {
            getProject(activity.projectID).endWeek = activity.endWeek;
        }
    }

    public boolean checkDateFormat(String date) throws OperationNotAllowedException
    {
        try
        {
            format = DateTimeFormatter.ofPattern("YYYY-ww-e");
            LocalDate.parse(date + "-7", format);
        }
        catch(DateTimeParseException DTPE)
        {
            throw new OperationNotAllowedException("Wrong date format");
        }
        return true;
    }

    public LocalDate toLocalDate(String date) throws OperationNotAllowedException
    {
        LocalDate locDate = null;
        if(checkDateFormat(date))
        {
            format = DateTimeFormatter.ofPattern("YYYY-ww-e");
            locDate = LocalDate.parse(date + "-7", format);
        }
        return locDate;
    }
}