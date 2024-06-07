package kr.ac.tukorea.ge.spgp.memecatdefense.game.scene.main;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import kr.ac.tukorea.ge.spgp.framework.interfaces.IRecyclable;
import kr.ac.tukorea.ge.spgp.framework.objects.Sprite;
import kr.ac.tukorea.ge.spgp.framework.scene.RecycleBin;
import kr.ac.tukorea.ge.spgp.framework.view.Metrics;
import kr.ac.tukorea.ge.spgp.memecatdefense.R;

public class Bullet extends Sprite implements IRecyclable {
    public Enemy targetEnemy;
    private float[] startPoint = {0.0f, 0.0f};
    private int damage = 10;
    private Cat.CatType bulletType;
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

    }

    public void init(Cat owner, Enemy target){
        bulletType = owner.catType;
        targetEnemy = target;


    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
    }
    @Override
    public void onRecycle() {

    }
}
