package org.kasun.discordleaderboards.Database;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.kasun.discordleaderboards.Utils.Leaderboard;

import java.io.IOException;
import java.io.Reader;
import java.sql.*;

import static org.kasun.discordleaderboards.Database.Database.getConnection;

public class LiveCache {
    public static void setCache(String leaderboard) {
        String cache = Leaderboard.toString(leaderboard);

        PreparedStatement preparedStatement;
        try {
            Connection conn = getConnection();
            preparedStatement = conn.prepareStatement("MERGE INTO LiveCache s USING (VALUES (?, ?)) data (Leaderboard, Cache) ON s.Leaderboard = data.Leaderboard WHEN MATCHED THEN UPDATE SET s.Cache = data.Cache WHEN NOT MATCHED THEN INSERT (Leaderboard, Cache) VALUES (data.Leaderboard, data.Cache);");
            preparedStatement.setString(1, leaderboard);

            // Convert the 'cache' string to a Clob object
            Clob clob = conn.createClob();
            clob.setString(1, cache);
            preparedStatement.setClob(2, clob);

            int rowsUpdated = preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "[Dleaderboards] " + ChatColor.RED + "Issue while saving Live Cache");
            ex.printStackTrace();
        }
    }

    public static String getCache(String leaderboard) {
        String cache = null;
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        try {
            Connection conn = getConnection();
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
        } catch (SQLException ex) {
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "[Dleaderboards] " + ChatColor.RED + "Issue while reading Live Cache");
            ex.printStackTrace();
        } catch (IOException ex) {
            return null;
        }
        return cache;
    }

    public static boolean isDifferent(String leaderboard) {
        String currentValues = Leaderboard.toString(leaderboard);
        String cache = getCache(leaderboard);

        if (cache == null) {
            return true; // If cache is null, then it's different
        }

        return !currentValues.equals(cache); // Return true if currentValues is different from cache
    }


}
