package dtu.system;

import java.sql.Time;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.io.*;
public class Menu extends TimeManager
{
    int projID;
    String startWeek, endWeek;

    //Gosden
    public void println()
    {
        System.out.println();
    }

    //Gosden
    public void println(String print)
    {
        System.out.println(print);
    }

    //Gosden
    public Menu()
    {
        mainMenu();
    }

    //William
    public void mainMenu()
    {
        menuList();
        int userIn = 0;
        sc = new Scanner(System.in);
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
                case 11 -> addDeveloperToActivityCase(0);
                case 12 -> editActivityCase();
                case 13 -> registerTimeCase();
                case 14 -> requestAssistanceCase();
                case 15 -> editRegisteredTimeCase();
                case 0 -> systemExit();
                default ->
                {
                    println("Undefined input. Try again");
                    mainMenu();
                }
            }
        }
    }

    //Wind
    public void menuList()
    {
        println();
        println("1. Create Project");                       //  1. Create Project
        println("2. View Projects");                        //  2. View Projects
        println("3. Create Report");                        //  3. Create Report
        println("4. Create Activity");                      //  4. Create Activity
        println("5. View Free Employees");                  //  5. View Free Employees
        println("6. View reports");                         //  6. View report list
        println("7. Create new developer");                 //  7. Create developer
        println("8. Edit a project");                       //  8. Edit project parameters
        println("9. Add or change manager for a project");  //  9. Add project manager
        println("10. Add developer to project");            //  10. Add developer to project
        println("11. Add developer to activity");           //  11. Add developer to activity
        println("12. View and edit activities");            //  12. Edit activity
        println("13. Register Time");                       //  13. Register time
        println("14. Request assistance");                  //  14. Request assistance
        println("15. Edit time registration");              //  15. Edit registered time
        println("0. Close system");                         //  0. Close system
    }

    //Gosden
    public void createProjectCase()
    {
        println("Project name must not contain any white spaces.");
        println("If a whitespace is typed in, anything after the first whitespace will not be a part of the project name.");
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
        mainMenu();
    }

    //Wind
    public void viewProjectsCase(int j)
    {
        int i = viewProjects();
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

    //Gosden
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
        mainMenu();
    }

    //Wind
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
        mainMenu();
    }

    //Nikolai
    public void viewFreeEmployeesCase()
    {
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

    //William
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

    //Gosden
    public void createNewDeveloperCase()
    {
        sc = new Scanner(System.in);
        println("Please enter the initials of the new developer. To return to the main menu, press \"x\" and then the enter key");
        println("Initals of a developer must only contain letters, and be 4 (four) characters long");
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

    //William
    public void editProjectCase()
    {
        println("Please enter the ID of the project that should be edited. To return to the main menu, press \"x\" and then the enter key");
        Project p = projectEditDuplicateCode();
        editProjectMenu(p,projID);
    }

    //Wind
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

    //William
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

    //Gosden
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
            DateTimeFormatter format = DateTimeFormatter.ofPattern("YYYY-ww-e");
            if(LocalDate.parse(input+"-7",format).isBefore(p.startWeek))
            {
                println("The end week cannot be before the start week of the project.");
                println("Returning to the main menu...");
                mainMenu();
            }
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

    //Wind
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

    //William
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

    //Gosden
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
            println("Invalid input, please try again. Returning to main menu...");
            mainMenu();
        }
        catch(OperationNotAllowedException ONAE)
        {
            println(ONAE.getMessage());
            println("Returning to main menu...");
            mainMenu();
        }

        int i = viewEmployees();
        if (i==2)
        {
            mainMenu();
        }
        return p;
    }

    //Nikolai
    public void addDeveloperToProjectCase()
    {
        println("Please enter the ID of the project you would like to assign a developer to. To return to the main menu, press \"x\" and then the enter key");
        Project p = projectEditDuplicateCode();
        println("Please enter the initials of the developer you would like to assign to the project " + p.name);
        sc = new Scanner(System.in);
        String developer = sc.nextLine();
        try
        {
            Developer devToAdd = getDeveloper(developer.toLowerCase());
            p.addWorkingDev(devToAdd);
            println("Developer " + devToAdd.initials + " is now assigned to project " + p.name);
            mainMenu();
        }
        catch (OperationNotAllowedException ONAE)
        {
            println(ONAE.getMessage());
            println("Returning to main menu...");
            mainMenu();
        }
    }

    //Wind
    public Activity activityEditDuplicateCode(int param)
    {
        if (param == 0)
        {
            println("Please enter the ID of the project, where the activity you would like modify is located. To return to the main menu, press \"x\" and then the enter key");
        }
        else if (param == 1)
        {
            println("Please enter the ID of the project, where the activity you would like help is located. To return to the main menu, press \"x\" and then the enter key");
        }
        Project p = projectEditDuplicateCode();
        viewActivities(p);
        int actID;
        sc = new Scanner(System.in);
        if (param == 0)
        {
            println("Please enter the number of the desired activity (as shown in the list above), you would like to modify. To return to the main menu, press \"x\" and then the enter key");
        }
        else if (param == 1)
        {
            println("Please enter the number of the desired activity (as shown in the list above), you would like help. To return to the main menu, press \"x\" and then the enter key");
        }
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

    //Nikolai
    public void addDeveloperToActivityCase(int param)
    {
        Activity a = activityEditDuplicateCode(param);

        int i = viewEmployees();
        if (i==2)
        {
            mainMenu();
        }

        if(param == 0)
        {
            println("Please enter the initials of the developer you would like to assign to the activity " + a.name);
        }
        else if (param == 1)
        {
            println("Please enter the initials of the developer you would like help from " + a.name);
        }
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

    //William
    public void editActivityCase()
    {
        Activity a = activityEditDuplicateCode(0);

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

    //Wind
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

    //Gosden
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

    //Nikolai
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

    //Wind
    public void registerTimeCase()
    {
        Developer dev = null;
        Activity desiredActivity = null;
        Activity[] actList;
        int index = 0;
        int input = 0;

        if(developerList.isEmpty())
        {
            println("There are currently no existing developers. Please add a developer first to proceed.");
            mainMenu();
        }

        println("To register your time spent on an activity, please enter your initials. To return to the main menu, press \"x\" and then the enter key");
        sc = new Scanner(System.in);
        String initials = sc.nextLine();
        try
        {
            dev = getDeveloper(initials);
            if (!dev.activities.isEmpty())
            {
                actList = new Activity[dev.activities.size()+1];
                println("You are currently registered on the activities:");
                for (Activity a : dev.activities)
                {
                    index++;
                    actList[index] = a;
                    println(index + ": " + a.name + " in project: " + getProject(a.projectID).name);
                }
                try
                {
                    println("please enter the number of the desired activity");
                    sc = new Scanner(System.in);
                    input = sc.nextInt();

                    desiredActivity = actList[input];
                }
                catch (ArrayIndexOutOfBoundsException AIOOBE)
                {
                    println("No activity with the index \"" + input +"\". Please try again.");
                    registerTimeCase();
                }
                catch (InputMismatchException IME)
                {
                    println("Illegal input. Only enter a number. Please try again.");
                    registerTimeCase();
                }
                println("Selected activity: " + desiredActivity.name);
                println("Please enter the start time, in the format \"dd-MM-yyyy hh.mm \".");

                String startTime = null;
                String endTime = null;

                sc = new Scanner(System.in);
                startTime = sc.nextLine();
                println("Please enter the end time, in the format \"dd-MM-yyyy HH.mm \".");
                sc = new Scanner(System.in);
                endTime = sc.nextLine();
                println();
                println("StartTime: " + startTime);
                println("EndTime: " + endTime);
                println();
                try
                {
                    dev.registerTimeSpent(desiredActivity,startTime,endTime);
                    println("Time has been registered");
                    println("Returning to main menu...");
                    mainMenu();
                }
                catch (OperationNotAllowedException ONAE)
                {
                    println(ONAE.getMessage());
                }
            }
            else
            {
                throw new OperationNotAllowedException("Developer \"" + dev.initials + "\" is not assigned to any activities");
            }
        }
        catch (OperationNotAllowedException ONAE)
        {
            println(ONAE.getMessage());
            println("Returning to main menu...");
            mainMenu();
        }
    }

    //Nikolai
    public void editRegisteredTimeCase()
    {
        Developer dev = null;
        TimeSpent desiredWorkTime = null;
        TimeSpent[] workTimesList;
        int index = 0;
        int input = 0;

        if(developerList.isEmpty())
        {
            println("There are currently no existing developers. Please add a developer first to proceed.");
            mainMenu();
        }

        println("To edit your registered time spent on an activity, please enter your initials. To return to the main menu, press \"x\" and then the enter key");
        sc = new Scanner(System.in);
        String initials = sc.nextLine();
        try
        {
            dev = getDeveloper(initials);
            if (!dev.workTimes.isEmpty())
            {
                workTimesList = new TimeSpent[dev.workTimes.size()+1];
                println("You currently  have registered these work times: ");
                for (TimeSpent ts : dev.workTimes)
                {
                    index++;
                    workTimesList[index] = ts;
                    println(index + ": Activity: " + ts.activity.name + ". Start time: " + ts.startTime + ". End time: " + ts.endTime + ". Duration: " + ts.duration);
                }
                try
                {
                    println("please enter the number of the desired time registration");
                    sc = new Scanner(System.in);
                    input = sc.nextInt();

                    desiredWorkTime = workTimesList[input];
                }
                catch (ArrayIndexOutOfBoundsException AIOOBE)
                {
                    println("No time registration with the index \"" + input +"\". Please try again.");
                    registerTimeCase();
                }
                catch (InputMismatchException IME)
                {
                    println("Illegal input. Only enter a number. Please try again.");
                    registerTimeCase();
                }

                println("Selected time registration: " + "Activity: " + desiredWorkTime.activity.name + ". Start time: " + desiredWorkTime.startTime + ". End time: " + desiredWorkTime.endTime+ ". Duration: " + desiredWorkTime.duration);
                println("Would you like to edit this time registration? (y/n)");

                sc = new Scanner(System.in);
                String yn = sc.next();
                if (!yn.equalsIgnoreCase("y"))
                {
                    println("Procedure cancelled");
                    mainMenu();
                }

                desiredWorkTime.activity.timeSpent.add(-desiredWorkTime.duration);
                dev.workTimes.remove(desiredWorkTime);

                println("The time registration has now been deleted. Please enter the new time registration:");

                registerTimeCase();
            }
            else
            {
                throw new OperationNotAllowedException("No time registrations made for developer \"" + dev.initials + "\"");
            }
        }
        catch (OperationNotAllowedException ONAE)
        {
            println(ONAE.getMessage());
            println("Returning to main menu...");
            mainMenu();
        }
    }

    //Willima
    public void requestAssistanceCase()
    {
        addDeveloperToActivityCase(1);
    }

    //Gosden
    public String projType(Project p)
    {
        if (p.customerProject)
        {
            return "Customer Project";
        }
        return "Internal Project";
    }

    //Wind
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

    //William
    public int viewActivities(Project p)
    {
        if(!p.activities.isEmpty())
        {
            int i = 1;
            println("Activies for project " + p.name);
            for (Activity a : p.activities)
            {
                println(i + ": " +a.name);
            }
        }
        else
        {
            println("No activities are created yet. Returning to the main menu...");
            mainMenu();
        }
        return 2;
    }

    //Gosden
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

    //Wind
    public void viewFreeEmployees(String startWeek, String endWeek) throws OperationNotAllowedException
    {
        format = DateTimeFormatter.ofPattern("YYYY-ww-e");

        for (Developer dev : developerList)
        {
            try
            {
                if (dev.isFree(LocalDate.parse(startWeek + "-1",format), LocalDate.parse(endWeek + "-7",format)))
                {
                    println(dev.initials);
                }
            }
            catch (DateTimeParseException DTPE)
            {
                println("Wrong format of entered dates. Please try again. Returning to main menu...");
                mainMenu();
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

    //William
    public void systemExit()
    {
        println("Do you really want to close the system? (y/n)");
        sc = new Scanner(System.in);
        String input = sc.next();
        if (input.equalsIgnoreCase("y"))
        {
            println("We are sad to see you go :(");
            println("We hope you liked our program!");
            println("It took us many hours and a lot of sweat and tears to make it.");
            exitProcedure();
        }
        else
        {
            mainMenu();
        }
    }

    //Wind
    public void exitProcedure()
    {
        String[] endScreen =
        {
            "Credits:",
            "Alexander Samuel Bendix Gosden (s204209)",
            "William Peytz (s204145)",
            "Nikolai Hansen (s214681)",
            "Aleksander Wind (s214683)",
            "Hubert (Aleksander Wind's Labrador puppy). No seriously, my dog is actually named Hubert. I am not kidding."
        };
        for (String str : endScreen)
        {
            try
            {
                Thread.sleep(1000);
            }
            catch (InterruptedException e)
            {
                System.exit(-1);
            }
            println(str);
        }
        System.exit(0);
    }
}