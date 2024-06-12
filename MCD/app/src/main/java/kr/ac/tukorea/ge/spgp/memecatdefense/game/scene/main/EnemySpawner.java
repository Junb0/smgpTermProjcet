package kr.ac.tukorea.ge.spgp.memecatdefense.game.scene.main;
import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import kr.ac.tukorea.ge.spgp.framework.interfaces.IGameObject;
import kr.ac.tukorea.ge.spgp.framework.scene.Scene;

public class EnemySpawner implements IGameObject{
    private final Random random = new Random();
    private WaveManager waveManager;
    public EnemySpawner(WaveManager waveManager) {this.waveManager = waveManager;}
    private static float spawnCooltime = 2f;
    private static float spawnElapsedSeconds = 0.f;

    @Override
    public void update(float elapsedSeconds){
        spawnElapsedSeconds += elapsedSeconds;
        if(spawnElapsedSeconds >= spawnCooltime) {
            Scene scene = Scene.top();
            spawnElapsedSeconds = 0.f;
            Enemy enemy = Enemy.get(500, 0.5f);
            scene.add(MainScene.Layer.enemy, enemy);
        }
    }
    @Override
    public void draw(Canvas canvas) {

    }
}
