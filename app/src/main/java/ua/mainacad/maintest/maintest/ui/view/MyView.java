package ua.mainacad.maintest.maintest.ui.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import ua.mainacad.maintest.maintest.R;

public class MyView extends View {

    private static final int DEFAULT_DIAMETER = 40;
    private static final int MAX_SHIFT = 700;


    private int startCircleDiameter = DEFAULT_DIAMETER;
    private int endCircleDiameter = DEFAULT_DIAMETER;
    private Paint paint = new Paint();
    private int shift;

    public MyView(Context context) {
        this(context, null);
    }

    public MyView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public MyView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        paint.setColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
        final TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyView);
        startCircleDiameter = typedArray.getDimensionPixelSize(R.styleable.MyView_startCircleDiameter, DEFAULT_DIAMETER);
        endCircleDiameter = typedArray.getDimensionPixelSize(R.styleable.MyView_endCircleDiameter, DEFAULT_DIAMETER);
        typedArray.recycle();
    }

    public void setColor(@ColorRes int color) {
        ValueAnimator animator = ValueAnimator.ofInt(0, MAX_SHIFT);
        animator.setDuration(1000);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.addUpdateListener(animation -> {
            final Object value = animation.getAnimatedValue();
            shift = (Integer) value;
            invalidate();
        });
        animator.start();
        paint.setColor(ContextCompat.getColor(getContext(), color));
        invalidate();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        final int wantedContentWidth = endCircleDiameter + startCircleDiameter;
        final int wantedContentHeight = Math.max(startCircleDiameter, endCircleDiameter);
        int wantedWidth = Math.max(getSuggestedMinimumWidth(),
                getPaddingStart() + getPaddingEnd() + wantedContentWidth);
        int wantedHeight = Math.max(getSuggestedMinimumHeight(),
                getPaddingTop() + getPaddingBottom() + wantedContentHeight + MAX_SHIFT);
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
        super.onDraw(canvas);
        final int r1 = startCircleDiameter / 2;
        final int x1 = getPaddingStart() + r1;
        final int y1 = getPaddingTop() + r1;
        final int r2 = endCircleDiameter / 2;
        final int x2 = getPaddingStart() + r2 + startCircleDiameter;
        final int y2 = getPaddingTop() + r2;
        final int y = Math.max(y1, y2) + shift;
        canvas.drawCircle(x1, y, r1, paint);
        canvas.drawCircle(x2, y, r2, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }
}
