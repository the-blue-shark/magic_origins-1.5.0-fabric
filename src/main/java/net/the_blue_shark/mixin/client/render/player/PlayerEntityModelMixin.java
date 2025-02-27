package net.the_blue_shark.mixin.client.render.player;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import io.github.apace100.apoli.component.PowerHolderComponent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.the_blue_shark.power.custom.HydraHeadPowerType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(PlayerEntityModel.class)
public abstract class PlayerEntityModelMixin<T extends LivingEntity> extends BipedEntityModel<T>{
    public PlayerEntityModelMixin(ModelPart root, boolean thinArms) {
        super(root, RenderLayer::getEntityTranslucent);
    }

    // Modify the model dimensions based on the static THIN_ARMS value
//    @ModifyReturnValue(method = "getTexturedModelData", at = @At("RETURN"))
//    private static ModelData modifyModelData(ModelData originalModelData, Dilation dilation, boolean slim) throws CommandSyntaxException {
//        ModelData modelData = BipedEntityModel.getModelData(dilation, 0.0F);
//        ModelPartData modelPartData = modelData.getRoot();
//
//        // Add custom parts like ear and cloak
//        modelPartData.addChild("ear",
//                ModelPartBuilder.create()
//                        .uv(24, 0)
//                        .cuboid(-3.0F, -6.0F, -1.0F, 6.0F, 6.0F, 1.0F, dilation),
//                ModelTransform.NONE
//        );
//        modelPartData.addChild("cloak",
//                ModelPartBuilder.create()
//                        .uv(0, 0)
//                        .cuboid(-5.0F, 0.0F, -1.0F, 10.0F, 16.0F, 1.0F, dilation, 1.0F, 0.5F),
//                ModelTransform.pivot(0.0F, 0.0F, 0.0F)
//        );
//
//        // Adjust torso to be closer to the ground
//        modelPartData.addChild(EntityModelPartNames.BODY,
//                ModelPartBuilder.create()
//                        .uv(16, 16)
//                        .cuboid(-4.0F, -4.0F, -8.0F, 8.0F, 8.0F, 16.0F, dilation),
//                ModelTransform.pivot(0.0F, 8.0F, 0.0F) // Lower torso closer to the ground
//        );
//
//        // Reposition Head (align to front of torso, lowered)
//        modelPartData.addChild(EntityModelPartNames.HEAD,
//                ModelPartBuilder.create()
//                        .uv(0, 0)
//                        .cuboid(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, dilation),
//                ModelTransform.pivot(0.0F, 4.0F, -10.0F) // Head moved slightly lower and closer to torso front
//        );
//
//        // Adjust front legs (Arms) to be closer to ground level
//        if (slim) {
//            // Left Arm (Front-left leg)
//            modelPartData.addChild(EntityModelPartNames.LEFT_ARM,
//                    ModelPartBuilder.create()
//                            .uv(32, 48)
//                            .cuboid(-1.0F, 0.0F, -2.0F, 3.0F, 12.0F, 4.0F, dilation),
//                    ModelTransform.pivot(4.0F, 10.0F, -6.0F)
//            );
//            modelPartData.addChild("left_sleeve",
//                    ModelPartBuilder.create()
//                            .uv(48, 48)
//                            .cuboid(-1.0F, 0.0F, -2.0F, 3.0F, 12.0F, 4.0F, dilation.add(0.25F)),
//                    ModelTransform.pivot(4.0F, 10.0F, -6.0F)
//            );
//
//            // Right Arm (Front-right leg)
//            modelPartData.addChild(EntityModelPartNames.RIGHT_ARM,
//                    ModelPartBuilder.create()
//                            .uv(40, 16)
//                            .cuboid(-2.0F, 0.0F, -2.0F, 3.0F, 12.0F, 4.0F, dilation),
//                    ModelTransform.pivot(-4.0F, 10.0F, -6.0F)
//            );
//            modelPartData.addChild("right_sleeve",
//                    ModelPartBuilder.create()
//                            .uv(40, 32)
//                            .cuboid(-2.0F, 0.0F, -2.0F, 3.0F, 12.0F, 4.0F, dilation.add(0.25F)),
//                    ModelTransform.pivot(-4.0F, 10.0F, -6.0F)
//            );
//        } else {
//            // Left Arm (Front-left leg)
//            modelPartData.addChild(EntityModelPartNames.LEFT_ARM,
//                    ModelPartBuilder.create()
//                            .uv(32, 48)
//                            .cuboid(-1.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, dilation),
//                    ModelTransform.pivot(4.0F, 10.0F, -6.0F)
//            );
//            modelPartData.addChild("left_sleeve",
//                    ModelPartBuilder.create()
//                            .uv(48, 48)
//                            .cuboid(-1.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, dilation.add(0.25F)),
//                    ModelTransform.pivot(4.0F, 10.0F, -6.0F)
//            );
//
//            // Right Arm (Front-right leg)
//            modelPartData.addChild(EntityModelPartNames.RIGHT_ARM,
//                    ModelPartBuilder.create()
//                            .uv(40, 16)
//                            .cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, dilation),
//                    ModelTransform.pivot(-4.0F, 10.0F, -6.0F)
//            );
//            modelPartData.addChild("right_sleeve",
//                    ModelPartBuilder.create()
//                            .uv(40, 32)
//                            .cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, dilation.add(0.25F)),
//                    ModelTransform.pivot(-4.0F, 10.0F, -6.0F)
//            );
//        }
//
//        // Adjust back legs (moved further back)
//        modelPartData.addChild(EntityModelPartNames.LEFT_LEG,
//                ModelPartBuilder.create()
//                        .uv(16, 48)
//                        .cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, dilation),
//                ModelTransform.pivot(4.0F, 10.0F, 8.0F)
//        );
//        modelPartData.addChild("left_pants",
//                ModelPartBuilder.create()
//                        .uv(0, 48)
//                        .cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, dilation.add(0.25F)),
//                ModelTransform.pivot(4.0F, 10.0F, 8.0F)
//        );
//
//        modelPartData.addChild(EntityModelPartNames.RIGHT_LEG,
//                ModelPartBuilder.create()
//                        .uv(16, 48)
//                        .cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, dilation),
//                ModelTransform.pivot(-4.0F, 10.0F, 8.0F)
//        );
//        modelPartData.addChild("right_pants",
//                ModelPartBuilder.create()
//                        .uv(0, 32)
//                        .cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, dilation.add(0.25F)),
//                ModelTransform.pivot(-4.0F, 10.0F, 8.0F)
//        );
//
//        // Jacket remains on the torso, resized to match the horizontal torso
//        modelPartData.addChild(EntityModelPartNames.JACKET,
//                ModelPartBuilder.create()
//                        .uv(16, 32)
//                        .cuboid(-4.0F, -4.0F, -8.0F, 8.0F, 8.0F, 16.0F, dilation.add(0.25F)),
//                ModelTransform.pivot(0.0F, 8.0F, 0.0F)
//        );
//
//        return modelData;
//    }
}
