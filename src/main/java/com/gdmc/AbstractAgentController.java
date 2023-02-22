package com.gdmc;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import com.gdmc.core.ControllerTrait;

import net.md_5.bungee.api.ChatColor;

/**
 * An abstract player-type controllable agent.
 * 
 * @author silver
 */
public abstract class AbstractAgentController extends ControllerTrait {

	private String agentName;
	private static int agentCount = 0;

	/**
	 * Create an agent controller, to be assigned to a citizen NPC. Will be called
	 * by the server.
	 * 
	 * The agent will be assigned a name under the following format:
	 * Agent#%agent_id% with %agent_id% being the current number of agent plus one.
	 */
	protected AbstractAgentController() {
		this("Agent#" + (++agentCount));
	}

	/**
	 * Create an agent controller with a name, to be assigned to a citizen NPC. Will
	 * be called by the server.
	 */
	protected AbstractAgentController(String agentName) {
		super();
		this.agentName = agentName;
	}

	/**
	 * Called every tick
	 */
	@Override
	public abstract void run();

	/**
	 * Called just after the NPC is spawned.
	 */
	@Override
	public abstract void onSpawn();

	/**
	 * Called just before the NPC entity is despawned.
	 */
	@Override
	public abstract void onDespawn();

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

	//////////////////////////////////////////////////////////////////////////////
	// Chat Interaction / Debug
	//////////////////////////////////////////////////////////////////////////////

	public void say(String message) {
		Bukkit.getServer().broadcastMessage(ChatColor.YELLOW + "[" + agentName + "]: " + ChatColor.RESET + message);
	}

	//////////////////////////////////////////////////////////////////////////////
	// Interaction
	//////////////////////////////////////////////////////////////////////////////

	// place blocks / break blocks
	// move
	// attack

	//////////////////////////////////////////////////////////////////////////////
	// Perception
	//////////////////////////////////////////////////////////////////////////////

	// sense nearby entities
	// sense nearby agent
	// sense nearby blocks
	// sense nearby interactive blocks
	// sense nearby biomes
	// sense nearby height map

}
