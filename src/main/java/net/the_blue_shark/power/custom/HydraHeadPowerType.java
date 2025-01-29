package net.the_blue_shark.power.custom;

import io.github.apace100.apoli.action.EntityAction;
import io.github.apace100.apoli.condition.EntityCondition;
import io.github.apace100.apoli.data.TypedDataObjectFactory;
import io.github.apace100.apoli.power.PowerConfiguration;
import io.github.apace100.apoli.power.type.HudRenderedVariableIntPowerType;
import io.github.apace100.apoli.power.type.PowerType;
import io.github.apace100.apoli.power.type.PowerTypes;
import io.github.apace100.apoli.power.type.ResourcePowerType;
import io.github.apace100.apoli.util.HudRender;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityFeatureRendererRegistrationCallback;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.entity.EntityType;
import net.the_blue_shark.power.ModPowerTypes;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class HydraHeadPowerType extends PowerType {



    @Override
    public PowerConfiguration<?> getConfig() {
        return ModPowerTypes.HYDRA_HEADS;
    }
}
