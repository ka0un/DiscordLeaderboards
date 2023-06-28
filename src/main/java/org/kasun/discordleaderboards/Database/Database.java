package org.kasun.discordleaderboards.Database;

import org.kasun.discordleaderboards.DiscordLeaderboards;
import org.kasun.discordleaderboards.Configs.MainConfig;


import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Database {
    MainConfig mainConfig = new MainConfig();
    String h2url;
    private final DiscordLeaderboards plugin  = DiscordLeaderboards.getInstance();

    public Connection getConnection() {
        Connection connection = null;
        h2url = "jdbc:h2:file:" + plugin.getDataFolder().getAbsolutePath() + "/database/database";

        if (mainConfig.getStorageType().equalsIgnoreCase("h2")) {
            try {
                org.h2.Driver driver = new org.h2.Driver();
                Properties properties = new Properties();
                connection = driver.connect(h2url, properties);
            } catch (SQLException e) {
                plugin.getLogger().severe("Failed to Connect H2 Database! [code: 07]");
                e.printStackTrace();
            }
        } else if (mainConfig.getStorageType().equalsIgnoreCase("mysql")) {
            String address = mainConfig.getMysqlAddress();
            String database = mainConfig.getMysqlDatabase();
            String username = mainConfig.getMysqlUsername();
            String password = mainConfig.getMysqlPassword();

            try {
                com.mysql.cj.jdbc.Driver driver = new com.mysql.cj.jdbc.Driver();
                Properties properties = new Properties();
                properties.setProperty("user", username);
                properties.setProperty("password", password);
                connection = driver.connect("jdbc:mysql://" + address + "/" + database, properties);
            } catch (SQLException e) {
                plugin.getLogger().severe("Failed to Connect MySQL Database! [code: 31]");
                e.printStackTrace();
            }
        }

        return connection;
    }



    /*public Connection getConnection() {
        Connection connection = null;
        h2url = "jdbc:h2:file:" + plugin.getDataFolder().getAbsolutePath() + "/database/database";

        if (mainConfig.getStorageType().equalsIgnoreCase("h2")) {
            try {
                Class.forName("org.h2.Driver");
                connection = DriverManager.getConnection(h2url);
            } catch (ClassNotFoundException e) {
                plugin.getLogger().severe("H2 Driver Not Found! [code: 06]");
                e.printStackTrace();
            } catch (SQLException e) {
                plugin.getLogger().severe("Failed to Connect H2 Database! [code: 07]");
                e.printStackTrace();
            }
        } else if (mainConfig.getStorageType().equalsIgnoreCase("mysql")) {
            String address = mainConfig.getMysqlAddress();
            String database = mainConfig.getMysqlDatabase();
            String username = mainConfig.getMysqlUsername();
            String password = mainConfig.getMysqlPassword();

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection("jdbc:mysql://" + address + "/" + database, username, password);
            } catch (ClassNotFoundException e) {
                plugin.getLogger().severe("MySQL Driver Not Found! [code: 30]");
                e.printStackTrace();
            } catch (SQLException e) {
                plugin.getLogger().severe("Failed to Connect MySQL Database! [code: 31]");
                e.printStackTrace();
            }
        }

        return connection;
    }*/

    /*public Connection getConnection() {
        Connection connection = null;
        h2url = "jdbc:h2:file:" + plugin.getDataFolder().getAbsolutePath() + "/database/database";

        if (mainConfig.getStorageType().equalsIgnoreCase("h2")){

            try {
                Class.forName("org.h2.Driver");
                connection = DriverManager.getConnection(h2url);
            } catch (ClassNotFoundException e) {
                plugin.getLogger().severe("Database Driver Not Found ! [code : 06]");
            } catch (SQLException e) {
                plugin.getLogger().severe("Failed to Connect H2 Database ! [code : 07]");
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
                plugin.getLogger().severe("MySql Driver Not Found ! [code : 30]");
                e.printStackTrace();
            } catch (SQLException e) {
                plugin.getLogger().severe("Failed to Connect Mysql Database ! [code : 31]");
                e.printStackTrace();
            }

        }

        return connection;
    }

     */

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
            plugin.getLogger().severe("Database Preparation Error Detected! [code : 08]");
            ex.printStackTrace();
        }
    }

}
