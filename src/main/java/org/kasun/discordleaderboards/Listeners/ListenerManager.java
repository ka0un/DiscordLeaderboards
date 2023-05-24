package org.kasun.discordleaderboards.Listeners;

import org.kasun.discordleaderboards.DiscordLeaderboards;

public class ListenerManager {

    public ListenerManager() {
        DiscordLeaderboards plugin = DiscordLeaderboards.getInstance();
        plugin.getServer().getPluginManager().registerEvents(new PlayerJoin(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new PlayerQuit(), plugin);
    }
}
