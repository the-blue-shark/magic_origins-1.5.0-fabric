package net.the_blue_shark.item.custom;

import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.annotation.Nullable;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.WindChargeEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;
import net.the_blue_shark.entity.ModEntities;
import net.the_blue_shark.entity.projectile.BambooDartEntity;
import net.the_blue_shark.item.ModItems;

import java.util.List;
import java.util.function.Predicate;

public class SniperItem extends BowItem {
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
