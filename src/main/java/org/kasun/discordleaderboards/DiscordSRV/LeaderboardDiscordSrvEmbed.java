package org.kasun.discordleaderboards.DiscordSRV;

import jdk.jfr.internal.tool.Main;
import org.kasun.discordleaderboards.Configs.MainConfig;
import org.kasun.discordleaderboards.Leaderboard.DescriptionGenerator;
import org.kasun.discordleaderboards.Leaderboard.Leaderboard;
import org.kasun.discordleaderboards.Leaderboard.TopList;

public class LeaderboardDiscordSrvEmbed {
    private Leaderboard leaderboard;
    private DiscordsrvEmbed discordsrvEmbed;
    private TopList topList;

    public LeaderboardDiscordSrvEmbed(Leaderboard leaderboard) {
        this.leaderboard = leaderboard;
    }

    private String getDefaultIfNullOrEmpty(String value, String defaultValue) {
        return (value == null || value.isEmpty() || value.equals("-")) ? defaultValue : value;
    }

    public DiscordsrvEmbed getDiscordsrvEmbed() {


        MainConfig mainConfig = new MainConfig();
        String title = getDefaultIfNullOrEmpty(leaderboard.getConfig().getEmbedTitle(), mainConfig.getDembedTitle());
        String colour = getDefaultIfNullOrEmpty(leaderboard.getConfig().getEmbedColour(), mainConfig.getDembedColour());
        String footer = getDefaultIfNullOrEmpty(leaderboard.getConfig().getEmbedFooter(), mainConfig.getDembedFooter());
        String image = getDefaultIfNullOrEmpty(leaderboard.getConfig().getEmbedImage(), mainConfig.getDembedImage());
        String thumb = getDefaultIfNullOrEmpty(leaderboard.getConfig().getEmbedThumbnail(), mainConfig.getDembedThumbnail());



        discordsrvEmbed = new DiscordsrvEmbed();
        discordsrvEmbed.setEmbedTitle(title);
        discordsrvEmbed.setEmbedColour(colour);
        discordsrvEmbed.setEmbedFooter(footer);
        discordsrvEmbed.setEmbedImage(image);
        discordsrvEmbed.setEmbedThumbnail(thumb);
        DescriptionGenerator descriptionGenerator = new DescriptionGenerator(leaderboard);
        discordsrvEmbed.setEmbedDescription(descriptionGenerator.getDescriptionForSrv());

        return discordsrvEmbed;
    }


}
