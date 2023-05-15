package org.kasun.discordleaderboards.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.kasun.discordleaderboards.configs.MainConfig;
import org.kasun.discordleaderboards.leaderboard.Leaderboard;

import java.util.List;

public class ViewCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String lable, String[] args) {

        if (sender instanceof Player){
            Player p = (Player) sender;
            if (!p.hasPermission("dl.view") && !p.hasPermission("dl.admin")) {
                p.sendMessage( ChatColor.AQUA + "[Dleaderboards] " + ChatColor.RED + "No Permission ! [dl.view], [dl.admin]");
                return true;
            }
        }

        if (args.length == 1){
            Leaderboard leaderboard = new Leaderboard(args[0]);
            String leaderboardstring = leaderboard.toString();

            if (sender instanceof Player){
                Player p = (Player) sender;
                p.sendMessage( ChatColor.GRAY + leaderboardstring);
            }else{
                Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GRAY + leaderboardstring);
            }

        }else{
            if (sender instanceof Player){
                Player p = (Player) sender;
                p.sendMessage( ChatColor.AQUA + "[Dleaderboards] " + ChatColor.RED + "Wrong Command Usage !");
                p.sendMessage( ChatColor.AQUA + "[Dleaderboards] " + ChatColor.GRAY + "/dl-view leaderboard");
            }else{
                Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "[Dleaderboards] " + ChatColor.RED + "Wrong Command Usage !");
                Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "[Dleaderboards] " + ChatColor.GRAY + "/dl-view leaderboard");
            }
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        MainConfig mainConfig = new MainConfig();
        List<String> suggestions = mainConfig.getLeaderboardsList();
        return suggestions;
    }
}
