package dev.ebo2022.still_life_extras.api;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public record WoodTypeData(String modId, ResourceLocation log, ResourceLocation leaves, @Nullable ResourceLocation sapling) {
    public static final Codec<WoodTypeData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.fieldOf("id").forGetter(WoodTypeData::modId),
            ResourceLocation.CODEC.fieldOf("log").forGetter(WoodTypeData::log),
            ResourceLocation.CODEC.fieldOf("leaves").forGetter(WoodTypeData::leaves),
            ResourceLocation.CODEC.optionalFieldOf("sapling", null).forGetter(WoodTypeData::sapling)
    ).apply(instance, WoodTypeData::new));
    public static final Codec<List<WoodTypeData>> LIST_CODEC = CODEC.listOf();


    public static WoodTypeData vanilla(String log, String leaves) {
        return new WoodTypeData("minecraft", new ResourceLocation(log), new ResourceLocation(leaves), null);
    }
}
