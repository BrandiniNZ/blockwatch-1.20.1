package net.brandini.blockwatch.block.entity;

import net.brandini.blockwatch.block.ModBlocks;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlockEntities {
    public static BlockEntityType<CameraMonitorBlockEntity> CAMERA_MONITOR_BE;

    public static void registerAllBlockEntities() {
        CAMERA_MONITOR_BE = Registry.register(
                Registries.BLOCK_ENTITY_TYPE,
                new Identifier("blockwatch", "camera_monitor"),
                FabricBlockEntityTypeBuilder.create(
                        CameraMonitorBlockEntity::new,
                        ModBlocks.CAMERA_MONITOR
                ).build(null)
        );
    }
}
