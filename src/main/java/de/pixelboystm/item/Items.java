package de.pixelboystm.item;

import de.pixelboystm.block.Blocks;
import de.pixelboystm.createdoener.CreateDoener;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class Items {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, CreateDoener.MODID);

    public static final RegistryObject<Item> DOENER_ITEM = ITEMS.register("doener", () -> new Item(new Item.Properties().food(new FoodProperties.Builder().alwaysEat().nutrition(6).meat().saturationMod(2.0f).build())));
    public static final RegistryObject<Item> DOENER_KNIFE = ITEMS.register("doener_knife", () -> new Item(new Item.Properties().durability(500)));

    public static final RegistryObject<Item> DOENER_MEAT = ITEMS.register("doener_meat", () -> new Item(new Item.Properties().food(new FoodProperties.Builder().alwaysEat().nutrition(1).meat().fast().build())));
    public static final RegistryObject<Item> DOENER_SPEAR = ITEMS.register("doener_spear", () -> new BlockItem(Blocks.DOENER_SPEAR.get(), new Item.Properties()));

    public static void register() {
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}
