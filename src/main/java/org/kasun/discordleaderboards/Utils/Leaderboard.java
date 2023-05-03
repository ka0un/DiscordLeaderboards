package org.kasun.discordleaderboards.Utils;

import org.bukkit.Bukkit;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

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
        c.get().addDefault("delay", delay);
        c.get().addDefault("webhookurl", "-");
        c.get().addDefault("webhook-avatar-url", "-");
        c.get().addDefault("webhook-user-name", "-");
        c.get().addDefault("embed-title", "-");
        c.get().addDefault("embed-url", "-");
        c.get().addDefault("embed-colour", "-");
        c.get().addDefault("embed-footer", "-");
        c.get().addDefault("embed-image", "-");
        c.get().addDefault("embed-thumbnail", "-");
        c.get().addDefault("embed-auther-name", "-");
        c.get().addDefault("embed-auther-url", "-");
        c.get().addDefault("embed-auther-icon", "-");
        c.get().options().copyDefaults(true);
        c.save();

    }

    public static void sendleaderboard(String name) {
        FileConfiguration config = CustomConfig.getFileConfiguration(name);
        String placeholder = config.getString("placeholder");
        int top = config.getInt("top");
        String delay = config.getString("delay");
        String webhookurl = config.getString("webhookurl");
        String webhookAvatarUrl = config.getString("webhook-avatar-url");
        String webhookUserName = config.getString("webhook-user-name", "-");
        String embedTitle = config.getString("embed-title", "-");
        String embedUrl = config.getString("embed-url", "-");
        String embedColour = config.getString("embed-colour", "-");
        String embedFooter = config.getString("embed-footer", "-");
        String embedImage = config.getString("embed-image", "-");
        String embedThumbnail = config.getString("embed-thumbnail", "-");
        String embedAuthorName = config.getString("embed-auther-name", "-");
        String embedAuthorUrl = config.getString("embed-auther-url", "-");
        String embedAuthorIcon = config.getString("embed-auther-icon", "-");


    }
}

