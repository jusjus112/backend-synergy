package usa.devrocoding.synergy.spigot.events;

import org.bukkit.event.Event;
import org.bukkit.event.block.*;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.enchantment.PrepareItemEnchantEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.*;
import org.bukkit.event.server.MapInitializeEvent;
import org.bukkit.event.weather.LightningStrikeEvent;
import org.bukkit.event.weather.ThunderChangeEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.spigotmc.event.entity.EntityDismountEvent;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
public enum Events {

    /**
     * World Entity Events
     */
    CONS_ITEM(PlayerItemConsumeEvent.class),
    ENTITY_DAMAGE_BY_ENTITY(EntityDamageByEntityEvent.class),
    ENTITY_DAMAGE(EntityDamageEvent.class),
    ENTITY_SPAWN(EntitySpawnEvent.class),
    CREATURE_SPAWN(CreatureSpawnEvent.class),
    ENTITY_DISMOUNT(EntityDismountEvent.class),
    ENTITY_DEATH(EntityDeathEvent.class),
    ENTITY_REGAIN_HEALTH(EntityRegainHealthEvent.class),
    POTION_SPLASH(PotionSplashEvent.class),
    PROJECTILE_HIT(ProjectileHitEvent.class),
    PROJECTILE_LAUNCH(ProjectileLaunchEvent.class),
    ITEM_SPAWN(ItemSpawnEvent.class),

    /**
     * Player Events
     */

    ASYNC_PLAYER_CHAT(AsyncPlayerChatEvent.class),
    ASYNC_PRE_LOGIN(AsyncPlayerPreLoginEvent.class),
    FOOD_LEVEL_CHANGE(FoodLevelChangeEvent.class),
    PLAYER_RESPAWN(PlayerRespawnEvent.class),
    PLAYER_DEATH(PlayerDeathEvent.class),
    PLAYER_ITEM_BREAK(PlayerItemBreakEvent.class),
    PLAYER_ANIMATION(PlayerAnimationEvent.class),
    PLAYER_CHAT_TAB_COMPLETE(PlayerChatTabCompleteEvent.class),
    PLAYER_DROP_ITEM(PlayerDropItemEvent.class),
    PLAYER_INTERACT_ENTITY(PlayerInteractEntityEvent.class),
    PLAYER_EGG_THROW(PlayerEggThrowEvent.class),
    PLAYER_INTERACT(PlayerInteractEvent.class),
    PLAYER_JOIN(PlayerJoinEvent.class),
    PLAYER_KICK(PlayerKickEvent.class),
    PLAYER_LOGIN(PlayerLoginEvent.class),
    PLAYER_MOVE(PlayerMoveEvent.class),
    PLAYER_QUIT(PlayerQuitEvent.class),
    PLAYER_TOGGLE_FLIGHT(PlayerToggleFlightEvent.class),
    PLAYER_TOGGLE_SNEAK(PlayerToggleSneakEvent.class),
    PLAYER_TOGGLE_SPRINT(PlayerToggleSprintEvent.class),
    PLAYER_VELOCITY(PlayerVelocityEvent.class),
    PLAYER_PICKUP_ITEM(PlayerPickupItemEvent.class),
    MAP_INIT(MapInitializeEvent.class),

    /**
     * Inventory Events
     */

    CRAFT_ITEM(CraftItemEvent.class),
    FURNACE_BURN(FurnaceBurnEvent.class),
    FURNACE_SMELT(FurnaceSmeltEvent.class),
    FURNACE_EXTRACT(FurnaceExtractEvent.class),
    INVENTORY_CLICK(InventoryClickEvent.class),
    INVENTORY_CLOSE(InventoryCloseEvent.class),
    INVENTORY_CREATIVE(InventoryCreativeEvent.class),
    INVENTORY_DRAG(InventoryDragEvent.class),
    INVENTORY_INTERACT(InventoryInteractEvent.class),
    INVENTORY_MOVE_ITEM(InventoryMoveItemEvent.class),
    INVENTORY_OPEN(InventoryOpenEvent.class),
    INVENTORY_PICKUP_ITEM(InventoryPickupItemEvent.class),
    PREPARE_ITEM_CRAFT(PrepareItemCraftEvent.class),
    BREW(BrewEvent.class),
    ENCHANT_ITEM(EnchantItemEvent.class),
    PREPARE_ITEM_ENCHANT(PrepareItemEnchantEvent.class),

    /**
     * Weather Events
     */

    LIGHTNING_STRIKE(LightningStrikeEvent.class),
    THUNDER_CHANGE(ThunderChangeEvent.class),
    WEATHER_CHANGE(WeatherChangeEvent.class),

    /**
     * 	Block Events
     */

    NOTE_PLAY(NotePlayEvent.class),
    CHUNK_LOAD(ChunkLoadEvent.class),
    CHUNK_UNLOAD(ChunkUnloadEvent.class),
    SIGN_CHANGE(SignChangeEvent.class),
    ENTITY_BLOCK_FORM(EntityBlockFormEvent.class),
    BLOCK_REDSTONE(BlockRedstoneEvent.class),
    BLOCK_PISTON_RETRACT(BlockPistonRetractEvent.class),
    BLOCK_PISTON_EXTEND(BlockPistonExtendEvent.class),
    BLOCK_PHYSICS(BlockPhysicsEvent.class),
    BLOCK_DISPENSE(BlockDispenseEvent.class),
    BLOCK_EXP(BlockExpEvent.class),
    BLOCK_PLACE(BlockPlaceEvent.class),
    BLOCK_DAMAGE(BlockDamageEvent.class),
    BLOCK_CAN_BUILD(BlockCanBuildEvent.class),
    BLOCK_BREAK(BlockBreakEvent.class),
    BLOCK_GROW(BlockGrowEvent.class),
    BLOCK_FROM_TO(BlockFromToEvent.class),
    BLOCK_FORM(BlockFormEvent.class),
    BLOCK_FADE(BlockFadeEvent.class),
    BLOCK_IGNITE(BlockIgniteEvent.class),
    BLOCK_BURN(BlockBurnEvent.class),
    BLOCK_SPREAD(BlockSpreadEvent.class),
    LEAVES_DECAY(LeavesDecayEvent.class),
    ENTITY_EXPLODE(EntityExplodeEvent.class),
    ITEM_DESPAWN(ItemDespawnEvent.class);


    private List<EventListener<?>> listeners = new ArrayList<>();
    private Class<?> myClass;

    Events(Class<?> myClass){
        this.myClass = myClass;
    }

    public void clear(){
        listeners.clear();
    }

    public void runEvent(Event e){
        for (EventListener el : listeners){
            el.process(e);
        }
    }

    public static void addListener(EventListener<?> listener){
        for (Events e : values()){
            if (listener.isType(e.myClass)){
                e.listeners.add(0,listener);
                return;
            }
        }
    }

}
