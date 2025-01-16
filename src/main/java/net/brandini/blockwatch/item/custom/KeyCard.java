package net.brandini.blockwatch.item.custom;

import net.brandini.blockwatch.BlockWatch;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Optional;

public class KeyCard extends Item {

    public KeyCard(Settings settings) {
        super(settings);
    }
}
