package org.kasun.discordleaderboards.Utils;

import net.kyori.adventure.platform.facet.Facet;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.kasun.discordleaderboards.DiscordLeaderboards;

public class StartMessage {
    public static void sendStartMessage() {
        Plugin plugin = JavaPlugin.getPlugin(DiscordLeaderboards.class);

        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "=========================================");
        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "Discord Leaderboards Plugin Started !");

        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "PlaceholderApi Hooked!");
        }else{
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "Could not find PlaceholderAPI! This plugin is required.");
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "download the plugin from here");
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.UNDERLINE + "https://www.spigotmc.org/resources/placeholderapi.6245/");
            Bukkit.getPluginManager().disablePlugin(plugin);
        }


    }
}
