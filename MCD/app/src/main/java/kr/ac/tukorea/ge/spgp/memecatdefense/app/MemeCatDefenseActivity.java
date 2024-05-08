package kr.ac.tukorea.ge.spgp.memecatdefense.app;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import kr.ac.tukorea.ge.spgp.framework.view.Metrics;
import kr.ac.tukorea.ge.spgp.memecatdefense.BuildConfig;
import kr.ac.tukorea.ge.spgp.memecatdefense.R;
import kr.ac.tukorea.ge.spgp.framework.activity.GameActivity;
import kr.ac.tukorea.ge.spgp.framework.scene.Scene;
import kr.ac.tukorea.ge.spgp.memecatdefense.game.scene.main.MainScene;

public class MemeCatDefenseActivity extends GameActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Scene.drawsDebugInfo = BuildConfig.DEBUG;
        Metrics.setGameSize(9, 16);
        super.onCreate(savedInstanceState);
        // Scene.drawsDebugInfo 변경 시점에 주의한다.
        // new GameView() 가 호출되는 super.onCreate() 보다 이전에 해야 한다.
        new MainScene().push();
    }
}