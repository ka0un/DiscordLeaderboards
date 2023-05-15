package org.kasun.discordleaderboards;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import org.kasun.discordleaderboards.utils.*;


public final class DiscordLeaderboards extends JavaPlugin {

    private static DiscordLeaderboards instance;

    @Override
    public void onEnable() {
        instance = this;
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
