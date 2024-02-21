package de.pixelboystm.createdoener;

import com.mojang.logging.LogUtils;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.item.ItemDescription;
import com.simibubi.create.foundation.item.KineticStats;
import com.simibubi.create.foundation.item.TooltipHelper;
import com.simibubi.create.foundation.item.TooltipModifier;
import de.pixelboystm.block.Blocks;
import de.pixelboystm.blockEntity.BlockEntityTypes;
import de.pixelboystm.item.Items;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;

@Mod(CreateDoener.MODID)
public class CreateDoener {
    public static final String MODID = "createdoener";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);
    public static final RegistryObject<CreativeModeTab> DOENER_TAB = CREATIVE_MODE_TABS.register("doener_tab", () -> CreativeModeTab.builder()
            .title(Component.translatable("tab.createdoener.doener_tab"))
            .icon(() -> Items.DOENER_ITEM.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                output.accept(Blocks.DOENER_SPEAR.get());
                output.accept(Items.DOENER_KNIFE.get());
                output.accept(Items.DOENER_MEAT.get());
            output.accept(Items.DOENER_ITEM.get());
            }).build());

    public static final CreateRegistrate REGISTRATE = CreateRegistrate.create(CreateDoener.MODID);

    static {
        REGISTRATE.setTooltipModifierFactory(item -> new ItemDescription.Modifier(item, TooltipHelper.Palette.STANDARD_CREATE).andThen(TooltipModifier.mapNull(KineticStats.create(item))));
    }

    public CreateDoener() {
        REGISTRATE.registerEventListeners(FMLJavaModLoadingContext.get().getModEventBus());

        FMLJavaModLoadingContext.get().getModEventBus().addListener(CreateDoener::clientInit);

        Blocks.register();
        Items.register();
        BlockEntityTypes.register();
        CREATIVE_MODE_TABS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    public static void clientInit(final FMLClientSetupEvent event) {
        PartialModels.init();
    }

    public static ResourceLocation asResource(String path) {
        return new ResourceLocation(MODID, path);
    }

}
