package dev.ebo2022.still_life_extras.core.forge;

import dev.ebo2022.still_life_extras.core.StillLifeExtrasConfig;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;

import dev.ebo2022.still_life_extras.core.StillLifeExtras;
import net.minecraftforge.fml.config.ModConfig;

@Mod(StillLifeExtras.MOD_ID)
public final class StillLifeExtrasForge {
    public StillLifeExtrasForge() {

        // Run our common setup.
        StillLifeExtras.init();
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, StillLifeExtrasConfig.SPEC);
    }
}
