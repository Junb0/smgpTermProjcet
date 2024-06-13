package kr.ac.tukorea.ge.spgp.memecatdefense.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import kr.ac.tukorea.ge.spgp.framework.activity.GameActivity;
import kr.ac.tukorea.ge.spgp.memecatdefense.BuildConfig;
import kr.ac.tukorea.ge.spgp.memecatdefense.R;
import kr.ac.tukorea.ge.spgp.framework.interfaces.IGameObject;
import kr.ac.tukorea.ge.spgp.memecatdefense.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {


    private ActivityMainBinding binding;
    private Toast toast = null;
    private int[] UpgradeLevels = {0, 0, 0, 0, 0, 0, 0, 0, 0};
    private int dia = 10000;
    private int highscore = 0;

    ActivityResultLauncher<Intent> startActivityResult =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                    result -> {
                        if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                            int tmpDia = result.getData().getIntExtra("dia", -1);
                            int tmpHighscore = result.getData().getIntExtra("highscore", -1);
                            Log.d("MainActivity", "Received dia = " + tmpDia);  // 로그 출력으로 받은 dia 값 확인
                            if (tmpDia >= 0) {
                                dia += tmpDia;
                            }
                            if(highscore < tmpHighscore){
                                highscore = tmpHighscore;
                            }
                            saveUpgradeState();
                            applyUpgradeInfoToViews();
                        }
                    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        restoreUpgradeState();
        applyUpgradeInfoToViews();
        //if (BuildConfig.DEBUG) {
            //startActivity(new Intent(this, MemeCatDefenseActivity.class));
        //}
    }

    @Override
    protected void onStart() {
        super.onStart();
    }



    private void runGameActivity(){
        Intent intent = new Intent(this, MemeCatDefenseActivity.class);
        intent.putExtra("upgrade", UpgradeLevels);
        intent.putExtra("highscore", highscore);
        startActivityResult.launch(intent);
    }


    private void makeToast(String msg){
        if(toast != null){
            toast.cancel();
        }
        toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        toast.show();
    }

    public void saveUpgradeState(){
        SharedPreferences pref = getSharedPreferences("save_data", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        editor.putInt("highscore", highscore);
        editor.putInt("dia", dia);
        editor.putInt("upgradeCount_AppleCat", UpgradeLevels[0]);
        editor.putInt("upgradeCount_BananaCat", UpgradeLevels[1]);
        editor.putInt("upgradeCount_HappyCat", UpgradeLevels[2]);
        editor.putInt("upgradeCount_MaxwellCat", UpgradeLevels[3]);
        editor.putInt("upgradeCount_OiiaCat", UpgradeLevels[4]);
        editor.putInt("upgradeCount_FinalDamage", UpgradeLevels[5]);
        editor.putInt("upgradeCount_GoldGain", UpgradeLevels[6]);
        editor.putInt("upgradeCount_CriticalPercent", UpgradeLevels[7]);
        editor.putInt("upgradeCount_PlayerHP", UpgradeLevels[8]);
        editor.commit();
    }

    public void restoreUpgradeState(){
        SharedPreferences pref = getSharedPreferences("save_data", Activity.MODE_PRIVATE);
        if((pref != null)){
            if(pref.contains("dia")){
                dia = pref.getInt("dia", 0);
            }
            if(pref.contains("highscore")){
                highscore = pref.getInt("highscore", 0);
            }
            if(pref.contains("upgradeCount_AppleCat")){
                UpgradeLevels[0] = pref.getInt("upgradeCount_AppleCat", 0);
            }
            if(pref.contains("upgradeCount_BananaCat")){
                UpgradeLevels[1] = pref.getInt("upgradeCount_BananaCat", 0);
            }
            if(pref.contains("upgradeCount_HappyCat")){
                UpgradeLevels[2] = pref.getInt("upgradeCount_HappyCat", 0);
            }
            if(pref.contains("upgradeCount_MaxwellCat")){
                UpgradeLevels[3] = pref.getInt("upgradeCount_MaxwellCat", 0);
            }
            if(pref.contains("upgradeCount_OiiaCat")){
                UpgradeLevels[4] = pref.getInt("upgradeCount_OiiaCat", 0);
            }
            if(pref.contains("upgradeCount_FinalDamage")){
                UpgradeLevels[5] = pref.getInt("upgradeCount_FinalDamage", 0);
            }
            if(pref.contains("upgradeCount_GoldGain")){
                UpgradeLevels[6] = pref.getInt("upgradeCount_GoldGain", 0);
            }
            if(pref.contains("upgradeCount_CriticalPercent")){
                UpgradeLevels[7] = pref.getInt("upgradeCount_CriticalPercent", 0);
            }
            if(pref.contains("upgradeCount_PlayerHP")){
                UpgradeLevels[8] = pref.getInt("upgradeCount_PlayerHP", 0);
            }
        }

        applyUpgradeInfoToViews();
    }

    public void applyUpgradeInfoToViews() {
        String str;
        str = Integer.toString(highscore);
        binding.highscore.setText(str);

        str = Integer.toString(dia);
        binding.diatextView.setText(str);

        str = "LV. " + Integer.toString(UpgradeLevels[0]);
        binding.appleCatUpgradeCountTextView.setText(str);
        str = "데미지 + " + Integer.toString(UpgradeLevels[0] * 10) + "%";
        binding.appleCatUpgradeEffectTextView.setText(str);
        str = Integer.toString(100 + UpgradeLevels[0] * 50);
        binding.appleCatUpgradeButton.setText(str);

        str = "LV. " + Integer.toString(UpgradeLevels[1]);
        binding.bananaCatUpgradeCountTextView.setText(str);
        str = "데미지 + " + Integer.toString(UpgradeLevels[1] * 10) + "%";
        binding.bananaCatUpgradeEffectTextView.setText(str);
        str = Integer.toString(100 + UpgradeLevels[1] * 50);
        binding.bananaCatUpgradeButton.setText(str);

        str = "LV. " + Integer.toString(UpgradeLevels[2]);
        binding.happyCatUpgradeCountTextView.setText(str);
        str = "데미지 + " + Integer.toString(UpgradeLevels[2] * 10) + "%";
        binding.happyCatUpgradeEffectTextView.setText(str);
        str = Integer.toString(100 + UpgradeLevels[2] * 50);
        binding.happyCatUpgradeButton.setText(str);

        str = "LV. " + Integer.toString(UpgradeLevels[3]);
        binding.maxwellCatUpgradeCountTextView.setText(str);
        str = "데미지 + " + Integer.toString(UpgradeLevels[3] * 10) + "%";
        binding.maxwellCatUpgradeEffectTextView.setText(str);
        str = Integer.toString(100 + UpgradeLevels[3] * 50);
        binding.maxwellCatUpgradeButton.setText(str);

        str = "LV. " + Integer.toString(UpgradeLevels[4]);
        binding.oiiaCatUpgradeCountTextView.setText(str);
        str = "데미지 + " + Integer.toString(UpgradeLevels[4] * 10) + "%";
        binding.oiiaCatUpgradeEffectTextView.setText(str);
        str = Integer.toString(100 + UpgradeLevels[4] * 50);
        binding.oiiaCatUpgradeButton.setText(str);

        str = "LV. " + Integer.toString(UpgradeLevels[5]);
        binding.finalDamageUpgradeCountTextView.setText(str);
        str = "+ " + Integer.toString(UpgradeLevels[5] * 2) + "%";
        binding.finalDamageUpgradeEffectTextView.setText(str);
        str = Integer.toString(100 + UpgradeLevels[5] * 50);
        binding.finalDamageUpgradeButton.setText(str);

        str = "LV. " + Integer.toString(UpgradeLevels[6]);
        binding.goldGainUpgradeCountTextView.setText(str);
        str = "+ " + Integer.toString(UpgradeLevels[6] * 10) + "%";
        binding.goldGainUpgradeEffectTextView.setText(str);
        str = Integer.toString(100 + UpgradeLevels[6] * 50);
        binding.goldGainUpgradeButton.setText(str);

        str = "LV. " + Integer.toString(UpgradeLevels[7]);
        binding.criticalPercentUpgradeCountTextView.setText(str);
        str = "+ " + Integer.toString(UpgradeLevels[7] * 3) + "%";
        binding.criticalPercentUpgradeEffectTextView.setText(str);
        str = Integer.toString(100 + UpgradeLevels[7] * 50);
        binding.criticalPercentUpgradeButton.setText(str);

        str = "LV. " + Integer.toString(UpgradeLevels[8]);
        binding.playerHPUpgradeCountTextView.setText(str);
        str = "+ " + Integer.toString(UpgradeLevels[8] * 20);
        binding.playerHPUpgradeEffectTextView.setText(str);
        str = Integer.toString(100 + UpgradeLevels[8] * 50);
        binding.playerHPUpgradeButton.setText(str);

    }
    public void onBtnStartGame(View view) {
        runGameActivity();
    }

    public void onBtnUpgradeAppleCat(View view) {
        if(dia < 100 + UpgradeLevels[0] * 50){
            makeToast("다이아가 부족합니다.");
            return;
        }
        dia -= 100 + UpgradeLevels[0] * 50;
        UpgradeLevels[0] += 1;
        applyUpgradeInfoToViews();
        saveUpgradeState();
        makeToast("업그레이드 완료!");
    }

    public void onBtnUpgradeBananaCat(View view) {
        if(dia < 100 + UpgradeLevels[1] * 50){
            makeToast("다이아가 부족합니다.");
            return;
        }
        dia -= 100 + UpgradeLevels[1] * 50;
        UpgradeLevels[1] += 1;
        applyUpgradeInfoToViews();
        saveUpgradeState();
        makeToast("업그레이드 완료!");
    }

    public void onBtnUpgradeHappyCat(View view) {
        if(dia < 100 + UpgradeLevels[2] * 50){
            makeToast("다이아가 부족합니다.");
            return;
        }
        dia -= 100 + UpgradeLevels[2] * 50;
        UpgradeLevels[2] += 1;
        applyUpgradeInfoToViews();
        saveUpgradeState();
        makeToast("업그레이드 완료!");
    }

    public void onBtnUpgradeMaxwellCat(View view) {
        if(dia < 100 + UpgradeLevels[3] * 50){
            makeToast("다이아가 부족합니다.");
            return;
        }
        dia -= 100 + UpgradeLevels[3] * 50;
        UpgradeLevels[3] += 1;
        applyUpgradeInfoToViews();
        saveUpgradeState();
        makeToast("업그레이드 완료!");
    }

    public void onBtnUpgradeOiiaCat(View view) {
        if(dia < 100 + UpgradeLevels[4] * 50){
            makeToast("다이아가 부족합니다.");
            return;
        }
        dia -= 100 + UpgradeLevels[4] * 50;
        UpgradeLevels[4] += 1;
        applyUpgradeInfoToViews();
        saveUpgradeState();
        makeToast("업그레이드 완료!");
    }

    public void onBtnUpgradeFinalDamage(View view) {
        if(dia < 100 + UpgradeLevels[5] * 50){
            makeToast("다이아가 부족합니다.");
            return;
        }
        dia -= 100 + UpgradeLevels[5] * 50;
        UpgradeLevels[5] += 1;
        applyUpgradeInfoToViews();
        saveUpgradeState();
        makeToast("업그레이드 완료!");
    }

    public void onBtnUpgradeGoldGain(View view) {
        if(dia < 100 + UpgradeLevels[6] * 50){
            makeToast("다이아가 부족합니다.");
            return;
        }
        dia -= 100 + UpgradeLevels[6] * 50;
        UpgradeLevels[6] += 1;
        applyUpgradeInfoToViews();
        saveUpgradeState();
        makeToast("업그레이드 완료!");
    }

    public void onBtnUpgradeCriticalPercent(View view) {
        if(UpgradeLevels[7] > 30){
            makeToast("최대 레벨입니다.");
            return;
        }
        if(dia < 100 + UpgradeLevels[7] * 50){
            makeToast("다이아가 부족합니다.");
            return;
        }

        dia -= 100 + UpgradeLevels[3] * 50;
        UpgradeLevels[7] += 1;
        applyUpgradeInfoToViews();
        saveUpgradeState();
        makeToast("업그레이드 완료!");
    }

    public void onBtnUpgradePlayerHP(View view) {
        if(dia < 100 + UpgradeLevels[8] * 50){
            makeToast("다이아가 부족합니다.");
            return;
        }
        dia -= 100 + UpgradeLevels[3] * 50;
        UpgradeLevels[8] += 1;
        applyUpgradeInfoToViews();
        saveUpgradeState();
        makeToast("업그레이드 완료!");

    }

    public void onBtnRest(View view) {
        new AlertDialog.Builder(MainActivity.this)
                .setTitle("초기화")
                .setMessage("모든 진행상황을 초기화 할까요?\n업그레이드 상태, 보유 다이아몬드, 최고 웨이브 기록이 삭제됩니다.")
                .setPositiveButton("초기화", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dia = 10000;
                        highscore = 0;
                        for(int i = 0; i < 9; i++){
                            UpgradeLevels[i] = 0;
                        }
                        applyUpgradeInfoToViews();
                        saveUpgradeState();
                    }
                })
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .create()
                .show();
    }

    protected void clearMyPrefs(){
        SharedPreferences pref = getSharedPreferences("save_data", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.commit();
    }

}