package com.gdmc.core;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.python.core.PyString;

import com.gdmc.api.CreativeAgentController;

import net.citizensnpcs.api.trait.Trait;

public class ControllerTrait extends Trait implements Runnable {

	private int taskId;
	private CreativeAgentController controller;

	public static ControllerTrait fromPython(String className, String[] args) {
		CreativeAgentController controller = null;
		try {
			controller = (CreativeAgentController) AgentPlugin.getInstance().getPythonInterpreter().get(className)
					.__call__(Arrays.stream(args).map(str -> new PyString(str)).toArray(PyString[]::new))
					.__tojava__(CreativeAgentController.class);
		} catch (Exception e) {
			AgentPlugin.getInstance().getLogger()
					.warning("Exception encountered while loading agent controller " + className + ".");
			e.printStackTrace();
			return null;
		}
		return new ControllerTrait(controller);
	}

	/**
	 * Create an agent controller, to be assigned to a citizen NPC.
	 */
	protected ControllerTrait(CreativeAgentController controller) {
		super("gdmc_agent_controller");
		this.controller = controller;
	}

	/**
	 * Called just before the NPC entity is spawned. Schedule run().
	 */
	@Override
	public void onAttach() {
		taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(AgentPlugin.getInstance(), new Runnable() {
			@Override
			public void run() {
				controller.onTick();
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
}
