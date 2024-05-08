package kr.ac.tukorea.ge.spgp.memecatdefense.game.scene.main;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.Choreographer;
import android.view.MotionEvent;
import android.view.View;

import kr.ac.tukorea.ge.spgp.framework.interfaces.IGameObject;
import kr.ac.tukorea.ge.spgp.framework.res.BitmapPool;
import kr.ac.tukorea.ge.spgp.framework.scene.Scene;
import kr.ac.tukorea.ge.spgp.memecatdefense.R;
import kr.ac.tukorea.ge.spgp.memecatdefense.game.scene.main.Cat;
import kr.ac.tukorea.ge.spgp.framework.view.Metrics;

public class TextUI implements IGameObject {
    protected static Paint goldPaint;
    protected static Paint upgradePaint;
    protected static Paint purchasePaint;
    protected static Paint upgradePricePaint;

    protected Bitmap bitmap;
    private static final String TAG = TextUI.class.getSimpleName();
    public TextUI(){
        bitmap = BitmapPool.get(R.mipmap.applecat);
        goldPaint = new Paint();
        goldPaint.setColor(Color.argb(255, 166, 124, 0));
        goldPaint.setTextSize(70f);

        upgradePaint = new Paint();
        upgradePaint.setColor(Color.WHITE);
        upgradePaint.setTextSize(60f);
        upgradePaint.setTextAlign(Paint.Align.CENTER);

        upgradePricePaint = new Paint();
        upgradePricePaint.setColor(Color.BLACK);
        upgradePricePaint.setTextSize(45f);
        upgradePricePaint.setTextAlign(Paint.Align.CENTER);

        purchasePaint = new Paint();
        purchasePaint.setColor(Color.WHITE);
        purchasePaint.setTextSize(50f);
        purchasePaint.setTextAlign(Paint.Align.CENTER);
    }
    @Override
    public void update(float elapsedSeconds){

    }

    @Override
    public void draw(Canvas canvas) {

        Scene scene = Scene.top();
        int gold = MainScene.gold;
        float[] pts = Metrics.toScreen(0.4f, 12.5f);
        canvas.restore();
        canvas.drawText("Gold: " + gold, pts[0], pts[1] , goldPaint);

        pts = Metrics.toScreen(4.5f, 13.1f);
        int price = CatSpawner.price;
        canvas.drawText("" + price, pts[0], pts[1] , purchasePaint);

        for(int i = 0; i < 5; i++){
            int level = UpgradeManager.upgradeLevels[i] + 1;
            pts = Metrics.toScreen(1f + i * 1.75f, 15.3f);
            canvas.drawText("LV." + level, pts[0], pts[1] , upgradePaint);
        }

        for(int i = 0; i < 5; i++){
            price = UpgradeManager.getPrice(i);
            pts = Metrics.toScreen(1f + i * 1.75f, 16f);
            canvas.drawText("" + price, pts[0], pts[1] , upgradePricePaint);
        }

        canvas.save();
        Metrics.concat(canvas);
    }

}
