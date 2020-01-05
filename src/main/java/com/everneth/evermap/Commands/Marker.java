package com.everneth.evermap.commands;

import java.util.List;

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
import co.aikar.commands.annotation.Optional;
import co.aikar.commands.annotation.Subcommand;
import co.aikar.commands.annotation.Syntax;
import co.aikar.idb.DbRow;
import net.md_5.bungee.api.chat.TextComponent;

@CommandAlias("marker")
public class Marker extends BaseCommand {
  DynmapManager dm = DynmapManager.getDynmapManager();

  @Dependency
  Plugin plugin = App.getPlugin();

  @Subcommand("add")
  public class Add extends BaseCommand {
    @Subcommand("base")
    @Syntax("<name> <description> - Marker name and description (Optional)")
    public void onBaseAdd(CommandSender sender, String label, @Optional String desc) {
      Player player = (Player) sender;
      EMIPlayer emiPlayer = PlayerUtils.getPlayer(player.getName());

      Integer count = PlayerUtils.getMarkerCount(emiPlayer.getId(), MarkerType.BASE);
      if (count >= plugin.getConfig().getInt("base_limit")) {
        player.sendMessage(Utils.color(plugin.getConfig().getString("base_limit_reach")));
        return;
      }

      String markerID = dm.addMarker(player, label, desc);
      MarkerModel markerModel = new MarkerModel(markerID, label, emiPlayer, MarkerType.BASE, 1, emiPlayer);
      markerModel.Insert();

      TextComponent tc = new TextComponent(
          TextComponent.fromLegacyText(plugin.getConfig().getString("marker_added_text")));
      tc.addExtra(ComponentManager.createMarkerLocationComponent(player.getLocation()));

      ((Player) sender).spigot().sendMessage(tc);
    }
  }

  @Subcommand("remove")
  public class Remove extends BaseCommand {

    @Subcommand("base")
    @Syntax("<name> - Marker name")
    public void onRemove(CommandSender sender, String label) {
      Player player = (Player) sender;
      EMIPlayer emiPlayer = PlayerUtils.getPlayer(player.getName());
      DbRow marker = PlayerUtils.getMarker(emiPlayer.getId(), label);
      if (marker == null) {
        player.sendMessage(Utils.color(plugin.getConfig().getString("marker_not_found")));
        return;
      }
      dm.removeMarker(player, marker.getString("id"));
    }
  }

  // marker list
  @Subcommand("list")
  @Syntax("<type> - Type of marker")
  public void onList(CommandSender sender, String type) {
    Player player = (Player) sender;
    EMIPlayer emiPlayer = PlayerUtils.getPlayer(player.getName());
    List<DbRow> markerList = PlayerUtils.getMarkerList(emiPlayer.getId(), MarkerType.fromString(type));
    if (markerList.size() == 0) {
      player.sendMessage(Utils.color("&4No base markers found!"));
      return;
    }
    player.sendMessage("");
    player.sendMessage(Utils.color("&6---------- &bYour marker list (Bases) &6----------"));
    for (DbRow data : markerList) {
      String verifed = "&4Not verified";
      if (data.getInt("verified") == 1) {
        verifed = "&2Verified";
      }
      player.sendMessage(Utils.color("&eLabel: &2" + data.getString("label") + "&e - " + " &eVerified: " + verifed));
    }
    player.sendMessage("");
  }
}