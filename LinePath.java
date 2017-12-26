package com.yu;

/**
 * Created by y on 2017/8/31.
 */
public class LinePath {
    private float sx;
    private float sy;
    private float w;
    private float h;

    public LinePath(float sx, float sy, float w, float h){
        this.sx = sx;
        this.sy = sy;
        this.w = w;
        this.h = h;
    }

    public float getSx() {
        return sx;
    }

    public void setSx(float sx) {
        this.sx = sx;
    }

    public float getSy() {
        return sy;
    }

    public void setSy(float sy) {
        this.sy = sy;
    }

    public float getW() {
        return w;
    }

    public void setW(float w) {
        this.w = w;
    }

    public float getH() {
        return h;
    }

    public void setH(float h) {
        this.h = h;
    }
}
