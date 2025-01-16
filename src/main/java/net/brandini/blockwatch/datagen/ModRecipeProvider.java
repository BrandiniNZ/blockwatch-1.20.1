package net.brandini.blockwatch.datagen;


import net.brandini.blockwatch.block.ModBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.RecipeProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;

import java.util.function.Consumer;

public class ModRecipeProvider extends FabricRecipeProvider {

    public ModRecipeProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generate(Consumer<RecipeJsonProvider> consumer) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModBlocks.CAMERA_MONITOR_BLOCK, 3)
                .pattern("GGG")
                .pattern("GGG")
                .pattern(" X ")
                .input('G', Items.TINTED_GLASS)
                .input('X', Items.IRON_INGOT)
                .criterion("has_tinted_glass", RecipeProvider.conditionsFromItem(Items.TINTED_GLASS))
                .criterion("has_iron_ingot", RecipeProvider.conditionsFromItem(Items.IRON_INGOT))
                .offerTo(consumer);
    }

}