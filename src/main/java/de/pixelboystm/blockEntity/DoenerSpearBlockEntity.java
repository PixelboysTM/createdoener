package de.pixelboystm.blockEntity;

import com.simibubi.create.AllTags;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.content.processing.burner.BlazeBurnerBlock;
import com.simibubi.create.foundation.utility.BlockHelper;
import de.pixelboystm.block.DoenerSpear;
import de.pixelboystm.createdoener.CreateDoener;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class DoenerSpearBlockEntity extends KineticBlockEntity {

    private int ticksBurning = 0;
    public DoenerSpearBlockEntity( BlockPos pos, BlockState state) {
        super(BlockEntityTypes.DOENER_SPEAR.get(), pos, state);
    }
    public static BlazeBurnerBlock.HeatLevel getHeatLevelOf(BlockState state) {
        if (state.hasProperty(BlazeBurnerBlock.HEAT_LEVEL))
            return state.getValue(BlazeBurnerBlock.HEAT_LEVEL);
        return AllTags.AllBlockTags.PASSIVE_BOILER_HEATERS.matches(state) && BlockHelper.isNotUnheated(state) ? BlazeBurnerBlock.HeatLevel.SMOULDERING : BlazeBurnerBlock.HeatLevel.NONE;
    }

    @Override
    public void tick() {
        super.tick();
        if (level.isClientSide)
            return;

        BlockState state = getBlockState();

        if (state.getValue(DoenerSpear.FILL) == DoenerSpear.Fill.None) {
            ticksBurning = 0;
        }

        if (state.getValue(DoenerSpear.FILL) != DoenerSpear.Fill.None && getHeatLevelOf(level.getBlockState(getBlockPos().below())).isAtLeast(BlazeBurnerBlock.HeatLevel.FADING) && Math.abs(getSpeed()) > 10.0 ) {
            if (state.getValue(DoenerSpear.STATE) == DoenerSpear.CookState.Raw) {
                level.setBlockAndUpdate(worldPosition, state.setValue(DoenerSpear.STATE,  DoenerSpear.CookState.Medium));
                ticksBurning = 0;
            } else if (state.getValue(DoenerSpear.STATE) == DoenerSpear.CookState.Medium) {
                if (ticksBurning >= 120){
                    ticksBurning = 0;
                    level.setBlockAndUpdate(worldPosition, state.setValue(DoenerSpear.STATE,  DoenerSpear.CookState.Cooked));
                    CreateDoener.LOGGER.debug("DONE");
                }else {
                    ticksBurning++;
                }
            }
        }

    }
}
