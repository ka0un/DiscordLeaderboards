package org.kasun.discordleaderboards;

import org.kasun.discordleaderboards.Configs.ConfigUpdater;
import org.kasun.discordleaderboards.Configs.MainConfig;

public class UpdateManager {
    public String currentVersion = "0.1.3";
    private String version;
    final DiscordLeaderboards plugin  = DiscordLeaderboards.getInstance();

    public UpdateManager() {
        MainConfig mainConfig = new MainConfig();
        version = mainConfig.getPluginVersion();
        if (version.equals("0.0.1") || version.equals("0.1.0") || version.equals("0.1.1") ) {
            plugin.getLogger().info("Updating Leaderboards...");
            ConfigUpdater configUpdater = new ConfigUpdater();
            mainConfig.setPluginVersion(currentVersion);
            mainConfig.saveConfig();
            plugin.getLogger().info("Leaderboards updated successfully! do /dl reload to reload the plugin");
        }
    }



}
