package org.kasun.discordleaderboards;

import org.bukkit.Bukkit;
import org.kasun.discordleaderboards.Commands.CommandsManager;
import org.kasun.discordleaderboards.Configs.ConfigManager;
import org.kasun.discordleaderboards.Database.DatabaseManager;
import org.kasun.discordleaderboards.DiscordSRV.DiscordSrvManager;
import org.kasun.discordleaderboards.Listeners.ListenerManager;
import org.kasun.discordleaderboards.Schedules.ScheduleManager;

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
        if (Bukkit.getPluginManager().getPlugin("DiscordSRV") != null){
            discordSrvManager = new DiscordSrvManager();
        }
    }
}
