package kr.ac.tukorea.ge.spgp.memecatdefense.game.scene.main;

import kr.ac.tukorea.ge.spgp.memecatdefense.R;
import kr.ac.tukorea.ge.spgp.framework.objects.Button;
import kr.ac.tukorea.ge.spgp.framework.res.Sound;
import kr.ac.tukorea.ge.spgp.framework.scene.Scene;

public class MainScene extends Scene {
    private static final String TAG = MainScene.class.getSimpleName();
    public enum Layer {
        bg, platform, item, player, ui, touch, controller, COUNT
    }

    public MainScene() {
        initLayers(Layer.COUNT);

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
