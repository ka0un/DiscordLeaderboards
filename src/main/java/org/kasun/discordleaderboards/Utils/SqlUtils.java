package org.kasun.discordleaderboards.Utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.kasun.discordleaderboards.Configs.MainConfig;
import org.kasun.discordleaderboards.Database.Database;

import java.io.IOException;
import java.io.Reader;
import java.sql.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class SqlUtils {
    public static void addUserDataToDatabase(String placeholderColumnName, String UUID, String name , double value){

        MainConfig mainConfig = new MainConfig();
        Database database = new Database();
        PreparedStatement preparedStatement;

        if (mainConfig.getStorageType().equalsIgnoreCase("h2")){

            try {
                preparedStatement = database.getConnection().prepareStatement("ALTER TABLE UserData ADD COLUMN IF NOT EXISTS " + placeholderColumnName + " DOUBLE;");
                preparedStatement.execute();
                preparedStatement.close();
            } catch (SQLException ex) {
                Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "[Dleaderboards] " + ChatColor.RED + "Issue while adding UserData to database [code : 09]");
                ex.printStackTrace();

            }

            try {
                preparedStatement = database.getConnection().prepareStatement("MERGE INTO UserData u USING (SELECT '" + UUID + "' AS PlayerUUID, '" + name + "' AS PlayerName, " + value + " AS " + placeholderColumnName + " FROM DUAL) data ON u.PlayerUUID = data.PlayerUUID WHEN MATCHED THEN UPDATE SET u." + placeholderColumnName + " = data." + placeholderColumnName + " WHEN NOT MATCHED THEN INSERT (PlayerUUID, PlayerName, " + placeholderColumnName + ") VALUES (data.PlayerUUID, data.PlayerName, data." + placeholderColumnName + ");");
                preparedStatement.execute();
                preparedStatement.close();
            } catch (SQLException ex) {
                Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "[Dleaderboards] " + ChatColor.RED + "Issue while adding UserData to database [code : 09.1]");
                ex.printStackTrace();

            }

        } else if (mainConfig.getStorageType().equalsIgnoreCase("mysql")) {

            try {
                Connection connection = database.getConnection();
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
                preparedStatement = database.getConnection().prepareStatement(
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


    public static Map<String, Integer> getTopPlayerMap(String placeholderColumnName, int top) {
        Map<String, Integer> topPlayerScores = new LinkedHashMap<>();
        MainConfig mainConfig = new MainConfig();

        try {
            Database database = new Database();
            PreparedStatement preparedStatement;

            String databaseName = mainConfig.getStorageType(); // Get the name of the current database

            String query;
            if (databaseName.equalsIgnoreCase("h2")) {
                query = "SELECT PlayerName, " + placeholderColumnName + " FROM UserData ORDER BY " + placeholderColumnName + " DESC LIMIT " + top;
            } else if (databaseName.equalsIgnoreCase("mysql")) {
                query = "SELECT PlayerName, " + placeholderColumnName + " FROM UserData ORDER BY " + placeholderColumnName + " DESC LIMIT " + top;
            } else {
                // Unsupported database type
                throw new UnsupportedOperationException("Unsupported database type: " + databaseName);
            }

            preparedStatement = database.getConnection().prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String playerName = rs.getString("PlayerName");
                int score = rs.getInt(placeholderColumnName);
                if (score > 0){
                    topPlayerScores.put(playerName, score);
                }
            }
            preparedStatement.close();
        } catch (SQLException ex) {
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "[Dleaderboards] " + ChatColor.RED + "Issue while reading Userdata, please check your placeholders in leaderboard configs [code : 10]");
        }

        return topPlayerScores;
    }

    public static void setScheduleLastSent(String leaderboard, Timestamp timestamp){
        PreparedStatement preparedStatement;
        MainConfig mainConfig = new MainConfig();
        Database database = new Database();
        if (mainConfig.getStorageType().equalsIgnoreCase("h2")){
            try {
                preparedStatement = database.getConnection().prepareStatement("MERGE INTO Schedule s USING (VALUES (?, ?)) data (Leaderboard, LastSent) ON s.Leaderboard = data.Leaderboard WHEN MATCHED THEN UPDATE SET s.LastSent = data.LastSent WHEN NOT MATCHED THEN INSERT (Leaderboard, LastSent) VALUES (data.Leaderboard, data.LastSent);");
                preparedStatement.setString(1, leaderboard);
                preparedStatement.setTimestamp(2, timestamp);
                int rowsUpdated = preparedStatement.executeUpdate();
                preparedStatement.close();
            } catch (SQLException ex) {
                Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "[Dleaderboards] " + ChatColor.RED + "Issue while reading data in database  [code : 13]");
            }
        } else if (mainConfig.getStorageType().equalsIgnoreCase("mysql")) {
            try {
                preparedStatement = database.getConnection().prepareStatement(
                        "REPLACE INTO Schedule (Leaderboard, LastSent) VALUES (?, ?)"
                );
                preparedStatement.setString(1, leaderboard);
                preparedStatement.setTimestamp(2, timestamp);
                int rowsUpdated = preparedStatement.executeUpdate();

                preparedStatement.close();

            } catch (SQLException ex) {
                Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "[Dleaderboards] " + ChatColor.RED + "Issue while reading data in database  [code : 13.1]");
            }
        }
    }

    public  static  boolean isScheduleAlredySent(String leaderboard){
        PreparedStatement preparedStatement;
        MainConfig mainConfig = new MainConfig();
        Database database = new Database();
        try{
            preparedStatement = database.getConnection().prepareStatement("SELECT COUNT(*) AS count FROM Schedule WHERE Leaderboard = ?");
            preparedStatement.setString(1, leaderboard);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                int count = rs.getInt("count");
                return count > 0;
            }
            preparedStatement.close();
        }catch (SQLException ex){
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "[Dleaderboards] " + ChatColor.RED + "Issue while reading data in database  [code : 14]");
        }
        return false;
    }

    public static Timestamp getScheduleLastSent(String leaderboard){
        Timestamp lastsent = Timestamp.valueOf("2000-01-01 00:00:00"); //Default Value - for check if lastsent
        PreparedStatement preparedStatement;
        MainConfig mainConfig = new MainConfig();
        Database database = new Database();
        try{
            preparedStatement = database.getConnection().prepareStatement("SELECT " + "LastSent" + " FROM Schedule WHERE Leaderboard = ?");
            preparedStatement.setString(1, leaderboard);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                lastsent = rs.getTimestamp("LastSent");
            }
            preparedStatement.close();
        }catch (SQLException ex){
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "[Dleaderboards] " + ChatColor.RED + "Issue while reading data in database  [code : 15]");
        }
        return lastsent;
    }

    public static void setCache(String leaderboard, String cache) {
        PreparedStatement preparedStatement;
        MainConfig mainConfig = new MainConfig();
        Database database = new Database();
        if (mainConfig.getStorageType().equalsIgnoreCase("h2")){
            try {
                Connection conn = database.getConnection();
                preparedStatement = conn.prepareStatement("MERGE INTO LiveCache s USING (VALUES (?, ?)) data (Leaderboard, Cache) ON s.Leaderboard = data.Leaderboard WHEN MATCHED THEN UPDATE SET s.Cache = data.Cache WHEN NOT MATCHED THEN INSERT (Leaderboard, Cache) VALUES (data.Leaderboard, data.Cache);");
                preparedStatement.setString(1, leaderboard);

                // Convert the 'cache' string to a Clob object
                Clob clob = conn.createClob();
                clob.setString(1, cache);
                preparedStatement.setClob(2, clob);

                int rowsUpdated = preparedStatement.executeUpdate();
                conn.close();
            } catch (SQLException ex) {
                Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "[Dleaderboards] " + ChatColor.RED + "Issue while saving Live Cache [code : 02]");

            }

        } else if (mainConfig.getStorageType().equalsIgnoreCase("mysql")) {

            try {
                Connection conn = database.getConnection();
                preparedStatement = conn.prepareStatement(
                        "INSERT INTO LiveCache (Leaderboard, Cache) VALUES (?, ?) " +
                                "ON DUPLICATE KEY UPDATE Cache = VALUES(Cache)"
                );
                preparedStatement.setString(1, leaderboard);
                preparedStatement.setString(2, cache);

                int rowsUpdated = preparedStatement.executeUpdate();
                preparedStatement.close();
                conn.close();

            } catch (SQLException ex) {
                Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "[Dleaderboards] " + ChatColor.RED + "Issue while saving Live Cache [code : 02.1]");

            }
        }

    }

    public static String getCache(String leaderboard) {
        String cache = null;
        MainConfig mainConfig = new MainConfig();
        Database database = new Database();
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        try {
            Connection conn = database.getConnection();
            preparedStatement = conn.prepareStatement("SELECT Cache FROM LiveCache WHERE Leaderboard = ?");
            preparedStatement.setString(1, leaderboard);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Clob clob = resultSet.getClob("Cache");
                Reader reader = clob.getCharacterStream();
                StringBuilder sb = new StringBuilder();
                char[] buffer = new char[1024];
                int bytesRead;

                while ((bytesRead = reader.read(buffer)) != -1) {
                    sb.append(buffer, 0, bytesRead);
                }
                cache = sb.toString();

            }
            resultSet.close();
            preparedStatement.close();
            conn.close();
        } catch (SQLException ex) {
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "[Dleaderboards] " + ChatColor.RED + "Issue while reading Live Cache [code : 03]");
        } catch (IOException ex) {
            return null;
        }
        return cache;
    }

    public static void deleteAllValues(String tableName, String columnName) {
        PreparedStatement preparedStatement;
        MainConfig mainConfig = new MainConfig();
        Database database = new Database();
        try {
            if (mainConfig.getStorageType().equalsIgnoreCase("h2")) {
                preparedStatement = database.getConnection().prepareStatement(
                        "DELETE FROM " + tableName + " WHERE " + columnName + " IS NOT NULL"
                );
            } else if (mainConfig.getStorageType().equalsIgnoreCase("mysql")) {
                preparedStatement = database.getConnection().prepareStatement(
                        "UPDATE " + tableName + " SET " + columnName + " = NULL"
                );
            } else {
                throw new UnsupportedOperationException("Unsupported storage type: " + mainConfig.getStorageType());
            }

            int rowsUpdated = preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException ex) {
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "[Dleaderboards] " + ChatColor.RED + "Issue while deleting data in the database [code: 13]");
        }
    }




}
