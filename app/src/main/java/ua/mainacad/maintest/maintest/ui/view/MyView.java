package ua.mainacad.maintest.maintest.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import ua.mainacad.maintest.maintest.R;

public class MyView extends View {

    private static final int DEFAULT_DIAMETER = 20;


    private int startCircleDiameter = DEFAULT_DIAMETER;
    private int endCircleDiameter = DEFAULT_DIAMETER;
    private Paint paint;

    public MyView(Context context) {
        super(context);
        paint = new Paint();
        paint.setColor(ContextCompat.getColor(context, R.color.colorAccent));
        paint.setStrokeWidth(5);
    }

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        final TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyView);
        startCircleDiameter = typedArray.getDimensionPixelSize(R.styleable.MyView_startCircleDiameter, DEFAULT_DIAMETER);
        endCircleDiameter = typedArray.getDimensionPixelSize(R.styleable.MyView_endCircleDiameter, DEFAULT_DIAMETER);
        typedArray.recycle();
    }

    public MyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MyView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setColor(@ColorRes int color) {
        paint.setColor(ContextCompat.getColor(getContext(), color));
        invalidate();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        final int wantedContentWidth = endCircleDiameter + startCircleDiameter;
        final int wantedContentHeight = Math.max(startCircleDiameter, endCircleDiameter);
        int wantedWidth = getPaddingStart() + getPaddingEnd() + wantedContentWidth;
        int wantedHeight = getPaddingTop() + getPaddingBottom() + wantedContentHeight;
        final int wMode = MeasureSpec.getMode(widthMeasureSpec);
        final int wSize = MeasureSpec.getSize(widthMeasureSpec);
        final int hMode = MeasureSpec.getMode(heightMeasureSpec);
        final int hSize = MeasureSpec.getSize(heightMeasureSpec);
        int width = wantedWidth;
        int height = wantedHeight;
        switch (wMode) {
            case MeasureSpec.AT_MOST:
                width = Math.min(wantedWidth, wSize);
                break;
            case MeasureSpec.EXACTLY:
                width = wSize;
                break;
            case MeasureSpec.UNSPECIFIED:
            default:
                break;
        }
        switch (hMode) {
            case MeasureSpec.AT_MOST:
                height = Math.min(wantedHeight, hSize);
                break;
            case MeasureSpec.EXACTLY:
                height = hSize;
                break;
            case MeasureSpec.UNSPECIFIED:
            default:
                break;
        }
        float wMultiplier = 1f;
        float hMultiplier = 1f;
        if (wantedWidth > width) {
            int contentWidth = width - getPaddingStart() - getPaddingEnd();
            wMultiplier = (float) contentWidth / wantedContentWidth;
        }
        if (wantedHeight > height) {
            int contentHeight = height - getPaddingTop() - getPaddingBottom();
            hMultiplier = (float) contentHeight / wantedContentHeight;
        }
        final float multiplier = Math.min(hMultiplier, wMultiplier);
        startCircleDiameter *= multiplier;
        endCircleDiameter *= multiplier;
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        final int r1 = startCircleDiameter / 2;
        final int x1 = getPaddingStart() + r1;
        final int y1 = getPaddingTop() + r1;
        canvas.drawCircle(x1, y1, r1, paint);
        final int r2 = endCircleDiameter / 2;
        final int x2 = getPaddingStart() + r2 + startCircleDiameter;
        final int y2 = getPaddingTop() + r1;
        canvas.drawCircle(x2, y2, r2, paint);
    }
}
