package com.gdmc.core;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.python.core.PyObject;
import org.python.core.PyString;

import com.gdmc.api.BasicAgentController;

import net.citizensnpcs.api.exception.NPCLoadException;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.trait.Trait;
import net.citizensnpcs.api.util.DataKey;

public class ControllerTrait extends Trait implements Runnable {

	private int taskId;
	private BasicAgentController controller;

	/**
	 * Create a dummy agent controller. "load" will need to be called before
	 * assigning it to an NPC.
	 */
	public ControllerTrait() {
		super("gdmc_agent_controller");
	}

	/**
	 * Create an agent controller, to be assigned to a citizen NPC.
	 */
	public ControllerTrait(String className, String[] args) {
		super("gdmc_agent_controller");

		controller = null;
		if (className != null && args != null) {
			loadController(className, args);
		}
	}

	private void loadController(String className, String[] args) {
		try {
			controller = (BasicAgentController) AgentPlugin.getInstance().getPythonInterpreter().get(className)
					.__call__(Arrays.stream(args).map(str -> new PyString(str)).toArray(PyObject[]::new))
					.__tojava__(BasicAgentController.class);
		} catch (Exception e) {
			AgentPlugin.getInstance().getLogger()
					.warning("Exception encountered while loading agent controller " + className + ".");
			e.printStackTrace();
		}
	}

	public void linkToNPC(NPC npc) {
		super.linkToNPC(npc);
		if (this.controller != null) {
			this.controller.linkToNPC(npc);
		}
	}

	/**
	 * Called just before the NPC entity is spawned. Schedule run().
	 */
	@Override
	public void onAttach() {
		taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(AgentPlugin.getInstance(), new Runnable() {
			private int timer = 0;

			@Override
			public void run() {
				if (timer <= 0) {
					try {
						timer = controller.onTick();
					} catch (Exception e) {
						e.printStackTrace();
						AgentPlugin.getInstance().getLogger()
								.warning("An error occured while ticking an agent. It will not be updated anymore "
										+ "until the next reload.");
						Bukkit.getScheduler().cancelTask(taskId);
					}
				} else {
					timer--;
				}
			}
		}, 1, 1);
	}

	/**
	 * Called just after the NPC entity is despawned. Unschedule run().
	 */
	@Override
	public final void onRemove() {
		Bukkit.getScheduler().cancelTask(taskId);
	}

	/**
	 * Called just after the NPC is spawned.
	 */
	public void onSpawn() {
		controller.onSpawn();
	}

	/**
	 * Called just before the NPC entity is despawned.
	 */
	public void onDespawn() {
		controller.onDespawn();

	}

	/**
	 * Save the npc
	 */
	public void save(DataKey key) {
		key.setString("controller_class", controller.getClass().getSimpleName().split("\\$")[1]);
		// controller.save(key);
	}

	/**
	 * Load the npc
	 */
	public void load(DataKey key) throws NPCLoadException {
		loadController(key.getString("controller_class"), null);
		// controller.load(key);
	}
}
