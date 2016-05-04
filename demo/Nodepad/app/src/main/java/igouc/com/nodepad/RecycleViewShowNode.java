package igouc.com.nodepad;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.daimajia.swipe.SwipeLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gouchao on 16-4-25.
 */
public class RecycleViewShowNode extends AppCompatActivity {
	private RecyclerView recyclerView;
	private List<String> nodeList;
	private SimpleAdapterShowNode adapter;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.test);
		initData();
//		initView();
		recyclerView = (RecyclerView) findViewById(R.id.recyclerview_show_node);
		adapter = new SimpleAdapterShowNode(this,nodeList);
		recyclerView.setAdapter(adapter);
		//设置RecyclerView的布局管理
		RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
		recyclerView.setLayoutManager(layoutManager);
		//设置RecycleView的分割线
		recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL_LIST));
		//设置默认动画效果
		recyclerView.setItemAnimator(new DefaultItemAnimator());


		adapter.setOnItemClickListener(new SimpleAdapterShowNode.OnItemClickListener() {
			@Override
			public void onItemClick(View view, int position) {
				Toast.makeText(RecycleViewShowNode.this,"click点击了事件",Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onItemLongClick(View view, int position) {
				Toast.makeText(RecycleViewShowNode.this,"long click点击了事件",Toast.LENGTH_SHORT).show();
			}
		});
	}

	private void initView() {

	}

	private void initData() {
		nodeList = new ArrayList<>();
		for(int i = 'A'; i <= 'z'; i++){
			nodeList.add(""+(char)i);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.nodepad,menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return super.onOptionsItemSelected(item);
	}


}
