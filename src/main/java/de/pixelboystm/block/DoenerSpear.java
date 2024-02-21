package de.pixelboystm.block;
import com.simibubi.create.content.equipment.goggles.IHaveGoggleInformation;
import com.simibubi.create.content.kinetics.base.IRotate;
import com.simibubi.create.content.kinetics.simpleRelays.ShaftBlock;
import com.simibubi.create.foundation.block.IBE;
import de.pixelboystm.blockEntity.BlockEntityTypes;
import de.pixelboystm.blockEntity.DoenerSpearBlockEntity;
import de.pixelboystm.createdoener.CreateDoener;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DoenerSpear extends Block implements IRotate, IBE<DoenerSpearBlockEntity> {

    public static final DirectionProperty FACING = DirectionProperty.create("dir", Direction.Plane.HORIZONTAL);
    public static final EnumProperty<Fill> FILL = EnumProperty.create("fill", Fill.class);
    public static final EnumProperty<CookState> STATE = EnumProperty.create("state", CookState.class);

    public enum Fill implements StringRepresentable {
        None,
        One,
        Two,
        Full;

        @Override
        public String getSerializedName() {
            return switch (this) {
                case None -> "none";
                case One -> "one";
                case Two -> "two";
                case Full -> "full";
            };
        }

        public Fill next() {
            return switch (this) {
                case None -> Fill.One;
                case One -> Fill.Two;
                case Two, Full -> Fill.Full;
            };
        }
        public Fill prev() {
            return switch (this) {
                case None, One -> Fill.None;
                case Two -> Fill.One;
                case Full -> Fill.Two;
            };
        }
    }

    public enum CookState implements StringRepresentable {
        Raw,
        Medium,
        Cooked,
        ;

        @Override
        public String getSerializedName() {
            return switch (this) {
                case Raw -> "raw";
                case Cooked -> "cooked";
                case Medium -> "medium";
            };
        }
    }
    public DoenerSpear(Properties p) {
//        super(BlockBehaviour.Properties.of().destroyTime(1.2f).strength(3).noOcclusion());
        super(p);
        registerDefaultState(defaultBlockState().setValue(FACING, Direction.NORTH).setValue(FILL, Fill.None).setValue(STATE, CookState.Raw));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, FILL, STATE);
        super.createBlockStateDefinition(builder);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return super.getStateForPlacement(context).setValue(FACING, context.getHorizontalDirection()).setValue(FILL, Fill.None).setValue(STATE, CookState.Raw);
    }

    @Override
    public boolean hasShaftTowards(LevelReader world, BlockPos pos, BlockState state, Direction face) {
        return face == Direction.UP || face == Direction.DOWN;
    }

    @Override
    public SpeedLevel getMinimumRequiredSpeedLevel() {
        return SpeedLevel.SLOW;
    }



    @Override
    public Direction.Axis getRotationAxis(BlockState state) {
        return Direction.Axis.Y;
    }

    @Override
    public Class<DoenerSpearBlockEntity> getBlockEntityClass() {
        return DoenerSpearBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends DoenerSpearBlockEntity> getBlockEntityType() {
        return BlockEntityTypes.DOENER_SPEAR.get();
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
//        CreateDoener.LOGGER.debug("CLICK######################");
//        withBlockEntityDo(world, pos, (e) -> {
//            CreateDoener.LOGGER.debug("Speed: "  + e.getSpeed() + "\n Heat: " + DoenerSpearBlockEntity.getHeatLevelOf(world.getBlockState(pos.below())));
//        });

        if (world.isClientSide())
            return InteractionResult.PASS;


//        CreateDoener.LOGGER.debug("# Not client");
        if (player.getItemInHand(hand).is(Items.MUTTON) && state.getValue(FILL) != Fill.Full) {
            if (state.getValue(FILL) != Fill.None && state.getValue(STATE) != CookState.Raw)
                return InteractionResult.PASS;
            world.setBlockAndUpdate(pos, state.setValue(FILL, state.getValue(FILL).next()).setValue(STATE, CookState.Raw));
            player.getItemInHand(hand).shrink(1);



//            CreateDoener.LOGGER.debug("# Mutton");
            return InteractionResult.CONSUME;
        }

        if (player.getItemInHand(hand).is(de.pixelboystm.item.Items.DOENER_KNIFE.get()) && state.getValue(STATE) == CookState.Cooked && state.getValue(FILL) != Fill.None) {
            world.setBlockAndUpdate(pos, state.setValue(FILL, state.getValue(FILL).prev()));
            player.drop(new ItemStack(de.pixelboystm.item.Items.DOENER_MEAT.get(), 1), false);
            if (player.getItemInHand(hand).isDamageableItem()) {
                player.getItemInHand(hand).hurtAndBreak(1, player, (e) ->{});
            }
            return InteractionResult.SUCCESS;
        }


        return InteractionResult.PASS;
    }


}
