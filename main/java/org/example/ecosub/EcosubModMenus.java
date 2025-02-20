package org.example.ecosub;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.common.extensions.IForgeMenuType;

import net.minecraft.world.inventory.MenuType;



public class EcosubModMenus {
    public static final DeferredRegister<MenuType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.MENU_TYPES, EconomySubSeasons.MODID);
    public static final RegistryObject<MenuType<WalletguiMenu>> WALLETGUI = REGISTRY.register("walletgui", () -> IForgeMenuType.create(WalletguiMenu::new));
}

