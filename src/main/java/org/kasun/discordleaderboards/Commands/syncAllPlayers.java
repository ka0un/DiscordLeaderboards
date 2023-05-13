package org.kasun.discordleaderboards.Commands;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.kasun.discordleaderboards.Database.Database;
import org.kasun.discordleaderboards.Database.UserData;
import org.kasun.discordleaderboards.DiscordLeaderboards;
import org.kasun.discordleaderboards.Utils.AllPlayers;
import org.kasun.discordleaderboards.Utils.CustomConfig;

import java.util.List;
import java.util.Random;

public class syncAllPlayers implements CommandExecutor {
    Plugin plugin = JavaPlugin.getPlugin(DiscordLeaderboards.class);
    @Override
    public boolean onCommand(CommandSender sender, Command command, String lable, String[] args) {
        if (sender instanceof Player){
            Player p = (Player) sender;

            if (!p.hasPermission("dl.syncall") && !p.hasPermission("dl.admin")) {
                p.sendMessage( ChatColor.AQUA + "[Dleaderboards] " + ChatColor.RED + "No Permission ! [dl.syncall], [dl.admin]");
                return true;
            }

            List<OfflinePlayer> players = AllPlayers.getAllPlayers();

            for (OfflinePlayer player : players) {
                if (player != null){
                    UserData.add(player);
                    p.sendMessage( ChatColor.AQUA + "[Dleaderboards] " + ChatColor.GRAY + "Added " + player.getName() + " to Database " + ChatColor.GREEN +  (players.indexOf(player) + 1) + "/" + players.size());
                }
            }

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
                    double value = Double.parseDouble(PlaceholderAPI.setPlaceholders(randomPlayer, ph));
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
