package org.kasun.discordleaderboards.Commands;

import org.kasun.discordleaderboards.DiscordLeaderboards;

public class CommandsManager {

    public CommandsManager() {
        DiscordLeaderboards plugin = DiscordLeaderboards.getInstance();
        plugin.getCommand("dl-testwebhook").setExecutor(new WebhookTestCommand());
        plugin.getCommand("dl-forcesend").setExecutor(new ForceLeaderboardSend());
        plugin.getCommand("dl-create").setExecutor(new CreateCommand());
        plugin.getCommand("dl-view").setExecutor(new ViewCommand());
        plugin.getCommand("dl-syncall").setExecutor(new SyncAllPlayers());
    }
}
