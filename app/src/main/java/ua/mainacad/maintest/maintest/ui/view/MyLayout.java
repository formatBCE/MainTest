package ua.mainacad.maintest.maintest.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import ua.mainacad.maintest.maintest.R;

public class MyLayout extends ViewGroup {

    private static final int DEFAULT_SHIFT = 5;

    private int childHorizontalShift = DEFAULT_SHIFT;
    private int childVerticalShift = DEFAULT_SHIFT;
    private int childFrameWidth;
    private int childFrameHeight;

    public MyLayout(Context context) {
        super(context);
    }

    public MyLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        final TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyLayout);
        childHorizontalShift = typedArray.getDimensionPixelSize(R.styleable.MyLayout_horizontalShift, 5);
        childVerticalShift = typedArray.getDimensionPixelSize(R.styleable.MyLayout_verticalShift, 5);
        typedArray.recycle();
    }

    public MyLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MyLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setChildShifts(
            int childHorizontalShift,
            int childVerticalShift) {
        this.childHorizontalShift = childHorizontalShift;
        this.childVerticalShift = childVerticalShift;
        requestLayout();
    }

    public int getChildHorizontalShift() {
        return childHorizontalShift;
    }

    public int getChildVerticalShift() {
        return childVerticalShift;
    }

    @Override
    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return p instanceof MarginLayoutParams;
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        if (p instanceof MarginLayoutParams) {
            return p;
        }
        return new MarginLayoutParams(p.width, p.height);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasuredWidth() - getPaddingEnd() - getPaddingStart();
        int height = getMeasuredHeight() - getPaddingTop() - getPaddingBottom();
        int visibleChildCount = 0;
        for (int i = 0; i < getChildCount(); i++) {
            final View child = getChildAt(i);
            if (child.getVisibility() != GONE) {
                visibleChildCount++;
            }
        }
        childFrameWidth = width - (childHorizontalShift * (visibleChildCount - 1));
        childFrameHeight = height - (childVerticalShift * (visibleChildCount - 1));
        final int widthSpec = MeasureSpec.makeMeasureSpec(childFrameWidth, MeasureSpec.EXACTLY);
        final int heightSpec = MeasureSpec.makeMeasureSpec(childFrameHeight, MeasureSpec.EXACTLY);
        measureChildren(widthSpec, heightSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int l = left + getPaddingLeft();
        int t = top + getPaddingTop();
        for (int i = 0; i < getChildCount(); i++) {
            final View child = getChildAt(i);
            if (child.getVisibility() != GONE) {
                final MarginLayoutParams layoutParams = (MarginLayoutParams) child.getLayoutParams();
                child.layout(
                        l + layoutParams.leftMargin,
                        t + layoutParams.topMargin,
                        l + childFrameWidth - layoutParams.rightMargin,
                        t + childFrameHeight - layoutParams.bottomMargin
                );
                l += childHorizontalShift;
                t += childVerticalShift;
            }
        }
    }
}
