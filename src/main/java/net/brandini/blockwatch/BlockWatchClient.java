package net.brandini.blockwatch;

import net.brandini.blockwatch.block.entity.ModBlockEntities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;

public class BlockWatchClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // Register the block entity renderer
        BlockEntityRendererRegistry.register(
                ModBlockEntities.CAMERA_MONITOR_BE,
                net.brandini.blockwatch.block.custom.CameraMonitorRenderer::new
        );
    }
}
