package kr.ac.tukorea.ge.spgp.memecatdefense.game.scene.main;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;


import java.util.Random;

import kr.ac.tukorea.ge.spgp.framework.interfaces.IRecyclable;
import kr.ac.tukorea.ge.spgp.framework.objects.Sprite;
import kr.ac.tukorea.ge.spgp.framework.res.Sound;
import kr.ac.tukorea.ge.spgp.framework.scene.RecycleBin;
import kr.ac.tukorea.ge.spgp.framework.scene.Scene;
import kr.ac.tukorea.ge.spgp.framework.view.Metrics;
import kr.ac.tukorea.ge.spgp.framework.util.MathHelper;
import kr.ac.tukorea.ge.spgp.memecatdefense.R;

public class Bullet extends Sprite implements IRecyclable {
    public Enemy targetEnemy;
    private static final Random random = new Random();
    private static final String TAG = Bullet.class.getSimpleName();
    public static final int SIZE = 60;
    public static final float INGAME_SIZE = 0.3f;
    private float[] startPoint = {0.0f, 0.0f};
    private float[] pos = {0.0f, 0.0f};
    private int damage = 10;
    private Cat.CatType bulletType;
    private boolean isDestroy;
    private int animIndex;
    private float animElapsedSeconds;
    private float bulletMoveProgress;
    private float bulletSpeed;
    private boolean isCrit;

    public Bullet(){
        super(R.mipmap.bullet_sheet);
        srcRect = new Rect();
    }
    public static Bullet get(Cat cat, Enemy target, boolean isCrit){
        Bullet bullet = (Bullet) RecycleBin.get(Bullet.class);
        if(bullet == null){
            bullet = new Bullet();
        }
        bullet.init(cat, target, isCrit);
        return bullet;
    }
    public void init(Cat owner, Enemy target, boolean isCrit){
        bulletType = owner.catType;
        targetEnemy = target;
        setStartpointBySlotIdx(owner.slotIdx);
        pos = startPoint;
        isDestroy = false;
        animIndex = 0;
        animElapsedSeconds = 0.f;
        bulletMoveProgress = 0.f;
        bulletSpeed = 1f;
        this.isCrit = isCrit;
        damage = owner.getFinalDamage();
        if(isCrit){
            if(owner.catType == Cat.CatType.applecat){
                damage += (int)(damage * (0.5f + 0.3f + owner.star * 0.1f));
            }
            damage += (int)(damage * 0.5f);
        }
        setSrcRect(bulletType);
    }
    @Override
    public void update(float elapsedSeconds) {
        if(targetEnemy.getIsDead()){
            isDestroy = true;
        }
        if(isDestroy){
            updateDestroyAnimation(elapsedSeconds);
            return;
        }

        moveBullet(elapsedSeconds);
    }

    private void moveBullet(float elapsedSeconds){
        bulletMoveProgress += elapsedSeconds;
        float[] targetPos = targetEnemy.getPosition();
        float[] norm = MathHelper.normalize(targetPos[0]-startPoint[0], targetPos[1]-startPoint[1]);
        pos[0] = startPoint[0] + norm[0] * bulletMoveProgress * bulletSpeed;
        pos[1] = startPoint[1] + norm[1] * bulletMoveProgress * bulletSpeed;

        float dist = MathHelper.distance(pos[0], pos[1], targetPos[0], targetPos[1]);
        if(bulletMoveProgress > 10.f || dist <= 0.5f){
            targetEnemy.getDamage(damage, isCrit);
            if(bulletType == Cat.CatType.oiiacat){
                if(random.nextFloat() <= 0.05f) {
                    targetEnemy.stunSeconds = 3.0f;
                }
            }

            switch(random.nextInt(3)){
                case 0:
                    Sound.playEffect(R.raw.hit1);
                    break;
                case 1:
                    Sound.playEffect(R.raw.hit2);
                    break;
                case 2:
                    Sound.playEffect(R.raw.hit3);
                    break;

                default:
                    Sound.playEffect(R.raw.hit1);
            }
            isDestroy = true;
        }

        //float[] targetPos = targetEnemy.getPosition();
        //pos[0] = (1.0f - bulletMoveProgress) * startPoint[0] + bulletMoveProgress * targetPos[0];
        //pos[1] = (1.0f - bulletMoveProgress) * startPoint[1] + bulletMoveProgress * targetPos[1];
        setDstRect(pos[0], pos[1]);
    }

    private void updateDestroyAnimation(float elapsedSeconds){
        animElapsedSeconds += elapsedSeconds;
        if(animElapsedSeconds >= 0.02f){
            animIndex += 1;
            setSrcRect(bulletType);
            animElapsedSeconds = 0.f;
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
