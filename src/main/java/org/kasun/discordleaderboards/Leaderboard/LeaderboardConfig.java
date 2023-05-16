package org.kasun.discordleaderboards.Leaderboard;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.kasun.discordleaderboards.DiscordLeaderboards;
import org.kasun.discordleaderboards.Configs.CustomConfig;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

public class LeaderboardConfig {
    private final DiscordLeaderboards plugin  = DiscordLeaderboards.getInstance();
    private String name;
    private FileConfiguration config;
    private String placeholder;
    private int top;
    private String delay;
    private boolean refreshOnSent;
    private String webhookurl;
    private String webhookAvatarUrl;
    private String webhookUserName;
    private String embedTitle;
    private List<String> embedDescription;
    private String embedUrl;
    private String embedColour;
    private String embedFooter;
    private String embedImage;
    private String embedThumbnail;
    private CustomConfig customConfig;

    //Constructer for load exciting leaderboardConfig
    public LeaderboardConfig(String name) {
        this.name = name;
        customConfig = new CustomConfig(name);
        config = customConfig.get();
        load();
    }

    //Constructer for create new LeaderboardConfig
    public LeaderboardConfig(String name, String placeholder, int top, String delay) {
        this.name = name;
        this.placeholder = placeholder;
        this.top = top;
        this.delay = delay;
        this.refreshOnSent = false;
        this.webhookurl = "-";
        this.webhookAvatarUrl = "-";
        this.webhookUserName = "-";
        this.embedTitle = "-";
        List<String> description = new ArrayList<>();
        description.add("{toplist}");
        this.embedDescription = description;
        this.embedUrl = "-";
        this.embedColour = "-";
        this.embedFooter = "-";
        this.embedImage = "-";
        this.embedThumbnail = "-";

        customConfig = new CustomConfig(name);
        customConfig.setup();
        config = customConfig.get();
        save();

    }

    //save methode
    public void save() {
        config.set("placeholder", placeholder);
        config.set("top", top);
        config.set("delay", delay);
        config.set("refresh-database-onsent", refreshOnSent);
        config.set("webhook-url", webhookurl);
        config.set("webhook-avatar-url", webhookAvatarUrl);
        config.set("webhook-user-name", webhookUserName);
        config.set("embed-title", embedTitle);
        config.set("embed-description", embedDescription);
        config.set("embed-url", embedUrl);
        config.set("embed-colour", embedColour);
        config.set("embed-footer", embedFooter);
        config.set("embed-image", embedImage);
        config.set("embed-thumbnail", embedThumbnail);

        customConfig.save();
        customConfig.reload();
    }


    //load methode
    public void load(){

        this.placeholder = config.getString("placeholder");
        this.top = config.getInt("top");
        this.delay = config.getString("delay");
        this.refreshOnSent = config.getBoolean("refresh-database-onsent");
        this.webhookurl = config.getString("webhook-url");
        this.webhookAvatarUrl = config.getString("webhook-avatar-url");
        this.webhookUserName = config.getString("webhook-user-name");
        this.embedTitle = config.getString("embed-title");
        this.embedDescription = config.getStringList("embed-description");
        this.embedUrl = config.getString("embed-url");
        this.embedColour = config.getString("embed-colour");
        this.embedFooter = config.getString("embed-footer");
        this.embedImage = config.getString("embed-image");
        this.embedThumbnail = config.getString("embed-thumbnail");
    }

    //getters and setters

    public boolean isRefreshOnSent() {
        return refreshOnSent;
    }

    public void setRefreshOnSent(boolean refreshOnSent) {
        this.refreshOnSent = refreshOnSent;
    }

    public List<String> getEmbedDescription() {
        return embedDescription;
    }

    public void setEmbedDescription(List<String> embedDescription) {
        this.embedDescription = embedDescription;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FileConfiguration getConfig() {
        return config;
    }

    public void setConfig(FileConfiguration config) {
        this.config = config;
    }

    public String getPlaceholder() {
        return placeholder;
    }

    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
    }

    public int getTop() {
        return top;
    }

    public void setTop(int top) {
        this.top = top;
    }

    public String getDelay() {
        return delay;
    }

    public void setDelay(String delay) {
        this.delay = delay;
    }

    public String getWebhookurl() {
        return webhookurl;
    }

    public void setWebhookurl(String webhookurl) {
        this.webhookurl = webhookurl;
    }

    public String getWebhookAvatarUrl() {
        return webhookAvatarUrl;
    }

    public void setWebhookAvatarUrl(String webhookAvatarUrl) {
        this.webhookAvatarUrl = webhookAvatarUrl;
    }

    public String getWebhookUserName() {
        return webhookUserName;
    }

    public void setWebhookUserName(String webhookUserName) {
        this.webhookUserName = webhookUserName;
    }

    public String getEmbedTitle() {
        return embedTitle;
    }

    public void setEmbedTitle(String embedTitle) {
        this.embedTitle = embedTitle;
    }

    public String getEmbedUrl() {
        return embedUrl;
    }

    public void setEmbedUrl(String embedUrl) {
        this.embedUrl = embedUrl;
    }

    public String getEmbedColour() {
        return embedColour;
    }

    public void setEmbedColour(String embedColour) {
        this.embedColour = embedColour;
    }

    public String getEmbedFooter() {
        return embedFooter;
    }

    public void setEmbedFooter(String embedFooter) {
        this.embedFooter = embedFooter;
    }

    public String getEmbedImage() {
        return embedImage;
    }

    public void setEmbedImage(String embedImage) {
        this.embedImage = embedImage;
    }

    public String getEmbedThumbnail() {
        return embedThumbnail;
    }

    public void setEmbedThumbnail(String embedThumbnail) {
        this.embedThumbnail = embedThumbnail;
    }
}
