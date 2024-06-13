package kr.ac.tukorea.ge.spgp.memecatdefense.game.scene.main;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import kr.ac.tukorea.ge.spgp.framework.interfaces.IGameObject;
import kr.ac.tukorea.ge.spgp.framework.view.Metrics;

public class WaveManager implements IGameObject{
    private final EnemySpawner enemySpawner;
    protected int recentWave;
    protected float waveRemainSeconds;
    protected float maxWaveSeconds;
    private float waveWaitRemainSeconds;
    private float maxWaveWaitSeconds;
    private boolean isWait;
    private static Paint waveNumPaint;
    private static Paint waveSecondsPaint;
    private static Paint descriptionPaint;
    public WaveManager() {
        enemySpawner = new EnemySpawner(this);

        recentWave = 1;
        maxWaveSeconds = 30.9f;
        waveRemainSeconds = maxWaveSeconds;
        maxWaveWaitSeconds = 5.9f;
        waveWaitRemainSeconds = maxWaveWaitSeconds;
        isWait = true;

        waveNumPaint = new Paint();
        waveNumPaint.setColor(Color.BLACK);
        waveNumPaint.setTextSize(70f);
        waveNumPaint.setTextAlign(Paint.Align.CENTER);

        waveSecondsPaint = new Paint();
        waveSecondsPaint.setColor(Color.BLACK);
        waveSecondsPaint.setTextSize(60f);
        waveSecondsPaint.setTextAlign(Paint.Align.CENTER);

        descriptionPaint = new Paint();
        descriptionPaint.setColor(Color.BLACK);
        descriptionPaint.setTextSize(50f);
        descriptionPaint.setTextAlign(Paint.Align.CENTER);
    }
    @Override
    public void update(float elapsedSeconds){
        if(isWait){
            waveWaitRemainSeconds -= elapsedSeconds;
            if(waveWaitRemainSeconds <= 0){
                waveRemainSeconds = maxWaveSeconds;
                isWait = false;
            }
            return;
        }

        enemySpawner.update(elapsedSeconds);

        waveRemainSeconds -= elapsedSeconds;
        if(waveRemainSeconds <= 0){
            recentWave += 1;
            waveWaitRemainSeconds = maxWaveWaitSeconds;
            isWait = true;
        }
    }
    @Override
    public void draw(Canvas canvas) {
        float[] pts = Metrics.toScreen(4.5f, 0.5f);
        canvas.restore();
        canvas.drawText("Wave " + recentWave, pts[0], pts[1], waveNumPaint);
        if(isWait){
            pts = Metrics.toScreen(4.5f, 1.0f);
            canvas.drawText("" + (int)waveWaitRemainSeconds, pts[0], pts[1], waveSecondsPaint);

            pts = Metrics.toScreen(4.5f, 1.5f);
            canvas.drawText("잠시 후 웨이브가 시작됩니다.", pts[0], pts[1], descriptionPaint);
        }
        else{
            pts = Metrics.toScreen(4.5f, 1.0f);
            canvas.drawText("" + (int)waveRemainSeconds, pts[0], pts[1], waveSecondsPaint);
        }

        canvas.save();
        Metrics.concat(canvas);
    }
}
