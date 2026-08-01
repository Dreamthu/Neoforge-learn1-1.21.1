package net.traum.learn1mod.entity.custom;

import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec2;
import net.traum.learn1mod.entity.ModEntities;
import net.traum.learn1mod.item.ModItems;

public class TomahawkProjectileEntity extends AbstractArrow {
    private float rotation;
    public Vec2 groundedOffset;

    public TomahawkProjectileEntity(EntityType<? extends AbstractArrow> entityType, Level level) {
        super(entityType, level);
    }

    public TomahawkProjectileEntity(LivingEntity shooter, Level level, ItemStack weaponStack) {
        super(ModEntities.TOMAHAWK.get(), shooter, level, weaponStack.copyWithCount(1), weaponStack.copyWithCount(1));
    }

    @Override
    protected ItemStack getDefaultPickupItem() {
        return new ItemStack(ModItems.TOMAHAWK.get());
    }

    @Override
    protected SoundEvent getDefaultHitGroundSoundEvent() {
        return SoundEvents.TRIDENT_HIT_GROUND;
    }

    public float getRenderingRotation() {
        rotation += 0.5f;
        if(rotation >= 360) {
            rotation = 0;
        }
        return rotation;
    }

    public boolean isGrounded() {
        return inGround;
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        Entity entity = result.getEntity();

        if (!this.level().isClientSide) {
            entity.hurt(this.damageSources().thrown(this, this.getOwner()), 8);
            this.level().broadcastEntityEvent(this, (byte)3);
            this.discard();
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);

        if(result.getDirection() == Direction.SOUTH) {
            groundedOffset = new Vec2(215f,180f);
        }
        if(result.getDirection() == Direction.NORTH) {
            groundedOffset = new Vec2(215f, 0f);
        }
        if(result.getDirection() == Direction.EAST) {
            groundedOffset = new Vec2(215f,-90f);
        }
        if(result.getDirection() == Direction.WEST) {
            groundedOffset = new Vec2(215f,90f);
        }

        if(result.getDirection() == Direction.DOWN) {
            groundedOffset = new Vec2(115f, this.getYRot());
        }
        if(result.getDirection() == Direction.UP) {
            groundedOffset = new Vec2(285f, this.getYRot());
        }
    }
}
