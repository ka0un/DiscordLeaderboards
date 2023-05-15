package org.kasun.discordleaderboards.Configs;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.kasun.discordleaderboards.DiscordLeaderboards;

import java.io.File;
import java.io.IOException;

public class CustomConfig {
    private File file;
    private FileConfiguration customFile;
    private String name;
    private final DiscordLeaderboards plugin  = DiscordLeaderboards.getInstance();
    public CustomConfig(String name) {
        this.name = name;
        file = new File(plugin.getDataFolder() + "/leaderboard/", name + ".yml");
    }

    public void setup(){
        if (!file.exists()){
            try {
                file.createNewFile();
            }catch (IOException e){

            }
        }
        customFile = YamlConfiguration.loadConfiguration(file);
    }

    public FileConfiguration get(){
        customFile = YamlConfiguration.loadConfiguration(file);
        return customFile;
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
