package com.mygdx.game;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameScreen implements Screen {

    final MyGdxAvoidCircles game;

    public GameScreen(MyGdxAvoidCircles game) {
        this.game = game;
    }




    @Override
    public void render(float delta) {
        ScreenUtils.clear(1,1,1,1);
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
