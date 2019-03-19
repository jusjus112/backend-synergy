package usa.devrocoding.synergy.spigot.listeners;

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
        Listeners.MAP_INIT.runEvent(e);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void initMap(ItemDespawnEvent e){
        Listeners.ITEM_DESPAWN.runEvent(e);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void eat(PlayerItemConsumeEvent e){
        Listeners.CONS_ITEM.runEvent(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntity(EntitySpawnEvent e) {
        Listeners.ENTITY_SPAWN.runEvent(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntity(CreatureSpawnEvent e) {
        Listeners.CREATURE_SPAWN.runEvent(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntity(EntityDismountEvent e) {
        Listeners.ENTITY_DISMOUNT.runEvent(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntity(EntityRegainHealthEvent e) {
        Listeners.ENTITY_REGAIN_HEALTH.runEvent(e);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onEntity(EntityDamageByEntityEvent e) {
        Listeners.ENTITY_DAMAGE_BY_ENTITY.runEvent(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntity(EntityExplodeEvent e) {
        Listeners.ENTITY_EXPLODE.runEvent(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntity(ItemSpawnEvent e) {
        Listeners.ITEM_SPAWN.runEvent(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntity(EntityDamageEvent e) {
        Listeners.ENTITY_DAMAGE.runEvent(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntity(EntityDeathEvent e) {
        Listeners.ENTITY_DEATH.runEvent(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntity(PlayerRespawnEvent e) {
        Listeners.PLAYER_RESPAWN.runEvent(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntity(FoodLevelChangeEvent e) {
        Listeners.FOOD_LEVEL_CHANGE.runEvent(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntity(PlayerDeathEvent e) {
        Listeners.PLAYER_DEATH.runEvent(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onItemBreak(PlayerItemBreakEvent e) {
        Listeners.PLAYER_ITEM_BREAK.runEvent(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntity(PotionSplashEvent e) {
        Listeners.POTION_SPLASH.runEvent(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntity(ProjectileHitEvent e) {
        Listeners.PROJECTILE_HIT.runEvent(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntity(ProjectileLaunchEvent e) {
        Listeners.PROJECTILE_LAUNCH.runEvent(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayer(AsyncPlayerChatEvent e) {
        Listeners.ASYNC_PLAYER_CHAT.runEvent(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayer(PlayerAnimationEvent e) {
        Listeners.PLAYER_ANIMATION.runEvent(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayer(PlayerChatTabCompleteEvent e) {
        Listeners.PLAYER_CHAT_TAB_COMPLETE.runEvent(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayer(PlayerDropItemEvent e) {
        Listeners.PLAYER_DROP_ITEM.runEvent(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayer(PlayerInteractEntityEvent e) {
        Listeners.PLAYER_INTERACT_ENTITY.runEvent(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayer(PlayerEggThrowEvent e) {
        Listeners.PLAYER_EGG_THROW.runEvent(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayer(PlayerInteractEvent e) {
        Listeners.PLAYER_INTERACT.runEvent(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayer(PlayerJoinEvent e) {
        Listeners.PLAYER_JOIN.runEvent(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayer(PlayerKickEvent e) {
        Listeners.PLAYER_KICK.runEvent(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayer(PlayerLoginEvent e) {
        Listeners.PLAYER_LOGIN.runEvent(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayer(PlayerMoveEvent e) {
        Listeners.PLAYER_MOVE.runEvent(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayer(AsyncPlayerPreLoginEvent e) {
        Listeners.ASYNC_PRE_LOGIN.runEvent(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayer(PlayerQuitEvent e) {
        Listeners.PLAYER_QUIT.runEvent(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayer(PlayerToggleFlightEvent e) {
        Listeners.PLAYER_TOGGLE_FLIGHT.runEvent(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayer(PlayerToggleSneakEvent e) {
        Listeners.PLAYER_TOGGLE_SNEAK.runEvent(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayer(PlayerToggleSprintEvent e) {
        Listeners.PLAYER_TOGGLE_SPRINT.runEvent(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayer(PlayerVelocityEvent e) {
        Listeners.PLAYER_VELOCITY.runEvent(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayer(PlayerPickupItemEvent e) {
        Listeners.PLAYER_PICKUP_ITEM.runEvent(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInventory(CraftItemEvent e) {
        Listeners.CRAFT_ITEM.runEvent(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInventory(FurnaceBurnEvent e) {
        Listeners.FURNACE_BURN.runEvent(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInventory(FurnaceSmeltEvent e) {
        Listeners.FURNACE_SMELT.runEvent(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInventory(FurnaceExtractEvent e) {
        Listeners.FURNACE_EXTRACT.runEvent(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInventory(InventoryClickEvent e) {
        Listeners.INVENTORY_CLICK.runEvent(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInventory(InventoryCloseEvent e) {
        Listeners.INVENTORY_CLOSE.runEvent(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInventory(InventoryCreativeEvent e) {
        Listeners.INVENTORY_CREATIVE.runEvent(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInventory(InventoryDragEvent e) {
        Listeners.INVENTORY_DRAG.runEvent(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInventory(InventoryInteractEvent e) {
        Listeners.INVENTORY_INTERACT.runEvent(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInventory(InventoryMoveItemEvent e) {
        Listeners.INVENTORY_MOVE_ITEM.runEvent(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInventory(InventoryOpenEvent e) {
        Listeners.INVENTORY_OPEN.runEvent(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInventory(InventoryPickupItemEvent e) {
        Listeners.INVENTORY_PICKUP_ITEM.runEvent(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInventory(PrepareItemCraftEvent e) {
        Listeners.PREPARE_ITEM_CRAFT.runEvent(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInventory(BrewEvent e) {
        Listeners.BREW.runEvent(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInventory(EnchantItemEvent e) {
        Listeners.ENCHANT_ITEM.runEvent(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInventory(PrepareItemEnchantEvent e) {
        Listeners.PREPARE_ITEM_ENCHANT.runEvent(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onWeather(LightningStrikeEvent e) {
        Listeners.LIGHTNING_STRIKE.runEvent(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onWeather(ThunderChangeEvent e) {
        Listeners.THUNDER_CHANGE.runEvent(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onWeather(WeatherChangeEvent e) {
        Listeners.WEATHER_CHANGE.runEvent(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlock(BlockRedstoneEvent e) {
        Listeners.BLOCK_REDSTONE.runEvent(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlock(ChunkLoadEvent e) {
        Listeners.CHUNK_LOAD.runEvent(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlock(ChunkUnloadEvent e) {
        Listeners.CHUNK_UNLOAD.runEvent(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlock(NotePlayEvent e) {
        Listeners.NOTE_PLAY.runEvent(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlock(SignChangeEvent e) {
        Listeners.SIGN_CHANGE.runEvent(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlock(BlockPistonRetractEvent e) {
        Listeners.BLOCK_PISTON_RETRACT.runEvent(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlock(BlockPistonExtendEvent e) {
        Listeners.BLOCK_PISTON_EXTEND.runEvent(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlock(BlockPhysicsEvent e) {
        Listeners.BLOCK_PHYSICS.runEvent(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlock(BlockDispenseEvent e) {
        Listeners.BLOCK_DISPENSE.runEvent(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlock(BlockExpEvent e) {
        Listeners.BLOCK_EXP.runEvent(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlock(BlockPlaceEvent e) {
        // TODO: Add flag's for story
        Listeners.BLOCK_PLACE.runEvent(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlock(BlockDamageEvent e) {
        Listeners.BLOCK_DAMAGE.runEvent(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlock(BlockCanBuildEvent e) {
        Listeners.BLOCK_CAN_BUILD.runEvent(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlock(BlockBreakEvent e) {
        // TODO: Add flag's for story
        Listeners.BLOCK_BREAK.runEvent(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlock(BlockGrowEvent e) {
        Listeners.BLOCK_GROW.runEvent(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlock(EntityBlockFormEvent e) {
        Listeners.ENTITY_BLOCK_FORM.runEvent(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlock(BlockFromToEvent e) {
        Listeners.BLOCK_FROM_TO.runEvent(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlock(BlockFormEvent e) {
        Listeners.BLOCK_FORM.runEvent(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlock(BlockFadeEvent e) {
        Listeners.BLOCK_FADE.runEvent(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlock(BlockIgniteEvent e) {
        Listeners.BLOCK_IGNITE.runEvent(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlock(BlockBurnEvent e) {
        Listeners.BLOCK_BURN.runEvent(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlock(BlockSpreadEvent e) {
        Listeners.BLOCK_SPREAD.runEvent(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlock(LeavesDecayEvent e) {
        Listeners.LEAVES_DECAY.runEvent(e);
    }

}
