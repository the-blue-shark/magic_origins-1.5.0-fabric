package net.the_blue_shark.mixin;

import io.github.apace100.apoli.component.PowerHolderComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.the_blue_shark.power.custom.ParrotSitPowerType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {

    @Inject(method = "dropShoulderEntities", at = @At("HEAD"), cancellable = true)
    private void preventDroppingShoulderEntities(CallbackInfo ci) {
        PlayerEntity player = (PlayerEntity) (Object) this;
        if (PowerHolderComponent.hasPowerType(player, ParrotSitPowerType.class)) {
            ci.cancel();
        }
    }
    @Inject(method = "dropShoulderEntity", at = @At("HEAD"), cancellable = true)
    private void preventDroppingShoulderEntity(CallbackInfo ci) {
        PlayerEntity player = (PlayerEntity) (Object) this;
        if (PowerHolderComponent.hasPowerType(player, ParrotSitPowerType.class)) {
            ci.cancel();
        }
    }

}
