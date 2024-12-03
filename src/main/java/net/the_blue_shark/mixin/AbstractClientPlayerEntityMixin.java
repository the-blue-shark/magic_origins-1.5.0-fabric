package net.the_blue_shark.mixin;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.option.Perspective;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.the_blue_shark.item.ModItems;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(AbstractClientPlayerEntity.class)
public class AbstractClientPlayerEntityMixin extends PlayerEntity {
    public AbstractClientPlayerEntityMixin(World world, BlockPos pos, float yaw, GameProfile gameProfile) {
        super(world, pos, yaw, gameProfile);
    }

    @Override
    public boolean isSpectator() {
        return false;
    }

    @Override
    public boolean isCreative() {
        return false;
    }

    @Inject(method = "getFovMultiplier", at = @At(value = "TAIL"), locals = LocalCapture.CAPTURE_FAILSOFT, cancellable = true)
    private void getFovMultiplierMixin(CallbackInfoReturnable<Float> info, float f) {
        Perspective perspective = MinecraftClient.getInstance().options.getPerspective();
        Item item = this.getActiveItem().getItem();
        ItemStack itemStack = this.getActiveItem();
            if (this.isUsingItem() && itemStack.isOf(ModItems.BAMBOO_SNIPER) && perspective == Perspective.FIRST_PERSON) {
                int i = this.getItemUseTime();
                float g = (float) i / 20.0f;
                g = g > 1.0f ? 1.0f : g * g;
                f *= 1.0f - g * 1.5f;
                info.setReturnValue(MathHelper.lerp(MinecraftClient.getInstance().options.getFovEffectScale().getValue().floatValue(), 1.0f, f));
            }

    }
}
