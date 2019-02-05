package usa.devrocoding.synergy.spigot.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
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
import org.bukkit.event.world.WorldUnloadEvent;
import org.spigotmc.event.entity.EntityDismountEvent;

public class EventHandlers implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void unloadWorld(WorldUnloadEvent e){
        e.setCancelled(false);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void initMap(MapInitializeEvent e){
        Events.MAP_INIT.runEvent(e);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void initMap(ItemDespawnEvent e){
        Events.ITEM_DESPAWN.runEvent(e);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void eat(PlayerItemConsumeEvent e){
        Events.CONS_ITEM.runEvent(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntity(EntitySpawnEvent e) {
        Events.ENTITY_SPAWN.runEvent(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntity(CreatureSpawnEvent e) {
        Events.CREATURE_SPAWN.runEvent(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntity(EntityDismountEvent e) {
        Events.ENTITY_DISMOUNT.runEvent(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntity(EntityRegainHealthEvent e) {
        Events.ENTITY_REGAIN_HEALTH.runEvent(e);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onEntity(EntityDamageByEntityEvent e) {
        Events.ENTITY_DAMAGE_BY_ENTITY.runEvent(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntity(EntityExplodeEvent e) {
        Events.ENTITY_EXPLODE.runEvent(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntity(ItemSpawnEvent e) {
        Events.ITEM_SPAWN.runEvent(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntity(EntityDamageEvent e) {
        Events.ENTITY_DAMAGE.runEvent(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntity(EntityDeathEvent e) {
        Events.ENTITY_DEATH.runEvent(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntity(PlayerRespawnEvent e) {
        Events.PLAYER_RESPAWN.runEvent(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntity(FoodLevelChangeEvent e) {
        Events.FOOD_LEVEL_CHANGE.runEvent(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntity(PlayerDeathEvent e) {
        Events.PLAYER_DEATH.runEvent(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onItemBreak(PlayerItemBreakEvent e) {
        Events.PLAYER_ITEM_BREAK.runEvent(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntity(PotionSplashEvent e) {
        Events.POTION_SPLASH.runEvent(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntity(ProjectileHitEvent e) {
        Events.PROJECTILE_HIT.runEvent(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntity(ProjectileLaunchEvent e) {
        Events.PROJECTILE_LAUNCH.runEvent(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayer(AsyncPlayerChatEvent e) {
        Events.ASYNC_PLAYER_CHAT.runEvent(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayer(PlayerAnimationEvent e) {
        Events.PLAYER_ANIMATION.runEvent(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayer(PlayerChatTabCompleteEvent e) {
        Events.PLAYER_CHAT_TAB_COMPLETE.runEvent(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayer(PlayerDropItemEvent e) {
        Events.PLAYER_DROP_ITEM.runEvent(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayer(PlayerInteractEntityEvent e) {
        Events.PLAYER_INTERACT_ENTITY.runEvent(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayer(PlayerEggThrowEvent e) {
        Events.PLAYER_EGG_THROW.runEvent(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayer(PlayerInteractEvent e) {
        Events.PLAYER_INTERACT.runEvent(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayer(PlayerJoinEvent e) {
        Events.PLAYER_JOIN.runEvent(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayer(PlayerKickEvent e) {
        Events.PLAYER_KICK.runEvent(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayer(PlayerLoginEvent e) {
        Events.PLAYER_LOGIN.runEvent(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayer(PlayerMoveEvent e) {
        Events.PLAYER_MOVE.runEvent(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayer(AsyncPlayerPreLoginEvent e) {
        Events.ASYNC_PRE_LOGIN.runEvent(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayer(PlayerQuitEvent e) {
        Events.PLAYER_QUIT.runEvent(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayer(PlayerToggleFlightEvent e) {
        Events.PLAYER_TOGGLE_FLIGHT.runEvent(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayer(PlayerToggleSneakEvent e) {
        Events.PLAYER_TOGGLE_SNEAK.runEvent(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayer(PlayerToggleSprintEvent e) {
        Events.PLAYER_TOGGLE_SPRINT.runEvent(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayer(PlayerVelocityEvent e) {
        Events.PLAYER_VELOCITY.runEvent(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayer(PlayerPickupItemEvent e) {
        Events.PLAYER_PICKUP_ITEM.runEvent(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInventory(CraftItemEvent e) {
        Events.CRAFT_ITEM.runEvent(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInventory(FurnaceBurnEvent e) {
        Events.FURNACE_BURN.runEvent(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInventory(FurnaceSmeltEvent e) {
        Events.FURNACE_SMELT.runEvent(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInventory(FurnaceExtractEvent e) {
        Events.FURNACE_EXTRACT.runEvent(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInventory(InventoryClickEvent e) {
        Events.INVENTORY_CLICK.runEvent(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInventory(InventoryCloseEvent e) {
        Events.INVENTORY_CLOSE.runEvent(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInventory(InventoryCreativeEvent e) {
        Events.INVENTORY_CREATIVE.runEvent(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInventory(InventoryDragEvent e) {
        Events.INVENTORY_DRAG.runEvent(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInventory(InventoryInteractEvent e) {
        Events.INVENTORY_INTERACT.runEvent(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInventory(InventoryMoveItemEvent e) {
        Events.INVENTORY_MOVE_ITEM.runEvent(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInventory(InventoryOpenEvent e) {
        Events.INVENTORY_OPEN.runEvent(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInventory(InventoryPickupItemEvent e) {
        Events.INVENTORY_PICKUP_ITEM.runEvent(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInventory(PrepareItemCraftEvent e) {
        Events.PREPARE_ITEM_CRAFT.runEvent(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInventory(BrewEvent e) {
        Events.BREW.runEvent(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInventory(EnchantItemEvent e) {
        Events.ENCHANT_ITEM.runEvent(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInventory(PrepareItemEnchantEvent e) {
        Events.PREPARE_ITEM_ENCHANT.runEvent(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onWeather(LightningStrikeEvent e) {
        Events.LIGHTNING_STRIKE.runEvent(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onWeather(ThunderChangeEvent e) {
        Events.THUNDER_CHANGE.runEvent(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onWeather(WeatherChangeEvent e) {
        Events.WEATHER_CHANGE.runEvent(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlock(BlockRedstoneEvent e) {
        Events.BLOCK_REDSTONE.runEvent(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlock(ChunkLoadEvent e) {
        Events.CHUNK_LOAD.runEvent(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlock(ChunkUnloadEvent e) {
        Events.CHUNK_UNLOAD.runEvent(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlock(NotePlayEvent e) {
        Events.NOTE_PLAY.runEvent(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlock(SignChangeEvent e) {
        Events.SIGN_CHANGE.runEvent(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlock(BlockPistonRetractEvent e) {
        Events.BLOCK_PISTON_RETRACT.runEvent(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlock(BlockPistonExtendEvent e) {
        Events.BLOCK_PISTON_EXTEND.runEvent(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlock(BlockPhysicsEvent e) {
        Events.BLOCK_PHYSICS.runEvent(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlock(BlockDispenseEvent e) {
        Events.BLOCK_DISPENSE.runEvent(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlock(BlockExpEvent e) {
        Events.BLOCK_EXP.runEvent(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlock(BlockPlaceEvent e) {
        // TODO: Add flag's for story
        Events.BLOCK_PLACE.runEvent(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlock(BlockDamageEvent e) {
        Events.BLOCK_DAMAGE.runEvent(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlock(BlockCanBuildEvent e) {
        Events.BLOCK_CAN_BUILD.runEvent(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlock(BlockBreakEvent e) {
        // TODO: Add flag's for story
        Events.BLOCK_BREAK.runEvent(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlock(BlockGrowEvent e) {
        Events.BLOCK_GROW.runEvent(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlock(EntityBlockFormEvent e) {
        Events.ENTITY_BLOCK_FORM.runEvent(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlock(BlockFromToEvent e) {
        Events.BLOCK_FROM_TO.runEvent(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlock(BlockFormEvent e) {
        Events.BLOCK_FORM.runEvent(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlock(BlockFadeEvent e) {
        Events.BLOCK_FADE.runEvent(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlock(BlockIgniteEvent e) {
        Events.BLOCK_IGNITE.runEvent(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlock(BlockBurnEvent e) {
        Events.BLOCK_BURN.runEvent(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlock(BlockSpreadEvent e) {
        Events.BLOCK_SPREAD.runEvent(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlock(LeavesDecayEvent e) {
        Events.LEAVES_DECAY.runEvent(e);
    }

}
