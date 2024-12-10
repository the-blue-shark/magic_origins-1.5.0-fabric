package net.the_blue_shark.item.custom;

import com.mojang.authlib.GameProfile;
import io.github.apace100.apoli.component.PowerHolderComponent;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.annotation.Nullable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.s2c.play.TitleS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;
import net.the_blue_shark.entity.projectile.BambooDartEntity;
import net.the_blue_shark.item.ModItems;
import net.the_blue_shark.power.custom.BambooSniperPowerType;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Predicate;

import static io.github.apace100.apoli.Apoli.server;

public class SniperItem extends BowItem {
    public void sendFailMessage(PlayerEntity playerEntity, String message) {
        Text text = Text
                .empty()
                .append(Text.literal("<").styled(style -> style.withObfuscated(false)))
                .append(Text.translatable("bamboo_sniper.mr_panda").styled(style -> style.withObfuscated(true).withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, Text.translatable("bamboo_sniper.mr_panda")))))
                .append(Text.literal("> ").styled(style -> style.withObfuscated(false)))
                .append(Text.translatable(message));

        if (playerEntity instanceof ServerPlayerEntity serverPlayer) {
            serverPlayer.sendMessage(text, false);
        }
    }

    public SniperItem(Settings settings) {
        super(settings);
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        if (user instanceof PlayerEntity playerEntity) {
            ItemStack itemStack = playerEntity.getProjectileType(stack);
            if (!itemStack.isEmpty()) {
                int i = this.getMaxUseTime(stack, user) - remainingUseTicks;
                float f = getPullProgress(i);

                if (f >= 1.0F) {

                    if (PowerHolderComponent.hasPowerType(playerEntity, BambooSniperPowerType.class)){

                        List<ItemStack> list = load(stack, itemStack, playerEntity);
                        if (world instanceof ServerWorld serverWorld && !list.isEmpty()) {
                            this.shootAll(serverWorld, playerEntity, playerEntity.getActiveHand(), stack, list, 4.0F, 1.0F, true, null);
                        }

                        world.playSound(
                                null,
                                playerEntity.getX(),
                                playerEntity.getY(),
                                playerEntity.getZ(),
                                SoundEvents.ENTITY_WITHER_SHOOT,
                                SoundCategory.PLAYERS,
                                1.0F,
                                2
                        );
                        playerEntity.incrementStat(Stats.USED.getOrCreateStat(this));
                    } else {
                        int random = ThreadLocalRandom.current().nextInt(0, 10);
                        if (random == 3) {
                            sendFailMessage(playerEntity, "bamboo_sniper.fail.three");
                        }
                        if (random == 2) {
                            sendFailMessage(playerEntity, "bamboo_sniper.fail.two");
                        }
                        if (random == 1) {
                            sendFailMessage(playerEntity, "bamboo_sniper.fail.one");
                        }
                    }
                }

            }
        }
    }
    @Override
    protected ProjectileEntity createArrowEntity(World world, LivingEntity shooter, ItemStack weaponStack, ItemStack projectileStack, boolean critical) {
        BambooDartEntity bambooDartEntity = new BambooDartEntity(world, (PlayerEntity) shooter);
        bambooDartEntity.setPos(shooter.getX(), shooter.getEyeY(), shooter.getZ()
        );
        if (critical) {
            bambooDartEntity.setCritical(true);
        }

        bambooDartEntity.setVelocity(shooter, shooter.getPitch(), shooter.getYaw(), 0.0F, 4.0F, 0.0F);

        return bambooDartEntity;
    }

    @Override
    protected void shoot(LivingEntity shooter, ProjectileEntity projectile, int index, float speed, float divergence, float yaw, @Nullable LivingEntity target) {
        if (projectile instanceof BambooDartEntity bamboodart) {
            bamboodart.setVelocity(shooter, shooter.getPitch(), shooter.getYaw() + yaw, 0.0F, speed, divergence);
        }
    }

    public static float getPullProgress(int useTicks) {
        float f = (float)useTicks / 20.0F;
        f = (f * f + f * 2.0F) / 3.0F;
        if (f > 1.0F) {
            f = 1.0F;
        }

        return f;
    }
    @Override
    public Predicate<ItemStack> getProjectiles() {
        return itemStack -> itemStack.getItem() == ModItems.DART;
    }
    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.SPYGLASS;
    }
    @Override
    public boolean isEnchantable(ItemStack stack) {
        return true;
    }
}
