package org.kasun.discordleaderboards.Database;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.kasun.discordleaderboards.DiscordLeaderboards;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {

    public static Connection getConnection() {
        Connection connection = null;

        try {
            Class.forName("org.h2.Driver");
            connection = DriverManager.getConnection(DiscordLeaderboards.getH2url());
        } catch (ClassNotFoundException e) {
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "Database Driver Not Found ! [code : 06]");
        } catch (SQLException e) {
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "Failed to Connect H2 Database ! [code : 07]");
        }
        return connection;

    }

    public static void initializeDatabase() {
        PreparedStatement preparedStatement;
        try {
            preparedStatement = getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS UserData (" +
                    "  PlayerUUID varchar(255) PRIMARY KEY," +
                    "  PlayerName varchar(255)" +
                    ");");
            preparedStatement.execute();
            preparedStatement = getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS Schedule (" +
                    "  Leaderboard varchar(255) PRIMARY KEY," +
                    "  LastSent TIMESTAMP" +
                    ");");
            preparedStatement.execute();
            preparedStatement = getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS LiveCache (" +
                    "  Leaderboard varchar(255) PRIMARY KEY," +
                    "  Cache CLOB" +
                    ");");
            preparedStatement.execute();
        } catch (SQLException ex) {
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "[Dleaderboards] " + ChatColor.RED + "Database Preparation Error Detected! [code : 08]");
        }
    }

    public static void enterUserData(String UUID, String name, String placeholder, double value) {
        String placeholderColumnName = placeholder.substring(1, placeholder.length() - 1);
        PreparedStatement preparedStatement;
        try {
            preparedStatement = getConnection().prepareStatement("ALTER TABLE UserData ADD COLUMN IF NOT EXISTS " + placeholderColumnName + " DOUBLE;");
            preparedStatement.execute();
            preparedStatement = getConnection().prepareStatement("MERGE INTO UserData u USING (SELECT '" + UUID + "' AS PlayerUUID, '" + name + "' AS PlayerName, " + value + " AS " + placeholderColumnName + " FROM DUAL) data ON u.PlayerUUID = data.PlayerUUID WHEN MATCHED THEN UPDATE SET u." + placeholderColumnName + " = data." + placeholderColumnName + " WHEN NOT MATCHED THEN INSERT (PlayerUUID, PlayerName, " + placeholderColumnName + ") VALUES (data.PlayerUUID, data.PlayerName, data." + placeholderColumnName + ");");
            preparedStatement.execute();
        } catch (SQLException ex) {
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "[Dleaderboards] " + ChatColor.RED + "Issue while adding UserData to database [code : 09]");

        }
    }

    public static List gettop(String placeholder, int top) {
        List<String> toplist = new ArrayList<>(top);
        String placeholderColumnName = placeholder.substring(1, placeholder.length() - 1);

        PreparedStatement preparedStatement;
        try {
            preparedStatement = getConnection().prepareStatement("SELECT PlayerUUID FROM UserData ORDER BY " + placeholderColumnName + " DESC LIMIT " + top);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String uuid = rs.getString("PlayerUUID");
                toplist.add(uuid);
            }
        } catch (SQLException ex) {
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "[Dleaderboards] " + ChatColor.RED + "Issue while reading Userdata, pleace check your placeholders in leaderboard configs [code : 10]");
        }

        return toplist;
    }

    public static String getName(String uuid) {
        String name = null;
        PreparedStatement preparedStatement;
        try {
            preparedStatement = getConnection().prepareStatement("SELECT PlayerName FROM UserData WHERE PlayerUUID = ?");
            preparedStatement.setString(1, uuid);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                String playerName = rs.getString("PlayerName");
                name = playerName;
            }
        } catch (SQLException ex) {
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "[Dleaderboards] " + ChatColor.RED + "Issue while reading data in database [code : 11]");

        }
        return name;
    }

    public static Double getValue(String uuid, String placeholder) {
        Double value = 0.0;
        String placeholderColumnName = placeholder.substring(1, placeholder.length() - 1);
        PreparedStatement preparedStatement;
        try {
            preparedStatement = getConnection().prepareStatement("SELECT " + placeholderColumnName + " FROM UserData WHERE PlayerUUID = ?");
            preparedStatement.setString(1, uuid);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                value = rs.getDouble(placeholderColumnName);
            }
        } catch (SQLException ex) {
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "[Dleaderboards] " + ChatColor.RED + "Issue while reading data in database  [code : 12]");

        }
        return value;
    }
}
