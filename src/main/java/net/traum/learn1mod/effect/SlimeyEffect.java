package net.traum.learn1mod.effect;

import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

// Climbing Effect by SameDifferent: https://github.com/samedifferent/TrickOrTreat/blob/master/LICENSE
// Distributed under MIT
public class SlimeyEffect extends MobEffect {
    public SlimeyEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean applyEffectTick(LivingEntity entity, int amplifier) {
        if (entity.horizontalCollision) {
            Vec3 vel = entity.getDeltaMovement();
            entity.setDeltaMovement(vel.x * 0.8F, 0.2D, vel.z * 0.8F);
            return true;
        }

        if (!(entity instanceof Player) && entity.getDeltaMovement().horizontalDistance() > 0.01 && isMovingTowardWall(entity)) {
            Vec3 vel = entity.getDeltaMovement();
            entity.setDeltaMovement(vel.x * 0.8F, 0.2D, vel.z * 0.8F);
            return true;
        }

        return super.applyEffectTick(entity, amplifier);
    }

    private boolean isMovingTowardWall(LivingEntity entity) {
        Vec3 movement = entity.getDeltaMovement();
        BlockPos pos = entity.blockPosition();
        Level level = entity.level();

        if (movement.x > 0.01 && level.getBlockState(pos.east()).isSolid()) return true;
        if (movement.x < -0.01 && level.getBlockState(pos.west()).isSolid()) return true;
        if (movement.z > 0.01 && level.getBlockState(pos.south()).isSolid()) return true;
        if (movement.z < -0.01 && level.getBlockState(pos.north()).isSolid()) return true;

        return false;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }
}
