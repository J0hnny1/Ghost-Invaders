package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.github.acanthite.gdx.graphics.g2d.FreeTypeSkin;
import com.mygdx.game.Widgets.*;

import java.util.regex.Pattern;

public class GUI {
    Controller controller;

    public GUI(Controller controller) {
        this.controller = controller;
    }
    final Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");

    TextField textfield_minamountofenemies, textfield_maxamountofenemies;
    CheckBox checkBox_onlyPinkGuys, godModeToggle, bouncyToggle;
    TextButtonC back_button;
    Skin default_skin;
    TextButtonC resume_button, fullscreen_button, settings_button, exit_button, apply_button, reset_button, cheatmenu_button;
    SelectBoxC<String> background_selectbox, playerTexture_selectbox;
    LabelC label_minamountofenemies, label_maxamountofenemies, label_waveCooldown, label_shootCooldown, label_playerHP, label_movementSpeed, label_ItemSpawnCooldown, label_warning;
    TextFieldC textField_waveCooldown, textField_shootCooldown, textField_playerHP, textField_movementSpeed, textField_ItemSpawnCooldown;
    Stage stage;

    public void initButtons() {
        stage = new Stage();

        int x = 600;
        int y = 600;
        //skin
        default_skin = new FreeTypeSkin(Gdx.files.internal("skin.json"));
        //Pause Menu
        resume_button = new TextButtonC("Resume", default_skin, 597, 450, 85, 25, false);
        stage.addActor(resume_button);
        fullscreen_button = new TextButtonC("Fullscreen", default_skin, 597, 450 - 35, 85, 25, false);
        stage.addActor(fullscreen_button);
        settings_button = new TextButtonC("Settings", default_skin, 597, 450 - 70, 85, 25, false);
        stage.addActor(settings_button);
        cheatmenu_button = new TextButtonC("Cheats", default_skin, 597, 450 - 105, 86, 25, false);
        stage.addActor(cheatmenu_button);
        exit_button = new TextButtonC("Exit", default_skin, 597, 450 - 140, 85, 25, false);
        stage.addActor(exit_button);
        //Settings Menu
        godModeToggle = new CheckBoxC("Godmode", default_skin, 600, y, !controller.config.getBoolean("GodMode"), false);
        stage.addActor(godModeToggle);
        back_button = new TextButtonC("Back", default_skin, 600 - 90, 60, 86, 25, false);
        stage.addActor(back_button);
        apply_button = new TextButtonC("Apply", default_skin, 600, 60, 86, 25, false);
        stage.addActor(apply_button);
        checkBox_onlyPinkGuys = new CheckBoxC("Only Pink Enemies", default_skin, 600, y -= 23, !controller.config.getBoolean("OnlyPinkEnemies"), false);
        stage.addActor(checkBox_onlyPinkGuys);
        reset_button = new TextButtonC("Reset", default_skin, 600 + 90, 60, 86, 25, false);
        stage.addActor(reset_button);
        textfield_minamountofenemies = new TextFieldC("", default_skin, 600, y  -= 23, 86, 25, false);
        textfield_minamountofenemies.setText(Integer.toString(controller.config.getInteger("MinAmountOfEnemies")));
        stage.addActor(textfield_minamountofenemies);
        label_minamountofenemies = new LabelC("Minimum Amount of Enemies per Wave: ", default_skin, 600, y -= 23, false);
        stage.addActor(label_minamountofenemies);
        textfield_maxamountofenemies = new TextFieldC(Integer.toString(controller.config.getInteger("MaxAmountOfEnemies")), default_skin, 600, 600 - 145, 86, 25, false);
        label_maxamountofenemies = new LabelC("Maximum Amount of Enemies per Wave: ", default_skin, 600, 600 - 120, false);
        stage.addActor(label_maxamountofenemies);
        stage.addActor(textfield_maxamountofenemies);
        //background selectbox
        background_selectbox = new SelectBoxC<>(default_skin, x, 450 - 30, 100, 25, false);
        background_selectbox.setItems("default", "desert", "desertCustom", "grass", "white");
        background_selectbox.setSelected(controller.config.getString("BackGroundTexture"));
        stage.addActor(background_selectbox);
        label_playerHP = new LabelC("Player HP: ", default_skin, x, 600 - 145 - 30, false);
        stage.addActor(label_playerHP);
        textField_playerHP = new TextFieldC(Integer.toString(controller.config.getInteger("PlayerHP")), default_skin, 600, 600 - 200, 86, 25, false);
        label_movementSpeed = new LabelC("Movementspeed: ", default_skin, x, 600 - 225, false);
        stage.addActor(label_movementSpeed);
        textField_movementSpeed = new TextFieldC(Integer.toString(controller.config.getInteger("MovementSpeed")), default_skin, 600, 600 - 250, 86, 25, false);
        stage.addActor(textField_movementSpeed);
        stage.addActor(textField_playerHP);
        textField_waveCooldown = new TextFieldC(Integer.toString(controller.config.getInteger("EnemyWaveCooldown")), default_skin, 600, 600 - 300, 86, 25, false);
        stage.addActor(textField_waveCooldown);
        label_waveCooldown = new LabelC("Wave Cooldown: ", default_skin, x, 600 - 277, false);
        stage.addActor(label_waveCooldown);
        textField_shootCooldown = new TextFieldC(Integer.toString(controller.config.getInteger("shootcooldown")), default_skin, 600, 600 - 345, 86, 25, false);
        stage.addActor(textField_shootCooldown);
        label_shootCooldown = new LabelC("Shootcooldown: ", default_skin, x, 600 - 325, false);
        stage.addActor(label_shootCooldown);
        label_ItemSpawnCooldown = new LabelC("Item Spawn Cooldown: ", default_skin, x, 600 - 370, false);
        stage.addActor(label_ItemSpawnCooldown);
        textField_ItemSpawnCooldown = new TextFieldC(Integer.toString(controller.config.getInteger("ItemSpawnCooldown")), default_skin, 600, 600 - 390, 86, 25, false);
        stage.addActor(textField_ItemSpawnCooldown);
        label_shootCooldown = new LabelC("Shoot Cooldown: ", default_skin, x, 600 - 325, false);
        stage.addActor(label_shootCooldown);
        textField_shootCooldown = new TextFieldC(Integer.toString(controller.config.getInteger("shootcooldown")), default_skin, 600, 600 - 348, 86, 25, false);
        stage.addActor(textField_shootCooldown);
        playerTexture_selectbox = new SelectBoxC<>(default_skin, x, 450, 100, 25, false);
        playerTexture_selectbox.setItems("Male 17-1", "Male 04-4", "Male 01-1", "Female 25-1", "Female 09-2", "Female 04-3");
        playerTexture_selectbox.setSelected(controller.config.getString("PlayerTexture"));
        stage.addActor(playerTexture_selectbox);
        label_warning = new LabelC("Cheat Options! Use at own risk!", default_skin, x, 630, false);
        label_warning.setColor(Color.RED);
        stage.addActor(label_warning);
        bouncyToggle = new CheckBoxC("Bouncy Bullets", default_skin, 600, 20, !controller.config.getBoolean("bouncyBullets"), false);
        stage.addActor(bouncyToggle);

        fullscreen_button.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                controller.toggleFullscreen();
                return true;
            }
        });
        settings_button.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                //settingsScreen = true;
                controller.gameState = Controller.GameState.SETTINGSMENU;
                setPauseMenuButtonsVisibility(false);
                setSettingsMenuButtonVisibility(true);

                return true;
            }
        });
        back_button.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                //settingsScreen = false;
                controller.gameState = Controller.GameState.PAUSEMENU;
                setPauseMenuButtonsVisibility(true);
                setCheatMenuButtonsVisibility(false);
                setSettingsMenuButtonVisibility(false);
                stage.setKeyboardFocus(null);

                return true;
            }
        });
        apply_button.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (controller.gameState == Controller.GameState.CHEATMENU) {
                    controller.config.putBoolean("OnlyPinkEnemies", !checkBox_onlyPinkGuys.isChecked());
                    controller.config.putBoolean("GodMode", !godModeToggle.isChecked());
                    controller.config.putBoolean("bouncyBullets", !bouncyToggle.isChecked());
                    if (isNumeric(textfield_minamountofenemies.getText()))
                        controller.config.putInteger("MinAmountOfEnemies", Integer.parseInt(textfield_minamountofenemies.getText()));

                    if (isNumeric(textfield_maxamountofenemies.getText()))
                        controller.config.putInteger("MaxAmountOfEnemies", Integer.parseInt(textfield_maxamountofenemies.getText()));
                    if (isNumeric(textField_playerHP.getText())) {
                        controller.config.putInteger("PlayerHP", Integer.parseInt(textField_playerHP.getText()));
                        if (Integer.parseInt(textField_playerHP.getText()) > controller.player.maxhp)
                            controller.player.maxhp = Integer.parseInt(textField_playerHP.getText()) + 15;
                    }
                    if (isNumeric(textField_movementSpeed.getText()))
                        controller.config.putInteger("MovementSpeed", Integer.parseInt(textField_movementSpeed.getText()));
                    if (isNumeric(textField_shootCooldown.getText()))
                        controller.config.putInteger("shootcooldown", Integer.parseInt(textField_shootCooldown.getText()));
                    if (isNumeric(textField_waveCooldown.getText()))
                        controller.config.putInteger("EnemyWaveCooldown", Integer.parseInt(textField_waveCooldown.getText()));
                    if (isNumeric(textField_ItemSpawnCooldown.getText()))
                        controller.config.putInteger("ItemSpawnCooldown", Integer.parseInt(textField_ItemSpawnCooldown.getText()));
                    controller.cheatsEnabled = true;
                } else {
                    controller.config.putString("PlayerTexture", playerTexture_selectbox.getSelected());
                    controller.player = new Player(controller.config.getInteger("PlayerHP"), controller.config.getInteger("PlayerHP") + 6);
                    controller.inputProcessor.setPlayer(controller.player);
                    controller.config.putString("BackGroundTexture", background_selectbox.getSelected());
                }


                controller.config.flush();
                controller.playerDeath(false);
                stage.setKeyboardFocus(null);
                return true;
            }
        });
        reset_button.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (controller.gameState == Controller.GameState.CHEATMENU) {
                    controller.initConfig(Controller.ConfigInit.CHEATS);
                    checkBox_onlyPinkGuys.setChecked(!controller.config.getBoolean("OnlyPinkEnemies"));
                    godModeToggle.setChecked(!controller.config.getBoolean("GodMode"));
                    textfield_maxamountofenemies.setText(Integer.toString(controller.config.getInteger("MaxAmountOfEnemies")));
                    textfield_minamountofenemies.setText(Integer.toString(controller.config.getInteger("MinAmountOfEnemies")));
                    textField_playerHP.setText(Integer.toString(controller.config.getInteger("PlayerHP")));
                    textField_movementSpeed.setText(Integer.toString(controller.config.getInteger("MovementSpeed")));
                    textField_shootCooldown.setText(Integer.toString(controller.config.getInteger("shootcooldown")));
                    textField_waveCooldown.setText(Integer.toString(controller.config.getInteger("EnemyWaveCooldown")));
                    textField_ItemSpawnCooldown.setText(Integer.toString(controller.config.getInteger("ItemSpawnCooldown")));
                    controller.player = new Player(controller.config.getInteger("PlayerHP"), controller.config.getInteger("PlayerHP") + 6);
                    stage.setKeyboardFocus(null);
                    controller.playerDeath(false);
                    controller.inputProcessor.setPlayer(controller.player);
                    controller.cheatsEnabled = false;
                } else {
                    controller.initConfig(Controller.ConfigInit.SETTINGS);
                    controller.playerDeath(false);
                    controller.inputProcessor.setPlayer(controller.player);
                    background_selectbox.setSelected(controller.config.getString("BackGroundTexture"));
                    playerTexture_selectbox.setSelected(controller.config.getString("PlayerTexture"));
                }

                return true;
            }
        });

        resume_button.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                controller.gameState = Controller.GameState.INGAME;
                return true;
            }
        });
        exit_button.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.exit();
                return true;
            }
        });
        cheatmenu_button.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                setPauseMenuButtonsVisibility(false);
                setSettingsMenuButtonVisibility(false);
                setCheatMenuButtonsVisibility(true);
                controller.gameState = Controller.GameState.CHEATMENU;
                return true;
            }
        });

    }
    public void setCheatMenuButtonsVisibility(boolean visible) {
        back_button.setVisible(visible);
        checkBox_onlyPinkGuys.setVisible(visible);
        godModeToggle.setVisible(visible);
        apply_button.setVisible(visible);
        reset_button.setVisible(visible);
        textfield_minamountofenemies.setVisible(visible);
        label_minamountofenemies.setVisible(visible);
        label_maxamountofenemies.setVisible(visible);
        textfield_maxamountofenemies.setVisible(visible);
        textField_playerHP.setVisible(visible);
        label_playerHP.setVisible(visible);
        label_movementSpeed.setVisible(visible);
        textField_movementSpeed.setVisible(visible);
        label_waveCooldown.setVisible(visible);
        textField_waveCooldown.setVisible(visible);
        label_ItemSpawnCooldown.setVisible(visible);
        textField_ItemSpawnCooldown.setVisible(visible);
        textField_shootCooldown.setVisible(visible);
        label_shootCooldown.setVisible(visible);
        label_warning.setVisible(visible);
        bouncyToggle.setVisible(visible);
    }

    public void setPauseMenuButtonsVisibility(boolean visible) {
        resume_button.setVisible(visible);
        fullscreen_button.setVisible(visible);
        settings_button.setVisible(visible);
        exit_button.setVisible(visible);
        cheatmenu_button.setVisible(visible);
    }

    public void setSettingsMenuButtonVisibility(boolean visible) {
        back_button.setVisible(visible);
        apply_button.setVisible(visible);
        reset_button.setVisible(visible);
        playerTexture_selectbox.setVisible(visible);
        background_selectbox.setVisible(visible);
    }

    public boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        return pattern.matcher(strNum).matches();
    }

    public void drawStage() {
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    public Stage getStage() {
        return stage;
    }


}
