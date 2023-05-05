package org.kasun.discordleaderboards.Utils;

import org.bukkit.Bukkit;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.kasun.discordleaderboards.Database.UserData;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Leaderboard {
    public enum WebhookDelay {Live, Hourly, Daily, Weekly, Monthly}

    private FileConfiguration config;

    public String defaultWebhookUrl = (String) Bukkit.getServer().getPluginManager().getPlugin("DiscordLeaderboards").getConfig().get("webhookurl");


    public static void createLeaderboard(String name, int top, String placeholder, WebhookDelay delay) {
        //adding leaderboard to main config
        List<String> leaderboards = Bukkit.getServer().getPluginManager().getPlugin("DiscordLeaderboards").getConfig().getStringList("leaderboards");
        leaderboards.add(name);
        Bukkit.getServer().getPluginManager().getPlugin("DiscordLeaderboards").getConfig().options().copyDefaults(true);
        Bukkit.getServer().getPluginManager().getPlugin("DiscordLeaderboards").getConfig().set("leaderboards", leaderboards);
        Bukkit.getServer().getPluginManager().getPlugin("DiscordLeaderboards").saveConfig();

        //creating custom config
        CustomConfig c = new CustomConfig(name);
        c.setup();
        c.get().options().copyDefaults(true);
        c.get().addDefault("name", name);
        c.get().addDefault("placeholder", placeholder);
        c.get().addDefault("top", top);
        c.get().addDefault("delay", delay.toString());
        c.get().addDefault("webhook-url", "");
        c.get().addDefault("webhook-avatar-url", "");
        c.get().addDefault("webhook-user-name", "");
        c.get().addDefault("embed-title", "");
        c.get().addDefault("embed-url", "");
        c.get().addDefault("embed-colour", "");
        c.get().addDefault("embed-footer", "");
        c.get().addDefault("embed-image", "");
        c.get().addDefault("embed-thumbnail", "");
        c.save();

    }

    public static void createexampleLeaderboard() {
        //creating custom config
        CustomConfig d = new CustomConfig("example");
        d.setup();
        d.get().options().copyDefaults(true);
        d.get().addDefault("name", "example");
        d.get().addDefault("placeholder", "%player_level%");
        d.get().addDefault("top", 5);
        d.get().addDefault("delay", "Daily");
        d.get().addDefault("webhook-url", "");
        d.get().addDefault("webhook-avatar-url", "");
        d.get().addDefault("webhook-user-name", "");
        d.get().addDefault("embed-title", "");
        d.get().addDefault("embed-url", "");
        d.get().addDefault("embed-colour", "");
        d.get().addDefault("embed-footer", "");
        d.get().addDefault("embed-image", "");
        d.get().addDefault("embed-thumbnail", "");
        d.save();

    }



    public static String toString(String name){
        FileConfiguration config = CustomConfig.getFileConfiguration(name);
        String placeholder = config.getString("placeholder");
        int top = config.getInt("top");
        String leaderboardstring = UserData.gettoplistString(placeholder, top);
        return leaderboardstring;
    }

    public static void sendleaderboard(String name) {
        String dembedTitle = "Leaderboard";
        //getting default webhook values
        FileConfiguration mainconfig = Bukkit.getPluginManager().getPlugin("DiscordLeaderboards").getConfig();
        String dwebhookurl = mainconfig.getString("webhook-url");
        String dwebhookAvatarUrl = mainconfig.getString("webhook-avatar-url");
        String dwebhookUserName = mainconfig.getString("webhook-user-name");
        dembedTitle = mainconfig.getString("embed-title");
        String dembedUrl = mainconfig.getString("embed-url");
        String dembedColour = mainconfig.getString("embed-colour");
        String dembedFooter = mainconfig.getString("embed-footer");
        String dembedImage = mainconfig.getString("embed-image");
        String dembedThumbnail = mainconfig.getString("embed-thumbnail");
        //String dembedAuthorName = mainconfig.getString("embed-author-name");
        //String dembedAuthorUrl = mainconfig.getString("embed-author-url");
        //String dembedAuthorIcon = mainconfig.getString("embed-author-icon");

        //getting leaderboard's values
        FileConfiguration config = CustomConfig.getFileConfiguration(name);
        String placeholder = config.getString("placeholder");
        int top = config.getInt("top");
        String delay = config.getString("delay");
        String webhookurl = config.getString("webhook-url", dwebhookurl);
        String webhookAvatarUrl = config.getString("webhook-avatar-url", dwebhookAvatarUrl);
        String webhookUserName = config.getString("webhook-user-name", dwebhookUserName);
        String embedTitle = config.getString("embed-title", dembedTitle);
        String embedUrl = config.getString("embed-url", dembedUrl);
        String embedColour = config.getString("embed-colour", dembedColour);
        String embedFooter = config.getString("embed-footer", dembedFooter);
        String embedImage = config.getString("embed-image", dembedImage);
        String embedThumbnail = config.getString("embed-thumbnail", dembedThumbnail);
        //String embedAuthorName = config.getString("embed-author-name", dembedAuthorName);
        //String embedAuthorUrl = config.getString("embed-author-url", dembedAuthorUrl);
        //String embedAuthorIcon = config.getString("embed-author-icon", dembedAuthorIcon);

        //checking if leaderboard values are null
        if (webhookurl == null || webhookurl.equals("") || webhookurl.equals("-")){
            webhookurl = dwebhookurl;
        }

        if (webhookAvatarUrl == null || webhookAvatarUrl.equals("") || webhookAvatarUrl.equals("-")){
            webhookAvatarUrl = dwebhookAvatarUrl;
        }
        if (webhookUserName == null || webhookUserName.equals("") || webhookUserName.equals("-")) {
            webhookUserName = dwebhookUserName;
        }
        if (embedTitle == null || embedTitle.equals("") || embedTitle.equals("-")) {
            embedTitle = dembedTitle;
        }
        if (embedUrl == null || embedUrl.equals("") || embedUrl.equals("-")) {
            embedUrl = dembedUrl;
        }
        if (embedColour == null || embedColour.equals("") || embedColour.equals("-")) {
            embedColour = dembedColour;
        }
        if (embedFooter == null || embedFooter.equals("") || embedFooter.equals("-")) {
            embedFooter = dembedFooter;
        }
        if (embedImage == null || embedImage.equals("") || embedImage.equals("-")) {
            embedImage = dembedImage;
        }
        if (embedThumbnail == null || embedThumbnail.equals("") || embedThumbnail.equals("-")) {
            embedThumbnail = dembedThumbnail;
        }
        //if (embedAuthorName == null || embedAuthorName.equals("") || embedAuthorName.equals("-")) {
        //    embedAuthorName = dembedAuthorName;
        //}
        //if (embedAuthorUrl == null || embedAuthorUrl.equals("") || embedAuthorUrl.equals("-")) {
        //    embedAuthorUrl = dembedAuthorUrl;
        //}
        //if (embedAuthorIcon == null || embedAuthorIcon.equals("") || embedAuthorIcon.equals("-")) {
        //    embedAuthorIcon = dembedAuthorIcon;
        //}


        //adding data to webhook
        DiscordWebhook webhook = new DiscordWebhook(webhookurl);


        //checking if default values are not null
        if (webhookAvatarUrl != null && !webhookAvatarUrl.equals("") && !webhookAvatarUrl.equals("-")){
            webhook.setAvatarUrl(webhookAvatarUrl);
            System.out.println("avatar added");
        }

        if (webhookUserName != null && !webhookUserName.equals("") && !webhookUserName.equals("-")){
            webhook.setUsername(webhookUserName);
            System.out.println("username added");
        }

        DiscordWebhook.EmbedObject embed = new DiscordWebhook.EmbedObject();

        if (embedTitle != null && !embedTitle.equals("") && !embedTitle.equals("-")){
            embed.setTitle(embedTitle);
            System.out.println("title added");
        }

        if (embedUrl != null && !embedUrl.equals("") && !embedUrl.equals("-")){
            embed.setUrl(embedUrl);
            System.out.println("embedurl added");
        }

        if (embedColour != null && !embedColour.equals("") && !embedColour.equals("-")){
            embed.setColor(Color.decode(embedColour));
            System.out.println("avatar colour");
        }

        if (embedFooter != null && !embedFooter.equals("") && !embedFooter.equals("-")){
            embed.setFooter(embedFooter);
            System.out.println("embed footer icon added");
        }

        //if (embedAuthorName != null && !embedAuthorName.equals("") && !embedAuthorName.equals("-")){
        //    embed.setAuthor(embedAuthorName, embedAuthorUrl, embedAuthorIcon);
        //    System.out.println("embed footer Auther icon added");
        //}

        if (embedThumbnail != null && !embedThumbnail.equals("") && !embedThumbnail.equals("-")){
            embed.setThumbnail(embedThumbnail);
            System.out.println("embed Thumb added");
        }

        if (embedImage != null && !embedImage.equals("") && !embedImage.equals("-")){
            embed.setImage(embedImage);
            System.out.println("embed image added");
        }

        String description = UserData.gettoplistStringforWebhook(placeholder, top);
        embed.setDescription(description);

        webhook.addEmbed(embed);
        try{
            webhook.execute();
        }catch (IOException e){
            System.out.println(e);

        }
    }
}

