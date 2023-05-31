package org.kasun.discordleaderboards.Configs;

import org.bukkit.configuration.file.YamlConfiguration;
import org.kasun.discordleaderboards.DiscordLeaderboards;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class ConfigUpdater {
    public ConfigUpdater() {
        final DiscordLeaderboards plugin = DiscordLeaderboards.getInstance();

        int i = 0;

        for (String lb : plugin.getConfig().getStringList("leaderboards")) {
            // Load the old YAML file
            File oldConfigFile = new File(plugin.getDataFolder(), "leaderboard/" + lb + ".yml");
            YamlConfiguration oldConfig = YamlConfiguration.loadConfiguration(oldConfigFile);

            // Load the new YAML file from plugin resources
            InputStream exampleStream = plugin.getResource("example.yml");
            Map<String, Object> newConfig = loadYaml(exampleStream);

            // Merge the new configuration into the old configuration
            mergeConfigurations(oldConfig, newConfig);

            // Save the updated configuration to the file
            saveYaml(oldConfig, oldConfigFile);

            i++;
        }

        if (i > 0) {
            plugin.getLogger().info("Updated " + i + " leaderboards.");
        }
    }

    private static Map<String, Object> loadYaml(InputStream inputStream) {
        try (InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8)) {
            Yaml yaml = new Yaml();
            return (Map<String, Object>) yaml.load(reader);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void mergeConfigurations(YamlConfiguration oldConfig, Map<String, Object> newConfig) {
        if (oldConfig != null && newConfig != null) {
            for (Map.Entry<String, Object> entry : newConfig.entrySet()) {
                oldConfig.set(entry.getKey(), entry.getValue());
            }
        }
    }

    private static void saveYaml(YamlConfiguration config, File configFile) {
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(configFile), StandardCharsets.UTF_8))) {
            DumperOptions options = new DumperOptions();
            options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);

            Yaml yaml = new Yaml(options);
            yaml.dump(config.getValues(true), writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


