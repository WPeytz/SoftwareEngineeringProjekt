package dtu.system;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.io.*;

public class Menu extends TimeManager
{
    int projID;
    String startWeek, endWeek;
    public void println()
    {
        System.out.println();
    }

    public void println(String print)
    {
        System.out.println(print);
    }

    public Menu()
    {
        mainMenu();
    }

    public void mainMenu()
    {
        menuList();
        int userIn = 0;
        while (true)
        {

            if (sc.hasNextLine())
            {
                try
                {
                    userIn = sc.nextInt();
                }
                catch (InputMismatchException IME)
                {
                    println("Please enter only numbers");
                    mainMenu();
                }
            }
            switch (userIn)
            {
                case 1 -> createProjectCase();
                case 2 -> viewProjectsCase(2);
                case 3 -> createReportCase();
                case 4 -> createActivityCase();
                case 5 -> viewFreeEmployeesCase();
                case 6 -> viewReportsCase();
                case 7 -> createNewDeveloperCase();
                case 8 -> viewProjectsCase(0);
                case 9 -> viewProjectsCase(1);
                case 10 -> addDeveloperToProjectCase();
                case 11 -> addDeveloperToActivityCase();
                case 12 -> editActivityCase();
                case 13 -> registerTimeCase();
                case 14 -> requestAssistanceCase();
                case 0 -> System.exit(0);
                default ->
                {
                    println("Undefined input. Try again");
                    mainMenu();
                }
            }
        }
    }

    /*
     * 1. Create Project
     * 2. View Projects
     * 3. Create Report
     * 4. Create Activity
     * 5. View Free Employees
     * 6. View report list
     * 7. Create developer
     * 8. Edit project parameters
     * 9. Add project manager
     * 10. Add developer to project
     * 11. Add developer to activity
     * TODO (test): 12. Edit activity
     * TODO: 13. Register time
     * TODO: 14. Request assistance
     * 0. Close system
     */

    public void menuList ()
    {
        println();
        println("1. Create Project");
        println("2. View Projects");
        println("3. Create Report");
        println("4. Create Activity");
        println("5. View Free Employees");
        println("6. View reports");
        println("7. Create new developer");
        println("8. Edit a project");
        println("9. Add or change manager for a project");
        println("10. Add developer to project");
        println("11. Add developer to activity");
        println("12. Edit activity");
        println("13. Register Time");
        println("14. Request assistance");
        println("0. Close system");
    }

    public void createProjectCase()
    {
        //clearScreen();
        println("Project name must not contain any white spaces.");
        System.out.print("Please enter the desired project name: ");
        sc = new Scanner(System.in);
        String projectName = sc.next();
        println();
        System.out.print("External Project? y/n: ");
        sc = new Scanner(System.in);
        String extProjectInput = sc.next();
        println();
        String extProject = extProjectInput.substring(0,1);
        if (!(extProject.equals("y") || extProject.equals("n")))
        {
            println("Wrong input");
            createProjectCase();
        }
        println();
        boolean isExtProject = extProject.contains("y");

        System.out.print("Start Date (yyyy-ww): ");
        startWeek = sc.next();
        try
        {
            checkDateFormat(startWeek);
        }
        catch (OperationNotAllowedException ONAE)
        {
            println(ONAE.getMessage() + " please try again");
            createProjectCase();
        }
        println();
        System.out.print("End Date (yyyy-ww): ");
        endWeek = sc.next();
        try
        {
            checkDateFormat(endWeek);
        }
        catch (OperationNotAllowedException ONAE)
        {
            println(ONAE.getMessage() + " please try again");
            createProjectCase();
        }
        println();
        try
        {
            createProject(projectName, isExtProject, startWeek, endWeek);
        }
        catch (OperationNotAllowedException e)
        {
            println(e.getMessage());
            println("Please try again");
            createProjectCase();
        }
        println("Project \"" + projectName + "\" created.");

        try
        {
            Thread.sleep(500);
        }
        catch (InterruptedException IE)
        {
            IE.printStackTrace();
        }
        clearScreen();
        mainMenu();
    }

    public void viewProjectsCase(int j)
    {
        clearScreen();
        int i = viewProjects();
        println("Press enter to continue");
        try
        {
            System.in.read();
        }
        catch (IOException IOE)
        {
            IOE.printStackTrace();
        }
        clearScreen();
        if(i == 2)
        {
            if (j == 1)
            {
                changeProjectManagerCase();
            }
            else if (j==2)
            {
                mainMenu();
            }
            else
            {
                editProjectCase();
            }
        }
        else
        {
            mainMenu();
        }
    }

    public void createReportCase()
    {
        sc = new Scanner(System.in);
        System.out.print("Enter Project ID: ");
        try
        {
            projID = sc.nextInt();
        }
        catch (InputMismatchException IME)
        {
            println("Please only enter numbers. Try again.");
            createReportCase();
        }
        println();

        System.out.print("Enter Initials: ");
        String initials = sc.next();
        println();

        try
        {
            createReport(getProject(projID),getDeveloper(initials));
        }
        catch (OperationNotAllowedException ONAE)
        {
            println(ONAE.getMessage());
        }
        catch (IOException IOE)
        {
            throw new Error(IOE);
        }

        try
        {
            Thread.sleep(500);
        }
        catch (InterruptedException IE)
        {
            IE.printStackTrace();
        }
        clearScreen();
        mainMenu();
    }

    public void createActivityCase()
    {
        System.out.print("Project ID (\"0\" if external activity): ");
        sc = new Scanner(System.in);
        int projectID = 0;
        try
        {
            projectID = sc.nextInt();
        }
        catch (InputMismatchException IME)
        {
            println("Please only enter numbers. Try again");
            createActivityCase();
        }
        println();

        System.out.print("Please enter your initials: ");
        sc = new Scanner(System.in);
        String initials = sc.next();
        println();
        try
        {
            if (projectID != 0 && !getProject(projectID).isProjectManager(initials))
            {
                println("Credentials do not match.");
                mainMenu();
            }
        }
        catch (OperationNotAllowedException ONAE)
        {
            println(ONAE.getMessage());
            mainMenu();
        }
        System.out.print("Activity Name: ");
        sc = new Scanner(System.in);
        String activityName = sc.next();
        println();

        System.out.print("Time Budget: ");
        sc = new Scanner(System.in);
        double timeBudget = sc.nextDouble();
        println();

        System.out.print("Start Date (yyyy-ww): ");
        sc = new Scanner(System.in);
        String startDate = sc.next();
        println();
        System.out.print("End Date (yyyy-ww): ");
        sc = new Scanner(System.in);
        String endDate = sc.next();
        println();

        if (projectID == 0)
        {
            extActList.add(new Activity(activityName, timeBudget, projectID, startDate, endDate));
        }
        else
        {
            try
            {
                Project p = getProject(projectID);
                if (!toLocalDate(startDate).isBefore(p.startWeek) && !toLocalDate(endDate).isAfter(p.endWeek))
                {
                    p.activities.add(new Activity(activityName, timeBudget, projectID, startDate, endDate));
                }
                else if (toLocalDate(startDate).isBefore(p.startWeek))
                {
                    throw new OperationNotAllowedException("The start week of activity cannot be before the start week of the project");
                }
                else if (toLocalDate(endDate).isAfter(p.endWeek))
                {
                    throw new OperationNotAllowedException("The end week of activity cannot be after the end week of the project");
                }
            }
            catch (OperationNotAllowedException ONAE)
            {
                println(ONAE.getMessage());
                createActivityCase();
            }
        }
        println("Activity \"" + activityName + "\" has been created");

        try
        {
            Thread.sleep(500);
        }
        catch (InterruptedException IE)
        {
            throw new RuntimeException(IE);
        }
        clearScreen();
        mainMenu();
    }

    public void viewFreeEmployeesCase()
    {
        clearScreen();
        System.out.print("View free employees in the period (yyyy-ww): ");
        startWeek = sc.next();
        println();
        System.out.print("to (yyyy-ww): ");
        endWeek = sc.next();
        println();
        try
        {
            viewFreeEmployees(startWeek,endWeek);
        }
        catch (OperationNotAllowedException ONAE)
        {
            println(ONAE.getMessage());
        }
        mainMenu();
    }

    public void viewReportsCase()
    {
        File RepDirs = new File(getRepDir());
        String[] ReportList = RepDirs.list();
        println("Please choose a file to view:");
        String fileName;

        int fileNumber = 0;
        for (String file : ReportList)
        {
            fileNumber++;
            println(fileNumber+": "+file);
        }

        Scanner fileSelect = new Scanner(System.in);
        int selectedFile = 0;
        while (true)
        {
            if (fileSelect.hasNextLine())
            {
                selectedFile = fileSelect.nextInt() - 1;
            }
            try
            {
                fileName =  ReportList[selectedFile];
            }
            catch(ArrayIndexOutOfBoundsException AIOOBE)
            {
                throw new RuntimeException("No file with the number: " + selectedFile);
            }

            //var path = Paths.get(getRepDir(), fileName);

            fileName = fileName.substring(0,6);

            int id = Integer.parseInt(fileName);

            try
            {
                println(getReportFromProjectID(id));
                println("Press enter to return to the main menu");
                try
                {
                    System.in.read();
                    mainMenu();
                }
                catch (IOException IOE)
                {
                    IOE.printStackTrace();
                }
            }
            catch (OperationNotAllowedException | FileNotFoundException EX)
            {
                println(EX.getMessage());
                mainMenu();
            }
        }
    }

    public void createNewDeveloperCase()
    {
        sc = new Scanner(System.in);
        println("Please enter the initials of the new developer. To return to the main menu, press \"x\" and then the enter key");
        String initials = sc.next();
        if (initials.equalsIgnoreCase("x"))
        {
            mainMenu();
        }
        println();
        try
        {
            developerList.add(new Developer(initials));
            println("Developer \"" + initials + "\" created");
            mainMenu();
        }
        catch (OperationNotAllowedException ONAE)
        {
            println(ONAE.getMessage());
            createNewDeveloperCase();
        }
    }

    public void editProjectCase()
    {
        println("Please enter the ID of the project that should be edited. To return to the main menu, press \"x\" and then the enter key");
        Project p = projectEditDuplicateCode();
        editProjectMenu(p,projID);
    }

    public void editProjectMenu(Project p, int projID)
    {
        String projType;
        if (p.customerProject)
        {
            projType = "Customer project";
        }
        else
        {
            projType = "Internal project";
        }

        println("Project " + projID + " has the following editable parameters:");
        println();
        println("1. Project name: " + p.name);
        println("2. End week: " + p.endWeek);
        println("3. Customer/Internal project: " + projType);
        println("4. Return to main menu");
        println();
        println("Please choose a parameter that should be edited by entering the corresponding menu index");
        sc = new Scanner(System.in);
        int inp = 0;
        try
        {
            inp = sc.nextInt();
        }
        catch (InputMismatchException IME)
        {
            println("Please enter only numbers. Try again.");
            editProjectMenu(p, projID);
        }
        switch (inp)
        {
            case 1 -> editProjectName(p);
            case 2 -> editProjectEndWeek(p);
            case 3 -> editProjectType(p);
            case 4 -> mainMenu();
            default ->
            {
                println("Invalid input: please try again");
                println();
                editProjectMenu(p,projID);
            }
        }
    }

    public void editProjectName(Project p)
    {
        println();
        println("Please enter the new name of the project. \r\nTo cancel this action, press \"x\" and then the enter key");
        sc = new Scanner(System.in);
        String input = sc.nextLine();
        if (input.equalsIgnoreCase("x"))
        {
            editProjectCase();
        }
        println(p.setName(input));
        mainMenu();
    }

    public void editProjectEndWeek(Project p)
    {
        println("Please enter the new end week of the project (format it yyyy-ww). \r\nTo cancel this action, press \"x\" and then the enter key");
        sc = new Scanner(System.in);
        String input = sc.next();
        if (input.equalsIgnoreCase("x"))
        {
            editProjectCase();
        }
        try
        {
            p.changeProjectEndWeek(input);
            println("Project end week changed to: " + input);
            mainMenu();
        }
        catch(DateTimeParseException DTPE)
        {
            println("Wrong format of week. Try again.");
            editProjectEndWeek(p);
        }
    }

    public void editProjectType(Project p)
    {
        println("Please enter the new type of the project (Customer/Internal). \r\nTo cancel this action, press \"x\" and then the enter key");
        println("Current project type is: " + projType(p));
        sc = new Scanner(System.in);
        String input = sc.next();
        if (input.equalsIgnoreCase("x"))
        {
            editProjectCase();
        }
        else if (input.equalsIgnoreCase("Customer"))
        {
            p.customerProject = true;
            println("New project type is: " + projType(p));
            mainMenu();
        }
        else if (input.equalsIgnoreCase("Internal"))
        {
            p.customerProject = false;
            println("New project type is: " + projType(p));
            mainMenu();
        }
        else
        {
            println("Wrong input. Please try again.");
            editProjectCase();
        }
    }

    public void changeProjectManagerCase()
    {
        println("Please enter the ID of the project you would like to add a project manager to. To return to the main menu, press \"x\" and then the enter key");
        Project p = projectEditDuplicateCode();
        println("Please enter the initials of the developer you would like to make the project manager of project " + p.name);
        sc = new Scanner(System.in);
        String projMgr = sc.nextLine();
        try
        {
            p.setProjectManager(getDeveloper(projMgr.toLowerCase()));
            println("Developer " + p.projectManager.initials + " is now the project manager for project " + p.name);
            mainMenu();
        }
        catch (OperationNotAllowedException ONAE)
        {
            println(ONAE.getMessage());
            println("Returning to main menu...");
            mainMenu();
        }
    }

    public Project projectEditDuplicateCode()
    {
        int projID;
        String input = sc.next();
        if (input.equalsIgnoreCase("x"))
        {
            mainMenu();
        }
        Project p = null;
        try
        {
            projID = Integer.parseInt(input);
            if(!projectExists(projID))
            {
                throw new OperationNotAllowedException("Project with ID \"" + projID + "\" does not exist. Please try again");
            }
            p = getProject(projID);
        }
        catch(NumberFormatException NFE)
        {
            println("Invalid input, please try again.");
            viewProjectsCase(0);
        }
        catch(OperationNotAllowedException ONAE)
        {
            println(ONAE.getMessage());
            viewProjects();
            editProjectCase();
        }

        int i = viewEmployees();
        if (i==2)
        {
            mainMenu();
        }
        return p;
    }

    public void addDeveloperToProjectCase()
    {
        println("Please enter the ID of the project you would like to assign a developer to. To return to the main menu, press \"x\" and then the enter key");
        Project p = projectEditDuplicateCode();
        println("Please enter the initials of the developer you would like to assign to the project " + p.name);
        sc = new Scanner(System.in);
        String developer = sc.nextLine();
        try
        {
            p.setProjectManager(getDeveloper(developer.toLowerCase()));
            println("Developer " + p.projectManager.initials + " is now assigned to project " + p.name);
            mainMenu();
        }
        catch (OperationNotAllowedException ONAE)
        {
            println(ONAE.getMessage());
            println("Returning to main menu...");
            mainMenu();
        }
    }

    public Activity activityEditDuplicateCode()
    {
        println("Please enter the ID of the project, where the activity you would like modify is located. To return to the main menu, press \"x\" and then the enter key");
        Project p = projectEditDuplicateCode();
        viewActivities(p);
        int actID;
        sc = new Scanner(System.in);
        println("Please enter the number of the desired activity (as shown in the list above), where the activity you would like to modify. To return to the main menu, press \"x\" and then the enter key");
        String inputAct = sc.next();
        if (inputAct.equalsIgnoreCase("x"))
        {
            mainMenu();
        }
        Activity a = null;
        try
        {
            actID = Integer.parseInt(inputAct);
            if(!p.activities.contains(p.activities.get(actID-1)))
            {
                throw new OperationNotAllowedException("Given activity does not exist");
            }
            a = p.activities.get(actID-1);
        }
        catch(NumberFormatException NFE)
        {
            println("Invalid input, please try again.");
            viewProjectsCase(0);
        }
        catch(OperationNotAllowedException ONAE)
        {
            println(ONAE.getMessage());
            viewProjects();
            editProjectCase();
        }
        return a;
    }

    public void addDeveloperToActivityCase()
    {
        Activity a = activityEditDuplicateCode();

        int i = viewEmployees();
        if (i==2)
        {
            mainMenu();
        }
        println("Please enter the initials of the developer you would like to assign to the activity " + a.name);
        sc = new Scanner(System.in);
        String developer = sc.next();
        try
        {
            a.addWorkingDev(getDeveloper(developer));
            println("Developer " + getDeveloper(developer).initials + " is now assigned to activity " + a.name);
            mainMenu();
        }
        catch (OperationNotAllowedException ONAE)
        {
            println(ONAE.getMessage());
            println("Returning to main menu...");
            mainMenu();
        }
    }
    public void editActivityCase()
    {
        Activity a = activityEditDuplicateCode();

        println("Please choose what parameters of the activity you would like to change. To return to the main menu, press \"0\" and then the enter key");
        println("1. Name. Current name is: " + "\"" + a.name + "\"");
        println("2. Timebudget. Current time budget is: " + a.timeBudget + " hours.");
        println("3.Start week. Current start week is: " + a.startWeek);
        println("4.End week. Current end week is: " + a.endWeek);
        sc = new Scanner(System.in);
        int in = 0;
        try
        {
            in = sc.nextInt();
        }
        catch (InputMismatchException IME)
        {
            println("Please enter only numbers. Try again");
            mainMenu();
        }
        switch (in)
        {
            case 0 -> mainMenu();
            case 1 -> editActivityName(a);
            case 2 -> editActivityTimeBudget(a);
            case 3 -> editActivityWeek(a, true);
            case 4 -> editActivityWeek(a, false);
            default ->
            {
                println("Invalid input. Returning to main menu...");
                mainMenu();
            }
        }
    }
    public void editActivityName(Activity a)
    {
        println("Please enter the new name of the activity.\r\nTo cancel this action, press \"x\" and then the enter key");
        sc = new Scanner(System.in);
        String input = sc.next();
        if (input.equalsIgnoreCase("x"))
        {
            editActivityCase();
        }
        a.name = input;
        println("New name of the activity is: " + a.name);
        mainMenu();
    }
    public void editActivityTimeBudget(Activity a)
    {
        println("Please enter the new time budget of the activity.\r\nTo cancel this action, press \"x\" and then the enter key");
        sc = new Scanner(System.in);
        String input = sc.next();
        if (input.equalsIgnoreCase("x"))
        {
            editActivityCase();
        }
        try
        {
            a.timeBudget = Double.parseDouble(input);
        }
        catch (NumberFormatException NFE)
        {
            println("Wrong input format. The input should only be a number. Please try again...");
            editActivityCase();
        }
        println("New time budget of the activity is: " + a.timeBudget + "hours");
        mainMenu();
    }
    public void editActivityWeek(Activity a, boolean weekTypeParam)
    {
        String weekType;
        if(weekTypeParam)
        {
            weekType = "start week";
        }
        else
        {
            weekType = "end week";
        }
        println("Please enter the new " + weekType + " of the activity.\r\nTo cancel this action, press \"x\" and then the enter key");
        println("Keep in mind that the format of the week is (yyyy-ww).");
        sc = new Scanner(System.in);
        String input = sc.next();
        if (input.equalsIgnoreCase("x"))
        {
            editActivityCase();
        }
        try
        {
            if(weekTypeParam)
            {
                a.startWeek = toLocalDate(input);
            }
            else
            {
                a.endWeek = toLocalDate(input);
            }
        }
        catch (OperationNotAllowedException ONAE)
        {
            println(ONAE.getMessage() + "Please try again.");
            editActivityCase();
        }
        if(weekTypeParam)
        {
            println("New " + weekType + " of the activity is: " + a.startWeek);
        }
        else
        {
            println("New " + weekType + " of the activity is: " + a.endWeek);
        }
        mainMenu();
    }
    public void registerTimeCase()
    {
        println("WIP. Returning to main menu...");
        mainMenu();
    }

    public void requestAssistanceCase()
    {
        println("WIP. Returning to main menu...");
        mainMenu();
    }

    public String projType(Project p)
    {
        if (p.customerProject)
        {
            return "Customer Project";
        }
        return "Internal Project";
    }

    public int viewProjects()
    {
        if(projectList.isEmpty())
        {
            println("No projects are created yet. Returning to the main menu...");
            return 1;
        }
        else
        {
            for (Project p : projectList)
            {
                String projMgr;
                if (p.projectManager == null)
                {
                    projMgr = "N/A";
                }
                else
                {
                    projMgr = p.projectManager.initials;
                }
                println(
                        "Project ID:" + p.projectID + " | Name: " + p.name + " | Manager: " + projMgr +
                                " | Time spent: " + p.totalTimeSpent() + " hours | " + "Start week: " + p.startWeek +
                                    " | End week: " + p.endWeek
                );
            }
        }
        return 2;
    }

    public int viewActivities(Project p)
    {
        if(p.activities.isEmpty())
        {
            println("No activities are created yet. Returning to the main menu...");
            mainMenu();
        }
        else
        {
            int i = 1;
            println("Activies for project " + p.name);
            for (Activity a : p.activities)
            {
                println(i + ": " +a.name);
            }
        }
        return 2;
    }

    public int viewEmployees()
    {
        if (developerList.isEmpty())
        {
            println("There are currently no developers created. Returning to the main menu...");
            return 2;
        }
        else
        {
            println("Existing developers:");
            for (Developer d : developerList)
            {
                println(d.initials);
            }
        }
        return 1;
    }

    public static void clearScreen()
    {
        try
        {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        }
        catch (InterruptedException | IOException EX)
        {
            EX.printStackTrace();
        }
    }
    public void viewFreeEmployees(String startWeek, String endWeek) throws OperationNotAllowedException
    {
        format = DateTimeFormatter.ofPattern("YYYY-ww-e");

        for (Developer dev : developerList)
        {
            if (dev.isFree(LocalDate.parse(startWeek + "-1",format), LocalDate.parse(endWeek + "-7",format)))
            {
                println(dev.initials);
            }
        }
        println("Press enter to return to the main menu");
        try
        {
            System.in.read();
            mainMenu();
        }
        catch (IOException IOE)
        {
            IOE.printStackTrace();
        }
    }
}