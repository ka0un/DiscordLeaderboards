package org.kasun.discordleaderboards;

import org.kasun.discordleaderboards.commands.CommandsManager;
import org.kasun.discordleaderboards.configs.ConfigManager;
import org.kasun.discordleaderboards.database.DatabaseManager;
import org.kasun.discordleaderboards.discordsrv.DiscordSrvManager;
import org.kasun.discordleaderboards.listeners.ListenerManager;
import org.kasun.discordleaderboards.schedules.ScheduleManager;

public class MainManager {
    private ConfigManager configManager;
    private DatabaseManager databaseManager;
    private CommandsManager commandsManager;
    private ListenerManager listenerManager;
    private DiscordSrvManager discordSrvManager;
    private ScheduleManager scheduleManager;

    public MainManager() {
        scheduleManager = new ScheduleManager();
        configManager = new ConfigManager();
        databaseManager = new DatabaseManager();
        commandsManager = new CommandsManager();
        listenerManager = new ListenerManager();
        discordSrvManager = new DiscordSrvManager();

    }
}
