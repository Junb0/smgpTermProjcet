package kr.ac.tukorea.ge.spgp.memecatdefense.game.scene.main;
import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import kr.ac.tukorea.ge.spgp.framework.interfaces.IGameObject;

public class EnemySpawner implements IGameObject{
    private final Random random = new Random();
    private final MainScene scene;
    public EnemySpawner(MainScene scene) {this.scene = scene;}
    private static float spawnCooltime = 1.0f;
    private static float spawnElapsedSeconds = 3.f;

    @Override
    public void update(float elapsedSeconds){
        spawnElapsedSeconds += elapsedSeconds;
        if(spawnElapsedSeconds >= spawnCooltime) {
            spawnElapsedSeconds = 0.f;
            Enemy enemy = Enemy.get(100, 0.5f);
            scene.add(MainScene.Layer.enemy, enemy);
        }
    }
    @Override
    public void draw(Canvas canvas) {

    }
}
