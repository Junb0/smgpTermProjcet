package kr.ac.tukorea.ge.spgp.memecatdefense.game.scene.main;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.style.UpdateAppearance;
import android.util.Log;

import kr.ac.tukorea.ge.spgp.framework.interfaces.IGameObject;
import kr.ac.tukorea.ge.spgp.framework.interfaces.IRecyclable;
import kr.ac.tukorea.ge.spgp.framework.objects.Sprite;
import kr.ac.tukorea.ge.spgp.framework.scene.RecycleBin;
import kr.ac.tukorea.ge.spgp.framework.scene.Scene;
import kr.ac.tukorea.ge.spgp.framework.view.Metrics;
import kr.ac.tukorea.ge.spgp.memecatdefense.R;
import kr.ac.tukorea.ge.spgp.memecatdefense.game.scene.main.Enemy;

public class DamageIndicator implements IRecyclable, IGameObject {
    private Enemy owner;
    private static final String TAG = DamageIndicator.class.getSimpleName();
    private int damage;
    private float[] position = {0.f, 0.f};
    protected Paint damagePaint;
    private float DIElapsedSeconds;
    private int alpha;
    private static final float lifeTime = 1.0f;
    private boolean ownerIsDead;

    public DamageIndicator(){
        damagePaint = new Paint();
        damagePaint.setColor(Color.WHITE);

    }

    public static DamageIndicator get(int damage, Enemy owner){
        DamageIndicator DI = (DamageIndicator) RecycleBin.get(DamageIndicator.class);
        if(DI == null){
            DI = new DamageIndicator();
        }
        DI.init(damage, owner);
        return DI;
    }

    private void init(int damage, Enemy owner){
        this.damage = damage;
        this.owner = owner;
        position[0] = owner.getPosition()[0];
        position[1] = owner.getPosition()[1];
        position[1] -= 0.5f;
        DIElapsedSeconds = 0.f;
        alpha = 255;
        ownerIsDead = false;
    }

    @Override
    public void update(float elapsedSeconds) {
        DIElapsedSeconds += elapsedSeconds;
        if(DIElapsedSeconds >= lifeTime){
            Destroy();
            return;
        }
        if(!owner.getIsDead() && !ownerIsDead){
            position[0] = owner.getPosition()[0];
            position[1] = owner.getPosition()[1] - 0.3f - DIElapsedSeconds * 0.2f;
        }
        else{
            position[1] -=  elapsedSeconds * 0.2f;
            ownerIsDead = true;
        }
        alpha = (int)((1.0f - DIElapsedSeconds) * 255);

    }

    public void Destroy(){
        Scene scene = Scene.top();
        if (scene == null){
            Log.e(TAG, "Scene stack is empty in removeToScene() " + this.getClass().getSimpleName());
            return;
        }
        scene.remove(MainScene.Layer.ui, this);
    }

    @Override
    public void draw(Canvas canvas) {
        float[] pts = Metrics.toScreen(position[0], position[1]);
        canvas.restore();
        damagePaint.setTextAlign(Paint.Align.CENTER);
        damagePaint.setStyle(Paint.Style.STROKE);
        damagePaint.setStrokeWidth(8);
        damagePaint.setColor(Color.BLACK);
        damagePaint.setAlpha(alpha);
        damagePaint.setTextSize(45f);

        canvas.drawText(""+damage, pts[0], pts[1], damagePaint);
        damagePaint.setStyle(Paint.Style.FILL);
        damagePaint.setColor(Color.WHITE);
        canvas.drawText(""+damage, pts[0], pts[1], damagePaint);

        canvas.save();
        Metrics.concat(canvas);

    }

    @Override
    public void onRecycle() {

    }
}
