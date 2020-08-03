package net.runelite.client.plugins.externals.oneclick.Comparables;

import com.google.common.collect.ImmutableSet;
import net.runelite.api.ItemID;
import net.runelite.api.MenuEntry;
import net.runelite.api.MenuOpcode;
import net.runelite.client.plugins.externals.oneclick.OneClickPlugin;

import java.util.Set;

public class Cannon implements ClickComparable {
	
	
	private static final Set<Integer> CANNONBALL = ImmutableSet.of(
			ItemID.CANNONBALL
	);
	
	private static final Set<String> DWARF_CANNON = ImmutableSet.of(
			"<col=ffff>Dwarf multicannon"
	);
	
	@Override
	public boolean isEntryValid(MenuEntry event) {
		return event.getOpcode() == MenuOpcode.EXAMINE_OBJECT.getId() &&
				DWARF_CANNON.contains(event.getTarget());
	}
	
	
	@Override
	public void modifyEntry(OneClickPlugin plugin, MenuEntry event) {
		if (plugin.findItem(ItemID.CANNONBALL).getLeft() == -1) {
			return;
		}
		event.setOption("Use");
		event.setTarget("<col=ff9040>Cannonball<col=ffffff> -> " + plugin.getTargetMap().get(event.getIdentifier()));
		event.setOpcode(MenuOpcode.ITEM_USE_ON_GAME_OBJECT.getId());
		event.setForceLeftClick(true);
	}
	
	@Override
	public boolean isClickValid(MenuEntry event) {
		return (event.getOpcode() == MenuOpcode.ITEM_USE_ON_GAME_OBJECT.getId() &&
				event.getTarget().contains("<col=ff9040>Cannonball<col=ffffff> -> "));
	}
	
	@Override
	public void modifyClick(OneClickPlugin plugin, MenuEntry event) {
		if (event.getTarget().contains("<col=ff9040>Cannonball<col=ffffff> -> ")) {
			if (plugin.updateSelectedItem(CANNONBALL)) {
				event.setOpcode(MenuOpcode.ITEM_USE_ON_GAME_OBJECT.getId());
			}
		}
	}
}
