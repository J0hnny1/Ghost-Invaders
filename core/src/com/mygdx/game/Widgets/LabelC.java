package com.mygdx.game.Widgets;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;


public class LabelC extends Label {
    public LabelC(CharSequence text, Skin skin) {
        super(text, skin);
    }

    public LabelC(CharSequence text, Skin skin, String styleName) {
        super(text, skin, styleName);
    }

    public LabelC(CharSequence text, Skin skin, String fontName, Color color) {
        super(text, skin, fontName, color);
    }

    public LabelC(CharSequence text, Skin skin, String fontName, String colorName) {
        super(text, skin, fontName, colorName);
    }

    public LabelC(CharSequence text, LabelStyle style) {
        super(text, style);
    }
    public LabelC(CharSequence text, Skin skin,float x, float y, boolean visible) {
        super(text, skin);
        super.setPosition(x, y);
        super.setVisible(visible);
    }
}
