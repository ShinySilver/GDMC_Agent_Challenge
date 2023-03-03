package com.gdmc.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.EntityType;

import com.gdmc.api.AgentControllerRegister;
import com.gdmc.core.AgentPlugin;
import com.gdmc.core.ControllerTrait;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.md_5.bungee.api.ChatColor;

public class AgentSummonCommand implements CommandExecutor, TabCompleter {

	public AgentSummonCommand() {
		// Not implemented
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (args.length < 4) {
			return false;
		}

		int x, y, z;
		try {
			x = Integer.parseInt(args[1]);
			y = Integer.parseInt(args[2]);
			z = Integer.parseInt(args[3]);
		} catch (NumberFormatException e) {
			return false;
		}

		String npcType = args[0];
		if (Arrays.stream(AgentPlugin.getInstance().getRegister().getControllers()).anyMatch(npcType::equals)) {
			NPC npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, "");
			npc.addTrait(new ControllerTrait(npcType,
					Arrays.copyOfRange(args, Math.max(args.length, 4), args.length)));
			npc.spawn(new Location(Bukkit.getWorlds().get(0), x, y, z));
			sender.sendMessage(ChatColor.GRAY + "NPC spawned!");
		} else {
			sender.sendMessage(
					ChatColor.GRAY + "Agent controller \"" + npcType + "\" is not defined. Currently available: ["
							+ String.join(", ", AgentPlugin.getInstance().getRegister().getControllers()) + "]");
		}
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		ArrayList<String> list = new ArrayList<>();
		if (args.length == 1) {
			Arrays.stream(AgentControllerRegister.getInstance().getControllers()).forEach(name -> list.add(name));
		} else if (args.length == 2) {
			list.add("<x>");
		} else if (args.length == 2) {
			list.add("<y>");
		} else if (args.length == 2) {
			list.add("<z>");
		} else if (args.length == 2) {
			list.add("<optionnal_args>");
		}
		return list;
	}
}
