package com.theironyard;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation;

public class MyGdxGame extends ApplicationAdapter {
    SpriteBatch batch;
    TextureRegion down, up, right, left;
    boolean faceRight = true;

    Animation walk;
    Animation walkUp;
    Animation walkDown;

    static final float MAX_VELOCITY = 300;

    static final float MAX_EXTRA_VELOCITY = 500;

    float time;

    float x, y, xv, yv;

    static final int WIDTH = 16;

    static final int HEIGHT = 16;

    static final int DRAW_WIDTH = WIDTH * 3;

    static final int DRAW_HEIGHT = HEIGHT * 3;


    @Override
    public void create() {
        batch = new SpriteBatch();
        Texture tiles = new Texture("tiles.png");
        TextureRegion[][] grid = TextureRegion.split(tiles, WIDTH, HEIGHT);
        down = grid[6][0];
        up = grid[6][1];
        right = grid[6][3];
        left = new TextureRegion(right);
        left.flip(true, false);

        walk = new Animation(0.2f, grid[6][2], grid[6][3]);
        walkUp = new Animation(0.2f, grid[6][1], grid[7][1]);
        walkDown = new Animation(0.2f,grid[6][0], grid[7][0]);

//    tree = new TextureRegion(tiles, 0, 8, 16,16);
//        for(int i =0;i<tress.length;i++);
    }

    @Override
    public void render() {
        time += Gdx.graphics.getDeltaTime();
        move();

        TextureRegion img;
        img = right;

        if (xv != 0) {
            img = walk.getKeyFrame(time, true);

        } else if (xv < 0){
            img = down;
        }
        else if (yv > 0) {
            img = walkUp.getKeyFrame(time, false);

        } else if (yv < 0){
            img = walkDown.getKeyFrame(time, true);
        }
        Gdx.gl.glClearColor(0.4f, 0, 0.5f, 1);

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        if (faceRight) {
            batch.draw(img, x, y, DRAW_WIDTH, DRAW_HEIGHT);
        } else {
            batch.draw(img, x, y, DRAW_WIDTH * -1, DRAW_HEIGHT);
        }
        batch.end();
    }

    public float decelerate(float velocity) {
        float deceleration = 0.95f;
        velocity *= deceleration;
        if (Math.abs(velocity) < 1) {
            velocity = 0;
        }
        return velocity;
    }


    public void move() {
        if (Gdx.input.isKeyPressed(Input.Keys.UP) && (Gdx.input.isKeyPressed(Input.Keys.SPACE))) {
            yv = MAX_VELOCITY;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            yv = MAX_VELOCITY * -1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            xv = MAX_VELOCITY;
            faceRight = true;

        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            xv = MAX_VELOCITY * -1;
            faceRight = false;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            yv = MAX_VELOCITY;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP) && (Gdx.input.isKeyPressed(Input.Keys.SPACE))) {
            yv = MAX_EXTRA_VELOCITY;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) && (Gdx.input.isKeyPressed(Input.Keys.SPACE))) {
            yv = MAX_EXTRA_VELOCITY * -1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && (Gdx.input.isKeyPressed(Input.Keys.SPACE))) {
            xv = MAX_EXTRA_VELOCITY;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && (Gdx.input.isKeyPressed(Input.Keys.SPACE))) {
            yv = MAX_EXTRA_VELOCITY * -1;
        }

        y = y + (yv * Gdx.graphics.getDeltaTime());
        x = x + (xv * Gdx.graphics.getDeltaTime());

        if (x < 0) {
            x = Gdx.graphics.getWidth();
        }
        if (y < 0) {
            y = Gdx.graphics.getHeight();
        }
        if (y > Gdx.graphics.getHeight()) {
            y = 0;
        }
        if (x > Gdx.graphics.getWidth()) {
            x = 0;
        }


        yv = decelerate(yv);
        xv = decelerate(xv);


    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
