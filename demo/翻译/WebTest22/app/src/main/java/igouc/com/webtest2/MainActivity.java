package igouc.com.webtest2;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends AppCompatActivity {
	TextView textView = null;
	Button send = null;
	EditText input = null;
	String src = "";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		textView = (TextView) findViewById(R.id.show);
		input = (EditText) findViewById(R.id.input);
		send = (Button) findViewById(R.id.send);
		final Handler handler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				if(msg.what == 123){
					textView.setText(msg.obj+"");
				}
			}
		};

		send.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				src = input.getText().toString();
				new Thread() {
					@Override
					public void run() {
						try {
							URL url = new URL("http://fanyi.youdao.com/translate?smartresult=dict&smartresult=rule&smartresult=ugc&sessionFrom=null");
							URLConnection conn = url.openConnection();
							conn.setRequestProperty("User-Agent", "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:46.0) Gecko/20100101 Firefox/46.0");
							conn.setRequestProperty("Accept", "application/json, text/javascript, */*; q=0.01");
							conn.setRequestProperty("connection", "keep-alive");
							conn.setDoInput(true);
							conn.setDoOutput(true);

							String param = "type=AUTO&i=" + src + "&doctype=json&xmlVersion=1.8&keyfrom=fanyi.web&ue=UTF-8&action=FY_BY_CLICKBUTTON&typoResult=true";

							Log.i("main", param);

							OutputStream out = conn.getOutputStream();
							out.write(param.getBytes());
							out.flush();
							BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
							StringBuffer stringBuffer = new StringBuffer();
							String count = null;
							while ((count = br.readLine()) != null) {
								stringBuffer.append(count);
							}

							Message message = new Message();
							message.obj = stringBuffer;
							message.what = 123;
							handler.sendMessage(message);

						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}.start();

			}
		});
	}
}
