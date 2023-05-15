package org.kasun.discordleaderboards.database;

import org.kasun.discordleaderboards.leaderboard.Leaderboard;
import org.kasun.discordleaderboards.utils.SqlUtils;

public class Cache {
    private Leaderboard leaderboard;
    private String cache;

    public Cache(Leaderboard leaderboard) {
        this.leaderboard = leaderboard;
        cache = leaderboard.toString();
    }

    public void setCache(){
        String leaderboardname = leaderboard.getName();
        SqlUtils.setCache(leaderboardname, cache);
    }

    public String getCache(){
        String leaderboardname = leaderboard.getName();
        String cache = SqlUtils.getCache(leaderboardname);
        return cache;
    }

    public boolean isDiffernt(){
        String cache = getCache();
        if (cache == null) {
            return true; // If cache is null, then it's different
        }
        return !this.cache.equals(cache);
    }
}
