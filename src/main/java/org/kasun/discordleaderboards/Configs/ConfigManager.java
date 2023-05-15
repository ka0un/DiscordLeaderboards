package org.kasun.discordleaderboards.Configs;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import org.kasun.discordleaderboards.DiscordLeaderboards;

import java.io.*;
import java.util.List;

public class ConfigManager {
    private final DiscordLeaderboards plugin  = DiscordLeaderboards.getInstance();
    public ConfigManager() {
        plugin.getConfig().options().copyDefaults();
        plugin.saveDefaultConfig();
        MainConfig mainConfig = new MainConfig();
        List<String> itemList = mainConfig.getLeaderboardsList();
        int scheduleDelay = mainConfig.getScheduleDelayMins();
        for (String lb : itemList) {
            CustomConfig c1 = new CustomConfig(lb);
            c1.setup();
            c1.get().options().copyDefaults(true);
            c1.save();
        }
        if (mainConfig.isFirstTime()) {
            copyResourceFile("example.yml", new File(JavaPlugin.getPlugin(DiscordLeaderboards.class).getDataFolder() + "\\leaderboard\\", "example.yml"));
            mainConfig.setFirstTime(false);
            mainConfig.saveConfig();
        }
    }

    public void copyResourceFile(String resourceName, File destination) {
        try (InputStream inputStream = plugin.getResource(resourceName);
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
