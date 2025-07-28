package dev.ebo2022.still_life_extras.core.fabric;

import net.fabricmc.api.ModInitializer;

import dev.ebo2022.still_life_extras.core.StillLifeExtras;

public final class StillLifeExtrasFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.

        // Run our common setup.
        StillLifeExtras.init();
    }
}
