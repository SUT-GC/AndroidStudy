package igouc.com.nodepad;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
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

import igouc.com.nodepad.Empty.ColorSchema;
import igouc.com.nodepad.Empty.Item;
import igouc.com.nodepad.util.ChangeDateAndLong;
import igouc.com.nodepad.util.ChangeIntAndLabel;
import igouc.com.nodepad.util.ItemUtil;

/**
 * Created by gouchao on 16-4-26.
 */
public class AddOneItemActivity extends Activity implements View.OnClickListener {

	enum Screen{
		HORIZONTAL,VERTICAL
	}
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
	private int ID = -1;
	private int selectLabelId;
	//创建状态码，0为add，1为edit
	private int ADD_OR_EDIT = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.additem);
		initWidget();
		//判断横竖屏，进行EditView的宽度控制
		//判断横屏
		if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
			initEditText(Screen.VERTICAL);
		}else{
			initEditText(Screen.HORIZONTAL);
		}

		/*
		*判断bundel中是否存入item
		*如果有item的数据，则向界面中添加数据：即为修改Item界面
		*如果没有Item的数据，则为添加Item界面
		*/
		if(getIntent()!=null && getIntent().getExtras() != null && getIntent().getExtras().getString("itemId")!=null ) {
			//读出Id
			ID = Integer.parseInt(getIntent().getExtras().getString("itemId"));
			//填充数据，隐藏save按钮
			fillValue(ID);
			//改变状态码的值
			ADD_OR_EDIT = 1;
			//当inputTitle与inputContent内容改变时，save按钮显示
			setSaveButtonShow();
			//当label改变时候，save按钮显示
			addSpinnerLisener();
			save.setOnClickListener(this);

		}else{
			//改变状态码的值
			ADD_OR_EDIT = 0;
			//如果没有Item，则为添加Item界面，save与spinner的监听事件则不一样
			addSpinnerLisener();
			save.setOnClickListener(this);
		}

		cancel.setOnClickListener(this);

		//设置背景颜色和线条颜色
		setSchemeColor(nodepad.COLORSCHAMESTATE);


	}

	private void setSchemeColor(int colorschamestate) {
		Log.i("additem","开始配置颜色，状态码是:"+colorschamestate+"......");
		switch (colorschamestate){
			case 0:{
				findViewById(R.id.additem_content_all).setBackgroundResource(ColorSchema.Default.getAddBackground());
				findViewById(R.id.input_item_head).setBackgroundResource(ColorSchema.Default.getAddBackground());
				findViewById(R.id.input_item_content).setBackgroundResource(ColorSchema.Default.getAddBackground());
				findViewById(R.id.item_content).setBackgroundResource(ColorSchema.Default.getAddFrame());
				findViewById(R.id.input_head).setBackgroundResource(ColorSchema.Default.getAddFrame());
				break;
			}case 1:{
				findViewById(R.id.additem_content_all).setBackgroundResource(ColorSchema.Paper.getAddBackground());
				findViewById(R.id.input_item_head).setBackgroundResource(ColorSchema.Paper.getAddBackground());
				findViewById(R.id.input_item_content).setBackgroundResource(ColorSchema.Paper.getAddBackground());
				findViewById(R.id.item_content).setBackgroundResource(ColorSchema.Paper.getAddFrame());
				findViewById(R.id.input_head).setBackgroundResource(ColorSchema.Paper.getAddFrame());
				break;
			}case 2:{
				findViewById(R.id.additem_content_all).setBackgroundResource(ColorSchema.Simple.getAddBackground());
				findViewById(R.id.input_item_head).setBackgroundResource(ColorSchema.Simple.getAddBackground());
				findViewById(R.id.input_item_content).setBackgroundResource(ColorSchema.Simple.getAddBackground());
				findViewById(R.id.item_content).setBackgroundResource(ColorSchema.Simple.getAddFrame());
				findViewById(R.id.input_head).setBackgroundResource(ColorSchema.Simple.getAddFrame());
				break;
			}
		}
	}

	//将InputTitle与InputContent更改的时候，则显示save按钮
	private void setSaveButtonShow() {
		inputTitle.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				save.setVisibility(View.INVISIBLE);
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				save.setVisibility(View.VISIBLE);
			}

			@Override
			public void afterTextChanged(Editable s) {
				save.setVisibility(View.VISIBLE);
			}
		});
		inputContent.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				save.setVisibility(View.INVISIBLE);
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				save.setVisibility(View.VISIBLE);
			}

			@Override
			public void afterTextChanged(Editable s) {
				save.setVisibility(View.VISIBLE);
			}
		});
	}

	//向EditView中填充数据
	private void fillValue(int id) {
		Item item = ItemUtil.selectItemById(id);
		inputTitle.setText(item.getTitle());
		inputContent.setText(item.getContent());
		inputTime.setText(ChangeDateAndLong.getFromatDateString(item.getDatetime()));
		inputLabel.setSelection(item.getLabel(), true);

		//save按钮隐藏
		save.setVisibility(View.INVISIBLE);
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
				Log.i("addItem", "得到的Label的Int值为 : " + selectLabelId);
				ItemUtil.insertItem(item);

				//每次插入新的事务的时候，状态码都设置为0；
				nodepad.LABELSTATE = 0;
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

	private void updateItem() {
		Item item = new Item();
		item.setTitle(inputTitle.getText().toString());
		item.setContent(inputContent.getText().toString());
		selectLabelId = inputLabel.getSelectedItemPosition();
		item.setLabel(selectLabelId);
		ItemUtil.updateItemById(ID,item);
		finish();
	}

	private void addSpinnerLisener() {
		inputLabel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				selectLabelId = position;
				save.setVisibility(View.VISIBLE);
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

	public void initEditText(Screen screen) {
		if(screen == Screen.VERTICAL){
			windowHeight = getWindowManager().getDefaultDisplay().getHeight();
			inputItemContent = (EditText) findViewById(R.id.input_item_content);
			inputItemContent.setHeight((int) (windowHeight * 0.6));
		}
		if(screen == Screen.HORIZONTAL){
			windowHeight = getWindowManager().getDefaultDisplay().getHeight();
			inputItemContent = (EditText) findViewById(R.id.input_item_content);
			inputItemContent.setHeight((int) (windowHeight * 0.4));
		}

	}


	@Override
	public void onClick(View v) {
		switch (ADD_OR_EDIT){
			//添加Item下的save与cancele的实现
			case 0:{
				switch (v.getId()){
					case R.id.button_save_input:
						insertNewItem();
						break;
					case R.id.button_cancel_input:
						finish();
						break;
				}
				break;
			}
			//编辑Item下的save与cancele的实现
			case 1:{
				switch (v.getId()){
					case R.id.button_save_input:
						updateItem();
						break;
					case R.id.button_cancel_input:
						finish();
						break;
				}
				break;
			}
		}

	}

}
