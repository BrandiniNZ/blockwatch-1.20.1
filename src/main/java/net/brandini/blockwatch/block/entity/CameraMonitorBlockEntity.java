package net.brandini.blockwatch.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;

public class CameraMonitorBlockEntity extends BlockEntity {

    private int rotation; // existing rotation
    private boolean active; // new field for "paired" / "unpaired" screen

    public CameraMonitorBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.CAMERA_MONITOR_BE, pos, state);
        this.rotation = 0;
        this.active = false; // default unpaired/off
    }

    public int getRotation() {
        return rotation;
    }

    public void setRotation(int rotation) {
        this.rotation = rotation;
        markDirty();
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
        markDirty();
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        this.rotation = nbt.getInt("Rotation");
        this.active = nbt.getBoolean("Active");
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.putInt("Rotation", this.rotation);
        nbt.putBoolean("Active", this.active);
    }
}
