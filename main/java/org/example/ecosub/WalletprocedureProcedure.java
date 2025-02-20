package org.example.ecosub;

import io.netty.buffer.Unpooled;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.network.NetworkHooks;

public class WalletprocedureProcedure {
    public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
        if (entity == null)
            return;
        if (entity instanceof ServerPlayer _ent) {
            BlockPos _bpos = BlockPos.containing(x, y, z);
            // Загружаем данные игрока
            PlayerData playerData = PlayerDataManager.loadPlayerData(_ent.getName().getString());
            int balance = playerData.getBalance();
            System.out.println(balance);

            NetworkHooks.openScreen(_ent, new MenuProvider() {
                @Override
                public Component getDisplayName() {
                    return Component.literal("Walletgui");
                }

                @Override
                public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
                    FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
                    buf.writeBlockPos(_bpos);
                    FriendlyByteBuf bufb = new FriendlyByteBuf(Unpooled.buffer());
                    bufb.writeInt(balance);
                    return new WalletguiMenu(id, inventory, buf, bufb);
                }
            }, _bpos);
        }
    }

}
