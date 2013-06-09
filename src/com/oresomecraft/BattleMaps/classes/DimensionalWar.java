package com.oresomecraft.BattleMaps.classes;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.oresomecraft.BattleMaps.BattleMap;
import com.oresomecraft.BattleMaps.MapInterface;
import com.oresomecraft.BattleMaps.OresomeBattlesMaps;
import com.oresomecraft.OresomeBattles.Gamemode;
import com.oresomecraft.OresomeBattles.events.ClearSpawnsEvent;
import com.oresomecraft.OresomeBattles.events.InventoryEvent;
import com.oresomecraft.OresomeBattles.events.ReadyMapsEvent;

public class DimensionlWar extends BattleMap implements MapInterface, Listener {

    OresomeBattlesMaps plugin;
    public DimensionalWar(OresomeBattlesMaps pl) {
        super(pl);
        plugin = pl;
    }

    public ArrayList<Location> redSpawns = new ArrayList<Location>();
    public ArrayList<Location> blueSpawns = new ArrayList<Location>();
    public ArrayList<Location> FFASpawns = new ArrayList<Location>();

    String name = "dimentional";
    String fullName = "Dimensional War";
    String creators = "ninsai";
    Gamemode[] modes = {Gamemode.TDM, Gamemode.CTF};

    @EventHandler(priority = EventPriority.NORMAL)
    public void readyMap(ReadyMapsEvent event) {
        addMap(name);
        readyTDMSpawns();
        readyFFASpawns();
        addCreators(name, creators); 
        setFullName(name, fullName);
        setGamemodes(name, modes);
    }

    public void readyTDMSpawns() {
        World w = Bukkit.getServer().getWorld(name);

        Location redSpawn = new Location(w, 1, 88, 12, 1, 0);
        Location blueSpawn = new Location(w, 9, 88, 107, 179, 0);

        redSpawns.add(redSpawn);
        blueSpawns.add(blueSpawn);

        setRedSpawns(name, redSpawns);
        setBlueSpawns(name, blueSpawns);
        
        Location redFlag = new Location(w, 1, 89, 9, 0, 0);
        Location blueFlag = new Location(w, 9, 89, 109, 0, 0);
        setCTFFlags(name, redFlag, blueFlag);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void applyInventory(InventoryEvent event) {
        String par = event.getMessage();
        Player p = event.getPlayer();
        Inventory i = p.getInventory();
        if (par.equalsIgnoreCase(name)) {
            clearInv(p);
            
            ItemStack HEALTH_POTION = new ItemStack(Material.POTION, 1, (short) 16373);
            ItemStack PIE = new ItemStack(Material.PUMPKIN_PIE, 1);
            ItemStack STEAK = new ItemStack(Material.COOKED_BEEF, 1);
            ItemStack BOW = new ItemStack(Material.BOW, 1);
            ItemStack ARROWS = new ItemStack(Material.ARROW, 64);
            ItemStack EXPBOTTLES = new ItemStack(Material.EXP_BOTTLE, 5);
            ItemStack IRON_HELMET = new ItemStack(Material.IRON_HELMET, 1);
            ItemStack IRON_CHESTPLATE = new ItemStack(Material.IRON_CHESTPLATE, 1);
            ItemStack IRON_PANTS = new ItemStack(Material.IRON_LEGGINGS, 1);
            ItemStack IRON_BOOTS = new ItemStack(Material.IRON_BOOTS, 1);
            ItemStack IRON_SWORD = new ItemStack(Material.IRON_SWORD, 1);

            p.getInventory().setBoots(IRON_BOOTS);
            p.getInventory().setLeggings(IRON_PANTS);
            p.getInventory().setChestplate(IRON_CHESTPLATE);
            p.getInventory().setHelmet(IRON_HELMET);

            i.setItem(0, IRON_SWORD);
            i.setItem(1, BOW);
            i.setItem(2, PIE);
            i.setItem(3, STEAK);
            i.setItem(4, HEALTH_POTION);
            i.setItem(5, EXPBOTTLES);
            i.setItem(6, ARROWS);

        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void clearSpawns(ClearSpawnsEvent event) {
        redSpawns.clear();
        blueSpawns.clear();
    }

    // Region. (Top corner block and bottom corner block.
    // Top left corner.
    public int x1 = -12;
    public int y1 = 117;
    public int z1 = 117;

    //Bottom right corner.
    public int x2 = 26;
    public int y2 = 80;
    public int z2 = 3;

    // Getting the region
    public boolean contains(Location loc, int x1, int x2, int y1,
            int y2, int z1, int z2) {
        int bottomCornerX = x1 < x2 ? x1 : x2; 
        int bottomCornerZ = z1 < z2 ? z1 : z2; 
        int topCornerX = x1 > x2 ? x1 : x2;
        int topCornerZ = z1 > z2 ? z1 : z2;
        int bottomCornerY = y1 < y2 ? y1 : y2;
        int topCornerY = y1 > y2 ? y1 : y2;
        if (loc.getX() >= bottomCornerX && loc.getX() <= topCornerX) {
            if (loc.getZ() >= bottomCornerZ && loc.getZ() <= topCornerZ) {
                if (loc.getY() >= bottomCornerY && loc.getY() <= topCornerY) {
                    return true;
                }
            }
        }
        return false;
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void protection(BlockBreakEvent event) {

        Block b = event.getBlock();
        Location loc = b.getLocation();

        if (loc.getWorld().getName().equals(name)) {

            event.setCancelled(true);
        }

    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void protection1(BlockPlaceEvent event) {

        Block b = event.getBlock();
        Location loc = b.getLocation();

        if (loc.getWorld().getName().equals(name)) {

            event.setCancelled(true);

        }

    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void firepie(PlayerInteractEvent event){  
      Player p = event.getPlayer();
     Action a = event.getAction();
     ItemStack i = p.getItemInHand();
     Inventory inv = p.getInventory();
     Material mat = i.getType();

       if( mat == Material.PUMPKIN_PIE){
         if(a == Action.RIGHT_CLICK_AIR || a == Action.RIGHT_CLICK_BLOCK){

           p.setFireTicks(1000);
         }
       }
        }
  @SuppressWarnings("deprecation")
  @EventHandler(priority =  EventPriority.NORMAL)
    public void firepieattack(EntityDamageByEntityEvent event){  
    Entity e = event.getEntity();
    Entity attacker = event.getDamager();


    if(e instanceof Player){
    Player p = (Player) e;


    if(attacker instanceof Player){
      Player o = (Player) attacker;
      Inventory inv = o.getInventory();
      ItemStack i = o.getItemInHand();
      Material mat = i.getType();
      
       o.setFireTicks(1000);
            }
    }
  }
}
