package com.gdmc.api;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

/**
 * A survival mode agent implementation with survival building, basic inventory,
 * combat and food support.
 * 
 * If enabled in the configuration files, the agents will be targeted by
 * monsters, will have to forage and mine for resources and food, and will need
 * a bed to sleep.
 */
public abstract class SurvivalAgentController extends BasicAgentController {

	//////////////////////////////////////////////////////////////////////////////
	// Inventory
	//////////////////////////////////////////////////////////////////////////////

	/**
	 * Returns the inventory of the agent as an array. The inventory size is fixed,
	 * and individual item stacks can be null.
	 * 
	 * @see <a href=
	 *      "https://hub.spigotmc.org/javadocs/spigot/org/bukkit/inventory/ItemStack.html">org.bukkit.inventory.ItemStack</a>
	 * @return The agent's inventory as an array of ItemStack
	 */
	public ItemStack[] getInventory() {
		return ((InventoryHolder) npc.getEntity()).getInventory().getStorageContents();
	}

	/**
	 * Returns the current item stack in the given equipment slot. Can be null.
	 * 
	 * @see <a href=
	 *      "https://hub.spigotmc.org/javadocs/spigot/org/bukkit/inventory/ItemStack.html">org.bukkit.inventory.ItemStack</a>
	 * @see <a href=
	 *      "https://hub.spigotmc.org/javadocs/spigot/org/bukkit/inventory/EquipmentSlot.html">org.bukkit.inventory.EquipmentSlot</a>
	 * @return The equipment in the given slot
	 */
	public ItemStack getEquipment(EquipmentSlot slot) {
		return ((InventoryHolder) npc.getEntity()).getInventory().getContents()[slot.ordinal()];
	}

	/**
	 * Move an item from the inventory to the given equipment slot. Does nothing if
	 * the item stack passed as an argument cannot be found in the inventory. If an
	 * item was already equipped, it goes back to the inventory.
	 * 
	 * @see <a href=
	 *      "https://hub.spigotmc.org/javadocs/spigot/org/bukkit/inventory/ItemStack.html">org.bukkit.inventory.ItemStack</a>
	 * @see <a href=
	 *      "https://hub.spigotmc.org/javadocs/spigot/org/bukkit/inventory/EquipmentSlot.html">org.bukkit.inventory.EquipmentSlot</a>
	 * @return The equipment in the given slot. Can be set to null to empty an
	 *         equipment slot as long as there is still room in the inventory.
	 */
	public ItemStack equip(EquipmentSlot slot, ItemStack stack) {
		return ((InventoryHolder) npc.getEntity()).getInventory().getContents()[slot.ordinal()] = stack;
	}

	/**
	 * 
	 * @param stack
	 */
	public void eat(ItemStack stack) {

	}

	/**
	 * 
	 * @param stack
	 */
	public void drop(ItemStack stack) {

	}

	/**
	 * 
	 * @param stack
	 */
	public void pickup(ItemStack stack) {

	}

	/**
	 * 
	 * @param stack
	 * @param blockPos
	 */
	public void storeToContainer(ItemStack stack, Location blockPos) {
	}

	/**
	 * 
	 * @param stack
	 * @param blockPos
	 */
	public void retrieveFromContainer(ItemStack stack, Location blockPos) {

	}

	/**
	 * 
	 * @param blockPos
	 * @return
	 */
	public ItemStack[] lookupContainer(Location blockPos) {
		return null;
	}

	/**
	 * Place a block at the given location.
	 * 
	 * @param m
	 * @param location
	 */
	@Override
	public void placeBlock(Material m, Location location) {
		location.getBlock().setType(m);
	}

	/**
	 * Break a block at the given location. Feel free to call pickup() on the
	 * resulting items :)
	 * 
	 * @param location
	 */
	@Override
	public ItemStack[] breakBlock(Location location) {
		return super.breakBlock(location);
	}
}
