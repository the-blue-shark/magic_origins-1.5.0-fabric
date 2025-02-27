package net.the_blue_shark.power.custom;

import io.github.apace100.apoli.condition.EntityCondition;
import io.github.apace100.apoli.power.PowerConfiguration;
import io.github.apace100.apoli.power.type.PowerType;
import net.the_blue_shark.power.ModPowerTypes;

import java.util.Optional;

public final class ParrotSitPowerType extends PowerType {
    public ParrotSitPowerType(Optional<EntityCondition> condition) {
        super(condition);
    }

    @Override
    public PowerConfiguration<?> getConfig() {
        return ModPowerTypes.SHOULDER_SIT_FOREVER;
    }
}
