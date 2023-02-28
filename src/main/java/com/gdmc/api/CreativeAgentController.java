package com.gdmc.api;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;

import net.citizensnpcs.api.npc.NPC;
import net.md_5.bungee.api.ChatColor;

/**
 * An player-type controllable agent, with functions to move, perceive and have
 * basic interactions with the world.
 * 
 * The agents made with this class will not be targeted by monsters, can place
 * any block without having to farm them beforehand, and don't need food or bed.
 * 
 * It is designed to be inherited by python agent-classes.
 */
public abstract class CreativeAgentController {
	/**
	 * The range at which you perceive blocks and entities
	 */
	protected final int CLOSE_PERCEPTION_RANGE = 32;

	/**
	 * The range at which you perceive heightmap and biome map
	 */
	protected final int FAR_PERCEPTION_RANGE = 128;

	/**
	 * Internal variable to the citizens2 npc.
	 * 
	 * @see <a href=
	 *      "https://jd.citizensnpcs.co/net/citizensnpcs/api/npc/NPC.html">net.citizensnpcs.api.npc.NPC</a>
	 */
	protected NPC npc;

	/**
	 * Create an agent controller, to be assigned to a citizen NPC. Will be called
	 * by the server.
	 */
	public CreativeAgentController(NPC npc) {
		this.npc = npc;
	}

	/**
	 * Called every tick
	 */
	public abstract void onTick();

	/**
	 * Called just after the NPC is spawned.
	 */
	public abstract void onSpawn();

	/**
	 * Called just before the NPC entity is despawned.
	 */
	public abstract void onDespawn();

	//////////////////////////////////////////////////////////////////////////////
	// Chat Interaction / Debug
	//////////////////////////////////////////////////////////////////////////////

	/**
	 * Say something.
	 * 
	 * @param message
	 */
	public void say(String message) {
		Bukkit.getServer().broadcastMessage(ChatColor.YELLOW + "[" + npc.getName() + "]: " + ChatColor.RESET + message);
	}

	//////////////////////////////////////////////////////////////////////////////
	// Navigation
	//////////////////////////////////////////////////////////////////////////////

	/**
	 * 
	 * @param location
	 * @return
	 */
	public boolean canNavigateTo(Location location) {
		return this.npc.getNavigator().canNavigateTo(location);
	}

	/**
	 * 
	 * @param location
	 * @return
	 */
	public boolean navigateTo(Location location) {
		if (!this.npc.getNavigator().canNavigateTo(location))
			return false;
		this.npc.getNavigator().setTarget(location);
		return true;
	}

	/**
	 * 
	 * @param location
	 */
	public void navigateStraightTo(Location location) {
		this.npc.getNavigator().setStraightLineTarget(location);
	}

	/**
	 * 
	 * @return
	 */
	public boolean isNavigating() {
		return this.npc.getNavigator().isNavigating();
	}

	/**
	 * 
	 */
	public void cancelNavigation() {
		this.npc.getNavigator().cancelNavigation();
	}

	//////////////////////////////////////////////////////////////////////////////
	// Interaction
	//////////////////////////////////////////////////////////////////////////////

	/**
	 * Place a block at the given position.
	 * 
	 * @param m
	 * @param location
	 */
	public void placeBlock(Material m, Location location) {
		location.getBlock().setType(m);
	}

	/**
	 * Break the block at the given position. Do not attempt to pickup the item.
	 * 
	 * @param location
	 */
	public void breakBlock(Location location) {
		location.getBlock().setType(Material.AIR);
	}

	/**
	 * Break the block at the given position.
	 * 
	 * @param location
	 * @return the dropped items, had the block been mined by a player with a
	 *         diamond pickaxe.
	 */
	public ItemStack[] harvestBlock(Location location) {
		ItemStack[] out = location.getBlock().getDrops(new ItemStack(Material.DIAMOND_PICKAXE))
				.toArray(ItemStack[]::new);
		location.getBlock().setType(Material.AIR);
		return out;
	}

	//////////////////////////////////////////////////////////////////////////////
	// Perception
	//////////////////////////////////////////////////////////////////////////////

	/**
	 * 
	 * @return
	 */
	public Entity[] getNearbyEntities() {
		return this.npc.getEntity()
				.getNearbyEntities(CLOSE_PERCEPTION_RANGE, CLOSE_PERCEPTION_RANGE, CLOSE_PERCEPTION_RANGE)
				.toArray(Entity[]::new);
	}

	/**
	 * 
	 * @param location
	 * @return
	 */
	public Material getBlock(Location location) {
		if (location.distanceSquared(this.npc.getEntity().getLocation()) > CLOSE_PERCEPTION_RANGE
				* CLOSE_PERCEPTION_RANGE)
			return null;
		return location.getBlock().getType();
	}

	/**
	 * 
	 * @param location
	 * @return
	 */
	public Material getRawBlock(Location location) {
		return location.getBlock().getType();
	}
	
	// sense nearby biomes
	// sense nearby height map

}
