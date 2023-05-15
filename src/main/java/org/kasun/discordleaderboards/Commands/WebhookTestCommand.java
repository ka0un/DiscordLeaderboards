package org.kasun.discordleaderboards.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.kasun.discordleaderboards.DiscordLeaderboards;
import org.kasun.discordleaderboards.Utils.DiscordWebhook;

import java.io.FileNotFoundException;
import java.io.IOException;

public class WebhookTestCommand implements CommandExecutor {
    private final DiscordLeaderboards plugin  = DiscordLeaderboards.getInstance();


    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player){
            Player p = (Player) sender;
            if (!p.hasPermission("dl.testwebhook") && !p.hasPermission("dl.admin")) {
                p.sendMessage( ChatColor.AQUA + "[Dleaderboards] " + ChatColor.RED + "No Permission ! [dl.testwebhook], [dl.admin]");
                return true;
            }
        }

        String url = (String) plugin.getConfig().get("webhook-url");
        DiscordWebhook w = new DiscordWebhook(url);
        DiscordWebhook.EmbedObject embed = new DiscordWebhook.EmbedObject();
        if (url != null && !url.equalsIgnoreCase("-")) {
            embed.setTitle("Dleaderboards");
            embed.setDescription("Your Webhook is Working");
            w.addEmbed(embed);
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
                    p.sendMessage(ChatColor.AQUA + "[Dleaderboards] " + ChatColor.RED + "Invalid Webhook Url [code : 04]");
                } else {
                    Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "[Dleaderboards] " + ChatColor.RED + "Invalid Webhook Url [code : 04]");
                }
            } catch (IOException ee) {
                Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "[Dleaderboards] " + ChatColor.RED + "embed not ready yet, internal plugin issue pleace contact plugin developer [code : 05]");
            }

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

