package org.kasun.discordleaderboards.schedules;


import org.bukkit.scheduler.BukkitRunnable;
import org.kasun.discordleaderboards.configs.MainConfig;
import org.kasun.discordleaderboards.DiscordLeaderboards;
import org.kasun.discordleaderboards.leaderboard.Leaderboard;

import java.util.List;

public class ScheduleManager {
    private final DiscordLeaderboards plugin  = DiscordLeaderboards.getInstance();
    public ScheduleManager() {
        MainConfig mainConfig = new MainConfig();
        if (!mainConfig.isFirstTime()){
            int scheduleDelay = mainConfig.getScheduleDelayMins();
            new BukkitRunnable() {
                @Override
                public void run() {
                    // This code will run every x minutes
                    List<String> itemList = mainConfig.getLeaderboardsList();

                    for (String leaderboardname : itemList) {
                        Leaderboard leaderboard = new Leaderboard(leaderboardname);
                        Schedule schedule = new Schedule(leaderboard);
                        schedule.run();
                    }
                }
            }.runTaskTimerAsynchronously(plugin, 0L, 20L * 60 * scheduleDelay);
        }
    }
}
