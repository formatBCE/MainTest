package ua.mainacad.maintest.maintest.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class MyLayout extends ViewGroup {

    private final int childShift = 40;
    private int childWidth;
    private int childHeight;

    public MyLayout(Context context) {
        super(context);
    }

    public MyLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MyLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasuredWidth() - getPaddingEnd() - getPaddingStart();
        int height = getMeasuredHeight() - getPaddingTop() - getPaddingBottom();
        int visibleChildCount = 0;
        for (int i = 0; i < getChildCount(); i++) {
            if (getChildAt(i).getVisibility() != GONE) {
                visibleChildCount++;
            }
        }
        childWidth = width - (childShift * (visibleChildCount - 1));
        childHeight = height - (childShift * (visibleChildCount - 1));
        final int widthSpec = MeasureSpec.makeMeasureSpec(childWidth, MeasureSpec.EXACTLY);
        final int heightSpec = MeasureSpec.makeMeasureSpec(childHeight, MeasureSpec.EXACTLY);
        measureChildren(widthSpec, heightSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int l = left + getPaddingLeft();
        int t = top + getPaddingTop();
        int r = l + childWidth;
        int b = top + childHeight;
        for (int i = 0; i < getChildCount(); i++) {
            final View child = getChildAt(i);
            if (child.getVisibility() != GONE) {
                child.layout(l, t, r, b);
                l += childShift;
                t += childShift;
                r += childShift;
                b += childShift;
            }
        }
    }
}
