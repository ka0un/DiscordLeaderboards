package org.kasun.discordleaderboards.Commands;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.kasun.discordleaderboards.Database.UserData;
import org.kasun.discordleaderboards.DiscordLeaderboards;
import org.kasun.discordleaderboards.Utils.AllPlayers;
import org.kasun.discordleaderboards.Utils.CustomConfig;
import org.kasun.discordleaderboards.Utils.Leaderboard;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CreateCommand implements CommandExecutor, TabCompleter {
    Plugin plugin = JavaPlugin.getPlugin(DiscordLeaderboards.class);
    @Override
    public boolean onCommand(CommandSender sender, Command command, String lable, String[] args) {
        if (args.length == 4){
            //name top placeholder delay
            if (sender instanceof Player){
                Player p = (Player) sender;

                if (!p.hasPermission("dl.create") && !p.hasPermission("dl.admin")) {
                    p.sendMessage( ChatColor.AQUA + "[Dleaderboards] " + ChatColor.RED + "No Permission ! [dl.create], [dl.admin]");
                    return true;
                }

                try{
                    double value = Double.parseDouble(PlaceholderAPI.setPlaceholders(p, args[2]));
                    Leaderboard.createLeaderboard(args[0],Integer.parseInt(args[1]), args[2], Leaderboard.WebhookDelay.valueOf(args[3]));
                    p.sendMessage( ChatColor.AQUA + "[Dleaderboards] " + ChatColor.GREEN + "Starting Leaderboard Setup!");
                    plugin.reloadConfig();
                    UserData.add(p);
                    p.sendMessage( ChatColor.AQUA + "[Dleaderboards] " + ChatColor.YELLOW + "Attempting to Sync Offline Player Data...");

                    try {
                        Thread.sleep(3000); // Sleep for 3 seconds (3000 milliseconds)
                    } catch (InterruptedException e) {
                        // Handle the InterruptedException if needed
                    }
                    p.sendMessage( ChatColor.AQUA + "[Dleaderboards] " + ChatColor.YELLOW + "Synchronization Started !");

                    List<OfflinePlayer> players = AllPlayers.getAllPlayers();
                    for (OfflinePlayer player : players) {
                        if (player != null){
                            UserData.add(player);
                            p.sendMessage( ChatColor.AQUA + "[Dleaderboards] " + ChatColor.GRAY + "Added " + player.getName() + " to Database " + ChatColor.GREEN +  (players.indexOf(player) + 1) + "/" + players.size());
                        }
                    }
                    p.sendMessage( ChatColor.AQUA + "[Dleaderboards] " + ChatColor.GREEN + "Leaderboard Created!");
                    p.sendMessage( ChatColor.AQUA + "[Dleaderboards] " + ChatColor.GRAY + "you can change the settings from plugins\\DiscordLeaderboards\\leaderboard\\" + args[0] + ".yml");

                    //getting random offline player
                    players.remove(p);
                    Random random = new Random();
                    int randomIndex = random.nextInt(players.size());
                    OfflinePlayer randomPlayer = players.get(randomIndex);

                    //checking placeholders if they supports offline players
                    FileConfiguration config = plugin.getConfig();
                    List<String> lblist = config.getStringList("leaderboards");
                    for (String lbname : lblist) {
                        FileConfiguration c = CustomConfig.getFileConfiguration(lbname);
                        String ph = c.getString("placeholder");
                        try {
                            double value2 = Double.parseDouble(PlaceholderAPI.setPlaceholders(randomPlayer, ph));
                        } catch (NumberFormatException ex) {
                            p.sendMessage(ChatColor.AQUA + "[Dleaderboards] " + ChatColor.RED + "[ERROR] " + ph + " may not support offline players. [code : 17]");
                        }
                    }


                }catch (NumberFormatException e){
                    p.sendMessage( ChatColor.AQUA + "[Dleaderboards] " + ChatColor.RED + "Placeholder " + args[2] + " Unsupported, Expansion Not Installed or Doesn't output Number value");
                }

            }else{
                Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "[Dleaderboards] " + ChatColor.RED + "You cant use this command here");
            }
        }else{
            if (sender instanceof Player){
                Player p = (Player) sender;
                p.sendMessage( ChatColor.AQUA + "[Dleaderboards] " + ChatColor.RED + "Wrong Command Usage !");
                p.sendMessage( ChatColor.AQUA + "[Dleaderboards] " + ChatColor.GRAY + "/dl-create name 5 %placeholder% Daily");
            }else{
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
