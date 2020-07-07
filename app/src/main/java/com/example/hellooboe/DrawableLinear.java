package com.example.hellooboe;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

class DrawableLinear extends Drawable {

    private ShapeDrawable drawable;

    public DrawableLinear(Context context) {
        super();
        createDrawable();
    }

    private void createDrawable() {
        drawable = new ShapeDrawable(new RectShape());
        drawable.getPaint().setColor(Color.BLACK);
        drawable.setBounds(0, 0, 10000, 10000);
    }

    public DrawableLinear(Context c, AttributeSet as) {
        super();
        createDrawable();
    }

    @Override
    public void draw(Canvas canvas) {
        // Get the drawable's bounds
        drawable.draw(canvas);
    }

    @Override
    public void setAlpha(int i) {

    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {

    }

    @Override
    public int getOpacity() {
        return PixelFormat.OPAQUE;
    }
}
