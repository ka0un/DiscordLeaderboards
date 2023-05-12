package org.kasun.discordleaderboards.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.kasun.discordleaderboards.Database.UserData;
import org.kasun.discordleaderboards.Utils.AllPlayers;

import java.util.List;

public class syncAllPlayers implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String lable, String[] args) {
        if (sender instanceof Player){
            Player p = (Player) sender;
            List<OfflinePlayer> players = AllPlayers.getAllPlayers();

            for (OfflinePlayer player : players) {
                if (player != null){
                    UserData.add(player);
                    p.sendMessage( ChatColor.AQUA + "[Dleaderboards] " + ChatColor.GRAY + "Added " + player.getName() + " to Database " + ChatColor.GREEN +  (players.indexOf(player) + 1) + "/" + players.size());
                }
            }
        }else{
            List<OfflinePlayer> players = AllPlayers.getAllPlayers();

            for (OfflinePlayer player : players) {
                if (player != null){
                    UserData.add(player);
                    Bukkit.getServer().getConsoleSender().sendMessage( ChatColor.AQUA + "[Dleaderboards] " + ChatColor.GRAY + "Added " + player.getName() + " to Database " + ChatColor.GREEN +  (players.indexOf(player) + 1) + "/" + players.size());
                }

            }
        }

        return true;
    }
}
