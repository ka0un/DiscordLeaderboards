package org.kasun.discordleaderboards.Database;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.kasun.discordleaderboards.DiscordLeaderboards;
import org.kasun.discordleaderboards.Configs.MainConfig;


import java.sql.*;

public class Database {
    MainConfig mainConfig = new MainConfig();
    String h2url;
    private final DiscordLeaderboards plugin  = DiscordLeaderboards.getInstance();

    public Connection getConnection() {
        Connection connection = null;
        h2url = "jdbc:h2:file:" + plugin.getDataFolder().getAbsolutePath() + "\\database\\database";

        if (mainConfig.getStorageType().equalsIgnoreCase("h2")){

            try {
                Class.forName("org.h2.Driver");
                connection = DriverManager.getConnection(h2url);
            } catch (ClassNotFoundException e) {
                Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "Database Driver Not Found ! [code : 06]");
            } catch (SQLException e) {
                Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "Failed to Connect H2 Database ! [code : 07]");
            }


        } else if (mainConfig.getStorageType().equalsIgnoreCase("mysql")) {

            String adderss = mainConfig.getMysqlAddress();
            String database = mainConfig.getMysqlDatabase();
            String username = mainConfig.getMysqlUsername();
            String password = mainConfig.getMysqlPassword();

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection("jdbc:mysql://" + adderss + "/" + database, username, password);
            } catch (ClassNotFoundException e) {
                Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "MySql Driver Not Found ! [code : 30]");
                e.printStackTrace();
            } catch (SQLException e) {
                Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "Failed to Connect Mysql Database ! [code : 31]");
                e.printStackTrace();
            }

        }

        return connection;
    }

    public void initializeDatabase() {

        String largetextsqltype = "CLOB";
        if (mainConfig.getStorageType().equalsIgnoreCase("h2")){
            largetextsqltype = "CLOB";
        } else if (mainConfig.getStorageType().equalsIgnoreCase("mysql")) {
            largetextsqltype = "LONGTEXT";
        }

        PreparedStatement preparedStatement;
        try {
            preparedStatement = getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS UserData (" +
                    "  PlayerUUID varchar(255) PRIMARY KEY," +
                    "  PlayerName varchar(255)" +
                    ");");
            preparedStatement.execute();
            preparedStatement.close();
            preparedStatement = getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS Schedule (" +
                    "  Leaderboard varchar(255) PRIMARY KEY," +
                    "  LastSent TIMESTAMP" +
                    ");");
            preparedStatement.execute();
            preparedStatement.close();
            preparedStatement = getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS LiveCache (" +
                    "  Leaderboard varchar(255) PRIMARY KEY," +
                    "  Cache " + largetextsqltype +
                    ");");
            preparedStatement.execute();
            preparedStatement.close();

        } catch (SQLException ex) {
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "[Dleaderboards] " + ChatColor.RED + "Database Preparation Error Detected! [code : 08]");
            ex.printStackTrace();
        }
    }

}
