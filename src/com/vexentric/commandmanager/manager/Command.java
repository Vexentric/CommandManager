package com.vexentric.commandmanager.manager;

import java.util.ArrayList;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.vexentric.commandmanager.manager.CommandManager.Requirement;

public abstract class Command {

	protected JavaPlugin pl;

	private ArrayList<String> aliases = new ArrayList<String>();
	private ArrayList<Requirement> requirements = new ArrayList<Requirement>();
	private String syntax;
	private String description;

	public Command(JavaPlugin m) {
		pl = m;
	}

	public ArrayList<String> getAliases() {
		return aliases;
	}

	public void setAliases(ArrayList<String> aliases) {
		this.aliases = aliases;
	}

	public String getSyntax() {
		return syntax;
	}

	public void setSyntax(String syntax) {
		this.syntax = syntax;
	}

	public void addAlias(String a) {
		aliases.add(a);
	}

	public void addRequirement(Requirement r) {
		requirements.add(r);
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ArrayList<Requirement> getRequirements() {
		return requirements;
	}

	public void setRequirements(ArrayList<Requirement> requirements) {
		this.requirements = requirements;
	}

	public abstract boolean execute(CommandSender sender, String[] args) throws Exception;

	public boolean hasRequirement(CommandSender s, Requirement r) {
		switch (r) {
		case PLAYER:
			if (!(s instanceof Player)) {
				return false;
			}
			break;
		case ADMIN:
			if (s instanceof Player) {
				if (!s.hasPermission("commandmanager.admin")) {
					return false;
				}
			}
			break;
		case MODERATOR:
			if (s instanceof Player) {
				if (!s.hasPermission("commandmanager.moderator")) {
					return false;
				}
			}
			break;
		case HELPER:
			if (s instanceof Player) {
				if (!s.hasPermission("commandmanager.helper")) {
					return false;
				}
			}
			break;
		}
		return true;
	}

}
