package com.XtraMothian.javasdelight.common.crafting;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.Tags;

import com.XtraMothian.javasdelight.item.ModItems;
import com.XtraMothian.javasdelight.common.registry.ModRecipeSerializers;

public class BucketCoffeeHulling implements CraftingRecipe {

    private final CraftingBookCategory category;

    // MANDATORY CODECS FOR CLIENT/SERVER SYNCHRONIZATION
    public static final MapCodec<BucketCoffeeHulling> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(
                    CraftingBookCategory.CODEC.fieldOf("category").orElse(CraftingBookCategory.MISC).forGetter(BucketCoffeeHulling::category)
            ).apply(instance, BucketCoffeeHulling::new)
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, BucketCoffeeHulling> STREAM_CODEC = StreamCodec.of(
            (buf, recipe) -> CraftingBookCategory.STREAM_CODEC.encode(buf, recipe.category()),
            buf -> new BucketCoffeeHulling(CraftingBookCategory.STREAM_CODEC.decode(buf))
    );

    public BucketCoffeeHulling(CraftingBookCategory category) {
        this.category = category;
    }

    @Override
    public boolean matches(CraftingInput container, Level level) {
        int occupiedBeanSlots = 0;
        boolean hasWaterBucket = false;

        for (int index = 0; index < container.size(); ++index) {
            ItemStack selectedStack = container.getItem(index);
            if (!selectedStack.isEmpty()) {
                if (selectedStack.is(ModItems.RAWCOFFEEBEANS.get())) {
                    // EXPLOIT FIX: Tracks individual occupied grid spaces.
                    // This blocks crafting if multiple beans are stacked in a single slot.
                    occupiedBeanSlots++;
                } else if (selectedStack.is(Tags.Items.BUCKETS_WATER)) {
                    if (hasWaterBucket) return false;
                    hasWaterBucket = true;
                } else {
                    return false;
                }
            }
        }
        // Validates only when exactly 8 slots hold beans and 1 slot holds the water bucket
        return occupiedBeanSlots == 8 && hasWaterBucket;
    }

    @Override
    public ItemStack assemble(CraftingInput container, HolderLookup.Provider registryAccess) {
        return new ItemStack(ModItems.GREENCOFFEEBEANS.get(), 8);
    }

    @Override
    public NonNullList<ItemStack> getRemainingItems(CraftingInput container) {
        NonNullList<ItemStack> remainders = NonNullList.withSize(container.size(), ItemStack.EMPTY);

        for (int index = 0; index < remainders.size(); ++index) {
            ItemStack selectedStack = container.getItem(index);
            if (!selectedStack.isEmpty()) {
                if (selectedStack.is(Tags.Items.BUCKETS_WATER)) {
                    // Leaves behind the clean empty bucket in its exact slot grid coordinate
                    remainders.set(index, new ItemStack(Items.BUCKET));
                }
            }
        }
        return remainders;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        // Enforces that the recipe requires a full 3x3 layout to spread across 9 total slots
        return width >= 3 && height >= 3;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipeSerializers.BUCKET_COFFEE_HULLING.get();
    }

    @Override
    public CraftingBookCategory category() {
        return this.category;
    }

    // --- VISUAL RENDERING INFORMATION FOR JEI & CRAFTING BOOK ---

    @Override
    public ItemStack getToastSymbol() {
        return new ItemStack(ModItems.GREENCOFFEEBEANS.get());
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> displayIngredients = NonNullList.create();
        for (int i = 0; i < 8; i++) {
            displayIngredients.add(Ingredient.of(ModItems.RAWCOFFEEBEANS.get()));
        }
        displayIngredients.add(Ingredient.of(Items.WATER_BUCKET));
        return displayIngredients;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider registryAccess) {
        return new ItemStack(ModItems.GREENCOFFEEBEANS.get(), 8);
    }

    @Override
    public boolean showNotification() {
        // TOAST FIX: Forces the game engine to drop constraints and allow the toast to pop up
        return true;
    }
}