package com.everneth.evermap.Commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Dependency;
import co.aikar.commands.annotation.Subcommand;
import net.md_5.bungee.api.ChatColor;

@CommandAlias("marker")
public class Marker extends BaseCommand {

  @Dependency
  private Plugin plugin;

  @Subcommand("add")
  public void onMarker(CommandSender sender, String name, String desc) {

    Player player = (Player) sender;
    player.sendMessage(
        ChatColor.translateAlternateColorCodes('&', "&3This is a start for marker command &4" + name + " &8" + desc));
  }
}