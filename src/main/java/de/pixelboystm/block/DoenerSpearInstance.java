package de.pixelboystm.block;

import com.jozufozu.flywheel.api.Instancer;
import com.jozufozu.flywheel.api.MaterialManager;
import com.simibubi.create.AllPartialModels;
import com.simibubi.create.content.kinetics.base.HalfShaftInstance;
import com.simibubi.create.content.kinetics.base.flwdata.RotatingData;
import de.pixelboystm.blockEntity.DoenerSpearBlockEntity;
import de.pixelboystm.createdoener.PartialModels;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;

public class DoenerSpearInstance extends HalfShaftInstance<DoenerSpearBlockEntity> {
    public DoenerSpearInstance(MaterialManager materialManager, DoenerSpearBlockEntity blockEntity) {
        super(materialManager, blockEntity);
    }

    @Override
    protected Instancer<RotatingData> getModel() {
        return getRotatingMaterial().getModel(PartialModels.DOENER_SPEAR_SHAFT, getRenderedBlockState());
    }

    @Override
    protected BlockState getRenderedBlockState() {
        return this.shaft();
    }
}
