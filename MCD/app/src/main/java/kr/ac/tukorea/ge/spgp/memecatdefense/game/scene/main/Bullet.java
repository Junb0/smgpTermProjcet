package kr.ac.tukorea.ge.spgp.memecatdefense.game.scene.main;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

import kr.ac.tukorea.ge.spgp.framework.interfaces.IRecyclable;
import kr.ac.tukorea.ge.spgp.framework.objects.Sprite;
import kr.ac.tukorea.ge.spgp.framework.scene.RecycleBin;
import kr.ac.tukorea.ge.spgp.framework.scene.Scene;
import kr.ac.tukorea.ge.spgp.framework.view.Metrics;
import kr.ac.tukorea.ge.spgp.memecatdefense.R;

public class Bullet extends Sprite implements IRecyclable {
    public Enemy targetEnemy;
    public static final int SIZE = 60;
    private float[] startPoint = {0.0f, 0.0f};
    private static final String TAG = Cat.class.getSimpleName();
    private int damage = 10;
    private Cat.CatType bulletType;
    private boolean isDestroy = false;
    private int animIndex = 0;
    private float animElapsedSeconds = 0.f;
    public static final float INGAME_SIZE = 0.5f;
    public Bullet(){
        super(R.mipmap.bullet_sheet);
        srcRect = new Rect();
    }
    public static Bullet get(Cat cat, Enemy target){
        Bullet bullet = (Bullet) RecycleBin.get(Bullet.class);
        if(bullet == null){
            bullet = new Bullet();
        }
        bullet.init(cat, target);
        return bullet;
    }
    @Override
    public void update(float elapsedSeconds) {
        Log.d(TAG, "Bullet.update(" + System.identityHashCode(this) + targetEnemy);
        if(isDestroy){
            updateDestroyAnimation(elapsedSeconds);
        }
        setSrcRect(bulletType);
        setDstRect(startPoint[0], startPoint[1]);

    }

    public void init(Cat owner, Enemy target){
        bulletType = owner.catType;
        targetEnemy = target;
        setStartpointBySlotIdx(owner.slotIdx);
    }

    private void updateDestroyAnimation(float elapsedSeconds){
        animElapsedSeconds += elapsedSeconds;
        if(animElapsedSeconds >= 0.1f){
            animIndex += 1;
        }
        if(animIndex > 9){
            Scene scene = Scene.top();
            if (scene == null){
                Log.e(TAG, "Scene stack is empty in addToScene() " + this.getClass().getSimpleName());
                return;
            }
            scene.remove(MainScene.Layer.bullet, this);
        }
    }

    private void setStartpointBySlotIdx(int slotIdx){
        int x = slotIdx % 5;
        int y = slotIdx / 5;
        startPoint[0] = 2.28f + x + x * 0.08f;
        startPoint[1] = 6.92f + y + y * 0.04f;
    }

    private void setSrcRect(Cat.CatType type) {
        int x = type.ordinal();
        int y = animIndex;
        int left = x * SIZE;
        int top = y * SIZE;
        //srcRect.set(left, top, left + SIZE, top + SIZE);
        srcRect.set(left, top, left + SIZE, top + SIZE);
    }

    private void setDstRect(float x, float y){
        float left = x - INGAME_SIZE / 2;
        float top = y - INGAME_SIZE / 2;
        dstRect.set(left, top, left + INGAME_SIZE, top + INGAME_SIZE);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

    }
    @Override
    public void onRecycle() {

    }
}
