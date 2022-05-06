package dtu.system;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class TimeSpent extends Developer
{
    Activity activity;
    LocalDateTime startTime, endTime;
    double duration;
    public TimeSpent (Activity act, LocalDateTime st, LocalDateTime et, double dur)
    {
        this.activity = act;
        this.startTime = st;
        this.endTime = et;
        this.duration = dur;
    }
}