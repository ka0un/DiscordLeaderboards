package org.kasun.discordleaderboards.Database;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.kasun.discordleaderboards.Configs.MainConfig;
import org.kasun.discordleaderboards.Leaderboard.Leaderboard;
import org.kasun.discordleaderboards.Utils.PlayerUtils;
import org.kasun.discordleaderboards.Utils.SqlUtils;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class UserData {
    private MainConfig mainConfig = new MainConfig();
    private Database database = new Database();
    private Player player;
    private OfflinePlayer offlinePlayer;
    private String placeholder;
    private String username;
    private String uuid;
    private double value;
    String placeholderColumnName;


    //constructers
    public UserData() {}
    public UserData(Player player, String placeholder) {
        this.player = player;
        this.placeholder = placeholder;
        placeholderColumnName = placeholder.substring(1, placeholder.length() - 1);
        uuid = player.getUniqueId().toString();
        try{
            value = Double.parseDouble(PlaceholderAPI.setPlaceholders(player, placeholder));
        }catch (NumberFormatException e){
            value  = 0.0;
        }
        username = player.getName();
    }
    public UserData(OfflinePlayer offlinePlayer, String placeholder) {
        this.offlinePlayer = offlinePlayer;
        this.placeholder = placeholder;
        placeholderColumnName = placeholder.substring(1, placeholder.length() - 1);
        uuid = offlinePlayer.getUniqueId().toString();
        try{
            value = Double.parseDouble(PlaceholderAPI.setPlaceholders(offlinePlayer, placeholder));
        }catch (NumberFormatException e){
            value  = 0.0;
        }
        username = offlinePlayer.getName();
    }

    //One Player One Placeholder
    public void addToDatabase(){
        CompletableFuture.runAsync(() -> {
            SqlUtils.addUserDataToDatabase(placeholderColumnName, uuid, username, value);
        });
    }

    //getters and setters
    public String getUserName(){
        if (player != null){
            username = player.getName();
        } else if (offlinePlayer != null) {
            username = offlinePlayer.getName();
        }
        return username;
    }

    public String getUuid(){
        if (player != null){
            uuid = player.getUniqueId().toString();
        } else if (offlinePlayer != null) {
            uuid = offlinePlayer.getUniqueId().toString();
        }
        return uuid;
    }

    public double getValue(){
        if (player != null){
            value = Double.parseDouble(PlaceholderAPI.setPlaceholders(player, placeholder));
        } else if (offlinePlayer != null) {
            value = Double.parseDouble(PlaceholderAPI.setPlaceholders(offlinePlayer, placeholder));
        }
        return value;
    }

    //One Player All Placeholders
    public void addUserDataToDBAllPlaceholders(Player player){
        MainConfig mainConfig = new MainConfig();
        List<String> leaderboardnamelist = mainConfig.getLeaderboardsList();
        for (String lbname : leaderboardnamelist){
            Leaderboard leaderboard = new Leaderboard(lbname);
            UserData userData = new UserData(player, leaderboard.getConfig().getPlaceholder());
            userData.addToDatabase();
        }
    }
    //One Player All Placeholders
    public void addUserDataToDBAllPlaceholders(OfflinePlayer offlinePlayer){
        MainConfig mainConfig = new MainConfig();
        List<String> leaderboardnamelist = mainConfig.getLeaderboardsList();
        for (String lbname : leaderboardnamelist){
            Leaderboard leaderboard = new Leaderboard(lbname);
            String valuestring = PlaceholderAPI.setPlaceholders(offlinePlayer, leaderboard.getConfig().getPlaceholder());
            if (valuestring != null && valuestring != ""){
                UserData userData = new UserData(offlinePlayer, leaderboard.getConfig().getPlaceholder());
                userData.addToDatabase();
            }
        }
    }

    //All Players All Placeholders
    public void addUserDataToDBAllPlayersAllPlaceholders(){
        List<OfflinePlayer> players = PlayerUtils.getAllPlayers();
        for (OfflinePlayer player : players) {
            if (player != null){
                addUserDataToDBAllPlaceholders(player);
            }
        }

    }

    //All Players One Placeholder (Dont use Void Constructer for this METHODE)
    public void addUserDataToDBAllPlayersThisPlaceholder(){
        List<OfflinePlayer> players = PlayerUtils.getAllPlayers();
        for (OfflinePlayer player : players) {
            if (player != null){
                addToDatabase();
            }
        }

    }

    public String getPlaceholder() {
        return placeholder;
    }

}
