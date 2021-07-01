package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;

public class MainMenuScreen implements Screen {

    final MyGdxAvoidCircles game;
    OrthographicCamera camera;
    Texture imgMainMenu;
    Texture imgWelcome;

    public MainMenuScreen(MyGdxAvoidCircles game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        imgMainMenu = new Texture("main-menu.png");
        imgWelcome = new Texture("welcome.png");
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(1, 1, 1, 1);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.batch.draw(imgMainMenu, 150, 10);
        game.batch.draw(imgWelcome, 120, 250);
        game.batch.end();

        if (Gdx.input.isTouched()) {
            game.setScreen(new GameScreen(game));
        }

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
        imgWelcome.dispose();
        imgMainMenu.dispose();
    }
}
