package org.kasun.discordleaderboards.Utils;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PlayerUtils {
    public static List<UUID> getAllPlayerUUIDs() {
        List<UUID> playerUUIDs = new ArrayList<>();

        for (OfflinePlayer offlinePlayer : Bukkit.getOfflinePlayers()) {
            playerUUIDs.add(offlinePlayer.getUniqueId());
        }

        return playerUUIDs;
    }

    public static List<OfflinePlayer> getAllPlayers() {
        List<OfflinePlayer> players = new ArrayList<>();

        for (OfflinePlayer offlinePlayer : Bukkit.getOfflinePlayers()) {
            players.add(offlinePlayer);
        }

        return players;
    }


}
