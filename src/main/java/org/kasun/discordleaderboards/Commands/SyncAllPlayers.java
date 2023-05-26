package org.kasun.discordleaderboards.Commands;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.kasun.discordleaderboards.Configs.MainConfig;
import org.kasun.discordleaderboards.Database.UserData;
import org.kasun.discordleaderboards.DiscordLeaderboards;
import org.kasun.discordleaderboards.Leaderboard.LeaderboardConfig;
import org.kasun.discordleaderboards.Utils.PlayerUtils;

import java.util.List;
import java.util.Random;

public class SyncAllPlayers implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player){
            Player p = (Player) sender;

            if (!p.hasPermission("dl.syncall") && !p.hasPermission("dl.admin")) {
                p.sendMessage( ChatColor.AQUA + "[Dleaderboards] " + ChatColor.RED + "No Permission ! [dl.syncall], [dl.admin]");
                return true;
            }

            p.sendMessage( ChatColor.AQUA + "[Dleaderboards] " + ChatColor.YELLOW + "Synchronization Starting...");
            UserData userData = new UserData();
            Plugin plugin = JavaPlugin.getPlugin(DiscordLeaderboards.class);
            Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
                userData.addUserDataToDBAllPlayersAllPlaceholders();
                Bukkit.getScheduler().runTask(plugin, () -> {
                    p.sendMessage( ChatColor.AQUA + "[Dleaderboards] " + ChatColor.YELLOW + "Synchronization Complete !");
                });
            });

            //getting random offline player
            List<OfflinePlayer> players = PlayerUtils.getAllPlayers();
            players.remove(p);
            Random random = new Random();
            int randomIndex = random.nextInt(players.size());
            OfflinePlayer randomPlayer = players.get(randomIndex);

            //checking placeholders if they supports offline players
            MainConfig mainConfig = new MainConfig();
            List<String> lblist = mainConfig.getLeaderboardsList();
            for (String lbname : lblist) {
                LeaderboardConfig leaderboardConfig = new LeaderboardConfig(lbname);
                String ph = leaderboardConfig.getPlaceholder();
                try {
                    double value2 = Double.parseDouble(PlaceholderAPI.setPlaceholders(randomPlayer, ph));
                } catch (NumberFormatException ex) {
                    p.sendMessage(ChatColor.AQUA + "[Dleaderboards] " + ChatColor.RED + "[ERROR] " + ph + " may not support offline players. [code : 17]");
                }
            }

        }else{
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "[Dleaderboards] " + ChatColor.RED + "you cant use that command here.");
        }

        return true;
    }
}
