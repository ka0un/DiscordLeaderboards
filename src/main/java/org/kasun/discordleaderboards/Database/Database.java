package org.kasun.discordleaderboards.Database;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.kasun.discordleaderboards.DiscordLeaderboards;



import java.sql.*;

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
/*
    public static void createPlaceholderColums(String placeholder){
        PreparedStatement preparedStatement;
        try{
            preparedStatement = getConnection().prepareStatement("ALTER TABLE UserData ADD COLUMN IF NOT EXISTS " + placeholder + " INT;");
            preparedStatement.execute();
        }catch (SQLException ex){
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "[Dleaderboards] " + ChatColor.RED + "Database Write Error Detected!");
            ex.printStackTrace();
        }
    }
*/
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

}
