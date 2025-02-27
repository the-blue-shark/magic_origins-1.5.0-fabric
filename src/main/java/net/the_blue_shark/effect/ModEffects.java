package net.the_blue_shark.effect;

import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.the_blue_shark.MagicOrigins;

public class ModEffects {
    public static final StatusEffect MIMIC_EFFECT = registerStatusEffect("mimic",
            new MimicEffect(StatusEffectCategory.NEUTRAL, 0x5A6C81));


    private static StatusEffect registerStatusEffect(String name, StatusEffect statusEffect) {
        return Registry.register(Registries.STATUS_EFFECT, Identifier.of(MagicOrigins.MOD_ID, name), statusEffect);
    }

    public static void registerModEffects() {
        MagicOrigins.LOGGER.info("Registering Mod Effects for " + MagicOrigins.MOD_ID);
    }
}
