package com.mygdx.game;

import com.badlogic.gdx.Input;

public class InputProcessor implements com.badlogic.gdx.InputProcessor {
    Player player;
    Controller controller;

    public InputProcessor(Player player, Controller controller) {
        this.player = player;
        this.controller = controller;
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
                if (controller.gameState == Controller.GameState.INGAME)
                    controller.gameState = Controller.GameState.PAUSEMENU;
                else if (controller.gameState == Controller.GameState.PAUSEMENU)
                    controller.gameState = Controller.GameState.INGAME;
                if (controller.gameState == Controller.GameState.SETTINGSMENU)
                    controller.gameState = Controller.GameState.PAUSEMENU;
            }
            case Input.Keys.SPACE -> {
                if (controller.gameState == Controller.GameState.STARTSCREEN)
                    controller.gameState = Controller.GameState.INGAME;
                if (controller.gameState == Controller.GameState.DEATHSCREEN) {
                    controller.gameState = Controller.GameState.INGAME;
                    controller.start_time_run = System.currentTimeMillis();
                }
            }
        }


        return true;
    }


    @Override
    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.W -> player.setPlayerdirection(Player.Direction.FRONT);
            case Input.Keys.A -> player.setPlayerdirection(Player.Direction.LEFT);
            case Input.Keys.S -> player.setPlayerdirection(Player.Direction.BACK);
            case Input.Keys.D -> player.setPlayerdirection(Player.Direction.RIGHT);

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
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }


}
