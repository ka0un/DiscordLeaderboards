package org.kasun.discordleaderboards.DiscordSRV;

import github.scarsz.discordsrv.DiscordSRV;
import org.bukkit.Bukkit;
import org.kasun.discordleaderboards.DiscordLeaderboards;

public class DiscordSrvManager {

    public DiscordSrvManager() {
        if (Bukkit.getPluginManager().getPlugin("DiscordSRV") != null) {
            DiscordLeaderboards plugin = DiscordLeaderboards.getInstance();
            DiscordSRVListener discordsrvListener = new DiscordSRVListener(plugin);
            SrvSlashCommands srvSlashCommands = new SrvSlashCommands();
            DiscordSRV.api.subscribe(discordsrvListener);
            DiscordSRV.api.addSlashCommandProvider(srvSlashCommands);
        }
    }

}
