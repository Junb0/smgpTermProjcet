package kr.ac.tukorea.ge.spgp.memecatdefense.game.scene.main;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

import kr.ac.tukorea.ge.spgp.framework.activity.GameActivity;
import kr.ac.tukorea.ge.spgp.framework.interfaces.IRecyclable;
import kr.ac.tukorea.ge.spgp.framework.objects.Sprite;
import kr.ac.tukorea.ge.spgp.framework.scene.RecycleBin;
import kr.ac.tukorea.ge.spgp.framework.scene.Scene;
import kr.ac.tukorea.ge.spgp.framework.view.Metrics;
import kr.ac.tukorea.ge.spgp.memecatdefense.app.MainActivity;
import kr.ac.tukorea.ge.spgp.memecatdefense.app.MemeCatDefenseActivity;
import kr.ac.tukorea.ge.spgp.memecatdefense.game.scene.main.DamageIndicator;
import kr.ac.tukorea.ge.spgp.memecatdefense.R;
import kr.ac.tukorea.ge.spgp.memecatdefense.game.scene.paused.GameOverScene;
import kr.ac.tukorea.ge.spgp.memecatdefense.game.scene.paused.PausedScene;

public class Enemy extends Sprite implements IRecyclable {
    private static final String TAG = Enemy.class.getSimpleName();
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
    private float totalProgress = 0.0f; // 총 진행한 거리
    protected static Paint hpPaint;
    protected static Paint hpStrokePaint;
    private boolean isDead = false;
    private float dropGold;
    private float dropDia;
    private int enemyType;
    private int hpOrigin;

    public Enemy(){
        super(R.mipmap.enemy_square2);
        srcRect = new Rect();
        hpPaint = new Paint();
        hpPaint.setColor(Color.WHITE);
        hpPaint.setTextSize(37f);
        hpPaint.setTextAlign(Paint.Align.CENTER);
        hpStrokePaint = new Paint();
        hpStrokePaint.setStyle(Paint.Style.STROKE);
        hpStrokePaint.setStrokeWidth(8f);
        hpStrokePaint.setColor(Color.BLACK);
        hpStrokePaint.setTextSize(37f);
        hpStrokePaint.setTextAlign(Paint.Align.CENTER);

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
                totalProgress += dy;
                break;
            case right:
                float dx = speed * elapsedSeconds;
                position[0] += dx;
                if (position[0] >= secondCornerPoint[0]){
                    dir = Dir.down;
                    position[0] = secondCornerPoint[0];
                }
                setDstRect(position[0], position[1]);
                totalProgress += dx;
                break;
            case down:
                dy = speed * elapsedSeconds;
                position[1] += dy;
                if (position[1] >= endPoint[1]){
                    EnemyArrive();
                }
                setDstRect(position[0], position[1]);
                totalProgress += dy;
                break;
        }

    }

    public float getTotalProgress(){
        return totalProgress;
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        float[] pts = Metrics.toScreen(position[0], position[1] + 0.1f);
        canvas.restore();

        canvas.drawText(getHPText(hp), pts[0], pts[1] , hpStrokePaint);
        canvas.drawText(getHPText(hp), pts[0], pts[1] , hpPaint);

        canvas.save();
        Metrics.concat(canvas);
    }

    public String getHPText(int hp){
        String str;
        if(hp > 1000000){
            str = hp/1000000 +"M";
            if(hp < 10000000){
                str = hp/1000000 + "." + hp%1000000 / 100000+"M";
            }
            return str;
        }
        if(hp > 1000){
            str = hp/1000 + "K";
            if(hp < 10000){
                str = hp/1000 + "." + hp%1000 / 100+"K";
            }
            return str;
        }
        str = Integer.toString(hp);
        return str;
    }

    public void getDamage(float damage, boolean isCrit){
        if(isDead){
            return;
        }
        Scene scene = Scene.top();
        hp -= damage;
        DamageIndicator DI = DamageIndicator.get((int)damage, this, isCrit);
        scene.add(MainScene.Layer.ui, DI);

        if(hp <= 0) {
            int gol = (int)(dropGold * (enemyType + 1));
            MainScene.gold += (int)(gol + gol * 0.1f * (float)UpgradeManager.outgameUpgradeLevels[6]);
            MainScene.dia += (int)(dropDia);
            if(enemyType >= 1){
                enemyType -= 1;
                hp = hpOrigin / 2;
                hpOrigin = hp;
                setSrcRect();
                return;
            }
            isDead = true;

            scene.remove(MainScene.Layer.enemy, this);
        }
    }

    public static Enemy get(int hp, float speed, int type){
        Enemy enemy = (Enemy) RecycleBin.get(Enemy.class);
        if (enemy == null){
            enemy = new Enemy();
        }
        enemy.init(hp, speed, type);
        return enemy;
    }

    private void EnemyArrive(){
        Scene scene = Scene.top();

        if (scene == null){
            Log.e(TAG, "Scene stack is empty in addToScene() " + this.getClass().getSimpleName());
            return;
        }
        MainScene.playerHP -= 10;

        if(MainScene.playerHP <= 0){
            new GameOverScene().push();
        }
        scene.remove(MainScene.Layer.enemy, this);
    }

    private void init(int hp, float speed, int type){
        position[0]=startingPoint[0];
        position[1]=startingPoint[1];
        setDstRect(position[0], position[1]);
        totalProgress = 0.f;
        dir = Dir.up;
        hpOrigin = hp;
        this.hp = hp;
        this.speed = speed;
        isDead = false;
        dropGold = 15f;
        dropDia = 10f;
        enemyType = type;
        setSrcRect();
    }

    public boolean getIsDead(){
        return isDead;
    }

    public float[] getPosition(){
        return position;
    }

    private void setSrcRect() {
        int x = enemyType;
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
        isDead = true;

    }

}
