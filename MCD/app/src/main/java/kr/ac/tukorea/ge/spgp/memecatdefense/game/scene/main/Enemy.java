package kr.ac.tukorea.ge.spgp.memecatdefense.game.scene.main;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

import kr.ac.tukorea.ge.spgp.framework.interfaces.IRecyclable;
import kr.ac.tukorea.ge.spgp.framework.objects.Sprite;
import kr.ac.tukorea.ge.spgp.framework.scene.RecycleBin;
import kr.ac.tukorea.ge.spgp.framework.view.Metrics;
import kr.ac.tukorea.ge.spgp.memecatdefense.R;

public class Enemy extends Sprite implements IRecyclable {
    public enum Dir{
        up, right, down, COUNT
    }
    protected Dir dir = Dir.up;
    public static final int SIZE = 160;
    public static final float INGAME_SIZE = 0.7f;
    public int hp = 100;
    public float speed = 1.f;
    private float[] position = {0.f, 0.f};
    private static final float[] startingPoint = {0.425f, 10.5f}; // 시작지점
    private static final float[] firstCornerPoint = {0.425f, 5.5f};    // 첫번째 코너
    private static final float[] secondCornerPoint = {8.46f, 5.5f}; // 두번째 코너
    private static final float[] endPoint = {8.46f, 10.5f}; // 끝 지점
    protected static Paint hpPaint;

    public Enemy(){
        super(R.mipmap.enemy_square);
        srcRect = new Rect();
        hpPaint = new Paint();
        hpPaint.setColor(Color.WHITE);
        hpPaint.setTextSize(40f);
        hpPaint.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    public void update(float elapsedSeconds) {
        switch (dir){
            case up:
                float dy = speed * elapsedSeconds;
                position[1] -= dy;
                if (position[1] <= firstCornerPoint[1]){
                    dir = Dir.right;
                    position[1] = firstCornerPoint[1];
                }
                setDstRect(position[0], position[1]);
                break;
            case right:
                float dx = speed * elapsedSeconds;
                position[0] += dx;
                if (position[0] >= secondCornerPoint[0]){
                    dir = Dir.down;
                    position[0] = secondCornerPoint[0];
                }
                setDstRect(position[0], position[1]);
                break;
            case down:
                dy = speed * elapsedSeconds;
                position[1] += dy;
                if (position[1] >= endPoint[1]){
                    break;
                }
                setDstRect(position[0], position[1]);
                break;
        }

    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        float[] pts = Metrics.toScreen(position[0], position[1] + 0.1f);
        canvas.restore();

        canvas.drawText("" + hp, pts[0], pts[1] , hpPaint);

        canvas.save();
        Metrics.concat(canvas);
    }

    public static Enemy get(int hp, float speed){
        Enemy enemy = (Enemy) RecycleBin.get(Enemy.class);
        if (enemy == null){
            enemy = new Enemy();
        }
        enemy.init(hp, speed);
        return enemy;
    }

    private void init(int hp, float speed){
        setSrcRect();
        position[0]=startingPoint[0];
        position[1]=startingPoint[1];
        setDstRect(position[0], position[1]);
        dir = Dir.up;
        this.hp = hp;
        this.speed = speed;
    }

    private void setSrcRect() {
        int x = 0;
        int y = 0;
        int left = x * SIZE;
        int top = y * SIZE;
        srcRect.set(left, top, left + SIZE, top + SIZE);
    }

    private void setDstRect(float x, float y){
        float left = x - INGAME_SIZE / 2;
        float top = y - INGAME_SIZE / 2;
        dstRect.set(left, top, left + INGAME_SIZE, top + INGAME_SIZE);
    }
    @Override
    public void onRecycle() {

    }
}