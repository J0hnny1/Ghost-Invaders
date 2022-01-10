package com.mygdx.game.desktop;

import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.mygdx.game.Controller;


public class DesktopLauncher {
    public static void main(String[] arg) {
        Graphics.DisplayMode primaryMode = Lwjgl3ApplicationConfiguration.getDisplayMode();
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();

        config.setTitle("Ghost Invaders");
        config.setWindowedMode(1280, 720);
        config.setResizable(false);
        config.setWindowIcon("ghostIcon.png");
        new Lwjgl3Application(new Controller(), config);
    }
}
