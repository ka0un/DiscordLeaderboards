package org.kasun.discordleaderboards.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.kasun.discordleaderboards.Utils.Leaderboard;

public class forceLeaderboardSend implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String lable, String[] args) {
        if (sender instanceof Player){
            Player p = (Player) sender;
            if (args.length == 1){
                Leaderboard l1 = new Leaderboard(args[0], 5, "%player_name%");
                l1.send();

            }

        }
        return true;
    }
}
