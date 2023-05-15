package org.kasun.discordleaderboards.Listeners;

import org.kasun.discordleaderboards.DiscordLeaderboards;

public class ListenerManager {
    private final DiscordLeaderboards plugin  = DiscordLeaderboards.getInstance();

    public ListenerManager() {
        plugin.getServer().getPluginManager().registerEvents(new PlayerJoin(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new PlayerQuit(), plugin);
    }
}
