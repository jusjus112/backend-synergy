package usa.devrocoding.synergy.proxy.assets.listener;

import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import usa.devrocoding.synergy.includes.PluginMessageType;
import usa.devrocoding.synergy.discord.Discord;
import usa.devrocoding.synergy.proxy.plugin_messaging.object.SynergyPMEvent;

public class AutomatedReportsListener implements Listener {

  @EventHandler
  public void onIncomingPM(SynergyPMEvent e){
    if (e.getType() == PluginMessageType.AUTOMATED_ERROR) {
      String fileName = e.getData()[0];
      String exceptionMessage = e.getData()[1];
      String exception = e.getData()[2];

      for (int i = 2; i < e.getData().length; i++) {
//        if (i == 6){
//          stacktraceBuilder.append("......");
//          stacktraceBuilder.append("See full stacktrace at: ");
//          stacktraceBuilder.append("https://logs.mirageprisons.net/errors/").append(fileName);
//          break;
//        }
//        if (i > 2){
//          stacktraceBuilder.append("\n");
//        }
//        stacktraceBuilder.append(e.getData()[i]);
      }

      Discord.getDiscordManager().automatedError(exceptionMessage, exception,
          "https://logs.mirageprisons.net/errors/" + fileName
      );
    }
  }

}
