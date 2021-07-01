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
    long startTime;
    float scoreTime;
    OrthographicCamera camera;
    Rectangle player;
    Array<Rectangle> enemies;
    Array<Integer> enemiesDirections;
    long lastEnemySpawn;
    float deltaTimeDist;

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

        enemies = new Array<>();
        enemiesDirections = new Array<>();
        spawnEnemy();

        lastEnemySpawn = 0;
        deltaTimeDist = 0;

        scoreTime = 0;
        startTime = TimeUtils.millis();
    }

    private void spawnEnemy() {
        Rectangle enemy = new Rectangle();
        enemy.width = 32;
        enemy.height = 32;

        int enemyDirection = MathUtils.random(4);
        enemiesDirections.add(enemyDirection);
        if (enemyDirection == 0) {
            enemy.x = MathUtils.random(0, 800 - 32);
            enemy.y = 480;
        }

        if (enemyDirection == 1) {
            enemy.x = MathUtils.random(0, 800 - 32);
            enemy.y = 0;
        }

        if (enemyDirection == 2) {
            enemy.y = MathUtils.random(0, 480 - 32);
            enemy.x = 0;
        }

        if (enemyDirection == 3) {
            enemy.y = MathUtils.random(0, 480 - 32);
            enemy.x = 800;
        }

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

        // check if we need to create a new enemy
        if (TimeUtils.nanoTime() - lastEnemySpawn > 2000000000){
            spawnEnemy();
        }


        // move the raindrops, remove any that are beneath the bottom edge of
        // the screen or that hit the bucket. In the later case we play back
        // a sound effect as well.
        Iterator<Rectangle> iter = enemies.iterator();
        Iterator<Integer> iterDirections = enemiesDirections.iterator();

        float tempTime = 0;
        while (iter.hasNext() && iterDirections.hasNext()) {
            Rectangle enemy = iter.next();
            int enemyDirection = iterDirections.next(); // TODO - Assign direction value to the enemy objects

            System.out.println("Enemy direction: " + enemyDirection);


            deltaTimeDist = 200 * Gdx.graphics.getDeltaTime();


            // TODO - Move in assigned direction
            if (enemyDirection == 0) {
                enemy.y -= deltaTimeDist;
            }

            if (enemyDirection == 1) {
                enemy.y += deltaTimeDist;
            }


            if (enemyDirection == 2) {
                enemy.x += deltaTimeDist;
            }


            if (enemyDirection == 3) {
                enemy.x -= deltaTimeDist;
            }

            // TODO - Check if on screen
            if (enemy.y < 0 && enemyDirection == 0) {
                iter.remove();
                iterDirections.remove();
            }

            if (enemy.y > 480 && enemyDirection == 1) {
                iter.remove();
                iterDirections.remove();
            }

            if (enemy.x < 0 && enemyDirection == 2) {
                iter.remove();
                iterDirections.remove();
            }

            if (enemy.x > 800 && enemyDirection == 3) {
                iter.remove();
                iterDirections.remove();
            }

            if (!enemy.overlaps(player)) {
                //scoreTime += (TimeUtils.timeSinceMillis(startTime))/1000;
                scoreTime += Gdx.graphics.getDeltaTime();
                tempTime += Gdx.graphics.getDeltaTime();
                System.out.println(tempTime);
            } else {
                System.exit(-1);
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
        playerImage.dispose();
        enemyImage.dispose();
    }
}
