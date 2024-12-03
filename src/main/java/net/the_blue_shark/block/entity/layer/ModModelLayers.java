package net.the_blue_shark.block.entity.layer;

import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;
import net.the_blue_shark.MagicOrigins;

public class ModModelLayers {

    public static final EntityModelLayer BAMBOO_DART =
            new EntityModelLayer(Identifier.of(MagicOrigins.MOD_ID, "bamboo_dart"), "main");
}
