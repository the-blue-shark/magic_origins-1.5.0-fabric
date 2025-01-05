package net.the_blue_shark.item.custom;

import io.github.apace100.apoli.component.PowerHolderComponent;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.Unit;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;
import net.the_blue_shark.entity.projectile.BambooDartEntity;
import net.the_blue_shark.item.ModItemGroups;
import net.the_blue_shark.item.ModItems;
import net.the_blue_shark.power.custom.BambooSniperPowerType;
import net.the_blue_shark.util.ModTags;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Predicate;

public class BambooBlowpipeItem extends Item {
    public static final Predicate<ItemStack> BLOWPIPE_PROJECTILES = stack -> stack.isIn(ModTags.Items.BAMBOO_DARTS);

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

    public BambooBlowpipeItem(Settings settings) {
        super(settings);
    }

    public Predicate<ItemStack> getHeldProjectiles() {
        return this.getProjectiles();
    }

    public Predicate<ItemStack> getProjectiles() {
        return BLOWPIPE_PROJECTILES;
    }

    public static ItemStack getHeldProjectile(LivingEntity entity, Predicate<ItemStack> predicate) {
        if (predicate.test(entity.getStackInHand(Hand.OFF_HAND))) {
            return entity.getStackInHand(Hand.OFF_HAND);
        } else {
            return predicate.test(entity.getStackInHand(Hand.MAIN_HAND)) ? entity.getStackInHand(Hand.MAIN_HAND) : ItemStack.EMPTY;
        }
    }

    @Override
    public int getEnchantability() {
        return 1;
    }

    public int getRange() {
        return 500;
    }

    protected void shootAll(
            ServerWorld world,
            LivingEntity shooter,
            Hand hand,
            ItemStack stack,
            List<ItemStack> projectiles,
            float speed,
            float divergence,
            boolean critical,
            @Nullable LivingEntity target
    ) {
        float f = EnchantmentHelper.getProjectileSpread(world, stack, shooter, 0.0F);
        float g = projectiles.size() == 1 ? 0.0F : 2.0F * f / (float)(projectiles.size() - 1);
        float h = (float)((projectiles.size() - 1) % 2) * g / 2.0F;
        float i = 1.0F;

        for (int j = 0; j < projectiles.size(); j++) {
            ItemStack itemStack = (ItemStack)projectiles.get(j);
            if (!itemStack.isEmpty()) {
                float k = h + i * (float)((j + 1) / 2) * g;
                i = -i;
                ProjectileEntity projectileEntity = this.createBambooDartEntity(world, shooter, stack, itemStack, critical);
                this.shoot(shooter, projectileEntity, j, speed, divergence, k, target);
                world.spawnEntity(projectileEntity);
                stack.damage(this.getWeaponStackDamage(itemStack), shooter, LivingEntity.getSlotForHand(hand));
                if (stack.isEmpty()) {
                    break;
                }
            }
        }
    }

    protected int getWeaponStackDamage(ItemStack projectile) {
        return 1;
    }

    protected void shoot(LivingEntity shooter, ProjectileEntity projectile, int index, float speed, float divergence, float yaw, @Nullable LivingEntity target) {
        projectile.setVelocity(shooter, shooter.getPitch(), shooter.getYaw() + yaw, 0.0F, speed, divergence);
    }

    protected ProjectileEntity createBambooDartEntity(World world, LivingEntity shooter, ItemStack weaponStack, ItemStack projectileStack, boolean critical) {
        BambooDartEntity bambooDartEntity = new BambooDartEntity(world, (PlayerEntity) shooter);
        bambooDartEntity.setPos(shooter.getX(), shooter.getEyeY(), shooter.getZ()
        );
        if (critical) {
            bambooDartEntity.setCritical(true);
        }

        return bambooDartEntity;
    }

    protected static List<ItemStack> load(ItemStack stack, ItemStack projectileStack, LivingEntity shooter) {
        if (projectileStack.isEmpty()) {
            return List.of();
        } else {
            int i = shooter.getWorld() instanceof ServerWorld serverWorld ? EnchantmentHelper.getProjectileCount(serverWorld, stack, shooter, 1) : 1;
            List<ItemStack> list = new ArrayList(i);
            ItemStack itemStack = projectileStack.copy();

            for (int j = 0; j < i; j++) {
                ItemStack itemStack2 = getProjectile(stack, j == 0 ? projectileStack : itemStack, shooter, j > 0);
                if (!itemStack2.isEmpty()) {
                    list.add(itemStack2);
                }
            }

            return list;
        }
    }

    protected static ItemStack getProjectile(ItemStack stack, ItemStack projectileStack, LivingEntity shooter, boolean multishot) {
        int i = !multishot && !shooter.isInCreativeMode() && shooter.getWorld() instanceof ServerWorld serverWorld
                ? EnchantmentHelper.getAmmoUse(serverWorld, stack, projectileStack, 1)
                : 0;
        if (i > projectileStack.getCount()) {
            return ItemStack.EMPTY;
        } else if (i == 0) {
            ItemStack itemStack = projectileStack.copyWithCount(1);
            itemStack.set(DataComponentTypes.INTANGIBLE_PROJECTILE, Unit.INSTANCE);
            return itemStack;
        } else {
            ItemStack itemStack = projectileStack.split(i);
            if (projectileStack.isEmpty() && shooter instanceof PlayerEntity playerEntity) {
                playerEntity.getInventory().removeOne(projectileStack);
            }

            return itemStack;
        }
    }

    @Override
    public int getMaxUseTime(ItemStack stack, LivingEntity user) {
        return 72000;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        boolean bl = !user.getProjectileType(itemStack).isEmpty();
        if (!user.isInCreativeMode() && !bl) {
            return TypedActionResult.fail(itemStack);
        } else {
            user.setCurrentHand(hand);
            return TypedActionResult.consume(itemStack);
        }
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.CROSSBOW;
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
                        Random rand = new Random();
                        int random = rand.nextInt(4);
                        if (random == 3) {
                            sendFailMessage(playerEntity, "bamboo_sniper.fail.three");
                        }
                        if (random == 2) {
                            sendFailMessage(playerEntity, "bamboo_sniper.fail.two");
                        }
                        if (random == 1) {
                            sendFailMessage(playerEntity, "bamboo_sniper.fail.one");
                        }
                        if (random == 0) {
                            sendFailMessage(playerEntity, "bamboo_sniper.fail.zero");
                        }
                    }
                }

            }
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
}
