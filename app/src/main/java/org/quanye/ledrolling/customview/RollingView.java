package org.quanye.ledrolling.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import org.quanye.ledrolling.R;

/**
 * @Created By QuanyeChen
 */
public class RollingView extends View {

    private String titleText;
    private int titleTextColor;
    private int titleTextSize;
    private int bgColor;
    private int speed;

    private Rect bound = new Rect();
    private Paint paint = new Paint();


    private static int position = 10;

    private RollingThread rollingThread;
    private boolean run;

    public RollingView(Context context) {
        this(context, null);
    }

    public RollingView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RollingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.RollingView, defStyleAttr, 0);
        int n = a.getIndexCount();
        for (int i = 0; i < n; ++i) {
            int attr = a.getIndex(i);
            switch (attr) {
                case R.styleable.RollingView_titleText:
                    titleText = a.getString(attr);
                    break;
                case R.styleable.RollingView_titleTextSize:
                    titleTextSize = a.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()));
                    break;
                case R.styleable.RollingView_titleTextColor:
                    titleTextColor = a.getColor(attr, Color.RED);
                    break;
                case R.styleable.RollingView_bgColor:
                    bgColor = a.getColor(attr, Color.YELLOW);
                    break;
                case R.styleable.RollingView_speed:
                    speed = a.getInteger(attr, 2);
                    break;
            }
        }
        a.recycle();
        // paint抗锯齿
        paint.setAntiAlias(true);
        paint.setTypeface(Typeface.DEFAULT_BOLD);
    }


    private class RollingThread extends Thread {
        @Override
        public void run() {
            while (run) {
                long preTime = System.currentTimeMillis();
                postInvalidate();
                long afterTime = System.currentTimeMillis();
                try {
                    Thread.sleep(afterTime - preTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.getTextBounds(titleText, 0, titleText.length(), bound);

        paint.setColor(bgColor);
        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), paint);

        paint.setColor(titleTextColor);
        paint.setTextSize(titleTextSize);
//        int xCenter = getWidth() / 2 - bound.width() / 2;
        int yCenter = getHeight() / 2 + bound.height() / 3;
        if (position > (getWidth() + bound.width())) {
            position = 0;
        } else {
            position += speed;
        }
        canvas.drawText(titleText, getWidth() - position, yCenter, paint);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int width;
        int height;
        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {
            paint.setTextSize(titleTextSize);
            paint.getTextBounds(titleText, 0, titleText.length(), bound);
            float textWidth = bound.width();
            width = (int) (getPaddingLeft() + textWidth + getPaddingRight());
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            paint.setTextSize(titleTextSize);
            paint.getTextBounds(titleText, 0, titleText.length(), bound);
            float textHeight = bound.height();
            height = (int) (getPaddingTop() + textHeight + getPaddingBottom());
        }
        setMeasuredDimension(width, height);
    }


    /**
     * 当窗口为显示状态时，启动线程；当窗口为不可见状态时，关闭线程
     * @param visibility
     */
    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
        if (visibility == GONE) {        // 停止更新画布的线程
            run = false;
            rollingThread.interrupt();
        } else if (visibility == VISIBLE) {        // 启动更新画布的线程
            run = true;
            rollingThread = new RollingThread();
            rollingThread.start();
        }
    }


    public String getTitleText() {
        return titleText;
    }

    public void setTitleText(String titleText) {
        this.titleText = titleText;
    }

    public int getTitleTextColor() {
        return titleTextColor;
    }

    public void setTitleTextColor(int titleTextColor) {
        this.titleTextColor = titleTextColor;
    }

    public int getTitleTextSize() {
        return titleTextSize;
    }

    public void setTitleTextSize(int titleTextSize) {
        this.titleTextSize = titleTextSize;
    }

    public int getBgColor() {
        return bgColor;
    }

    public void setBgColor(int bgColor) {
        this.bgColor = bgColor;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

}
