package org.example.ecosub;

import org.lwjgl.glfw.GLFW;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.client.Minecraft;
import net.minecraft.client.KeyMapping;


@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = {Dist.CLIENT})
public class EcosubModKeyMappings {
    public static final KeyMapping WALLETKEY = new KeyMapping("key.ecosub.walletkey", GLFW.GLFW_KEY_K, "key.categories.ui") {
        private boolean isDownOld = false;

        @Override
        public void setDown(boolean isDown) {
            super.setDown(isDown);
            if (isDownOld != isDown && isDown) {
                WALLETKEY_LASTPRESS = System.currentTimeMillis();
            } else if (isDownOld != isDown && !isDown) {
                int dt = (int) (System.currentTimeMillis() - WALLETKEY_LASTPRESS);
                EconomySubSeasons.PACKET_HANDLER.sendToServer(new WalletkeyMessage(1, dt));
                WalletkeyMessage.pressAction(Minecraft.getInstance().player, 1, dt);
            }
            isDownOld = isDown;
        }
    };
    private static long WALLETKEY_LASTPRESS = 0;

    @SubscribeEvent
    public static void registerKeyMappings(RegisterKeyMappingsEvent event) {
        event.register(WALLETKEY);
    }

    @Mod.EventBusSubscriber({Dist.CLIENT})
    public static class KeyEventListener {
        @SubscribeEvent
        public static void onClientTick(TickEvent.ClientTickEvent event) {
            if (Minecraft.getInstance().screen == null) {
                WALLETKEY.consumeClick();
            }
        }
    }
}

