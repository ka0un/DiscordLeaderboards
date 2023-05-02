package org.kasun.discordleaderboards.Listeners;

import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.platform.facet.Facet;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.kasun.discordleaderboards.Database.Database;
import org.kasun.discordleaderboards.DiscordLeaderboards;
import org.kasun.discordleaderboards.Utils.CustomConfig;

import java.util.List;

public class PlayerJoin implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        String name = player.getName();

        FileConfiguration config = Bukkit.getPluginManager().getPlugin("DiscordLeaderboards").getConfig();
        List<String> lblist = config.getStringList("leaderboards");

        for (String lbname : lblist) {

            FileConfiguration c = CustomConfig.getFileConfiguration(lbname);
            String ph = c.getString("placeholder");
            try{
                double value = Double.parseDouble(PlaceholderAPI.setPlaceholders(player, ph));
                String UUID = player.getUniqueId().toString();
                Database.enterUserData(UUID, name, ph, value);
            }catch (NumberFormatException ex){
                Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "[Dleaderboards] " + ChatColor.RED + "[ERROR] " + ph + " isnt working Make Sure Required Papi Expantion installed or Placeholder Outputting Number Value eg- 1, 5, 2000");
            }



        }


    }
}
