#Discord Leaderboards

Create Minecraft Leaderboards with PlaceholderAPI and Post it to Discord Via Webhooks or DiscordSRV

* Spigot - https://www.spigotmc.org/resources/discord-leaderboards-free-beta.109908/
* BuildByBit - soon
* Discord - https://dsc.gg/sundevs
* Bstats - https://bstats.org/plugin/bukkit/Discord%20Leaderboards/18497
* Trello - https://trello.com/b/3nEM0ADF/dleaderboard

###Dependencies

* PlaceholderAPI - Required
* DiscordSRV - Optional

###Commands And Permissions

* /DL-CREATE <name> <top> <%placeholder%> <Schedule> - Create Leaderboard
* /DL-VIEW <name> - Preview Leaderboard
* /DL-FORCESEND <name> - Force send leaderboard to Discord Webook
* /DL-TESTWEBHOOK - Test the Webhook Url
* /DL-SYNCALL - Sync all Offline Players Required Placeholder Values to Plugin Database

* DL.ADMIN - Permission To All Commands
* DL.<COMMAND> - Permission to Specific Command

###Features

* Unlimited Leaderboards
* Support 1.8.8 - 1.19.4 All Versions
* 100% Customizable Each Leaderboard
* H2V2 Embedded Database Support (Default)
* MYSQL Remote Database Support (Beta)
* Asynchronized Schedules (Hourly , Daily , Weekly , Monthly)
* Offline Player Support (For Some Placeholders)
* DiscordSRV Slash Command (/Leaderboard <leaderboardname>)
* Opensource And MIT LICENCE
* Embed Description Placeholders
* Support Discord

###Main Config
    
# ╔═══╗╔╗   ╔═══╗╔═══╗╔═══╗╔═══╗╔═══╗╔══╗ ╔═══╗╔═══╗╔═══╗╔═══╗╔═══╗
# ╚╗╔╗║║║   ║╔══╝║╔═╗║╚╗╔╗║║╔══╝║╔═╗║║╔╗║ ║╔═╗║║╔═╗║║╔═╗║╚╗╔╗║║╔═╗║
# ║║║║║║   ║╚══╗║║ ║║ ║║║║║╚══╗║╚═╝║║╚╝╚╗║║ ║║║║ ║║║╚═╝║ ║║║║║╚══╗
# ║║║║║║ ╔╗║╔══╝║╚═╝║ ║║║║║╔══╝║╔╗╔╝║╔═╗║║║ ║║║╚═╝║║╔╗╔╝ ║║║║╚══╗║
# ╔╝╚╝║║╚═╝║║╚══╗║╔═╗║╔╝╚╝║║╚══╗║║║╚╗║╚═╝║║╚═╝║║╔═╗║║║║╚╗╔╝╚╝║║╚═╝║
# ╚═══╝╚═══╝╚═══╝╚╝ ╚╝╚═══╝╚═══╝╚╝╚═╝╚═══╝╚═══╝╚╝ ╚╝╚╝╚═╝╚═══╝╚═══╝
# =================================================================
# Discord Leaderboards Plugin Configuration FREE VERSION
# Github https://github.com/KASUNHapangama/DiscordLeaderboards
# Discord https://dsc.gg/sundevs | KASUN#8771
# =================================================================
# STORAGE SETTINGS (H2) (MYSQL)
# =================================================================
# Available Options
# Local Databases - H2 (Default)
# Remote Databases - MYSQL
storage-method: h2

# Remote Mysql Database Settings
# you don't have to change anything if you are using H2
# adderss must contain port eg:- localhost:3306
address: localhost
database: minecraft
username: root
password: ''
# remote databases may case lag depends on connection delay
# =================================================================
# Main Settings
# =================================================================
# Delay between schedule check
# Default 5
# High Delay = Low Cpu Usage
# Low Delay = High Accenture Timings
scheduledelaymins: 5
# you have to list leaderboard name (filename) here for load them
# you can disable the leaderboard by removing them from this list
# all leaderboard configs must be inside the leaderboards folder
# and must follow default template as example leaderboard
leaderboards:
- example

# =================================================================
# Default Settings
# =================================================================
# You can change these values for each leaderboard in their own
# config file witch you can find inside leaderboards folder
webhook-url: '-' # Required !!!
webhook-avatar-url: https://cdn.discordapp.com/attachments/1074520108514431026/1104804320349802596/Board_3.png
webhook-user-name: DLeaderBoards

embed-title: Leaderboard # Required !!!
embed-url: '-'
embed-colour: '#00ffee'
embed-footer: '-'
embed-image: '-'
embed-thumbnail: '-'

# =================================================================
# Debug Settings
# =================================================================
# Reserved for Plugin Developer, Do not Change Anything Here
# It May Break Your Plugin
pluginversion: 0.0.1
firsttime: false

###Leaderboard Config

