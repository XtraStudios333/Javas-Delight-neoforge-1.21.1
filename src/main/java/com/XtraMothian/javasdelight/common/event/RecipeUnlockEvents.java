package com.XtraMothian.javasdelight.common.event;

import com.XtraMothian.javasdelight.JavasDelight;
import com.XtraMothian.javasdelight.item.ModItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.ItemEntityPickupEvent;

import java.util.Collections;
import java.util.Optional;

@EventBusSubscriber(modid = JavasDelight.MOD_ID)
public class RecipeUnlockEvents {

    @SubscribeEvent
    public static void onItemPickup(ItemEntityPickupEvent.Post event) {
        if (event.getPlayer() instanceof ServerPlayer player) {
            if (event.getCurrentStack().is(ModItems.RAWCOFFEEBEANS.get())) {

                ResourceLocation recipeId = ResourceLocation.fromNamespaceAndPath(JavasDelight.MOD_ID, "green_coffee_beans");

                // Only process the registration if the player hasn't already unlocked this ID
                if (!player.getRecipeBook().contains(recipeId)) {
                    Optional<RecipeHolder<?>> recipe = player.server.getRecipeManager().byKey(recipeId);

                    recipe.ifPresent(recipeHolder -> {
                        // FIXED COMPILER ERROR: Removed the non-existent sendRecipes() method call.
                        // awardRecipes automatically handles network updates and displays the screen toast!
                        player.awardRecipes(Collections.singletonList(recipeHolder));
                    });
                }
            }
        }
    }
}