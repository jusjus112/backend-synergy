package usa.devrocoding.synergy.discord.utilities;

import jdk.internal.jline.internal.Nullable;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;

import java.awt.*;
import usa.devrocoding.synergy.discord.utilities.MessageBuilder.SetType;

public class MessageBuilder {

    private EmbedBuilder eb;

    public MessageBuilder(String message, SetType setType){
        this.eb = new EmbedBuilder();

        if (setType == MessageBuilder.SetType.TITLE){
            this.eb.setTitle(message);
        }else if (setType == MessageBuilder.SetType.DESCRIPTION){
            this.eb.setDescription(message);
        }
        this.eb.setColor(Color.MAGENTA);
    }

    public MessageBuilder(@Nullable String title, @Nullable String description){
        this.eb = new EmbedBuilder();

        if (title != null) {
            this.eb.setTitle(title);
        }
        if (description != null) {
            this.eb.setDescription(description);
        }
        this.eb.setColor(Color.MAGENTA);
    }

    public MessageBuilder addField(String title, String text, boolean inline){
        this.eb.addField(title, text, inline);
        return this;
    }

    public MessageBuilder appendDescription(String description){
        this.eb.appendDescription(description);
        return this;
    }

    public MessageBuilder addSpacer(){
        this.eb.addBlankField(true);
        return this;
    }

    public MessageBuilder setThumbnail(String url){
        this.eb.setThumbnail(url);
        return this;
    }

    public MessageBuilder setFooter(String text, String url){
        this.eb.setFooter(text, url);
        return this;
    }

    public MessageBuilder setFooter(String text){
        this.eb.setFooter(text, null);
        return this;
    }

    public MessageBuilder setAuthor(User user){
        this.eb.setAuthor(user.getName());
        return this;
    }

    public MessageBuilder overwriteColor(Color color){
        this.eb.setColor(color);
        return this;
    }

    public MessageEmbed build(){
        return this.eb.build();
    }

    public enum SetType{
        TITLE,
        DESCRIPTION
    }

}
