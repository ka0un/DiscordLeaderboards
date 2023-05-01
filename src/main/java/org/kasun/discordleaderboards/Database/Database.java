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
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "[Dleaderboards] " + ChatColor.GREEN + "Connected To H2!");
        }catch (ClassNotFoundException e) {
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "[Dleaderboards] " + ChatColor.RED + "Database Driver Not Found !");
            e.printStackTrace();
        } catch (SQLException e) {
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "[Dleaderboards] " + ChatColor.RED + "Failed to Connect H2 Database !");
            e.printStackTrace();
        }
        return connection;

    }

    public static void initializeDatabase(){
        PreparedStatement preparedStatement;
        try{
            preparedStatement = getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS UserData(PlayerID int NOT NULL IDENTITY(1, 1), PlayerUUID varchar)");
            preparedStatement.execute();
        }catch (SQLException ex){
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "[Dleaderboards] " + ChatColor.RED + "Database Preperation Error Detected!");
            ex.printStackTrace();
        }
    }

}
