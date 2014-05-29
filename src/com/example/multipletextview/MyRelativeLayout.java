package com.example.multipletextview;

import java.util.ArrayList;
import java.util.List;

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

public class MyRelativeLayout extends RelativeLayout{

	private Context context;
	private float textSize;
	private int textColor;
	private int wordMargin;
	private int lineMargin;
	private Drawable textBackground;
	
	private int layout_width;
	
	private OnMultipleTVItemClickListener listener;
	public MyRelativeLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		this.context=context;
		
		TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.MyView);   
		textColor = array.getColor(R.styleable.MyView_textColor, 0XFF00FF00); //提供默认值，放置未指定   
		textSize = array.getDimension(R.styleable.MyView_textSize, 36);  
		wordMargin = array.getDimensionPixelSize(R.styleable.MyView_textWordMargin, 0);
		lineMargin = array.getDimensionPixelSize(R.styleable.MyView_textLineMargin, 0);
		textBackground = array.getDrawable(R.styleable.MyView_textBackground);
		
		
		int[] attrsArray = new int[] {
		        android.R.attr.id, // 0
		        android.R.attr.background, // 1
		        android.R.attr.layout_width, // 2
		        android.R.attr.layout_height, // 3
		        android.R.attr.layout_marginRight, // 4  这个地方必须放到android.R.attr.layout_width的下边 不知道为什么
		        android.R.attr.layout_marginRight // 5
		    };
		    TypedArray ta = context.obtainStyledAttributes(attrs, attrsArray);

		    try {
		    	layout_width = ta.getDimensionPixelSize(2, ViewGroup.LayoutParams.MATCH_PARENT);
			} catch (Exception e) {
				// TODO: handle exception
				DisplayMetrics dm = getResources().getDisplayMetrics();
				layout_width=dm.widthPixels;
			}
		    int marginRight = ta.getDimensionPixelSize(4, 0);
		    int marginLeft = ta.getDimensionPixelSize(5, 0);
		    layout_width=layout_width-marginRight-marginLeft;
		    ta.recycle();
		    Log.e("ss", "啊啊啊啊——"+layout_width+"__");
		

	    setTextViews();
	}

	public OnMultipleTVItemClickListener getOnMultipleTVItemClickListener() {
		return listener;
	}

	public void setOnMultipleTVItemClickListener(OnMultipleTVItemClickListener listener) {
		this.listener = listener;
	}

	public void setTextViews(){
		
		List<String> dataList=new ArrayList<String>();
		dataList.add("Mason Liu");
		dataList.add("天盟");
		dataList.add("天盟天盟天盟天盟");
		dataList.add("Mason Mason Mason");
		
		dataList.add("Mason Liu");
		dataList.add("天盟");
		dataList.add("天盟天盟天盟");
		dataList.add("Mason Mason");
		
		//RelativeLayout rl=(RelativeLayout)findViewById(R.id.main_rl);
		int x=0;
		int y=0;
		int line=0;
		for (int i = 0; i < dataList.size(); i++) {
			TextView tv=new TextView(context);
			tv.setText(dataList.get(i));
			tv.setTextSize(textSize);
			if (textBackground != null)
				tv.setBackgroundDrawable(textBackground);
			
			tv.setTextColor(textColor);
			tv.setPadding(10, 10, 10, 10);
			tv.setTag(i);//标记position
			tv.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if (listener!=null) {
						listener.onMultipleTVItemClick(v, (Integer)v.getTag());
					}
				}
			});
			
			int w = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);  
			int h = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);  
			tv.measure(w, h);  
			int tvh =tv.getMeasuredHeight();  
			int tvw =tv.getMeasuredWidth();
			
			
			RelativeLayout.LayoutParams lp=new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			if (x+tvw>layout_width) {
				x=0;
				y=y+tvh+lineMargin;
				
				//这里是换行，此时把上一行的TextView 拉伸铺满父控件
				//line++;
			}
			lp.leftMargin=x;
            lp.topMargin=y;
            x=x+tvw+wordMargin;
			tv.setLayoutParams(lp);
			
			
//			tv.setTag(1, line);
//			if (i==dataList.size()-1) {//这里说明是最后一块了，把最后一行 做拉伸处理
//				
//			}
			addView(tv);

			Log.e("aa", "addwan"+tv.getWidth()+"_"+tv.getMeasuredWidth());
		}
	}
	@Override
	protected void dispatchDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.dispatchDraw(canvas);
		
	}
	interface OnMultipleTVItemClickListener{
		public void onMultipleTVItemClick(View view,int position);
	}
}
