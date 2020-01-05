package com.everneth.evermap.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.everneth.evermap.App;
import com.everneth.evermap.models.EMIPlayer;
import com.everneth.evermap.models.MarkerType;

import org.bukkit.plugin.Plugin;

import co.aikar.idb.DB;
import co.aikar.idb.DbRow;

public class PlayerUtils {
  private static Plugin plugin = App.getPlugin();

  private static DbRow getPlayerRow(String playerName) {
    CompletableFuture<DbRow> futurePlayer;
    DbRow player = new DbRow();
    futurePlayer = DB.getFirstRowAsync("SELECT * FROM players WHERE player_name = ?", playerName);
    try {
      player = futurePlayer.get();
    } catch (Exception e) {
      plugin.getLogger().warning(e.getMessage());
    }
    return player;
  }

  private static DbRow getPlayerRow(Integer id) {
    CompletableFuture<DbRow> futurePlayer;
    DbRow player = new DbRow();
    futurePlayer = DB.getFirstRowAsync("SELECT * FROM players WHERE player_id = ?", id);
    try {
      player = futurePlayer.get();
    } catch (Exception e) {
      plugin.getLogger().warning(e.getMessage());
    }
    return player;
  }

  public static EMIPlayer getPlayer(String name) {
    DbRow playerRow = getPlayerRow(name);
    EMIPlayer player = new EMIPlayer(playerRow.getString("player_uuid"), playerRow.getString("player_name"),
        playerRow.getInt("player_id"));

    return player;
  }

  public static EMIPlayer getPlayer(Integer id) {
    DbRow playerRow = getPlayerRow(id);
    EMIPlayer player = new EMIPlayer(playerRow.getString("player_uuid"), playerRow.getString("player_name"),
        playerRow.getInt("player_id"));

    return player;
  }

  public static Integer getMarkerCount(Integer playerID, MarkerType type) {
    DbRow markerRow;
    Integer count = 0;

    CompletableFuture<DbRow> markerObjectFuture = DB.getFirstRowAsync(
        "SELECT COUNT(id) AS count FROM markers WHERE owned_by = ? AND type = ?", playerID, type.getValue());

    try {
      markerRow = markerObjectFuture.get();
      count = markerRow.getInt("count");
    } catch (Exception e) {
      plugin.getLogger().warning(e.getMessage());
    }

    return count;
  }

  public static List<DbRow> getMarkerList(Integer playerID, MarkerType type) {
    List<DbRow> markerList = new ArrayList<DbRow>();

    try {
      markerList = DB.getResultsAsync("SELECT label, verified FROM markers WHERE owned_by = ? AND type = ?", playerID,
          type.getValue()).get();
    } catch (Exception e) {
      plugin.getLogger().warning(e.getMessage());
    }

    return markerList;
  }

  public static DbRow getMarker(Integer playerID, String label) {
    DbRow marker = new DbRow();
    try {
      marker = DB.getFirstRowAsync("SELECT id FROM markers WHERE owned_by = ? AND label = ?", playerID, label).get();
    } catch (Exception e) {
      plugin.getLogger().warning(e.getMessage());
    }
    return marker;
  }

}