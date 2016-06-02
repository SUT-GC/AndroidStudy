package igouc.com.nodepad;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.ImageSpan;
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

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URI;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

	private EditText inputTitle;
	private EditText inputContent;
	private TextView inputTime;
	private Spinner inputLabel;
	private Button save, cancel,insert_image;
	private Date nowTime;
	private int ID = -1;
	private int selectLabelId;
	//创建状态码，0为add，1为edit
	private int ADD_OR_EDIT = 0;

	//ContentResolver
	private ContentResolver resolver = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.additem);
		initWidget();
		//设置insert_image显示时机
		showInsertIamgeIcon();
		//给insert_image按钮添加事件
		addLisenerToInsertImage();
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

	//给插入图片按钮添加事件
	private void addLisenerToInsertImage() {
		insert_image.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
//				Intent getImage = new Intent(Intent.ACTION_PICK,
//						android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//				getImage.setType("image/*");
//				getImage.setAction(Intent.ACTION_GET_CONTENT);
//				startActivityForResult(getImage, 1);
				//根据SDK版本不同，启设置不同的intent
				if (Build.VERSION.SDK_INT < 19) {
					Intent intent = new Intent();
					intent.setType("image/*");
					intent.setAction(Intent.ACTION_GET_CONTENT);
					startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
				} else {
					Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
					intent.addCategory(Intent.CATEGORY_OPENABLE);
					intent.setType("image/*");
					startActivityForResult(intent, 1);
				}
			}
		});
	}

	//开始进入结果处理ActivityResult
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode == RESULT_OK){
			if(requestCode == 1){
				if(data != null){
					Uri imageUri = data.getData();
					try {
						InputStream is = resolver.openInputStream(imageUri);
						int fileLong = is.available();
						Log.i("AddOneItem","添加图片大小是"+fileLong);
						if(fileLong > 9*1024*1024){
							Toast.makeText(AddOneItemActivity.this,"添加失败：图片过大",Toast.LENGTH_SHORT).show();
						}else{
							Log.i("AddOneItem","添加图片合法");
							Bitmap imageBitmap = BitmapFactory.decodeStream(is);
							if(imageBitmap != null){
								insertImageIntoEditText(getBitmapMine(imageBitmap,imageUri));
							}
						}


					} catch (Exception e) {
						e.printStackTrace();
						Toast.makeText(AddOneItemActivity.this,"添加图片错误",Toast.LENGTH_SHORT).show();
					}

				}
			}
		}
	}

	//将字符换成图片
	private void insertImageIntoEditText(SpannableString bitmapMine) {
		Editable ea = inputContent.getText();
		int start = inputContent.getSelectionStart();
		ea.insert(start,bitmapMine);
		inputContent.setText(ea);
		inputContent.setSelection(start + bitmapMine.length());
	}

	//创建spannable
	private SpannableString getBitmapMine(Bitmap imageBitmap, Uri imageUri) {
		String picPath = imageUri.toString();
		Log.i("AddItem","获取到的绝对路径是"+picPath);
		//加上默认的头和尾巴
		SpannableString ss = new SpannableString("---igouc-"+picPath+"-com---");
		//等比例缩放图片
		ImageSpan span = new ImageSpan(AddOneItemActivity.this,zoomImage(imageBitmap,500));
		ss.setSpan(span, 0, picPath.length() + 9 + 7, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		return ss;
	}

	//等比例缩放图片成一个大小
	public Bitmap zoomImage(Bitmap bitmap, int width){
		Bitmap newImage = null;
		int height = (int) (((bitmap.getHeight() * 1.0)/ (bitmap.getWidth() * 1.0))* width);
		newImage = bitmap.createScaledBitmap(bitmap,width,height,false);
		return newImage;
	}

	private void setSchemeColor(int colorschamestate) {
		Log.i("additem", "开始配置颜色，状态码是:" + colorschamestate + "......");
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

	//设计插入图片按钮显示时机
	public void showInsertIamgeIcon(){
		inputContent.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
					insert_image.setVisibility(View.VISIBLE);
				} else {
					insert_image.setVisibility(View.INVISIBLE);
				}
			}
		});
	}

	//向EditView中填充数据
	private void fillValue(int id) {
		Item item = ItemUtil.selectItemById(id);
		inputTitle.setText(item.getTitle());
//		inputContent.setText(item.getContent());
		//填充输入内容中的图片
		changeStringToImage(inputContent,item.getContent());
		inputTime.setText(ChangeDateAndLong.getFromatDateString(item.getDatetime()));
		inputLabel.setSelection(item.getLabel(), true);

		//save按钮隐藏
		save.setVisibility(View.INVISIBLE);
		insert_image.setVisibility(View.INVISIBLE);

	}

	//讲字符串里的特定内容换成图片
	private void changeStringToImage(EditText inputContent, String content) {
		SpannableString ss = new SpannableString(content);
		//创建正则表达式进行匹配
		Pattern pattern = Pattern.compile("---igouc-content://com.android.providers.media.documents/document/image%[\\w|\\d]+-com---");
		Matcher ma = pattern.matcher(content);
		//获取匹配的内容
		while(ma.find()){
			Log.i("addItem","分解得到的图片路径是:"+ma.group().split("---igouc-")[1].split("-com---")[0]);
			String uriPath = ma.group().split("--igouc-")[1].split("-com---")[0];
			Bitmap bitmap = null;
			try {
				Uri imageuri = Uri.parse(uriPath);
				bitmap = MediaStore.Images.Media.getBitmap(resolver, imageuri);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(bitmap == null){
				ss = new SpannableString(ss.subSequence(0,ma.start())+"【图片丢失】"+ss.subSequence(ma.end(),ss.length()));
				ma = pattern.matcher(ss);
			}else{
				ImageSpan span = new ImageSpan(AddOneItemActivity.this,zoomImage(bitmap,500));
				ss.setSpan(span, ma.start(), ma.end(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
			}
		}
		inputContent.setText(ss);
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
		inputTitle = (EditText) findViewById(R.id.input_item_head);
		inputContent = (EditText) findViewById(R.id.input_item_content);
		inputTime = (TextView) findViewById(R.id.input_item_time);
		inputLabel = (Spinner) findViewById(R.id.input_item_label);
		save = (Button) findViewById(R.id.button_save_input);
		cancel = (Button) findViewById(R.id.button_cancel_input);
		insert_image = (Button) findViewById(R.id.button_insert_image);
		nowTime = new Date();
		inputTime.setText(ChangeDateAndLong.formatDateFromDate(nowTime));
		//初始化ContentResolver
		resolver = getContentResolver();
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
