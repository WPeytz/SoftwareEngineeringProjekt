package dtu.system;

import java.nio.file.Paths;
import java.util.*;
import java.io.*;

public class TimeManager {
    ArrayList<Activity> extActList;
    HashSet<Project> projectList;
    ArrayList<DevEmp> devEmpList;

    public TimeManager () {
        extActList = new ArrayList<>();
        projectList = new HashSet<>();
        devEmpList = new ArrayList<>();

    }

    public void createReport(Project project)
    {

    }


    public void createProject(String name, boolean customerProject)
    {
        projectList.add(new Project(name, customerProject));
    }


}
