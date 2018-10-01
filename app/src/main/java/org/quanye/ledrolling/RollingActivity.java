package org.quanye.ledrolling;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import org.quanye.ledrolling.customview.RollingView;
import org.quanye.ledrolling.domain.RollingValue;

public class RollingActivity extends AppCompatActivity {

    public static final String ROLLING_VALUE = "ROLLING-VALUE";

    private RollingView rv;
    private RollingValue value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 屏幕常亮
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        setContentView(R.layout.activity_rolling);

        Intent intent = getIntent();
        value = (RollingValue) intent.getSerializableExtra(ROLLING_VALUE);

        rv = findViewById(R.id.rv);

        rv.setSpeed(value.getSpeed() * 2);
        rv.setTitleText(value.getTitleText());
        rv.setTitleTextSize(value.getTitleTextSize() * 2);
        rv.setTitleTextColor(value.getTitleTextColor());
        rv.setBgColor(value.getBgColor());
    }


    public static void startAction(Activity act, RollingValue value) {
        Intent intent = new Intent(act, RollingActivity.class);
        intent.putExtra(ROLLING_VALUE, value);
        act.startActivity(intent);
    }


    @Override
    protected void onStop() {
        super.onStop();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
