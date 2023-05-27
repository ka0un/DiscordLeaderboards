package org.kasun.discordleaderboards.Leaderboard;


import org.kasun.discordleaderboards.Utils.SqlUtils;


import java.util.Map;


public class TopList {
    private final int top;
    private final LeaderboardConfig leaderboardConfig;
    private final String placeholder;
    private final String leaderboardname;
    String placeholderColumnName;

    public TopList(LeaderboardConfig leaderboardConfig) {
        this.leaderboardConfig = leaderboardConfig;
        top = leaderboardConfig.getTop();
        placeholder = leaderboardConfig.getPlaceholder();
        leaderboardname = leaderboardConfig.getName();
        placeholderColumnName = leaderboardConfig.getPlaceholder().substring(1, leaderboardConfig.getPlaceholder().length() - 1);
    }

    public Map<String, Integer> getTopListAsMap() {
        int top = leaderboardConfig.getTop();
        return SqlUtils.getTopPlayerMap(placeholderColumnName, top);

    }

    public String getTopListAsStringForWebhook() {
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
            String formattedEntry = String.format("%d. %-" + (maxNameLength + 3) + "s %d%s\\u000A", i++, name, score, leaderboardConfig.getMetric());
            sb.append(formattedEntry);
        }
        String leaderboardString = sb.toString();
        return leaderboardString;
    }

    public String getTopListAsString() {
        Map<String, Integer> toplistmap = getTopListAsMap();
        StringBuilder sb = new StringBuilder();
        int i = 1;
        for (Map.Entry<String, Integer> entry : toplistmap.entrySet()) {
            String name = entry.getKey();
            int score = entry.getValue();
            String formattedEntry = String.format("%d. %-20s %d%s\n", i++, name, score, leaderboardConfig.getMetric());
            sb.append(formattedEntry);
        }
        String leaderboardString = sb.toString();
        return leaderboardString;
    }


}
