package org.kasun.discordleaderboards.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.kasun.discordleaderboards.DiscordLeaderboards;
import org.kasun.discordleaderboards.Utils.DiscordWebhook;

import java.io.FileNotFoundException;
import java.io.IOException;

public class WebhookTestCommand implements CommandExecutor {
    Plugin plugin = JavaPlugin.getPlugin(DiscordLeaderboards.class);


    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {


        String url = (String) plugin.getConfig().get("webhookurl");
        DiscordWebhook w = new DiscordWebhook(url);
        if (url != null && !url.equalsIgnoreCase("-")) {
            w.setContent("Your Webhook is Working");
            System.out.println(url);
            if (sender instanceof Player) {
                Player p = (Player) sender;
                p.sendMessage( ChatColor.AQUA + "[Dleaderboards] " + ChatColor.GRAY + "Sending Webhook Message....");
            } else {
                Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "[Dleaderboards] " + ChatColor.GRAY + "Sending Webhook Message....");
            }
            try {
                w.execute();
            } catch (FileNotFoundException e) {
                if (sender instanceof Player) {
                    Player p = (Player) sender;
                    p.sendMessage(ChatColor.AQUA + "[Dleaderboards] " + ChatColor.RED + "Invalid Webhook Url");
                } else {
                    Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "[Dleaderboards] " + ChatColor.RED + "Invalid Webhook Url");
                }
            } catch (IOException ee) {}

        }else{
            if (sender instanceof Player) {
                Player p = (Player) sender;
                p.sendMessage(ChatColor.AQUA + "[Dleaderboards] " + ChatColor.RED + "Webhook Url not Configured");
            } else {
                Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "[Dleaderboards] " + ChatColor.RED + "Webhook Url not Configured");
            }
        }
        return true;
    }
}

