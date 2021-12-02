package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.math.Rectangle;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	Texture drop;
	Texture bucket1;
	OrthographicCamera camera;
	Rectangle bucket;


	@Override
	public void create() {
		//Texturen
		img = new Texture("badlogic.jpg");
		drop = new Texture("drop.png");
		bucket1 = new Texture("bucket.png");

		//Kamera
		camera = new OrthographicCamera();
		batch = new SpriteBatch();
		camera.setToOrtho(false,800,400);

		//Bucket
		bucket = new Rectangle();
		bucket.x = 800 / 2 -64 /2;
		bucket.y = 20;
		bucket.width = 64;
		bucket.height = 64;
	}
	@Override
	public void render() {
		ScreenUtils.clear(0, 0, 0, 0);
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		batch.begin(); //Start des Draw Prozess
		batch.draw(bucket1,bucket.x, bucket.y);
		batch.end(); //Ende Des Draw Prozess

		if (Gdx.input.isKeyPressed(Input.Keys.D)){
			bucket.x += 200 * Gdx.graphics.getDeltaTime();
		}
		if (Gdx.input.isKeyPressed(Input.Keys.A)) {
			bucket.x -= 200 * Gdx.graphics.getDeltaTime();
		}
		if (Gdx.input.isKeyPressed(Input.Keys.W)){
			bucket.y += 200 * Gdx.graphics.getDeltaTime();
		}
		if (Gdx.input.isKeyPressed(Input.Keys.S)){
			bucket.y -= 200 * Gdx.graphics.getDeltaTime();
		}
		// Wenn Bucket ausserhalb des Screens im X bereich
		if(bucket.x < 0) bucket.x = 0;
		if(bucket.x > 800 - 64) bucket.x = 800 - 64;
		// Wenn Bucket ausserhalb des Screens im Y bereich
		if (bucket.y < 0) bucket.y = 0;
		if(bucket.y +64 > 400) bucket.y = 400 - 64;


	}

	@Override
	public void dispose() {
		batch.dispose();
		img.dispose();
	}
}
