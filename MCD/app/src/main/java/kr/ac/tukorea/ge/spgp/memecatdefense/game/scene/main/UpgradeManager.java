package kr.ac.tukorea.ge.spgp.memecatdefense.game.scene.main;
import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import kr.ac.tukorea.ge.spgp.framework.interfaces.IGameObject;

public class UpgradeManager implements IGameObject{
    public static int[] upgradeLevels = {0, 0, 0, 0, 0};
    public static int[] outgameUpgradeLevels = {0, 0, 0, 0, 0, 0, 0, 0, 0};
    public UpgradeManager() {}

    public boolean upgradeCat(boolean purchase, int idx){
        if(!purchase){
            return false;
        }
        if(MainScene.gold < getPrice(idx)){
            return false;
        }

        MainScene.gold -= getPrice(idx);

        upgradeLevels[idx] += 1;

        return true;
    }
    public static int getPrice(int idx){
        return 100 + upgradeLevels[idx] * 10;
    }
    @Override
    public void update(float elapsedSeconds){

    }
    @Override
    public void draw(Canvas canvas) {

    }
}
