package igouc.com.nodepad;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * Created by gouchao on 16-4-26.
 */
public class AddOneItemActivity extends Activity {

	private EditText inputItemContent;
	private int windowHeight;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.additem);

		initEditText();

	}
	public void initEditText(){
		windowHeight = getWindowManager().getDefaultDisplay().getHeight();
		inputItemContent = (EditText) findViewById(R.id.input_item_content);
		inputItemContent.setHeight((int)(windowHeight*0.6));
	}

}
