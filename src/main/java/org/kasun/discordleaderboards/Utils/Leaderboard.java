package org.kasun.discordleaderboards.Utils;

import org.bukkit.Bukkit;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class Leaderboard {
    public enum WebhookDelay {Live, Hourly, Daily, Weekly, Monthly}

    private WebhookDelay Timer;

    private String colourHash = "#FCBA04";
    private java.awt.Color embedColour = Color.decode(colourHash);
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
        c.get().addDefault("embed-image", "");
        c.get().addDefault("embed-thumbnail", "");
        c.get().addDefault("embed-author-name", "");
        c.get().addDefault("embed-author-url", "");
        c.get().addDefault("embed-author-icon", "");
        c.get().options().copyDefaults(true);
        c.save();

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
        String embedImage = config.getString("embed-image", dembedImage);
        String embedThumbnail = config.getString("embed-thumbnail", dembedThumbnail);
        String embedAuthorName = config.getString("embed-author-name", dembedAuthorName);
        String embedAuthorUrl = config.getString("embed-author-url", dembedAuthorUrl);
        String embedAuthorIcon = config.getString("embed-author-icon", dembedAuthorIcon);

    }
}

