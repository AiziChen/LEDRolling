package org.quanye.ledrolling.domain;

import java.io.Serializable;

/**
 * @Created By QuanyeChen
 */
public class RollingValue implements Serializable {
    private String titleText;
    private int titleTextColor;
    private int titleTextSize;
    private int bgColor;
    private int speed;

    public RollingValue() {}

    public RollingValue(String titleText, int titleTextColor, int titleTextSize, int bgColor, int speed) {
        this.titleText = titleText;
        this.titleTextColor = titleTextColor;
        this.titleTextSize = titleTextSize;
        this.bgColor = bgColor;
        this.speed = speed;
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
