package com.kondratyonok.kondratyonok.custom;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

/**
 * Created by NKondratyonok on 01.02.18.
 */

public class CircleView extends android.support.v7.widget.AppCompatImageView {

    public CircleView(Context context) {
        super(context);
    }

    public CircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CircleView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Drawable drawable = getDrawable();
        if (drawable == null) {
            return;
        }

        if (getWidth() == 0 || getHeight() == 0) {
            return;
        }

        final Bitmap b = ((BitmapDrawable) drawable).getBitmap();
        final int radius = Math.min(getWidth(), getHeight()) / 2;

        final Bitmap roundBitmap = getRoundBitmap(b, radius, 16);
        canvas.drawBitmap(roundBitmap, 0, 0, null);
    }

    public Bitmap getRoundBitmap(Bitmap sourceBitmap, int radius, int borderSize) {

        final int size = radius * 2;

        // scale if not the same size
        if (sourceBitmap.getWidth() != size || sourceBitmap.getHeight() != size) {

            float smallest = Math.min(sourceBitmap.getWidth(), sourceBitmap.getHeight());
            float factor = smallest / size;

            sourceBitmap = Bitmap.createScaledBitmap(
                    sourceBitmap,
                    (int) (sourceBitmap.getWidth() / factor),
                    (int) (sourceBitmap.getHeight() / factor),
                    false
            );
        }

        final Bitmap output = Bitmap.createBitmap(
                size,
                size,
                Bitmap.Config.ARGB_8888
        );
        final Canvas canvas = new Canvas(output);

        final Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        paint.setDither(true);
        ;
        paint.setColor(Color.WHITE);

        // Clear everything with transparent, draw white mask circle
        canvas.drawARGB(0, 0, 0, 0);
        canvas.drawCircle(radius, radius, radius - borderSize, paint);

        // Exclude everything outside circle
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));
        canvas.drawBitmap(sourceBitmap, 0, 0, paint);

        final int borderColor = Color.WHITE; //ContextCompat.getColor(getContext(), R.color.colorAccent);

        final Paint border = new Paint();
        border.setStyle(Paint.Style.STROKE);
        border.setStrokeWidth(borderSize);
        border.setColor(borderColor);
        border.setAntiAlias(true);

        // draw border
        canvas.drawCircle(radius, radius, radius - borderSize, border);

        return output;
    }
}