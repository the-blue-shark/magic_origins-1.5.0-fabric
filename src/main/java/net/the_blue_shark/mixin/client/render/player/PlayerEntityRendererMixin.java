package net.the_blue_shark.mixin.client.render.player;

import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import io.github.apace100.apoli.component.PowerHolderComponent;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.model.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.util.Identifier;
import net.the_blue_shark.client.render.custom.HydraHeadFeatureRenderer;
import net.the_blue_shark.power.custom.HydraHeadPowerType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntityRenderer.class)
public class PlayerEntityRendererMixin {

    @Inject(method = "render", at = @At("HEAD"))
    private void adjustPlayerModelVisibility(AbstractClientPlayerEntity player, float f, float g, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, CallbackInfo ci) {
        // Get the player model from the renderer
        PlayerEntityModel<AbstractClientPlayerEntity> model = ((PlayerEntityRenderer) (Object) this).getModel();

        // Ensure the player entity is valid and check the power condition
        if (PowerHolderComponent.hasPowerType(player, HydraHeadPowerType.class)) {
            model.head.visible = false;  // Hide the vanilla head
        }
    }
    /*
    @Inject(method = "render", at = @At("HEAD"))
    private void adjustPlayerModelPosition(AbstractClientPlayerEntity player, float f, float g, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, CallbackInfo ci) {
        // Apply a translation of -1.0F along the Y-axis to move the entire player model down
        matrices.translate(0.0F, -0.05F, 0.0F);
    }

    @Inject(method = "renderArm", at = @At("HEAD"), cancellable = true)
    private void modifyRenderArm(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, AbstractClientPlayerEntity player, ModelPart arm, ModelPart sleeve, CallbackInfo ci) {
        arm.roll = 0.0F;
        arm.yaw = 0.0F;
        arm.pitch = 0.0F;
        arm.render(matrices, vertexConsumers.getBuffer(RenderLayer.getEntitySolid(Identifier.ofVanilla("textures/entity/armorstand/wood.png"))), light, OverlayTexture.DEFAULT_UV);
        ci.cancel();
    } */
//    @Inject(method = "getTexture", at = @At("HEAD"), cancellable = true)
//    private void modifyTexture(AbstractClientPlayerEntity abstractClientPlayerEntity, CallbackInfoReturnable<Identifier> cir) {
//        // Check if you want to apply your custom texture
//        // Return a custom texture (you can replace with your own Identifier)
//        Identifier customTexture = Identifier.ofVanilla("textures/entity/pig/pig.png");
//        cir.setReturnValue(customTexture); // Set custom texture
//    }
}