package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Input;

public class InputProcessor implements com.badlogic.gdx.InputProcessor {
    boolean fullscreen = false;
    Player player;

    public InputProcessor(Player player) {
        this.player = player;
    }

    boolean isPaused = false;

    public boolean isPaused() {
        return isPaused;
    }

    public void setPaused(boolean paused) {
        isPaused = paused;
    }

    /**
     * @param keycode
     * @return if action is performed
     */
    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.F:
                if (!fullscreen) {
                    Graphics.Monitor currMonitor = Gdx.graphics.getMonitor();
                    Graphics.DisplayMode displayMode = Gdx.graphics.getDisplayMode(currMonitor);
                    fullscreen = true;
                    if (!Gdx.graphics.setFullscreenMode(displayMode)) {
                        // switching to full-screen mode failed

                    }

                } else {
                    Gdx.graphics.setWindowedMode(1280, 720);
                    fullscreen = false;
                }
                break;
            case Input.Keys.ESCAPE:
                if (!isPaused) {
                    isPaused = true;
                } else isPaused = false;


        }

        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.W -> player.setPlayerdirection(Player.direction.FRONT);
            case Input.Keys.A -> player.setPlayerdirection(Player.direction.LEFT);
            case Input.Keys.S -> player.setPlayerdirection(Player.direction.BACK);
            case Input.Keys.D -> player.setPlayerdirection(Player.direction.RIGHT);
        }
        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }


}
