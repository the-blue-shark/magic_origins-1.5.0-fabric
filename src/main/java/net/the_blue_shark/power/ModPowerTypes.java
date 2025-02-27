package net.the_blue_shark.power;

import io.github.apace100.apoli.power.PowerConfiguration;
import io.github.apace100.apoli.power.type.PowerType;
import io.github.apace100.apoli.registry.ApoliRegistries;
import io.github.apace100.apoli.util.HudRender;
import io.github.apace100.origins.Origins;
import net.minecraft.registry.Registry;
import net.the_blue_shark.MagicOrigins;
import net.the_blue_shark.client.render.custom.HydraHeadFeatureRenderer;
import net.the_blue_shark.power.custom.BambooSniperPowerType;
import net.the_blue_shark.power.custom.HydraHeadPowerType;
import net.the_blue_shark.power.custom.ParrotSitPowerType;

import java.util.Optional;

public class ModPowerTypes {
    public static final PowerConfiguration<BambooSniperPowerType> BAMBOO_SNIPER = register(
            PowerConfiguration.conditionedSimple(MagicOrigins.identifier("bamboo_sniper"), BambooSniperPowerType::new));
    public static final PowerConfiguration<ParrotSitPowerType> SHOULDER_SIT_FOREVER = register(
            PowerConfiguration.conditionedSimple(MagicOrigins.identifier("shoulder_sit_forever"), ParrotSitPowerType::new));

    public static final PowerConfiguration<HydraHeadPowerType> HYDRA_HEADS = register(
            PowerConfiguration.simple(MagicOrigins.identifier("hydra_heads"), HydraHeadPowerType::new));

    public static void register() {

    }
    public static <T extends PowerType> PowerConfiguration<T> register(PowerConfiguration<T> configuration) {

        PowerConfiguration<PowerType> casted = (PowerConfiguration<PowerType>) configuration;
        Registry.register(ApoliRegistries.POWER_TYPE, casted.id(), casted);
        return configuration;

    }
}
