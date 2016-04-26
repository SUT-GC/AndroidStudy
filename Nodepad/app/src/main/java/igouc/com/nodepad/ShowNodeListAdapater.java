package igouc.com.nodepad;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import igouc.com.nodepad.util.ChangeDateAndLong;
import igouc.com.nodepad.util.ChangeIntAndLabel;

/**
 * Created by gouchao on 16-4-25.
 */
public class ShowNodeListAdapater extends BaseSwipeAdapter {

	private List<Map<String,Object>> listNode;
	private Context context;
	public ShowNodeListAdapater(Context context,  List<Map<String,Object>> listNode){
		this.context = context;
		this.listNode = listNode;
	}

	@Override
	public int getSwipeLayoutResourceId(int i) {
		return R.id.swipe;
	}

	@Override
	public View generateView(int i, ViewGroup viewGroup) {
		View v = LayoutInflater.from(context).inflate(R.layout.showeach_swiperlayout,null);
		SwipeLayout swipeLayout = (SwipeLayout) v.findViewById(getSwipeLayoutResourceId(i));
		swipeLayout.setShowMode(SwipeLayout.ShowMode.LayDown);
		return v;
	}

	@Override
	public void fillValues(int i, View view) {
		Map<String,Object> each = listNode.get(i);

		TextView each_title = (TextView) view.findViewById(R.id.show_eachnoe_info);
		TextView each_content = (TextView) view.findViewById(R.id.show_each_item_content);
		TextView each_label = (TextView) view.findViewById(R.id.show_each_item_label);
		TextView each_data = (TextView) view.findViewById(R.id.show_each_item_date);

		each_title.setText((String)each.get("title"));
		each_content.setText((String)each.get("content"));
		each_label.setText(ChangeIntAndLabel.getLabelByInt((Integer) each.get("label")));
		each_data.setText(ChangeDateAndLong.getFromatDateString((Long) each.get("datetime")));
	}

	@Override
	public int getCount() {
		return listNode.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
}
