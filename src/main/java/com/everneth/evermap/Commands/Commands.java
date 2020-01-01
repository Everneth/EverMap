package com.everneth.evermap.Commands;

import org.bukkit.plugin.Plugin;

import co.aikar.commands.BukkitCommandManager;

public class Commands {

  public void init(Plugin plugin) {

    BukkitCommandManager manager = new BukkitCommandManager(plugin);
    manager.registerCommand(new Marker());
  }
}