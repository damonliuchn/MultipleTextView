package net.masonliu.multipletextview.library;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MultipleTextViewGroup extends RelativeLayout {

    private Context context;
    private float textSize;
    private int textColor;
    private int wordMargin;
    private int lineMargin;
    private int textPaddingLeft;
    private int textPaddingRight;
    private int textPaddingTop;
    private int textPaddingBottom;
    private boolean overspread;
    private int columnNum;
    private int textBackground;

    private int layout_width;
    private int marginRight;
    private int marginLeft;

    private OnMultipleTVItemClickListener listener;

    public MultipleTextViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

        TypedArray array = context.obtainStyledAttributes(attrs,
                R.styleable.MultipleTextViewGroup);
        textColor = array.getColor(R.styleable.MultipleTextViewGroup_textColor, 0XFF00FF00); // 提供默认值，放置未指定
        textSize = array.getDimension(R.styleable.MultipleTextViewGroup_textSize, 24);
        textSize = px2sp(context, textSize);
        wordMargin = array.getDimensionPixelSize(R.styleable.MultipleTextViewGroup_textWordMargin, 0);
        lineMargin = array.getDimensionPixelSize(R.styleable.MultipleTextViewGroup_textLineMargin, 0);
        textBackground = array.getResourceId(R.styleable.MultipleTextViewGroup_textBackground, -1);
        //array.getc
        textPaddingLeft = array.getDimensionPixelSize(R.styleable.MultipleTextViewGroup_textPaddingLeft, 0);
        textPaddingRight = array.getDimensionPixelSize(R.styleable.MultipleTextViewGroup_textPaddingRight, 0);
        textPaddingTop = array.getDimensionPixelSize(R.styleable.MultipleTextViewGroup_textPaddingTop, 0);
        textPaddingBottom = array.getDimensionPixelSize(R.styleable.MultipleTextViewGroup_textPaddingBottom, 0);
        overspread = array.getBoolean(R.styleable.MultipleTextViewGroup_overspread, false);
        columnNum = array.getInteger(R.styleable.MultipleTextViewGroup_columnNum, 1000);
        array.recycle();
        //下边是获取系统属性
        int[] attrsArray = new int[]{android.R.attr.id, // 0
                android.R.attr.background, // 1
                android.R.attr.layout_width, // 2
                android.R.attr.layout_height, // 3
                android.R.attr.layout_marginLeft, // 4
                android.R.attr.layout_marginRight // 5
        };
        TypedArray ta = context.obtainStyledAttributes(attrs, attrsArray);
        layout_width = ta.getLayoutDimension(2, ViewGroup.LayoutParams.MATCH_PARENT);
        marginRight = ta.getDimensionPixelSize(4, 0);
        marginLeft = ta.getDimensionPixelSize(5, 0);
        validateMargin();
        ta.recycle();
    }

    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    public OnMultipleTVItemClickListener getOnMultipleTVItemClickListener() {
        return listener;
    }

    public void setOnMultipleTVItemClickListener(
            OnMultipleTVItemClickListener listener) {
        this.listener = listener;
    }

    private void validateMargin() {
        layout_width = layout_width - marginRight - marginLeft;
    }

    public void setTextViews(final List<String> dataList) {
        if (layout_width >= 0) {
            setTextViewsTrue(dataList);
        } else {
            ViewTreeObserver viewTreeObserver = getViewTreeObserver();
            if (viewTreeObserver.isAlive()) {
                viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        getViewTreeObserver().removeGlobalOnLayoutListener(this);
                        layout_width = ((ViewGroup) getParent()).getWidth();
                        validateMargin();
                        setTextViewsTrue(dataList);
                    }
                });
            }
        }
    }

    private void setTextViewsTrue(List<String> dataList) {
        ViewGroup parentView = (ViewGroup) getParent();
        parentView.getWidth();
        if (dataList == null || dataList.size() == 0) {
            return;
        }

        int line = 0;
        Map<Integer, List<TextView>> lineMap = new HashMap<Integer, List<TextView>>();
        List<TextView> lineList = new ArrayList<TextView>();
        lineMap.put(0, lineList);

        int x = 0;
        int y = 0;

        for (int i = 0; i < dataList.size(); i++) {
            TextView tv = new TextView(context);
            tv.setText(dataList.get(i));
            tv.setTextSize(textSize);
            if (textBackground != -1)
                tv.setBackgroundResource(textBackground);

            tv.setTextColor(textColor);
            tv.setPadding(textPaddingLeft, textPaddingTop, textPaddingRight, textPaddingBottom);
            tv.setTag(i);// 标记position
            tv.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onMultipleTVItemClick(v, (Integer) v.getTag());
                    }
                }
            });

            int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
            int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
            tv.measure(w, h);
            int tvh = tv.getMeasuredHeight();
            int tvw = getMeasuredWidth(tv);

            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                    LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            if (x + tvw > layout_width || lineMap.get(line).size() >= columnNum) {
                x = 0;
                y = y + tvh + lineMargin;

                line++;
                lineMap.put(line, new ArrayList<TextView>());
            }
            lp.leftMargin = x;
            lp.topMargin = y;
            x = x + tvw + wordMargin;
            tv.setLayoutParams(lp);
            lineMap.get(line).add(tv);
        }

        for (int i = 0; i <= line; i++) {
            int padding = 0;
            if (overspread) {
                // 该行最后一个位置
                int len = lineMap.get(i).size();
                TextView tView = lineMap.get(i).get(len - 1);

                RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) tView.getLayoutParams();
                int right = lp.leftMargin + getMeasuredWidth(tView);
                int emptyWidth = layout_width - right;
                padding = emptyWidth / (len * 2);
            }


            int leftOffset = 0;
            for (int j = 0; j < lineMap.get(i).size(); j++) {
                TextView tView2 = lineMap.get(i).get(j);

                if (overspread) {
                    RelativeLayout.LayoutParams lp2 = (RelativeLayout.LayoutParams) tView2.getLayoutParams();
                    lp2.leftMargin = lp2.leftMargin + leftOffset;
                    leftOffset = (j + 1) * 2 * padding;
                }

                tView2.setPadding(
                        tView2.getPaddingLeft() + padding,
                        tView2.getPaddingTop(),
                        tView2.getPaddingRight() + padding,
                        tView2.getPaddingBottom());

                addView(tView2);
            }
        }
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);

    }

    public int getMeasuredWidth(View v) {
        return v.getMeasuredWidth();
    }

    public interface OnMultipleTVItemClickListener {
        void onMultipleTVItemClick(View view, int position);
    }
}
