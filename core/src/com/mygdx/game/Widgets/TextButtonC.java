package com.mygdx.game.Widgets;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class TextButtonC extends TextButton {
    public TextButtonC(String text, Skin skin) {
        super(text, skin);
    }

    public TextButtonC(String text, Skin skin, String styleName) {
        super(text, skin, styleName);
    }

    public TextButtonC(String text, TextButtonStyle style) {
        super(text, style);
    }

    public TextButtonC(String text, Skin style, float x, float y, float witdh, float height, boolean visible ) {
        super(text, style);
        super.setPosition(x, y);
        super.setSize(witdh,height);
        super.setVisible(visible);
    }

}
