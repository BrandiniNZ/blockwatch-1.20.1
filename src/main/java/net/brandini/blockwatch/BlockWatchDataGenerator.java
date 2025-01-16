package net.brandini.blockwatch;

import net.brandini.blockwatch.datagen.ModBlockTagProvider;
import net.brandini.blockwatch.datagen.ModItemTagProvider;
import net.brandini.blockwatch.datagen.ModLootTableProvider;
import net.brandini.blockwatch.datagen.ModModelProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class BlockWatchDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

		pack.addProvider((ModBlockTagProvider::new));
		pack.addProvider((ModItemTagProvider::new));
		pack.addProvider((ModLootTableProvider::new));
		pack.addProvider((ModModelProvider::new));

	}
}
