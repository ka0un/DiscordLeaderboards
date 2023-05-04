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
        Bukkit.getServer().getPluginManager().getPlugin("DiscordLeaderboards").getConfig().set("leaderboards", leaderboards);
        Bukkit.getServer().getPluginManager().getPlugin("DiscordLeaderboards").saveConfig();

        //creating custom config
        CustomConfig c = new CustomConfig(name);
        c.setup();
        c.save();
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
        c.get().addDefault("embed-footer-icon", "");
        c.get().addDefault("embed-image", "");
        c.get().addDefault("embed-thumbnail", "");
        c.get().addDefault("embed-author-name", "");
        c.get().addDefault("embed-author-url", "");
        c.get().addDefault("embed-author-icon", "");
        c.get().options().copyDefaults(true);
        c.save();

    }

    public static String toString(String name){
        FileConfiguration config = CustomConfig.getFileConfiguration(name);
        String placeholder = config.getString("placeholder");
        int top = config.getInt("top");
        String leaderboardstring = UserData.gettoplistString(placeholder, top);
        return leaderboardstring;
    }

    public static void sendleaderboard(String name) {
        //getting default webhook values
        FileConfiguration mainconfig = Bukkit.getPluginManager().getPlugin("DiscordLeaderboards").getConfig();
        String dwebhookurl = mainconfig.getString("webhook-url");
        String dwebhookAvatarUrl = mainconfig.getString("webhook-avatar-url");
        String dwebhookUserName = mainconfig.getString("webhook-user-name");
        String dembedTitle = mainconfig.getString("embed-title");
        String dembedUrl = mainconfig.getString("embed-url");
        String dembedColour = mainconfig.getString("embed-colour");
        String dembedFooter = mainconfig.getString("embed-footer");
        String dembedFooterIcon = mainconfig.getString("embed-footer-icon");
        String dembedImage = mainconfig.getString("embed-image");
        String dembedThumbnail = mainconfig.getString("embed-thumbnail");
        String dembedAuthorName = mainconfig.getString("embed-author-name");
        String dembedAuthorUrl = mainconfig.getString("embed-author-url");
        String dembedAuthorIcon = mainconfig.getString("embed-author-icon");

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
        String embedFooterIcon = config.getString("embed-footer-icon", dembedFooterIcon);
        String embedImage = config.getString("embed-image", dembedImage);
        String embedThumbnail = config.getString("embed-thumbnail", dembedThumbnail);
        String embedAuthorName = config.getString("embed-author-name", dembedAuthorName);
        String embedAuthorUrl = config.getString("embed-author-url", dembedAuthorUrl);
        String embedAuthorIcon = config.getString("embed-author-icon", dembedAuthorIcon);

        //prepairing webhook
        DiscordWebhook webhook = new DiscordWebhook(webhookurl);

        if (webhookAvatarUrl != null && webhookAvatarUrl != "" && webhookAvatarUrl != "-"){
            webhook.setAvatarUrl(webhookAvatarUrl);
        }

        if (webhookUserName != null && webhookUserName != "" && webhookUserName != "-"){
            webhook.setUsername(webhookUserName);
        }

        DiscordWebhook.EmbedObject embed = new DiscordWebhook.EmbedObject();

        if (embedTitle != null && embedTitle != "" && embedTitle != "-"){
            embed.setTitle(embedTitle);
        }

        if (embedUrl != null && embedUrl != "" && embedUrl != "-"){
            embed.setUrl(embedUrl);
        }

        if (embedColour != null && embedColour != "" && embedColour != "-"){
            embed.setColor(Color.decode(embedColour));
        }

        if (embedFooter != null && embedFooter != "" && embedFooter != "-"){
            embed.setFooter(embedFooter, embedFooterIcon);
        }

        if (embedAuthorName != null && embedAuthorName != "" && embedAuthorName != "-"){
            embed.setAuthor(embedAuthorName, embedAuthorUrl, embedAuthorIcon);
        }

        if (embedThumbnail != null && embedThumbnail != "" && embedThumbnail != "-"){
            embed.setThumbnail(embedThumbnail);
        }

        if (embedThumbnail != null && embedThumbnail != "" && embedThumbnail != "-"){
            embed.setThumbnail(embedThumbnail);
        }

        if (embedImage != null && embedImage != "" && embedImage != "-"){
            embed.setThumbnail(embedImage);
        }

        String description = UserData.gettoplistString(placeholder, top);
        embed.setDescription(description);

        webhook.addEmbed(embed);
        try{
            webhook.execute();
        }catch (IOException e){

        }

    }
}

