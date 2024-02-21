package de.pixelboystm.blockEntity;

import com.simibubi.create.content.kinetics.base.HalfShaftInstance;
import com.tterrag.registrate.util.entry.BlockEntityEntry;
import de.pixelboystm.block.Blocks;
import de.pixelboystm.block.DoenerSpearInstance;
import de.pixelboystm.createdoener.CreateDoener;
import de.pixelboystm.renderers.DoenerSpearRenderer;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlockEntityTypes {

//    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, CreateDoener.MODID);

//    public static final RegistryObject<BlockEntityType<DoenerSpearBlockEntity>> DOENER_SPEAR = BLOCK_ENTITY_TYPES.register("doener_spear_block_entity" , () -> BlockEntityType.Builder.of(DoenerSpearBlockEntity::new, Blocks.DOENER_SPEAR.get()).build(null));

    public static final BlockEntityEntry<DoenerSpearBlockEntity> DOENER_SPEAR = CreateDoener.REGISTRATE.blockEntity("doener_spear", DoenerSpearBlockEntity::new)
            .instance(() -> DoenerSpearInstance::new)
            .validBlock(Blocks.DOENER_SPEAR)
            .renderer(() -> DoenerSpearRenderer::new)
            .register();
    public static void register(){
//        BLOCK_ENTITY_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}
