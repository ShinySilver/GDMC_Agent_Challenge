package com.gdmc.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

public class ReloadCommand implements CommandExecutor, TabCompleter {

	public ReloadCommand() {
		// Not implemented
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		return false;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		ArrayList<String> list = new ArrayList<>();
		if (args.length == 1) {
			//
		} else if (args.length == 2 && !args[0].equals("undo")) {
			//
		}
		return list;
	}
}
