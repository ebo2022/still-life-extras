package dev.ebo2022.still_life_extras.api;

import com.google.common.collect.ListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import com.google.common.collect.SetMultimap;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.ebo2022.still_life_extras.core.StillLifeExtras;
import net.minecraft.resources.FileToIdConverter;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.StringRepresentable;
import net.minecraft.util.profiling.ProfilerFiller;

import java.io.Reader;
import java.util.*;

public class StillLifeWoodTypeManager extends SimplePreparableReloadListener<Map<String, List<JsonElement>>> {

    private final Gson gson;
    private final String directory;
    private static final ListMultimap<StillLifeWoodType, WoodTypeData> REGISTRY = Multimaps.newListMultimap(new HashMap<>(), ArrayList::new);

    private StillLifeWoodTypeManager(Gson gson, String directory) {
        this.gson = gson;
        this.directory = directory;
    }

    public static WoodTypeData getData(StillLifeWoodType type) {
        List<WoodTypeData> data = Objects.requireNonNull(REGISTRY.get(type), "No defined blocks for wood type " + type.getSerializedName());

        // shortcut for singleton lists
        if (data.size() == 1) return data.get(0);


    }

    @Override
    protected Map<String, List<JsonElement>> prepare(ResourceManager resourceManager, ProfilerFiller profiler) {
        Map<String, List<JsonElement>> map = new HashMap<>();
        FileToIdConverter converter = FileToIdConverter.json("still_life_wood_types");
        Map<ResourceLocation, List<Resource>> resources = converter.listMatchingResourceStacks(resourceManager);
        if (resources.isEmpty()) return Collections.emptyMap();
        for (Map.Entry<ResourceLocation, List<Resource>> resourceEntry : resources.entrySet()) {
            ResourceLocation rawId = resourceEntry.getKey();
            ResourceLocation idToUse = converter.fileToId(rawId);
            List<JsonElement> list = map.computeIfAbsent(idToUse.getPath(), __ -> new ArrayList<>());
            for (Resource resource : resourceEntry.getValue()) {
                try {
                    Reader reader = resource.openAsReader();
                    try {
                        JsonElement json = GsonHelper.fromJson(this.gson, reader, JsonElement.class);
                        if (json.isJsonObject())
                            list.add(json);
                    } catch (Throwable throwable) {
                        if (reader != null) {
                            try {
                                reader.close();
                            } catch (Throwable throwable2) {
                                throwable.addSuppressed(throwable2);
                            }
                        }
                        throw throwable;
                    }
                } catch (Exception e) {
                    StillLifeExtras.LOGGER.error("Couldn't parse data file {} from {}", idToUse, rawId, e);
                }
            }
        }
        return map;
    }

    @Override
    protected void apply(Map<String, List<JsonElement>> map, ResourceManager resourceManager, ProfilerFiller profilerFiller) {
        REGISTRY.clear();
        map.forEach((key, value) -> {
            StillLifeWoodType type = StillLifeWoodType.CODEC.byName(key);
            value.forEach(json -> {
                List<WoodTypeData> data = WoodTypeData.LIST_CODEC.parse(JsonOps.INSTANCE, json).getOrThrow(false, StillLifeExtras.LOGGER::error);
                data.forEach(datum -> REGISTRY.put(type, datum));
            });
        });
    }

    public enum BlockType implements StringRepresentable {
        LOG("log"),
        LEAVES("leaves");

        private final String name;
        static final Codec<BlockType> CODEC = StringRepresentable.fromEnum(BlockType::values);

        BlockType(String name) {
            this.name = name;
        }

        @Override
        public String getSerializedName() {
            return this.name;
        }
    }
}
