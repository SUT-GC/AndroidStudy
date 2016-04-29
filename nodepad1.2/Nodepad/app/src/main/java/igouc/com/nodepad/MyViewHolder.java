package igouc.com.nodepad;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by gouchao on 16-4-25.
 */
public class MyViewHolder extends RecyclerView.ViewHolder {
	TextView textView;

	public MyViewHolder(View itemView) {
		super(itemView);
		textView = (TextView) itemView.findViewById(R.id.test_textview2);

	}
}
