package kr.ac.tukorea.ge.spgp.memecatdefense.game.scene.main;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.MotionEvent;

import java.util.ArrayList;

import kr.ac.tukorea.ge.spgp.framework.objects.Sprite;
import kr.ac.tukorea.ge.spgp.memecatdefense.R;
import kr.ac.tukorea.ge.spgp.framework.interfaces.IBoxCollidable;
import kr.ac.tukorea.ge.spgp.framework.interfaces.IGameObject;
import kr.ac.tukorea.ge.spgp.framework.scene.Scene;
import kr.ac.tukorea.ge.spgp.framework.view.Metrics;
public class Cat extends Sprite{
    public enum CatType{
        applecat, bananacat, happycat, maxwellcat, oiiacat, COUNT
    }

    protected CatType catType = CatType.applecat;
    public Cat(){
        super(R.mipmap.applecat);

    }
}
