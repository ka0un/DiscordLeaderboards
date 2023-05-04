package org.kasun.discordleaderboards.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.kasun.discordleaderboards.Utils.Leaderboard;

import java.util.ArrayList;
import java.util.List;

public class createCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String lable, String[] args) {
        if (args.length == 4){
            //name top placeholder delay
            Leaderboard.createLeaderboard(args[0],Integer.parseInt(args[1]), args[2], Leaderboard.WebhookDelay.valueOf(args[3]));
            if (sender instanceof Player){
                Player p = (Player) sender;
                p.sendMessage( ChatColor.AQUA + "[Dleaderboards] " + ChatColor.GREEN + "Leaderboard Created!");
                p.sendMessage( ChatColor.AQUA + "[Dleaderboards] " + ChatColor.GRAY + "you can change the settings from plugins\\DiscordLeaderboards\\leaderboard\\" + args[0] + ".yml");
            }else{
                Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "[Dleaderboards] " + ChatColor.GREEN + "Leaderboard Created!");
                Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "[Dleaderboards] " + ChatColor.GRAY + "you can change the settings from plugins\\DiscordLeaderboards\\leaderboard\\" + args[0] + ".yml");
            }
        }else{
            if (sender instanceof Player){
                Player p = (Player) sender;
                p.sendMessage( ChatColor.AQUA + "[Dleaderboards] " + ChatColor.RED + "Wrong Command Usage !");
                p.sendMessage( ChatColor.AQUA + "[Dleaderboards] " + ChatColor.GRAY + "/dl-create name 5 %placeholder% Daily");
            }else{
                Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "[Dleaderboards] " + ChatColor.RED + "Wrong Command Usage !");
                Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "[Dleaderboards] " + ChatColor.GRAY + "/dl-create name 5 %placeholder% Daily");
            }
        }


        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> suggestions = new ArrayList<String>();
        if (args.length == 1) {
            suggestions.add("name");
        } else if (args.length == 2) {
            suggestions.add("3");
            suggestions.add("5");
            suggestions.add("10");
            suggestions.add("15");
            suggestions.add("20");
        } else if (args.length == 3) {
            suggestions.add("%placeholder%");
        } else if (args.length == 4) {
            suggestions.add("Live");
            suggestions.add("Hourly");
            suggestions.add("Daily");
            suggestions.add("Weekly");
            suggestions.add("Monthly");
        }

        // Return the list of suggestions
        return suggestions;
    }
}
