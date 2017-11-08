package com.kizxm.kirakiradraw.models;

import android.graphics.Path;

public class Brush {

    public int color;
    public boolean emboss;
    public boolean blur;
    public int strokeW;
    public Path path;

    public Brush(int color, boolean emboss, boolean blur, int strokeW, Path path) {
        this.color = color;
        this.emboss = emboss;
        this.blur = blur;
        this.strokeW = strokeW;
        this.path = path;
    }
}