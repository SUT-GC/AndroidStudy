##Toast介绍

Toast的常用方法:    

* Toast Toast.makeToast(context, text, duration) 
* setDuration(duration)
* setGravity(gravity, xxOffset, yOffset) :Grsvity的static变量，偏移量，偏移量
* setText(text)
* show()


####Toast的自定义   

        Toast toast = Toast.makeText(NullTestMainActivity.this, "设置时间的Toast", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, -100);
        LinearLayout linearLayout = new LinearLayout(NullTestMainActivity.this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        ImageView imageView = new ImageView(NullTestMainActivity.this);
        imageView.setBackgroundResource(R.drawable.bizhi1);
        linearLayout.addView(imageView);
        TextView textView = new TextView(NullTestMainActivity.this);
        textView.setText("这里是文字");
        textView.setTextSize(20);
        linearLayout.addView(textView);
        toast.setView(linearLayout);
        toast.show();

