package org.kasun.discordleaderboards.Schedules;

import org.kasun.discordleaderboards.Database.LiveCache;
import org.kasun.discordleaderboards.Database.Schedule;
import org.kasun.discordleaderboards.Utils.Leaderboard;
import org.kasun.discordleaderboards.Utils.TimeUtils;

import java.sql.Timestamp;

public class LeaderboardSchedule {

    private static final long SECSFORHOUR = 3600;
    private static final long SECSFORDAY = 86400;
    private static final long SECSFORWEEK = 604800;
    private static final long SECSSFORMONTH = 2629746;

    public static void runLeaderboardSchedule(String leaderboard) {
        if (Schedule.isAlredySent(leaderboard)){

            System.out.println("Debug : Sheduler is running");

            Timestamp currentTime = TimeUtils.getCurrentTimeStamp();
            Timestamp lastsent = Schedule.getLastSent(leaderboard);

            switch (Leaderboard.getDelay(leaderboard).toLowerCase()){
                case "live":
                    if (LiveCache.isDifferent(leaderboard)){
                        Leaderboard.sendleaderboard(leaderboard);
                        LiveCache.setCache(leaderboard);
                    }
                    break;
                case "hourly":
                    if (TimeUtils.getTimestampDifference(currentTime, lastsent) >= SECSFORHOUR){
                        Leaderboard.sendleaderboard(leaderboard);
                        Schedule.setLastSent(leaderboard);

                    }
                    break;
                case "daily":
                    if (TimeUtils.getTimestampDifference(currentTime, lastsent) >= SECSFORDAY){
                        Leaderboard.sendleaderboard(leaderboard);
                        Schedule.setLastSent(leaderboard);

                    }
                    break;
                case "weekly":
                    if (TimeUtils.getTimestampDifference(currentTime, lastsent) >= SECSFORWEEK){
                        Leaderboard.sendleaderboard(leaderboard);
                        Schedule.setLastSent(leaderboard);

                    }
                    break;
                case "monthly":
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
