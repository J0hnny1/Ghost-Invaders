package com.mygdx.game.Widgets;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

public class TextFieldC extends TextField {
    public TextFieldC(String text, Skin skin) {
        super(text, skin);
    }

    public TextFieldC(String text, Skin skin, String styleName) {
        super(text, skin, styleName);
    }

    public TextFieldC(String text, TextFieldStyle style) {
        super(text, style);
    }

    public TextFieldC(String text, Skin skin, float x, float y, float width, float height, boolean visible) {
        super(text, skin);
        super.setPosition(x, y);
        super.setSize(width,height);
        super.setVisible(visible);
    }
}
