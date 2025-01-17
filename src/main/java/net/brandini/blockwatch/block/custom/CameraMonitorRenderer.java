package net.brandini.blockwatch.block.custom;

import net.brandini.blockwatch.block.entity.CameraMonitorBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;

public class CameraMonitorRenderer implements BlockEntityRenderer<CameraMonitorBlockEntity> {

    // Example screen textures
    private static final Identifier SCREEN_OFF = new Identifier("blockwatch", "textures/block/monitor_screen_off.png");
    private static final Identifier SCREEN_ON = new Identifier("blockwatch", "textures/block/monitor_screen_on.png");

    public CameraMonitorRenderer(BlockEntityRendererFactory.Context ctx) {
        // Constructor
    }

    @Override
    public void render(CameraMonitorBlockEntity entity, float tickDelta,
                       MatrixStack matrices, VertexConsumerProvider vertexConsumers,
                       int light, int overlay) {

        // Get rotation angle (0-15 -> 0° to 337.5°)
        float angleDegrees = entity.getRotation() * 22.5f;

        matrices.push();

        // Move to the center of the block for proper rotation
        matrices.translate(0.5, 0.5, 0.5);

        // Apply Y-axis rotation
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(angleDegrees));

        // Undo centering
        matrices.translate(-0.5, -0.5, -0.5);

        // Render the block model dynamically
        MinecraftClient.getInstance().getBlockRenderManager().renderBlockAsEntity(
                entity.getCachedState(),
                matrices,
                vertexConsumers,
                light,
                overlay
        );

        matrices.pop();

        // Render the screen overlay
        renderScreenOverlay(entity, matrices, vertexConsumers, light, overlay);
    }

    /**
     * Renders the screen overlay based on the active state of the monitor.
     */
    private void renderScreenOverlay(CameraMonitorBlockEntity entity,
                                     MatrixStack matrices,
                                     VertexConsumerProvider vertexConsumers,
                                     int light, int overlay) {
        matrices.push();

        // Screen position and dimensions in block coordinates
        float zPos = 8.3f / 16f; // Slightly above the front face
        float minX = 0.0f;
        float maxX = 1.0f;
        float minY = 3f / 16f;
        float maxY = 12f / 16f;

        // Select the appropriate texture
        Identifier screenTexture = entity.isActive() ? SCREEN_ON : SCREEN_OFF;

        // Render the screen quad
        MatrixStack.Entry entry = matrices.peek();
        var vc = vertexConsumers.getBuffer(RenderLayer.getEntityCutout(screenTexture));

        vc.vertex(entry.getPositionMatrix(), minX, maxY, zPos)
                .color(255, 255, 255, 255)
                .texture(0.0f, 1.0f)
                .light(light)
                .overlay(overlay)
                .normal(0, 0, -1)
                .next();

        vc.vertex(entry.getPositionMatrix(), maxX, maxY, zPos)
                .color(255, 255, 255, 255)
                .texture(1.0f, 1.0f)
                .light(light)
                .overlay(overlay)
                .normal(0, 0, -1)
                .next();

        vc.vertex(entry.getPositionMatrix(), maxX, minY, zPos)
                .color(255, 255, 255, 255)
                .texture(1.0f, 0.0f)
                .light(light)
                .overlay(overlay)
                .normal(0, 0, -1)
                .next();

        vc.vertex(entry.getPositionMatrix(), minX, minY, zPos)
                .color(255, 255, 255, 255)
                .texture(0.0f, 0.0f)
                .light(light)
                .overlay(overlay)
                .normal(0, 0, -1)
                .next();

        matrices.pop();
    }

    @Override
    public boolean rendersOutsideBoundingBox(CameraMonitorBlockEntity blockEntity) {
        return true;
    }
}