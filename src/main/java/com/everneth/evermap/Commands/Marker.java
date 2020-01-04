package com.everneth.evermap.commands;

import com.everneth.evermap.App;
import com.everneth.evermap.manager.ComponentManager;
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
import co.aikar.commands.annotation.Syntax;

@CommandAlias("marker")
public class Marker extends BaseCommand {
  DynmapManager dm = DynmapManager.getDynmapManager();

  @Dependency
  Plugin plugin = App.getPlugin();

  @Subcommand("add")
  public class Add extends BaseCommand {
    @Subcommand("base")
    @Syntax("<name> - Marker name")
    public void onBaseAdd(CommandSender sender, String label) {
      Player player = (Player) sender;
      EMIPlayer emiPlayer = PlayerUtils.getPlayer(player.getName());
      Integer count = PlayerUtils.getMarkerCount(emiPlayer.getId(), MarkerType.BASE);
      if (count >= plugin.getConfig().getInt("base_limit")) {
        player.sendMessage(Utils.color(plugin.getConfig().getString("base_limit_reach")));
        return;
      }
      String markerID = dm.addMarker(player, label);
      MarkerModel markerModel = new MarkerModel(markerID, label, emiPlayer, MarkerType.BASE, true, emiPlayer);
      markerModel.Insert();

      ((Player) sender).spigot().sendMessage(ComponentManager.createMarkerLocationComponent(player.getLocation()));
    }
  }

  @Subcommand("remove")
  public class Remove extends BaseCommand {

    @Subcommand("base")
    @Syntax("<id|name>")
    public void onRemove(CommandSender sender, String label) {
      Player player = (Player) sender;
      dm.removeMarker(player, label);
    }
  }
}