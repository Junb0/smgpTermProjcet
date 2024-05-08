package kr.ac.tukorea.ge.spgp.memecatdefense.game.scene.main;
import android.graphics.Canvas;
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
import kr.ac.tukorea.ge.spgp.memecatdefense.game.scene.main.Cat;
public class CatSpawner implements IGameObject{
    public static int price = 20;
    private final Random random = new Random();
    private final MainScene scene;
    public CatSpawner(MainScene scene) {this.scene = scene;}

    public boolean purchaseCat(boolean purchase){
        if(!purchase){
            return false;
        }
        if(MainScene.gold < price){
            return false;
        }
        Cat.CatType[] types = Cat.CatType.values();
        ArrayList<IGameObject> cats = scene.objectsAt(MainScene.Layer.cat);
        ArrayList<Integer> emptySlots = new ArrayList<Integer>(Arrays.asList(0,1,2,3,4,5,6,7,8,
                9,10,11,12,13,14));

        int count = cats.size();
        for(int i = count - 1; i >= 0; i--){
            Cat cat = (Cat)cats.get(i);
            emptySlots.remove((Integer)cat.slotIdx);
        }
        if(emptySlots.isEmpty()){
            return false;
        }
        int randomIdx = emptySlots.get(random.nextInt(emptySlots.size()));
        Cat.CatType randomType = types[random.nextInt(types.length - 1)];


        Cat cat = Cat.get(randomIdx, randomType, 1);
        scene.add(MainScene.Layer.cat, cat);

        MainScene.gold -= price;

        price += 10;

        return true;
    }
    @Override
    public void update(float elapsedSeconds){

    }
    @Override
    public void draw(Canvas canvas) {

    }
}
