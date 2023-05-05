package org.kasun.discordleaderboards;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.kasun.discordleaderboards.Commands.WebhookTestCommand;
import org.kasun.discordleaderboards.Commands.createCommand;
import org.kasun.discordleaderboards.Commands.forceLeaderboardSend;
import org.kasun.discordleaderboards.Commands.viewCommand;
import org.kasun.discordleaderboards.Database.Database;
import org.kasun.discordleaderboards.Listeners.PlayerJoin;
import org.kasun.discordleaderboards.Utils.CustomConfig;
import org.kasun.discordleaderboards.Utils.Leaderboard;
import org.kasun.discordleaderboards.Utils.StartMessage;

import java.sql.SQLException;
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
        getCommand("dl-view").setExecutor(new viewCommand());
        getServer().getPluginManager().registerEvents(new PlayerJoin(), this);

        h2url = "jdbc:h2:file:" + getDataFolder().getAbsolutePath() + "\\database\\database";
        Database.initializeDatabase();


        FileConfiguration config = getConfig();
        List<String> itemList = config.getStringList("leaderboards");


        for (String lb : itemList) {
            CustomConfig c1 = new CustomConfig(lb);
            c1.setup();
            c1.get().options().copyDefaults(true);
            c1.save();
        }

        //example leaderboard generate
        if (config.getBoolean("firsttime")) {
            Leaderboard.createexampleLeaderboard();
            config.set("firsttime", false);
        }

        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "=========================================");

    }

    @Override
    public void onDisable() {
        try{
            Database.getConnection().close();
        }catch (SQLException e){

        }
        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "[Dleaderboards] " + ChatColor.GRAY + "Plugin Disconnected From Database...");
        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "[Dleaderboards] " + ChatColor.GRAY + "Plugin ShutDown");
    }

    public static String getH2url() {
        return h2url;
    }
}
