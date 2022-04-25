package dtu.system;


import java.nio.file.*;
import java.time.LocalDate;
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

    public Path getFilePath(String projName) throws FileNotFoundException
    {
        File RepDirs = new File(getRepDir());
        String[] ReportList = RepDirs.list();
        String fileName = null;

        for (String file : ReportList)
        {
            try
            {
                if (file.equals(getProject(projName).getProjectID()+"_report.txt"))
                {
                    fileName = file;
                    break;
                }
            }
            catch (OperationNotAllowedException ONAE)
            {
                throw new RuntimeException(ONAE);
            }
        }

        if (fileName == null)
        {
            throw new FileNotFoundException("File not found");
        }

        return Paths.get(getRepDir(), fileName);
    }

    public String readReport(String projName) throws FileNotFoundException
    {
        String text = "";
        var path = getFilePath(projName);
        try
        {
            File file = new File(String.valueOf(path));
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine())
            {
                text += sc.nextLine() + "\n\r";
            }
        }
        catch (FileNotFoundException FNFE)
        {
            FNFE.printStackTrace();
        }
        return text;
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

    public void createReport(Project project, Developer dev) throws OperationNotAllowedException
    {

        project.isProjectManager(dev.initials);

        String report = reportText(project);

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