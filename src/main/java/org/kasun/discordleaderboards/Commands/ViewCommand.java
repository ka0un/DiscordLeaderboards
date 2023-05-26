package org.kasun.discordleaderboards.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.kasun.discordleaderboards.Configs.MainConfig;
import org.kasun.discordleaderboards.Leaderboard.Leaderboard;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ViewCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("dl.view") && !sender.hasPermission("dl.admin")) {
            sender.sendMessage(ChatColor.AQUA + "[Dleaderboards] " + ChatColor.RED + "No Permission ! [dl.view], [dl.admin]");
            return true;
        }
        if (args.length != 1) {
            sender.sendMessage(ChatColor.AQUA + "[Dleaderboards] " + ChatColor.RED + "Wrong Command Usage !");
            sender.sendMessage(ChatColor.AQUA + "[Dleaderboards] " + ChatColor.GRAY + "/dl-view leaderboard");
            return true;
        }
        CompletableFuture.runAsync(() -> {
            Leaderboard leaderboard = new Leaderboard(args[0]);
            String leaderboardstring = leaderboard.toString();

            sender.sendMessage(ChatColor.GRAY + leaderboardstring);
        });
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        MainConfig mainConfig = new MainConfig();
        return mainConfig.getLeaderboardsList();
    }
}
