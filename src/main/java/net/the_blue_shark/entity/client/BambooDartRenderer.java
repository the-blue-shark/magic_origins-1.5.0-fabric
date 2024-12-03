package net.the_blue_shark.entity.client;

import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.annotation.Nullable;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import net.the_blue_shark.MagicOrigins;
import net.the_blue_shark.block.entity.layer.ModModelLayers;
import net.the_blue_shark.entity.projectile.BambooDartEntity;

public class BambooDartRenderer extends EntityRenderer<BambooDartEntity> {
    public static final Identifier TEXTURE = Identifier.of(MagicOrigins.MOD_ID, "textures/entity/bamboo_dart.png");
    protected BambooDartModel model;

    public BambooDartRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
        model = new BambooDartModel(ctx.getPart(ModModelLayers.BAMBOO_DART));
    }

    @Override
    public void render(BambooDartEntity entity, float yaw, float tickDelta, MatrixStack matrices,
                       VertexConsumerProvider vertexConsumers, int light) {
        matrices.push();
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(MathHelper.lerp(tickDelta, entity.prevYaw, entity.getYaw()) - 90.0F));
        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(MathHelper.lerp(tickDelta, entity.prevPitch, entity.getPitch()) + 90.0F));
        VertexConsumer vertexconsumer = ItemRenderer.getDirectItemGlintConsumer(vertexConsumers, this.model.getLayer(TEXTURE), false, false);
        this.model.render(matrices, vertexconsumer, light, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);

        matrices.pop();
        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
    }

    @Override
    public Identifier getTexture(BambooDartEntity entity) {
        return TEXTURE;
    }
}
