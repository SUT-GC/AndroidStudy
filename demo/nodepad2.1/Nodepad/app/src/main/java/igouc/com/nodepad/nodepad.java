package igouc.com.nodepad;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Map;

import igouc.com.nodepad.Empty.ColorSchema;
import igouc.com.nodepad.util.ItemUtil;

public class nodepad extends AppCompatActivity
		implements NavigationView.OnNavigationItemSelectedListener, ShowNodeListAdapater.FlashMainActivity {
	private List<Map<String, Object>> nodeList;
	private ShowNodeListAdapater adapater;
	private ListView listView;
	private int LIFE = 1;
	private int STUDY = 2;
	private int WORK = 3;
	public static int LABELSTATE = 0;
	public static int COLORSCHAMESTATE = 0;
	//创建一个临时变量
	private static int COLORSCHAMESTATE_TEMP = 0;
	private NavigationView navigationView;
	private AlertDialog showColorScheme;
	private SharedPreferences preferences;
	private SharedPreferences.Editor editor;
	private String[] menuItems = new String[]{
			"系统默认",
			"古朴纸张",
			"黑白简约"
	};


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i("nodepad", "onCreate进行创建");

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

		navigationView = (NavigationView) findViewById(R.id.nav_view);
		navigationView.setNavigationItemSelectedListener(this);


		/*
		* 初始化配色方案
		* 必须在adapter适配器放入之前进行初始
		*/
		//初始化Preferences
		preferences = getSharedPreferences("nodepad_schame", MODE_PRIVATE);
		//初始化editor
		editor = preferences.edit();
		//获取相应的状态码
		COLORSCHAMESTATE = getColorSchemaKey();
		//进行对其配色

		//初始化弹出菜单
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("选择配色方案");
		builder.setSingleChoiceItems(menuItems, COLORSCHAMESTATE, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				COLORSCHAMESTATE_TEMP = which;
			}
		});
		builder.setNegativeButton("确认", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				COLORSCHAMESTATE = COLORSCHAMESTATE_TEMP;
				writeSchemeKey(COLORSCHAMESTATE);
				onStart();
			}
		});
		builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				COLORSCHAMESTATE_TEMP = COLORSCHAMESTATE;
			}
		});
		showColorScheme = builder.create();


		//对数组进行初始
		ItemUtil.init(this);
		nodeList = ItemUtil.getItemsByLabel(LABELSTATE);

		//读取listview

		adapater = new ShowNodeListAdapater(this, nodeList, this);
		listView.setAdapter(adapater);

		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(nodepad.this, AddOneItemActivity.class);
				startActivity(intent);
			}
		});

		//添加点击查看item的事件
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				TextView viewId = (TextView) view.findViewById(R.id.show_eachitem_id);
				String itemId = viewId.getText().toString();
				Bundle bundle = new Bundle();
				bundle.putString("itemId", itemId);
				Intent intent = new Intent(nodepad.this, AddOneItemActivity.class);
				intent.putExtras(bundle);
				nodepad.this.startActivity(intent);
			}
		});

	}

	@Override
	protected void onStart() {
		Log.i("main", "调用onStart方法.....");
		//每次onstart的时候，都将全部的按钮选中
		Menu menu = navigationView.getMenu();
		MenuItem menuItem = menu.getItem(LABELSTATE + 1);
		Log.i("获得的菜单是", menuItem.getTitle().toString() + ".........");

		menuItem.setChecked(true);
		flashView(LABELSTATE);
		super.onStart();
	}

	//查出全部的Item并且刷新适配器
	private void flashView() {
		//这里刷新了界面上的内容
		nodeList = ItemUtil.getAllItems();
		adapater = new ShowNodeListAdapater(nodepad.this, nodeList, this);
		listView.setAdapter(adapater);
	}

	//根据label_int查处Item并且刷新适配器
	private void flashView(int labelKey) {
		//判断是否为0，如果为0，则进行全部刷新，否则进行按标签刷新
		if (labelKey == 0) {
			flashView();
		} else {
			nodeList = ItemUtil.getItemsByLabel(labelKey);
			adapater = new ShowNodeListAdapater(nodepad.this, nodeList, this);
			listView.setAdapter(adapater);
		}
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
			showColorScheme.show();
		}

		return super.onOptionsItemSelected(item);
	}

	@SuppressWarnings("StatementWithEmptyBody")
	@Override
	public boolean onNavigationItemSelected(MenuItem item) {
		// Handle navigation view item clicks here.
		int id = item.getItemId();
		if (id == R.id.nav_all) {
			LABELSTATE = 0;
			flashView();
		} else if (id == R.id.nav_life) {
			LABELSTATE = 1;
			flashView(LIFE);
		} else if (id == R.id.nav_study) {
			LABELSTATE = 2;
			flashView(STUDY);
		} else if (id == R.id.nav_work) {
			LABELSTATE = 3;
			flashView(WORK);
		}

		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		drawer.closeDrawer(GravityCompat.START);
		return true;
	}

	@Override
	public int getLabelState() {
		return LABELSTATE;
	}

	@Override
	public void flash(int labelState) {
		flashView(labelState);
	}

	public int getColorSchemaKey() {
		int key = 0;
		key = preferences.getInt("schemeKey", 0);

		if (ColorSchema.checkStateKeyLegal(key)) {
			Log.i("nodepad", "获取了Color的状态码是:" + key + "......");
			return key;
		} else {
			key = 0;
			Toast.makeText(this, "状态码文件被修改至损坏", Toast.LENGTH_SHORT).show();
			return key;
		}
	}

	public void writeSchemeKey(int key) {
		if (ColorSchema.checkStateKeyLegal(key)) {
			Log.i("nodepad", "写入的状态码是:" + key + "......");
			editor.putInt("schemeKey", key);
			editor.commit();
		} else {
			Toast.makeText(this, "状态码错误", Toast.LENGTH_SHORT).show();
		}

	}


}
