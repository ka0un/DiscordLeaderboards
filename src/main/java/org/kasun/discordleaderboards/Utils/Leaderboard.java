package org.kasun.discordleaderboards.Utils;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.kasun.discordleaderboards.Database.UserData;
import org.kasun.discordleaderboards.DiscordLeaderboards;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class Leaderboard {
    public enum WebhookDelay {Live, Hourly, Daily, Weekly, Monthly}
    private String name;
    private FileConfiguration mainconfig = Bukkit.getPluginManager().getPlugin("DiscordLeaderboards").getConfig();
    private String dembedTitle = mainconfig.getString("embed-title");
    private String dwebhookurl = mainconfig.getString("webhook-url");
    private String dwebhookAvatarUrl = mainconfig.getString("webhook-avatar-url");
    private String dwebhookUserName = mainconfig.getString("webhook-user-name");
    private String dembedUrl = mainconfig.getString("embed-url");
    private String dembedColour = mainconfig.getString("embed-colour");
    private String dembedFooter = mainconfig.getString("embed-footer");
    private String dembedImage = mainconfig.getString("embed-image");
    private String dembedThumbnail = mainconfig.getString("embed-thumbnail");

    private FileConfiguration config;
    private String placeholder;
    private int top;
    private String delay;
    private String webhookurl;
    private String webhookAvatarUrl;
    private String webhookUserName;
    private String embedTitle;
    private String embedUrl;
    private String embedColour;
    private String embedFooter;
    private String embedImage;
    private String embedThumbnail;

    public Leaderboard(String name) {
        this.name = name;
        this.config = CustomConfig.getFileConfiguration(name);
        this.placeholder = config.getString("placeholder");
        this.top = config.getInt("top");
        this.delay = config.getString("delay");
        this.webhookurl = config.getString("webhook-url", dwebhookurl);
        this.webhookAvatarUrl = config.getString("webhook-avatar-url", dwebhookAvatarUrl);
        this.webhookUserName = config.getString("webhook-user-name", dwebhookUserName);
        this.embedTitle = config.getString("embed-title", dembedTitle);
        this.embedUrl = config.getString("embed-url", dembedUrl);
        this.embedColour = config.getString("embed-colour", dembedColour);
        this.embedFooter = config.getString("embed-footer", dembedFooter);
        this.embedImage = config.getString("embed-image", dembedImage);
        this.embedThumbnail = config.getString("embed-thumbnail", dembedThumbnail);

        //null removers (just replacing nulls with default values)
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
    }

    public static void createLeaderboard(String name, int top, String placeholder, WebhookDelay delay) {
        //adding leaderboard to main config

        File configFile = new File(Bukkit.getPluginManager().getPlugin("DiscordLeaderboards").getDataFolder(), "config.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(configFile);
        List<String> leaderboards = config.getStringList("leaderboards");
        leaderboards.add(name);
        config.set("leaderboards", leaderboards);
        config.addDefault("new_key", "default_value");
        try {
            config.save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }


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



    public static String toString(String name){
        FileConfiguration config = CustomConfig.getFileConfiguration(name);
        String placeholder = config.getString("placeholder");
        int top = config.getInt("top");
        String leaderboardstring = UserData.gettoplistString(placeholder, top);
        return leaderboardstring;
    }

    public static void sendleaderboard(String name) {

        String dembedTitle = "Leaderboard";
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
            System.out.println("colour added");
        }

        if (embedFooter != null && !embedFooter.equals("") && !embedFooter.equals("-")){
            embed.setFooter(embedFooter);
            System.out.println("embed footer icon added");
        }


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
            e.printStackTrace();
        }

    }
    public static String getDelay(String leaderboard){
        FileConfiguration config = CustomConfig.getFileConfiguration(leaderboard);
        String delay = config.getString("delay");
        return delay;
    }



    //all Getters


    public String getName() {
        return name;
    }

    public FileConfiguration getMainconfig() {
        return mainconfig;
    }

    public FileConfiguration getConfig() {
        return config;
    }

    public String getPlaceholder() {
        return placeholder;
    }

    public int getTop() {
        return top;
    }

    public String getDelay() {
        return delay;
    }

    public String getWebhookurl() {
        return webhookurl;
    }

    public String getWebhookAvatarUrl() {
        return webhookAvatarUrl;
    }

    public String getWebhookUserName() {
        return webhookUserName;
    }

    public String getEmbedTitle() {
        return embedTitle;
    }

    public String getEmbedUrl() {
        return embedUrl;
    }

    public String getEmbedColour() {
        return embedColour;
    }

    public String getEmbedFooter() {
        return embedFooter;
    }

    public String getEmbedImage() {
        return embedImage;
    }

    public String getEmbedThumbnail() {
        return embedThumbnail;
    }
    public String getDescription() {
        String description = UserData.gettoplistStringforWebhook(placeholder, top);
        return description;
    }

}

