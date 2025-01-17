package net.brandini.blockwatch.item.custom;

import net.brandini.blockwatch.block.ModBlocks;
import net.brandini.blockwatch.block.custom.CameraMonitor;
import net.brandini.blockwatch.block.custom.DomeCamera;
import net.brandini.blockwatch.block.entity.CameraMonitorBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class KeyCard extends Item {

    public KeyCard(Settings settings) {
        super(settings);
    }

    /**
     * Called when the player right-clicks a block with this item in hand.
     */
    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        if (world.isClient) {
            return ActionResult.SUCCESS; // Client side doesn't do pairing logic
        }

        BlockPos pos = context.getBlockPos();
        BlockState state = world.getBlockState(pos);
        ItemStack stack = context.getStack();
        ServerPlayerEntity player = (ServerPlayerEntity) context.getPlayer();

        if (player == null) return ActionResult.FAIL;

        // 1) Are we in pairing mode (i.e., is the KeyCard already storing a "first block")?
        NbtCompound nbt = stack.getOrCreateNbt();
        boolean inPairingMode = nbt.getBoolean("InPairingMode");

        // 2) Check what block is being clicked
        Block block = state.getBlock();
        boolean isCamera = (block instanceof DomeCamera);
        boolean isMonitor = (block instanceof CameraMonitor);

        // SHIFT-click on the monitor to unpair
        if (isMonitor && player.isSneaking()) {
            // Unpair logic:
            // For example, reset the "screen" to default texture
            // you can store pairing data in the block's block entity
            unpairMonitor(world, pos, player);
            return ActionResult.SUCCESS;
        }

        if (!isCamera && !isMonitor) {
            // Not a camera or monitor => ignore
            player.sendMessage(Text.literal("This block is neither a camera nor a monitor.").formatted(Formatting.GRAY), false);
            return ActionResult.SUCCESS;
        }

        if (!inPairingMode) {
            // Start pairing mode
            nbt.putBoolean("InPairingMode", true);
            nbt.putLong("FirstBlockPos", pos.asLong());
            nbt.putString("FirstBlockType", isCamera ? "camera" : "monitor");

            player.sendMessage(
                    Text.literal("First block selected: " + (isCamera ? "Camera" : "Monitor"))
                            .formatted(Formatting.GREEN),
                    false
            );
        } else {
            // Already have a first block in NBT
            long firstPosLong = nbt.getLong("FirstBlockPos");
            String firstType = nbt.getString("FirstBlockType");
            BlockPos firstPos = BlockPos.fromLong(firstPosLong);

            // Are we clicking the same block again?  (pos == firstPos)
            if (pos.equals(firstPos)) {
                player.sendMessage(Text.literal("You clicked the same block again, pairing cancelled.").formatted(Formatting.RED), false);
                resetPair(stack);
                return ActionResult.SUCCESS;
            }

            // We have firstType and second block type => check if they differ
            boolean firstIsCamera = firstType.equals("camera");
            boolean secondIsCamera = isCamera;

            if (firstIsCamera == secondIsCamera) {
                // Both are cameras or both monitors => invalid
                player.sendMessage(
                        Text.literal("Cannot pair two " + (firstIsCamera ? "Cameras" : "Monitors") + " together.")
                                .formatted(Formatting.RED),
                        false
                );
                resetPair(stack);
                return ActionResult.SUCCESS;
            }

            // Valid pairing => camera + monitor
            BlockPos cameraPos = firstIsCamera ? firstPos : pos;
            BlockPos monitorPos = firstIsCamera ? pos : firstPos;

            pairCameraAndMonitor(world, cameraPos, monitorPos, player);

            // Reset the keycard pairing data
            resetPair(stack);
        }

        return ActionResult.SUCCESS;
    }

    private void pairCameraAndMonitor(World world, BlockPos cameraPos, BlockPos monitorPos, ServerPlayerEntity player) {
        BlockEntity be = world.getBlockEntity(monitorPos);
        if (be instanceof CameraMonitorBlockEntity monitorBE) {
            monitorBE.setActive(true); // Turns screen "on"
            monitorBE.markDirty();
        }

        player.sendMessage(Text.literal("Camera at " + cameraPos + " paired to monitor at " + monitorPos).formatted(Formatting.GREEN), false);

        // Update the monitor's "monitorVideo" face to the "active" texture => see section 3 below
    }

    private void unpairMonitor(World world, BlockPos monitorPos, ServerPlayerEntity player) {
        BlockEntity be = world.getBlockEntity(monitorPos);
        if (be instanceof CameraMonitorBlockEntity monitorBE) {
            monitorBE.setActive(false); // Turns screen "off"
            monitorBE.markDirty();
        }
        player.sendMessage(Text.literal("Monitor unpaired!"), false);
    }

    private void resetPair(ItemStack stack) {
        NbtCompound nbt = stack.getOrCreateNbt();
        nbt.remove("InPairingMode");
        nbt.remove("FirstBlockPos");
        nbt.remove("FirstBlockType");
    }
}
