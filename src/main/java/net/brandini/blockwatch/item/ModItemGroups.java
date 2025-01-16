package net.brandini.blockwatch.item;

import net.brandini.blockwatch.BlockWatch;
import net.brandini.blockwatch.block.ModBlocks;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import static net.brandini.blockwatch.item.ModItems.CAMERA_KEY_CARD;

public class ModItemGroups {

    // Creative Mode Tab for BLOCKWATCH items
    public static final ItemGroup BLOCKWATCH_ITEM_GROUP = Registry.register(Registries.ITEM_GROUP,
            Identifier.of(BlockWatch.MOD_ID, "blockwatch_items"),
            FabricItemGroup.builder().icon(() -> new ItemStack(ModItems.ITEM_HALL))
                    .displayName(Text.translatable("itemgroup.blockwatch.blockwatch_items"))
                    .entries((displayContext, entries) -> {
                        entries.add(ModItems.ITEM_HALL);
                        entries.add(ModItems.CONTROL_PANEL);
                        entries.add(ModItems.CAMERA_LENS);
                        entries.add(CAMERA_KEY_CARD);

                        entries.add(ModBlocks.CCTV_CAMERA_BLOCK);
                        entries.add(ModBlocks.CAMERA_MONITOR_BLOCK);


                    }).build());

    public static void registerItemGroups() {
        BlockWatch.LOGGER.info("Registering Item Groups for " + BlockWatch.MOD_ID);
    }
}

