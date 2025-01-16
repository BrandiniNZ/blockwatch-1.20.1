package net.brandini.blockwatch.block;

import net.brandini.blockwatch.BlockWatch;
import net.brandini.blockwatch.block.custom.CameraMonitor;
import net.brandini.blockwatch.block.custom.DomeCamera;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

public class ModBlocks {

    // ========== BLOCKS ==========

    // CCTV CAMERA
    public static final Block CCTV_CAMERA_BLOCK = registerBlock("cctv_camera",
            new Block(AbstractBlock.Settings.create()
                    .strength(4f)
                    .sounds(BlockSoundGroup.METAL)));
    // New DomeCamera (using your custom DomeCamera class)
    public static final Block DOME_CAMERA_BLOCK = registerBlock("dome_camera",
            new DomeCamera(AbstractBlock.Settings.create()
                    .strength(1f)
                    .sounds(BlockSoundGroup.METAL)));

    public static final Block CAMERA_MONITOR_BLOCK = registerBlock("camera_monitor",
            new CameraMonitor(CameraMonitor.Type.MONITOR, AbstractBlock.Settings.create()
                    .strength(1f)
                    .sounds(BlockSoundGroup.METAL)));

    //helper methods
    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, Identifier.of(BlockWatch.MOD_ID, name), block);
    }

    private static void registerBlockItem(String name, Block block) {
        Registry.register(Registries.ITEM, Identifier.of(BlockWatch.MOD_ID, name),
                new BlockItem(block, new Item.Settings()));
    }

    public static void registerModBlock() {
        BlockWatch.LOGGER.info("Registering Mod Blocks for " + BlockWatch.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL).register(fabricItemGroupEntries ->
                fabricItemGroupEntries.add(ModBlocks.CCTV_CAMERA_BLOCK));
    }
}