package usa.devrocoding.synergy.spigot.utilities;

import net.minecraft.server.v1_12_R1.IChatBaseComponent;
import net.minecraft.server.v1_12_R1.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_12_R1.PacketPlayOutPlayerListHeaderFooter;
import net.minecraft.server.v1_12_R1.PacketPlayOutTitle;
import net.minecraft.server.v1_12_R1.PacketPlayOutTitle.EnumTitleAction;
import net.minecraft.server.v1_12_R1.PlayerConnection;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import usa.devrocoding.synergy.assets.Synergy;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class UtilDisplay implements Listener {

    private Player player;

    public UtilDisplay setPlayer(Player player){
        this.player = player;
        return this;
    }


    public void sendTitle(String title) {
        sendTitleAndSubTitle(title, "");
    }

    public void sendTitleAndSubTitle(String title, String subtitle) {
        IChatBaseComponent message = ChatSerializer.a("{\"text\":\"\"}").a(title);
        PacketPlayOutTitle packet = new PacketPlayOutTitle(EnumTitleAction.TITLE, message);
        ((CraftPlayer) this.player).getHandle().playerConnection.sendPacket(packet);
        IChatBaseComponent message2 = ChatSerializer.a("{\"text\":\"\"}").a(subtitle);
        PacketPlayOutTitle packet2 = new PacketPlayOutTitle(EnumTitleAction.SUBTITLE, message2);
        ((CraftPlayer) this.player).getHandle().playerConnection.sendPacket(packet2);
    }

    public void sendSubTitle(String subtitle) {
        sendTitle("");
        IChatBaseComponent message = ChatSerializer.a("{\"text\":\"\"}").a(subtitle);
        PacketPlayOutTitle packet = new PacketPlayOutTitle(EnumTitleAction.SUBTITLE, message);

        ((CraftPlayer) this.player).getHandle().playerConnection.sendPacket(packet);
    }

    public void sendTiming(int fadeIn, int stay, int fadeOut) {
        try {
            Object h = getHandle(this.player);
            Object c = getField(h.getClass(), "playerConnection").get(h);

            Object packet = PacketPlayOutTitle.class
                    .getConstructor(new Class[]{PacketPlayOutTitle.class, Integer.TYPE, Integer.TYPE, Integer.TYPE})
                    .newInstance(new Object[]{EnumTitleAction.TIMES, Integer.valueOf(fadeIn), Integer.valueOf(stay),
                            Integer.valueOf(fadeOut)});
            getMethod(h.getClass(), "sendPacket", new Class[0]).invoke(c, new Object[]{packet});
        } catch (Exception e) {
            Synergy.error(e.getMessage());
        }
    }

    public void reset() {
        PacketPlayOutTitle packet = new PacketPlayOutTitle(EnumTitleAction.RESET, null);
        ((CraftPlayer) this.player).getHandle().playerConnection.sendPacket(packet);
    }

    public void clear() {
        PacketPlayOutTitle packet = new PacketPlayOutTitle(EnumTitleAction.CLEAR, null);
        ((CraftPlayer) this.player).getHandle().playerConnection.sendPacket(packet);
    }

    private boolean ClassListEqual(Class<?>[] l1, Class<?>[] l2) {
        boolean equal = true;

        if (l1.length != l2.length)
            return false;

        for (int i = 0; i < l1.length; i++) {
            if (l1[i] != l2[i]) {
                equal = false;
                break;
            }
        }
        return equal;
    }

    private Field getField(Class<?> clazz, String name) {
        try {
            Field f = clazz.getDeclaredField(name);
            f.setAccessible(true);
            return f;
        } catch (Exception e) {
            Synergy.error(e.getMessage());
        }
        return null;
    }

    private Method getMethod(Class<?> clazz, String name, Class<?>[] args) {
        for (Method m : clazz.getMethods()) {
            if ((m.getName().equals(name)) && ((args.length == 0) || (ClassListEqual(args, m.getParameterTypes())))) {
                m.setAccessible(true);
                return m;
            }
        }
        return null;
    }

    private Object getHandle(Object obj) {
        try {
            return getMethod(obj.getClass(), "getHandle", new Class[0]).invoke(obj, new Object[0]);
        } catch (Exception e) {
            Synergy.error(e.getMessage());
        }
        return null;
    }

    public void sendTablist(String header, String footer) {
        PlayerConnection con = ((CraftPlayer) this.player).getHandle().playerConnection;
        IChatBaseComponent headerComponent = ChatSerializer.a("{\"text\": \"" + header + "\"}");
        IChatBaseComponent footerComponent = ChatSerializer.a("{\"text\": \"" + footer + "\"}");
        PacketPlayOutPlayerListHeaderFooter packet = new PacketPlayOutPlayerListHeaderFooter();

        try {
            Field exception = packet.getClass().getDeclaredField("a");
            exception.setAccessible(true);
            exception.set(packet, headerComponent);
            Field b = packet.getClass().getDeclaredField("b");
            b.setAccessible(true);
            b.set(packet, footerComponent);
        } catch (Exception arg7) {
            arg7.printStackTrace();
        }

        con.sendPacket(packet);;
    }
}