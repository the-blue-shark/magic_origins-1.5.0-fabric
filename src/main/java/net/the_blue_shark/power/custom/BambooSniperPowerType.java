package net.the_blue_shark.power.custom;

import io.github.apace100.apoli.power.PowerConfiguration;
import io.github.apace100.apoli.power.type.PowerType;
import io.github.apace100.apoli.power.type.PowerTypes;
import org.jetbrains.annotations.NotNull;

public final class BambooSniperPowerType extends PowerType {
    public BambooSniperPowerType() {
        return;
    }

    @Override
    public @NotNull PowerConfiguration<?> getConfig() {
        return PowerTypes.DUMMY;
    }
}
