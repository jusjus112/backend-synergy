package usa.devrocoding.synergy.spigot.changelog.object;

import lombok.Getter;
import org.bukkit.entity.Player;
import usa.devrocoding.synergy.spigot.Core;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import usa.devrocoding.synergy.spigot.utilities.BookBuilder;

@Getter
public class Changelog {

    private final String title;
    private final Date date;
    private final Date showTill;
    private final List<String> pages;
    private final BookBuilder bookBuilder;

    public Changelog(String title, Date date, Date showTill){
        this(title, date, showTill, new ArrayList<>());
    }

    public Changelog(String title, Date date, Date showTill, List<String> pages){
        this.date = date;
        this.pages = pages;
        this.title = title;
        this.showTill = showTill;

        this.bookBuilder = new BookBuilder();
        this.bookBuilder.setPages(pages);

        Core.getPlugin().getChangelogManager().addChangelog(this);
    }

    public String getNiceDate(){
        return new SimpleDateFormat("MMM dd, yyyy - HH:mm aa").format(date);
    }

    public void open(Player player){
        this.bookBuilder.open(player);
    }

}
