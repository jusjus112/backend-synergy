package usa.devrocoding.synergy.spigot.changelog.object;

import lombok.Getter;
import lombok.Setter;
import usa.devrocoding.synergy.spigot.Core;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Changelog {

    @Getter @Setter
    private String title;
    @Getter @Setter
    private Date date;
    @Getter @Setter
    private int order;
    @Getter @Setter
    private List<String> lines;

    public Changelog(String title, int order, Date date){
        new Changelog(title, order, date, new ArrayList<>());
    }

    public Changelog(String title, int order, Date date, List<String> lines){
        this.date = date;
        this.lines = lines;
        this.title = title;
        this.order = order;

        Core.getPlugin().getChangelogManager().addChangelog(this);
    }

}
