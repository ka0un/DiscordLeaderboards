package org.kasun.discordleaderboards.Configs;


import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.kasun.discordleaderboards.DiscordLeaderboards;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class MainConfig {
    private final DiscordLeaderboards plugin  = DiscordLeaderboards.getInstance();
    private File configFile;
    private FileConfiguration config;
    private List<String> leaderboardsList;
    private String dwebhookurl;
    private String dwebhookAvatarUrl;
    private String dwebhookUserName;
    private String dembedTitle;
    private String dembedUrl;
    private String dembedColour;
    private String dembedFooter;
    private String dembedImage;
    private String dembedThumbnail;
    private String storageType;
    private String mysqlAddress;
    private String mysqlDatabase;
    private String mysqlUsername;
    private String mysqlPassword;
    private int scheduleDelayMins;
    private String pluginVersion;
    private boolean firstTime;

    //constructer
    public MainConfig() {
        loadConfig();
    }

    //load config
    public void loadConfig() {
        configFile = new File(plugin.getDataFolder(), "config.yml");
        config = YamlConfiguration.loadConfiguration(configFile);
        leaderboardsList = config.getStringList("leaderboards");
        dwebhookurl = config.getString("webhook-url");
        dwebhookAvatarUrl = config.getString("webhook-avatar-url");
        dwebhookUserName = config.getString("webhook-user-name");
        dembedTitle = config.getString("embed-title");
        dembedUrl = config.getString("embed-url");
        dembedColour = config.getString("embed-colour");
        dembedFooter = config.getString("embed-footer");
        dembedImage = config.getString("embed-image");
        dembedThumbnail = config.getString("embed-thumbnail");
        storageType = config.getString("storage-method");
        mysqlAddress = config.getString("address");
        mysqlDatabase = config.getString("database");
        mysqlUsername = config.getString("username");
        mysqlPassword = config.getString("password");
        scheduleDelayMins = config.getInt("scheduledelaymins");
        pluginVersion = config.getString("pluginversion");
        firstTime = config.getBoolean("firsttime");
    }

    //save config
    public void saveConfig() {
        config.set("leaderboards", leaderboardsList);
        config.set("webhook-url", dwebhookurl);
        config.set("webhook-avatar-url", dwebhookAvatarUrl);
        config.set("webhook-user-name", dwebhookUserName);
        config.set("embed-title", dembedTitle);
        config.set("embed-url", dembedUrl);
        config.set("embed-colour", dembedColour);
        config.set("embed-footer", dembedFooter);
        config.set("embed-image", dembedImage);
        config.set("embed-thumbnail", dembedThumbnail);
        config.set("storage-method", storageType);
        config.set("address", mysqlAddress);
        config.set("database", mysqlDatabase);
        config.set("username", mysqlUsername);
        config.set("password", mysqlPassword);
        config.set("scheduledelaymins", scheduleDelayMins);
        config.set("pluginversion", pluginVersion);
        config.set("firsttime", firstTime);

        try {
            config.save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //setters
    public void setConfig(FileConfiguration config) {
        this.config = config;
        try{
            this.config.save(configFile);
        }catch (IOException e){

        }

    }

    public void setLeaderboardsList(List<String> leaderboardsList) {
        this.leaderboardsList = leaderboardsList;
    }

    public void setDwebhookurl(String dwebhookurl) {
        this.dwebhookurl = dwebhookurl;
    }

    public void setDwebhookAvatarUrl(String dwebhookAvatarUrl) {
        this.dwebhookAvatarUrl = dwebhookAvatarUrl;
    }

    public void setDwebhookUserName(String dwebhookUserName) {
        this.dwebhookUserName = dwebhookUserName;
    }

    public void setDembedTitle(String dembedTitle) {
        this.dembedTitle = dembedTitle;
    }

    public void setDembedUrl(String dembedUrl) {
        this.dembedUrl = dembedUrl;
    }

    public void setDembedColour(String dembedColour) {
        this.dembedColour = dembedColour;
    }

    public void setDembedFooter(String dembedFooter) {
        this.dembedFooter = dembedFooter;
    }

    public void setDembedImage(String dembedImage) {
        this.dembedImage = dembedImage;
    }

    public void setDembedThumbnail(String dembedThumbnail) {
        this.dembedThumbnail = dembedThumbnail;
    }

    public void setStorageType(String storageType) {
        this.storageType = storageType;
    }

    public void setMysqlAddress(String mysqlAddress) {
        this.mysqlAddress = mysqlAddress;
    }

    public void setMysqlDatabase(String mysqlDatabase) {
        this.mysqlDatabase = mysqlDatabase;
    }

    public void setMysqlUsername(String mysqlUsername) {
        this.mysqlUsername = mysqlUsername;
    }

    public void setMysqlPassword(String mysqlPassword) {
        this.mysqlPassword = mysqlPassword;
    }

    public void setScheduleDelayMins(int scheduleDelayMins) {
        this.scheduleDelayMins = scheduleDelayMins;
    }

    public void setPluginVersion(String pluginVersion) {
        this.pluginVersion = pluginVersion;
    }

    public void setFirstTime(boolean firstTime) {
        this.firstTime = firstTime;
    }

    //getters
    public File getConfigFile() {
        return configFile;
    }

    public FileConfiguration getConfig() {
        return config;
    }

    public List<String> getLeaderboardsList() {
        return leaderboardsList;
    }

    public String getDwebhookurl() {
        return dwebhookurl;
    }

    public String getDwebhookAvatarUrl() {
        return dwebhookAvatarUrl;
    }

    public String getDwebhookUserName() {
        return dwebhookUserName;
    }

    public String getDembedTitle() {
        return dembedTitle;
    }

    public String getDembedUrl() {
        return dembedUrl;
    }

    public String getDembedColour() {
        return dembedColour;
    }

    public String getDembedFooter() {
        return dembedFooter;
    }

    public String getDembedImage() {
        return dembedImage;
    }

    public String getDembedThumbnail() {
        return dembedThumbnail;
    }

    public String getStorageType() {
        return storageType;
    }

    public String getMysqlAddress() {
        return mysqlAddress;
    }

    public String getMysqlDatabase() {
        return mysqlDatabase;
    }

    public String getMysqlUsername() {
        return mysqlUsername;
    }

    public String getMysqlPassword() {
        return mysqlPassword;
    }

    public int getScheduleDelayMins() {
        return scheduleDelayMins;
    }

    public String getPluginVersion() {
        return pluginVersion;
    }

    public boolean isFirstTime() {
        return firstTime;
    }


}
