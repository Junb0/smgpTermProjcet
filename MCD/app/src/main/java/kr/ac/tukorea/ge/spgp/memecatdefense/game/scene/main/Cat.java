package kr.ac.tukorea.ge.spgp.memecatdefense.game.scene.main;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;

import androidx.annotation.NonNull;

import java.util.ArrayList;
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
    public int slotIdx = 0;
    public enum CatType{
        applecat, bananacat, happycat, maxwellcat, oiiacat, COUNT
    }

    public int star = 1;
    public boolean isDragged = false;
    private float fireElapsedTime = 0.0f;
    private float fireCooltime = 1.0f;

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
        if(fireElapsedTime >= fireCooltime){
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
            star += 1;
            CatType[] types = CatType.values();
            catType = types[random.nextInt(types.length - 1)];
            setSrcRect(catType, star);
        }
    }

    public void makeDraggedCatFromThis(){
        DraggedCat draggedCat = DraggedCat.get(slotIdx, catType, star);
        Scene scene = Scene.top();
        if (scene == null){
            Log.e(TAG, "Scene stack is empty in addToScene() " + this.getClass().getSimpleName());
            return;
        }
        scene.add(MainScene.Layer.touch, draggedCat);
        scene.remove(MainScene.Layer.cat, this);
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

    public int getFinalDamage(){

        return 0;
    }

    public void fire(){

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

