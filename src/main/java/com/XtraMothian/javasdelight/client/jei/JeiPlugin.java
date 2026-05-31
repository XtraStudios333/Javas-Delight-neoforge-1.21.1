package com.XtraMothian.javasdelight.client.jei;

import com.XtraMothian.javasdelight.JavasDelight;
import com.XtraMothian.javasdelight.common.crafting.BucketCoffeeHulling;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;

import java.util.List;
import java.util.stream.Collectors;

@mezz.jei.api.JeiPlugin
public class JeiPlugin implements IModPlugin {

    @Override
    public ResourceLocation getPluginUid() {
        return ResourceLocation.fromNamespaceAndPath(JavasDelight.MOD_ID, "jei_plugin");
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        ClientLevel level = Minecraft.getInstance().level;
        if (level == null) return;

        RecipeManager recipeManager = level.getRecipeManager();

        // FIXED: The stream now explicitly returns a generic CraftingRecipe holder, resolving the type constraint error
        List<RecipeHolder<CraftingRecipe>> coffeeRecipes = recipeManager.getAllRecipesFor(RecipeType.CRAFTING)
                .stream()
                .filter(holder -> holder.value() instanceof BucketCoffeeHulling)
                .map(holder -> new RecipeHolder<CraftingRecipe>(holder.id(), holder.value()))
                .collect(Collectors.toList());

        // Now JEI seamlessly registers the list into the default Crafting Table category
        registration.addRecipes(RecipeTypes.CRAFTING, coffeeRecipes);
    }
}