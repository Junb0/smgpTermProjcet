package kr.ac.tukorea.ge.spgp.memecatdefense.game.scene.main;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import kr.ac.tukorea.ge.spgp.framework.interfaces.IRecyclable;
import kr.ac.tukorea.ge.spgp.framework.interfaces.ITouchable;
import kr.ac.tukorea.ge.spgp.framework.objects.Sprite;
import kr.ac.tukorea.ge.spgp.framework.res.BitmapPool;
import kr.ac.tukorea.ge.spgp.framework.scene.RecycleBin;
import kr.ac.tukorea.ge.spgp.memecatdefense.R;
import kr.ac.tukorea.ge.spgp.framework.interfaces.IBoxCollidable;
import kr.ac.tukorea.ge.spgp.framework.interfaces.IGameObject;
import kr.ac.tukorea.ge.spgp.framework.scene.Scene;
import kr.ac.tukorea.ge.spgp.framework.view.Metrics;
import kr.ac.tukorea.ge.spgp.memecatdefense.game.scene.main.DraggedCat;
public class Cat extends Sprite implements IRecyclable, ITouchable {
    public static final int SIZE = 188;
    private static final Random random = new Random();
    private static final String TAG = Cat.class.getSimpleName();
    public static int[] upgrade = {0,0,0,0,0};
    public static int numOfMaxwellCat = 0;
    public int slotIdx;
    public enum CatType{
        applecat, bananacat, happycat, maxwellcat, oiiacat, COUNT
    }

    public int star;
    public boolean isDragged = false;
    private float fireElapsedTime;
    private float fireCooltime;
    private float criticalPercent;

    protected CatType catType = CatType.applecat;
    public Cat(){
        super(R.mipmap.allcat_sheet_removebg);
        srcRect = new Rect();
    }

    @Override
    public void update(float elapsedSeconds){
        if(isDragged){
            return;
        }

        fireElapsedTime += elapsedSeconds;
        if(fireElapsedTime >= getFinalFireCooltime()){
            fire();
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent e){
        float[] pts = Metrics.fromScreen(e.getX(), e.getY());
        if (!dstRect.contains(pts[0], pts[1]) && !isDragged) {
            return false;
        }

        int action = e.getAction();

        if (action == MotionEvent.ACTION_DOWN){
            isDragged = true;
            Log.d(TAG, "Button.onTouch(" + System.identityHashCode(this) + ", " + e.getAction() + ", " + e.getX() + ", " + e.getY());
            makeDraggedCatFromThis();
        }
        else if(action == MotionEvent.ACTION_MOVE && isDragged){
            dstRect.set(pts[0] - 0.5f, pts[1] - 0.5f, pts[0] + 0.5f, pts[1] + 0.5f);
        }
        else if(action == MotionEvent.ACTION_UP && isDragged){
            isDragged = false;
            setDstRect(slotIdx);
        }
        return true;
    }

    public void starUp(){
        if(star < 6) {
            if(catType == CatType.maxwellcat){
                numOfMaxwellCat -= 1;
            }
            star += 1;
            CatType[] types = CatType.values();
            catType = types[random.nextInt(types.length - 1)];
            if(catType == CatType.maxwellcat){
                numOfMaxwellCat += 1;
            }
            setSrcRect(catType, star);
        }
    }

    public float getFinalFireCooltime(){
        float fireCool = 0.f;
        fireCool = (float)1 / star;
        if(catType == CatType.happycat){
            fireCool = fireCool / (1.0f + (star-1) * 0.2f);
        }
        return fireCool;
    }

    public void makeDraggedCatFromThis(){
        DraggedCat draggedCat = DraggedCat.get(slotIdx, catType, star);
        Scene scene = Scene.top();
        if (scene == null){
            Log.e(TAG, "Scene stack is empty in addToScene() " + this.getClass().getSimpleName());
            return;
        }
        if(catType == CatType.maxwellcat){
            numOfMaxwellCat -= 1;
        }
        scene.add(MainScene.Layer.touch, draggedCat);
        scene.remove(MainScene.Layer.cat, this);
    }

    private Enemy findTargetEnemy(){
        Enemy enemy;
        switch(catType){
            case happycat:
            case maxwellcat:
                enemy = findTargetFirstEnemy();
                break;
            case applecat:
            case oiiacat:
                enemy = findTargetRandomEnemy();
                break;
            case bananacat:
                enemy = findTargetHighestHPEnemy();
                break;
            default:
                enemy = findTargetFirstEnemy();
        }
        return enemy;
    }

    private Enemy findTargetFirstEnemy(){
        Enemy target = null;
        ArrayList<IGameObject> Enemies = Scene.top().objectsAt(MainScene.Layer.enemy);
        float dist = 0.f;
        // 선두의 Enemy 찾기
        for (IGameObject gameObject: Enemies){
            if(!(gameObject instanceof Enemy)) continue;
            Enemy enemy = (Enemy) gameObject;
            float enemyProgress = enemy.getTotalProgress();
            if(dist < enemyProgress){
                dist = enemyProgress;
                target = enemy;
            }
        }
        return target;
    }

    private Enemy findTargetRandomEnemy(){
        ArrayList<IGameObject> enemies = Scene.top().objectsAt(MainScene.Layer.enemy);
        if (enemies.isEmpty()) return null; // 적이 없으면 null 반환

        Random random = new Random();
        Enemy target;
        do {
            int randomIndex = random.nextInt(enemies.size());
            target = enemies.get(randomIndex) instanceof Enemy ? (Enemy)enemies.get(randomIndex) : null;
        } while (target == null); // Enemy 타입이 아닌 경우 다시 랜덤 선택

        return target;
    }

    private Enemy findTargetSecondEnemy(){
        ArrayList<IGameObject> enemies = Scene.top().objectsAt(MainScene.Layer.enemy);
        if (enemies.size() == 0) return null; // 적이 없으면 null 반환
        if (enemies.size() == 1 && enemies.get(0) instanceof Enemy) return (Enemy)enemies.get(0); // 적이 하나만 있으면 그 적 반환

        Enemy largest = null;
        Enemy secondLargest = null;

        for (IGameObject gameObject : enemies) {
            if (!(gameObject instanceof Enemy)) continue;
            Enemy enemy = (Enemy) gameObject;

            if (largest == null || enemy.getTotalProgress() > largest.getTotalProgress()) {
                secondLargest = largest;
                largest = enemy;
            } else if (secondLargest == null || enemy.getTotalProgress() > secondLargest.getTotalProgress()) {
                secondLargest = enemy;
            }
        }

        return secondLargest != null ? secondLargest : largest; // 두 번째로 큰 적이 없는 경우 가장 큰 적 반환
    }

    private Enemy findTargetHighestHPEnemy(){
        ArrayList<IGameObject> enemies = Scene.top().objectsAt(MainScene.Layer.enemy);
        if (enemies.size() == 0) return null; // 적이 없으면 null 반환

        Enemy bestEnemy = null;
        float maxHp = 0;
        float maxProgress = 0;

        for (IGameObject gameObject : enemies) {
            if (!(gameObject instanceof Enemy)) continue;
            Enemy enemy = (Enemy) gameObject;

            float currentHp = enemy.hp;
            float currentProgress = enemy.getTotalProgress();

            if (bestEnemy == null || currentHp > maxHp || (currentHp == maxHp && currentProgress > maxProgress)) {
                bestEnemy = enemy;
                maxHp = currentHp;
                maxProgress = currentProgress;
            }
        }

        return bestEnemy;
    }


    public static Cat get(int slotIdx, CatType type, int initstar){
        Cat cat = (Cat) RecycleBin.get(Cat.class);
        if (cat == null){
            cat = new Cat();
        }
        cat.init(slotIdx, type, initstar);
        return cat;
    }

    private void init(int idx, CatType type, int initstar){
        setSrcRect(type, initstar);
        setDstRect(idx);
        isDragged = false;
        star = initstar;
        slotIdx = idx;
        catType = type;
        fireElapsedTime = 0.f;
        fireCooltime = 0.1f;
        criticalPercent = 0.1f;
        if(catType == CatType.maxwellcat){
            numOfMaxwellCat += 1;
        }
        Log.d(TAG, "Cat.init(" + System.identityHashCode(this) + ", " + slotIdx + ", " + type + ", " + initstar);
    }

    private void setSrcRect(CatType type, int star) {
        int x = type.ordinal();
        int y = star - 1;
        int left = x * SIZE;
        int top = y * SIZE;
        //srcRect.set(left, top, left + SIZE, top + SIZE);
        srcRect.set(left, top, left + SIZE, top + SIZE);
    }

    private void setDstRect(int slotIdx){
        int x = slotIdx % 5;
        int y = slotIdx / 5;
        float left = 1.78f + x + x * 0.08f;
        float top = 6.42f + y + y * 0.04f;
        dstRect.set(left, top, left + 1, top + 1);
    }

    public boolean simulateCritical(){
        float finalCritPercent = criticalPercent + (float)UpgradeManager.outgameUpgradeLevels[7] * 0.03f;
        return random.nextFloat() <= finalCritPercent;
    }

    public int getFinalDamage(){
        float finalDamage;
        float baseDamage;
        float upgradeScale = 1.0f + 0.1f * (float)UpgradeManager.upgradeLevels[catType.ordinal()];
        float outgameUpgradeScale = 1.0f + 0.1f * (float)UpgradeManager.outgameUpgradeLevels[catType.ordinal()];
        float ougameFinalDamageScale = 1.0f + 0.02f * (float)UpgradeManager.outgameUpgradeLevels[5];
        switch(catType){
            case applecat:
                baseDamage = 15f;
                finalDamage = ( baseDamage * upgradeScale * outgameUpgradeScale * ougameFinalDamageScale);
                break;
            case bananacat:
                baseDamage = 12f;
                finalDamage = ( baseDamage * upgradeScale * outgameUpgradeScale * ougameFinalDamageScale);
                finalDamage = finalDamage + finalDamage * 0.01f * (20f - (WaveManager.waveRemainSeconds) * (2f + (star-1)));
                break;
            case happycat:
                baseDamage = 14f;
                finalDamage = ( (baseDamage + (baseDamage * 0.2f * star)) * upgradeScale * outgameUpgradeScale * ougameFinalDamageScale);
                break;
            case maxwellcat:
                baseDamage = 12f;
                finalDamage = ( baseDamage * upgradeScale * outgameUpgradeScale * ougameFinalDamageScale);
                break;
            case oiiacat:
                baseDamage = 8f;
                finalDamage = ( baseDamage * upgradeScale * outgameUpgradeScale * ougameFinalDamageScale);
                break;
            default:
                finalDamage = 1.0f;
        }

        finalDamage = finalDamage + finalDamage * numOfMaxwellCat * 0.1f;

        finalDamage = finalDamage * (1.0f + (random.nextFloat() * 0.2f) - 0.1f);

        return (int)finalDamage;
    }

    public void fire(){
        Enemy target = findTargetEnemy();
        if(target != null) {
            Bullet bullet = Bullet.get(this, target, simulateCritical());
            Scene.top().add(MainScene.Layer.bullet, bullet);
            fireElapsedTime = 0.f;
        }
    }

    @Override
    public void onRecycle(){

    }

    @NonNull
    @Override
    public String toString() {
        return getClass().getSimpleName() + "@" + System.identityHashCode(this) + "(" + width + "x" + height + ")";
    }

}

