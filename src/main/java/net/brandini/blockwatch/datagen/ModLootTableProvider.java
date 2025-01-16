package net.brandini.blockwatch.datagen;

import net.brandini.blockwatch.block.ModBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;

public class ModLootTableProvider extends FabricBlockLootTableProvider {
    public ModLootTableProvider(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generate() {
        addDrop(ModBlocks.CAMERA_MONITOR_BLOCK);
        addDrop(ModBlocks.DOME_CAMERA_BLOCK);
        addDrop(ModBlocks.CCTV_CAMERA_BLOCK);

    }
}
