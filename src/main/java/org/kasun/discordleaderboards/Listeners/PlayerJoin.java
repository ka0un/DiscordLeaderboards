package org.kasun.discordleaderboards.Listeners;


import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.kasun.discordleaderboards.Database.UserData;

public class PlayerJoin implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        UserData userData = new UserData();
        userData.addUserDataToDBAllPlaceholders(player);
    }
}
