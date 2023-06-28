Ok, so basically you'll need to get an instance of the plugin class (using Bukkit.getPluginManager().getPlugin or something similar) and then cast it to LeaderboardPlugin. From there, you can get the instance of the TopManager class, which you can then use the getBoards() method to get the list of boards. And the board names are the original placeholders (just without the %)

If you want to add ajleaderboards to your IDE, you can either use the plugin jar, or for maven/gradle you can add my repo (https://repo.ajg0702.us/releases is the url for it) then the dependency info is listed here: https://repo.ajg0702.us/#/releases/us/ajg0702/ajLeaderboards/2.6.8

It’s published only maven repo https://repo.ajg0702.us/

Use https://repo.ajg0702.us/releases for the repo url

Artifact info is on this page: https://repo.ajg0702.us/#/releases/us/ajg0702/ajLeaderboards/2.6.8 (on the right)

Get an instance of the plugin class using Bukkit.getPlugin then cast it to LeaderboardPlugin. From there, you can getTopManager() which has most of the things you would want to get