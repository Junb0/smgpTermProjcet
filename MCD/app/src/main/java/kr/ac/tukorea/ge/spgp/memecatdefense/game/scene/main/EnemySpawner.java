package kr.ac.tukorea.ge.spgp.memecatdefense.game.scene.main;
import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.lang.Math;

import kr.ac.tukorea.ge.spgp.framework.interfaces.IGameObject;
import kr.ac.tukorea.ge.spgp.framework.scene.Scene;

public class EnemySpawner implements IGameObject{
    private final Random random = new Random();
    private WaveManager waveManager;
    private static float spawnCooltime = 2.5f;
    private static float spawnElapsedSeconds = 0.f;
    public EnemySpawner(WaveManager waveManager) {
        this.waveManager = waveManager;
        random.setSeed(System.currentTimeMillis());
        spawnCooltime = 2.5f;
        spawnElapsedSeconds = 0.f;
    }


    @Override
    public void update(float elapsedSeconds){
        spawnElapsedSeconds += elapsedSeconds;
        if(spawnElapsedSeconds >= spawnCooltime) {
            Scene scene = Scene.top();
            spawnElapsedSeconds = 0.f + random.nextFloat() * 0.5f;
            Enemy enemy;
            int enemyType = getEnemyType();
            int enemyHP = getEnemyHP(enemyType);
            enemy = Enemy.get(enemyHP, 0.5f, enemyType);
            scene.add(MainScene.Layer.enemy, enemy);
        }
    }
    @Override
    public void draw(Canvas canvas) {

    }
    public void newWave(){

    }
    private int getEnemyHP(int enemyType){
        float hp;
        int wave = waveManager.recentWave;
        if(wave < 10) {
            hp = 100f + (wave - 1) * 100f;
        }
        else if (wave < 20){
            hp = 1000f + (wave - 10) * 350f;
        }
        else{
            float scale = (float)Math.pow(2, (double)(wave-20) * 0.1);
            hp = 5000f + (wave - 20) * 700f * scale ;
        }

        switch (enemyType){
            case 1:
                hp = hp * 2.0f;
                break;
            case 2:
                hp = hp * 4.0f;
                break;
            default:
        }

        return (int)hp;
    }

    private int getEnemyType(){
        int type = 0;
        float waveScale;
        float rndVal;
        int wave = waveManager.recentWave;

        if(wave < 10){
            waveScale = 1.0f;
        }
        else if (wave < 20){
            waveScale = 1.2f;
        }
        else{
            waveScale = 1.4f;
        }

        rndVal = random.nextFloat() * waveScale;
        if(wave < 5){
            rndVal = Math.min(rndVal, 0.89f);
        }

        if(rndVal > 0.85f){
            type = 1;
        }
        if(rndVal > 0.95f){
            type = 2;
        }
        return type;
    }
}
