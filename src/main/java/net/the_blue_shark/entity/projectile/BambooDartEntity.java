package net.the_blue_shark.entity.projectile;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ItemEnchantmentsComponent;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ProjectileDeflection;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.EntityEffectParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.potion.Potion;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.the_blue_shark.entity.ModEntities;
import net.the_blue_shark.item.ModItems;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static net.minecraft.enchantment.Enchantments.FLAME;

public class BambooDartEntity extends PersistentProjectileEntity {

    public BambooDartEntity(EntityType<? extends PersistentProjectileEntity> entityType, World world) {
        super(entityType, world);
    }
    public BambooDartEntity(World world, PlayerEntity player) {
        super(ModEntities.BAMBOO_DART, world);
        setOwner(player);
        BlockPos blockpos = player.getBlockPos();
        double d0 = (double)blockpos.getX() + 0.5D;
        double d1 = (double)blockpos.getY() + 1.75D;
        double d2 = (double)blockpos.getZ() + 0.5D;
        this.refreshPositionAndAngles(d0, d1, d2, this.getYaw(), this.getPitch());

    }


    @Override
    public void tick() {
        super.tick();
        boolean bl = this.isNoClip();
        Vec3d vec3d = this.getVelocity();

        if (this.prevPitch == 0.0F && this.prevYaw == 0.0F) {
            double d = vec3d.horizontalLength();
            this.setYaw((float) (MathHelper.atan2(vec3d.x, vec3d.z) * 180.0F / Math.PI));
            this.setPitch((float) (MathHelper.atan2(vec3d.y, d) * 180.0F / Math.PI));
            this.prevYaw = this.getYaw();
            this.prevPitch = this.getPitch();
        }

        BlockPos blockPos = this.getBlockPos();
        BlockState blockState = this.getWorld().getBlockState(blockPos);
        if (!blockState.isAir() && !bl) {
            VoxelShape voxelShape = blockState.getCollisionShape(this.getWorld(), blockPos);
            if (!voxelShape.isEmpty()) {
                Vec3d vec3d2 = this.getPos();

                for (Box box : voxelShape.getBoundingBoxes()) {
                    if (box.offset(blockPos).contains(vec3d2)) {
                        this.inGround = true;
                        break;
                    }
                }
            }
        }

        if (this.shake > 0) {
            this.shake--;
        }

        if (this.isTouchingWaterOrRain() || blockState.isOf(Blocks.POWDER_SNOW)) {
            this.extinguish();
        }

        if (this.inGround && !bl) {
            try {
                // Access private inBlockState field
                Field inBlockStateField = PersistentProjectileEntity.class.getDeclaredField("inBlockState");
                inBlockStateField.setAccessible(true);
                BlockState inBlockState = (BlockState) inBlockStateField.get(this);

                // Access and invoke shouldFall method
                Method shouldFallMethod = PersistentProjectileEntity.class.getDeclaredMethod("shouldFall");
                shouldFallMethod.setAccessible(true);
                boolean shouldFall = (boolean) shouldFallMethod.invoke(this);

                if (inBlockState != blockState && shouldFall) {
                    // Access and invoke fall method
                    Method fallMethod = PersistentProjectileEntity.class.getDeclaredMethod("fall");
                    fallMethod.setAccessible(true);
                    fallMethod.invoke(this);
                } else if (!this.getWorld().isClient) {
                    this.age();
                }
            } catch (NoSuchFieldException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                e.printStackTrace(); // Log reflection issues for debugging
            }

            this.inGroundTime++;
        } else {
            this.inGroundTime = 0;
            Vec3d vec3d3 = this.getPos();
            Vec3d vec3d2 = vec3d3.add(vec3d);
            HitResult hitResult = this.getWorld().raycast(new RaycastContext(vec3d3, vec3d2, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, this));
            if (hitResult.getType() != HitResult.Type.MISS) {
                vec3d2 = hitResult.getPos();
            }

            while (!this.isRemoved()) {
                EntityHitResult entityHitResult = this.getEntityCollision(vec3d3, vec3d2);
                if (entityHitResult != null) {
                    hitResult = entityHitResult;
                }

                if (hitResult != null && hitResult.getType() == HitResult.Type.ENTITY) {
                    Entity entity = ((EntityHitResult) hitResult).getEntity();
                    Entity entity2 = this.getOwner();
                    if (entity instanceof PlayerEntity && entity2 instanceof PlayerEntity && !((PlayerEntity) entity2).shouldDamagePlayer((PlayerEntity) entity)) {
                        hitResult = null;
                        entityHitResult = null;
                    }
                }

                if (hitResult != null && !bl) {
                    ProjectileDeflection projectileDeflection = this.hitOrDeflect(hitResult);
                    this.velocityDirty = true;
                    if (projectileDeflection != ProjectileDeflection.NONE) {
                        break;
                    }
                }

                if (entityHitResult == null || this.getPierceLevel() <= 0) {
                    break;
                }

                hitResult = null;
            }

            vec3d = this.getVelocity();
            double e = vec3d.x;
            double f = vec3d.y;
            double g = vec3d.z;
            if (this.isCritical()) {
                for (int i = 0; i < 4; i++) {
                    this.getWorld()
                            .addParticle(
                                    ParticleTypes.CRIT,
                                    this.getX() + e * i / 4.0,
                                    this.getY() + f * i / 4.0,
                                    this.getZ() + g * i / 4.0,
                                    -e,
                                    -f + 0.2,
                                    -g
                            );
                }
            }

            double h = this.getX() + e;
            double j = this.getY() + f;
            double k = this.getZ() + g;
            double l = vec3d.horizontalLength();
            if (bl) {
                this.setYaw((float) (MathHelper.atan2(-e, -g) * 180.0F / Math.PI));
            } else {
                this.setYaw((float) (MathHelper.atan2(e, g) * 180.0F / Math.PI));
            }

            this.setPitch((float) (MathHelper.atan2(f, l) * 180.0F / Math.PI));
            this.setPitch(updateRotation(this.prevPitch, this.getPitch()));
            this.setYaw(updateRotation(this.prevYaw, this.getYaw()));
            if (this.isTouchingWater()) {
                this.discard();
                this.dropItem(ModItems.DART);
            }
            if (vec3d.lengthSquared() < 9 && !this.isTouchingWater()) {
                this.discard();
                this.dropItem(ModItems.DART);
            }
            this.setPosition(h, j, k);
            this.checkBlockCollision();
        }
    }



    @Override
    protected ItemStack getDefaultItemStack() {
        return new ItemStack(ModItems.DART);
    }
    @Override
    public boolean hasNoGravity() {
        return true;
    }
}
