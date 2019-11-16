package usa.devrocoding.synergy.spigot.plugin_messaging;

import lombok.Getter;
import usa.devrocoding.synergy.assets.Synergy;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.bot_sam.Sam;
import usa.devrocoding.synergy.spigot.plugin_messaging.event.SynergyReceiveEvent;
import usa.devrocoding.synergy.spigot.plugin_messaging.object.PluginMessageType;
import usa.devrocoding.synergy.spigot.user.object.MessageModification;
import usa.devrocoding.synergy.spigot.user.object.Rank;
import usa.devrocoding.synergy.spigot.user.object.SynergyUser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class PluginMessage {

    @Getter
    private PluginMessageType type;
    @Getter
    private List<String> messages = new ArrayList<>();
    @Getter
    private UUID targetUUID = null;
    @Getter
    private String prefix = Sam.getRobot().getPrefix();
    private boolean dontSend = false;

    public PluginMessage(PluginMessageType type){
        this.type = type;
    }

    public PluginMessage(){
    }

    public PluginMessage message(String... messages){
        for (String s : messages) {
            this.messages.add(prefix + s);
        }
        return this;
    }

    public PluginMessage prefix(String prefix){
        this.prefix = prefix;
        return this;
    }
    public PluginMessage prefix(boolean prefix){
        if (!prefix){
            this.prefix = "";
        }
        return this;
    }

    public PluginMessage target(UUID uuid){
        this.targetUUID = uuid;
        return this;
    }

    private PluginMessage action(String content){
        switch (getType()){
            case PLAYER_MESSAGE:
                Synergy.debug("RETRIEVE -----------");
                Synergy.debug(getTargetUUID().toString());
                SynergyUser user = Core.getPlugin().getUserManager().getUser(getTargetUUID());
                if (user != null){
                    System.out.println(user);
                    System.out.println(user.getUuid().toString());
                    System.out.println(user.getName());
                    System.out.println(getMessages());
                    dontSend = true;
                    user.sendModifactionMessage(
                            MessageModification.RAW,
                            getMessages()
                    );
                }
                Synergy.debug("RETRIEVE -----------");
                break;
            case STAFF_MESSAGE:
                for(SynergyUser synergyUser : Core.getPlugin().getUserManager().getOnlineUsers()){
                    if (Rank.isStaff(synergyUser)){
                        synergyUser.sendModifactionMessage(
                                MessageModification.RAW,
                                getMessages()
                        );
                    }
                }
            case ACTION:
                if (content != null){
                    Core.getPlugin().getRunnableManager().runTask("hack thread", core -> {
                        core.getServer().getPluginManager().callEvent(new SynergyReceiveEvent(content, this));
                    });
                }
                break;
        }
        return this;
    }

    public PluginMessage retrieve(String channel, String content){
        String[] splitted = content.split("//");
        if (content.contains("TARGET")){
            String target = splitted[0];
            String uuid = target.substring(8, target.indexOf("]"));
            Synergy.debug("TARGET = "+target);
            Synergy.debug("UUID = "+uuid);
            this.targetUUID = UUID.fromString(uuid);
//            splitted[0] = splitted[0].replaceAll(target.split("]")[1], "");
        }
        try{
            this.type = PluginMessageType.valueOf(channel);
        }catch (Exception e) {
            e.printStackTrace();
        }

        for(int i=1;i<splitted.length;i++){
            this.messages.add(splitted[i]);
        }
        action(null);
        return this;
    }

    public void send(){
        if (!dontSend) {
            StringBuilder builder = new StringBuilder();
            if (this.targetUUID != null) {
                builder.append("[TARGET=" + this.targetUUID.toString() + "]");
            }
            for (String string : messages) {
                builder.append("//" + string);
            }
            Core.getPlugin().getPluginMessagingManager().send(this.type, builder.toString());
            action(builder.toString());
        }
    }

}
