package igouc.com.nodepad;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;

import junit.framework.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import igouc.com.nodepad.Empty.ColorSchema;
import igouc.com.nodepad.util.ChangeDateAndLong;
import igouc.com.nodepad.util.ChangeIntAndLabel;
import igouc.com.nodepad.util.ItemUtil;

/**
 * Created by gouchao on 16-4-25.
 */
public class ShowNodeListAdapater extends BaseSwipeAdapter {

	private List<Map<String,Object>> listNode;
	private Context context;
	private FlashMainActivity flashView;
	public ShowNodeListAdapater(Context context,  List<Map<String,Object>> listNode , FlashMainActivity flashView){
		this.flashView = flashView;
		this.context = context;
		this.listNode = listNode;
	}

	public interface FlashMainActivity{
		public int getLabelState();
		public void flash(int labelState);
	}

	@Override
	public int getSwipeLayoutResourceId(int i) {
		return R.id.swipe;
	}

	@Override
	public View generateView(int i, final ViewGroup viewGroup) {
		View v = LayoutInflater.from(context).inflate(R.layout.showeach_swiperlayout, null);
		//设置配色方案
		setColorScheme(nodepad.COLORSCHAMESTATE,v);

		final SwipeLayout swipeLayout = (SwipeLayout) v.findViewById(getSwipeLayoutResourceId(i));
		swipeLayout.setShowMode(SwipeLayout.ShowMode.LayDown);
		final TextView tvId = (TextView) v.findViewById(R.id.show_eachitem_id);
		final TextView tvPower = (TextView) v.findViewById(R.id.show_eachitem_power);
		LinearLayout star = (LinearLayout) v.findViewById(R.id.button_setitemtotop);
		LinearLayout delete = (LinearLayout) v.findViewById(R.id.button_deleteitem);

		star.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				int itemPower = Integer.parseInt(tvPower.getText().toString());
				int itemId = Integer.parseInt(tvId.getText().toString());
				//改变Item的置顶元素
				if (itemPower == 0) {
					ItemUtil.setPowerById(itemId, 1);
					Toast.makeText(context, "设置置顶成功", Toast.LENGTH_SHORT).show();
					flashView.flash(flashView.getLabelState());
				} else {
					ItemUtil.setPowerById(itemId, 0);
					Toast.makeText(context, "取消置顶成功", Toast.LENGTH_SHORT).show();
					flashView.flash(flashView.getLabelState());
				}
			}
		});

		delete.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ItemUtil.deleteItemById(Integer.parseInt(tvId.getText().toString()));
				Toast.makeText(context, "删除成功", Toast.LENGTH_SHORT).show();
				flashView.flash(0);

			}
		});


		return v;
	}

	public void setColorScheme(int colorScheme,View v) {
		Log.i("nodepad", "开始配置颜色，状态码是:" + colorScheme + "......");
		switch (colorScheme){
			case 0:{
				v.findViewById(R.id.show_each_item_surfaceview).setBackgroundResource(ColorSchema.Default.getItemBackground());
				break;
			}case 1:{
				v.findViewById(R.id.show_each_item_surfaceview).setBackgroundResource(ColorSchema.Paper.getItemBackground());
				break;
			}case 2:{
				v.findViewById(R.id.show_each_item_surfaceview).setBackgroundResource(ColorSchema.Simple.getItemBackground());
				break;
			}
		}
	}
	@Override
	public void fillValues(int i, View view) {
		Map<String,Object> each = listNode.get(i);

		TextView each_title = (TextView) view.findViewById(R.id.show_eachnoe_info);
		TextView each_content = (TextView) view.findViewById(R.id.show_each_item_content);
		TextView each_label = (TextView) view.findViewById(R.id.show_each_item_label);
		TextView each_data = (TextView) view.findViewById(R.id.show_each_item_date);
		TextView each_id = (TextView) view.findViewById(R.id.show_eachitem_id);
		TextView each_power = (TextView) view.findViewById(R.id.show_eachitem_power);
		TextView each_top = (TextView) view.findViewById(R.id.show_item_powershow);

		each_title.setText((String)each.get("title"));
		each_content.setText((String)each.get("content"));
		each_label.setText(ChangeIntAndLabel.getLabelByInt((Integer) each.get("label")));
		each_data.setText(ChangeDateAndLong.getFromatDateString((Long) each.get("datetime")));
		each_id.setText(each.get("id").toString());
		each_power.setText(each.get("power").toString());

		//设置置顶图标显示
		if(Integer.parseInt((each.get("power").toString()))==1){
			each_top.setVisibility(View.VISIBLE);
		}else{
			each_top.setVisibility(View.INVISIBLE);
		}

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
