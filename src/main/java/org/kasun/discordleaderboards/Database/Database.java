package org.kasun.discordleaderboards.Database;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.kasun.discordleaderboards.DiscordLeaderboards;



import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {

    public static Connection getConnection(){
        Connection connection = null;

        try {
            Class.forName("org.h2.Driver");
            connection = DriverManager.getConnection(DiscordLeaderboards.getH2url());
        }catch (ClassNotFoundException e) {
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "Database Driver Not Found !");
            e.printStackTrace();
        } catch (SQLException e) {
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "Failed to Connect H2 Database !");
            e.printStackTrace();
        }
        return connection;

    }

    public static void initializeDatabase(){
        PreparedStatement preparedStatement;
        try{
            preparedStatement = getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS UserData (" +
                    "  PlayerUUID varchar(255) PRIMARY KEY," +
                    "  PlayerName varchar(255)" +
                    ");");
            preparedStatement.execute();
        }catch (SQLException ex){
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "[Dleaderboards] " + ChatColor.RED + "Database Preperation Error Detected!");
            ex.printStackTrace();
        }
    }

    public static void enterUserData(String UUID, String name, String placeholder, double value){
        String placeholderColumnName = placeholder.substring(1, placeholder.length()-1);
        PreparedStatement preparedStatement;
        try{
            preparedStatement = getConnection().prepareStatement("ALTER TABLE UserData ADD COLUMN IF NOT EXISTS " + placeholderColumnName + " DOUBLE;");
            preparedStatement.execute();
            preparedStatement = getConnection().prepareStatement("MERGE INTO UserData u USING (SELECT '" + UUID + "' AS PlayerUUID, '" + name + "' AS PlayerName, " + value + " AS " + placeholderColumnName + " FROM DUAL) data ON u.PlayerUUID = data.PlayerUUID WHEN MATCHED THEN UPDATE SET u." + placeholderColumnName + " = data." + placeholderColumnName + " WHEN NOT MATCHED THEN INSERT (PlayerUUID, PlayerName, " + placeholderColumnName + ") VALUES (data.PlayerUUID, data.PlayerName, data." + placeholderColumnName + ");");
            preparedStatement.execute();
        }catch (SQLException ex){
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "[Dleaderboards] " + ChatColor.RED + "Issue while adding data to database");
            ex.printStackTrace();
        }
    }

    public static List gettop(String placeholder, int top){
        List<String> toplist = new ArrayList<>(top);
        String placeholderColumnName = placeholder.substring(1, placeholder.length()-1);

        PreparedStatement preparedStatement;
        try{
            preparedStatement = getConnection().prepareStatement("SELECT PlayerUUID FROM UserData ORDER BY " + placeholderColumnName + " DESC LIMIT " + top);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String uuid = rs.getString("PlayerUUID");
                toplist.add(uuid);
            }

        }catch (SQLException ex){
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "[Dleaderboards] " + ChatColor.RED + "Issue while reading data in database");
            ex.printStackTrace();
        }
        return toplist;
    }

    public static String getName(String uuid){
        String name = null;
        PreparedStatement preparedStatement;
        try{
            preparedStatement = getConnection().prepareStatement("SELECT PlayerName FROM UserData WHERE PlayerUUID = ?");
            preparedStatement.setString(1, uuid);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                String playerName = rs.getString("PlayerName");
                name = playerName;
            }
        }catch (SQLException ex){
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "[Dleaderboards] " + ChatColor.RED + "Issue while reading data in database");
            ex.printStackTrace();
        }
        return name;
    }

    public static Double getValue(String uuid, String placeholder){
        Double value = 0.0;
        String placeholderColumnName = placeholder.substring(1, placeholder.length()-1);
        PreparedStatement preparedStatement;
        try{
            preparedStatement = getConnection().prepareStatement("SELECT " + placeholderColumnName + " FROM UserData WHERE PlayerUUID = ?");
            preparedStatement.setString(1, uuid);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                value = rs.getDouble(placeholderColumnName);
            }
        }catch (SQLException ex){
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "[Dleaderboards] " + ChatColor.RED + "Issue while reading data in database");
            ex.printStackTrace();
        }
        return value;
    }
}
