package org.kasun.discordleaderboards;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.kasun.discordleaderboards.Commands.WebhookTestCommand;
import org.kasun.discordleaderboards.Commands.createCommand;
import org.kasun.discordleaderboards.Commands.forceLeaderboardSend;
import org.kasun.discordleaderboards.Database.Database;
import org.kasun.discordleaderboards.Listeners.PlayerJoin;
import org.kasun.discordleaderboards.Utils.CustomConfig;
import org.kasun.discordleaderboards.Utils.Leaderboard;
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
        getCommand("dl-testwebhook").setExecutor(new WebhookTestCommand());
        getCommand("dl-forcesend").setExecutor(new forceLeaderboardSend());
        getCommand("dl-create").setExecutor(new createCommand());
        getServer().getPluginManager().registerEvents(new PlayerJoin(), this);

        h2url = "jdbc:h2:file:" + getDataFolder().getAbsolutePath() + "\\database\\database";
        Database.initializeDatabase();


        FileConfiguration config = getConfig();
        List<String> itemList = config.getStringList("leaderboards");

        //example leaderboard generate
        if (config.getBoolean("firsttime")) {
            Leaderboard.createLeaderboard("example", 5, "%player_level%", Leaderboard.WebhookDelay.Daily);
            config.set("firsttime", false);
        }

        for (String lb : itemList) {
            CustomConfig c1 = new CustomConfig(lb);
            c1.setup();
            c1.get().options().copyDefaults(true);
            c1.save();
        }
        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "=========================================");

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static String getH2url() {
        return h2url;
    }
}
