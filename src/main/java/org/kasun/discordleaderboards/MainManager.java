package org.kasun.discordleaderboards;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.HandlerList;
import org.kasun.discordleaderboards.Commands.CommandsManager;
import org.kasun.discordleaderboards.Configs.ConfigManager;
import org.kasun.discordleaderboards.Database.DatabaseManager;
import org.kasun.discordleaderboards.DiscordSRV.DiscordSrvManager;
import org.kasun.discordleaderboards.Listeners.ListenerManager;
import org.kasun.discordleaderboards.Schedules.ScheduleManager;

public class MainManager {

    private final DiscordLeaderboards plugin = DiscordLeaderboards.getInstance();
    private ConfigManager configManager;
    private DatabaseManager databaseManager;
    private CommandsManager commandsManager;
    private ListenerManager listenerManager;
    private DiscordSrvManager discordSrvManager;
    private ScheduleManager scheduleManager;
    private UpdateManager updateManager;

    public MainManager() {
        scheduleManager = new ScheduleManager();
        configManager = new ConfigManager();
        databaseManager = new DatabaseManager();
        commandsManager = new CommandsManager(this);
        listenerManager = new ListenerManager();
        updateManager = new UpdateManager();

        if (Bukkit.getPluginManager().getPlugin("DiscordSRV") != null){
            discordSrvManager = new DiscordSrvManager();
        }


    }

    public void reload() {
        HandlerList.unregisterAll(plugin);
        Bukkit.getScheduler().cancelTasks(plugin);
        setScheduleManager(new ScheduleManager());
        setConfigManager(new ConfigManager());
        setDatabaseManager(new DatabaseManager());
        setCommandsManager(new CommandsManager(this));
        setListenerManager(new ListenerManager());
        if (Bukkit.getPluginManager().getPlugin("DiscordSRV") != null){
            setDiscordSrvManager(new DiscordSrvManager());
        }
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public void setConfigManager(ConfigManager configManager) {
        this.configManager = configManager;
    }

    public DatabaseManager getDatabaseManager() {
        return databaseManager;
    }

    public void setDatabaseManager(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    public CommandsManager getCommandsManager() {
        return commandsManager;
    }

    public void setCommandsManager(CommandsManager commandsManager) {
        this.commandsManager = commandsManager;
    }

    public ListenerManager getListenerManager() {
        return listenerManager;
    }

    public void setListenerManager(ListenerManager listenerManager) {
        this.listenerManager = listenerManager;
    }

    public DiscordSrvManager getDiscordSrvManager() {
        return discordSrvManager;
    }

    public void setDiscordSrvManager(DiscordSrvManager discordSrvManager) {
        this.discordSrvManager = discordSrvManager;
    }

    public ScheduleManager getScheduleManager() {
        return scheduleManager;
    }

    public void setScheduleManager(ScheduleManager scheduleManager) {
        this.scheduleManager = scheduleManager;
    }
}
