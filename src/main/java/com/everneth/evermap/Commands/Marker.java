package com.everneth.evermap.commands;

import com.everneth.evermap.App;
import com.everneth.evermap.manager.DynmapManager;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Dependency;
import co.aikar.commands.annotation.Subcommand;

@CommandAlias("marker")
public class Marker extends BaseCommand {
  DynmapManager dm = DynmapManager.getDynmapManager();

  @Dependency
  Plugin plugin = App.getPlugin();

  @Subcommand("add")
  public void onAdd(CommandSender sender, String label, String desc) {
    Player player = (Player) sender;
    dm.addMarker(player, label, desc);
  }

  @Subcommand("remove")
  public void onRemove(CommandSender sender, String label) {
    Player player = (Player) sender;
    dm.removeMarker(player, label);
  }
}