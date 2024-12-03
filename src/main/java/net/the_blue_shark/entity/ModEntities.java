package net.the_blue_shark.entity;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.the_blue_shark.MagicOrigins;
import net.the_blue_shark.entity.projectile.BambooDartEntity;

public class ModEntities {
    public static final EntityType<BambooDartEntity> BAMBOO_DART = Registry.register(Registries.ENTITY_TYPE,
            Identifier.of(MagicOrigins.MOD_ID, "bamboo_dart"),
            FabricEntityTypeBuilder.<BambooDartEntity>create(SpawnGroup.MISC, BambooDartEntity::new)
                    .dimensions(EntityDimensions.fixed(0.2f, 0.2f)).build());
    public static void registerModEntities() {
        MagicOrigins.LOGGER.info("Registering Mod Entities for " + MagicOrigins.MOD_ID);
    }
}