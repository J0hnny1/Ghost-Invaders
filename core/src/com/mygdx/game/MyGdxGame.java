package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	Texture drop;
	Texture bucket;

	@Override

	public void create() {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		drop = new Texture("drop.png");
		bucket = new Texture("bucket.png");
	}

	@Override
	public void render() {
		ScreenUtils.clear(1, 0, 0, 1);
		batch.begin();
		batch.draw(drop, 0, 0);
		batch.draw(bucket,10,10);
		batch.end();
	}

	@Override
	public void dispose() {
		batch.dispose();
		img.dispose();
	}
}
