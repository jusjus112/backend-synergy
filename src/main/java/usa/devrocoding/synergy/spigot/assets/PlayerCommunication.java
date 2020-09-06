package usa.devrocoding.synergy.spigot.assets;

import java.util.Arrays;
import lombok.Getter;
import org.bukkit.entity.Player;
import usa.devrocoding.synergy.spigot.user.object.SynergyUser;
import usa.devrocoding.synergy.spigot.utilities.UtilString;

public class PlayerCommunication {

  @Getter
  private final String prefix;
  private final String subPrefix = "§d§l</> §7";

  /**
   * Creat the PlayerCommunication with the given prefix.
   * @param prefix The prefix for the messages
   */
  public PlayerCommunication(String prefix){
    this.prefix = prefix;
  }

  public PlayerCommunication(){
    this("");
  }

  /**
   * Sends a message to the player with the global info format
   * @apiNote Multiple messages are formatted by single lines
   * @param player to send the message to.
   * @param messages messages to be send.
   */
  public void info(Player player, String... messages){
    Arrays.stream(messages).forEach(s -> {
      player.sendMessage(
          this.subPrefix + this.prefix + ": " + C.INFO + C.colorize(s)
      );
    });
  }

  /**
   * Sends a message to the player with the global info format
   * @apiNote Multiple messages are formatted by single lines
   * @param synergyUser to send the message to.
   * @param messages messages to be send.
   */
  public void info(SynergyUser synergyUser, String... messages){
    this.info(synergyUser.getPlayer(), messages);
  }

  /**
   * Sends a message to the player with the global error format
   * @apiNote Multiple messages are formatted by single lines
   * @param player to send the message to
   * @param messages messages to be send.
   */
  public void error(Player player, String... messages){
    Arrays.stream(messages).forEach(s -> {
      player.sendMessage(
          C.ERROR + "⚠" + this.subPrefix + this.prefix + ": " + C.ERROR + C.colorize(s)
      );
    });
  }

  /**
   * Sends a message to the player with the global info format
   * @apiNote Multiple messages are formatted by single lines
   * @param synergyUser to send the message to
   * @param messages messages to be send.
   */
  public void error(SynergyUser synergyUser, String... messages){
    this.error(synergyUser.getPlayer(), messages);
  }

  /**
   * Sends a message to the player with the global warning format
   * @apiNote Multiple messages are formatted by single lines.
   * @param player to send the message to
   * @param messages messages to be send.
   */
  public void warning(Player player, String... messages){
    Arrays.stream(messages).forEach(s -> {
      player.sendMessage(
          C.PLUGIN + "[WARNING] " + this.subPrefix + this.prefix + ": " + C.ERROR + C.colorize(s)
      );
    });
  }

  /**
   * Sends a message to the player with the global warning format
   * @apiNote Multiple messages are formatted by single lines.
   * @param synergyUser to send the message to
   * @param messages messages to be send.
   */
  public void warning(SynergyUser synergyUser, String... messages){
    this.warning(synergyUser.getPlayer(), messages);
  }

  /**
   * Sends a announcement to the player with our global format.
   * @apiNote Multiple messages are formatted into the same announcement!
   * @param player to send the message to.
   * @param messages messages to include.
   */
  public void announcement(Player player, String... messages){
    player.sendMessage("  ");
    player.sendMessage(UtilString.centered(this.prefix + " &C&LANNOUNCEMENT"));
    Arrays.stream(messages).forEach(s -> player.sendMessage(UtilString.centered(s)));
    player.sendMessage("  ");
  }

  /**
   * Sends a announcement to the player with our global format.
   * @apiNote Multiple messages are formatted into the same announcement!
   * @param synergyUser to send the message to.
   * @param messages messages to include.
   */
  public void announcement(SynergyUser synergyUser, String... messages){
    this.announcement(synergyUser.getPlayer(), messages);
  }

}
