package usa.devrocoding.synergy.spigot.scoreboard.listener;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.NameTagVisibility;
import org.bukkit.scoreboard.Team;
import usa.devrocoding.synergy.assets.Synergy;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.scoreboard.ScoreboardManager;
import usa.devrocoding.synergy.spigot.scoreboard.ZylemBoard;
import usa.devrocoding.synergy.spigot.user.event.UserLoadEvent;

public class ScoreboardListener implements Listener {

	private final Core plugin;
	private final ScoreboardManager scoreboardManager;

	public ScoreboardListener(Core plugin, ScoreboardManager scoreboardManager) {
		this.plugin = plugin;
		this.scoreboardManager = scoreboardManager;

		plugin.getRunnableManager().runTaskTimer("Nametag Task", plugin1 -> {
			scoreboardManager.getScoreboards().values().forEach(scoreboardSession -> {
				boolean invis = false;
				
				for (PotionEffect effect : scoreboardSession.getPlayer().getActivePotionEffects()) {
					if (effect.getType().equals(PotionEffectType.INVISIBILITY)) {
						invis = true;
					}
				}
				
				if(!invis) {
					scoreboardManager.getScoreboards().values().forEach(ss -> {
						Team team = ss.getScoreboard().getTeam(ZylemBoard.getTeamName(plugin1.getUserManager().getUser(scoreboardSession.getPlayer())));
						
						if(team != null) {
							team.setNameTagVisibility(NameTagVisibility.ALWAYS);
						}
					});
				} else {;
					scoreboardManager.getScoreboards().values().forEach(ss -> {
						Team team = ss.getScoreboard().getTeam(ZylemBoard.getTeamName(plugin1.getUserManager().getUser(scoreboardSession.getPlayer())));
						
						if(team != null) {
							team.setNameTagVisibility(NameTagVisibility.HIDE_FOR_OTHER_TEAMS);
						}
					});
				}
				
			});
		}, 0, 20);
		
	}

//	@EventHandler (priority = EventPriority.LOWEST)
//	public void onPlayerLoadBoard(UserLoadEvent e) {
//		try{
//			if (scoreboardManager.getDefaultPolicy() == null) {
//				return;
//			}
//
//			Bukkit.getScheduler().runTaskLater(plugin, () -> {
//				scoreboardManager.getScoreboards().put(e.getUser().getUuid(), new ZylemBoard(plugin, e.getUser(),
//						scoreboardManager.getDefaultPolicy()));
//				scoreboardManager.update(true);
//			}, 15);//TODO: may cause issues if so change to 15 instead of 5
//		}catch (Exception exception){
//			Synergy.error("Error on user joining set scoreboard.");
//			Synergy.error(exception.getMessage());
//		}
//	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e) {
		if (scoreboardManager.getScoreboards().containsKey(e.getPlayer().getUniqueId()))
			scoreboardManager.getScoreboards().get(e.getPlayer().getUniqueId()).unregister();
		scoreboardManager.getScoreboards().remove(e.getPlayer().getUniqueId());
	}

}
