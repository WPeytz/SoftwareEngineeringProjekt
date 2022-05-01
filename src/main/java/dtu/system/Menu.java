package dtu.system;

import io.cucumber.java.en_old.Ac;

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
    public void println(boolean print)
    {
        System.out.println(print);
    }
    public void println(char print)
    {
        System.out.println(print);
    }
    public void println(char[] print)
    {
        System.out.println(print);
    }
    public void println(double print)
    {
        System.out.println(print);
    }
    public void println(float print)
    {
        System.out.println(print);
    }
    public void println(int print)
    {
        System.out.println(print);
    }
    public void println(long print)
    {
        System.out.println(print);
    }
    public void println(Object print)
    {
        System.out.println(print);
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
                userIn = sc.nextInt();
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
     * TODO: 12. Edit activity
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

    public void case1()
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
            case1();
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
            case1();
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
            case1();
        }
        println();
        try
        {
            createProject(projectName, isExtProject, startWeek, endWeek);
        } catch (OperationNotAllowedException e) {
            println(e.getMessage());
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

    public void case2(int j)
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
                case9();
            }
            else if (j==2)
            {
                mainMenu();
            }
            else
            {
                case8();
            }
        }
        else
        {
            mainMenu();
        }
    }

    public void case3()
    {
        clearScreen();
        System.out.print("Enter Project ID: ");
        projID = sc.nextInt();
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

    public void case4()
    {
        System.out.print("Project ID (\"0\" if external activity): ");
        sc = new Scanner(System.in);
        int projectID = sc.nextInt();
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
                return;
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
                getProject(projectID).activities.add(new Activity(activityName, timeBudget, projectID, startDate, endDate));
            }
            catch (OperationNotAllowedException ONAE)
            {
                println(ONAE.getMessage());
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

    public void case5()
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
            throw new RuntimeException(ONAE);
        }
        mainMenu();
    }

    public void case6()
    {
        File RepDirs = new File(getRepDir());
        String[] ReportList = RepDirs.list();
        println("Please choose a file to view:");
        String fileName = "";

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

    public void case7()
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
            ONAE.getMessage();
            case7();
        }
    }

    public void case8()
    {
        int projID = 0;
        sc = new Scanner(System.in);
        println("Please enter the ID of the project that should be edited. To return to the main menu, press \"x\" and then the enter key");
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
            case2(0);
        }
        catch(OperationNotAllowedException ONAE)
        {
            println(ONAE.getMessage());
            viewProjects();
            case8();
        }

        case8_menu(p,projID);
    }

    public void case8_menu(Project p, int projID)
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
        int inp = sc.nextInt();
        switch (inp)
        {
            case 1 -> case8_1(p);
            case 2 -> case8_2(p);
            case 3 -> case8_3(p);
            case 4 -> mainMenu();
            default ->
            {
                println("Invalid input: please try again");
                println();
                case8_menu(p,projID);
            }
        }
    }

    public void case8_1(Project p)
    {
        println();
        println("Please enter the new name of the project. To cancel this action, press \"x\" and then the enter key");
        sc = new Scanner(System.in);
        String input = sc.nextLine();
        if (input.equalsIgnoreCase("x"))
        {
            case8();
        }
        println(p.setName(input));
        mainMenu();
    }

    public void case8_2(Project p)
    {
        println("Please enter the new end week of the project (format it yyyy-ww). \r\n To cancel this action, press \"x\" and then the enter key");
        sc = new Scanner(System.in);
        String input = sc.next();
        if (input.equalsIgnoreCase("x"))
        {
            case8();
        }
        try
        {
            p.changeProjectEndWeek(input);
        }
        catch(DateTimeParseException DTPE)
        {
            println("Wrong format of week. Try again.");
            case8_2(p);
        }
    }

    public void case8_3(Project p)
    {
        println("Please enter the new type of the project (Customer/Internal). \r\n To cancel this action, press \"x\" and then the enter key");
        println("Current project type is: " + projType(p));
        sc = new Scanner(System.in);
        String input = sc.next();
        if (input.equalsIgnoreCase("x"))
        {
            case8();
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
            case8();
        }
    }

    public void case9()
    {
        int projID = 0;
        sc = new Scanner(System.in);
        println("Please enter the ID of the project you would like to add a project manager to. To return to the main menu, press \"x\" and then the enter key");
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

    public void addDeveloperToProjectCase()
    {
        int projID = 0;
        sc = new Scanner(System.in);
        println("Please enter the ID of the project you would like to assign a developer to. To return to the main menu, press \"x\" and then the enter key");
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

    public void addDeveloperToActivityCase()
    {
        int projID = 0;
        sc = new Scanner(System.in);
        println("Please enter the ID of the project, where the activity you would like to assign a developer to. To return to the main menu, press \"x\" and then the enter key");
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

        viewActivities(p);

        int actID = 0;
        sc = new Scanner(System.in);
        println("Please enter the number of the desired activity (as shown in the list above), where the activity you would like to assign a developer to. To return to the main menu, press \"x\" and then the enter key");
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
        println("WIP. Returning to main menu...");
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
                        "Project ID:" + p.projectID + " | Name: " + p.name + " | Manager: " + projMgr + " | Time spent: " + p.totalTimeSpent() + " h"
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
            println("Developers:");
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