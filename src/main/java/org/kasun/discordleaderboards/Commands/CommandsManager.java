package org.kasun.discordleaderboards.Commands;

import org.kasun.discordleaderboards.DiscordLeaderboards;

public class CommandsManager {
    private final DiscordLeaderboards plugin  = DiscordLeaderboards.getInstance();

    public CommandsManager() {
        plugin.getCommand("dl-testwebhook").setExecutor(new WebhookTestCommand());
        plugin.getCommand("dl-forcesend").setExecutor(new ForceLeaderboardSend());
        plugin.getCommand("dl-create").setExecutor(new CreateCommand());
        plugin.getCommand("dl-view").setExecutor(new ViewCommand());
        plugin.getCommand("dl-syncall").setExecutor(new SyncAllPlayers());
    }
}
