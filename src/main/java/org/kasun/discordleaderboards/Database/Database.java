package org.kasun.discordleaderboards.Database;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.kasun.discordleaderboards.DiscordLeaderboards;
import org.kasun.discordleaderboards.Utils.MainConfig;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {

    public static Connection getConnection() {
        Connection connection = null;

        if (MainConfig.getStorageType().equalsIgnoreCase("h2")){

            try {
                Class.forName("org.h2.Driver");
                connection = DriverManager.getConnection(DiscordLeaderboards.getH2url());
            } catch (ClassNotFoundException e) {
                Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "Database Driver Not Found ! [code : 06]");
            } catch (SQLException e) {
                Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "Failed to Connect H2 Database ! [code : 07]");
            }


        } else if (MainConfig.getStorageType().equalsIgnoreCase("mysql")) {

            String adderss = MainConfig.getMysqlAddress();
            String database = MainConfig.getMysqlDatabase();
            String username = MainConfig.getMysqlUsername();
            String password = MainConfig.getMysqlPassword();

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

    public static void initializeDatabase() {
        String largetextsqltype = "CLOB";
        if (MainConfig.getStorageType().equalsIgnoreCase("h2")){
            largetextsqltype = "CLOB";
        } else if (MainConfig.getStorageType().equalsIgnoreCase("mysql")) {
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

    public static void enterUserData(String UUID, String name, String placeholder, double value) {
        String placeholderColumnName = placeholder.substring(1, placeholder.length() - 1);
        PreparedStatement preparedStatement;

        if (MainConfig.getStorageType().equalsIgnoreCase("h2")){
            try {
                preparedStatement = getConnection().prepareStatement("ALTER TABLE UserData ADD COLUMN IF NOT EXISTS " + placeholderColumnName + " DOUBLE;");
                preparedStatement.execute();
                preparedStatement.close();
            } catch (SQLException ex) {
                Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "[Dleaderboards] " + ChatColor.RED + "Issue while adding UserData to database [code : 09]");
                ex.printStackTrace();

            }
            try {
                preparedStatement = getConnection().prepareStatement("MERGE INTO UserData u USING (SELECT '" + UUID + "' AS PlayerUUID, '" + name + "' AS PlayerName, " + value + " AS " + placeholderColumnName + " FROM DUAL) data ON u.PlayerUUID = data.PlayerUUID WHEN MATCHED THEN UPDATE SET u." + placeholderColumnName + " = data." + placeholderColumnName + " WHEN NOT MATCHED THEN INSERT (PlayerUUID, PlayerName, " + placeholderColumnName + ") VALUES (data.PlayerUUID, data.PlayerName, data." + placeholderColumnName + ");");
                preparedStatement.execute();
                preparedStatement.close();
            } catch (SQLException ex) {
                Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "[Dleaderboards] " + ChatColor.RED + "Issue while adding UserData to database [code : 09.1]");
                ex.printStackTrace();

            }
        } else if (MainConfig.getStorageType().equalsIgnoreCase("mysql")) {
            try {
                Connection connection = getConnection();
                Statement statement = connection.createStatement();

                     // Check if the column exists in the table
                ResultSet resultSet = statement.executeQuery("SELECT * FROM information_schema.COLUMNS WHERE TABLE_NAME = 'UserData' AND COLUMN_NAME = '" + placeholderColumnName + "'");
                if (!resultSet.next()) {
                    // Column does not exist, so add it
                    statement.executeUpdate("ALTER TABLE UserData ADD COLUMN " + placeholderColumnName + " DOUBLE");
                }

                resultSet.close();
                statement.close();
                connection.close();

            } catch (SQLException ex) {
                Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "[Dleaderboards] " + ChatColor.RED + "Issue while adding UserData to database [code : 09.2]");
                ex.printStackTrace();

            }
            try {
                preparedStatement = getConnection().prepareStatement(
                        "INSERT INTO UserData (PlayerUUID, PlayerName, " + placeholderColumnName + ") VALUES (?, ?, ?) " +
                                "ON DUPLICATE KEY UPDATE " + placeholderColumnName + " = VALUES(" + placeholderColumnName + ")"
                );
                preparedStatement.setString(1, UUID);
                preparedStatement.setString(2, name);
                preparedStatement.setDouble(3, value);

                preparedStatement.executeUpdate();
                preparedStatement.close();

            } catch (SQLException ex) {
                Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "[Dleaderboards] " + ChatColor.RED + "Issue while adding UserData to database [code : 09.3]");
                ex.printStackTrace();

            }
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
            preparedStatement.close();
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
            preparedStatement.close();
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
            preparedStatement.close();
        } catch (SQLException ex) {
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "[Dleaderboards] " + ChatColor.RED + "Issue while reading data in database  [code : 12]");

        }
        return value;
    }
}
