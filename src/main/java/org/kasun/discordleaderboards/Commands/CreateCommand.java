package org.kasun.discordleaderboards.Commands;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.kasun.discordleaderboards.Configs.MainConfig;
import org.kasun.discordleaderboards.Database.UserData;
import org.kasun.discordleaderboards.DiscordLeaderboards;
import org.kasun.discordleaderboards.Leaderboard.LeaderboardConfig;
import org.kasun.discordleaderboards.Utils.PlayerUtils;
import org.kasun.discordleaderboards.Leaderboard.Leaderboard;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CreateCommand implements CommandExecutor, TabCompleter {
    private final DiscordLeaderboards plugin = DiscordLeaderboards.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String lable, String[] args) {
        if (args.length == 4) {
            //name top placeholder delay
            if (sender instanceof Player) {
                Player p = (Player) sender;

                if (!p.hasPermission("dl.create") && !p.hasPermission("dl.admin")) {
                    p.sendMessage(ChatColor.AQUA + "[Dleaderboards] " + ChatColor.RED + "No Permission ! [dl.create], [dl.admin]");
                    return true;
                }

                try {
                    double value = Double.parseDouble(PlaceholderAPI.setPlaceholders(p, args[2]));
                    Leaderboard leaderboard = new Leaderboard(args[0], Integer.parseInt(args[1]), args[2], Leaderboard.WebhookDelay.valueOf(args[3]));
                    p.sendMessage(ChatColor.AQUA + "[Dleaderboards] " + ChatColor.GREEN + "Starting Leaderboard Setup!");
                    plugin.reloadConfig();
                    UserData userData = new UserData(p, args[2]);
                    userData.addToDatabase();
                    p.sendMessage(ChatColor.AQUA + "[Dleaderboards] " + ChatColor.YELLOW + "Starting to Sync Offline Player Data... Please Wait!");

                    Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
                        userData.addUserDataToDBAllPlayersThisPlaceholder();
                        Bukkit.getScheduler().runTask(plugin, () -> {
                            p.sendMessage(ChatColor.AQUA + "[Dleaderboards] " + ChatColor.YELLOW + "Synchronization Complete !");
                            p.sendMessage(ChatColor.AQUA + "[Dleaderboards] " + ChatColor.GREEN + "Leaderboard Created!");
                            p.sendMessage(ChatColor.AQUA + "[Dleaderboards] " + ChatColor.GRAY + "you can change the settings from plugins\\DiscordLeaderboards\\leaderboard\\" + args[0] + ".yml");
                        });
                    });



                    //getting random offline player
                    List<OfflinePlayer> players = PlayerUtils.getAllPlayers();
                    players.remove(p);
                    Random random = new Random();
                    int randomIndex = random.nextInt(players.size());
                    OfflinePlayer randomPlayer = players.get(randomIndex);

                    //checking placeholder if they supports offline players
                    try {
                        double value2 = Double.parseDouble(PlaceholderAPI.setPlaceholders(randomPlayer, args[2]));
                    } catch (NumberFormatException ex) {
                        p.sendMessage(ChatColor.AQUA + "[Dleaderboards] " + ChatColor.RED + "[ERROR] " + args[2] + " may not support offline players. [code : 17]");
                    }

                } catch (NumberFormatException e) {
                    p.sendMessage(ChatColor.AQUA + "[Dleaderboards] " + ChatColor.RED + "Placeholder " + args[2] + " Unsupported, Expansion Not Installed or Doesn't output Number value");
                }

            } else {
                Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "[Dleaderboards] " + ChatColor.RED + "You cant use this command here");
            }
        } else {
            if (sender instanceof Player) {
                Player p = (Player) sender;
                p.sendMessage(ChatColor.AQUA + "[Dleaderboards] " + ChatColor.RED + "Wrong Command Usage !");
                p.sendMessage(ChatColor.AQUA + "[Dleaderboards] " + ChatColor.GRAY + "/dl-create name 5 %placeholder% Daily");
            } else {
                Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "[Dleaderboards] " + ChatColor.RED + "You cant use this command here");
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
            suggestions.add("None");
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
