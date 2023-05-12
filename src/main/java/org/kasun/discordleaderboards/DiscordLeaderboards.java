package org.kasun.discordleaderboards;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.kasun.discordleaderboards.Commands.*;
import org.kasun.discordleaderboards.Database.Database;
import org.kasun.discordleaderboards.DiscordSRV.SrvUtils;
import org.kasun.discordleaderboards.Listeners.PlayerJoin;
import org.kasun.discordleaderboards.Listeners.PlayerQuit;
import org.kasun.discordleaderboards.Schedules.LeaderboardSchedule;
import org.kasun.discordleaderboards.Utils.*;

import java.io.*;
import java.sql.SQLException;
import java.util.*;


public final class DiscordLeaderboards extends JavaPlugin {



    private static String h2url;

    private static DiscordLeaderboards instance;

    @Override
    public void onEnable() {
        StartMessage.sendStartMessage();
        getConfig().options().copyDefaults();
        saveDefaultConfig();
        getCommand("dl-testwebhook").setExecutor(new WebhookTestCommand());
        getCommand("dl-forcesend").setExecutor(new forceLeaderboardSend());
        getCommand("dl-create").setExecutor(new createCommand());
        getCommand("dl-view").setExecutor(new viewCommand());
        getCommand("dl-syncall").setExecutor(new syncAllPlayers());
        getServer().getPluginManager().registerEvents(new PlayerJoin(), this);
        getServer().getPluginManager().registerEvents(new PlayerQuit(), this);



        h2url = "jdbc:h2:file:" + getDataFolder().getAbsolutePath() + "\\database\\database";
        Database.initializeDatabase();

        File configFile = MainConfig.getConfigFile();
        FileConfiguration config = MainConfig.getConfig();
        List<String> itemList = MainConfig.getLeaderboardsList();
        int scheduleDelay = MainConfig.getScheduleDelayMins();

        instance = this;


        for (String lb : itemList) {
            CustomConfig c1 = new CustomConfig(lb);
            c1.setup();
            c1.get().options().copyDefaults(true);
            c1.save();
        }

        //example leaderboard generate
        if (config.getBoolean("firsttime")) {
            copyResourceFile("example.yml", new File(JavaPlugin.getPlugin(DiscordLeaderboards.class).getDataFolder() + "\\leaderboard\\", "example.yml"));
            config.set("firsttime", false);
            try {
                config.save(configFile);
            } catch (IOException e) {
                Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "[Dleaderboards] " + ChatColor.RED + "unable to save file, internal plugin issue please contact plugin developer  [code : 18]");
            }
        }else{
            new BukkitRunnable() {
                @Override
                public void run() {
                    // This code will run every x minutes
                    List<String> itemList = config.getStringList("leaderboards");

                    for (String leaderboard : itemList) {
                        LeaderboardSchedule.runLeaderboardSchedule(leaderboard);
                    }
                }
            }.runTaskTimerAsynchronously(this, 0L, 20L * 60 * scheduleDelay);
        }



        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "=========================================");
        if (Bukkit.getPluginManager().getPlugin("DiscordSRV") != null) {
            SrvUtils.load();
        }

    }





    @Override
    public void onDisable() {
        try{
            Database.getConnection().close();
        }catch (SQLException e){
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "[Dleaderboards] " + ChatColor.RED + "Invalid Webhook Url  [code : 19]");
        }
        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "[Dleaderboards] " + ChatColor.GRAY + "Plugin Disconnected From Database...");
        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "[Dleaderboards] " + ChatColor.GRAY + "Plugin ShutDown");
    }



    public static String getH2url() {
        return h2url;
    }

    public static DiscordLeaderboards getInstance() {
        return instance;
    }

    public void copyResourceFile(String resourceName, File destination) {
        try (InputStream inputStream = getResource(resourceName);
             OutputStream outputStream = new FileOutputStream(destination)) {

            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
        } catch (IOException e) {
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "[Dleaderboards] " + ChatColor.RED + "cannot copy file , internal plugin issue please contact the developer  [code : 20]");
        }
    }

}
