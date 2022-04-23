package dtu.system;

import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.*;
import java.io.*;

public class Menu extends TimeManager
{
    int projID;
    String startDate,endDate;

    public void menu()
    {
        /*
         * 1. Create Project
         * 2. View Projects
         * 3. Create Report
         * 4. Create Activity
         * 5. View Free Employees
         * 6. View report list
         * 7. Open report file //TODO
         */
        System.out.println();
        System.out.println("1. Create Project");
        System.out.println("2. View Projects");
        System.out.println("3. Create Report");
        System.out.println("4. Create Activity");
        System.out.println("5. View Free Employees");
        System.out.println("6. View reports");
        System.out.println("0. Close");
    }

    public void case1() throws Exception
    {
        clearScreen();
        System.out.print("Project Name: ");
        String projectName = sc.nextLine();
        System.out.println();
        System.out.print("External Project? y/n: ");
        String extProject = sc.next();
        System.out.println();
        boolean extPrjct;
        if (extProject.contains("n"))
        {
            extPrjct = false;
        }
        else
        {
            extPrjct = true;
        }
        System.out.print("Start Date (yyyy-ww): ");
        startDate = sc.nextLine();
        System.out.println();
        System.out.print("End Date (yyyy-ww): ");
        endDate = sc.nextLine();
        System.out.println();
        createProject(projectName, extPrjct, startDate, endDate);
        System.out.println("Project \"" + projectName + "\" created.");
        Thread.sleep(500);
        clearScreen();
        menu();
    }

    public void case2 () throws Exception
    {
        clearScreen();
        viewProjects();
        sc.next();
        clearScreen();
        menu();
    }

    public void case3() throws Exception
    {
        clearScreen();
        System.out.print("Enter Project ID: ");
        projID = sc.nextInt();
        System.out.println();

        System.out.print("Enter Initials: ");
        String initials = sc.nextLine();
        System.out.println();

        createReport(getProject(projID),getDeveloper(initials));
        Thread.sleep(500);
        clearScreen();
        menu();
    }

    public void case4() throws Exception
    {
        clearScreen();
        createActivity();
        Thread.sleep(500);
        clearScreen();
        menu();
    }

    public void case5 () throws Exception
    {
        clearScreen();
        System.out.print("View free employees in the period (yyyy-ww): ");
        startDate = sc.nextLine();
        System.out.println();
        System.out.print("to (yyyy-ww): ");
        endDate = sc.nextLine();
        System.out.println();
        viewFreeEmployees(startDate,endDate);
        sc.next();
        clearScreen();
        menu();
    }

    public void case6() throws FileNotFoundException
    {
        File RepDirs = new File(getRepDir());
        String[] ReportList = RepDirs.list();
        System.out.println("Please choose a file to view:");

        int fileNumber = 0;

        for (String file : ReportList)
        {
            fileNumber++;
            System.out.println(fileNumber+": "+file);
        }

        Scanner fileSelect = new Scanner(System.in);
        int selectedFile = 0;
        while (true)
        {
            if (fileSelect.hasNextLine())
            {
                selectedFile = fileSelect.nextInt() - 1;
            }
            String fileName =  ReportList[selectedFile];
            var path = Paths.get(getRepDir(), fileName);

            printFileContents(path);
        }
    }

    public void printFileContents(Path path)
    {
        try
        {
            File file = new File(String.valueOf(path));
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine())
            {
                System.out.println(sc.nextLine());
            }
        }
        catch (FileNotFoundException FNFE)
        {
            FNFE.printStackTrace();
        }
    }
}
