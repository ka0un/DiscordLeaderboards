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

public class ForceLeaderboardSend implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String lable, String[] args) {
        if (!sender.hasPermission("dl.forcesend") && !sender.hasPermission("dl.admin")) {
            sender.sendMessage(ChatColor.AQUA + "[Dleaderboards] " + ChatColor.RED + "No Permission ! [dl.forcesend], [dl.admin]");
            return true;
        }
        if (args.length != 1) {
            sender.sendMessage(ChatColor.AQUA + "[Dleaderboards] " + ChatColor.RED + "Wrong Command Usage !");
            sender.sendMessage(ChatColor.AQUA + "[Dleaderboards] " + ChatColor.GRAY + "/dl-forcesend leaderboard");
            return true;
        }
        CompletableFuture.runAsync(() -> {
            Leaderboard leaderboard = new Leaderboard(args[0]);
            leaderboard.send();
        });
        sender.sendMessage(ChatColor.AQUA + "[Dleaderboards] " + ChatColor.GREEN + "Leaderboard Sent!");
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        MainConfig mainConfig = new MainConfig();
        return mainConfig.getLeaderboardsList();
    }
}
