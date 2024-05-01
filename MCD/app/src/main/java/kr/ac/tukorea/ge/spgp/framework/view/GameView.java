package kr.ac.tukorea.ge.spgp.framework.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.Choreographer;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

import kr.ac.tukorea.ge.spgp2024.dragonflight.BuildConfig;
import kr.ac.tukorea.ge.spgp2024.framework.interfaces.IGameObject;
import kr.ac.tukorea.ge.spgp2024.framework.scene.Scene;


//import android.util.AttributeSet;

public class GameView extends View implements Choreographer.FrameCallback {
    private static final String TAG = GameView.class.getSimpleName();

    //////////////////////////////////////////////////
    // Debug Helper
    private Paint borderPaint;
    private Paint fpsPaint;
    private void initDebugObjects() {
        borderPaint = new Paint();
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(0.1f);
        borderPaint.setColor(Color.RED);

        fpsPaint = new Paint();
        fpsPaint.setColor(Color.BLUE);
        fpsPaint.setTextSize(100f);
    }

    //////////////////////////////////////////////////
    // View Constructor
    public GameView(Context context) {
        super(context);

        setFullScreen();

        if (BuildConfig.DEBUG) {
            initDebugObjects();
        }

        initGame();
        scheduleUpdate();
    }

    //////////////////////////////////////////////////
    // Global Variable (static member) for Resources
    public static Resources res;
    private final ArrayList<IGameObject> gameObjects = new ArrayList<>();

    private void initGame() {
        res = getResources();
    }

    //////////////////////////////////////////////////
    // Game Loop
    private long previousNanos = 0;
    private float elapsedSeconds;
    private void scheduleUpdate() {
        Choreographer.getInstance().postFrameCallback(this);
    }

    @Override
    public void doFrame(long nanos) {
        long elapsedNanos = nanos - previousNanos;
        elapsedSeconds = elapsedNanos / 1_000_000_000f;
        if (previousNanos != 0) {
            update();
        }
        invalidate();
        if (isShown()) {
            scheduleUpdate();
        }
        previousNanos = nanos;
    };
    private void update() {
        Scene scene = Scene.top();
        if (scene != null) {
            scene.update(elapsedSeconds);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Scene scene = Scene.top();
        if (scene == null) {
            return;
        }
        canvas.save();
        Metrics.concat(canvas);
        if (BuildConfig.DEBUG) {
            canvas.drawRect(Metrics.borderRect, borderPaint);
        }
        scene.draw(canvas);
        canvas.restore();

        if (BuildConfig.DEBUG) {
            int fps = (int) (1.0f / elapsedSeconds);
            canvas.drawText("FPS: " + fps, 100f, 200f, fpsPaint);
        }
    }

    //////////////////////////////////////////////////
    // Utilities

    public void setFullScreen() {
        int flags = View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                View.SYSTEM_UI_FLAG_FULLSCREEN |
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        setSystemUiVisibility(flags);
    }

    //////////////////////////////////////////////////
    // Coordinate System

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        Metrics.onSize(w, h);
    }

    //////////////////////////////////////////////////
    // Touch Event
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Scene scene = Scene.top();
        if (scene != null) {
            boolean handled = scene.onTouch(event);
            if (handled) return true;
        }
        return super.onTouchEvent(event);
    }

    public void onBackPressed() {
        Scene scene = Scene.top();
        if (scene == null) {
            Scene.finishActivity();
            return;
        }
        boolean handled = scene.onBackPressed();
        if (handled) return;

        Scene.pop();
    }
}
