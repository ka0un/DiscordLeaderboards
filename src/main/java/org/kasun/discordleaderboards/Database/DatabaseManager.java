package org.kasun.discordleaderboards.Database;

import org.kasun.discordleaderboards.DiscordLeaderboards;

public class DatabaseManager {
    private final DiscordLeaderboards plugin  = DiscordLeaderboards.getInstance();

    public DatabaseManager() {
        Database database = new Database();
        database.initializeDatabase();
    }
}
