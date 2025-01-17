package net.brandini.blockwatch.block.custom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

public class DomeCamera extends Block {

    // A DirectionProperty allowing UP, DOWN, NORTH, SOUTH, EAST, WEST
    public static final DirectionProperty FACING = DirectionProperty.of(
            "facing",
            direction -> true // all 6 directions are valid
    );

    private static final VoxelShape SHAPE = Block.createCuboidShape(
            4.0, 0.0, 4.0, 12.0, 8.0, 12.0
    );

    public DomeCamera(Settings settings) {
        super(settings);
        // Default to UP, for example
        this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.UP));
    }

    /**
     * Called when the block is placed. We use the side the player clicked on
     * to decide orientation.
     */
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        Direction sideClicked = ctx.getSide(); // The face the player clicked
        Direction facing;

        if (sideClicked == Direction.UP) {
            // Ceiling placement: bottom of the block faces the ceiling
            facing = Direction.UP;
        } else if (sideClicked == Direction.DOWN) {
            // Floor placement: top of the block faces upward
            facing = Direction.DOWN;
        } else {
            // Wall placement: face opposite the side clicked
            facing = sideClicked.getOpposite();
        }

        return this.getDefaultState().with(FACING, facing);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        // Register the FACING property
        builder.add(FACING);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        Direction facing = state.get(FACING);

        switch (facing) {
            case DOWN: // Ceiling placement
                return Block.createCuboidShape(4.0, 8.0, 4.0, 12.0, 16.0, 12.0); // Adjust to match ceiling position
            case UP: // Floor placement
                return Block.createCuboidShape(4.0, 0.0, 4.0, 12.0, 8.0, 12.0); // Adjust to match floor position
            default: // Wall placement
                return Block.createCuboidShape(4.0, 4.0, 0.0, 12.0, 12.0, 8.0); // Default for walls
        }
    }

}
