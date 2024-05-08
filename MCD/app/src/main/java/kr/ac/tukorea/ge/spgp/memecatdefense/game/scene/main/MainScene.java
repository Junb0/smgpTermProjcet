package kr.ac.tukorea.ge.spgp.memecatdefense.game.scene.main;

import kr.ac.tukorea.ge.spgp.framework.objects.Sprite;
import kr.ac.tukorea.ge.spgp.memecatdefense.R;
import kr.ac.tukorea.ge.spgp.framework.objects.Button;
import kr.ac.tukorea.ge.spgp.framework.res.Sound;
import kr.ac.tukorea.ge.spgp.framework.scene.Scene;

public class MainScene extends Scene {
    private static final String TAG = MainScene.class.getSimpleName();
    public enum Layer {
        bg, platform, item, cat, ui, touch, controller, COUNT
    }

    public final int[] slot = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14 };

    public MainScene() {
        initLayers(Layer.COUNT);

        add(Layer.bg, new Sprite(R.mipmap.bg_main, 4.5f, 8, 9, 16));
        for(int i=0; i < 15; i+=2){
            Cat cat = Cat.get(i, Cat.CatType.applecat, 1);
            add(Layer.cat, cat);
        }


        add(Layer.touch, new Button(R.mipmap.btn_spawn, 4.5f, 12.5f, 3.0f, 3.0f, new Button.Callback(){
            public boolean onTouch(Button.Action action){
                return true;
            }
        }));


    }

    @Override
    protected void onStart() {
    }

    @Override
    protected void onPause() {
        Sound.pauseMusic();
    }

    @Override
    protected void onResume() {
        Sound.resumeMusic();
    }

    @Override
    protected void onEnd() {
        Sound.stopMusic();
    }

    @Override
    protected int getTouchLayerIndex() {
        return Layer.touch.ordinal();
    }
}
