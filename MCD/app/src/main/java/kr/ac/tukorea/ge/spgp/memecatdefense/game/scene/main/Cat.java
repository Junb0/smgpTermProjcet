package kr.ac.tukorea.ge.spgp.memecatdefense.game.scene.main;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;

import androidx.annotation.NonNull;

import java.util.ArrayList;

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
public class Cat extends Sprite implements IRecyclable, ITouchable {
    public static final int SIZE = 188;
    private static final String TAG = Cat.class.getSimpleName();
    public int slotIdx = 0;
    public enum CatType{
        applecat, bananacat, happycat, maxwellcat, oiiacat, COUNT
    }

    public int star = 1;
    public boolean isDragged = false;

    protected CatType catType = CatType.applecat;
    public Cat(){
        super(R.mipmap.allcat_sheet_removebg);
        srcRect = new Rect();
    }

    @Override
    public boolean onTouchEvent(MotionEvent e){
        float[] pts = Metrics.fromScreen(e.getX(), e.getY());
        if (!dstRect.contains(pts[0], pts[1])) {
            return false;
        }
        int action = e.getAction();

        if (action == MotionEvent.ACTION_DOWN){
            isDragged = true;
            Log.d(TAG, "Button.onTouch(" + System.identityHashCode(this) + ", " + e.getAction() + ", " + e.getX() + ", " + e.getY());
        }
        else if(action == MotionEvent.ACTION_MOVE && isDragged){
            dstRect.set(pts[0], pts[1], pts[0]+1, pts[1] + 1);
        }
        else if(action == MotionEvent.ACTION_UP && isDragged){
            isDragged = false;
        }
        return true;
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
    }

    private void setSrcRect(CatType type, int star) {
        int x = type.ordinal();
        int y = star - 1;
        int left = x * SIZE;
        int top = y * SIZE;
        //srcRect.set(left, top, left + SIZE, top + SIZE);
        srcRect.set(0, 0, SIZE, SIZE);
    }

    private void setDstRect(int slotIdx){
        int x = slotIdx % 5;
        int y = slotIdx / 5;
        float left = 1.78f + x + x * 0.08f;
        float top = 6.42f + y + y * 0.04f;
        dstRect.set(left, top, left + 1, top + 1);
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

