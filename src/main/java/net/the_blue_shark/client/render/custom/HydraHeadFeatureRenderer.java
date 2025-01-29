package net.the_blue_shark.client.render.custom;

import io.github.apace100.apoli.component.PowerHolderComponent;
import net.minecraft.client.model.*;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.the_blue_shark.power.custom.HydraHeadPowerType;

public class HydraHeadFeatureRenderer extends FeatureRenderer<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> {
    public HydraHeadFeatureRenderer(PlayerEntityRenderer playerRenderer) {
        super(playerRenderer);
    }

    public void render(MatrixStack matrices,Dilation dilation, VertexConsumerProvider vertexConsumers, int light, AbstractClientPlayerEntity player, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        ModelData modelData = BipedEntityModel.getModelData(dilation, 0.0F);
        ModelPartData modelPartData = modelData.getRoot();
        if (PowerHolderComponent.hasPowerType(player, HydraHeadPowerType.class)) {
            double head_gap = 1.2;
            modelPartData.addChild("head", ModelPartBuilder.create()
                            .uv(0, 0).cuboid((float) (-8 - head_gap / 2), -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new Dilation(0.0F))
                            .uv(0, 0).cuboid((float) (head_gap / 2), -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new Dilation(0.0F)),
                    ModelTransform.pivot(0.0F, 1.0F, 0.0F));

            modelPartData.addChild(EntityModelPartNames.HAT, ModelPartBuilder.create()
                            .uv(32, 0).cuboid((float) (-8 - head_gap / 2), -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, dilation.add(0.5F))
                            .uv(32, 0).cuboid((float) (head_gap / 2), -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, dilation.add(0.5F)),
                    ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        }
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, AbstractClientPlayerEntity entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
    }
}
