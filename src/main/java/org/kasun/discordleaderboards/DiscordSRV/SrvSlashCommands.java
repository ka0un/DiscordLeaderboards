package org.kasun.discordleaderboards.DiscordSRV;

import github.scarsz.discordsrv.api.commands.PluginSlashCommand;
import github.scarsz.discordsrv.api.commands.SlashCommand;
import github.scarsz.discordsrv.api.commands.SlashCommandProvider;
import github.scarsz.discordsrv.dependencies.jda.api.entities.MessageEmbed;
import github.scarsz.discordsrv.dependencies.jda.api.events.interaction.SlashCommandEvent;
import github.scarsz.discordsrv.dependencies.jda.api.interactions.commands.OptionType;
import github.scarsz.discordsrv.dependencies.jda.api.interactions.commands.build.CommandData;
import github.scarsz.discordsrv.dependencies.jda.api.interactions.commands.build.OptionData;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.kasun.discordleaderboards.Utils.Leaderboard;
import org.kasun.discordleaderboards.Utils.MainConfig;

import java.util.*;

public class SrvSlashCommands implements Listener, SlashCommandProvider {
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
                new PluginSlashCommand(Bukkit.getServer().getPluginManager().getPlugin("DiscordLeaderboards"), new CommandData("ping", "A classic match of ping pong")),

                // bests
                new PluginSlashCommand(Bukkit.getServer().getPluginManager().getPlugin("DiscordLeaderboards"), commandData)

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
}
