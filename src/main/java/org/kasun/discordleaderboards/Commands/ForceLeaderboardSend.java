package org.kasun.discordleaderboards.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.kasun.discordleaderboards.Configs.MainConfig;
import org.kasun.discordleaderboards.Leaderboard.Leaderboard;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ForceLeaderboardSend implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String lable, String[] args) {

        if (sender instanceof Player){
            Player p = (Player) sender;
            if (!p.hasPermission("dl.forcesend") && !p.hasPermission("dl.admin")) {
                p.sendMessage( ChatColor.AQUA + "[Dleaderboards] " + ChatColor.RED + "No Permission ! [dl.forcesend], [dl.admin]");
                return true;
            }
        }

        if (args.length == 1){
            CompletableFuture.runAsync(() -> {
                Leaderboard leaderboard = new Leaderboard(args[0]);
                leaderboard.send();
            });
            if (sender instanceof Player){
                Player p = (Player) sender;
                p.sendMessage( ChatColor.AQUA + "[Dleaderboards] " + ChatColor.GREEN + "Leaderboard Sent!");
            }else{
                Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "[Dleaderboards] " + ChatColor.GREEN + "Leaderboard Sent!");
            }
        }else{
            if (sender instanceof Player){
                Player p = (Player) sender;
                p.sendMessage( ChatColor.AQUA + "[Dleaderboards] " + ChatColor.RED + "Wrong Command Usage !");
                p.sendMessage( ChatColor.AQUA + "[Dleaderboards] " + ChatColor.GRAY + "/dl-forcesend leaderboard");
            }else{
                Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "[Dleaderboards] " + ChatColor.RED + "Wrong Command Usage !");
                Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "[Dleaderboards] " + ChatColor.GRAY + "/dl-forcesend leaderboard");
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
