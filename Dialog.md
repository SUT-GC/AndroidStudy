##Dialog介绍    

* AlertDialog    
    - 使用AlertDialog.builder中的create方法返回AlertDialog。
    - alertDialog.show()进行显示
    - AlertDialog.builder对Dialog进行包装的方法
        + setTitle :为对话框设置标题
        + setIcon :为对话框设置图标
        + setMessage :为对话框设置内容
        + setView :为对话框自定义样式
        + setItems :设置要显示的一个list
        + setMultiChoiceItems :用来设置对话框显示复选框
        + setSingleChoiceItems :设置单选按钮
        + setNeutralButton :设置普通按钮
        + setPositiveButton :给对话框添加确认按钮
        + setNegativeButton :对话框添加取消按钮
    - cancel() :取消dialog显示


> 当设置setSingleChoiceItems的时候，请不要设置setMessage，不然会有一个会被隐藏

`=========测试代码==========`    

    public class NullTestMainActivity extends Activity {
        Button b1,b2,b3;
        String[] persons  = new String[]{"男生","女生","女博士","程序员"};
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.null_main_activity);
            b1 = (Button) findViewById(R.id.null_bu1);
            b2 = (Button) findViewById(R.id.null_bu2);
            b3 = (Button) findViewById(R.id.null_bu3);
            b1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(NullTestMainActivity.this);
                    //设置单选按钮
    //              builder.setSingleChoiceItems(persons, 0, new DialogInterface.OnClickListener() {
    //                  @Override
    //                  public void onClick(DialogInterface dialog, int which) {
    //                      Toast.makeText(NullTestMainActivity.this,"点击的是第"+which+"个",Toast.LENGTH_SHORT).show();
    //                  }
    //              });
                    //设置多选按钮
                    builder.setMultiChoiceItems(persons, null, new DialogInterface.OnMultiChoiceClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                            Toast.makeText(NullTestMainActivity.this,"点击的是第"+which+"个被选中"+isChecked,Toast.LENGTH_SHORT).show();
                        }
                    });
                    builder.setTitle("对话框的标题");
    //              builder.setMessage("对话框的消息");
                    builder.setIcon(R.drawable.bizhi1);
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            b2.setVisibility(View.GONE);
                        }
                    });
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            b2.setVisibility(View.VISIBLE);
                        }
                    });
                    builder.setNeutralButton("普通",null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            });
        }
    }

