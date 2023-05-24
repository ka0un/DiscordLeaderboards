package org.kasun.discordleaderboards.Configs;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.kasun.discordleaderboards.DiscordLeaderboards;

import java.io.File;
import java.io.IOException;

public class CustomConfig {
    private final File file;
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
            }catch (IOException ignored){
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
            plugin.getLogger().severe("file saving issue , internal plugin issue please contact plugin developer [code : 22]");
        }
    }

    public void reload(){
        customFile = YamlConfiguration.loadConfiguration(file);
    }

}
