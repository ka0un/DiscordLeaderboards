package org.kasun.discordleaderboards.Database;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.kasun.discordleaderboards.Utils.CustomConfig;

import java.util.*;

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

    public static Map gettoplistmap(String placeholder, int top){
        List<String> uuidlist = Database.gettop(placeholder, top);
        LinkedHashMap<String, Integer> toplistmap = new LinkedHashMap<>();
        for (String uuid: uuidlist){
            String name = Database.getName(uuid);
            int value = Database.getValue(uuid, placeholder).intValue();
            toplistmap.put(name, value);
        }
        return toplistmap;
    }

    public static String gettoplistStringforWebhook(String placeholder, int top){
        Map<String, Integer> toplistmap = UserData.gettoplistmap(placeholder, top);

        // find the maximum length of the names
        int maxNameLength = 0;
        for (String name : toplistmap.keySet()) {
            if (name.length() > maxNameLength) {
                maxNameLength = name.length();
            }
        }

        StringBuilder sb = new StringBuilder();
        int i = 1;
        for (Map.Entry<String, Integer> entry : toplistmap.entrySet()) {
            String name = entry.getKey();
            int score = entry.getValue();
            String formattedEntry = String.format("%d. %-"+(maxNameLength+4)+"s %d\\u000A", i++, name, score);
            sb.append(formattedEntry);
        }
        String leaderboardString = sb.toString();
        return leaderboardString;
    }


    public static String gettoplistString(String placeholder, int top){
        Map<String, Integer> toplistmap = UserData.gettoplistmap(placeholder, top);
        StringBuilder sb = new StringBuilder();
        int i = 1;
        for (Map.Entry<String, Integer> entry : toplistmap.entrySet()) {
            String name = entry.getKey();
            int score = entry.getValue();
            String formattedEntry = String.format("%d. %-20s %d\n", i++, name, score);
            sb.append(formattedEntry);
        }
        String leaderboardString = sb.toString();
        return leaderboardString;
    }
}
