package igouc.com.nodepad;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.AttributeSet;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.swipe.SwipeLayout;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import igouc.com.nodepad.Empty.Item;
import igouc.com.nodepad.util.ItemUtil;

public class nodepad extends AppCompatActivity
		implements NavigationView.OnNavigationItemSelectedListener {
	private  List<Map<String,Object>>  nodeList;
	private ShowNodeListAdapater adapater;
	private ListView listView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_nodepad);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		listView = (ListView) findViewById(R.id.show_node_listview);
		setTitle("记事本");
		setSupportActionBar(toolbar);

		FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);




		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
				this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
		drawer.setDrawerListener(toggle);
		toggle.syncState();

		NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
		navigationView.setNavigationItemSelectedListener(this);


		//对数组进行初始
		ItemUtil.init(this);
		nodeList = ItemUtil.getAllItems();

		//读取listview

		adapater = new ShowNodeListAdapater(this,nodeList);
		listView.setAdapter(adapater);

		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				Item item = new Item();
				item.setTitle("这里是title");
				item.setContent("这里是content" + new Date());
				item.setLabel(1);
				item.setPower(1);
				item.setDatetime((new Date()).getTime());
				ItemUtil.insertItem(item);
				Intent intent = new Intent(nodepad.this, AddOneItemActivity.class);
				startActivity(intent);
				//这里刷新了界面上的内容
				nodeList = ItemUtil.getAllItems();
				adapater = new ShowNodeListAdapater(nodepad.this,nodeList);
				listView.setAdapter(adapater);
//				Intent intent = new Intent(nodepad.this,nodepad.class);
//				startActivity(intent);

			}
		});
	}

	@Override
	public View onCreateView(String name, Context context, AttributeSet attrs) {
		return super.onCreateView(name, context, attrs);

	}

	@Override
	public void onBackPressed() {
		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		if (drawer.isDrawerOpen(GravityCompat.START)) {
			drawer.closeDrawer(GravityCompat.START);
		} else {
			super.onBackPressed();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.nodepad, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.action_settings) {
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	@SuppressWarnings("StatementWithEmptyBody")
	@Override
	public boolean onNavigationItemSelected(MenuItem item) {
		// Handle navigation view item clicks here.
		int id = item.getItemId();

		if (id == R.id.nav_all) {
//			LinearLayout linearLayout = (LinearLayout) findViewById(R.id.show_node);
//			TextView tv = new TextView(this);
//			tv.setText("这里是动态创建的textView");
//			linearLayout.addView(tv);

		} else if (id == R.id.nav_life) {
			
			
		} else if (id == R.id.nav_study) {

		} else if (id == R.id.nav_work) {

		}

		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		drawer.closeDrawer(GravityCompat.START);
		return true;
	}
}
