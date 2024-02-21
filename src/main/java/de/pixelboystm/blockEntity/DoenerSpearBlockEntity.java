package de.pixelboystm.blockEntity;

import com.simibubi.create.AllTags;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.content.processing.burner.BlazeBurnerBlock;
import com.simibubi.create.foundation.utility.BlockHelper;
import com.simibubi.create.foundation.utility.Color;
import com.simibubi.create.foundation.utility.Lang;
import de.pixelboystm.block.DoenerSpear;
import de.pixelboystm.createdoener.CreateDoener;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class DoenerSpearBlockEntity extends KineticBlockEntity {

    private int ticksBurning = 0;

    public DoenerSpearBlockEntity(BlockEntityType<?> typeIn, BlockPos pos, BlockState state) {
        super(typeIn, pos, state);
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

        if (state.getValue(DoenerSpear.FILL) != DoenerSpear.Fill.None && getHeatLevelOf(level.getBlockState(getBlockPos().below())).isAtLeast(BlazeBurnerBlock.HeatLevel.FADING) && Math.abs(getSpeed()) >= 10.0 ) {
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
            level.sendBlockUpdated(getBlockPos(), state, state, 1);

        }

    }

    @Override
    public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
//        Lang.text("HELLO").forGoggles(tooltip);
//        return true;

        boolean show = false;

        Lang.translate("googles.spear.fill").space().add(Lang.text(getBlockState().getValue(DoenerSpear.FILL).getSerializedName())).forGoggles(tooltip);


        if (!getHeatLevelOf(level.getBlockState(getBlockPos().below())).isAtLeast(BlazeBurnerBlock.HeatLevel.FADING)) {
            Lang.translate("googles.spear.cold").color(new Color(0, 0, 255).getRGB() ).forGoggles(tooltip);
            show = true;
        }

        if (getSpeed() < 10) {
            Lang.translate("googles.spear.slow").forGoggles(tooltip);
            show = true;
        }


        if (ticksBurning > 0) {
            Lang.translate("googles.spear.progress").space().add(Lang.number(ticksBurning).color(Color.RED.getRGB())).forGoggles(tooltip);
           show = true;
        }

        return super.addToGoggleTooltip(tooltip, isPlayerSneaking) || show;
    }

    @Override
    protected void write(CompoundTag compound, boolean clientPacket) {
        super.write(compound, clientPacket);
        compound.putInt("time", ticksBurning);
    }

    @Override
    protected void read(CompoundTag compound, boolean clientPacket) {
        super.read(compound, clientPacket);
        ticksBurning = compound.getInt("time");
    }
}
