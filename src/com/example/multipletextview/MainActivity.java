package com.example.multipletextview;

import java.util.ArrayList;
import java.util.List;

import com.example.multipetextview.R;
import com.example.multipletextview.MyRelativeLayout.OnMultipleTVItemClickListener;

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

		MyRelativeLayout rl=(MyRelativeLayout)findViewById(R.id.main_rl);
		rl.setOnMultipleTVItemClickListener(this);

	}

	@Override
	public void onMultipleTVItemClick(View view, int position) {
		// TODO Auto-generated method stub
		Toast.makeText(getApplicationContext(), "sssss"+position, Toast.LENGTH_SHORT).show();
	}

}
