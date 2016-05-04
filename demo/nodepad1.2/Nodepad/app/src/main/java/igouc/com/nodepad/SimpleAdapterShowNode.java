package igouc.com.nodepad;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daimajia.swipe.SwipeLayout;

import java.util.List;
import java.util.Map;

/**
 * Created by gouchao on 16-4-25.
 */
public class SimpleAdapterShowNode extends RecyclerView.Adapter<MyViewHolder> {

	private Context context;
	private List<String> nodeList;
	private LayoutInflater inflater;
	private OnItemClickListener itemClickListener;

	public SimpleAdapterShowNode(Context context, List<String> nodeList){
		this.context = context;
		this.nodeList = nodeList;
		inflater = LayoutInflater.from(context);
	}

	//设置监听接口
	public interface OnItemClickListener{
		public void onItemClick(View view, int position);
		public void onItemLongClick(View view, int position);
	}

	//设置监听器
	public void setOnItemClickListener(OnItemClickListener itemClickListener){
		this.itemClickListener = itemClickListener;
	}

	@Override
	public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = inflater.inflate(R.layout.simpleadpater_item, parent, false);

		MyViewHolder myViewHolder = new MyViewHolder(view);
		return myViewHolder;
	}

	@Override
	public void onBindViewHolder(MyViewHolder holder, final int position) {
		holder.textView.setText(nodeList.get(position));
		if(itemClickListener != null){
			holder.textView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					itemClickListener.onItemClick(v,position);
				}
			});
			holder.textView.setOnLongClickListener(new View.OnLongClickListener() {
				@Override
				public boolean onLongClick(View v) {
					itemClickListener.onItemLongClick(v,position);
					return false;
				}
			});
		}

	}

	@Override
	public int getItemCount() {
		return nodeList.size();
	}

	public void addItem(int pos){
		nodeList.add(pos,"insert");
		notifyItemInserted(pos);
	}
	public void deleteItem(int pos){
		nodeList.remove(pos);
		notifyItemInserted(2);
	}
}
