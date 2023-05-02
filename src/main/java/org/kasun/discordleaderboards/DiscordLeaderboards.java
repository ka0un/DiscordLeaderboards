package org.kasun.discordleaderboards;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.kasun.discordleaderboards.Commands.WebhookTestCommand;
import org.kasun.discordleaderboards.Commands.forceLeaderboardSend;
import org.kasun.discordleaderboards.Database.Database;
import org.kasun.discordleaderboards.Listeners.PlayerJoin;
import org.kasun.discordleaderboards.Utils.CustomConfig;
import org.kasun.discordleaderboards.Utils.StartMessage;

import java.sql.SQLOutput;
import java.util.List;

public final class DiscordLeaderboards extends JavaPlugin {

    private static String h2url;

    @Override
    public void onEnable() {
        StartMessage.sendStartMessage();
        getConfig().options().copyDefaults();
        saveDefaultConfig();
        getCommand("webhooktest").setExecutor(new WebhookTestCommand());
        getCommand("forcesend").setExecutor(new forceLeaderboardSend());
        getServer().getPluginManager().registerEvents(new PlayerJoin(), this);

        h2url = "jdbc:h2:file:" + getDataFolder().getAbsolutePath() + "\\database\\database";
        Database.initializeDatabase();


        FileConfiguration config = getConfig();
        List<String> itemList = config.getStringList("leaderboards");

        //templates
        if (config.getBoolean("firsttime")) {
            String lname = "example";
            CustomConfig c2 = new CustomConfig(lname);
            c2.setup();
            c2.get().addDefault("name", lname);
            c2.get().addDefault("placeholder", "%player_level%");
            c2.get().addDefault("top", 5);
            c2.get().options().copyDefaults(true);
            c2.save();

            String lname2 = "example2";
            CustomConfig c3 = new CustomConfig(lname2);
            c3.setup();
            c3.get().addDefault("name", lname2);
            c3.get().addDefault("placeholder", "%player_level%");
            c3.get().addDefault("top", 10);
            c3.get().options().copyDefaults(true);
            c3.save();

            config.set("firsttime", false);

            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "=========================================");


        }

        for (String lb : itemList) {
            CustomConfig c1 = new CustomConfig(lb);
            c1.setup();
            c1.get().options().copyDefaults(true);
            c1.save();
        }

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static String getH2url() {
        return h2url;
    }
}
