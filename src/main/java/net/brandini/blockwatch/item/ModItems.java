package net.brandini.blockwatch.item;

import net.brandini.blockwatch.BlockWatch;
import net.brandini.blockwatch.item.custom.KeyCard;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {

    // ========== ITEMS ==========

    public static final Item ITEM_HALL = registerItem("item_hall", new Item(new Item.Settings()));
    public static final Item CAMERA_LENS = registerItem("camera_lens", new Item(new Item.Settings()));
    public static final Item CONTROL_PANEL = registerItem("control_panel", new Item(new Item.Settings()));

    public static final Item CAMERA_KEY_CARD = registerItem("camera_key_card", new KeyCard(new Item.Settings()));


    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(BlockWatch.MOD_ID, name), item);
    }

    public static void registerModItems() {
        BlockWatch.LOGGER.info("Registering Mod Items for " + BlockWatch.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> {
            entries.add(ITEM_HALL);
            entries.add(CAMERA_LENS);
            entries.add(CONTROL_PANEL);
            entries.add(CAMERA_KEY_CARD);
        });
    }

}
