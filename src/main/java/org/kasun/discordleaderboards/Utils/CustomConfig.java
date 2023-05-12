package org.kasun.discordleaderboards.Utils;

import jogamp.graph.font.typecast.ot.mac.ResourceFile;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.kasun.discordleaderboards.DiscordLeaderboards;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class CustomConfig {
    private File file;
    private FileConfiguration customFile;
    private String name;

    public CustomConfig(String name) {
        this.name = name;
    }

    public void setup(){
        Plugin plugin = JavaPlugin.getPlugin(DiscordLeaderboards.class);
        file = new File(plugin.getDataFolder() + "/leaderboard/", name + ".yml");

        if (!file.exists()){
            try {
                file.createNewFile();
            }catch (IOException e){

            }
        }
        customFile = YamlConfiguration.loadConfiguration(file);
    }

    public FileConfiguration get(){
        return customFile;
    }

    public static FileConfiguration getFileConfiguration(String fileName) {
        Plugin plugin = JavaPlugin.getPlugin(DiscordLeaderboards.class);
        File file = new File(plugin.getDataFolder() + "/leaderboard/", fileName + ".yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        return config;
    }

    public void save(){
        try {
            customFile.save(file);
        }catch (IOException e){
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "[Dleaderboards] " + ChatColor.RED + "file saving issue , internal plugin issue please contact plugin developer [code : 22]");

        }
    }

    public void reload(){
        customFile = YamlConfiguration.loadConfiguration(file);
    }

}
