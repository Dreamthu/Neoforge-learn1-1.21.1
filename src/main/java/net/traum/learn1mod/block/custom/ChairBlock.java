package net.traum.learn1mod.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.traum.learn1mod.entity.ModEntities;
import net.traum.learn1mod.entity.custom.ChairEntity;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ChairBlock extends HorizontalDirectionalBlock {
    public static final MapCodec<ChairBlock> CODEC = simpleCodec(ChairBlock::new);
    private static final VoxelShape SEAT = Block.box(3.0, 0.0, 3.0, 13.0, 8.0, 13.0);
    private static final VoxelShape BACK_NORTH = Block.box(3.0, 8.0, 11.0, 13.0, 16.0, 13.0);
    private static final VoxelShape BACK_SOUTH = Block.box(3.0, 8.0, 3.0, 13.0, 16.0, 5.0);
    private static final VoxelShape BACK_WEST = Block.box(11.0, 8.0, 3.0, 13.0, 16.0, 13.0);
    private static final VoxelShape BACK_EAST = Block.box(3.0, 8.0, 3.0, 5.0, 16.0, 13.0);

    public ChairBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if(!level.isClientSide()) {
            Entity entity = null;
            List<ChairEntity> entities = level.getEntities(ModEntities.CHAIR_ENTITY.get(), new AABB(pos), chair -> true);
            if(entities.isEmpty()) {
                entity = ModEntities.CHAIR_ENTITY.get().spawn(((ServerLevel) level), pos, MobSpawnType.TRIGGERED);
            } else {
                entity = entities.get(0);
            }

            if (entity != null) {
                player.startRiding(entity);
            }
        }

        return InteractionResult.SUCCESS;
    }

    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        if (!state.is(newState.getBlock())) {
            level.getEntities(ModEntities.CHAIR_ENTITY.get(), new AABB(pos), chair -> true)
                    .forEach(ChairEntity::discard);
        }

        super.onRemove(state, level, pos, newState, movedByPiston);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return switch (state.getValue(FACING)) {
            case NORTH -> Shapes.or(SEAT, BACK_NORTH);
            case SOUTH -> Shapes.or(SEAT, BACK_SOUTH);
            case WEST -> Shapes.or(SEAT, BACK_WEST);
            case EAST -> Shapes.or(SEAT, BACK_EAST);
            default -> SEAT;
        };
    }

    @Override
    protected MapCodec<? extends HorizontalDirectionalBlock> codec() {
        return CODEC;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }
}
