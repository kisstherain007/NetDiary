package com.mall.android.common.utils.imageloader;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;

import com.squareup.picasso.Transformation;

public class CircleTransForm implements Transformation {
    @Override
    public Bitmap transform(Bitmap source) {
        int width = source.getWidth();
        int height = source.getHeight();
        int min = Math.min(width, height);

        int width_min = (width - min) / 2;
        int height_min = (height - min) / 2;

        Bitmap bitmap = Bitmap.createBitmap(source, width_min, height_min, min, min);
        if (bitmap != source) {
            source.recycle();
        }
        Bitmap circleBit = Bitmap.createBitmap(min, min, source.getConfig());
        Canvas canvas = new Canvas(circleBit);
        Paint paint = new Paint();
        BitmapShader shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        paint.setShader(shader);
        paint.setAntiAlias(true);
        float radius = min / (float) 2;
        canvas.drawCircle(radius, radius, radius, paint);
        bitmap.recycle();
        return circleBit;
    }

    @Override
    public String key() {
        return "circle";
    }
}