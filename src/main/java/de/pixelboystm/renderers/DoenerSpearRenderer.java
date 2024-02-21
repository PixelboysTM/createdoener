package de.pixelboystm.renderers;

import com.simibubi.create.AllPartialModels;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;
import com.simibubi.create.foundation.render.CachedBufferer;
import com.simibubi.create.foundation.render.SuperByteBuffer;
import de.pixelboystm.block.DoenerSpear;
import de.pixelboystm.createdoener.PartialModels;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.block.state.BlockState;

public class DoenerSpearRenderer extends KineticBlockEntityRenderer {
    public DoenerSpearRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    protected SuperByteBuffer getRotatedModel(KineticBlockEntity be, BlockState state) {
        return CachedBufferer.partial(PartialModels.DOENER_SPEAR_SHAFT, state);
    }
}