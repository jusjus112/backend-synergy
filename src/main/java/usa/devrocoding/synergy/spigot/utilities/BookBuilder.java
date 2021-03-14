package usa.devrocoding.synergy.spigot.utilities;

import com.google.common.collect.Lists;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.chat.ComponentSerializer;
import net.minecraft.server.v1_12_R1.IChatBaseComponent;
import net.minecraft.server.v1_12_R1.PacketDataSerializer;
import net.minecraft.server.v1_12_R1.PacketPlayOutCustomPayload;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftMetaBook;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.inventory.meta.BookMeta;

import java.util.List;
import org.bukkit.scheduler.BukkitRunnable;
import usa.devrocoding.synergy.assets.Synergy;
import usa.devrocoding.synergy.spigot.Core;

public class BookBuilder {

    private final ItemStack book;
    private final BookMeta bookMeta;
    private final List<IChatBaseComponent> components;

    public BookBuilder(int amount){
        this.book = new ItemStack(Material.WRITTEN_BOOK, amount);
        this.bookMeta = (BookMeta) book.getItemMeta();
        this.components = Lists.newArrayList();
    }

    public BookBuilder(){
        this.book = new ItemStack(Material.WRITTEN_BOOK, 1);
        this.bookMeta = (BookMeta) book.getItemMeta();
        this.components = Lists.newArrayList();
    }

    public BookBuilder setAuthor(String author){
        this.bookMeta.setAuthor(author);
        return this;
    }

    public BookBuilder addPages(String... pages){
        this.bookMeta.addPage(pages);
        return this;
    }

    public BookBuilder addPage(TextComponent... textComponents){
        components.add(IChatBaseComponent.ChatSerializer.a(ComponentSerializer
            .toString(textComponents)));
        return this;
    }

    public BookBuilder setPages(List<String> pages){
        this.bookMeta.setPages(pages);
        return this;
    }

    public BookBuilder setPages(String... pages){
        this.bookMeta.setPages(pages);
        return this;
    }

    public BookBuilder setGeneration(BookMeta.Generation generation){
        this.bookMeta.setGeneration(generation);
        return this;
    }

    public BookBuilder setTitle(String title){
        this.bookMeta.setTitle(title);
        return this;
    }

    public ItemStack build(){
        if (!components.isEmpty()) {
            try {
                List<IChatBaseComponent> pages = (List<IChatBaseComponent>) CraftMetaBook.class
                    .getDeclaredField("pages").get(this.bookMeta); //This
                pages.addAll(components);
            } catch (Exception e) {
                Synergy.error("Error while building a book!");
                Synergy.error(e.getMessage());
            }
        }

        this.book.setItemMeta(this.bookMeta);

        return this.book;
    }

    public void open(Player player){
        ItemStack book = this.build();
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