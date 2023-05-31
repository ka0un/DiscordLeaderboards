package org.kasun.discordleaderboards.Leaderboard;

import me.clip.placeholderapi.PlaceholderAPI;
import org.kasun.discordleaderboards.Utils.TimeUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.regex.*;

public class DescriptionGenerator {
    private final Leaderboard leaderboard;
    private final List<String> embedDescriptionlist;
    private final TopList topList;
    private final long SECSFORHOUR = 3600;
    private final long SECSFORDAY = 86400;
    private final long SECSFORWEEK = 604800;
    private final long SECSSFORMONTH = 2629746;
    private int numberOfFloatingPoints;
    private boolean isHigherBetter;

    public DescriptionGenerator(Leaderboard leaderboard) {
        this.leaderboard = leaderboard;
        embedDescriptionlist = leaderboard.getConfig().getEmbedDescription();
        topList = leaderboard.getTopList();
        numberOfFloatingPoints = leaderboard.getConfig().getFloatingpoints();
        isHigherBetter = leaderboard.getConfig().isHigherisbetter();
    }

    public DescriptionGenerator(String leaderboardname) {
        this.leaderboard = new Leaderboard(leaderboardname);
        embedDescriptionlist = leaderboard.getConfig().getEmbedDescription();
        topList = leaderboard.getTopList();
    }

    public String getDescription(boolean isForWebhook) {
        String description = String.join(isForWebhook ? "\\u000A" : "\n", embedDescriptionlist);

        //{toplist} placeholder
        String replacement1 = "```" + (isForWebhook ? topList.getTopListAsString(true) : topList.getTopListAsString(false)) + "```";
        if (replacement1.equals("``````") || replacement1 == null) {
            description = description.replace("{toplist}", "```Leaderboard is empty!```");
        } else {
            description = description.replace("{toplist}", replacement1);
        }

        //{top-1-name} {top-1-score} placeholders
        Map<String, Double> playerScores = topList.getTopListAsMap();
        Pattern pattern = Pattern.compile("\\{top-(\\d+)-(name|score)\\}");
        Matcher matcher = pattern.matcher(description);
        while (matcher.find()) {
            int position = Integer.parseInt(matcher.group(1));
            String placeholderType = matcher.group(2);

            Comparator<Map.Entry<String, Double>> scoreComparator;
            if (isHigherBetter) {
                scoreComparator = Map.Entry.comparingByValue(Comparator.reverseOrder());
            } else {
                scoreComparator = Map.Entry.comparingByValue();
            }

            Map.Entry<String, Double> entry = playerScores.entrySet().stream()
                    .sorted(scoreComparator)
                    .skip(position - 1)
                    .findFirst()
                    .orElse(null);

            if (entry != null) {
                String placeholder = "{top-" + position + "-" + placeholderType + "}";
                int intValue = entry.getValue().intValue();
                String replacement = "";
                if (leaderboard.getConfig().getFloatingpoints() > 0){
                    replacement = placeholderType.equals("name") ? entry.getKey() : String.valueOf(entry.getValue());
                }else{
                    replacement = placeholderType.equals("name") ? entry.getKey() : String.valueOf(intValue);
                }
                description = description.replace(placeholder, replacement);
            }
        }

        //{timestamp-now} placeholder
        description = description.replace("{timestamp-now}", "<t:" + TimeUtils.getCurrentUnixTimestamp() + ":R>");

        //{timestamp-next} placeholder
        String delay = leaderboard.getConfig().getDelay();
        switch (delay.toLowerCase()) {
            case "hourly":
                description = description.replace("{timestamp-next}", "<t:" + (TimeUtils.getCurrentUnixTimestamp() + SECSFORHOUR) + ":R>");
                break;
            case "daily":
                description = description.replace("{timestamp-next}", "<t:" + (TimeUtils.getCurrentUnixTimestamp() + SECSFORDAY) + ":R>");
                break;
            case "weekly":
                description = description.replace("{timestamp-next}", "<t:" + (TimeUtils.getCurrentUnixTimestamp() + SECSFORWEEK) + ":R>");
                break;
            case "monthly":
                description = description.replace("{timestamp-next}", "<t:" + (TimeUtils.getCurrentUnixTimestamp() + SECSSFORMONTH) + ":R>");
                break;
            default:
                break;
        }

        //{%papi%} placeholders
        String patternString = "\\{%(.*?)%\\}";
        Pattern pattern1 = Pattern.compile(patternString);
        Matcher matcher1 = pattern1.matcher(description);

        StringBuilder result = new StringBuilder();
        while (matcher1.find()) {
            String placeholder = matcher1.group(0);
            String innerValue = matcher1.group(1);
            String replacement = PlaceholderAPI.setPlaceholders(null, "%" + innerValue + "%");

            description = description.replace(placeholder, replacement);
            result.append(innerValue).append(", ");
        }

        return description;
    }


}
