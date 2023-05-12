package org.kasun.discordleaderboards.DiscordSRV;

import github.scarsz.discordsrv.DiscordSRV;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.kasun.discordleaderboards.DiscordLeaderboards;

public class SrvUtils {
    static Plugin plugin = JavaPlugin.getPlugin(DiscordLeaderboards.class);
    public static void load(){
        DiscordSRVListener discordsrvListener = new DiscordSRVListener(plugin);
        SrvSlashCommands srvSlashCommands = new SrvSlashCommands();
        DiscordSRV.api.subscribe(discordsrvListener);
        DiscordSRV.api.addSlashCommandProvider(srvSlashCommands);
    }
}
