package usa.devrocoding.synergy.proxy.announcements.thread;

import lombok.RequiredArgsConstructor;
import usa.devrocoding.synergy.proxy.announcements.AnnouncerManager;

@RequiredArgsConstructor
public class Announcer implements Runnable{

  private final AnnouncerManager announcerManager;

  @Override
  public void run() {
    if (!this.announcerManager.getPlugin().getProxy().getPlayers().isEmpty())
      this.announcerManager.getPlugin().getProxy().getPlayers().forEach(
          this.announcerManager::announcement);
  }
}
