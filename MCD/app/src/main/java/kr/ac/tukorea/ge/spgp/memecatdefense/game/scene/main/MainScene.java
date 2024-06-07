package kr.ac.tukorea.ge.spgp.memecatdefense.game.scene.main;

import kr.ac.tukorea.ge.spgp.framework.objects.Sprite;
import kr.ac.tukorea.ge.spgp.memecatdefense.R;
import kr.ac.tukorea.ge.spgp.framework.objects.Button;
import kr.ac.tukorea.ge.spgp.framework.res.Sound;
import kr.ac.tukorea.ge.spgp.framework.scene.Scene;

public class MainScene extends Scene {
    private static final String TAG = MainScene.class.getSimpleName();
    public enum Layer {
        bg, platform, item, enemy,bullet, cat, touch,ui, controller, COUNT
    }

    private final CatSpawner catSpawner;
    private final UpgradeManager upgradeManager;
    private final EnemySpawner enemySpawner;
    public static int gold = 100;
    public static int playerHP = 100;


    public MainScene() {
        initLayers(Layer.COUNT);

        add(Layer.bg, new Sprite(R.mipmap.bg_main, 4.5f, 8, 9, 16));
        for(int i=0; i < 15; i+=2){
            Cat cat = Cat.get(i, Cat.CatType.applecat, 1);
            add(Layer.cat, cat);
        }
        catSpawner = new CatSpawner(this);
        add(Layer.controller, catSpawner);

        upgradeManager = new UpgradeManager();
        add(Layer.controller, upgradeManager);

        enemySpawner = new EnemySpawner(this);
        add(Layer.controller, enemySpawner);

        add(Layer.ui, new TextUI());


        add(Layer.touch, new Button(R.mipmap.btn_spawn, 4.5f, 12.5f, 3.0f, 3.0f, new Button.Callback(){
            public boolean onTouch(Button.Action action){
                catSpawner.purchaseCat(action == Button.Action.pressed);
                return true;
            }
        }));

        add(Layer.touch, new Button(R.mipmap.applecat_removebg, 1f, 15.f, 1.5f, 1.5f, new Button.Callback(){
            public boolean onTouch(Button.Action action){
                upgradeManager.upgradeCat(action == Button.Action.pressed, 0);
                return true;
            }
        }));

        add(Layer.touch, new Button(R.mipmap.bananacat_removebg, 2.75f, 15.f, 1.5f, 1.5f, new Button.Callback(){
            public boolean onTouch(Button.Action action){
                upgradeManager.upgradeCat(action == Button.Action.pressed, 1);
                return true;
            }
        }));

        add(Layer.touch, new Button(R.mipmap.happycat_removebg, 4.5f, 15.f, 1.5f, 1.5f, new Button.Callback(){
            public boolean onTouch(Button.Action action){
                upgradeManager.upgradeCat(action == Button.Action.pressed, 2);
                return true;
            }
        }));

        add(Layer.touch, new Button(R.mipmap.maxwellcat_removebg, 6.25f, 15.f, 1.5f, 1.5f, new Button.Callback(){
            public boolean onTouch(Button.Action action){
                upgradeManager.upgradeCat(action == Button.Action.pressed, 3);
                return true;
            }
        }));

        add(Layer.touch, new Button(R.mipmap.oiiacat_removebg, 8f, 15.f, 1.5f, 1.5f, new Button.Callback(){
            public boolean onTouch(Button.Action action){
                upgradeManager.upgradeCat(action == Button.Action.pressed, 4);
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

    @Override
    protected int getCatLayerIndex() {
        return Layer.cat.ordinal();
    }
}
