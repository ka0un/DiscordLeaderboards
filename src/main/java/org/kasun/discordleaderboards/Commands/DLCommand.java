package org.kasun.discordleaderboards.Commands;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.kasun.discordleaderboards.Configs.MainConfig;
import org.kasun.discordleaderboards.Database.UserData;
import org.kasun.discordleaderboards.DiscordLeaderboards;
import org.kasun.discordleaderboards.Leaderboard.Leaderboard;
import org.kasun.discordleaderboards.Leaderboard.LeaderboardConfig;
import org.kasun.discordleaderboards.Utils.DiscordWebhook;
import org.kasun.discordleaderboards.Utils.PlayerUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;

public class DLCommand implements TabExecutor {

    private final CommandsManager commandsManager;

    public DLCommand(CommandsManager commandsManager) {
        this.commandsManager = commandsManager;
    }

    private final DiscordLeaderboards plugin = DiscordLeaderboards.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 0) {
            return true;
        }
        //Webhook Test Command
        if (args[0].equalsIgnoreCase("testwebhook")) {
            if (!sender.hasPermission("dl.testwebhook") && !sender.hasPermission("dl.admin")) {
                sender.sendMessage(ChatColor.AQUA + "[Dleaderboards] " + ChatColor.RED + "No Permission ! [dl.testwebhook], [dl.admin]");
                return true;
            }
            String url = (String) plugin.getConfig().get("webhook-url");
            DiscordWebhook w = new DiscordWebhook(url);
            DiscordWebhook.EmbedObject embed = new DiscordWebhook.EmbedObject();
            if (url == null || url.equalsIgnoreCase("-")) {
                sender.sendMessage(ChatColor.AQUA + "[Dleaderboards] " + ChatColor.RED + "Webhook Url not Configured");
                return true;
            }
            embed.setTitle("Dleaderboards");
            embed.setDescription("Your Webhook is Working");
            w.addEmbed(embed);
            sender.sendMessage(ChatColor.AQUA + "[Dleaderboards] " + ChatColor.GRAY + "Sending Webhook Message....");
            try {
                w.execute();
            } catch (FileNotFoundException e) {
                sender.sendMessage(ChatColor.AQUA + "[Dleaderboards] " + ChatColor.RED + "Invalid Webhook Url [code : 04]");
            } catch (IOException ee) {
                sender.sendMessage(ChatColor.AQUA + "[Dleaderboards] " + ChatColor.RED + "embed not ready yet, internal plugin issue pleace contact plugin developer [code : 05]");
            }
        }
        //Force Leaderboard Send
        if (args[0].equalsIgnoreCase("forcesend")) {
            if (!sender.hasPermission("dl.forcesend") && !sender.hasPermission("dl.admin")) {
                sender.sendMessage(ChatColor.AQUA + "[Dleaderboards] " + ChatColor.RED + "No Permission ! [dl.forcesend], [dl.admin]");
                return true;
            }
            if (args.length != 2) {
                sender.sendMessage(ChatColor.AQUA + "[Dleaderboards] " + ChatColor.RED + "Wrong Command Usage !");
                sender.sendMessage(ChatColor.AQUA + "[Dleaderboards] " + ChatColor.GRAY + "/dl forcesend leaderboard");
                return true;
            }
            CompletableFuture.runAsync(() -> {
                Leaderboard leaderboard = new Leaderboard(args[1]);
                leaderboard.send();
            });
            sender.sendMessage(ChatColor.AQUA + "[Dleaderboards] " + ChatColor.GREEN + "Leaderboard Sent!");
        }
        //Create Command
        if (args[0].equalsIgnoreCase("create")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(ChatColor.AQUA + "[Dleaderboards] " + ChatColor.RED + "You cant use this command here");
                return true;
            }
            Player p = (Player) sender;
            if (args.length != 5) {
                p.sendMessage(ChatColor.AQUA + "[Dleaderboards] " + ChatColor.RED + "Wrong Command Usage !");
                p.sendMessage(ChatColor.AQUA + "[Dleaderboards] " + ChatColor.GRAY + "/dl create name 5 %placeholder% Daily");
            }
            //name top placeholder delay
            if (!p.hasPermission("dl.create") && !p.hasPermission("dl.admin")) {
                p.sendMessage(ChatColor.AQUA + "[Dleaderboards] " + ChatColor.RED + "No Permission ! [dl.create], [dl.admin]");
                return true;
            }
            try {
                Double.parseDouble(PlaceholderAPI.setPlaceholders(p, args[3]));
                new Leaderboard(args[1], Integer.parseInt(args[2]), args[3], Leaderboard.WebhookDelay.valueOf(args[4]));
                p.sendMessage(ChatColor.AQUA + "[Dleaderboards] " + ChatColor.GREEN + "Starting Leaderboard Setup!");
                plugin.reloadConfig();
                UserData userData = new UserData(p, args[2]);
                userData.addToDatabase();
                p.sendMessage(ChatColor.AQUA + "[Dleaderboards] " + ChatColor.YELLOW + "Starting to Sync Offline Player Data... Please Wait!");

                Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
                    userData.addUserDataToDBAllPlayersThisPlaceholder();
                    Bukkit.getScheduler().runTask(plugin, () -> {
                        p.sendMessage(ChatColor.AQUA + "[Dleaderboards] " + ChatColor.YELLOW + "Synchronization Complete !");
                        p.sendMessage(ChatColor.AQUA + "[Dleaderboards] " + ChatColor.GREEN + "Leaderboard Created!");
                        p.sendMessage(ChatColor.AQUA + "[Dleaderboards] " + ChatColor.GRAY + "you can change the settings from plugins\\DiscordLeaderboards\\leaderboard\\" + args[1] + ".yml");
                    });
                });


                //getting random offline player
                List<OfflinePlayer> players = PlayerUtils.getAllPlayers();
                players.remove(p);
                Random random = new Random();
                int randomIndex = random.nextInt(players.size());
                OfflinePlayer randomPlayer = players.get(randomIndex);

                //checking placeholder if they supports offline players
                try {
                    Double.parseDouble(PlaceholderAPI.setPlaceholders(randomPlayer, args[2]));
                } catch (NumberFormatException ex) {
                    p.sendMessage(ChatColor.AQUA + "[Dleaderboards] " + ChatColor.RED + "[ERROR] " + args[3] + " may not support offline players. [code : 17]");
                }

            } catch (NumberFormatException e) {
                p.sendMessage(ChatColor.AQUA + "[Dleaderboards] " + ChatColor.RED + "Placeholder " + args[3] + " Unsupported, Expansion Not Installed or Doesn't output Number value");
            }
        }
        //View Command
        if (args[0].equalsIgnoreCase("view")) {
            if (!sender.hasPermission("dl.view") && !sender.hasPermission("dl.admin")) {
                sender.sendMessage(ChatColor.AQUA + "[Dleaderboards] " + ChatColor.RED + "No Permission ! [dl.view], [dl.admin]");
                return true;
            }
            if (args.length != 2) {
                sender.sendMessage(ChatColor.AQUA + "[Dleaderboards] " + ChatColor.RED + "Wrong Command Usage !");
                sender.sendMessage(ChatColor.AQUA + "[Dleaderboards] " + ChatColor.GRAY + "/dl view leaderboard");
                return true;
            }
            CompletableFuture.runAsync(() -> {
                Leaderboard leaderboard = new Leaderboard(args[1]);
                String leaderboardstring = leaderboard.toString();

                sender.sendMessage(ChatColor.GRAY + leaderboardstring);
            });
        }
        //Sync All Players
        if (args[0].equalsIgnoreCase("syncall")) {
            if (!(sender instanceof Player)) {
                plugin.getLogger().info(ChatColor.AQUA + "[Dleaderboards] " + ChatColor.RED + "you cant use that command here.");
                return true;
            }
            Player p = (Player) sender;

            if (!p.hasPermission("dl.syncall") && !p.hasPermission("dl.admin")) {
                p.sendMessage(ChatColor.AQUA + "[Dleaderboards] " + ChatColor.RED + "No Permission ! [dl.syncall], [dl.admin]");
                return true;
            }

            p.sendMessage(ChatColor.AQUA + "[Dleaderboards] " + ChatColor.YELLOW + "Synchronization Starting...");
            UserData userData = new UserData();
            Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
                userData.addUserDataToDBAllPlayersAllPlaceholders();
                Bukkit.getScheduler().runTask(plugin, () -> {
                    p.sendMessage(ChatColor.AQUA + "[Dleaderboards] " + ChatColor.YELLOW + "Synchronization Complete !");
                });
            });

            //getting random offline player
            List<OfflinePlayer> players = PlayerUtils.getAllPlayers();
            players.remove(p);
            Random random = new Random();
            int randomIndex = random.nextInt(players.size());
            OfflinePlayer randomPlayer = players.get(randomIndex);

            //checking placeholders if they supports offline players
            MainConfig mainConfig = new MainConfig();
            List<String> lblist = mainConfig.getLeaderboardsList();
            for (String lbname : lblist) {
                LeaderboardConfig leaderboardConfig = new LeaderboardConfig(lbname);
                String ph = leaderboardConfig.getPlaceholder();
                try {
                    Double.parseDouble(PlaceholderAPI.setPlaceholders(randomPlayer, ph));
                } catch (NumberFormatException ex) {
                    p.sendMessage(ChatColor.AQUA + "[Dleaderboards] " + ChatColor.RED + "[ERROR] " + ph + " may not support offline players. [code : 17]");
                }
            }
        }
        if (args[0].equalsIgnoreCase("reload")) {
            if (!sender.hasPermission("dl.reload") && !sender.hasPermission("dl.admin")) {
                sender.sendMessage(ChatColor.AQUA + "[Dleaderboards] " + ChatColor.RED + "No Permission ! [dl.reload], [dl.admin]");
                return true;
            }
            commandsManager.getMainManager().reload();
            sender.sendMessage(ChatColor.AQUA + "[Dleaderboards] " + ChatColor.GREEN + "Reloaded!");
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 1) {
            List<String> arguments = new ArrayList<>();
            arguments.add("testwebhook");
            arguments.add("forcesend");
            arguments.add("create");
            arguments.add("view");
            arguments.add("syncall");
            return arguments;
        }
        if (args[0].equalsIgnoreCase("forcesend")) {
            if (args.length == 2) {
                MainConfig mainConfig = new MainConfig();
                return mainConfig.getLeaderboardsList();
            }
        }
        if (args[0].equalsIgnoreCase("create")) {
            if (args.length == 2) {
                List<String> arguments = new ArrayList<>();
                arguments.add("name");
                return arguments;
            }
            if (args.length == 3) {
                List<String> arguments = new ArrayList<>();
                arguments.add("3");
                arguments.add("5");
                arguments.add("10");
                arguments.add("15");
                arguments.add("20");
                return arguments;
            }
            if (args.length == 4) {
                List<String> arguments = new ArrayList<>();
                arguments.add("%placeholder%");
                return arguments;
            }
            if (args.length == 5) {
                List<String> arguments = new ArrayList<>();
                arguments.add("None");
                arguments.add("Live");
                arguments.add("Hourly");
                arguments.add("Daily");
                arguments.add("Weekly");
                arguments.add("Monthly");
                return arguments;
            }
        }
        if (args[0].equalsIgnoreCase("view")) {
            if (args.length == 2) {
                MainConfig mainConfig = new MainConfig();
                return mainConfig.getLeaderboardsList();
            }
        }
        return null;
    }
}
