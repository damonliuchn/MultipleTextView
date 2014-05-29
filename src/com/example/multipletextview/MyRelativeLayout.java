package com.example.multipletextview;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.multipetextview.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.RelativeLayout.LayoutParams;

public class MyRelativeLayout extends RelativeLayout {

	private Context context;
	private float textSize;
	private int textColor;
	private int wordMargin;
	private int lineMargin;
	private int textPaddingLeft;
	private int textPaddingRight;
	private int textPaddingTop;
	private int textPaddingBottom;
	private Drawable textBackground;

	private int layout_width;

	private OnMultipleTVItemClickListener listener;

	public MyRelativeLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		this.context = context;

		TypedArray array = context.obtainStyledAttributes(attrs,
				R.styleable.MyView);
		textColor = array.getColor(R.styleable.MyView_textColor, 0XFF00FF00); // 提供默认值，放置未指定
		textSize = array.getDimension(R.styleable.MyView_textSize, 14);
		wordMargin = array.getDimensionPixelSize(R.styleable.MyView_textWordMargin, 0);
		lineMargin = array.getDimensionPixelSize(R.styleable.MyView_textLineMargin, 0);
		textBackground = array.getDrawable(R.styleable.MyView_textBackground);
		textPaddingLeft = array.getDimensionPixelSize(R.styleable.MyView_textPaddingLeft, 0);
		textPaddingRight = array.getDimensionPixelSize(R.styleable.MyView_textPaddingRight, 0);
		textPaddingTop = array.getDimensionPixelSize(R.styleable.MyView_textPaddingTop, 0);
		textPaddingBottom = array.getDimensionPixelSize(R.styleable.MyView_textPaddingBottom, 0);
		array.recycle();
		//下边是获取系统属性
		int[] attrsArray = new int[] { android.R.attr.id, // 0
				android.R.attr.background, // 1
				android.R.attr.layout_width, // 2
				android.R.attr.layout_height, // 3
				android.R.attr.layout_marginRight, // 4
													// 这个地方必须放到android.R.attr.layout_width的下边
													// 不知道为什么
				android.R.attr.layout_marginRight // 5
		};
		TypedArray ta = context.obtainStyledAttributes(attrs, attrsArray);

		try {
			layout_width = ta.getDimensionPixelSize(2,ViewGroup.LayoutParams.MATCH_PARENT);
		} catch (Exception e) {
			// TODO: handle exception
			DisplayMetrics dm = getResources().getDisplayMetrics();
			layout_width = dm.widthPixels;
		}
		int marginRight = ta.getDimensionPixelSize(4, 0);
		int marginLeft = ta.getDimensionPixelSize(5, 0);
		layout_width = layout_width - marginRight - marginLeft;
		ta.recycle();

	}

	public OnMultipleTVItemClickListener getOnMultipleTVItemClickListener() {
		return listener;
	}

	public void setOnMultipleTVItemClickListener(
			OnMultipleTVItemClickListener listener) {
		this.listener = listener;
	}

	public void setTextViews(List<String> dataList) {
		if (dataList==null||dataList.size()==0) {
			return;
		}
		

		// 每一行拉伸
		int line = 0;
		Map<Integer, List<TextView>> lineMap = new HashMap<Integer, List<TextView>>();
		List<TextView> lineList = new ArrayList<TextView>();
		lineMap.put(0, lineList);

		// RelativeLayout rl=(RelativeLayout)findViewById(R.id.main_rl);
		int x = 0;
		int y = 0;

		for (int i = 0; i < dataList.size(); i++) {
			TextView tv = new TextView(context);
			tv.setText(dataList.get(i));
			tv.setTextSize(textSize);
			if (textBackground != null)
				tv.setBackgroundDrawable(textBackground);

			tv.setTextColor(textColor);
			tv.setPadding(textPaddingLeft, textPaddingTop, textPaddingRight,textPaddingBottom);
			tv.setTag(i);// 标记position
			tv.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if (listener != null) {
						listener.onMultipleTVItemClick(v, (Integer) v.getTag());
					}
				}
			});

			int w = View.MeasureSpec.makeMeasureSpec(0,
					View.MeasureSpec.UNSPECIFIED);
			int h = View.MeasureSpec.makeMeasureSpec(0,
					View.MeasureSpec.UNSPECIFIED);
			tv.measure(w, h);
			int tvh = tv.getMeasuredHeight();
			int tvw = getMeasuredWidth(tv);

			RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			if (x + tvw > layout_width) {
				x = 0;
				y = y + tvh + lineMargin;

				// 拉伸处理
				line++;
				lineMap.put(line, new ArrayList<TextView>());
			}
			lp.leftMargin = x;
			lp.topMargin = y;
			x = x + tvw + wordMargin;
			tv.setLayoutParams(lp);

			// 拉伸处理
			lineMap.get(line).add(tv);

		}
		// 每一行拉伸
		for (int i = 0; i <= line; i++) {

			// 该行最后一个位置
			int len = lineMap.get(i).size();
			TextView tView = lineMap.get(i).get(len - 1);

			RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) tView
					.getLayoutParams();
			int right = lp.leftMargin + getMeasuredWidth(tView);
			int emptyWidth = layout_width - right;
			int padding = emptyWidth / (len * 2);

			int leftOffset = 0;
			for (int j = 0; j < lineMap.get(i).size(); j++) {
				TextView tView2 = lineMap.get(i).get(j);

				RelativeLayout.LayoutParams lp2 = (RelativeLayout.LayoutParams) tView2
						.getLayoutParams();
				lp2.leftMargin = lp2.leftMargin + leftOffset;
				leftOffset = (j + 1) * 2 * padding;
				tView2.setPadding(tView2.getPaddingLeft() + padding,
						tView2.getPaddingTop(), tView2.getPaddingRight()
								+ padding, tView2.getPaddingBottom());
				// Log.e("aa", "leftmargin"+lp2.leftMargin);
				// Log.e("aa", "Padding"+tView2.getPaddingLeft()+"__"+
				// tView2.getPaddingTop()+"__"+ tView2.getPaddingRight()+"__"+
				// tView2.getPaddingBottom());
				// Log.e("aa", "width"+tView2.getMeasuredWidth());
				addView(tView2);
			}
		}
	}

	@Override
	protected void dispatchDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.dispatchDraw(canvas);

	}

	public int getMeasuredWidth(View v) {
		return v.getMeasuredWidth() + v.getPaddingLeft() + v.getPaddingRight();
	}

	interface OnMultipleTVItemClickListener {
		public void onMultipleTVItemClick(View view, int position);
	}
}
