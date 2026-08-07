package net.traum.learn1mod.entity.custom;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class ChairEntity extends Entity {
    public ChairEntity(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

//    @Override
//    public void tick() {
//        super.tick();
//
//        if (!this.level().isClientSide && !this.level().getBlockState(this.blockPosition()).is(ModBlocks.CHAIR.get())) {
//            this.discard();
//        }
//    }

    @Override
    public boolean shouldBeSaved() {
        return false;
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {

    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compoundTag) {

    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compoundTag) {

    }

    @Override
    protected void removePassenger(Entity passenger) {
        super.removePassenger(passenger);
        this.discard();
    }
}
