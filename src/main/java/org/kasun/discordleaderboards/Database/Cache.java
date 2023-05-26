package org.kasun.discordleaderboards.Database;

import org.kasun.discordleaderboards.Leaderboard.Leaderboard;
import org.kasun.discordleaderboards.Utils.SqlUtils;

public class Cache {
    private final Leaderboard leaderboard;
    private final String cache;

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
        return SqlUtils.getCache(leaderboardname);
    }

    public boolean isDiffernt(){
        String cache = getCache();
        if (cache == null) {
            return true; // If cache is null, then it's different
        }
        return !this.cache.equals(cache);
    }
}
