    package org.example.ecosub;

    import io.netty.buffer.Unpooled;
    import net.minecraft.core.BlockPos;
    import net.minecraft.network.FriendlyByteBuf;
    import net.minecraft.network.chat.Component;
    import net.minecraft.server.level.ServerPlayer;
    import net.minecraft.world.MenuProvider;
    import net.minecraft.world.entity.player.Inventory;
    import net.minecraft.world.entity.player.Player;
    import net.minecraft.world.inventory.AbstractContainerMenu;
    import net.minecraft.world.item.Item;
    import net.minecraft.world.item.ItemStack;
    import net.minecraft.world.item.Rarity;
    import net.minecraft.world.item.context.UseOnContext;
    import net.minecraft.world.InteractionHand;
    import net.minecraft.world.InteractionResult;
    import net.minecraft.world.InteractionResultHolder;
    import net.minecraft.world.level.Level;
    import net.minecraftforge.network.NetworkHooks;

    public class WalletItem extends Item {
        public WalletItem() {
            super(new Item.Properties().stacksTo(1).rarity(Rarity.COMMON));
        }

        @Override
        public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
            if (!world.isClientSide && player instanceof ServerPlayer serverPlayer) {
                openWalletGUI(serverPlayer);
            }
            return InteractionResultHolder.success(player.getItemInHand(hand));
        }

        private void openWalletGUI(ServerPlayer player) {
            BlockPos pos = player.blockPosition(); // Получаем текущую позицию игрока

            NetworkHooks.openScreen(player, new MenuProvider() {
                @Override
                public Component getDisplayName() {
                    return Component.literal("Wallet GUI");
                }

                @Override
                public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
                    return new WalletguiMenu(id, inventory, new FriendlyByteBuf(Unpooled.buffer()).writeBlockPos(pos));
                }
            });
        }
    }
