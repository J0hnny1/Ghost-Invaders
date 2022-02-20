package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Input;

public class InputProcessor implements com.badlogic.gdx.InputProcessor {
    boolean fullscreen = false;
    Player player;
    int player_texture_index = 0;
    boolean gameIsStarted = false;
    boolean deathScreen = false;
    boolean isPaused = false;
    public int mouse_x, mouse_y;
    Controller controller = new Controller();

    public InputProcessor(Player player) {
        this.player = player;
    }


    /**
     * @param keycode keycode of pressed key
     * @return if action is performed
     */
    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.F -> controller.toggleFullscreen();
            case Input.Keys.ESCAPE -> {
                isPaused = !isPaused;

            }
            case Input.Keys.SPACE -> {
                gameIsStarted = true;
                deathScreen = false;
            }
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
        return true;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        mouse_x = screenX;
        mouse_y = screenY;
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }


}
