package org.kasun.discordleaderboards.Database;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.kasun.discordleaderboards.Utils.MainConfig;
import org.kasun.discordleaderboards.Utils.TimeUtils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import static org.kasun.discordleaderboards.Database.Database.getConnection;

public class Schedule {
    public static void setLastSent(String leaderboard, Timestamp timestamp){
        PreparedStatement preparedStatement;
        try{
            preparedStatement = getConnection().prepareStatement("UPDATE Schedule SET LastSent = ? WHERE Leaderboard = ?");
            preparedStatement.setTimestamp(1, timestamp);
            preparedStatement.setString(2, leaderboard);
            int rowsUpdated = preparedStatement.executeUpdate();
            preparedStatement.close();
        }catch (SQLException ex){
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "[Dleaderboards] " + ChatColor.RED + "Issue while reading data in database [code : 01]");
        }
    }

    public static void setLastSent(String leaderboard){
        Timestamp timestamp = TimeUtils.getCurrentTimeStamp();
        PreparedStatement preparedStatement;
        if (MainConfig.getStorageType().equalsIgnoreCase("h2")){
            try {
                preparedStatement = getConnection().prepareStatement("MERGE INTO Schedule s USING (VALUES (?, ?)) data (Leaderboard, LastSent) ON s.Leaderboard = data.Leaderboard WHEN MATCHED THEN UPDATE SET s.LastSent = data.LastSent WHEN NOT MATCHED THEN INSERT (Leaderboard, LastSent) VALUES (data.Leaderboard, data.LastSent);");
                preparedStatement.setString(1, leaderboard);
                preparedStatement.setTimestamp(2, timestamp);
                int rowsUpdated = preparedStatement.executeUpdate();
                preparedStatement.close();
            } catch (SQLException ex) {
                Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "[Dleaderboards] " + ChatColor.RED + "Issue while reading data in database  [code : 13]");
            }
        } else if (MainConfig.getStorageType().equalsIgnoreCase("mysql")) {
            try {
                preparedStatement = getConnection().prepareStatement(
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

    public  static  boolean isAlredySent(String leaderboard){
        PreparedStatement preparedStatement;
        try{
            preparedStatement = getConnection().prepareStatement("SELECT COUNT(*) AS count FROM Schedule WHERE Leaderboard = ?");
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

    public static Timestamp getLastSent(String leaderboard){
        Timestamp lastsent = Timestamp.valueOf("2000-01-01 00:00:00"); //Default Value - for check if lastsent
        PreparedStatement preparedStatement;
        try{
            preparedStatement = getConnection().prepareStatement("SELECT " + "LastSent" + " FROM Schedule WHERE Leaderboard = ?");
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
}
