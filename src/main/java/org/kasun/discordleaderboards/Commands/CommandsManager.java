package org.kasun.discordleaderboards.Commands;

import org.kasun.discordleaderboards.DiscordLeaderboards;
import org.kasun.discordleaderboards.MainManager;

public class CommandsManager {

    private final MainManager mainManager;

    public CommandsManager(MainManager mainManager) {
        this.mainManager = mainManager;
        DiscordLeaderboards plugin = DiscordLeaderboards.getInstance();
        plugin.getCommand("dl").setExecutor(new DLCommand(this));
    }

    public MainManager getMainManager() {
        return mainManager;
    }
}
