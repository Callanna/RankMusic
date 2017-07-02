package customview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.callanna.rankmusic.R;

/**
 * Created by Callanna on 2017/6/25.
 */

public class CustomIndicator extends View implements ViewPager.OnPageChangeListener {
    private Paint fillPaint = new Paint();
    private Paint strokePaint = new Paint();
    private ViewPager viewPager;
    private int fillColor;
    private int strokeColor;
    private int activeItem = 0;
    private int numberOfItems = 0;
    private int previouslyActiveItem = 99;
    private static final int SELECTED_FACTOR = 2;
    private static final int SPACING_FACTOR = 1;
    private float radius = 30f;
    private float calculatedRadius, constantRadius, previousRadius;
    private String shape = "", circle = "", rectangle = "";
    private Context context;

    public CustomIndicator(Context context) {
        super(context, null);
    }

    public CustomIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        TypedArray attributes = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.CustomIndicator,
                0, 0);

        try {
            radius = attributes.getDimension(R.styleable.CustomIndicator_radius, radius);
            fillColor = attributes.getColor(R.styleable.CustomIndicator_fillColor, Color.WHITE);
            strokeColor = attributes.getColor(R.styleable.CustomIndicator_strokeColor, Color.GRAY);
            if (attributes.getString(R.styleable.CustomIndicator_shape) != null) {
                shape = attributes.getString(R.styleable.CustomIndicator_shape);
            }
        } finally {
            attributes.recycle();
        }

        fillPaint.setAntiAlias(true);
        fillPaint.setStyle(Paint.Style.FILL);
        fillPaint.setStrokeJoin(Paint.Join.ROUND);
        fillPaint.setStrokeCap(Paint.Cap.ROUND);
        fillPaint.setStrokeWidth(10);

        if (strokeColor == 0) {
            strokeColor = fillColor;
        }
        strokePaint.setAntiAlias(true);
        strokePaint.setStyle(Paint.Style.STROKE);
        strokePaint.setStrokeWidth((radius / 10) * 2);
        calculatedRadius = (radius / 10) * 3;
        //size of inactive indicator
        constantRadius = calculatedRadius;
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (previouslyActiveItem != 99) {
            calculatedRadius /= SELECTED_FACTOR;
        }
        //desiredRadius is always size of active indicator
        setActiveItem(position, constantRadius * SELECTED_FACTOR);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    @Override
    protected void onDraw(Canvas canvas) {
        fillPaint.setColor(fillColor);
        strokePaint.setColor(strokeColor);

        float x, y;
        for (int i = 0; i < numberOfItems; i++) {
            //coordinates of circles  3*n+2
            x = (i * SELECTED_FACTOR * radius) + (i * radius) + (radius * SELECTED_FACTOR);
            y = radius * SELECTED_FACTOR;

            if (i == activeItem) {
                //if its on initial state
                if (previouslyActiveItem == 99) {
                    drawIndicators(canvas, x, y, (calculatedRadius * SELECTED_FACTOR), true);
                } else {
                    drawIndicators(canvas, x, y, calculatedRadius, true);
                }
            } else if (i == previouslyActiveItem) {
                drawIndicators(canvas, x, y, previousRadius, false);
            } else {
                drawIndicators(canvas, x, y, constantRadius, false);
            }
        }
    }

    public void drawIndicators(Canvas canvas, float coordinateX, float coordinateY, float calculatedSize, boolean isSelected) {
        if (isSelected) {
            fillPaint.setColor(Color.parseColor("#ffffff"));
            canvas.drawLine(coordinateX - calculatedSize, coordinateY, coordinateX + calculatedSize, coordinateY, fillPaint);
        } else {
            fillPaint.setColor(strokeColor);
            canvas.drawCircle(coordinateX, coordinateY, calculatedSize, fillPaint);
        }
    }


    //to set new indicator active when viewpager flipped
    public void setActiveItem(int activeItem, float desiredRadius) {
        this.previouslyActiveItem = this.activeItem;
        this.activeItem = activeItem;

        //setting up animation
        ValueAnimator animation = ValueAnimator.ofFloat(calculatedRadius, desiredRadius);
        animation.setDuration(350);
        animation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                //on every animation update effects 2 indicator:
                //active and previously active one
                calculatedRadius = (float) valueAnimator.getAnimatedValue();
                previousRadius = (constantRadius * SELECTED_FACTOR) - (calculatedRadius - constantRadius);
                CustomIndicator.this.invalidate();
            }
        });
        animation.start();

        this.invalidate();
        this.requestLayout();
    }

    public ViewPager getViewPager() {
        return viewPager;
    }

    public void setViewPager(ViewPager viewPager) {
        this.viewPager = viewPager;
        this.numberOfItems = this.viewPager.getAdapter().getCount();
        this.activeItem = viewPager.getCurrentItem();
        this.invalidate();
        this.requestLayout();
        this.viewPager.addOnPageChangeListener(this);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // x(n) = a + d(n-1)
        int n = numberOfItems;
        int measuredWidth = (int) (radius * SELECTED_FACTOR + (radius * (SELECTED_FACTOR + SPACING_FACTOR)) * (n - 1));
        measuredWidth += radius * SELECTED_FACTOR;
        int measuredHeight = (int) (radius * SELECTED_FACTOR) * 2;
        setMeasuredDimension(measuredWidth, measuredHeight);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }


    public void setRadius(float newRadius) {
        this.radius = newRadius;
        calculatedRadius = (radius / 10) * 6;
        constantRadius = calculatedRadius;
        this.invalidate();
    }

    public float getRadius() {
        return this.radius;
    }

    public void setFillColor(int color) {
        this.fillColor = color;
        this.invalidate();
    }

    public int getFillColor() {
        return this.fillColor;
    }

    public void setStrokeColor(int color) {
        this.strokeColor = color;
        this.invalidate();
    }

    public int getStrokeColor() {
        return this.strokeColor;
    }

    public String getShape() {
        return this.shape;
    }

    public void setShape(String shape) {
        this.shape = shape;
        this.invalidate();
    }
}
