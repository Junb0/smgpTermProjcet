package kr.ac.tukorea.ge.spgp.memecatdefense.game.scene.main;

import android.content.Intent;
import android.os.Bundle;

import kr.ac.tukorea.ge.spgp.framework.activity.GameActivity;
import kr.ac.tukorea.ge.spgp.framework.objects.Sprite;
import kr.ac.tukorea.ge.spgp.memecatdefense.R;
import kr.ac.tukorea.ge.spgp.framework.objects.Button;
import kr.ac.tukorea.ge.spgp.framework.res.Sound;
import kr.ac.tukorea.ge.spgp.framework.scene.Scene;
import kr.ac.tukorea.ge.spgp.memecatdefense.game.scene.paused.GameOverScene;
import kr.ac.tukorea.ge.spgp.memecatdefense.game.scene.paused.PausedScene;

public class MainScene extends Scene {
    private static final String TAG = MainScene.class.getSimpleName();
    public enum Layer {
        bg, platform, item, enemy, cat, touch, bullet, ui, controller, COUNT
    }

    private final CatSpawner catSpawner;
    private final UpgradeManager upgradeManager;
    private final WaveManager waveManager;
    public static int gold = 100;
    public static int playerHP = 100;
    public static int dia = -1;
    public static int highscore = -1;
    public static int highscoreOrigin = -1;
    public static boolean isGameOver = false;


    public MainScene() {
        dia = 0;
        highscore = 0;
        isGameOver = false;
        Intent intent = GameActivity.activity.getIntent();
        Bundle extras = intent.getExtras();
        if(extras != null){
            for(int i = 0; i < 9; i++){
                UpgradeManager.outgameUpgradeLevels[i] = extras.getIntArray("upgrade")[i];
            }
            dia = 0;
            highscoreOrigin = extras.getInt("highscore", 0);
        }

        playerHP = 100 + UpgradeManager.outgameUpgradeLevels[8] * 20;
        gold = 100;

        initLayers(Layer.COUNT);

        add(Layer.bg, new Sprite(R.mipmap.bg_main, 4.5f, 8, 9, 16));
        for(int i=0; i < 0; i+=2){
            Cat cat = Cat.get(i, Cat.CatType.applecat, 1);
            add(Layer.cat, cat);
        }
        catSpawner = new CatSpawner(this);
        add(Layer.controller, catSpawner);

        upgradeManager = new UpgradeManager();
        add(Layer.controller, upgradeManager);

        waveManager = new WaveManager();
        add(Layer.controller, waveManager);

        add(Layer.ui, new TextUI());


        add(Layer.touch, new Button(R.mipmap.btn_spawn, 4.5f, 12.5f, 3.0f, 3.0f, new Button.Callback(){
            public boolean onTouch(Button.Action action){
                catSpawner.purchaseCat(action == Button.Action.pressed);
                return false;
            }
        }));

        add(Layer.touch, new Button(R.mipmap.applecat_removebg, 1f, 15.f, 1.5f, 1.5f, new Button.Callback(){
            public boolean onTouch(Button.Action action){
                upgradeManager.upgradeCat(action == Button.Action.pressed, 0);
                return false;
            }
        }));

        add(Layer.touch, new Button(R.mipmap.bananacat_removebg, 2.75f, 15.f, 1.5f, 1.5f, new Button.Callback(){
            public boolean onTouch(Button.Action action){
                upgradeManager.upgradeCat(action == Button.Action.pressed, 1);
                return false;
            }
        }));

        add(Layer.touch, new Button(R.mipmap.happycat_removebg, 4.5f, 15.f, 1.5f, 1.5f, new Button.Callback(){
            public boolean onTouch(Button.Action action){
                upgradeManager.upgradeCat(action == Button.Action.pressed, 2);
                return false;
            }
        }));

        add(Layer.touch, new Button(R.mipmap.maxwellcat_removebg, 6.25f, 15.f, 1.5f, 1.5f, new Button.Callback(){
            public boolean onTouch(Button.Action action){
                upgradeManager.upgradeCat(action == Button.Action.pressed, 3);
                return false;
            }
        }));

        add(Layer.touch, new Button(R.mipmap.oiiacat_removebg, 8f, 15.f, 1.5f, 1.5f, new Button.Callback(){
            public boolean onTouch(Button.Action action){
                upgradeManager.upgradeCat(action == Button.Action.pressed, 4);
                return false;
            }
        }));

        add(Layer.touch, new Button(R.mipmap.btn_pause, 8.0f, 1.0f, 1.0f, 1.0f, new Button.Callback() {
            @Override
            public boolean onTouch(Button.Action action) {
                new PausedScene().push();
                return false;
            }
        }));


    }

    @Override
    public void update(float elapsedSeconds) {
        if(!isGameOver) {
            super.update(elapsedSeconds);
        }
        if(isGameOver){
            new GameOverScene().push();
        }
    }

    @Override
    protected void onStart() {
        Sound.playMusic(R.raw.newmain);
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
    public boolean onBackPressed(){
        new PausedScene().push();
        return true;
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
