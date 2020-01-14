package com.everneth.evermap.manager;

import com.everneth.evermap.App;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class ComponentManager {

  private static Plugin plugin = App.getPlugin();

  public static void sendComponent(CommandSender sender, TextComponent tc) {
    ((Player) sender).spigot().sendMessage(tc);
  }

  public static TextComponent createMarkerLocationComponent(Location loc) {
    TextComponent tc = new TextComponent(
        TextComponent.fromLegacyText(plugin.getConfig().getString("marker_added_text_component")));
    tc.setBold(true);
    tc.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "http://" + plugin.getConfig().getString("dynmap_url")
        + "/?x=" + loc.getX() + "y=" + loc.getY() + "&z=" + loc.getZ() + "&zoom=10"));
    tc.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
        new ComponentBuilder(plugin.getConfig().getString("marker_added_text_component_desc")).create()));

    return tc;
  }
}