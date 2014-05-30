package com.dd544.bu;

import java.util.ArrayList;
import java.util.List;

import com.dd544.bu.MultipleTextView.OnMultipleTVItemClickListener;
import com.dd544.multipletextview.R;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

public class MainActivity extends ActionBarActivity implements OnMultipleTVItemClickListener{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		
		List<String> dataList = new ArrayList<String>();
		
		dataList.add("Mason Liu");
		dataList.add("Mason Liu");
		
		dataList.add("天盟天盟天盟天盟");
		dataList.add("Mason Mason Mason");

		dataList.add("Mason Liu");
		dataList.add("天盟");
		dataList.add("天盟天盟天盟");
		dataList.add("Mason Mason");

		dataList.add("Mason");
		dataList.add("天");
		dataList.add("天");
		dataList.add("Ma");
		
		
		MultipleTextView rl=(MultipleTextView)findViewById(R.id.main_rl);
		rl.setOnMultipleTVItemClickListener(this);
		rl.setTextViews(dataList);
		
	}

	@Override
	public void onMultipleTVItemClick(View view, int position) {
		// TODO Auto-generated method stub
		Toast.makeText(getApplicationContext(), "sssss"+position, Toast.LENGTH_SHORT).show();
	}

}
