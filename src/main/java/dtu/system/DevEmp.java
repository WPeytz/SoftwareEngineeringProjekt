package dtu.system;

import java.util.ArrayList;

public class DevEmp {
    //DevEmp
    String initials;
    ArrayList<Activity> activeActivities;
    boolean free;

    public DevEmp (String initials, boolean free)
    {
        this.initials = initials;
        activeActivities = new ArrayList<>();
        this.free = free;
    }

    public boolean isFree () {
        for (Activity a : activeActivities) {
            if (!a.externalActivity) {
                return false;
            }
        }
        return true;
    }

}
