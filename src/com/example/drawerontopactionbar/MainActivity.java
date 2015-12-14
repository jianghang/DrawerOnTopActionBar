package com.example.drawerontopactionbar;

import android.app.ActionBar;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.DrawerLayout.DrawerListener;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

public class MainActivity extends Activity implements OnItemClickListener {

	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;
	private ActionBar actionBar;
	private int screenWidth;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
		Point point = new Point();
		wm.getDefaultDisplay().getSize(point);
		screenWidth = point.x;

		moveDrawerToTop();

		initActionBar();
		initDrawer();
	}

	private void moveDrawerToTop() {
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		DrawerLayout drawer = (DrawerLayout) inflater.inflate(R.layout.decor,
				null);

		ViewGroup decor = (ViewGroup) getWindow().getDecorView();
		View child = decor.getChildAt(0);
		decor.removeView(child);
		FrameLayout container = (FrameLayout) drawer
				.findViewById(R.id.container);
		container.addView(child, 0);
		drawer.findViewById(R.id.drawer).setPadding(0, getStatusBarHeight(), 0,
				0);

		decor.addView(drawer);
	}

	private void initActionBar() {
		actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setHomeButtonEnabled(true);
	}

	private void initDrawer() {
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.drawer);
		mDrawerLayout.setDrawerListener(createDrawerToggle());
		ListAdapter adapter = (ListAdapter) (new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, getResources()
						.getStringArray(R.array.nav_items)));
		mDrawerList.setAdapter(adapter);
		mDrawerList.setOnItemClickListener(this);
	}
	
	private DrawerListener createDrawerToggle() {
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, R.string.drawer_open,
				R.string.drawer_close) {
			
			@Override
			public void onDrawerClosed(View view) {
				super.onDrawerClosed(view);
			}
			
			@Override
			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
			}
			
			@Override
			public void onDrawerStateChanged(int state) {
			}
		};
		return mDrawerToggle;
	}

	public int getStatusBarHeight() {
		int result = 0;
		int resourceId = getResources().getIdentifier("status_bar_height",
				"dimen", "android");
		if (resourceId > 0) {
			result = getResources().getDimensionPixelSize(resourceId);
		}
		return result;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);

		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		mDrawerToggle.syncState();
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}

		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		mDrawerToggle.syncState();
		
		android.view.ViewGroup.LayoutParams layoutparam = mDrawerList.getLayoutParams();
		layoutparam.width = (int) (screenWidth * 0.8);
		mDrawerList.setLayoutParams(layoutparam);
	}


	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		FragmentManager fm = getFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();

		if (position == 0) {
			ft.replace(R.id.main_content, new MainFragment());
		}

		ft.commit();
		mDrawerLayout.closeDrawer(mDrawerList);
	}
}
