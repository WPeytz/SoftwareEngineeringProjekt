package dtu.system;

import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.io.*;

public class Menu extends TimeManager
{

    int projID;
    String startDate,endDate;

    public void menu() {
        System.out.println();
        System.out.println("1. Create Project");
        System.out.println("2. View Projects");
        System.out.println("3. Create Report");
        System.out.println("4. Create Activity");
        System.out.println("5. View Free Employees");
        System.out.println("6. View reports");
        System.out.println("0. Close");
    }
    public void case1() throws InterruptedException
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
    public void case2()
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
        if (!initials.equals(getProject(projID).projectManager.initials))
        {
            System.out.println("Credentials do not match.");
        }

        createReport(getProject(projID));
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
    public void case5()
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
            File file = new File(String.valueOf(path));
            Scanner sc = new Scanner(file);

            printFileContents(sc);
        }
    }

    public void case666() throws FileNotFoundException
    {
        String filePath = "HappyMerchant.txt";
        var path = Paths.get("src/main/java/dtu/system/", filePath);
        File file = new File(String.valueOf(path));
        Scanner sc = new Scanner(file);

        printFileContents(sc);
    }

    public void printFileContents (Scanner sc)
    {
        while (sc.hasNextLine())
        {
            System.out.println(sc.nextLine());
        }
    }
}
