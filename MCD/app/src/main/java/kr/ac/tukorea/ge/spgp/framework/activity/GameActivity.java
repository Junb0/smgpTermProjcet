package kr.ac.tukorea.ge.spgp.framework.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import kr.ac.tukorea.ge.spgp.framework.view.GameView;
import kr.ac.tukorea.ge.spgp.memecatdefense.app.MainActivity;

public class GameActivity extends AppCompatActivity {

    public static GameActivity activity;
    private GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        gameView = new GameView(this);
        //gameView.setFullScreen();
        setContentView(gameView);
        //new MainScene().push();

        getOnBackPressedDispatcher().addCallback(onBackPressedCallback);
    }

    private final OnBackPressedCallback onBackPressedCallback = new OnBackPressedCallback(true) {
        @Override
        public void handleOnBackPressed() {
            gameView.onBackPressed();
        }
    };


    @Override
    protected void onPause() {
        gameView.pauseGame();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        gameView.resumeGame();
    }

    @Override
    protected void onDestroy() {
        gameView.destroyGame();
        activity = null;
        super.onDestroy();
    }

    public void toMain(){
        Intent intent = new Intent(GameActivity.this, MainActivity.class);
        intent.putExtra("dia", 1111);
        setResult(Activity.RESULT_OK, intent);
        Log.d("GameActivity", "Setting result with dia = 1111");
        finish();
    }
}