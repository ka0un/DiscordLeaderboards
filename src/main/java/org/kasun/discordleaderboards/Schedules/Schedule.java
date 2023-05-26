package org.kasun.discordleaderboards.Schedules;

import org.kasun.discordleaderboards.Database.Cache;
import org.kasun.discordleaderboards.Leaderboard.Leaderboard;
import org.kasun.discordleaderboards.Utils.SqlUtils;
import org.kasun.discordleaderboards.Utils.TimeUtils;

import java.sql.Timestamp;

public class Schedule {
    private final String delay;
    private final Leaderboard leaderboard;
    private boolean isalredysent;

    private final long SECSFORHOUR = 3600;
    private final long SECSFORDAY = 86400;
    private final long SECSFORWEEK = 604800;
    private final long SECSSFORMONTH = 2629746;

    public Schedule(Leaderboard leaderboard) {
        this.leaderboard = leaderboard;
        delay = leaderboard.getConfig().getDelay();
    }

    public void run() {

        if (isAlredySent()){

            Timestamp currentTime = TimeUtils.getCurrentTimeStamp();
            Timestamp lastsent = getLastSent();

            switch (delay.toLowerCase()){
                case "live":
                    Cache cache = new Cache(leaderboard);
                    if (cache.isDiffernt()){
                        leaderboard.send();
                        cache.setCache();
                    }
                    break;
                case "hourly":
                    if (TimeUtils.getTimestampDifference(currentTime, lastsent) >= SECSFORHOUR){
                        leaderboard.send();
                        setLastSent();
                        deleteDataIfNeeded();
                    }
                    break;
                case "daily":
                    if (TimeUtils.getTimestampDifference(currentTime, lastsent) >= SECSFORDAY){
                        leaderboard.send();
                        setLastSent();
                        deleteDataIfNeeded();
                    }
                    break;
                case "weekly":
                    if (TimeUtils.getTimestampDifference(currentTime, lastsent) >= SECSFORWEEK){
                        leaderboard.send();
                        setLastSent();
                        deleteDataIfNeeded();
                    }
                    break;
                case "monthly":
                    if (TimeUtils.getTimestampDifference(currentTime, lastsent) >= SECSSFORMONTH){
                        leaderboard.send();
                        setLastSent();
                        deleteDataIfNeeded();
                    }
                    break;
                default:
                    break;
            }
        }else {
            leaderboard.send();
            setLastSent();
        }
    }

    public void setLastSent(){
        String leaderboardname = leaderboard.getName();
        Timestamp currenttimestamp = TimeUtils.getCurrentTimeStamp();
        SqlUtils.setScheduleLastSent(leaderboardname, currenttimestamp);
    }

    public Timestamp getLastSent(){
        String leaderboardname = leaderboard.getName();
        Timestamp lastsent = SqlUtils.getScheduleLastSent(leaderboardname);
        return lastsent;
    }

    public boolean isAlredySent(){
        String leaderboardname = leaderboard.getName();
        isalredysent = SqlUtils.isScheduleAlredySent(leaderboardname);
        return isalredysent;
    }

    public void deleteDataIfNeeded(){
        boolean isrefreshonsent = leaderboard.getConfig().isRefreshOnSent();
        String ColumnName = leaderboard.getConfig().getPlaceholder().substring(1, leaderboard.getConfig().getPlaceholder().length() - 1);
        String tablename = "UserData";
        if (isrefreshonsent){
            SqlUtils.deleteAllValues(tablename, ColumnName);
        }
    }
}
