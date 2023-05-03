package org.kasun.discordleaderboards.Database;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.kasun.discordleaderboards.Utils.CustomConfig;

import java.util.ArrayList;
import java.util.List;

public class UserData {
    public static void add(Player p) {
        String name = p.getName();

        FileConfiguration config = Bukkit.getPluginManager().getPlugin("DiscordLeaderboards").getConfig();
        List<String> lblist = config.getStringList("leaderboards");

        for (String lbname : lblist) {
            FileConfiguration c = CustomConfig.getFileConfiguration(lbname);
            String ph = c.getString("placeholder");
            try {
                double value = Double.parseDouble(PlaceholderAPI.setPlaceholders(p, ph));
                String UUID = p.getUniqueId().toString();
                Database.enterUserData(UUID, name, ph, value);
            } catch (NumberFormatException ex) {
                Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "[Dleaderboards] " + ChatColor.RED + "[ERROR] " + ph + " isn't working Make Sure Required Papi Expansion installed or Placeholder Outputting Number Value eg- 1, 5, 2000");
            }

            //debug code
            //List<String> toplist = Database.gettop(ph, 5);
            //for (String uuid : toplist){
            //    System.out.println("Debug top : " + uuid);
            //}
            //System.out.println("Debug getname : " + Database.getName(p.getUniqueId().toString()));
            //System.out.println("Debug getvalue : " + Database.getValue(p.getUniqueId().toString(), ph));
        }

    }

    public static void gettop(String placeholder, int top){
        List<String> toplist = new ArrayList<>(top);


    }
}
