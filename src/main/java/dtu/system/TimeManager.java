package dtu.system;

import java.util.*;

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
 
    public ArrayList<DevEmp> viewFreeEmps()
    {
        ArrayList<DevEmp> FreeEmpsList = new ArrayList<>();
        for (DevEmp devs : devEmpList)
        {
            if (devs.free) FreeEmpsList.add(devs);
        }

        return FreeEmpsList;
    }

    public void createProject(String name, boolean customerProject)
    {
        projectList.add(new Project(name, customerProject));
    }


}
