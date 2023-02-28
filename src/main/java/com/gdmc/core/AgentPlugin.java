package com.gdmc.core;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.Properties;

import org.bukkit.command.PluginCommand;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import org.python.util.PythonInterpreter;

import com.gdmc.api.AgentControllerRegister;
import com.gdmc.commands.AgentSummonCommand;

/**
 * The core of the plugin. An instance of this class is created by Spigot on server startup.
 * 
 * @author silver
 */
public class AgentPlugin extends JavaPlugin implements Listener {
	private static AgentPlugin instance;
	private AgentControllerRegister register;
	private PythonInterpreter interpreter;
	private File dataFolder;
	private File[] pythonScripts;
	private static boolean pythonInitialized = false;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onEnable() {
		instance = this;
		dataFolder = new File(getDataFolder().getAbsoluteFile().getParentFile().getParentFile().getPath()
				+ File.separator + "python" + File.separator);
		register = new AgentControllerRegister();

		PluginCommand command = this.getCommand("agent-summon");
		AgentSummonCommand c = new AgentSummonCommand();
		command.setExecutor(c);
		command.setTabCompleter(c);

		if (!pythonInitialized) {
			Properties pythonPath = new Properties();
			pythonPath.setProperty("python.path", dataFolder.getAbsolutePath());
			PythonInterpreter.initialize(System.getProperties(), pythonPath, new String[] { "" });
			pythonInitialized = true;
		}

		regenerateExampleScripts();
		reloadPythonFolder();

		this.getLogger().info(getPythonScripts().length + " python file(s) found. " + register.getControllers().length
				+ " agent controller(s) registered: [" + String.join(", ", register.getControllers()) + "]");
	}

	@Override
	public void onDisable() {
		instance = null;
	}

	public static AgentPlugin getInstance() {
		return instance;
	}

	public void regenerateExampleScripts() {
		try {
			InputStream is = getResource("python" + File.separator + "example_agent.py");
			Files.createDirectories(dataFolder.toPath());
			Files.copy(is, new File(dataFolder.getAbsolutePath() + File.separator + "example_agent.py").toPath(),
					StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void reloadPythonFolder() {
		interpreter = new PythonInterpreter();
		if (dataFolder.list().length > 0) {
			pythonScripts = Arrays.stream(dataFolder.list()).filter(path -> path.endsWith(".py"))
					.map(path -> new File(path)).toArray(File[]::new);
			Arrays.stream(pythonScripts).forEach(name -> interpreter
					.exec("from " + name.getName().substring(0, name.getName().length() - 3) + " import *"));
		} else {
			pythonScripts = new File[0];
		}

	}

	public File[] getPythonScripts() {
		return pythonScripts;
	}

	public AgentControllerRegister getRegister() {
		return register;
	}

	public PythonInterpreter getPythonInterpreter() {
		return interpreter;
	}
}
