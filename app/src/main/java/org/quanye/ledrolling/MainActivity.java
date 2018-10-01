package org.quanye.ledrolling;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import com.jrummyapps.android.colorpicker.ColorPickerDialog;
import com.jrummyapps.android.colorpicker.ColorPickerDialogListener;

import org.quanye.ledrolling.customview.RollingView;
import org.quanye.ledrolling.domain.RollingValue;

/**
 * Created By QuanyeChen
 */
public class MainActivity extends AppCompatActivity {

    private static final String BG_COLOR = "BG-COLOR";
    private static final String TEXT_COLOR = "TEXT-COLOR";
//    private static final String IS_REVERSE = "IS-REVERSE";
    private static final String SHOW_TEXT = "SHOW-TEXT";
    private static final String TEXT_SIZE = "TEXT-SIZE";
    private static final String ROLLING_SPEED = "ROLLING-SPEED";

    private RollingView rv;
    private EditText etText;
    private SeekBar sbTextSize;
    private SeekBar sbRollingSpeed;
//    private Switch swhReverse;
    private Button btnBgColor;
    private Button btnTextColor;

    private String showText;
    private int textSize;
    private int rollingSpeed;
//    private boolean isReverse;
    private int bgColor;
    private int textColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rv = findViewById(R.id.rv);
        etText = findViewById(R.id.et_text);
        sbTextSize = findViewById(R.id.sb_textsize);
        sbRollingSpeed = findViewById(R.id.sb_rollingspeed);
//        swhReverse = findViewById(R.id.swh_reverse);
        btnBgColor = findViewById(R.id.btn_bgcolor);
        btnTextColor = findViewById(R.id.btn_textcolor);

        restoreData();

        etText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                showText = s.toString();
                rv.setTitleText(showText);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        sbTextSize.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                rv.setTitleTextSize(progress + 100);
                textSize = progress + 100;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        sbRollingSpeed.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                rv.setSpeed(progress);
                rollingSpeed = progress;
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }


    private void restoreData() {
        showText = getPreferences(MODE_PRIVATE).getString(SHOW_TEXT, "XX加油！");
        textSize = getPreferences(MODE_PRIVATE).getInt(TEXT_SIZE, 100);
        rollingSpeed = getPreferences(MODE_PRIVATE).getInt(ROLLING_SPEED, 5);
//        isReverse = getPreferences(MODE_PRIVATE).getBoolean(IS_REVERSE, false);
        bgColor = getPreferences(MODE_PRIVATE).getInt(BG_COLOR, Color.YELLOW);
        textColor = getPreferences(MODE_PRIVATE).getInt(TEXT_COLOR, Color.RED);

        rv.setTitleText(showText);
        etText.setText(showText);

        rv.setTitleTextSize(textSize);
        sbTextSize.setProgress(textSize - 100);

        rv.setSpeed(rollingSpeed);
        sbRollingSpeed.setProgress(rollingSpeed);

        rv.setBgColor(bgColor);

        rv.setTitleTextColor(textColor);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (R.id.about == item.getItemId()) {
            TextView tv = new TextView(this);
            SpannableString s = new SpannableString("作者：QuanyeChen\n=开源代码=\nhttp://github.com/aizichen/LEDRolling\n\n");
            Linkify.addLinks(s, Linkify.WEB_URLS);
            tv.setText(s);
            tv.setMovementMethod(LinkMovementMethod.getInstance());

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setIcon(R.mipmap.icon).setTitle(R.string.about)
                    .setView(tv).setNegativeButton("确定", null)
                    .create().show();
        }
        return super.onOptionsItemSelected(item);
    }


    /**
     * 背景色
     *
     * @param view
     */
    public void onBgColor(View view) {
        ColorPickerDialog dialog = ColorPickerDialog.newBuilder().setColor(bgColor)
                .setDialogTitle(R.string.bgcolor)
                .setDialogType(ColorPickerDialog.TYPE_CUSTOM)
                .create();
        dialog.setColorPickerDialogListener(new ColorPickerDialogListener() {
            @Override
            public void onColorSelected(int dialogId, int color) {
                bgColor = color;
                rv.setBgColor(color);
                getPreferences(MODE_PRIVATE).edit().putInt(BG_COLOR, color).apply();
            }

            @Override
            public void onDialogDismissed(int dialogId) {
            }
        });
        dialog.show(getFragmentManager(), "colorpicker-dialog:bgcolor");
    }


    /**
     * 文字色
     *
     * @param view
     */
    public void onTextColor(View view) {
        ColorPickerDialog dialog = ColorPickerDialog.newBuilder().setColor(textColor)
                .setDialogTitle(R.string.textcolor)
                .setDialogType(ColorPickerDialog.TYPE_CUSTOM)
                .create();
        dialog.setColorPickerDialogListener(new ColorPickerDialogListener() {
            @Override
            public void onColorSelected(int dialogId, int color) {
                textColor = color;
                rv.setTitleTextColor(color);
                getPreferences(MODE_PRIVATE).edit().putInt(TEXT_COLOR, color).apply();
            }

            @Override
            public void onDialogDismissed(int dialogId) {
            }
        });
        dialog.show(getFragmentManager(), "colorpicker-dialog:textcolor");
    }


    /**
     * 全屏显示
     * @param view
     */
    public void onStartFullScreen(View view) {
        RollingValue value = new RollingValue(showText, textColor, textSize, bgColor, rollingSpeed);
        RollingActivity.startAction(this, value);
    }

//    /**
//     * 是否反转文字滚动方向
//     *
//     * @param view
//     */
//    public void onReverse(View view) {
//        if (swhReverse.isChecked()) {
//            isReverse = true;
//            getPreferences(MODE_PRIVATE).edit().putBoolean(IS_REVERSE, true).apply();
//        } else {
//            isReverse = false;
//            getPreferences(MODE_PRIVATE).edit().putBoolean(IS_REVERSE, false).apply();
//        }
//    }

    @Override
    protected void onStop() {
        super.onStop();
        getPreferences(MODE_PRIVATE).edit().putString(SHOW_TEXT, showText).apply();
        getPreferences(MODE_PRIVATE).edit().putInt(TEXT_SIZE, textSize).apply();
        getPreferences(MODE_PRIVATE).edit().putInt(ROLLING_SPEED, rollingSpeed).apply();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
