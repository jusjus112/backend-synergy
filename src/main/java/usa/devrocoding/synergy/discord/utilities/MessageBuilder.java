package usa.devrocoding.synergy.discord.utilities;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;

import java.awt.*;

public class MessageBuilder {

    private EmbedBuilder eb;

    public MessageBuilder(String title, String description){
        this.eb = new EmbedBuilder();

        this.eb.setTitle(title);
        this.eb.setDescription(description);
        this.eb.setColor(Color.BLUE);
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
        this.eb.addField(" ", null, false);
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

}
