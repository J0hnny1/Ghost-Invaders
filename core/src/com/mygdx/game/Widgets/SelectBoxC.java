package com.mygdx.game.Widgets;

import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class SelectBoxC<T> extends SelectBox<T> {
    public SelectBoxC(Skin skin) {
        super(skin);
    }


    public SelectBoxC(Skin skin, String styleName) {
        super(skin, styleName);
    }

    public SelectBoxC(SelectBoxStyle style) {
        super(style);
    }
    public SelectBoxC(Skin skin,float x, float y, float width, float height, boolean visible) {
        super(skin);
        super.setPosition(x, y);
        super.setSize(width,height);
        super.setVisible(visible);
    }

}
