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
    public static final StatusEffect MIMIC = registerStatusEffect("mimic",
            new MimicEffect(StatusEffectCategory.NEUTRAL, 0x36ebab)
                    .addAttributeModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED,
                            "7107DE5E-7CE8-4030-940E-514C1F160890", -0.25f,
                            EntityAttributeModifier.Operation.MULTIPLY_TOTAL));


    private static StatusEffect registerStatusEffect(String name, StatusEffect statusEffect) {
        return Registry.register(Registries.STATUS_EFFECT, new Identifier(MagicOrigins.MOD_ID, name), statusEffect);
    }

    public static void registerEffects() {
        MagicOrigins.LOGGER.info("Registering Mod Effects for " + MagicOrigins.MOD_ID);
    }
}
