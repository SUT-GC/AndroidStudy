package igouc.com.nodepad;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

import igouc.com.nodepad.Empty.Item;
import igouc.com.nodepad.util.ChangeDateAndLong;
import igouc.com.nodepad.util.ChangeIntAndLabel;
import igouc.com.nodepad.util.ItemUtil;

/**
 * Created by gouchao on 16-4-26.
 */
public class AddOneItemActivity extends Activity implements View.OnClickListener {

	private static int KEY_ALL_LEGAL = 0;
	private static int KEY_TITLE_NOTLEGAL = 1;
	private static int KEY_CONTENT_NOTLEGAL = 2;

	private EditText inputItemContent;
	private int windowHeight;

	private TextView inputTitle;
	private TextView inputContent;
	private TextView inputTime;
	private Spinner inputLabel;
	private Button save, cancel;
	private Date nowTime;

	private int selectLabelId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.additem);
		initWidget();
		initEditText();
		addSpinnerLisener();
		save.setOnClickListener(this);
		cancel.setOnClickListener(this);

	}

	public void insertNewItem(){
		String stringTitle = inputTitle.getText().toString().trim();
		String stringContent = inputContent.getText().toString().trim();
		long nowTimeLong = nowTime.getTime();


		//进行对输出合法否的检查
		int RETURN_KEY = checkLegal(stringTitle, stringContent);
		try {
			if(RETURN_KEY == KEY_ALL_LEGAL){
				//创建新Item并且进行插入
				Item item = new Item();
				item.setTitle(stringTitle);
				item.setContent(stringContent);
				item.setDatetime(nowTimeLong);
				item.setLabel(selectLabelId);
				Log.i("addItem","得到的Label的Int值为 : "+selectLabelId);
				ItemUtil.insertItem(item);
				finish();

			}else{
				if(RETURN_KEY == KEY_TITLE_NOTLEGAL)
					Toast.makeText(AddOneItemActivity.this,"标题不能空",Toast.LENGTH_SHORT).show();
				else if(RETURN_KEY == KEY_CONTENT_NOTLEGAL)
					Toast.makeText(AddOneItemActivity.this,"内容不能空",Toast.LENGTH_SHORT).show();
				else
					throw new Exception("合法字段检查出错");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}


	}

	private void addSpinnerLisener() {
		inputLabel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				selectLabelId = position;
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				selectLabelId = 0;
			}
		});
	}

	private int checkLegal(String stringTitle, String stringContent) {
		if(stringTitle.trim().equals("")){
			return KEY_TITLE_NOTLEGAL;
		}else if(stringContent.trim().equals("")){
			return KEY_CONTENT_NOTLEGAL;
		}else {
			return KEY_ALL_LEGAL;
		}
	}

	private void initWidget() {
		inputTitle = (TextView) findViewById(R.id.input_item_head);
		inputContent = (TextView) findViewById(R.id.input_item_content);
		inputTime = (TextView) findViewById(R.id.input_item_time);
		inputLabel = (Spinner) findViewById(R.id.input_item_label);
		save = (Button) findViewById(R.id.button_save_input);
		cancel = (Button) findViewById(R.id.button_cancel_input);
		nowTime = new Date();
		inputTime.setText(ChangeDateAndLong.formatDateFromDate(nowTime));
	}

	public void initEditText() {
		windowHeight = getWindowManager().getDefaultDisplay().getHeight();
		inputItemContent = (EditText) findViewById(R.id.input_item_content);
		inputItemContent.setHeight((int) (windowHeight * 0.6));
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.button_save_input:
				insertNewItem();
				break;
			case R.id.button_cancel_input:
				finish();
				break;
		}
	}
}
