package com.gdmc.api;

import java.util.ArrayList;

import com.gdmc.core.AgentPlugin;

/**
 * The agent class register. An agent controller class must be registered here
 * for it to be summonable in the world using the agent-summon command.
 */
public class AgentControllerRegister {
	private ArrayList<String> controllers = new ArrayList<>();

	/**
	 * @return the instance of this class that is currently in use by the server.
	 */
	public static AgentControllerRegister getInstance() {
		assert AgentPlugin.getInstance() != null;
		return AgentPlugin.getInstance().getRegister();
	}

	/**
	 * Register a new agent controller class on the server.
	 * 
	 * @param agentController the class to register
	 */
	public void register(Class<? extends BasicAgentController> agentController) {
		String name = agentController.getSimpleName().split("\\$")[1]; // TODO Find another way to do that
		if (controllers.contains(name)) {
			AgentPlugin.getInstance().getLogger().severe("Scripts in the python folder tried to register \"" + name
					+ "\" more than once. It's most likely a mistake, rename your classes!");
		} else {
			controllers.add(name);
		}
	}

	/**
	 * Called by the server to know about the different controllers available.
	 * 
	 * TODO: create a data class, and store information about the constructor
	 * parameters.
	 * 
	 * @return the list of available class names
	 */
	public String[] getControllers() {
		return controllers.toArray(String[]::new);
	}
}
