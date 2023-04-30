package org.kasun.discordleaderboards.Utils;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import java.io.File;
import java.io.IOException;
import java.sql.SQLOutput;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

public class Leaderboard {
    private String name;
    private int top = 5;
    private String placeholder;
    public enum WebhookTimer { Live, Hourly, Daily, Weekly, Monthly }
    private WebhookTimer Timer;
    private boolean isLive;
    private double LiveDelay;

    private static File file;
    private static FileConfiguration customFile;
    private String webhookUrl;
    private String webhookAvatarUrl;
    private String webhookUserName = "DiscordLeaderboards";
    private static String webhookTitle = "DiscordLeaderboards Plugin";
    private static java.awt.Color webhookColour = new java.awt.Color(252, 186, 4);

    public String defaultWebhookUrl = (String) Bukkit.getServer().getPluginManager().getPlugin("DiscordLeaderboards").getConfig().get("webhookurl");

    public Leaderboard(String name, int top, String placeholder) {
        this.name = name;
        this.top = top;
        this.placeholder = placeholder;

        //config constructer

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
        c.get().addDefault("webhookurl", webhookUrl);
        c.get().options().copyDefaults(true);
        c.save();

    }

    public void send(){
        if (webhookUrl == null){
            webhookUrl = defaultWebhookUrl;
        }
        DiscordWebhook webhook = new DiscordWebhook(webhookUrl);
        DiscordWebhook.EmbedObject embed = new DiscordWebhook.EmbedObject();
        embed.setTitle(webhookTitle + name);
        embed.setColor(webhookColour);
        embed.setDescription("kasun    18");
        webhook.addEmbed(embed);
        try{
            System.out.println("debug : sending webhook " + webhookUrl);
            webhook.execute();
        }catch (IOException e){

        }
    }
}
