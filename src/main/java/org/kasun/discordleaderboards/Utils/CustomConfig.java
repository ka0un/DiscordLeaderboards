package org.kasun.discordleaderboards.Utils;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class CustomConfig {
    private File file;
    private FileConfiguration customFile;
    private String name;

    public CustomConfig(String name) {
        this.name = name;
    }

    public void setup(){
        file = new File(Bukkit.getServer().getPluginManager().getPlugin("DiscordLeaderboards").getDataFolder() + "\\leaderboard\\", name + ".yml");

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
        File file = new File(Bukkit.getPluginManager().getPlugin("DiscordLeaderboards").getDataFolder() + "\\leaderboard\\", fileName + ".yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        return config;
    }

    public void save(){
        try {
            customFile.save(file);
        }catch (IOException e){
            System.out.println("couldent Save File");
        }
    }

    public void reload(){
        customFile = YamlConfiguration.loadConfiguration(file);
    }

}
