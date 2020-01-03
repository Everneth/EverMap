package com.everneth.evermap.commands;

import com.everneth.evermap.App;
import com.everneth.evermap.manager.DynmapManager;
import com.everneth.evermap.models.EMIPlayer;
import com.everneth.evermap.models.MarkerModel;
import com.everneth.evermap.models.MarkerType;
import com.everneth.evermap.utils.PlayerUtils;
import com.everneth.evermap.utils.Utils;

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
  public void onAdd(CommandSender sender, String label) {
    Player player = (Player) sender;
    String markerID = dm.addMarker(player, label);
    EMIPlayer emiPlayer = PlayerUtils.getPlayer(sender.getName());
    MarkerModel markerModel = new MarkerModel(markerID, label, emiPlayer, MarkerType.BASE, true, emiPlayer);
    markerModel.Insert();

    player.sendMessage(Utils.color("&3 Marker created" + "&4 " + MarkerType.BASE));
  }

  @Subcommand("remove")
  public void onRemove(CommandSender sender, String label) {
    Player player = (Player) sender;
    dm.removeMarker(player, label);
  }
}