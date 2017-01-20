package com.vexentric.commandmanager.manager;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import com.vexentric.commandmanager.commands.CommandTest;

public class CommandManager implements CommandExecutor {
	private ArrayList<Command> commands;

	private JavaPlugin pl;

	public CommandManager(JavaPlugin m) {
		pl = m;
		commands = new ArrayList<Command>();
		addCommand(new CommandTest(pl));
		registerCommands();
	}

	public void addCommand(Command c) {
		commands.add(c);
	}

	public ArrayList<Command> getCommands() {
		return commands;
	}

	public void registerCommands() {
		for (Command c : this.getCommands()) {
			for (String l : c.getAliases()) {
				pl.getCommand(l).setExecutor(this);
			}
		}
	}

	@Override
	public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
		for (Command c : getCommands()) {
			if (c.getAliases().contains(label.toLowerCase())) {
				if (!meetsRequirements(c, sender)) {
					sender.sendMessage(
							ChatColor.translateAlternateColorCodes('&', "&8[&6Command&8] &7Unknown command."));
					return false;
				}
				try {
					c.execute(sender, args);
				} catch (Exception e) {
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
							"&8[&6Command&8] &cIncorrect usage. &7(&f" + c.getSyntax() + "&7)"));
				}
				return true;
			}
		}
		return false;
	}

	public boolean meetsRequirements(Command c, CommandSender s) {
		for (Requirement r : c.getRequirements()) {
			if (!c.hasRequirement(s, r)) {
				return false;
			}
		}
		return true;
	}

	public enum Requirement {
		PLAYER, ADMIN, MODERATOR, HELPER;
	}

}
