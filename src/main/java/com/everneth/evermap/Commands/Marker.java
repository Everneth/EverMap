package com.everneth.evermap.commands;

import java.util.List;

import com.everneth.evermap.App;
import com.everneth.evermap.manager.ComponentManager;
import com.everneth.evermap.manager.DynmapManager;
import com.everneth.evermap.manager.MarkerManager;
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

  @Dependency
  Plugin plugin = App.getPlugin();

  @Subcommand("add")
  public class Add extends BaseCommand {

    @Subcommand("base")
    @Syntax("<name> <description> - Marker name and description (Optional)")
    public void onBaseAdd(CommandSender sender, String label, @Optional String desc) {
      Player player = (Player) sender;
      EMIPlayer emiPlayer = PlayerUtils.getPlayer(player.getUniqueId());

      // Check marker limit
      if (PlayerUtils.markerLimitReach(emiPlayer.getId(), MarkerType.BASE)) {
        player.sendMessage(Utils.color(plugin.getConfig().getString("base_limit_reach")));
        return;
      }

      MarkerManager.AddMarker(player, emiPlayer, label, desc, MarkerType.BASE);

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
      EMIPlayer emiPlayer = PlayerUtils.getPlayer(player.getUniqueId());
      DbRow marker = PlayerUtils.getMarker(emiPlayer.getId(), label);

      if (marker == null) {
        player.sendMessage(Utils.color(plugin.getConfig().getString("marker_not_found")));
        return;
      }

      MarkerManager.RemoveMarker(player, marker.getString("id"));

    }
  }

  // marker list
  @Subcommand("list")
  @Syntax("<type> - Type of marker")
  public void onList(CommandSender sender, String type) {
    Player player = (Player) sender;
    EMIPlayer emiPlayer = PlayerUtils.getPlayer(player.getUniqueId());
    List<DbRow> markerList = PlayerUtils.getMarkerList(emiPlayer.getId(), MarkerType.fromString(type));
    if (markerList.size() == 0) {
      player.sendMessage(Utils.color("&4No base markers found!"));
      return;
    }

    MarkerManager.GetMarkerList(player, markerList);

  }
}