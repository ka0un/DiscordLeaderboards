package org.kasun.discordleaderboards.DiscordSRV;

import github.scarsz.discordsrv.dependencies.jda.api.EmbedBuilder;
import github.scarsz.discordsrv.dependencies.jda.api.entities.MessageEmbed;
import org.kasun.discordleaderboards.Database.UserData;
import org.kasun.discordleaderboards.Utils.Leaderboard;

import java.awt.*;


public class SrvEmbeds {
    public static MessageEmbed getMessageEmbed(String leaderboard) {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        Leaderboard lb = new Leaderboard(leaderboard);

        String embedTitle = lb.getEmbedTitle();
        String embedColour = lb.getEmbedColour();
        String embedFooter = lb.getEmbedFooter();
        String embedImage = lb.getEmbedImage();
        String embedThumbnail = lb.getEmbedThumbnail();

        if (embedTitle != null && !embedTitle.equals("") && !embedTitle.equals("-")){
            embedBuilder.setTitle(embedTitle);
            System.out.println("title added");
        }

        if (embedColour != null && !embedColour.equals("") && !embedColour.equals("-")){
            embedBuilder.setColor(Color.decode(embedColour));
            System.out.println("colour added");
        }

        if (embedFooter != null && !embedFooter.equals("") && !embedFooter.equals("-")){
            embedBuilder.setFooter(embedFooter);
            System.out.println("embed footer added");
        }


        if (embedThumbnail != null && !embedThumbnail.equals("") && !embedThumbnail.equals("-")){
            embedBuilder.setThumbnail(embedThumbnail);
            System.out.println("embed Thumb added");
        }

        if (embedImage != null && !embedImage.equals("") && !embedImage.equals("-")){
            embedBuilder.setImage(embedImage);
            System.out.println("embed image added");
        }

        String description = "```" + UserData.gettoplistString(lb.getPlaceholder(), lb.getTop()) + "```";
        embedBuilder.setDescription(description);

        MessageEmbed messageEmbed = embedBuilder.build();
        return messageEmbed;
    }
}
