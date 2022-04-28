package dtu.system;

import java.nio.file.Path;
import java.nio.file.Paths;

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
                case 1 -> case1();
                case 2 -> case2(1);
                case 3 -> case3();
                case 4 -> case4();
                case 5 -> case5();
                case 6 -> case6();
                case 7 -> case7();
                case 8 -> case2(2);
                case 0 -> System.exit(0);
                default ->
                {
                    println("Undefined input. The program will close...");
                    System.exit(69);
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
     * 9. TODO: Add project manager
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
        println("8. edit a project");
        println("0. Close system");
    }

    public void case1()
    {
        //clearScreen();
        System.out.print("Project Name: ");
        String projectName = sc.next();
        println();
        System.out.print("External Project? y/n: ");
        println();
        String extProjectInput = sc.next();
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

    public void case2(int i)
    {
        clearScreen();
        viewProjects();
        println("Press enter to continue");
        sc.next();
        clearScreen();
        if (i == 1)
        {
            mainMenu();
        }
        else
        {
            if(viewProjects() == 2)
            {
                case8();
            }
            else
            {
                mainMenu();
            }
        }
    }

    public void case3()
    {
        clearScreen();
        System.out.print("Enter Project ID: ");
        projID = sc.nextInt();
        println();

        System.out.print("Enter Initials: ");
        String initials = sc.nextLine();
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
        clearScreen();

        //createActivity();
        System.out.print("Project ID (\"0\" if external activity: ");
        int projectID = sc.nextInt();
        println();

        System.out.print("Initials: ");
        String initials = sc.nextLine();
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
        }
        System.out.print("Activity Name: ");
        String activityName = sc.nextLine();
        println();

        System.out.print("Time Budget: ");
        double timeBudget = sc.nextDouble();
        println();

        System.out.print("Start Date (yyyy-ww): ");
        String startDate = sc.nextLine();
        println();
        System.out.print("End Date (yyyy-ww): ");
        String endDate = sc.nextLine();
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

    public void case5 ()
    {
        clearScreen();

        System.out.print("View free employees in the period (yyyy-ww): ");
        startWeek = sc.nextLine();
        println();
        System.out.print("to (yyyy-ww): ");
        endWeek = sc.nextLine();
        println();
        try
        {
            viewFreeEmployees(startWeek,endWeek);
        }
        catch (OperationNotAllowedException ONAE)
        {
            throw new RuntimeException(ONAE);
        }
        sc.next();
        clearScreen();

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
            }
            catch (OperationNotAllowedException | FileNotFoundException EX)
            {
                println(EX.getMessage());
            }
        }
    }

    public void case7()
    {
        sc = new Scanner(System.in);
        println("Please enter the initials of the new developer. To return to the main menu, press \"x\" and then the enter key");
        String input = sc.next();
        if (input.equalsIgnoreCase("x"))
        {
            mainMenu();
        }
        String initials = sc.next();
        println();
        try
        {
            developerList.add(new Developer(initials));
            if (developerList.contains(getDeveloper(initials)))
            {
                println("Developer \"" + initials + "\" created");
            }
            else
            {
                throw new OperationNotAllowedException("An error occurred in adding developer");
            }
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
            case2(2);
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
        String input = sc.nextLine();
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
        String input = sc.nextLine();
        if (input.equalsIgnoreCase("x"))
        {
            case8();
        }
        else if (input.equalsIgnoreCase("Customer Project"))
        {
            p.customerProject = true;
            println("New project type is: " + projType(p));
            mainMenu();
        }
        else if (input.equalsIgnoreCase("Internal Project"))
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
            println("No projects are created yet");
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
            if (dev.isFree(LocalDate.parse(startWeek + "-1"), LocalDate.parse(endWeek + "-7")))
            {
                println(dev.initials);
            }
        }
    }
}
