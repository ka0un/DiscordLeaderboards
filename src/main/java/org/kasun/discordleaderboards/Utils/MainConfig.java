package org.kasun.discordleaderboards.Utils;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.kasun.discordleaderboards.DiscordLeaderboards;

import java.io.File;
import java.util.List;

public class MainConfig {
    private static File configFile = new File(Bukkit.getPluginManager().getPlugin("DiscordLeaderboards").getDataFolder(), "config.yml");
    private static FileConfiguration config = YamlConfiguration.loadConfiguration(configFile);
    private static List<String> leaderboardsList = config.getStringList("leaderboards");
    private static String dwebhookurl = config.getString("webhook-url");
    private static String dwebhookAvatarUrl = config.getString("webhook-avatar-url");
    private static String dwebhookUserName = config.getString("webhook-user-name");
    private static String dembedTitle = config.getString("embed-title");
    private static String dembedUrl = config.getString("embed-url");
    private static String dembedColour = config.getString("embed-colour");
    private static String dembedFooter = config.getString("embed-footer");
    private static String dembedImage = config.getString("embed-image");
    private static String dembedThumbnail = config.getString("embed-thumbnail");
    private static String storageType = config.getString("storage-method");
    private static String mysqlAddress = config.getString("address");
    private static String mysqlDatabase = config.getString("database");
    private static String mysqlUsername = config.getString("username");
    private static String mysqlPassword = config.getString("password");
    private static int scheduleDelayMins = config.getInt("scheduledelaymins");
    private static String pluginVersion = config.getString("pluginversion");
    private static boolean firstTime = config.getBoolean("firsttime");


    //getters


    public static File getConfigFile() {
        return configFile;
    }

    public static FileConfiguration getConfig() {
        return config;
    }

    public static List<String> getLeaderboardsList() {
        return leaderboardsList;
    }

    public static String getDwebhookurl() {
        return dwebhookurl;
    }

    public static String getDwebhookAvatarUrl() {
        return dwebhookAvatarUrl;
    }

    public static String getDwebhookUserName() {
        return dwebhookUserName;
    }

    public static String getDembedTitle() {
        return dembedTitle;
    }

    public static String getDembedUrl() {
        return dembedUrl;
    }

    public static String getDembedColour() {
        return dembedColour;
    }

    public static String getDembedFooter() {
        return dembedFooter;
    }

    public static String getDembedImage() {
        return dembedImage;
    }

    public static String getDembedThumbnail() {
        return dembedThumbnail;
    }

    public static String getStorageType() {
        return storageType;
    }

    public static String getMysqlAddress() {
        return mysqlAddress;
    }

    public static String getMysqlDatabase() {
        return mysqlDatabase;
    }

    public static String getMysqlUsername() {
        return mysqlUsername;
    }

    public static String getMysqlPassword() {
        return mysqlPassword;
    }

    public static int getScheduleDelayMins() {
        return scheduleDelayMins;
    }

    public static String getPluginVersion() {
        return pluginVersion;
    }

    public static boolean isFirstTime() {
        return firstTime;
    }

}
