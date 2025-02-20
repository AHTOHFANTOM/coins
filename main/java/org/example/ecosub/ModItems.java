package org.example.ecosub;


import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, EconomySubSeasons.MODID);
    public static final RegistryObject<Item> WALLET = REGISTRY.register("wallet", () -> new WalletItem());
    // Start of user code block custom items
    // End of user code block custom items

}


