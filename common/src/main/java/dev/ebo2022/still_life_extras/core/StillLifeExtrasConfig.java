package dev.ebo2022.still_life_extras.core;

import net.minecraftforge.common.ForgeConfigSpec;

import java.util.Collections;
import java.util.List;

public class StillLifeExtrasConfig {

    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    private static final ForgeConfigSpec.BooleanValue SAPLING_OVERRIDES = BUILDER
            .comment("Whether modded saplings should use Still Life's tree shapes when applicable.")
            .define("sapling_overrides", true);
    private static final ForgeConfigSpec.ConfigValue<List<? extends String>> MOD_PRIORITY = BUILDER
            .comment("If more than 1 compatible mod is installed AND both of them add an identical feature (e.g. BOP, BWG both have willow wood), this list will determine which mod's blocks are used.",
                    "Mods closer to the start of the list have a HIGHER priority.")
            .defineListAllowEmpty(Collections.singletonList("mod_priority"), Collections::emptyList, object -> true);
    public static final ForgeConfigSpec SPEC = BUILDER.build();
}
