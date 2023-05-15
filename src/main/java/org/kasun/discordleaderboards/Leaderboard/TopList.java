package org.kasun.discordleaderboards.Leaderboard;

import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import org.kasun.discordleaderboards.Utils.SqlUtils;
import java.util.Map;

public class TopList {
    private int top;
    private Leaderboard leaderboard;
    private LeaderboardConfig leaderboardConfig;
    private String placeholder;
    private String leaderboardname;
    String placeholderColumnName;

    public TopList(Leaderboard leaderboard) {
        this.leaderboard = leaderboard;
        leaderboardConfig = leaderboard.getConfig();
        top = leaderboardConfig.getTop();
        placeholder = leaderboardConfig.getPlaceholder();
        leaderboardname = leaderboard.getName();
        placeholderColumnName = leaderboardConfig.getPlaceholder().substring(1, leaderboardConfig.getPlaceholder().length() - 1);
    }

    public TopList(String leaderboardname) {
        this.leaderboardname = leaderboardname;
        leaderboard = new Leaderboard(leaderboardname);
        leaderboardConfig = leaderboard.getConfig();
        top = leaderboardConfig.getTop();
        placeholder = leaderboardConfig.getPlaceholder();
        placeholderColumnName = leaderboardConfig.getPlaceholder().substring(1, leaderboardConfig.getPlaceholder().length() - 1);
    }

    public Map<String, Integer> getTopListAsMap(){
        int top = leaderboardConfig.getTop();
        Map<String, Integer> toplist = SqlUtils.getTopPlayerMap(placeholderColumnName, top);
        return toplist;
    }

    public String getTopListAsStringForWebhook(){
        Map<String, Integer> toplistmap = getTopListAsMap();
        // find the maximum length of the names
        int maxNameLength = 0;
        for (String name : toplistmap.keySet()) {
            if (name.length() > maxNameLength) {
                maxNameLength = name.length();
            }
        }
        StringBuilder sb = new StringBuilder();
        int i = 1;
        for (Map.Entry<String, Integer> entry : toplistmap.entrySet()) {
            String name = entry.getKey();
            int score = entry.getValue();
            String formattedEntry = String.format("%d. %-"+(maxNameLength+4)+"s %d\\u000A", i++, name, score);
            sb.append(formattedEntry);
        }
        String leaderboardString = sb.toString();
        return leaderboardString;
    }

    public String getTopListAsString(){
        Map<String, Integer> toplistmap = getTopListAsMap();
        StringBuilder sb = new StringBuilder();
        int i = 1;
        for (Map.Entry<String, Integer> entry : toplistmap.entrySet()) {
            String name = entry.getKey();
            int score = entry.getValue();
            String formattedEntry = String.format("%d. %-20s %d\n", i++, name, score);
            sb.append(formattedEntry);
        }
        String leaderboardString = sb.toString();
        return leaderboardString;
    }


}
