package usa.devrocoding.synergy.proxy.announcements;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import usa.devrocoding.synergy.proxy.Core;
import usa.devrocoding.synergy.proxy.ProxyModule;
import usa.devrocoding.synergy.proxy.announcements.thread.Announcer;
import usa.devrocoding.synergy.proxy.files.ProxyYMLFile;

public class AnnouncerManager extends ProxyModule {

  private List<String> announcementsData;
  private Iterator<String> announcements;
  private final ProxyYMLFile file;

  public AnnouncerManager(Core plugin){
    super(plugin, "Announcements Manager", true);

    getPlugin().getProxy().getScheduler().schedule(getPlugin(), new Announcer(this),
        1, 6, TimeUnit.MINUTES);

    this.file = new ProxyYMLFile(getPlugin(), getPlugin().getDataFolder(), "announcements");

    this.file.set(new HashMap<String, Object>(){{
      put("announcements", new ArrayList<String>(){{
        add("&4This is a test announcement");
      }});
    }});

    this.announcementsData = this.retrieveAnnouncements();
    this.announcements = this.announcementsData.iterator();
  }

  @Override
  public void reload() {
    this.announcementsData = this.retrieveAnnouncements();
    this.announcements = this.announcementsData.iterator();
  }

  @Override
  public void deinit() {

  }

  private List<String> retrieveAnnouncements(){
    if (this.file.getConfiguration().contains("announcements")){
      return this.file.getConfiguration().getStringList("announcements");
    }
    return Lists.newArrayList();
  }

  private String getRandomAnnouncement(){
    if (!this.announcements.hasNext()){
      this.announcements = this.announcementsData.iterator();
    }
    return this.announcements.next();
  }

  private TextComponent line(){
    TextComponent textComponent = new TextComponent(" \n");
    textComponent.setColor(ChatColor.GRAY);
    return textComponent;
  }

  public void announcement(ProxiedPlayer proxiedPlayer){
    String announcement = this.getRandomAnnouncement();
    TextComponent baseComponent = new TextComponent();
    baseComponent.addExtra(this.line());
    baseComponent.addExtra(new TextComponent(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD +"DID YOU KNOW!?"));
    baseComponent.addExtra("\n");

    TextComponent message = new TextComponent(ChatColor.translateAlternateColorCodes('&', announcement));
    baseComponent.addExtra(message);

    baseComponent.addExtra(this.line());
    proxiedPlayer.sendMessage(baseComponent);
  }
}
