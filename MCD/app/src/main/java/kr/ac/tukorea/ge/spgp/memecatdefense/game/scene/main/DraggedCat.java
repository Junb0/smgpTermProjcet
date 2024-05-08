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
import kr.ac.tukorea.ge.spgp.memecatdefense.game.scene.main.Cat;
public class DraggedCat extends Sprite implements IRecyclable, ITouchable {
    public static final int SIZE = 188;

    private static final String TAG = Cat.class.getSimpleName();
    public int slotIdx = 0;


    public int star = 1;
    public boolean isDragged = false;

    protected Cat.CatType catType = Cat.CatType.applecat;
    public DraggedCat(){
        super(R.mipmap.allcat_sheet_removebg);
        srcRect = new Rect();
    }

    @Override
    public void update(float elapsedSeconds){
        if(isDragged){
            return;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent e){
        float[] pts = Metrics.fromScreen(e.getX(), e.getY());
        int action = e.getAction();

        if(action == MotionEvent.ACTION_MOVE){
            dstRect.set(pts[0] - 0.5f, pts[1] - 0.5f, pts[0] + 0.5f, pts[1] + 0.5f);
        }
        else if(action == MotionEvent.ACTION_UP){
            if( !mergeCat(getSlotIdx(pts[0], pts[1])) ) {
                makeCatFromThis();
            }
        }
        return true;
    }

    public static DraggedCat get(int slotIdx, Cat.CatType type, int initstar){
        DraggedCat cat = (DraggedCat) RecycleBin.get(DraggedCat.class);
        if (cat == null){
            cat = new DraggedCat();
        }
        cat.init(slotIdx, type, initstar);
        return cat;
    }

    public void makeCatFromThis(){
        Cat cat = Cat.get(slotIdx, catType, star);
        Scene scene = Scene.top();
        if (scene == null){
            Log.e(TAG, "Scene stack is empty in addToScene() " + this.getClass().getSimpleName());
            return;
        }
        scene.add(MainScene.Layer.cat, cat);
        scene.remove(MainScene.Layer.touch, this);
    }

    public boolean mergeCat(int idx){
        if(idx == -1){
            return false;
        }
        Scene scene = Scene.top();
        if (scene == null){
            Log.e(TAG, "Scene stack is empty in addToScene() " + this.getClass().getSimpleName());
            return false;
        }
        ArrayList<IGameObject> cats = scene.objectsAt(MainScene.Layer.cat);
        int count = cats.size();
        if(count == 0){
            return false;
        }
        for(int i = count - 1; i >= 0; i--){
            Cat cat = (Cat)cats.get(i);
            if(cat.slotIdx == idx && cat.catType == catType && cat.star == star){
                cat.starUp();
                scene.remove(MainScene.Layer.touch, this);
                return true;
            }
        }
        return false;
    }

    public int getSlotIdx(float x, float y){
        int calcX = (int)((x - 1.78f) / 1.08f); // 각 사각형의 너비와 x 간격을 고려
        int calcY = (int)((y - 6.42f) / 1.04f); // 각 사각형의 높이와 y 간격을 고려

        // 유효한 범위 체크
        if (calcX < 0 || calcX >= 5 || calcY < 0 || calcY >= 3) {
            return -1; // 좌표가 유효 범위를 벗어나면 -1 리턴
        }

        // slotIdx 계산
        int idx = calcY * 5 + calcX;
        return idx;
    }

    private void init(int idx, Cat.CatType type, int initstar){
        setSrcRect(type, initstar);
        setDstRect(idx);
        isDragged = false;
        star = initstar;
        slotIdx = idx;
        catType = type;
    }

    private void setSrcRect(Cat.CatType type, int star) {
        int x = type.ordinal();
        int y = star - 1;
        int left = x * SIZE;
        int top = y * SIZE;
        srcRect.set(left, top, left + SIZE, top + SIZE);
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

