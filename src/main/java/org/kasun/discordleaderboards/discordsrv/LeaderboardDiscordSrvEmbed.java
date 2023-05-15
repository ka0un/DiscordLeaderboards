package org.kasun.discordleaderboards.discordsrv;

import org.kasun.discordleaderboards.leaderboard.Leaderboard;
import org.kasun.discordleaderboards.leaderboard.TopList;

public class LeaderboardDiscordSrvEmbed {
    private Leaderboard leaderboard;
    private DiscordsrvEmbed discordsrvEmbed;
    private TopList topList;

    public LeaderboardDiscordSrvEmbed(Leaderboard leaderboard) {
        this.leaderboard = leaderboard;
    }

    public DiscordsrvEmbed getDiscordsrvEmbed(){
        discordsrvEmbed = new DiscordsrvEmbed();
        discordsrvEmbed.setEmbedTitle(leaderboard.getConfig().getEmbedTitle());
        discordsrvEmbed.setEmbedColour(leaderboard.getConfig().getEmbedColour());
        discordsrvEmbed.setEmbedFooter(leaderboard.getConfig().getEmbedFooter());
        discordsrvEmbed.setEmbedImage(leaderboard.getConfig().getEmbedImage());
        discordsrvEmbed.setEmbedThumbnail(leaderboard.getConfig().getEmbedThumbnail());
        topList = new TopList(leaderboard.getConfig());
        String description = "```" + topList.getTopListAsStringForWebhook() + "```";
        discordsrvEmbed.setEmbedDescription(description);

        return discordsrvEmbed;
    }


}