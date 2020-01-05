package com.everneth.evermap.manager;

import java.util.UUID;

import com.everneth.evermap.App;
import com.everneth.evermap.models.MarkerModel;
import com.everneth.evermap.utils.Utils;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import org.bukkit.plugin.Plugin;
import org.dynmap.DynmapAPI;
import org.dynmap.markers.Marker;
import org.dynmap.markers.MarkerAPI;
import org.dynmap.markers.MarkerSet;

public final class DynmapManager {
  private static DynmapManager dm;

  private Plugin plugin = App.getPlugin();
  private DynmapAPI dynmap = (DynmapAPI) plugin.getServer().getPluginManager().getPlugin("dynmap");
  private MarkerAPI markerapi = dynmap.getMarkerAPI();
  private MarkerSet baseMarkerSet;

  private DynmapManager() {
    if (baseMarkerSet == null) {
      if (markerapi.getMarkerSet("bases") == null) {
        baseMarkerSet = dynmap.getMarkerAPI().createMarkerSet("bases", plugin.getConfig().getString("base_set_label"),
            null, true);
      }
      baseMarkerSet = markerapi.getMarkerSet("bases");
      return;
    }
  }

  public static DynmapManager getDynmapManager() {
    if (dm == null) {
      dm = new DynmapManager();
    }

    return dm;
  }

  public String addMarker(Player player, String label, String desc) {
    Location loc = player.getLocation();
    String markerID = UUID.randomUUID().toString();
    Marker marker = baseMarkerSet.createMarker(markerID, player.getName() + " - " + label, true,
        loc.getWorld().getName(), loc.getX(), loc.getY(), loc.getZ(),
        markerapi.getMarkerIcon(plugin.getConfig().getString("base_icon")), true);
    marker.setDescription(desc);
    return marker.getMarkerID();
  }

  public void removeMarker(Player player, String id) {
    Marker marker = baseMarkerSet.findMarker(id);
    marker.deleteMarker();
    MarkerModel.Delete(id);
    player.sendMessage(Utils.color(plugin.getConfig().getString("marker_deleted")));
  }
}