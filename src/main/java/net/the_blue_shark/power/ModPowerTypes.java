package net.the_blue_shark.power;

import io.github.apace100.apoli.power.PowerConfiguration;
import io.github.apace100.apoli.power.type.PowerType;
import io.github.apace100.apoli.registry.ApoliRegistries;
import io.github.apace100.origins.Origins;
import net.minecraft.registry.Registry;
import net.the_blue_shark.power.custom.BambooSniperPowerType;

public class ModPowerTypes {
    public static final PowerConfiguration<BambooSniperPowerType> BAMBOO_SNIPER = register(PowerConfiguration.simple(Origins.identifier("bamboo_sniper"), BambooSniperPowerType::new));

    public static void register() {

    }

    public static <T extends PowerType> PowerConfiguration<T> register(PowerConfiguration<T> config) {

        //noinspection unchecked
        PowerConfiguration<PowerType> casted = (PowerConfiguration<PowerType>) config;
        Registry.register(ApoliRegistries.POWER_TYPE, casted.id(), casted);

        return config;

    }
}
