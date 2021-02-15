package usa.devrocoding.synergy.assets;

import com.google.common.collect.Lists;
import java.util.Arrays;
import lombok.Getter;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.botsam.Sam;
import usa.devrocoding.synergy.spigot.pluginmessaging.event.SynergyReceiveEvent;
import usa.devrocoding.synergy.spigot.user.object.MessageModification;
import usa.devrocoding.synergy.spigot.user.object.SynergyUser;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PluginMessageBuilder {

    @Getter
    private PluginMessageType type;
    private final List<String> messages = new ArrayList<>();
    @Getter
    private UUID targetUUID = null;
    @Getter
    private String prefix = Sam.getRobot().getPrefix();
    @Getter
    private boolean hasPrefix = true;
    private boolean dontSend = false;

    public PluginMessageBuilder(PluginMessageType type){
        this.type = type;
    }

    public PluginMessageBuilder(){
    }

    public List<String> getMessages() {
        List<String> messages = Lists.newArrayList();
        if (this.hasPrefix) {
            this.messages.forEach(s -> {
                messages.add(prefix + " " + s);
            });
        }else{
            messages.addAll(this.messages);
        }
        return messages;
    }

    public PluginMessageBuilder message(String... messages){
        this.messages.addAll(Arrays.asList(messages));
        return this;
    }

    public PluginMessageBuilder message(List<String> messages){
        this.messages.addAll(messages);
        return this;
    }

    public PluginMessageBuilder prefix(String prefix){
        this.prefix = prefix;
        this.hasPrefix = true;
        return this;
    }
    public PluginMessageBuilder prefix(boolean prefix){
        if (!prefix){
            this.hasPrefix = false;
            this.prefix = "";
        }
        this.hasPrefix = true;
        return this;
    }

    public PluginMessageBuilder target(UUID uuid){
        this.targetUUID = uuid;
        return this;
    }

    private PluginMessageBuilder action(String content){
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

    public PluginMessageBuilder retrieve(String channel, String content){
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

        this.messages.addAll(Arrays.asList(splitted).subList(1, splitted.length));
        action(null);
        return this;
    }

    public void send(){
        if (!this.dontSend) {
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < messages.size(); i++) {
                if (i > 0) builder.append("//");
                builder.append(messages.get(i));
            }
            Core.getPlugin().getPluginMessagingManager().send(
                this.targetUUID != null ? this.targetUUID.toString() : "", this.type, builder.toString());
            action(builder.toString());
        }
    }

}
