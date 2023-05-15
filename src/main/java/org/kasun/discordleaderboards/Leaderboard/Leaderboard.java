package org.kasun.discordleaderboards.Leaderboard;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.kasun.discordleaderboards.DiscordLeaderboards;
import org.kasun.discordleaderboards.Utils.DiscordWebhook;
import org.kasun.discordleaderboards.Configs.MainConfig;

import java.awt.*;
import java.io.IOException;
import java.util.List;

public class Leaderboard {
    private final DiscordLeaderboards plugin  = DiscordLeaderboards.getInstance();
    public enum WebhookDelay {Live, Hourly, Daily, Weekly, Monthly, None}
    private LeaderboardConfig leaderboardConfig;
    private String name;
    private TopList topList;

    //constructer for load leaderboard
    public Leaderboard(String name) {
        this.name = name;
        leaderboardConfig = new LeaderboardConfig(name);
        topList = new TopList(leaderboardConfig);
    }

    //constructer for create leaderboard
    public Leaderboard(String name, int top, String placeholder, WebhookDelay delay){
        this.name = name;

        MainConfig mainConfig = new MainConfig();
        List<String> leaderboards = mainConfig.getLeaderboardsList();
        leaderboards.add(name);
        mainConfig.setLeaderboardsList(leaderboards);
        mainConfig.saveConfig();

        leaderboardConfig = new LeaderboardConfig(name, placeholder, top, delay.toString()); //creating LeaderboardConfig
        topList = new TopList(leaderboardConfig);
    }

    public String toString(){
        String placeholder = leaderboardConfig.getPlaceholder();
        int top = leaderboardConfig.getTop();
        String leaderboardstring = topList.getTopListAsString();
        return leaderboardstring;
    }

    public void send() {

        //getting default values from main config
        MainConfig mainConfig = new MainConfig();

        String dembedTitle = mainConfig.getDembedTitle();
        String dwebhookurl = mainConfig.getDwebhookurl();
        String dwebhookAvatarUrl = mainConfig.getDwebhookAvatarUrl();
        String dwebhookUserName = mainConfig.getDwebhookUserName();
        String dembedUrl = mainConfig.getDembedUrl();
        String dembedColour = mainConfig.getDembedColour();
        String dembedFooter = mainConfig.getDembedFooter();
        String dembedImage = mainConfig.getDembedImage();
        String dembedThumbnail = mainConfig.getDembedThumbnail();

        String dembedDescription = "No Player Data Found !";

        //getting leaderboard's values from leaderboard config
        String placeholder = leaderboardConfig.getPlaceholder();
        int top = leaderboardConfig.getTop();
        String delay = leaderboardConfig.getDelay();
        String webhookurl = leaderboardConfig.getWebhookurl();
        String webhookAvatarUrl = leaderboardConfig.getWebhookAvatarUrl();
        String webhookUserName = leaderboardConfig.getWebhookUserName();
        String embedTitle = leaderboardConfig.getEmbedTitle();
        String embedUrl = leaderboardConfig.getEmbedUrl();
        String embedColour = leaderboardConfig.getEmbedColour();
        String embedFooter = leaderboardConfig.getEmbedFooter();
        String embedImage = leaderboardConfig.getEmbedImage();
        String embedThumbnail = leaderboardConfig.getEmbedThumbnail();

        //checking if leaderboard config values are null, if it is getting default values from main config
        webhookurl = getOrDefault(webhookurl, dwebhookurl);
        webhookAvatarUrl = getOrDefault(webhookAvatarUrl, dwebhookAvatarUrl);
        webhookUserName = getOrDefault(webhookUserName, dwebhookUserName);
        embedTitle = getOrDefault(embedTitle, dembedTitle);
        embedUrl = getOrDefault(embedUrl, dembedUrl);
        embedColour = getOrDefault(embedColour, dembedColour);
        embedFooter = getOrDefault(embedFooter, dembedFooter);
        embedImage = getOrDefault(embedImage, dembedImage);
        embedThumbnail = getOrDefault(embedThumbnail, dembedThumbnail);

        DiscordWebhook webhook = new DiscordWebhook(webhookurl);

        //checking if final values are not null and them to discord webhook
        if (webhookAvatarUrl != null && !webhookAvatarUrl.equals("") && !webhookAvatarUrl.equals("-")){
            webhook.setAvatarUrl(webhookAvatarUrl);
        }

        if (webhookUserName != null && !webhookUserName.equals("") && !webhookUserName.equals("-")){
            webhook.setUsername(webhookUserName);
        }

        DiscordWebhook.EmbedObject embed = new DiscordWebhook.EmbedObject();

        if (embedTitle != null && !embedTitle.equals("") && !embedTitle.equals("-")){
            embed.setTitle(embedTitle);
        }

        if (embedUrl != null && !embedUrl.equals("") && !embedUrl.equals("-")){
            embed.setUrl(embedUrl);
        }

        if (embedColour != null && !embedColour.equals("") && !embedColour.equals("-")){
            embed.setColor(Color.decode(embedColour));
        }

        if (embedFooter != null && !embedFooter.equals("") && !embedFooter.equals("-")){
            embed.setFooter(embedFooter);
        }


        if (embedThumbnail != null && !embedThumbnail.equals("") && !embedThumbnail.equals("-")){
            embed.setThumbnail(embedThumbnail);

        }

        if (embedImage != null && !embedImage.equals("") && !embedImage.equals("-")){
            embed.setImage(embedImage);

        }


        String description = topList.getTopListAsStringForWebhook();

        if (description == null || description.equals("") || dembedDescription.equals("-")) {
            description = dembedDescription;
        }

        embed.setDescription(description);

        webhook.addEmbed(embed);
        try{
            webhook.execute();
        }catch (IOException e){
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "[Dleaderboards] " + ChatColor.RED + "Webhook issue detected, Check You Config Files  [code : 24]");
        }

    }

    //methode for select default value or leaderboard value
    private String getOrDefault(String value, String defaultValue) {
        return (value == null || value.isEmpty() || value.equals("-")) ? defaultValue : value;
    }


    //all Getters
    public LeaderboardConfig getConfig() {
        return leaderboardConfig;
    }

    public String getName() {
        return name;
    }

    public TopList getTopList() {
        return topList;
    }
}

