package org.example.ecosub;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = {Dist.CLIENT})
public class KeyHandler extends JFrame implements KeyListener {

    @Override
    public void keyTyped(KeyEvent e) {
        if(KeyEvent.getKeyText(e.getKeyCode()).equals("k")){
            System.out.println("k");
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
