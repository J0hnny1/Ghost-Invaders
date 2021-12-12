package com.mygdx.game.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.HdpiMode;
import com.mygdx.game.MyGdxGame;



public class DesktopLauncher {
    public static void main(String[] arg) {
        Graphics.DisplayMode primaryMode = Lwjgl3ApplicationConfiguration.getDisplayMode();
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        //Fullscreen
        //config.setFullscreenMode(primaryMode);
        config.setTitle("Game Demo");
        config.setWindowedMode(1280,720);
        new Lwjgl3Application(new MyGdxGame(), config);

        //config.setHdpiMode(HdpiMode.Logical);

        //config.setInitialBackgroundColor(Color.BLUE);


    }
}
