package net.brandini.blockwatch.datagen;

import net.brandini.blockwatch.block.ModBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.Models;

public class ModModelProvider extends FabricModelProvider {

    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        // Generate a block state with all faces using the same texture
        blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.CAMERA_MONITOR);
    }

    @Override
    public void generateItemModels(net.minecraft.data.client.ItemModelGenerator itemModelGenerator) {
        // Use the GENERATED model for the item version of the block
        itemModelGenerator.register(ModBlocks.CAMERA_MONITOR.asItem(), Models.GENERATED);
    }
}
