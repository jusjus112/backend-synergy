package usa.devrocoding.synergy.spigot.nick;

import com.mojang.authlib.GameProfile;
import net.minecraft.server.v1_12_R1.*;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_12_R1.CraftServer;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import usa.devrocoding.synergy.assets.Synergy;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.Module;
import usa.devrocoding.synergy.spigot.nick.command.CommandNick;

import java.lang.reflect.Field;

public class NickManager extends Module {

    public NickManager(Core plugin){
        super(plugin, "Nick Manager", false);

        registerCommand(
                new CommandNick(getPlugin())
        );
    }

    @Override
    public void reload(String response) {

    }

    public boolean nickPlayer(Player player, String name){
        try{
            EntityPlayer entity = ((CraftPlayer) player).getHandle();
            GameProfile profile = entity.getProfile();
            Field profilefield = profile.getClass().getDeclaredField("name");
            profilefield.setAccessible(true);
            profilefield.set(profile, name);
            MinecraftServer nmsServer = ((CraftServer) Bukkit.getServer()).getServer();
            WorldServer nmsWorld = ((CraftWorld) Bukkit.getWorlds().get(0)).getHandle();
            EntityPlayer pp = new EntityPlayer(nmsServer, nmsWorld, new GameProfile(player.getUniqueId(), name),
                    new PlayerInteractManager(nmsWorld));
            EntityPlayer pl = new EntityPlayer(nmsServer, nmsWorld,
                    new GameProfile(player.getUniqueId(), player.getCustomName()), new PlayerInteractManager(nmsWorld));
            for (Player players : Bukkit.getOnlinePlayers()) {
                PlayerConnection connection = ((CraftPlayer) players).getHandle().playerConnection;
                connection.sendPacket(new PacketPlayOutPlayerInfo(
                        PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, new EntityPlayer[] { pl }));
                connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER,
                        new EntityPlayer[] { pp }));
                players.hidePlayer(player);
                players.showPlayer(player);
            }
            return true;
        }catch (Exception e){
            Synergy.error("Cannot nick: "+e.getMessage());
            return false;
        }
    }
}
