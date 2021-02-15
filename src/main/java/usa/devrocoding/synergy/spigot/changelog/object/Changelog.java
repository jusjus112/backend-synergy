package usa.devrocoding.synergy.spigot.changelog.object;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.server.v1_12_R1.PacketDataSerializer;
import net.minecraft.server.v1_12_R1.PacketPlayOutCustomPayload;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import usa.devrocoding.synergy.assets.Synergy;
import usa.devrocoding.synergy.spigot.Core;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import usa.devrocoding.synergy.spigot.utilities.BookBuilder;

@Getter
public class Changelog {

    private final String title;
    private final Date date;
    private final Date showTill;
    private final List<String> pages;
    private final BookBuilder bookBuilder;

    public Changelog(String title, Date date, Date showTill){
        this(title, date, showTill, new ArrayList<>());
    }

    public Changelog(String title, Date date, Date showTill, List<String> pages){
        this.date = date;
        this.pages = pages;
        this.title = title;
        this.showTill = showTill;

        this.bookBuilder = new BookBuilder();
        this.bookBuilder.setPages(pages);

        Core.getPlugin().getChangelogManager().addChangelog(this);
    }

    public String getNiceDate(){
        return new SimpleDateFormat("MMM dd, yyyy - HH:mm aa").format(date);
    }

    public void open(Player player){
        ItemStack book = this.bookBuilder.build();
        int slot = player.getInventory().getHeldItemSlot();
        ItemStack old = player.getInventory().getItem(slot);

        new BukkitRunnable(){
            @Override
            public void run() {
                player.getInventory().setItem(slot, book);

                ByteBuf buf = Unpooled.buffer(256);
                buf.setByte(0, (byte)0);
                buf.writerIndex(1);

                PacketPlayOutCustomPayload packet = new PacketPlayOutCustomPayload("MC|BOpen", new PacketDataSerializer(buf));
                ((CraftPlayer)player).getHandle().playerConnection.sendPacket(packet);

                player.getInventory().setItem(slot, old);
            }
        }.runTaskLater(Core.getPlugin(), 3L);
    }

}
