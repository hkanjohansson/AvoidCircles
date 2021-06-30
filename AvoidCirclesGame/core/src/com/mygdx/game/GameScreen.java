package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.Timer;

import java.util.Iterator;

public class GameScreen implements Screen {

    final MyGdxAvoidCircles game;
    Texture playerImage;
    Texture enemyImage;
    Timer timer;
    int scoreTime;
    OrthographicCamera camera;
    Rectangle player;
    Array<Rectangle> enemies;
    long lastEnemySpawn;
    int enemyDirection;

    public GameScreen(MyGdxAvoidCircles game) {
        this.game = game;
        playerImage = new Texture("player.png");
        enemyImage = new Texture("enemy.png");

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        // create a Rectangle to logically represent the bucket
        player = new Rectangle();
        player.x = 800 / 2 - 64 / 2; // center the bucket horizontally
        player.y = 20; // bottom left corner of the bucket is 20 pixels above
        // the bottom screen edge
        player.width = 64;
        player.height = 64;

        enemyDirection = 0;
        enemies = new Array<>();
        spawnEnemy();

        lastEnemySpawn = 0;
    }

    private void spawnEnemy() {
        Rectangle enemy = new Rectangle();
        enemyDirection = MathUtils.random(4);
        System.out.println("Hej1");
        switch (enemyDirection) {


            case 0:
                // top

                enemy.x = MathUtils.random(0, 800 - 32);
                System.out.println("Hej2: " + enemy.x);
                enemy.y = 0;
                break;

            case 1:
                // bottom
                enemy.x = MathUtils.random(0, 800 - 32);
                System.out.println("Hej3: " + enemy.x);
                enemy.y = 480;
                break;

            case 2:
                // left
                enemy.x = 0;
                enemy.y = MathUtils.random(0, 480 - 32);
                System.out.println("Hej4: " + enemy.y);
                break;

            case 3:
                // right
                enemy.x = 800;
                enemy.y = MathUtils.random(0, 480 - 32);
                System.out.println("Hej5: " + enemy.y);
                break;

        }

        enemy.width = 32;
        enemy.height = 32;
        enemies.add(enemy);
        lastEnemySpawn = TimeUtils.nanoTime();

    }


    @Override
    public void render(float delta) {
        ScreenUtils.clear(1, 1, 1, 1);
        // clear the screen with a dark blue color. The
        // arguments to clear are the red, green
        // blue and alpha component in the range [0,1]
        // of the color to be used to clear the screen.


        // tell the camera to update its matrices.
        camera.update();

        // tell the SpriteBatch to render in the
        // coordinate system specified by the camera.
        game.batch.setProjectionMatrix(camera.combined);

        // begin a new batch and draw the bucket and
        // all drops
        game.batch.begin();
        game.font.draw(game.batch, "Score: " + scoreTime + " seconds", 0, 480);
        game.font.setColor(0, 0, 0, 1);
        game.batch.draw(playerImage, player.x, player.y);

        for (Rectangle e : enemies) {
            game.batch.draw(enemyImage, e.x, e.y);
        }

        game.batch.end();

        // process user input
        if (Gdx.input.isTouched()) {
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            player.x = touchPos.x - 64 / 2;
            player.y = touchPos.y - 64 / 2;
        }

        // make sure the player stays within the screen bounds
        if (player.x < 0)
            player.x = 0;
        if (player.x > 800 - 44)
            player.x = 800 - 44;

        if (player.y < 0)
            player.y = 0;
        if (player.y > 480 - 64)
            player.y = 480 - 64;

        // check if we need to create a new raindrop
        if (TimeUtils.nanoTime() - lastEnemySpawn > 1000000000)
            spawnEnemy();

        // move the raindrops, remove any that are beneath the bottom edge of
        // the screen or that hit the bucket. In the later case we play back
        // a sound effect as well.
        Iterator<Rectangle> iter = enemies.iterator();
        while (iter.hasNext()) {
            Rectangle enemy = iter.next();
            //enemy.x += Gdx.graphics.getDeltaTime();
            switch (enemyDirection) {
                case 0:
                    // top
                    enemy.y -= 200 * Gdx.graphics.getDeltaTime();
                    if (enemy.y + 32 < 0)
                        iter.remove();
                    break;


                case 1:
                    // bottom

                    enemy.y += 200 * Gdx.graphics.getDeltaTime();
                    if (enemy.y - 32 > 480)
                        iter.remove();
                    break;

                case 2:
                    // left
                    System.out.println(enemyDirection);
                    enemy.x += 200 * Gdx.graphics.getDeltaTime();
                    if (enemy.x - 32 < 0)
                        iter.remove();
                    break;

                case 3:
                    // right
                    System.out.println(enemyDirection);
                    enemy.x -= 200 * Gdx.graphics.getDeltaTime();
                    if (enemy.x + 32 > 800)
                        iter.remove();
                    break;

            }

            //enemy.y -= 200 * Gdx.graphics.getDeltaTime(); // TODO - Check which direction the enemies are coming from
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

    }
}
