package com.everneth.evermap.manager;

import java.util.List;

import com.everneth.evermap.models.EMIPlayer;
import com.everneth.evermap.models.MarkerModel;
import com.everneth.evermap.models.MarkerType;
import com.everneth.evermap.utils.Utils;

import org.bukkit.entity.Player;

import co.aikar.idb.DbRow;

public class MarkerManager {
  private static DynmapManager dm = DynmapManager.getDynmapManager();

  public static void AddMarker(Player player, EMIPlayer emiPlayer, String label, String desc, MarkerType type) {
    String markerID = dm.addMarker(player, label, desc);
    MarkerModel markerModel = new MarkerModel(markerID, label, emiPlayer, type, 0, null);
    markerModel.Insert();
  }

  // TODO - Need to remove marker from db too.
  public static void RemoveMarker(Player player, String id) {
    dm.removeMarker(player, id);
  }

  // TODO - Make everything customiazable.
  public static void GetMarkerList(Player player, List<DbRow> markerList) {
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