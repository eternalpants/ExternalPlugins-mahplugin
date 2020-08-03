package net.runelite.client.plugins.externals.oneclick.Comparables;

import com.google.common.collect.ImmutableSet;
import net.runelite.api.ItemID;
import net.runelite.api.MenuEntry;
import net.runelite.api.MenuOpcode;
import net.runelite.client.plugins.externals.oneclick.OneClickPlugin;

import java.util.Set;

public class Farming implements ClickComparable {
	
	/*
	05/27/20 | Sea
	- Added all Herb seeds
	- Added OneClick "Use" Bucket -> Compost Bin
	 */
	
	// (HERB SEEDS) Types of Herb seeds that can be used to left click on empty Herb patch
	private static final Set<Integer> HERB_SEEDS = ImmutableSet.of(
			ItemID.GUAM_SEED, ItemID.RANARR_SEED, ItemID.MARRENTILL_SEED, ItemID.TARROMIN_SEED, ItemID.HARRALANDER_SEED,
			ItemID.RANARR_SEED, ItemID.TOADFLAX_SEED, ItemID.IRIT_SEED, ItemID.AVANTOE_SEED, ItemID.KWUARM_SEED, ItemID.SNAPDRAGON_SEED,
			ItemID.CADANTINE_SEED, ItemID.LANTADYME_SEED, ItemID.DWARF_WEED_SEED, ItemID.TORSTOL_SEED
	);
	
	// (FLOWER) Types of Bushes seeds that can be used to left click on empty Flower patch
	private static final Set<Integer> FLOWER_SEEDS = ImmutableSet.of(
			ItemID.LIMPWURT_SEED
	);
	
	// (Allotment) Types of Allotment seeds that can be used to left click on empty Allotment patch
	private static final Set<Integer> ALLOTMENT_SEEDS = ImmutableSet.of(
			ItemID.STRAWBERRY_SEED, ItemID.WATERMELON_SEED, ItemID.SNAPE_GRASS_SEED
	);
	
	// (FLOWER PATCH)
	private static final Set<String> FLOWER_PATCH = ImmutableSet.of(
			"<col=ffff>Flower Patch"
	);
	
	// (ALLOTMENT PATCH)
	private static final Set<String> ALLOTMENT_PATCH = ImmutableSet.of(
			"<col=ffff>Allotment"
	);
	
	// (ALLOTMENT PATCH PLANTED)
	private static final Set<String> ALLOTMENT_PATCH_COMPOST = ImmutableSet.of(
			"<col=ffff>Limpwurt plant", "<col=ffff>Strawberry seed", "<col=ffff>Watermelon seed", "<col=ffff>Snape grass seedling"
	);
	
	// EMPTY BUCKET TO USE ON COMPOST BIN
	private static final Set<Integer> EMPTY_BUCKET = ImmutableSet.of(
			ItemID.BUCKET
	);
	
	// EMPTY BUCKET TO USE ON COMPOST BIN
	private static final Set<String> COMPOST_BIN = ImmutableSet.of(
			"<col=ffff>Compost Bin"
	);
	
	// (HERB TYPES) Types of Herb seeds that can be used to left click on the Tool Leprechaun
	// WORK IN PROGRESS
	private static final Set<Integer> HERB_TYPE = ImmutableSet.of(
			ItemID.GUAM_LEAF, ItemID.RANARR_WEED
	);
	
	// (SPECIAL SEEDS) Types of Special seeds that can be used to left click on empty patch
	private static final Set<Integer> SPECIAL_SEEDS = ImmutableSet.of(
			ItemID.CACTUS_SEED, ItemID.BELLADONNA_SEED
	);
	
	private static final Set<Integer> COMPOST_TYPE = ImmutableSet.of(
			ItemID.BOTTOMLESS_COMPOST_BUCKET_22997, ItemID.SUPERCOMPOST, ItemID.ULTRACOMPOST
	);
	
	// Herb patches that are empty
	private static final Set<String> EMPTY_FARM_PATCHES = ImmutableSet.of(
			"<col=ffff>Herb patch"
	);
	
	// Belladonna & Cactus patch
	private static final Set<String> SPECIAL_FARM_PATCHES = ImmutableSet.of(
			"<col=ffff>Belladonna", "<col=ffff>Cactus"
	);
	
	// Herb patches that have seeds planted
	private static final Set<String> FARM_PATCHES_COMPOST = ImmutableSet.of(
			"<col=ffff>Herbs"
	);
	
	@Override
	public boolean isEntryValid(MenuEntry event) {
		return
				// Herb patches that are empty
				event.getOpcode() == MenuOpcode.GAME_OBJECT_SECOND_OPTION.getId() &&
						EMPTY_FARM_PATCHES.contains(event.getTarget()) ||
						
						// Herb patches that have seeds planted
						event.getOpcode() == MenuOpcode.GAME_OBJECT_SECOND_OPTION.getId() &&
								FARM_PATCHES_COMPOST.contains(event.getTarget()) ||
						
						// Belladonna & Cacti patch
						event.getOpcode() == MenuOpcode.GAME_OBJECT_SECOND_OPTION.getId() &&
								SPECIAL_FARM_PATCHES.contains(event.getTarget()) ||
						
						// Compost Bin
						event.getOpcode() == MenuOpcode.GAME_OBJECT_SECOND_OPTION.getId() &&
								COMPOST_BIN.contains(event.getTarget()) ||
						
						// Flower Patch
						event.getOpcode() == MenuOpcode.GAME_OBJECT_SECOND_OPTION.getId() &&
								FLOWER_PATCH.contains(event.getTarget()) ||
						
						// Allotment Patch
						event.getOpcode() == MenuOpcode.GAME_OBJECT_SECOND_OPTION.getId() &&
								ALLOTMENT_PATCH.contains(event.getTarget()) ||
						
						// Allotment Planted
						event.getOpcode() == MenuOpcode.GAME_OBJECT_SECOND_OPTION.getId() &&
								ALLOTMENT_PATCH_COMPOST.contains(event.getTarget());
	}
	
	@Override
	public void modifyEntry(OneClickPlugin plugin, MenuEntry event) {
		if (EMPTY_FARM_PATCHES.contains(event.getTarget())) {
			if (plugin.findItem(HERB_SEEDS).getLeft() == -1) {
				return;
			}
			event.setOption("Use");
			event.setTarget("<col=ff9040>Herb seed<col=ffffff> -> " + plugin.getTargetMap().get(event.getIdentifier()));
			event.setOpcode(MenuOpcode.ITEM_USE_ON_GAME_OBJECT.getId());
			event.setForceLeftClick(true);
			
		} else if (FARM_PATCHES_COMPOST.contains(event.getTarget()) ||
				SPECIAL_FARM_PATCHES.contains(event.getTarget()) ||
				ALLOTMENT_PATCH_COMPOST.contains(event.getTarget())) {
			if (plugin.findItem(COMPOST_TYPE).getLeft() == -1) {
				return;
			}
			event.setOption("Use");
			event.setTarget("<col=ff9040>Compost<col=ffffff> -> " + plugin.getTargetMap().get(event.getIdentifier()));
			event.setOpcode(MenuOpcode.ITEM_USE_ON_GAME_OBJECT.getId());
			event.setForceLeftClick(true);
			
		} else if (SPECIAL_FARM_PATCHES.contains(event.getTarget())) {
			if (plugin.findItem(SPECIAL_SEEDS).getLeft() == -1) {
				return;
			}
			event.setOption("Use");
			event.setTarget("<col=ff9040>Special seed<col=ffffff> -> " + plugin.getTargetMap().get(event.getIdentifier()));
			event.setOpcode(MenuOpcode.ITEM_USE_ON_GAME_OBJECT.getId());
			event.setForceLeftClick(true);
			
		} else if (COMPOST_BIN.contains(event.getTarget())) {
			if (plugin.findItem(EMPTY_BUCKET).getLeft() == -1) {
				return;
			}
			event.setOption("Use");
			event.setTarget("<col=ff9040>Bucket<col=ffffff> -> " + plugin.getTargetMap().get(event.getIdentifier()));
			event.setOpcode(MenuOpcode.ITEM_USE_ON_GAME_OBJECT.getId());
			event.setForceLeftClick(true);
			
		} else if (FLOWER_PATCH.contains(event.getTarget())) {
			if (plugin.findItem(FLOWER_SEEDS).getLeft() == -1) {
				return;
			}
			event.setOption("Use");
			event.setTarget("<col=ff9040>Flower seed<col=ffffff> -> " + plugin.getTargetMap().get(event.getIdentifier()));
			event.setOpcode(MenuOpcode.ITEM_USE_ON_GAME_OBJECT.getId());
			event.setForceLeftClick(true);
			
		} else if (ALLOTMENT_PATCH.contains(event.getTarget())) {
			if (plugin.findItem(ALLOTMENT_SEEDS).getLeft() == -1) {
				return;
			}
			event.setOption("Use");
			event.setTarget("<col=ff9040>Allotment seed<col=ffffff> -> " + plugin.getTargetMap().get(event.getIdentifier()));
			event.setOpcode(MenuOpcode.ITEM_USE_ON_GAME_OBJECT.getId());
			event.setForceLeftClick(true);
		}
	}
	
	@Override
	public boolean isClickValid(MenuEntry event) {
		return (event.getOpcode() == MenuOpcode.ITEM_USE_ON_GAME_OBJECT.getId() &&
				
				// There are two of same event.getTarget().contains Compost because the first one the normal herb patches show "herbs" on stage 1/5
				// Whereas Weiss (Unconfirmed) and Troll Stronghold show "Herb patch" on stage 1
				
				// (HERB SEEDS) Planting herb seeds
				event.getTarget().contains("<col=ff9040>Herb seed<col=ffffff> -> ") ||
				
				// (HERB SEEDS) Using compost on patches
				event.getTarget().contains("<col=ff9040>Compost<col=ffffff> -> ") &&
						event.getTarget().contains("<col=ffff>Herbs")) ||
				
				// (SPECIAL PATCHES) Using compost on Belladonna
				event.getTarget().contains("<col=ff9040>Compost<col=ffffff> -> ") &&
						event.getTarget().contains("<col=ffff>Belladonna") ||
				
				// (SPECIAL PATCHES) Using compost on Cactus
				event.getTarget().contains("<col=ff9040>Compost<col=ffffff> -> ") &&
						event.getTarget().contains("<col=ffff>Cactus") ||
				
				// (SPECIAL PATCHES) Planting Belladonna seeds
				event.getTarget().contains("<col=ff9040>Special seed<col=ffffff> -> ") &&
						event.getTarget().contains("<col=ffff>Belladonna") ||
				event.getTarget().contains("<col=ffff>Cactus") ||
				
				// HERB TYPE
				event.getTarget().contains("<col=ff9040>Herb<col=ffffff> -> ") &&
						event.getTarget().contains("<col=ffff>Tool Leprechaun") ||
				
				// USE EMPTY BUCKET ON COMPOST BIN
				event.getTarget().contains("<col=ff9040>Bucket<col=ffffff> -> ") &&
						event.getTarget().contains("<col=ffff>Compost Bin") ||
				
				// (FLOWER PATCH) Planting Flower seeds
				event.getTarget().contains("<col=ff9040>Flower seed<col=ffffff> -> ") &&
						event.getTarget().contains("<col=ffff>Flower Patch") ||
				
				// (ALLOTMENT PATCH) Planting Allotment seeds
				event.getTarget().contains("<col=ff9040>Allotment seed<col=ffffff> -> ") &&
						event.getTarget().contains("<col=ffff>Allotment") ||
				
				// (FLOWER PATCH) Using compost on Flower Patches
				event.getTarget().contains("<col=ff9040>Compost<col=ffffff> -> ") &&
						event.getTarget().contains("<col=ffff>Limpwurt plant") ||
				event.getTarget().contains("<col=ffff>Watermelon seed") ||
				event.getTarget().contains("<col=ffff>Strawberry seed") ||
				event.getTarget().contains("<col=ffff>Snape grass seedling");
	}
	
	@Override
	public void modifyClick(OneClickPlugin plugin, MenuEntry event) {
		
		if (event.getTarget().contains("<col=ff9040>Herb seed<col=ffffff> -> ")) {
			plugin.updateSelectedItem(HERB_SEEDS);
			event.setOpcode(MenuOpcode.ITEM_USE_ON_GAME_OBJECT.getId());
			
		} else if (event.getTarget().contains("<col=ff9040>Compost<col=ffffff> -> ") &&
				event.getTarget().contains("<col=ffff>Herbs") ||
				event.getTarget().contains("<col=ffff>Limpwurt plant") ||
				event.getTarget().contains("<col=ffff>Watermelon seed") ||
				event.getTarget().contains("<col=ffff>Strawberry seed") ||
				event.getTarget().contains("<col=ffff>Snape grass seedling")) {
			plugin.updateSelectedItem(COMPOST_TYPE);
			event.setOpcode(MenuOpcode.ITEM_USE_ON_GAME_OBJECT.getId());
			
		} else if (event.getTarget().contains("<col=ff9040>Special seed<col=ffffff> -> ") &&
				event.getTarget().contains("<col=ffff>Belladonna") ||
				event.getTarget().contains("<col=ffff>Cactus")) {
			plugin.updateSelectedItem(SPECIAL_SEEDS);
			event.setOpcode(MenuOpcode.ITEM_USE_ON_GAME_OBJECT.getId());
			
		} else if (event.getTarget().contains("<col=ff9040>Bucket<col=ffffff> -> ") &&
				event.getTarget().contains("<col=ffff>Compost Bin")) {
			plugin.updateSelectedItem(EMPTY_BUCKET);
			event.setOpcode(MenuOpcode.ITEM_USE_ON_GAME_OBJECT.getId());
			
		} else if (event.getTarget().contains("<col=ff9040>Flower seed<col=ffffff> -> ") &&
				event.getTarget().contains("<col=ffff>Flower Patch")) {
			plugin.updateSelectedItem(FLOWER_SEEDS);
			event.setOpcode(MenuOpcode.ITEM_USE_ON_GAME_OBJECT.getId());
			
		} else if (event.getTarget().contains("<col=ff9040>Allotment seed<col=ffffff> -> ") &&
				event.getTarget().contains("<col=ffff>Allotment")) {
			plugin.updateSelectedItem(ALLOTMENT_SEEDS);
			event.setOpcode(MenuOpcode.ITEM_USE_ON_GAME_OBJECT.getId());
		}
	}
}