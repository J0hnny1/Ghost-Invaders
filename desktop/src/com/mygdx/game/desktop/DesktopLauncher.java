package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.mygdx.game.Controller;


public class DesktopLauncher {
    public static void main(String[] arg) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();

        config.setTitle("Ghost Invaders");
        config.setWindowedMode(1280, 720);
        config.setResizable(true);
        config.setWindowIcon("ghostIcon.png");
        new Lwjgl3Application(new Controller(), config);
    }
}
