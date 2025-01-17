package net.brandini.blockwatch.block.custom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class CameraMonitor extends Block {
    public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;
    public static final IntProperty VARIANT = IntProperty.of("variant", 0, 2); // 0: default, 1: left, 2: right

    private static final VoxelShape SHAPE = Block.createCuboidShape(4.0, 0.0, 4.0, 12.0, 8.0, 12.0);

    public CameraMonitor(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState()
                .with(FACING, Direction.NORTH)
                .with(VARIANT, 0));
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state,
                         LivingEntity placer, ItemStack itemStack) {
        super.onPlaced(world, pos, state, placer, itemStack);

        if (!world.isClient) {
            // Set facing direction to match the player's facing direction
            Direction facing = placer.getHorizontalFacing().getOpposite();
            state = state.with(FACING, facing);

            // Check neighbors for another CameraMonitor block
            BlockPos leftPos = pos.offset(facing.rotateYClockwise()); // Switched: was Counterclockwise
            BlockPos rightPos = pos.offset(facing.rotateYCounterclockwise()); // Switched: was Clockwise

            boolean isLeftNeighbor = world.getBlockState(leftPos).getBlock() instanceof CameraMonitor;
            boolean isRightNeighbor = world.getBlockState(rightPos).getBlock() instanceof CameraMonitor;

            // Set variant based on swapped neighbors
            if (isRightNeighbor) { // Was: isLeftNeighbor
                state = state.with(VARIANT, 1); // Left variant
            } else if (isLeftNeighbor) { // Was: isRightNeighbor
                state = state.with(VARIANT, 2); // Right variant
            } else {
                state = state.with(VARIANT, 0); // Default variant
            }

            // Apply the updated state
            world.setBlockState(pos, state, Block.NOTIFY_ALL);

            // Schedule a tick to check for support
            world.scheduleBlockTick(pos, this, 1);
        }
    }

    public void scheduledTick(BlockState state, World world, BlockPos pos, net.minecraft.util.math.random.Random random) {
        super.scheduledTick(state, (ServerWorld) world, pos, random);

        // Ensure we are working on the server side
        if (!world.isClient) {
            ServerWorld serverWorld = (ServerWorld) world;// Check if there's a block underneath
            BlockPos belowPos = pos.down();
            if (world.getBlockState(belowPos).isAir()) {
                // Break the block and drop it
                serverWorld.breakBlock(pos, true);
            } else {
                // Schedule the next tick to continuously check for support
                serverWorld.scheduleBlockTick(pos, this, 20); // Check again in 20 ticks (1 second)
            }
        }
    }


    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, VARIANT);
    }
}
