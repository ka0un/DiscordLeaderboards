package org.kasun.discordleaderboards.Schedules;

import org.kasun.discordleaderboards.Database.Schedule;
import org.kasun.discordleaderboards.Utils.Leaderboard;
import org.kasun.discordleaderboards.Utils.TimeUtils;

import java.sql.Timestamp;

public class LeaderboardSchedule {

    private static long SECSFORHOUR = 3600;
    private static long SECSFORDAY = 86400;
    private static long SECSFORWEEK = 604800;
    private static long SECSSFORMONTH = 2629746;

    public static void runLeaderboardSchedule(String leaderboard) {
        if (Schedule.isAlredySent(leaderboard)){

            System.out.println("Debug : Sheduler is running");

            Timestamp currentTime = TimeUtils.getCurrentTimeStamp();
            Timestamp lastsent = Schedule.getLastSent(leaderboard);

            switch (Leaderboard.getDelay(leaderboard)){
                case "Hourly":
                    if (TimeUtils.getTimestampDifference(currentTime, lastsent) >= SECSFORHOUR){
                        Leaderboard.sendleaderboard(leaderboard);
                        Schedule.setLastSent(leaderboard);
                    }
                    break;
                case "Daily":
                    if (TimeUtils.getTimestampDifference(currentTime, lastsent) >= SECSFORDAY){
                        Leaderboard.sendleaderboard(leaderboard);
                        Schedule.setLastSent(leaderboard);
                    }
                    break;
                case "Weekly":
                    if (TimeUtils.getTimestampDifference(currentTime, lastsent) >= SECSFORWEEK){
                        Leaderboard.sendleaderboard(leaderboard);
                        Schedule.setLastSent(leaderboard);
                    }
                    break;
                case "Monthly":
                    if (TimeUtils.getTimestampDifference(currentTime, lastsent) >= SECSSFORMONTH){
                        Leaderboard.sendleaderboard(leaderboard);
                        Schedule.setLastSent(leaderboard);
                    }
                    break;
                default:
                    break;
            }

        }else {
            Leaderboard.sendleaderboard(leaderboard);
            Schedule.setLastSent(leaderboard);
        }
    }
}
