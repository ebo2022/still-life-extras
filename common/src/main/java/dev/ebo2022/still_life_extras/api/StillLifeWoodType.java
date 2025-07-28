package dev.ebo2022.still_life_extras.api;

import com.mojang.serialization.Codec;
import dev.ebo2022.still_life_extras.core.StillLifeExtras;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import org.jetbrains.annotations.Nullable;

public enum StillLifeWoodType implements StringRepresentable {
    BAOBAB("baobab", WoodTypeData.vanilla("stripped_jungle_log", "birch_leaves"), true, true),
    BEECH("beech", WoodTypeData.vanilla("stripped_dark_oak_log", "dark_oak_leaves"), true, true),
    BLACK_PINE("black_pine", WoodTypeData.vanilla("mangrove_log", "spruce_leaves"), false, false),
    CEDAR("cedar", WoodTypeData.vanilla("oak_log", "birch_leaves"), true, false),
    CYPRESS("cypress", WoodTypeData.vanilla("stripped_spruce_log", "spruce_leaves"), true, false),
    DOUGLAS_FIR("douglas_fir", WoodTypeData.vanilla("spruce_log", "spruce_leaves"), false, false),
    FIR("fir", WoodTypeData.vanilla("stripped_dark_oak_log", "mangrove_leaves"), true, false),
    KAPOK("kapok", WoodTypeData.vanilla("jungle_log", "jungle_leaves"), true, "kapok_small", "kapok"),
    LARCH("larch", WoodTypeData.vanilla("dark_oak_log", "acacia_leaves"), true, false),
    LINDEN("linden", WoodTypeData.vanilla("stripped_oak_log", "azalea_leaves"), true, false),
    MAHOGANY("mahogany", WoodTypeData.vanilla("jungle_log", "jungle_leaves"), true, true),
    MARULA("marula", WoodTypeData.vanilla("mangrove_log", "azalea_leaves"), true, false),
    MOPANE("mopane", WoodTypeData.vanilla("oak_log", "spruce_leaves"), true, false),
    OLIVE("olive", WoodTypeData.vanilla("acacia_wood", "birch_leaves"), true, true),
    PALM("palm", WoodTypeData.vanilla("oak_wood", "azalea_leaves"), true, "palm_trunk", null),
    PINE("pine", WoodTypeData.vanilla("stripped_spruce_log", "spruce_leaves"), true, false),
    RED_MANGROVE("red_mangrove", WoodTypeData.vanilla("mangrove_log", "birch_leaves"), true, false),
    TEAK("teak", WoodTypeData.vanilla("oak_log", "mangrove_leaves"), true, false),
    WHITE_MANGROVE("white_mangrove", WoodTypeData.vanilla("stripped_spruce_log", "mangrove_leaves"), true, false),
    WILLOW("willow", WoodTypeData.vanilla("dark_oak_log", "mangrove_leaves"), true, false);

    private final String name;
    private final WoodTypeData defaultType;
    private final ResourceKey<ConfiguredFeature<?, ?>> feature;
    @Nullable
    private final ResourceKey<ConfiguredFeature<?, ?>> largeFeature;
    // used to distinguish between main tree types & variants
    private final boolean primary;
    public static final StringRepresentable.EnumCodec<StillLifeWoodType> CODEC = StringRepresentable.fromEnum(StillLifeWoodType::values);

    StillLifeWoodType(String name, WoodTypeData defaultType, boolean primary, boolean hasLargeVariant) {
        this.name = name;
        this.defaultType = defaultType;
        this.primary = primary;
        this.feature = ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(StillLifeExtras.STILL_LIFE_ID, name));
        this.largeFeature = hasLargeVariant ? ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(StillLifeExtras.STILL_LIFE_ID, name + "_large")) : null;
    }

    StillLifeWoodType(String name, WoodTypeData defaultType, boolean primary, String feature, String largeFeature) {
        this.name = name;
        this.defaultType = defaultType;
        this.primary = primary;
        this.feature = ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(StillLifeExtras.STILL_LIFE_ID, feature));
        this.largeFeature = ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(StillLifeExtras.STILL_LIFE_ID, largeFeature));
    }

    @Override
    public String getSerializedName() {
        return this.name;
    }

    public boolean isPrimary() {
        return this.primary;
    }

    public ResourceKey<ConfiguredFeature<?, ?>> getFeature() {
        return this.feature;
    }

    @Nullable
    public ResourceKey<ConfiguredFeature<?, ?>> getLargeFeature() {
        return this.largeFeature;
    }
}
