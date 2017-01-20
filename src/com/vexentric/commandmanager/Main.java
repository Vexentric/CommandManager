package com.vexentric.commandmanager;

import org.bukkit.plugin.java.JavaPlugin;

import com.vexentric.commandmanager.manager.CommandManager;

public class Main extends JavaPlugin {

	public void onEnable() {
		new CommandManager(this);
	}

}
