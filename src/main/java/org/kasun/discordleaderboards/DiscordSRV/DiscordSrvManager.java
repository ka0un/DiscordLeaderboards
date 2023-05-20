package org.kasun.discordleaderboards.DiscordSRV;

import github.scarsz.discordsrv.DiscordSRV;
import org.bukkit.Bukkit;
import org.kasun.discordleaderboards.DiscordLeaderboards;

public class DiscordSrvManager {
    private final DiscordLeaderboards plugin  = DiscordLeaderboards.getInstance();

    public DiscordSrvManager() {
        if (Bukkit.getPluginManager().getPlugin("DiscordSRV") != null) {
            DiscordSRVListener discordsrvListener = new DiscordSRVListener(plugin);
            SrvSlashCommands srvSlashCommands = new SrvSlashCommands();
            DiscordSRV.api.subscribe(discordsrvListener);
            DiscordSRV.api.addSlashCommandProvider(srvSlashCommands);
        }
    }

}
