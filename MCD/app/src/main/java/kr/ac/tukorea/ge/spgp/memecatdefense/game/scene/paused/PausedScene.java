package kr.ac.tukorea.ge.spgp.memecatdefense.game.scene.paused;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;

import kr.ac.tukorea.ge.spgp.memecatdefense.R;
import kr.ac.tukorea.ge.spgp.framework.activity.GameActivity;
import kr.ac.tukorea.ge.spgp.framework.objects.Button;
import kr.ac.tukorea.ge.spgp.framework.objects.Sprite;
import kr.ac.tukorea.ge.spgp.framework.scene.Scene;
import kr.ac.tukorea.ge.spgp.framework.view.Metrics;
import kr.ac.tukorea.ge.spgp.memecatdefense.app.MainActivity;
import kr.ac.tukorea.ge.spgp.memecatdefense.app.MemeCatDefenseActivity;
import kr.ac.tukorea.ge.spgp.memecatdefense.game.scene.main.MainScene;

public class PausedScene extends Scene {
    public enum Layer {
        bg, ui, touch, COUNT
    }
    private float angle;
    public PausedScene() {
        initLayers(Layer.COUNT);
        float w = Metrics.width, h = Metrics.height;
        float cx = w / 2, cy = h / 2;
        add(Layer.bg, new Sprite(R.mipmap.trans_50b, cx, cy, w, h));
        add(Layer.bg, new Sprite(R.mipmap.white, cx, cy, 6.75f, 10f));
        add(Layer.touch, new Button(R.mipmap.btn_resume_n, 8.0f, 1.0f, 2.0f, 0.75f, new Button.Callback() {
            @Override
            public boolean onTouch(Button.Action action) {
                pop();
                return false;
            }
        }));
        String msg;
        if(MainScene.highscoreOrigin < MainScene.highscore){
            msg = "\n최고기록 경신!";
        }
        else{
            msg = "";
        }
        add(Layer.touch, new Button(R.mipmap.btn_exit_n, 4.5f, 10.0f, 2.667f, 1f, new Button.Callback() {
            @Override
            public boolean onTouch(Button.Action action) {
                new AlertDialog.Builder(GameActivity.activity)
                        .setTitle("Confirm")
                        .setMessage("게임을 종료할까요?\n 획득 다이아: " + MainScene.dia + msg)
                        .setNegativeButton("No", null)
                        .setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(MemeCatDefenseActivity.activity, MainActivity.class);
                                intent.putExtra("dia", MainScene.dia);
                                intent.putExtra("highscore", MainScene.highscore);
                                MemeCatDefenseActivity.activity.setResult(Activity.RESULT_OK, intent);
                                finishActivity();
                            }
                        })
                        .create()
                        .show();
                return false;
            }
        }));
    }

    @Override
    public void update(float elapsedSeconds) {
        super.update(elapsedSeconds);
    }

    @Override
    protected int getTouchLayerIndex() {
        return Layer.touch.ordinal();
    }

    @Override
    public boolean isTransparent() {
        return true;
    }
}
