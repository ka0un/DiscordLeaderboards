package org.kasun.discordleaderboards;

import github.scarsz.discordsrv.DiscordSRV;
import github.scarsz.discordsrv.api.commands.PluginSlashCommand;
import github.scarsz.discordsrv.api.commands.SlashCommand;
import github.scarsz.discordsrv.api.commands.SlashCommandProvider;
import github.scarsz.discordsrv.dependencies.jda.api.entities.MessageEmbed;
import github.scarsz.discordsrv.dependencies.jda.api.events.interaction.SlashCommandEvent;
import github.scarsz.discordsrv.dependencies.jda.api.interactions.commands.OptionType;
import github.scarsz.discordsrv.dependencies.jda.api.interactions.commands.build.CommandData;
import github.scarsz.discordsrv.dependencies.jda.api.interactions.commands.build.OptionData;
import github.scarsz.discordsrv.dependencies.jda.api.interactions.commands.build.SubcommandData;
import jdk.jfr.internal.tool.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.kasun.discordleaderboards.Commands.WebhookTestCommand;
import org.kasun.discordleaderboards.Commands.createCommand;
import org.kasun.discordleaderboards.Commands.forceLeaderboardSend;
import org.kasun.discordleaderboards.Commands.viewCommand;
import org.kasun.discordleaderboards.Database.Database;
import org.kasun.discordleaderboards.Database.UserData;
import org.kasun.discordleaderboards.Listeners.DiscordSRVListener;
import org.kasun.discordleaderboards.Listeners.PlayerJoin;
import org.kasun.discordleaderboards.Listeners.PlayerQuit;
import org.kasun.discordleaderboards.Schedules.LeaderboardSchedule;
import org.kasun.discordleaderboards.Utils.*;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;


public final class DiscordLeaderboards extends JavaPlugin implements Listener, SlashCommandProvider {

    private final DiscordSRVListener discordsrvListener = new DiscordSRVListener(this);

    private static String h2url;

    private static DiscordLeaderboards instance;

    @Override
    public void onEnable() {
        StartMessage.sendStartMessage();
        getConfig().options().copyDefaults();
        saveDefaultConfig();
        getCommand("dl-testwebhook").setExecutor(new WebhookTestCommand());
        getCommand("dl-forcesend").setExecutor(new forceLeaderboardSend());
        getCommand("dl-create").setExecutor(new createCommand());
        getCommand("dl-view").setExecutor(new viewCommand());
        getServer().getPluginManager().registerEvents(new PlayerJoin(), this);
        getServer().getPluginManager().registerEvents(new PlayerQuit(), this);



        h2url = "jdbc:h2:file:" + getDataFolder().getAbsolutePath() + "\\database\\database";
        Database.initializeDatabase();

        File configFile = MainConfig.getConfigFile();
        FileConfiguration config = MainConfig.getConfig();
        List<String> itemList = MainConfig.getLeaderboardsList();
        int scheduleDelay = MainConfig.getScheduleDelayMins();

        instance = this;


        for (String lb : itemList) {
            CustomConfig c1 = new CustomConfig(lb);
            c1.setup();
            c1.get().options().copyDefaults(true);
            c1.save();
        }

        //example leaderboard generate
        if (config.getBoolean("firsttime")) {
            Leaderboard.createexampleLeaderboard();
            config.set("firsttime", false);
            try {
                config.save(configFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            new BukkitRunnable() {
                @Override
                public void run() {
                    // This code will run every x minutes
                    List<String> itemList = config.getStringList("leaderboards");

                    for (String leaderboard : itemList) {
                        LeaderboardSchedule.runLeaderboardSchedule(leaderboard);
                    }
                }
            }.runTaskTimerAsynchronously(this, 0L, 20L * 60 * scheduleDelay);
        }

        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "=========================================");

        if (getServer().getPluginManager().isPluginEnabled("DiscordSRV")){
            DiscordSRV.api.subscribe(discordsrvListener);
        }

    }





    @Override
    public void onDisable() {
        try{
            Database.getConnection().close();
        }catch (SQLException e){

        }
        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "[Dleaderboards] " + ChatColor.GRAY + "Plugin Disconnected From Database...");
        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "[Dleaderboards] " + ChatColor.GRAY + "Plugin ShutDown");
    }



    @Override
    public Set<PluginSlashCommand> getSlashCommands() {

        CommandData commandData = new CommandData("leaderboard", "view leaderboards");
        List<String> itemList = MainConfig.getLeaderboardsList();
        List<OptionData> options = new ArrayList<>();
        OptionData dropdownOption = new OptionData(OptionType.INTEGER, "leaderboard", "Dropdown Option Description", true);

        if (itemList.size() != 0) {
            for (String subcommandName : itemList) {
                dropdownOption.addChoice(subcommandName, itemList.indexOf(subcommandName));
            }
        }
        options.add(dropdownOption);
        commandData.addOptions(options);




        return new HashSet<>(Arrays.asList(

                // ping pong
                new PluginSlashCommand(this, new CommandData("ping", "A classic match of ping pong")),

                // bests
                new PluginSlashCommand(this, commandData)

        ));


    }



    @SlashCommand(path = "leaderboard")
    public void bestPlugin(SlashCommandEvent event) {
        List<String> itemList = MainConfig.getLeaderboardsList();
        int option = (int) event.getOption("leaderboard").getAsDouble();
        String replay = Leaderboard.toString(itemList.get(option));
        MessageEmbed messageEmbed = SrvEmbeds.getMessageEmbed(itemList.get(option));
        event.replyEmbeds(messageEmbed).queue();
    }




    @SlashCommand(path = "ping")
    public void pingCommand(SlashCommandEvent event) {
        event.reply("Pong!").queue();
    }



    public static String getH2url() {
        return h2url;
    }

    public static DiscordLeaderboards getInstance() {
        return instance;
    }

}
