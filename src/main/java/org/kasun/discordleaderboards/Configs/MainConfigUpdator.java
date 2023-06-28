package org.kasun.discordleaderboards.Configs;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.kasun.discordleaderboards.DiscordLeaderboards;
import org.kasun.discordleaderboards.Utils.MainConfigNewKeysList;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class MainConfigUpdator {
    final DiscordLeaderboards plugin = DiscordLeaderboards.getInstance();
    private FileConfiguration config;
    private File configFile;
    MainConfigNewKeysList mainConfigNewKeysList = new MainConfigNewKeysList();
    public MainConfigUpdator() {
        //getting new kyes list
        List<String> newkeys = mainConfigNewKeysList.getNewKeys();

        // Initialize the configuration file
        File configFile = new File(plugin.getDataFolder(), "config.yml");
        config = YamlConfiguration.loadConfiguration(configFile);

        // Check if keys exist in the config file, add missing keys
        for (String key : newkeys) {
            if (!config.contains(key)) {
                config.set(key, "-"); // Set a default value for the missing key
            }
        }

        // Save the config file
        try {
            config.save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
