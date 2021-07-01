package com.mygdx.game;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;

public class ScoreScreen implements Screen {

    final MyGdxAvoidCircles game;
    OrthographicCamera camera;
    Screen gameScreen;
    int score;
    int level;

    public ScoreScreen(MyGdxAvoidCircles game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        gameScreen = game.getScreen();
        score = ((GameScreen) gameScreen).score;
        level = ((GameScreen) gameScreen).level;
    }


    @Override
    public void render(float delta) {
        ScreenUtils.clear(1, 1, 1, 1);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.font.draw(game.batch, "You reached level: " + level + " and", 200, 400);
        game.font.draw(game.batch, "this is your total score: " + score + " points", 200, 350);
        game.font.draw(game.batch, "THANKS FOR PLAYING!", 200, 300);
        game.batch.end();
    }

    @Override
    public void show() {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
