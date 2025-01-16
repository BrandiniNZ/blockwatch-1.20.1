package net.brandini.blockwatch;

import net.brandini.blockwatch.block.ModBlocks;
import net.brandini.blockwatch.item.ModItemGroups;
import net.brandini.blockwatch.item.ModItems;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BlockWatch implements ModInitializer {
	public static final String MOD_ID = "blockwatch";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		//initialize creative tabs
		ModItemGroups.registerItemGroups();
		//initialize items
		ModItems.registerModItems();
		//initialize blocks
		ModBlocks.registerModBlock();
	}
}