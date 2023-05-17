package org.kasun.discordleaderboards;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.kasun.discordleaderboards.Commands.*;
import org.kasun.discordleaderboards.Configs.MainConfig;
import org.kasun.discordleaderboards.Database.Database;
import org.kasun.discordleaderboards.DiscordSRV.DiscordSrvManager;
import org.kasun.discordleaderboards.Listeners.PlayerJoin;
import org.kasun.discordleaderboards.Listeners.PlayerQuit;
import org.kasun.discordleaderboards.Schedules.ScheduleManager;
import org.kasun.discordleaderboards.Utils.*;

import java.io.*;
import java.sql.SQLException;
import java.util.*;


public final class DiscordLeaderboards extends JavaPlugin {

    private static DiscordLeaderboards instance;

    @Override
    public void onEnable() {
        instance = this;
        int pluginId = 18497;
        Metrics metrics = new Metrics(this, pluginId);
        StartMessage.sendStartMessage();
        MainManager mainManager = new MainManager();
    }

    @Override
    public void onDisable() {
        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "[Dleaderboards] " + ChatColor.GRAY + "Plugin ShutDown");
    }

    public static DiscordLeaderboards getInstance() {
        return instance;
    }

}
