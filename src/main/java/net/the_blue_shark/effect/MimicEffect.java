package net.the_blue_shark.effect;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MimicEffect extends StatusEffect {
    protected MimicEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }
    private void sendActionBarMessage(PlayerEntity player, String messageKey) {
        Text message = Text.translatable(messageKey);
        player.sendMessage(message, true);
    }
    @Override
        public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        super.applyUpdateEffect(entity, amplifier);

        if (entity instanceof PlayerEntity player) {
            ItemStack mainHandItem = player.getMainHandStack();

            // Check if the item in the main hand is a block item
            if (mainHandItem.getItem() instanceof BlockItem blockItem) {
                Block block = blockItem.getBlock();

                // Display the block to the player
                displayBlock(player, block);
            } else {
                // Display a message that the item in the main hand is not possible to morph into
                sendActionBarMessage(player, "message.mimic.impossible");
            }
        }
    }
    public void onRemoved(AttributeContainer attributeContainer, PlayerEntity player) {
        player.removeStatusEffect(StatusEffects.INVISIBILITY);
        spawnGreenParticles(player);
    }
    private void displayBlock(PlayerEntity player, Block block) {
        // Make the player invisible
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.INVISIBILITY, Integer.MAX_VALUE, 0, false, false, true));


        BlockState state = block.getDefaultState();
        BlockPos pos = player.getBlockPos().offset(player.getHorizontalFacing());


    }

    private void spawnGreenParticles(PlayerEntity player) {
        World world = player.getWorld();
        BlockPos pos = player.getBlockPos();

        for (int i = 0; i < 20; i++) {
            double offsetX = (world.random.nextFloat() - 0.5) * 2.0;
            double offsetY = (world.random.nextFloat() - 0.5) * 2.0;
            double offsetZ = (world.random.nextFloat() - 0.5) * 2.0;
            world.addParticle(ParticleTypes.HAPPY_VILLAGER,
                    pos.getX() + offsetX,
                    pos.getY() + offsetY,
                    pos.getZ() + offsetZ,
                    0.0, 0.0, 0.0);
        }
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }
}

