package com.XtraMothian.javasdelight.common.registry;

import com.XtraMothian.javasdelight.common.crafting.BucketCoffeeHulling;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModRecipeSerializers {

    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS =
            DeferredRegister.create(BuiltInRegistries.RECIPE_SERIALIZER, "javasdelight");

    // FIXED: Instead of SimpleCraftingRecipeSerializer, we instantiate a direct, standard RecipeSerializer
    // that safely maps your custom class codecs over the network.
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<BucketCoffeeHulling>> BUCKET_COFFEE_HULLING =
            RECIPE_SERIALIZERS.register("bucket_coffee_hulling", () -> new RecipeSerializer<BucketCoffeeHulling>() {
                @Override
                public MapCodec<BucketCoffeeHulling> codec() {
                    return BucketCoffeeHulling.CODEC;
                }

                @Override
                public StreamCodec<RegistryFriendlyByteBuf, BucketCoffeeHulling> streamCodec() {
                    return BucketCoffeeHulling.STREAM_CODEC;
                }
            });

    public static void register(IEventBus eventBus) {
        RECIPE_SERIALIZERS.register(eventBus);
    }
}