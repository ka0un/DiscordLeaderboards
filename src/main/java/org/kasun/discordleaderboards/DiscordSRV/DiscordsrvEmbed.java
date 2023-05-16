package org.kasun.discordleaderboards.DiscordSRV;

import github.scarsz.discordsrv.dependencies.jda.api.EmbedBuilder;
import github.scarsz.discordsrv.dependencies.jda.api.entities.MessageEmbed;

import java.awt.*;


public class DiscordsrvEmbed {
    private String embedTitle;
    private String embedColour;
    private String embedFooter;
    private String embedImage;
    private String embedThumbnail;
    private String embedDescription;
    private MessageEmbed messageEmbed;


    public MessageEmbed getMessageEmbed() {

        EmbedBuilder embedBuilder = new EmbedBuilder();

        if (embedTitle != null && !embedTitle.equals("") && !embedTitle.equals("-")){
            embedBuilder.setTitle(embedTitle);
        }

        if (embedColour != null && !embedColour.equals("") && !embedColour.equals("-")){
            embedBuilder.setColor(Color.decode(embedColour));
        }

        if (embedFooter != null && !embedFooter.equals("") && !embedFooter.equals("-")){
            embedBuilder.setFooter(embedFooter);
        }

        if (embedThumbnail != null && !embedThumbnail.equals("") && !embedThumbnail.equals("-")){
            embedBuilder.setThumbnail(embedThumbnail);
        }

        if (embedImage != null && !embedImage.equals("") && !embedImage.equals("-")){
            embedBuilder.setImage(embedImage);
        }

        if (embedDescription != null && !embedDescription.equals("") && !embedDescription.equals("-")){
            embedBuilder.setDescription(embedDescription);
        }

        MessageEmbed messageEmbed = embedBuilder.build();
        return messageEmbed;
    }


    //getters and setters

    public String getEmbedTitle() {
        return embedTitle;
    }

    public void setEmbedTitle(String embedTitle) {
        this.embedTitle = embedTitle;
    }

    public String getEmbedColour() {
        return embedColour;
    }

    public void setEmbedColour(String embedColour) {
        this.embedColour = embedColour;
    }

    public String getEmbedFooter() {
        return embedFooter;
    }

    public void setEmbedFooter(String embedFooter) {
        this.embedFooter = embedFooter;
    }

    public String getEmbedImage() {
        return embedImage;
    }

    public void setEmbedImage(String embedImage) {
        this.embedImage = embedImage;
    }

    public String getEmbedThumbnail() {
        return embedThumbnail;
    }

    public void setEmbedThumbnail(String embedThumbnail) {
        this.embedThumbnail = embedThumbnail;
    }

    public String getEmbedDescription() {
        return embedDescription;
    }

    public void setEmbedDescription(String embedDescription) {
        this.embedDescription = embedDescription;
    }


}
