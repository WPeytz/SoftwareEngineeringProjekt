package dtu.system;

public class Main {
    public static void main(String[] args) {
        TimeManager manager = new TimeManager();
        manager.createProject("website001",true);
        for (Project p : manager.projectList) {
            System.out.println(p.projectID);
        }
    }
}
