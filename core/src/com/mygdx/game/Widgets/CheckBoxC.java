package com.mygdx.game.Widgets;

import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class CheckBoxC extends CheckBox {
    public CheckBoxC(String text, Skin skin) {
        super(text, skin);
    }

    public CheckBoxC(String text, Skin skin, String styleName) {
        super(text, skin, styleName);
    }

    public CheckBoxC(String text, CheckBoxStyle style) {
        super(text, style);
    }

    public CheckBoxC(String text, Skin skin,float x, float y, float width, float height, boolean ischecked, boolean visible) {
        super(text, skin);
        super.setPosition(x, y);
        super.setSize(width,height);
        super.setVisible(visible);
        super.setChecked(ischecked);
    }
    public CheckBoxC(String text, Skin skin,float x, float y,boolean ischecked, boolean visible) {
        super(text, skin);
        super.setPosition(x, y);
        super.setVisible(visible);
        super.setChecked(ischecked);
    }
}
