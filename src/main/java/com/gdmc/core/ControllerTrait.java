package com.gdmc.core;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.python.core.PyString;
import net.citizensnpcs.api.trait.Trait;

public abstract class ControllerTrait extends Trait implements Runnable {

	private int taskId;

	public static ControllerTrait fromPython(String className, String[] args) {
		ControllerTrait returnValue = null;
		try {
			returnValue = (ControllerTrait) AgentPlugin.getInstance().getPythonInterpreter().get(className)
					.__call__(Arrays.stream(args).map(str -> new PyString(str)).toArray(PyString[]::new))
					.__tojava__(ControllerTrait.class);
		} catch (Exception e) {
			Bukkit.getLogger().warning("Exception encountered while loading agent controller " + className + ".");
			e.printStackTrace();
		}
		return returnValue;
	}

	/**
	 * Create an agent controller, to be assigned to a citizen NPC.
	 */
	protected ControllerTrait() {
		super("gdmc_agent_controller");
	}

	/**
	 * Called just before the NPC entity is spawned. Schedule run().
	 */
	@Override
	public final void onAttach() {
		taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(AgentPlugin.getInstance(), this, 1, 0);
	}

	/**
	 * Called just after the NPC entity is despawned. Unschedule run().
	 */
	@Override
	public final void onRemove() {
		Bukkit.getScheduler().cancelTask(taskId);
	}
}
