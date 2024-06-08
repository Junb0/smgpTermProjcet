package kr.ac.tukorea.ge.spgp.memecatdefense.game.scene.main;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import kr.ac.tukorea.ge.spgp.framework.interfaces.IRecyclable;
import kr.ac.tukorea.ge.spgp.framework.objects.Sprite;
import kr.ac.tukorea.ge.spgp.framework.view.Metrics;
import kr.ac.tukorea.ge.spgp.memecatdefense.R;

public class Bullet extends Sprite implements IRecyclable {
    public Enemy targetEnemy;
    public Bullet(){
        super(R.mipmap.bullet_sheet);
        srcRect = new Rect();
    }
    @Override
    public void update(float elapsedSeconds) {

    }

    public void init(Cat owner){
        
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
    }
    @Override
    public void onRecycle() {

    }
}
