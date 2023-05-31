package org.kasun.discordleaderboards.Leaderboard;


import org.kasun.discordleaderboards.Utils.SqlUtils;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Map;


public class TopList {
    private final int top;
    private final LeaderboardConfig leaderboardConfig;
    private final String placeholder;
    private final String leaderboardname;
    String placeholderColumnName;
    private int numberOfFloatingPoints;

    public TopList(LeaderboardConfig leaderboardConfig) {
        this.leaderboardConfig = leaderboardConfig;
        top = leaderboardConfig.getTop();
        placeholder = leaderboardConfig.getPlaceholder();
        leaderboardname = leaderboardConfig.getName();
        placeholderColumnName = leaderboardConfig.getPlaceholder().substring(1, leaderboardConfig.getPlaceholder().length() - 1);
        numberOfFloatingPoints = leaderboardConfig.getFloatingpoints();
    }

    public Map<String, Double> getTopListAsMap() {
        int top = leaderboardConfig.getTop();
        return SqlUtils.getTopPlayerMap(placeholderColumnName, top, leaderboardConfig.isHigherisbetter());

    }

    public String getTopListAsString(boolean isWebhookFormat) {
        Map<String, Double> toplistmap = getTopListAsMap();
        int maxNameLength = getmaxnamelenght(toplistmap);
        StringBuilder sb = new StringBuilder();
        int i = 1;
        for (Map.Entry<String, Double> entry : toplistmap.entrySet()) {
            String name = entry.getKey();
            double score = entry.getValue();
            int intValue = (int) score;

            String formattedEntry = "";
            if (numberOfFloatingPoints <= 0) {
                if (isWebhookFormat) {
                    formattedEntry = String.format("%d. %-" + (maxNameLength + 3) + "s %d%s\\u000A", i++, name, intValue, leaderboardConfig.getMetric());
                } else {
                    formattedEntry = String.format("%d. %-" + (maxNameLength + 3) + "s %d%s\n", i++, name, intValue, leaderboardConfig.getMetric());
                }
            } else {
                double roundedScore = roundScore(score);
                if (isWebhookFormat) {
                    formattedEntry = String.format("%d. %-" + (maxNameLength + 3) + "s %." + numberOfFloatingPoints + "f%s\\u000A", i++, name, roundedScore, leaderboardConfig.getMetric());
                } else {
                    formattedEntry = String.format("%d. %-" + (maxNameLength + 3) + "s %." + numberOfFloatingPoints + "f%s\n", i++, name, roundedScore, leaderboardConfig.getMetric());
                }
            }
            sb.append(formattedEntry);
        }
        String leaderboardString = sb.toString();
        return leaderboardString;
    }


    public String getTopListAsStringForWebhook() {
        Map<String, Double> toplistmap = getTopListAsMap();
        int maxNameLength = getmaxnamelenght(toplistmap);
        StringBuilder sb = new StringBuilder();
        int i = 1;
        for (Map.Entry<String, Double> entry : toplistmap.entrySet()) {
            String name = entry.getKey();
            double score = entry.getValue();
            int intValue = (int) score;

            String formattedEntry = "";
            if (numberOfFloatingPoints <= 0) {
                formattedEntry = String.format("%d. %-" + (maxNameLength + 3) + "s %d%s\\u000A", i++, name, intValue, leaderboardConfig.getMetric());
            } else {
                double roundedScore = roundScore(score);
                formattedEntry = String.format("%d. %-" + (maxNameLength + 3) + "s %." + numberOfFloatingPoints + "f%s\\u000A", i++, name, roundedScore, leaderboardConfig.getMetric());
            }
            sb.append(formattedEntry);
        }
        String leaderboardString = sb.toString();
        return leaderboardString;
    }

    public String getTopListAsString() {
        Map<String, Double> toplistmap = getTopListAsMap();
        int maxNameLength = getmaxnamelenght(toplistmap);
        StringBuilder sb = new StringBuilder();
        int i = 1;
        for (Map.Entry<String, Double> entry : toplistmap.entrySet()) {
            System.out.println("debug : 1");
            String name = entry.getKey();
            double score = entry.getValue();
            int intValue = (int) score;


            System.out.println("debug : 2");

            String formattedEntry = "";
            if (numberOfFloatingPoints <= 0){
                formattedEntry = String.format("%d. %-" + (maxNameLength + 3) + "s %d%s\n", i++, name, intValue, leaderboardConfig.getMetric());
            }else{
                double roundedScore = roundScore(score);
                formattedEntry = String.format("%d. %-" + (maxNameLength + 3) + "s %." + numberOfFloatingPoints + "f%s\n", i++, name, roundedScore, leaderboardConfig.getMetric());
            }
            System.out.println("debug : 3");
            sb.append(formattedEntry);
            System.out.println("Debug : " + numberOfFloatingPoints);

        }
        String leaderboardString = sb.toString();
        return leaderboardString;
    }

    private int getmaxnamelenght(Map<String, Double> toplistmap) {
        // find the maximum length of the names
        int maxNameLength = 0;
        for (String name : toplistmap.keySet()) {
            if (name.length() > maxNameLength) {
                maxNameLength = name.length();
            }
        }
        return maxNameLength;
    }

    private double roundScore(double score) {
        BigDecimal bd = new BigDecimal(score);
        bd = bd.setScale(numberOfFloatingPoints, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }


}
