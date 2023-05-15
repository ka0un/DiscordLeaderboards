package org.kasun.discordleaderboards.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.kasun.discordleaderboards.database.UserData;

public class PlayerQuit implements Listener {

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        UserData userData = new UserData();
        userData.addUserDataToDBAllPlaceholders(player);
    }
}
