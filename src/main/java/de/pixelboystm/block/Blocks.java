package de.pixelboystm.block;

import com.simibubi.create.AllTags;
import com.simibubi.create.content.kinetics.BlockStressDefaults;
import com.simibubi.create.foundation.data.SharedProperties;
import com.tterrag.registrate.util.entry.BlockEntry;
import de.pixelboystm.createdoener.CreateDoener;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.simibubi.create.foundation.data.ModelGen.customItemModel;

public class Blocks {

    static {
        CreateDoener.REGISTRATE.getCreativeTab();
    }
//    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, CreateDoener.MODID);

//    public static final RegistryObject<Block> DOENER_SPEAR = BLOCKS.register("doener_spear", () -> new DoenerSpear());

    public static final BlockEntry<DoenerSpear> DOENER_SPEAR = CreateDoener.REGISTRATE.block("doener_spear", DoenerSpear::new)
            .initialProperties(SharedProperties::wooden)
            .properties((p) -> p.destroyTime(1.2f).strength(3).noOcclusion())
            .tag(AllTags.AllBlockTags.SAFE_NBT.tag)
            .transform(BlockStressDefaults.setCapacity(1000.0))
            .item()
            .transform(customItemModel())
            .register();

    public static void register() {
//        BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}
